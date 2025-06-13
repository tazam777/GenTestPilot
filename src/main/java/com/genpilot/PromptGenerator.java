package com.genpilot;

import io.github.cdimascio.dotenv.Dotenv;
import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.*;
import java.util.*;

public class PromptGenerator {
    private static final String API_URL = "https://api.openai.com/v1/chat/completions";
    private static final MediaType JSON = MediaType.get("application/json");

    public static String generateFromNaturalLanguage(String userInput) {
        // Load credentials
        Map<String, String> creds = readCreds("creds.txt");
        if (creds.isEmpty()) {
            System.err.println("❌ creds.txt missing or invalid format.");
            return null;
        }

        Dotenv dotenv = Dotenv.configure()
                .directory(System.getProperty("user.dir"))
                .filename(".env")
                .ignoreIfMissing()
                .load();

        String apiKey = dotenv.get("OPENAI_API_KEY");
        if (apiKey == null || apiKey.isEmpty()) {
            System.err.println("❌ Missing OPENAI_API_KEY in .env");
            return null;
        }

        String username = creds.getOrDefault("USERNAME", "tomsmith");
        String password = creds.getOrDefault("PASSWORD", "SuperSecretPassword!");
        String url = "https://the-internet.herokuapp.com/login";

        // Strict system prompt
        String systemPrompt = """
You are a test case assistant.
You convert login test scenario descriptions into key=value format configuration.
Strict rules:
- Only output key=value pairs. No extra explanation, no JSON, no markdown, no quotes.
- Use exactly this format:
TYPE=...
URL=...
USERNAME=...
PASSWORD=...
- Do not include anything else. Just respond with the 4 key=value lines.
""";

        String exampleOutput = "TYPE=POSITIVE\nURL=" + url + "\nUSERNAME=" + username + "\nPASSWORD=" + password;

        JSONObject json = new JSONObject()
                .put("model", "gpt-3.5-turbo")
                .put("messages", new JSONArray()
                        .put(new JSONObject().put("role", "system").put("content", systemPrompt))
                        .put(new JSONObject().put("role", "user").put("content",
                                userInput + "\nStrictly respond in key=value format like this:\n" + exampleOutput))
                )
                .put("temperature", 0.2);

        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(JSON, json.toString());
        Request request = new Request.Builder()
                .url(API_URL)
                .post(body)
                .addHeader("Authorization", "Bearer " + apiKey)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful() || response.body() == null) {
                System.err.println("⚠️ GPT API call failed: " + response.code());
                return null;
            }

            String responseBody = response.body().string();
            String output = new JSONObject(responseBody)
                    .getJSONArray("choices")
                    .getJSONObject(0)
                    .getJSONObject("message")
                    .getString("content")
                    .trim();

            // ✅ Parse key=value response
            Map<String, String> parsedMap = parseKeyValueOutput(output);

            if (!parsedMap.containsKey("TYPE") || !parsedMap.containsKey("URL") ||
                !parsedMap.containsKey("USERNAME") || !parsedMap.containsKey("PASSWORD")) {
                System.err.println("⚠️ GPT response missing required fields. Full output:\n" + output);
                return null;
            }

            // ✅ Save to file
            String filePath = "prompts/generated_prompt.txt";
            Files.createDirectories(Paths.get("prompts"));
            Files.writeString(Paths.get(filePath), output);
            return filePath;

        } catch (IOException e) {
            System.err.println("⚠️ GPT API error: " + e.getMessage());
            return null;
        } catch (Exception e) {
            System.err.println("❌ Unexpected error parsing GPT output: " + e.getMessage());
            return null;
        }
    }

    private static Map<String, String> readCreds(String filePath) {
        Map<String, String> creds = new HashMap<>();
        try {
            List<String> lines = Files.readAllLines(Paths.get(filePath));
            for (String line : lines) {
                String[] parts = line.trim().split("=", 2);
                if (parts.length == 2) {
                    creds.put(parts[0].trim().toUpperCase(), parts[1].trim());
                }
            }
        } catch (IOException e) {
            System.err.println("⚠️ Couldn't read creds.txt: " + e.getMessage());
        }
        return creds;
    }

    // ✅ Parses GPT response in key=value format
    private static Map<String, String> parseKeyValueOutput(String output) {
        Map<String, String> result = new HashMap<>();
        String[] lines = output.split("\\n");
        for (String line : lines) {
            if (line.contains("=")) {
                String[] parts = line.split("=", 2);
                result.put(parts[0].trim().toUpperCase(), parts[1].trim());
            }
        }
        return result;
    }
}
