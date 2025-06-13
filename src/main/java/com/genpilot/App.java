package com.genpilot;

import com.genpilot.model.Prompt;

import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println(" Describe the test case");
        String naturalPrompt = scanner.nextLine();

        // Step 1: Call PromptGenerator to generate structured prompt
        String promptFilePath = PromptGenerator.generateFromNaturalLanguage(naturalPrompt);

        if (promptFilePath == null) {
            System.out.println(" Failed to create prompt.");
            return;
        }

        // Step 2: Parse prompt file
        Prompt prompt = PromptReader.read(promptFilePath);
        if (prompt == null) {
            System.out.println("Failed to read the generated prompt.");
            return;
        }

        // Step 3: Run the test
        TestRunner.run(prompt);
    }
}
