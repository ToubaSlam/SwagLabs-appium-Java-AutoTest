package utils.logging_reporting;

import io.qameta.allure.restassured.AllureRestAssured;

import java.io.*;

import static utils.logging_reporting.LogHelper.logErrorStep;

public class AllureReportHelper {

    public static void deleteOldFiles(File dirPath) {
        File filesList[] = dirPath.listFiles();
        if (filesList != null) {
            for (File file : filesList) {
                if (!file.getName().equals("file.gitkeep")) {
                    if (file.isFile()) {
                        file.delete();
                    } else {
                        deleteOldFiles(file);
                    }
                }
            }
        }
    }

    public static AllureRestAssured logApiRequestsToAllureReport() {
        try {
            return new AllureRestAssured()
                    .setRequestAttachmentName("Request Details")
                    .setResponseAttachmentName("Response Details");
        } catch (Exception e) {
            logErrorStep("Failed to Log API Requests to Allure Report", e);
            return null;
        }
    }

    public static void autoOpenAllureReport() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                Runtime.getRuntime().exec(System.getProperty("user.dir") + "/Open_Allure_Report_Windows.bat");
            } else if (System.getProperty("os.name").contains("Mac")) {
                String script = System.getProperty("user.dir") + "/Open_Allure_Report_Mac.sh";

                ProcessBuilder pb = new ProcessBuilder("bash", script);
                Process process = pb.start();
            }
        } catch (Exception e) {
            logErrorStep("Failed to Open Allure Report", e);
        }
    }
}
