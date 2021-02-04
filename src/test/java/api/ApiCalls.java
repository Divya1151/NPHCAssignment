package api;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import util.Constants;
import util.Util;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class ApiCalls {

    public static Response insertSingleRecord(){
        RestAssured.baseURI = Constants.BASE_URL;
        RequestSpecification request = RestAssured.given();
        Map<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json");
        header.put("Accept", "*/*");
        request.headers(header);
        Object record = Util.readFile(Constants.singleRecordFile);
        request.body(record.toString());
        return request.post(Constants.EndPoints.insertSingleRecord);
    }

    public static Response insertMultipleRecords(){
        RestAssured.baseURI = Constants.BASE_URL;
        RequestSpecification request = RestAssured.given();
        Map<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json");
        header.put("Accept", "*/*");
        request.headers(header);
        Object record = Util.readFile(Constants.multipleRecordFile);
        request.body(record.toString());
        return request.post(Constants.EndPoints.insertMultipleRecord);
    }

    public static Response uploadCSVFile(){
        RestAssured.baseURI = Constants.BASE_URL;
        RequestSpecification request = RestAssured.given();
        Map<String, String> header = new HashMap<>();
        header.put("Content-Type", "multipart/form-data");
        header.put("Accept", "*/*");
        request.headers(header);
        try {
            request.multiPart("file", new File(Constants.csvFile));
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return request.post(Constants.EndPoints.uploadLargeFileForInsertionToDatabase);
    }

    public static Response getTaxRelief(){
        RequestSpecification request = RestAssured.given();
        request.header("Accept", "*/*");
        return request.get(Constants.EndPoints.getTaxRelief);
    }

}

//    @Test
//    public void insertSingleRecordSuccessfully() {
//        Response response = ApiCalls.insertSingleRecord();
//        Assert.assertEquals(response.getStatusCode(), Constants.StatusCodes.statusCode202);
//        Assert.assertEquals(response.body().asString(), "Alright");
//        Assert.assertEquals(response.header("content-length"), "7");
//        Assert.assertEquals(response.header("content-type"), "text/plain;charset=UTF-8");

//
//    }

//    @Test(threadPoolSize = 5, invocationCount = 20)
//    public void insertMultipleRecordSuccessfully() {
//        Response response = ApiCalls.insertMultipleRecords();
//        Assert.assertEquals(response.getStatusCode(), Constants.StatusCodes.statusCode202);
//        Assert.assertEquals(response.body().asString(), "Alright");
//    }

//    @Test
//    public void uploadCSVFileSuccessfully() {
//        Response response = ApiCalls.uploadCSVFile();
//        Assert.assertEquals(response.getStatusCode(), Constants.StatusCodes.statusCode200);
//        Assert.assertEquals(response.body().asString(), "Successfully uploaded");
//
//    }


//    @Test(dependsOnMethods = {"insertSingleRecordSuccessfully"})
//    public void verifyTaxReliefResponseAndAmount() {
//        Object record = Util.readFile(Constants.singleRecordFile);
//        Response response = ApiCalls.getTaxRelief();
//        SoftAssert softAssert = new SoftAssert();
//
//        String name = ((JSONObject) record).get("name").toString();
//        String natid = ((JSONObject) record).get("natid").toString();
//        float salary = Float.parseFloat(((JSONObject) record).get("salary").toString());
//        float taxPaid = Float.parseFloat(((JSONObject) record).get("tax").toString());
//        String birthday = ((JSONObject) record).get("birthday").toString();
//        String gender = ((JSONObject) record).get("gender").toString();
//        String taxRelief = Util.calculateTaxRelief(salary, taxPaid, birthday, gender);
//        System.out.println("Calculated Tax Relief" + taxRelief);
//
//        JSONParser parser = new JSONParser();
//        try {
//            JSONArray json = (JSONArray) parser.parse(response.body().asString());
//            for (Object obj : json) {
//                softAssert.assertTrue(((JSONObject) obj).containsKey("natid"), "Natutal Id key not present in Tax Relief Response");
//                softAssert.assertTrue(((JSONObject) obj).containsKey("name"), "Name key not present in Tax Relief Response");
//                softAssert.assertTrue(((JSONObject) obj).containsKey("relief"), "Relief key not present in Tax Relief Response");
//
//                if (((JSONObject) obj).get("natid").toString().length() > 4) {
//                    softAssert.assertTrue(Pattern.matches("[$]+", ((JSONObject) obj).get("natid").toString().substring(4)));
//                } else softAssert.assertEquals(((JSONObject) obj).get("natid").toString(), natid);
//
//                if (((JSONObject) obj).get("natid").toString().substring(0, 4).equals(natid.substring(0, 4))
//                        && ((JSONObject) obj).get("name").toString().equals(name)) {
//                    System.out.println("Tax from the system  " + ((JSONObject) obj).get("relief"));
//                    softAssert.assertEquals(((JSONObject) obj).get("relief").toString(), taxRelief, "Tax Relief amount not matched");
//                }
//            }
//            softAssert.assertAll();
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//    }

