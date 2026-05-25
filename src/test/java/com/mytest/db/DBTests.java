package com.mytest.db;


import com.mytest.automation.controller.DBModule;
import com.mytest.automation.utils.Datapool;
import com.mytest.automation.utils.Logs;
import com.mytest.automation.utils.TestCase;
import org.testng.SkipException;
import org.testng.annotations.*;

/**
 * @author Rajani
 */
public class DBTests extends TestCase {

    DBModule db;
    Logs logger = Logs.getInstance();
    Datapool dp = new Datapool();

    @Parameters("dataFile")
    @BeforeClass
    public void loadData(String dataFile) throws Throwable {
        dp.loadDatapool(dataFile, getClass().getSimpleName());
        Logs.info("Loaded datapool");
        dp.setDataRow("DB Connection");
        db = new DBModule(dp.getData("Environment") + "_DB");
    }



    private void checkExecutionStatus() throws Throwable {
        if (!(dp.getData("execute").equalsIgnoreCase("yes"))) {
            throw new SkipException("Skipped the test");
        }
    }
}