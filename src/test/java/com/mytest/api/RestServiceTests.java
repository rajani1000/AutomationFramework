package com.mytest.api;


import com.mytest.automation.controller.APIModule;
import com.mytest.automation.utils.Datapool;
import com.mytest.automation.utils.Logs;
import org.testng.SkipException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

/**
 * @author Rajani
 */
public class RestServiceTests {

    APIModule ac;
    Logs logger = Logs.getInstance();
    Datapool dp = new Datapool();


    @Parameters("dataFile")
    @BeforeClass
    public void loadDataPool(String dataFile) throws Throwable {
        dp.loadDatapool(dataFile, getClass().getSimpleName());
        Logs.info("Loaded datapool");
        dp.setDataRow("Load Data");
        String environment = dp.getData("Environment");
        ac = new APIModule(environment + "_API");
    }

    @Test
    public void addPet() throws Throwable {
        Logs.info("************************Add Pet*******************");
        dp.setDataRow("Add Pet");
        checkExecutionStatus();
        String category = dp.getData("ParamVal1");
        String name = dp.getData("ParamVal2");
        String tag = dp.getData("ParamVal3");
        ac.addPet(category, name, tag);
    }

    @Test
    public void getPet() throws Throwable {
        Logs.info("************************Get Pet*******************");
        dp.setDataRow("Get Pet");
        checkExecutionStatus();
        String id = dp.getData("ParamVal1");
        ac.getPet(id);
    }

    private void checkExecutionStatus() throws Throwable {
        if (!(dp.getData("execute").equalsIgnoreCase("yes"))) {
            throw new SkipException("Skipped the test");
        }
    }

}