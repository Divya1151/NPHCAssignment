package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class Home {
    WebDriver driver;

    @FindBy(linkText = "Dispense Now")
    WebElement dispenseButton;

    @FindBy(css = "input[class='custom-file-input']")
    WebElement csvUpload;

    @FindBy(css = "button[class='btn btn-primary']")
    WebElement refreshTaxReliefTable;

    @FindBy(xpath = "//table[@class='table table-hover table-dark']/tbody/tr")
    List<WebElement> reliefTableRows;

    public Home(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver,this);
    }

    public void uploadCSV(String csvPath){
        csvUpload.sendKeys(csvPath);
    }

    public void refreshTaxReliefTable(){
        refreshTaxReliefTable.click();
    }
    
    public int getTableRowsCount(){
       return reliefTableRows.size();
    }
}
