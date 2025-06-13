package com.genpilot; // Define the package this class belongs to

// Import necessary classes for file input/output operations
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import com.genpilot.model.Prompt; // Import the Prompt class where data will be stored

public class PromptReader {

    // Static method to read a prompt file and return a filled Prompt object
    public static Prompt read(String filePath) {

        // Create a new Prompt object to store values from the file
        Prompt prompt = new Prompt();

        // Try-with-resources ensures the file is closed automatically
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {

            String line; // Store each line read from the file

            // Read each line until end-of-file
            while ((line = br.readLine()) != null) {

                // Split the line into key and value at the first '='
                String[] parts = line.split("=", 2);

                // Only proceed if the line has both key and value
                if (parts.length == 2) {
                    String key = parts[0].trim().toUpperCase(); // Convert key to uppercase for consistency
                    String value = parts[1].trim(); // Trim any surrounding spaces

                    // Based on key, store the value in the appropriate field in Prompt object
                    switch (key) {
                        case "TYPE":
                            prompt.setType(value);
                            break;
                        case "URL":
                            prompt.setUrl(value);
                            break;
                        case "USERNAME":
                            prompt.setUsername(value);
                            break;
                        case "PASSWORD":
                            prompt.setPassword(value);
                            break;
                        default:
                            System.out.println("Unknown key in prompt file: " + key);
                    }
                }
            }

        } catch (IOException e) {
            // If something goes wrong while reading the file, print the error
            System.out.println("Failed to read prompt: " + e.getMessage());
        }

        // Return the filled Prompt object
        return prompt;
    }
}
