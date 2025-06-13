# GenTestPilot-Lite

A minimal Java-based tool that uses Generative AI (OpenAI GPT) and Selenium to convert natural language test prompts into executable browser tests.

Example:  
Prompt: "Go to Amazon, search for 'wireless headphones', and screenshot the first result."  
Output: A generated Java Selenium script runs the test and saves the screenshot.

---

## Features
- Converts plain English test instructions into Java Selenium code
- Executes the test in a browser using ChromeDriver
- Captures screenshots and console logs
- Small, simple, and self-contained

---

## Project Structure
```
GenTestPilotLite/
├── src/main/java/com/genpilotlite/
│   ├── App.java
│   ├── GPTService.java
│   ├── TestRunner.java
│   ├── FileUtils.java
│   └── PromptReader.java
├── prompts/
│   └── prompt.txt
├── output/
│   └── (screenshots and logs)
├── .gitignore
├── pom.xml
├── README.md
```

---

## Getting Started

1. Clone the repository  
2. Add your OpenAI API key to `config.properties`  
3. Add your test prompt in `prompts/prompt.txt`  
4. Run the app:
```bash
mvn compile exec:java -Dexec.mainClass="com.genpilotlite.App"
```

---

## Sample Prompt
```
Go to Google, search 'Selenium WebDriver', click the first link, and screenshot the result.
```

---

## License
MIT License – free to use and modify

---

## Author
Tarique Anwar Mulla  
Inspired by real-world QA and Generative AI integration
