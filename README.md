# SwagLabs App Mobile Automation Project

Mobile Test Automation Framework developed using Java and Appium to Automate Testing of SwagLabs Application on Android and IOS
<img width="412" height="344" alt="image" src="https://github.com/user-attachments/assets/97e63da0-28d8-4aba-a079-8990e8c7c83f" />


## Project Overview

This Project automates SwagLabs E-commerce Application on Android and IOS with Positive and Negative Test cases for each Feature and Validate E2E Scenarios for Cart Checkout, within an Automation Framework

## Allure Report
### Generate and Open Allure Report easily in one step by running Open_Allure_Report.bat file
<img width="1920" height="1080" alt="Run Details Allure Report" src="https://github.com/user-attachments/assets/f45faa00-67aa-4cd8-8d0e-52ca72cff0bd" />\
<img width="1920" height="884" alt="Test Result Allure Report" src="https://github.com/user-attachments/assets/b6e23a57-76e9-4d9f-946d-ace23bd24aeb" />

### Positive Tests & Negative Tests

## Technologies Used

- Appium with Java
- Maven Project
- TestNG as Testing Framework
- Fluent Page Object Model Design Pattern
- Cross Platform Framework for both Android and IOS
- W3C TouchActions for Simulating Mobile Gestures for Cross Platform Mobiles
- UiAutomator2 Driver for Mobile Gestures Automation on Android Devices
- XCUITest Driver for Mobile Gestures Automation on IOS Devices
- Retry Mechanism for Flaky Tests
- Smart Scroll Search for Mobile Elements
- Fluent Wait Strategy for Identifying Elements
- Appium Factory for creating Driver across Different Platforms and Capabilities
- TestNg Listeners for Monitoring different Events on Suite, Test and Method Level 
- Test Reporting using Allure
- Logging using Log4j2
- Test Data Management using Json
- Test Data Generation using JavaFaker
- Running Tests from TestNG XML Files on a specific Platform (Android/IOS) or across both Platforms


## Features
- Creating Cross Platform Framework for Android and IOS "Locators and Actions and Capaiblities"
- Easy Switching between Android and IOS , only change the platformType and platformVersion before Run
- Using Fluent Page Object Model for chaining all scenario steps and validations for each page in one line of code
- Simulating all Mobile Gestures like Tap / DoubleTap/ TapAndHold /Zoom / DragAndDrop / Swipe, with only one command while Auto Scrolling in a given Direction till the Target Element Dispalyed in ViewPort
- Using W3C Touch Actions for Mobile Gestures Automation similar to Real Finger Movemenets
- Using Automation Driver Commands for Android "UiAutomator2" & IOS "XCUITest" for Mobile Gestures Automation with very simple code and more accurate than finger movements
- Smart Scroll Search: Automatically scans the entire page by scrolling in the specified direction (Vertical or Horizontal). In Vertical mode, it scrolls down to the footer, then reverses to the header if the element is not found, ensuring a complete page scan before performing the required action.
- Using Test Data Management for Storing all Test data in Json Files
- Managing Project Configurations and Appium Capabilities from Properties Files
- Auto-Generation of Allure Report after Every Test Run through .bat file and Logging all Test Steps and Screenshots in the Report
- Logging All Steps in Console using Log4j2
- Waiting for All Elements Identifications and All Actions taken on elements inside Fluent Wait with lambda expression, so waiting till finding element and also taking the action
- Retry Mechanism for Flaky Tests in case of Failed
- Automatic Start Appium Server Before Run and Stop it after Run
- Generate Random Test Data using JavaFaker
- Retry Type action for incorrect typing


## Utilities
- AppiumDriver Factory
- W3C TouchActions
- NativeAndroidActions by UiAutomator2 Driver
- NativeIOSActions by XCUITest Driver
- TestNG Suite / Test / Method Listeners
- Log4j2 LogHelper
- AllureReportHelper
- Screenshot
- RetryAnalyizer 
- RandomDataGenerator
- JsonReader
- PropertiesReader

# 📁 Project Structure

```text
MobileAutomation_Appium_SwagLabsApp
│
├── .github
│   └── workflows
│
├── src
│   ├── main
│   │   ├── java
│   │   │   └── utils
│   │   └── resources
│   │
│   └── test
│       ├── java
│       │   ├── actions
│       │   ├── pages
│       │   ├── listeners
│       │   ├── utilities
│       │   └── testCases
│       │
│       └── resources
│           ├── TestData
│           ├── Properties
│           └── XMLSuites
│
├── pom.xml
└── README.md
```

---

# ▶ Running Tests

### 📱 Run All Tests (Single Platform)

* `RunAllTests.xml`

### 📱📱 Run All Tests (Android & iOS)

* `RunAllTests_Android-IOS.xml`

### ✅ Run Positive Test Cases

* `PositiveTestCases.xml`

### ❌ Run Negative Test Cases

* `NegativeTestCases.xml`

---

# 👩‍💻 Author

**Hadeer Atef Abdalla**

**Mobile Automation Test Engineer**

**GitHub**

🔗 https://github.com/HadeerAtef96

**Project Repository**

🔗 https://github.com/HadeerAtef96/MobileAutomation_Appium_SwagLabsApp

