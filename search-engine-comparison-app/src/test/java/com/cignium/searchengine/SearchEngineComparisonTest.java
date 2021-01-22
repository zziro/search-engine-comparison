package com.cignium.searchengine;

import com.cignium.searchengine.model.*;
import com.cignium.searchengine.model.google.*;
import com.cignium.searchengine.service.impl.*;
import com.cignium.searchengine.util.*;
import org.junit.*;

import java.io.*;
import java.util.*;

public class SearchEngineComparisonTest {
    APIServiceImpl apiService = new APIServiceImpl();
    App app = new App();
    HightestUtil hightestUtil = new HightestUtil();

    @Test(expected = IllegalArgumentException.class)
    public void getResponseFromGooleAPI_InvalidInput_ExceptionThrown() {
        apiService.getResponseFromGoogleAPI("");
    }

    @Test
    public void when_ThereIsResult_Expect_NotNullResponse() {
        Assert.assertNotNull(apiService.getResponseFromGoogleAPI("Java"));

    }

    @Test(expected = IllegalArgumentException.class)
    public void getResponseFromBingAPI_InvalidInput_ExceptionThrown() {
        apiService.getResponseFromBingAPI("");
    }


    @Test
    public void getResponseFromBingAPI_ThereIsAResult_True() {
        Assert.assertNotNull(apiService.getResponseFromBingAPI("Java"));
    }


    @Test(expected = IllegalArgumentException.class)
    public void callAPI_InvalidInput_ExceptionThrown() {
        app.callAPI(new String[]{});
    }

    @Test
    public void callAPI_ThereIsAResult_True() {
        Assert.assertNotNull(app.callAPI(new String[]{"Java", "Java Script"}));
    }

    @Test(expected = IllegalArgumentException.class)
    public void printResults_responseWithoutData_ThrowExeption() {
        app.printResults(null);
    }

    @Test
    public void printResults_responseWithData_Success() {
        List<GenericResult> list = app.callAPI(new String[]{"Java", "Java Script"});
        app.printResults(list);
        Assert.assertNotNull(app);
    }

    @Test(expected = IllegalArgumentException.class)
    public void main_responseWithoutData_ThrowExeption() {
        app.main(new String[]{});
    }

    @Test(expected = IllegalArgumentException.class)
    public void getHighestResult_responseWithoutData_ThrowExeption() {
        hightestUtil.getHighestResult(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getTotalHighestWithThreeParameters_responseWithoutData_ThrowExeption() {
        hightestUtil.getTotalHighestWithThreeParameters(null, null, null);
    }

    @Test
    public void getTotalHighestWithThreeParameters_responseWithData_Success() {
        Assert.assertNotNull(hightestUtil.getTotalHighestWithThreeParameters(1L, 2L, 3L));
    }
}
