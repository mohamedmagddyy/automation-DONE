# Quick Reference Guide - Done KF Automation Framework

## 🚀 Quick Start

### 1. Clone or Download the Project
```bash
cd "E:\software testing\work\automation projects\Done KF"
cd automation-DONE
```

### 2. Verify Installation
```bash
mvn --version
# Should show Maven 3.9.11+ and Java 17+
```

### 3. Build the Project
```bash
mvn clean compile
# Expected: BUILD SUCCESS
```

---

## 📁 Project Structure at a Glance

```
automation-DONE/
├── src/main/java/com/DoneProject/
│   ├── drivers/        → WebDriver factory (Chrome, Edge, Firefox)
│   ├── helpers/        → Reusable action wrappers
│   ├── pages/          → Page Object Model (12 pages)
│   └── utils/          → Configuration, logging, URLs, waits
│
├── src/test/java/tests/DoneProject/
│   ├── login/          → Login tests
│   ├── projects/       → Project CRUD tests
│   ├── roles/          → Role CRUD tests
│   ├── sectors/        → Sector CRUD tests
│   ├── tasks/          → Task CRUD tests
│   ├── users/          → User CRUD tests
│   └── recurringtask/  → Recurring task tests
│
├── src/test/resources/
│   ├── config.properties  → Test configuration
│   └── log4j2.xml         → Logging setup
│
├── pom.xml             → Maven configuration
├── testng.xml          → Test suite configuration
└── .gitignore          → Git ignore rules
```

---

## 🧪 Running Tests

### Run All Tests
```bash
mvn clean test
```

### Run Specific Module
```bash
mvn clean test -Dtest=tests.DoneProject.sectors.*
```

### Run Single Test Class
```bash
mvn clean test -Dtest=tests.DoneProject.login.LoginTest
```

### Run with TestNG Suite
```bash
mvn clean test -DsuiteXmlFile=testng.xml
```

### Run Tests in Parallel
```bash
mvn clean test -DsuiteXmlFile=testng.xml -Dparallel=methods -DthreadCount=4
```

---

## 🔧 Common Commands

### Clean Build
```bash
mvn clean
```

### Compile Only (No Tests)
```bash
mvn compile
```

### Compile with Tests
```bash
mvn test-compile
```

### Full Build (Compile + Package)
```bash
mvn clean install
```

### Skip Tests During Build
```bash
mvn clean install -DskipTests
```

### View Dependencies
```bash
mvn dependency:tree
```

---

## 📝 Key Files

| File | Purpose | Location |
|------|---------|----------|
| pom.xml | Maven configuration | Root |
| testng.xml | Test suite definition | Root |
| config.properties | Test configuration | src/test/resources/ |
| log4j2.xml | Logging configuration | src/test/resources/ |
| .gitignore | Git ignore rules | Root |

---

## 🎯 Test Modules

### Login Module
**Location:** `src/test/java/tests/DoneProject/login/`  
**Purpose:** User authentication tests  
**Classes:** LoginTest.java

### Projects Module
**Location:** `src/test/java/tests/DoneProject/projects/`  
**Purpose:** Project management CRUD  
**Classes:** AddProjectTest, EditProjectTest, DeleteProjectTest

### Sectors Module
**Location:** `src/test/java/tests/DoneProject/sectors/`  
**Purpose:** Sector management tests  
**Classes:** AddSectorTest, EditSectorTest, DeleteSectorTest, SectorPageTest, AddEditDeleteSectorTest

### Roles Module
**Location:** `src/test/java/tests/DoneProject/roles/`  
**Purpose:** Role/Permission management  
**Classes:** AddRoleTest, EditRoleTest, DeleteRoleTest

### Users Module
**Location:** `src/test/java/tests/DoneProject/users/`  
**Purpose:** User management CRUD  
**Classes:** AddUserTest, EditUserTest, DeleteUserTest

### Tasks Module
**Location:** `src/test/java/tests/DoneProject/tasks/`  
**Purpose:** Task management tests  
**Classes:** AddTaskTest, EditTaskTest, DeleteTaskTest, ArchiveTaskTest

### Recurring Task Module
**Location:** `src/test/java/tests/DoneProject/recurringtask/`  
**Purpose:** Recurring task functionality  
**Classes:** AddRecurringTaskTest

---

## 🏗️ Architecture Overview

```
Test Execution Flow:
    ↓
BaseTest (Setup/Teardown)
    ↓
Test Methods (e.g., LoginTest)
    ↓
Page Objects (e.g., LoginPage)
    ↓
ActionBot (Reusable Actions)
    ↓
WebDriver / Selenium
```

### Layer Responsibilities

**Page Objects (pages/)**
- Locator management
- Page-specific methods
- Element interactions

**Test Classes (tests/)**
- Test scenarios
- Assertions
- Test data

**Helpers (helpers/)**
- ActionBot: Wrapper for common actions
- DropdownHelper: Dropdown-specific utilities

**Utils (utils/)**
- ConfigReader: Configuration management
- LogUtils: Logging functionality
- ScreenshotUtils: Screenshot capture
- Urls: URL constants
- WaitUtils: Wait strategies

**Drivers (drivers/)**
- WebDriverFactory: Driver instantiation
- Browser-specific factories

---

## 🔍 Important Classes

### BaseTest
```java
- Manages WebDriver lifecycle
- Setup: Initializes browser, maximizes window, navigates to login
- Teardown: Closes browser, captures screenshots on failure
- Base for all test classes
```

### BasePage
```java
- Common page operations
- Wait utilities
- Element interaction methods
- Toast notification handling
```

### WebDriverFactory
```java
- Creates WebDriver instances
- Supports Chrome, Edge, Firefox
- Singleton pattern
```

### ActionBot
```java
- Wrapper for common Selenium actions
- Click, type, submit operations
- Wait-enabled actions
```

---

## 📊 Test Configuration

### config.properties
```properties
# Environment variables
# Browser settings
# Application URLs
# Test data paths
# Credentials
```

### log4j2.xml
```xml
<!-- Logging configuration -->
<!-- Log levels: DEBUG, INFO, WARN, ERROR -->
<!-- Appenders: Console, File -->
<!-- Output format and location -->
```

---

## 🐛 Debugging Tips

### Enable Debug Logging
Edit `src/test/resources/log4j2.xml`:
```xml
<Logger name="com.DoneProject" level="DEBUG"/>
```

### View Browser Activity
Keep browser visible during test:
```java
// Tests run in headed mode by default
```

### Capture Screenshots
Automatically captured on test failure in `target/screenshots/`

### View Test Output
```bash
mvn clean test -X  # Detailed output
```

---

## ⚙️ Configuration

### Change Browser
Edit `BaseTest.java`:
```java
driver = WebDriverFactory.initDriver("firefox");  // or "edge"
```

### Update Base URL
Edit `utils/Urls.java`:
```java
public static final String LOGIN_URL = "https://your-app-url";
```

### Modify Wait Timeout
Edit `BasePage.java`:
```java
private static final int DEFAULT_TIMEOUT = 10;  // seconds
```

---

## 🚀 Best Practices

### ✅ DO
- Keep tests independent
- Use page objects for locators
- Add meaningful assertions
- Use waits instead of Thread.sleep()
- Keep business logic in pages
- Use descriptive test names

### ❌ DON'T
- Hard-code locators in test files
- Use Thread.sleep() for timing
- Create test dependencies
- Skip base class initialization
- Modify page objects for single tests
- Keep sensitive data in code

---

## 📚 Useful Resources

### Dependencies Used
- **Selenium:** 4.41.0 - Browser automation
- **TestNG:** 7.10.2 - Test framework
- **Log4j2:** 2.25.3 - Logging
- **WebDriverManager:** 5.6.1 - Driver management

### Documentation
- [Selenium Documentation](https://www.selenium.dev/documentation/)
- [TestNG Documentation](https://testng.org/doc/)
- [Log4j2 Configuration](https://logging.apache.org/log4j/2.x/manual/)

---

## 🆘 Troubleshooting

### Build Fails
```bash
mvn clean
mvn install
```

### Tests Don't Run
```bash
# Check testng.xml package names are correct
# Verify Java version: java --version
mvn test-compile
```

### WebDriver Issues
```bash
# Update WebDriverManager
# Check browser is installed
# Verify browser path in properties
```

### Import Errors
```bash
# Run Maven update
# Check package names (all lowercase)
# Verify IDE has Maven project configured
```

---

## 📞 Support

For issues or questions:
1. Check the REFACTORING_REPORT.md
2. Review FINAL_SUMMARY.md
3. Check test logs in target/
4. Enable debug logging in log4j2.xml

---

## 📅 Project Info

- **Framework:** Selenium 4 + TestNG
- **Language:** Java 17
- **Build Tool:** Maven 3.9.11+
- **Browser Support:** Chrome, Firefox, Edge
- **Status:** ✅ Production Ready

---

**Version:** 1.0  
**Last Updated:** 2026-04-01  
**Maintainer:** Automation Team  

Happy Testing! 🎯

