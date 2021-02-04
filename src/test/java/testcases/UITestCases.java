package testcases;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.Color;
import org.testng.Assert;
import org.testng.annotations.Test;
import util.Constants;
import util.Util;

import java.util.List;


public class UITestCases {
    WebDriver driver;

    @Test
    public void verifyDispenseSuccessfully() {
        System.setProperty("webdriver.chrome.driver",
                "src/test/resources/chromedriver");
        driver = new ChromeDriver();
        driver.get("http://localhost:8080/");
        driver.manage().window().maximize();
        WebElement dispenseButton;
        dispenseButton = By.linkText("Dispense Now").findElement(driver);
        String color = dispenseButton.getCssValue("background-color");
        String hex = Color.fromString(color).asHex();
        Assert.assertEquals(hex, "#dc3545");
        Assert.assertEquals(dispenseButton.getText(), "Dispense Now");
        dispenseButton.click();
        Assert.assertEquals(driver.getTitle(), "Dispense!!");
        WebElement cashDispense = By.cssSelector("div[class='display-4 font-weight-bold']").findElement(driver);
        Assert.assertEquals(cashDispense.getText(), "Cash dispensed");
        driver.quit();

    }

    @Test
    public void uploadCSVFromUISuccessfully() {
        System.setProperty("webdriver.chrome.driver",
                "src/test/resources/chromedriver");
        driver = new ChromeDriver();
        driver.get("http://localhost:8080/");
        driver.manage().window().maximize();
        WebElement csvUpload = By.cssSelector("input[class='custom-file-input']").findElement(driver);
        long lineCount;
        try {
            long totalLinesCSV = Util.countLines(Constants.csvFile);
            WebElement refreshTaxReliefTable = By.cssSelector("button[class='btn btn-primary']").findElement(driver);
            refreshTaxReliefTable.click();
            List<WebElement> rows = By.tagName("tr").findElements(driver);
            int noOfRowsBefore = rows.size();
            csvUpload.sendKeys("/Users/ankit/Divya/Assignments/NPHCAssignment/src/test/resources/test.csv");
            Thread.sleep(2000);
            refreshTaxReliefTable.click();
            List<WebElement> rowsAfter = By.tagName("tr").findElements(driver);
            Assert.assertEquals(rowsAfter.size(), ((totalLinesCSV - 1) + noOfRowsBefore));
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        driver.quit();
    }


}
