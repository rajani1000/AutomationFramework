# Test Automation Framework
Automation project for API,UI 

A **hybrid test automation framework** built with Java, Selenium, TestNG, and RestAssured supporting **UI automation**, **REST API testing**, and **Database validation** testing.

## 📋 Features

### ✅ UI Automation (Selenium)
- Multi-browser support: Chrome, Firefox, Edge, IE, Headless
- Page Object Model (POM) design pattern
- Shadow DOM & Cookie banner handling
- Screenshot capture test pass/Fail
- Keyboard action handling for non-DOM popups
- Dynamic element waits & interactions

### ✅ REST API Testing (RestAssured)
- GET, POST, PUT, DELETE operations
- Request/response validation
- JSON path extraction
- Basic authentication support
- Request templating with JSON files
- Multipart form data support

### ✅ Database Testing (Oracle)
- Oracle JDBC connection support
- Query execution & result validation

### ✅ Reporting & Logging
- Log4j2 detailed logging
- Test screenshots repository (test-screenshots folder)
- Excel data-driven testing (Apache POI)

### ✅ Test Data Management
- Excel-based data pooling
- Environment property management (Dev, QA, Prod, Staging)
- Header configuration

## XPath Variations for UI Automation

Use these XPath styles in page objects based on element stability and readability.

- Exact attribute match  
  `//input[@name='search']`
- Text match  
  `//button[text()='Add to Cart']`
- Partial text match (`contains`)  
  `//a[contains(text(),'Checkout')]`
- Partial attribute match (`contains`)  
  `//a[contains(@href,'product')]`
- Prefix attribute match (`starts-with`)  
  `//div[starts-with(@id,'cart-')]`
- Multiple conditions (`and` / `or`)  
  `//button[@type='button' and contains(@class,'btn-primary')]`
- Parent to child navigation  
  `//div[@id='header-cart']//button`
- Axes (sibling and ancestor traversal)  
  `//*[text()='Total']/parent::td/following-sibling::td`
- Indexed match (use only when no stable attribute exists)  
  `(//button[contains(text(),'Add to Cart')])[1]`
- Dynamic XPath template for reusable methods  
  `//a[contains(text(),'<<VARIABLE>>')]`

### Quick Guidance
- Prefer relative XPath (`//...`) over absolute XPath (`/html/body/...`).
- Prefer stable attributes (`id`, `name`, `data-*`) before using index-based XPath.
- Use `contains` and `starts-with` for dynamic values.
- Keep XPath logic in page objects for maintainability.

---

Run UI Tests:
mvn -DsuiteXmlFile=testng-ui.xml test

Run API Tests:
mvn -DsuiteXmlFile=testng-api.xml test
