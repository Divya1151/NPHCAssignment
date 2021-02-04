package testcases;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import data.Employee;
import io.restassured.filter.log.LogDetail;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import util.Constants;
import util.Util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;


public class APITestCases {


    @Test
    public void insertSingleRecordSuccessfully() {
        Employee employeeRecord = new Employee();
        Gson gson = new Gson();
        try {
            employeeRecord = gson.fromJson(new FileReader(Constants.singleRecordFile), Employee.class);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        given()
                .baseUri(Constants.BASE_URL)
                .header("Content-Type", "application/json")
                .accept("*/*")
                .body(gson.toJson(employeeRecord))
                .when()
                .post(Constants.EndPoints.insertSingleRecord)
                .then()
                .log().ifValidationFails()
                .assertThat().statusCode(Constants.StatusCodes.statusCode202)
                .and()
                .body(equalTo("Alright"))
                .header("content-length", equalTo("7"));


    }

    @Test
    public void verifyInsertSingleRecordWithMissingParam() {
        Employee employeeRecord = new Employee();
        Gson gson = new Gson();
        try {
            employeeRecord = gson.fromJson(new FileReader(Constants.singleRecordFile), Employee.class);
            employeeRecord.setTax(null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        given()
                .baseUri(Constants.BASE_URL)
                .header("Content-Type", "application/json")
                .accept("*/*")
                .body(gson.toJson(employeeRecord))
                .when()
                .post(Constants.EndPoints.insertSingleRecord)
                .then()
                .log().ifValidationFails()
                .assertThat().statusCode(Constants.StatusCodes.statusCode500)
                .and()
                .body("error", equalTo("Internal Server Error"))
                .body("message", equalTo("Cannot invoke \"String.toCharArray()\" because \"val\" is null"));

    }

    @Test
    public void verifyInsertSingleRecordWithInvalidGenderFormat() {
        Employee employeeRecord = new Employee();
        Gson gson = new Gson();
        try {
            employeeRecord = gson.fromJson(new FileReader(Constants.singleRecordFile), Employee.class);
            employeeRecord.setGender("Female");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        given()
                .baseUri(Constants.BASE_URL)
                .header("Content-Type", "application/json")
                .accept("*/*")
                .body(gson.toJson(employeeRecord))
                .when()
                .post(Constants.EndPoints.insertSingleRecord)
                .then()
                .log().ifValidationFails()
                .assertThat().statusCode(Constants.StatusCodes.statusCode500)
                .and()
                .body("error", equalTo("Internal Server Error"))
                .body("message", equalTo(
                        "could not execute statement; SQL [n/a]; nested exception is org.hibernate.exception.DataException: could not execute statement"));

    }

    @Test
    public void verifyInsertSingleRecordWithInvalidBirthdayFormat() {
        Employee employeeRecord = new Employee();
        Gson gson = new Gson();
        try {
            employeeRecord = gson.fromJson(new FileReader(Constants.singleRecordFile), Employee.class);
            employeeRecord.setBirthday("3131990");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        given()
                .baseUri(Constants.BASE_URL)
                .header("Content-Type", "application/json")
                .accept("*/*")
                .body(gson.toJson(employeeRecord))
                .when()
                .post(Constants.EndPoints.insertSingleRecord)
                .then()
                .log().ifValidationFails()
                .assertThat().statusCode(Constants.StatusCodes.statusCode500)
                .and()
                .body("error", equalTo("Internal Server Error"))
                .body("message", equalTo(
                        "Text '3131990' could not be parsed at index 4"));

    }

    @Test
    public void verifyInsertSingleRecordWithInvalidSalary() {
        Employee employeeRecord = new Employee();
        Gson gson = new Gson();
        try {
            employeeRecord = gson.fromJson(new FileReader(Constants.singleRecordFile), Employee.class);
            employeeRecord.setSalary("$12300");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        given()
                .baseUri(Constants.BASE_URL)
                .header("Content-Type", "application/json")
                .accept("*/*")
                .body(gson.toJson(employeeRecord))
                .when()
                .post(Constants.EndPoints.insertSingleRecord)
                .then()
                .log().ifValidationFails()
                .assertThat().statusCode(Constants.StatusCodes.statusCode500)
                .and()
                .body("error", equalTo("Internal Server Error"))
                .body("message", equalTo(
                        "Character D is neither a decimal digit number, decimal point, nor \"e\" notation exponential mark."));

    }


    @Test(threadPoolSize = 5, invocationCount = 20)
    public void insertMultipleRecordSuccessfully() {
        List<Employee> employeeRecords = new ArrayList<>();
        ;
        Gson gson = new Gson();
        try {
            employeeRecords = gson.fromJson(new FileReader(Constants.multipleRecordFile), new TypeToken<List<Employee>>() {
            }.getType());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        given()
                .baseUri(Constants.BASE_URL)
                .header("Content-Type", "application/json")
                .accept("*/*")
                .body(gson.toJson(employeeRecords))
                .when()
                .post(Constants.EndPoints.insertMultipleRecord)
                .then()
                .assertThat().statusCode(Constants.StatusCodes.statusCode202)
                .and()
                .contentType("text/plain;charset=UTF-8")
                .and()
                .header("content-length", equalTo("7"));

    }

    @Test(threadPoolSize = 5, invocationCount = 20) // Load Testing
    public void insertSingleRecordUsingMultipleRecordAPISuccessfully() {
        Employee employeeRecord = new Employee();
        ;
        Gson gson = new Gson();
        try {
            employeeRecord = gson.fromJson(new FileReader(Constants.singleRecordFile), Employee.class);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        given()
                .baseUri(Constants.BASE_URL)
                .header("Content-Type", "application/json")
                .accept("*/*")
                .body(gson.toJson(employeeRecord))
                .when()
                .post(Constants.EndPoints.insertMultipleRecord)
                .then()
                .assertThat().statusCode(Constants.StatusCodes.statusCode202)
                .and()
                .contentType("text/plain;charset=UTF-8")
                .and()
                .body(equalTo("Alright"))
                .header("content-length", equalTo("7"));

    }

    @Test
    public void uploadCSVFileSuccessfully() {
        File file = new File(Constants.csvFile);
        given()
                .baseUri(Constants.BASE_URL)
                .accept("*/*")
                .multiPart(file)
                .when()
                .post(Constants.EndPoints.uploadLargeFileForInsertionToDatabase)
                .then()
                .log().ifValidationFails(LogDetail.BODY)
                .statusCode(Constants.StatusCodes.statusCode200)
                .and()
                .body(equalTo("Successfully uploaded"));

    }

    @Test
    public void verifyInvalidFileUpload() {
        File file = new File(Constants.singleRecordFile);
        given()
                .baseUri(Constants.BASE_URL)
                .accept("*/*")
                .multiPart(file)
                .when()
                .post(Constants.EndPoints.uploadLargeFileForInsertionToDatabase)
                .then()
                .log().ifValidationFails(LogDetail.BODY)
                .statusCode(Constants.StatusCodes.statusCode500)
                .and()
                .body("error", equalTo("Internal Server Error"));

    }

    @Test(dependsOnMethods = {"insertSingleRecordSuccessfully"})
    public void verifyTaxReliefResponseAndAmount() {
        Employee employeeRecord = new Employee();
        Gson gson = new Gson();
        try {
            employeeRecord = gson.fromJson(new FileReader(Constants.singleRecordFile), Employee.class);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        SoftAssert softAssert = new SoftAssert();

        String name = employeeRecord.getName();
        String natid = employeeRecord.getNatid();
        float salary = Float.parseFloat(employeeRecord.getSalary());
        float taxPaid = Float.parseFloat(employeeRecord.getTax());
        String birthday = employeeRecord.getBirthday();
        String gender = employeeRecord.getGender();
        String expectedTaxRelief = Util.calculateTaxRelief(salary, taxPaid, birthday, gender);

        final Response response = given()
                .baseUri(Constants.BASE_URL)
                .contentType("text/plain;charset=UTF-8")
                .accept("*/*")
                .when()
                .get(Constants.EndPoints.getTaxRelief);

        List<Employee> responseRecords = gson.fromJson(response.body().asString(), new TypeToken<List<Employee>>() {
        }.getType());

        response.body().prettyPrint();
        JsonParser jsonParser = new JsonParser();

        JsonArray jsonArray = (JsonArray) jsonParser.parse(response.body().asString());
        for (Object obj : jsonArray) {
            softAssert.assertTrue(((JsonObject) obj).has("natid"), "Natural Id key not present in Tax Relief Response");
            softAssert.assertTrue(((JsonObject) obj).has("name"), "Name key not present in Tax Relief Response");
            softAssert.assertTrue(((JsonObject) obj).has("relief"), "Relief key not present in Tax Relief Response");
        }
        for (Employee employee : responseRecords) {
            if (employee.getNatid().length() > 4)
                softAssert.assertTrue(Pattern.matches("[$]+", employee.getNatid().substring(4)));
            else softAssert.assertEquals(employee.getNatid(), natid);

            if (employee.getNatid().substring(0, 4).equals(natid.substring(0, 4)) && employee.getName().equals(name))
                softAssert.assertEquals(employee.getRelief(), expectedTaxRelief, "Tax Relief amount not matched");

        }
        softAssert.assertAll();

    }
}



