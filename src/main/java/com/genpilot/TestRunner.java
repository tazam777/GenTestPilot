package com.genpilot;

import com.genpilot.model.Prompt;
import com.genpilot.tests.LoginPositiveTest;
import com.genpilot.tests.NegativeLoginTest;

public class TestRunner {

    public static void run(Prompt prompt) {
        switch (prompt.getType().toUpperCase()) {
            case "POSITIVE":
                LoginPositiveTest.run(prompt);
                break;
            case "NEGATIVE":
                NegativeLoginTest.run(prompt);
                break;
            default:
                System.err.println("❌ Unknown test TYPE: " + prompt.getType());
        }
    }
}
