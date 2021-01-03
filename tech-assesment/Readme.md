Tools used to develop API Automation Scripts

    - Java-1.8
    - Rest assured API
    - Cucumber API
    - Maven
    - TestNG
    - JSON APIs

- All the dynamic configuration are stored under
    - src/main/resources/config.properties file

Test scenarios are written in BDD format by using cucumber
    - Scenarios can be found in: src/test/resources/features folder
    - API Schemas are stored in: src/test/resources/schemas folder

For executing the project from Eclipse IDE

    - Open eclipse
    - Make sure eclipse Maven Plugin is installed
    - Import the project as existing Maven Project
    - Build and update the project
    - navigate to src/test/resources/suites/ folder
    - right click on testng.xml file --> Run as --> TestNG Suite
    - once execution complete , html report will be generated.
    - navigate to reports/ folder under main project and open execution-report.html file

For executing the project from CLI command prompt

    - make sure maven is installed in the system and MAVEN_HOME environment variable is set
    - Open command prompt and change directory path to the project root folder
        e.g: cd D:\obs-coding-test
    - type command: mvn clean install
    - once Build is Success, open reports folder to check execution-report.html file

The test process
    - Script starts execution and send the valid API request
    - After getting the API Response, script validates 
            - Response schema is matching with expected schema stored under src/test/resources/schemas folder
            - Validates the status code
            - captures the response time in miliseconds
            - Validates the response data by fetching data from the database
            - Logs all the validation results along with response schema and Database resultset details to html report


