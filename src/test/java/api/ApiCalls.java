import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import util.Constants;
import util.Util;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class ApiCalls {
    private static final String BASE_URL = "http://localhost:8080";
    public static Response insertSingleRecord(){
        RestAssured.baseURI = BASE_URL;
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
        RestAssured.baseURI = BASE_URL;
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
        RestAssured.baseURI = BASE_URL;
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
