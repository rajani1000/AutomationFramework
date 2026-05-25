package com.mytest.automation.controller;


import com.mytest.automation.utils.DBUtility;

public class DBModule extends DBUtility {
    public DBModule(String env) throws Exception {
        super(env);
    }

    public void updateVal(String sql) throws Exception {
        updateColumnVal(sql);
    }
}
