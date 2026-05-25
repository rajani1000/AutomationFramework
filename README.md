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
- Test screenshots repository
- Excel data-driven testing (Apache POI)

### ✅ Test Data Management
- Excel-based data pooling
- Environment property management (Dev, QA, Prod, Staging)
- Header configuration

---

Run UI Tests:
mvn -DsuiteXmlFile=testng-ui.xml test

Run API Tests:
mvn -DsuiteXmlFile=testng-api.xml test
