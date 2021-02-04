package util;

public class Constants {
    final public static String singleRecordFile = "src/test/resources/singleRecord.json";
    final public static String multipleRecordFile = "src/test/resources/multipleRecords.json";
    final public static String csvFile = "src/test/resources/test.csv";
    final public static String BASE_URL = "http://localhost:8080";


    final public static class EndPoints {
        final public static String insertSingleRecord = "/calculator/insert";
        final public static String insertMultipleRecord = "/calculator/insertMultiple";
        final public static String uploadLargeFileForInsertionToDatabase = "/calculator/uploadLargeFileForInsertionToDatabase";
        final public static String getTaxRelief = "/calculator/taxRelief";

    }

    final public static class StatusCodes {
        final public static int statusCode200 = 200;
        final public static int statusCode202 = 202;
        final public static int statusCode500 = 500;
    }

}
