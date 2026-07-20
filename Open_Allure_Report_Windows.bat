@echo off
REM Generates and opens the Allure HTML report from the last test run's
REM raw results (target/allure-results), produced automatically by the
REM allure-testng dependency during `mvn test`.
mvn allure:serve
