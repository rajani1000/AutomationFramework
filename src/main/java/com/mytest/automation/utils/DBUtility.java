package com.mytest.automation.utils;


import java.sql.*;
import java.util.Properties;

/**
 * @author Rajani
 */
public class DBUtility {
    private Connection conn = null;
    private String connectionString = "";
    private String userName = "";
    private String password = "";
    Logs logger = Logs.getInstance();

    public DBUtility(String env) throws Exception {
        this.connectionString = new Environments().getProperty(env);
     if (env.toLowerCase().contains("Saucedemo")) {
            this.userName = TestProps.get(env + "_userName");
            this.password = TestProps.get(env + "_password");
        }
        setConnection();
    }

    private void setConnection() throws SQLException, Exception {
        try {
            if (conn == null || conn.isClosed()) {
                if (!connectionString.isEmpty() || !userName.isEmpty() || !password.isEmpty()) {
                    Class.forName("oracle.jdbc.driver.OracleDriver");
                    Properties prop = new Properties();
                    prop.put("user", this.userName);
                    prop.put("password", this.password);
                    conn = DriverManager.getConnection(connectionString,  prop);
                } else {
                    throw new RuntimeException("DB connection properties are empty, please check connection string, username and password");
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public Connection getConnection() throws SQLException, Exception {
        if (conn == null || conn.isClosed()) {
            setConnection();
        }
        return conn;
    }

    public ResultSet getResultSet(String sql) throws SQLException, Exception {
        ResultSet rs;
        Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_READ_ONLY);


           /* try(Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE,
                    ResultSet.HOLD_CURSORS_OVER_COMMIT)) {*/
            rs = stmt.executeQuery(sql);
            return rs;

    }

    public Object retrieveValue(String sql, String columnName) throws SQLException {
        Object val = null;
        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                val = rs.getObject(columnName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return val;
    }

    public void updateMultiplerowsValue(String sql, String columnName, String updateVal) throws SQLException {
        try (Statement stmt =
                     conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE)) {
            ResultSet uprs = stmt.executeQuery(sql);
            while (uprs.next()) {
                //String s = uprs.getString(columnName);
                uprs.updateString(columnName, updateVal);
                uprs.updateRow();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public void updateCellValue(String sql, Object updateVal, String columnName) throws SQLException {
        try (Statement stmt =
                     conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE)) {
            ResultSet uprs = stmt.executeQuery(sql);
            while (uprs.next()) {
                //String s = uprs.getString(columnName);
              //  uprs.updateString(columnName, updateVal);
                uprs.updateObject(columnName, updateVal);
                uprs.updateRow();

            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public void updateColumnVal(String sql) throws SQLException {
        logger.info("Executing query: "+ sql);
        PreparedStatement pstmt = conn.prepareStatement(sql);
        logger.info("Query execution completed");
        int rowsUpdated = pstmt.executeUpdate();
        if(rowsUpdated > 0) {
            logger.info("Updated rows: "+ rowsUpdated);
        } else {
            logger.info("No rows were updated, Please try again");
        }
    }


    public void disconnect() throws SQLException {
        if (conn != null) {
            conn.close();
        }
    }
}
