#!/bin/bash
# Generates and opens the Allure HTML report from the last test run's
# raw results (target/allure-results), produced automatically by the
# allure-testng dependency during `mvn test`.
mvn allure:serve
