package com.genpilot.tests;

import com.genpilot.model.Prompt;
import com.genpilot.pages.LoginPage;
import com.genpilot.pages.SecureArea;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait; 

import java.time.Duration;

public class LoginPositiveTest {

    public static void run(Prompt prompt) {
        // Step 1: Launch browser
        WebDriver driver = new ChromeDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30)); 

        try {
            // Step 2: Go to the login URL
            driver.get(prompt.getUrl());

            // Step 3: Login using Page Object
            LoginPage loginPage = new LoginPage(driver);
            loginPage.enterLogin(prompt.getUsername());
            loginPage.enterPassword(prompt.getPassword());
            loginPage.clickLoginButton();

            // Step 4: On SecureArea page, assert success and logout
            SecureArea secureArea = new SecureArea(driver);
            String message = secureArea.getSuccessMessage();

            // Optional: Basic assertion
            if (message.contains("You logged into a secure area!")) {
                System.out.println("✅ Login test passed!");
            } else {
                System.out.println("❌ Login failed unexpectedly.");
            }

            // Logout
            secureArea.clickLogoutbutton();

        } catch (Exception e) {
            System.out.println("❌ Test failed with error: " + e.getMessage());
        } finally {
            driver.quit(); // close browser
        }
    }
}
