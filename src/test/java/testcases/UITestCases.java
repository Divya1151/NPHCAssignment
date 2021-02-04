package testcases;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.CashDispensed;
import pages.Home;
import util.Constants;
import util.Util;

@Listeners(value = Reporters.class)
public class UITestCases {
    WebDriver driver;


    // This is to initialise the driver before test method
    @BeforeMethod
    public void setUp() {
        // Setting up system property to set the path of chrome driver
        System.setProperty("webdriver.chrome.driver",
                "src/test/resources/chromedriver");
        driver = new ChromeDriver();
        driver.get(Constants.BASE_URL);
        driver.manage().window().maximize();
    }

    // This test case covers User Story 5- "As the Governor, I should be able to see a button on the screen so
//that I can dispense tax relief for my working class heroes"

    @Test
    public void verifyDispenseSuccessfully() {
        CashDispensed cashDispensed = new CashDispensed(driver);
        SoftAssert softAssert = new SoftAssert();

        // It verifies whether the dispense button is in red color or not
        softAssert.assertEquals(cashDispensed.getDispenseButtonColor(), "#dc3545", "Dispense Button color is not red");

        //It verifies if the button text is Dispense Now
        softAssert.assertEquals(cashDispensed.getDispenseButtonText(), "Dispense Now", "Button Text is not Dispense Now");
        cashDispensed.dispenseCash();

        //It verifies if user is directed to page with title Dispense!! and the text displayed on page is Cash Dispensed
        softAssert.assertEquals(driver.getTitle(), "Dispense!!");
        softAssert.assertEquals(cashDispensed.cashDispensedMessage(), "Cash dispensed",
                "User is not directed to a page where it displays cash dispensed");
        softAssert.assertAll();

    }


//    This test covers User Story- 3 "As the Clerk, I should be able to upload a csv file to a portal so
//that I can populate the database from a UI"

    @Test
    public void uploadCSVFromUISuccessfully() {
        try {
            long totalLinesCSV = Util.countLines(Constants.csvFile);
            Home home = new Home(driver);
            int noOfRowsBefore = home.getTableRowsCount();
            home.uploadCSV(System.getProperty("user.dir") + "/src/test/resources/test.csv");
            Thread.sleep(2000);
            home.refreshTaxReliefTable();
            int noOfRowsAfter = home.getTableRowsCount();

//            Verifies if the total no of records after uploading the csv matches total no of existing records in DB + total no of new records uploaded
            Assert.assertEquals(noOfRowsAfter, ((totalLinesCSV - 1) + noOfRowsBefore), "CSV didn't upload successfully");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //    After Method annotation is used to quit driver after every test case finished execution
    @AfterMethod
    public void tearDown() {
        driver.quit();
    }


}
