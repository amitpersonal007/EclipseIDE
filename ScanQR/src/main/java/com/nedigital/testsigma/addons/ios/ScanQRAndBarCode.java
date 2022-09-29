package com.nedigital.testsigma.addons.ios;

import com.nedigital.testsigma.addons.util.ImageInjectionUtil;
import com.testsigma.sdk.AndroidAction;
import com.testsigma.sdk.ApplicationType;
import com.testsigma.sdk.IOSAction;
import com.testsigma.sdk.Result;
import com.testsigma.sdk.annotation.Action;
import com.testsigma.sdk.annotation.TestData;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import org.apache.commons.lang3.exception.ExceptionUtils;

@Action(actionText = "Scan QR/Barcode image from path file URL",
        applicationType = ApplicationType.IOS)
public class ScanQRAndBarCode extends IOSAction {
    @TestData(reference = "file URL")
    private com.testsigma.sdk.TestData filePath;

    @Override
    protected com.testsigma.sdk.Result execute()  {
        //Your Awesome code starts here
        com.testsigma.sdk.Result result = com.testsigma.sdk.Result.SUCCESS;
        IOSDriver iosDriver = (IOSDriver) this.driver;
        ImageInjectionUtil util = new ImageInjectionUtil();
        logger.info("Scanning QR/Barcode image, image Path:"+filePath.toString());
        try{
            util.injectImage(iosDriver,filePath.getValue().toString());
        }catch (Exception e){
            logger.info("Unable to scan QR code, please verify if the file path is correct");
            logger.info(ExceptionUtils.getStackTrace(e));
            setErrorMessage("Scanning/injecting image failed");
            return Result.FAILED;
        }
        setSuccessMessage("QR/Bar code scanned successfully");
        return result;
    }

}
