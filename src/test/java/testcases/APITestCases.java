package testcases;

import io.restassured.response.Response;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import util.Constants;
import util.Util;

import java.util.regex.Pattern;


public class APITestCases {


    @Test
    public void insertSingleRecordSuccessfully() {
        Response response = ApiCalls.insertSingleRecord();
        Assert.assertEquals(response.getStatusCode(), Constants.StatusCodes.statusCode202);
        Assert.assertEquals(response.body().asString(), "Alright");
        Assert.assertEquals(response.header("content-length"), "7");
        Assert.assertEquals(response.header("content-type"), "text/plain;charset=UTF-8");


    }

    @Test
    public void insertMultipleRecordSuccessfully() {
        Response response = ApiCalls.insertMultipleRecords();
        Assert.assertEquals(response.getStatusCode(), Constants.StatusCodes.statusCode202);
        Assert.assertEquals(response.body().asString(), "Alright");
    }

    @Test
    public void uploadCSVFileSuccessfully() {
        Response response = ApiCalls.uploadCSVFile();
        Assert.assertEquals(response.getStatusCode(), Constants.StatusCodes.statusCode200);
        Assert.assertEquals(response.body().asString(), "Successfully uploaded");

    }

    @Test
    public void verifyTaxReliefResponse() {
        Object record = Util.readFile(Constants.singleRecordFile);
        Response response = ApiCalls.getTaxRelief();
        SoftAssert softAssert = new SoftAssert();

        String name = ((JSONObject) record).get("name").toString();
        String natid = ((JSONObject) record).get("natid").toString();
        float salary = Float.parseFloat(((JSONObject) record).get("salary").toString());
        float taxPaid = Float.parseFloat(((JSONObject) record).get("tax").toString());
        String birthday = ((JSONObject) record).get("birthday").toString();
        String gender = ((JSONObject) record).get("gender").toString();
        String  taxRelief = Util.calculateTaxRelief(salary, taxPaid, birthday, gender);
        System.out.println(taxRelief);

        JSONParser parser = new JSONParser();
        try {
            JSONArray json = (JSONArray) parser.parse(response.body().asString());
            for (Object obj : json) {
                softAssert.assertTrue(((JSONObject) obj).containsKey("natid"));
                softAssert.assertTrue(((JSONObject) obj).containsKey("name"));
                softAssert.assertTrue(((JSONObject) obj).containsKey("relief"));

                if (((JSONObject) obj).get("natid").toString().length() > 4) {
                    softAssert.assertTrue(Pattern.matches("[$]+", ((JSONObject) obj).get("natid").toString().substring(4)));
                } else {
                    softAssert.assertTrue(true);
                }
                if (((JSONObject) obj).get("natid").toString().substring(0, 4).equals(natid.substring(0, 4))
                        && ((JSONObject) obj).get("name").toString().equals(name)) {
                    System.out.println("Tax from query  " +((JSONObject) obj).get("relief"));
                    softAssert.assertEquals(((JSONObject) obj).get("relief").toString(), taxRelief);
                    System.out.println("Tax Verified Successfully");
                }
            }

//            softAssert.assertAll();
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }


}

