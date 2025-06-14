package com.genpilot.pages;
import static com.genpilot.utils.Utils.sleep;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class LoginPage {

    private WebDriver driver;
    private WebDriverWait wait;

    // Elements by ID
    private By idField = By.id("username");
    private By passwordField = By.id("password");

    // Elements by selector
    private By loginButton = By.cssSelector("button[type='submit']");
    private By successMessage = By.cssSelector(".flash.success");
    private By errorMessage = By.cssSelector(".flash.error");

    // Constructor: initializes driver and wait
    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30)); // 10 seconds wait
    }

    // Methods aka Steps in QA language

    public void enterLogin(String USERNAME) {
        WebElement usernameInput = wait.until(ExpectedConditions.visibilityOfElementLocated(idField));
        sleep(5);
        usernameInput.clear();
        sleep(5);
        usernameInput.sendKeys(USERNAME);
        sleep(5);
    }

    public void enterPassword(String PASSWORD) {
        WebElement passwordInput = wait.until(ExpectedConditions.visibilityOfElementLocated(passwordField));
        passwordInput.clear();
        sleep(5);
        passwordInput.sendKeys(PASSWORD);
        sleep(5);
    }

    public void clickLoginButton() {
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(loginButton));
        sleep(5);
        button.click();
        sleep(5);
    }

    // Optional: methods to verify outcomes
    public boolean isLoginSuccess() {
        sleep(5);
        return wait.until(ExpectedConditions.visibilityOfElementLocated(successMessage)).isDisplayed();
        
    }

    public boolean isLoginError() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(errorMessage)).isDisplayed();
    }
}
