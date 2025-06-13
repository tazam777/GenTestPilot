# GenTestPilot-Lite

A minimal Java-based tool that uses Generative AI (OpenAI GPT) and Selenium to convert natural language test prompts into executable browser tests.

---

## How It Works

1. User writes a test prompt in plain English (e.g., "Test login with correct username and password").
2. The system parses it into structured input (URL, username, password, type: POSITIVE/NEGATIVE).
3. Based on the prompt type, a Java-based Selenium test is triggered.
4. Results are printed to the console and optionally stored (screenshot, logs, etc.).

---

## Features

- Converts plain English prompts into structured login test cases.
- Runs automated browser tests using Selenium WebDriver (Chrome).
- Supports both **positive** and **negative** login scenarios.
- Simple command-line interface.
- Self-contained, lightweight, and extendable.

---

## Project Structure

```
GenTestPilot/
├── src/main/java/com/genpilot/
│   ├── App.java                    # Main entry point
│   ├── Prompt.java                 # Model for structured prompts
│   ├── PromptGenerator.java        # OpenAI GPT integration
│   ├── LoginPositiveTest.java      # Handles valid login tests
│   ├── NegativeLoginTest.java      # Handles invalid login tests
│   ├── pages/                      # Page Object Model structure
│   │   ├── LoginPage.java
│   │   └── SecureArea.java
├── prompts/
│   └── prompt.txt                  # User enters natural language here
├── output/
│   └── (screenshots, logs)
├── pom.xml                         # Maven config
├── README.md
```

---

## Getting Started

### Prerequisites

- Java 17+
- Maven
- Chrome installed
- OpenAI API key

### Steps

1. Clone the repository
2. Add your OpenAI API key in PromptGenerator.java
3. Write a test prompt in prompts/prompt.txt
4. Run the application:

```bash
mvn compile exec:java -Dexec.mainClass="com.genpilot.App"
```

---

## Prompt Format Examples

### Positive Test Prompt

```
Test with correct credentials.
URL: https://the-internet.herokuapp.com/login
USERNAME: tomsmith
PASSWORD: SuperSecretPassword!
```

### Negative Test Prompt

```
Test with incorrect credentials.
URL: https://the-internet.herokuapp.com/login
USERNAME: wronguser
PASSWORD: wrongpass
```

---

## Architecture Flow

```
Natural Language Prompt
         |
         v
PromptGenerator (GPT call → structured format)
         |
         v
Dispatcher (type-based routing)
         |
         v
LoginPositiveTest / NegativeLoginTest
         |
         v
Selenium Test Execution → Result
```

---

## Use Cases

- Fast prototype testing of login forms
- QA engineers validating both happy and sad paths
- Demonstrating AI-assisted test generation in interviews or demos

---

## License

MIT License – free to use and modify

---

## Author

**Tarique Anwar Mulla**

Built as a hybrid between practical QA testing and AI-enhanced productivity to