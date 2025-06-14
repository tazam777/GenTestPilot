package com.genpilot.pages;
import static com.genpilot.utils.Utils.sleep;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class SecureArea {

    private WebDriver driver;
    private WebDriverWait wait;

    private By logoutButton = By.linkText("Logout");
    private By successMessage = By.id("flash");

    // Constructor initializes the driver and WebDriverWait
    public SecureArea(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30)); // 10 sec timeout
    }

    // Step: Click Logout Button
    public void clickLogoutbutton() {
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(logoutButton));
        sleep(5);
        button.click();
        sleep(5);
    }

    // Step: Get Success Message
    public String getSuccessMessage() {
        sleep(5);
        WebElement message = wait.until(ExpectedConditions.visibilityOfElementLocated(successMessage));
        sleep(5);
        return message.getText();
    }
}
