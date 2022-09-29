package com.nedigital.testsigma.addons.util;

import io.appium.java_client.AppiumDriver;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.JavascriptExecutor;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.util.Base64;

import static org.apache.commons.io.IOUtils.toByteArray;

public class ImageInjectionUtil {
    public void injectImage(AppiumDriver driver, String imageURLPath) throws Exception{
        URL imageURL = new URL(imageURLPath);
        File localTempFile = File.createTempFile("scan","image");
        FileUtils.copyURLToFile(imageURL,localTempFile);
        FileInputStream in = new FileInputStream(localTempFile.getAbsolutePath());
        String qrCodeImage = Base64.getEncoder().encodeToString(
                toByteArray(in));
        String s = "sauce:inject-image=" + qrCodeImage;
        ((JavascriptExecutor)driver).executeScript(s);

    }
}
