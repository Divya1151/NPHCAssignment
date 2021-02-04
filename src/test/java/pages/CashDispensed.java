package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class Dispensed {
    WebDriver driver;

    @FindBy(css = "div[class='display-4 font-weight-bold']")
    WebElement cashDispensed;

}
