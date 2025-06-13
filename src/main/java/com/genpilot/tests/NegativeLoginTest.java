package com.genpilot.tests;

import com.genpilot.model.Prompt;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class NegativeLoginTest {

    public static void run(Prompt prompt) {
        System.out.println("🚨 Running NEGATIVE login test...");

        WebDriver driver = new ChromeDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30)); // 10 second timeout

        try {
            // Step 1: Go to the login page
            driver.get(prompt.getUrl());

            // Step 2: Wait for login form fields
            WebElement usernameField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("username")));
            WebElement passwordField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("password")));
            WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[type='submit']")));

            // Step 3: Fill in invalid credentials and submit
            usernameField.sendKeys(prompt.getUsername());
            passwordField.sendKeys(prompt.getPassword());
            loginButton.click();

            // Step 4: Wait for error message and validate
            WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("flash")));
            String message = errorMessage.getText();

            if (message.contains("Your username is invalid") || message.contains("Your password is invalid")) {
                System.out.println("✅ NEGATIVE test passed: Proper error message shown");
            } else {
                System.err.println("❌ NEGATIVE test failed: Unexpected message -> " + message);
            }

        } catch (Exception e) {
            System.err.println("⚠️ Error during NEGATIVE test: " + e.getMessage());
        } finally {
            driver.quit();
        }
    }
}
