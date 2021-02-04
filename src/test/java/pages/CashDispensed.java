package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CashDispensed {
    WebDriver driver;

    @FindBy(css = "div[class='display-4 font-weight-bold']")
    WebElement cashDispensed;

    @FindBy(linkText = "Dispense Now")
    WebElement dispenseButton;

    public CashDispensed(WebDriver driver)
    {
        this.driver = driver;
        PageFactory.initElements(driver,this);
    }

    public String getDispenseButtonColor(){
        return Color.fromString(dispenseButton.getCssValue("background-color")).asHex();
    }

    public String getDispenseButtonText(){
        return dispenseButton.getText();
    }

    public void dispenseCash(){
        dispenseButton.click();
    }

    public String cashDispensedMessage(){
        return cashDispensed.getText();
    }

}
