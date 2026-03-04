 CaseKaro Test Automation 

Automated end-to-end tests for [casekaro.com](https://casekaro.com) using Java, Playwright and TestNG.

Tech Stack
- Java 17
- Playwright 1.44.0
- TestNG 7.9.0
- Maven

Project Structure
casekaro-automation/
├── src/
│   └── test/
│       └── java/
│           └── tests/
│               └── CaseKaroTest.java
├── pom.xml
├── testng.xml
└── README.md


 Test Scenarios

1. Navigate to casekaro.com 
2.  Click on Mobile Covers 
3. Search for Apple brand
4.  Negative validation - other brands not visible 
5.  Navigate to iPhone 16 Pro collection 
6. Open first product page 
7. Add Hard, Soft and Glass materials to cart 
8. Open cart 
9. Validate all 3 items are in cart 
10. Print item details to console 

How to Run

Prerequisites
- Java 17 or above
- Maven installed

Clone the project
--bash
git clone https://github.com/YOUR_USERNAME/casekaro-automation.git
cd casekaro-automation


Install Playwright browsers
--bash
mvn exec:java -e -D exec.mainClass=com.microsoft.playwright.CLI -D exec.args="install chromium"


Run the tests
--bash
mvn clean test


Sample Console Output

STEP 1 PASSED – Website loaded successfully
STEP 2 PASSED – Mobile Covers page loaded
STEP 3 PASSED – Searched for Apple
STEP 4 PASSED – No other brands found in Apple search results
STEP 5 PASSED – iPhone 16 Pro collection loaded with 48 products
STEP 6 PASSED – Product page opened
STEP 7 PASSED – All 3 materials added to cart
STEP 8 PASSED – Cart page opened
STEP 9 PASSED – All 3 materials found in cart 
STEP 10 PASSED – All item details printed

======================================
        CART ITEMS DETAILS
======================================
Item 1:
  Name     : Classic Porsche 911 iPhone 16 Pro Back Cover
  Material : Glass
  Price    : ₹ 249.00
  Link     : https://casekaro.com/products/...

Item 2:
  Name     : Classic Porsche 911 iPhone 16 Pro Back Cover
  Material : Black Soft
  Price    : ₹ 149.00
  Link     : https://casekaro.com/products/...

Item 3:
  Name     : Classic Porsche 911 iPhone 16 Pro Back Cover
  Material : Hard
  Price    : ₹ 69.00
  Link     : https://casekaro.com/products/...
======================================

Author
Name:Shifa Kouser
Assignment: CaseKaro QA Automation