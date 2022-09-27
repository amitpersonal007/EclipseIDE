package com.testsigma.addons.android;



import com.testsigma.sdk.AndroidAction;
import com.testsigma.sdk.ApplicationType;
import com.testsigma.sdk.annotation.Action;
import com.testsigma.sdk.annotation.TestData;
import com.testsigma.sdk.annotation.Element;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.android.nativekey.KeyEventMetaModifier;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

@Action(actionText = "Enter testdata inside an element using numpad", applicationType = ApplicationType.ANDROID)
public class NumPadEvent extends AndroidAction {

  @TestData(reference = "testdata")
  private com.testsigma.sdk.TestData testData;
  @Element(reference = "element")
  private com.testsigma.sdk.Element element;

  @Override
  protected com.testsigma.sdk.Result execute() throws NoSuchElementException {
    //Your Awesome code starts here
    com.testsigma.sdk.Result result = com.testsigma.sdk.Result.SUCCESS;
    logger.info("Initiating execution");
    logger.debug("Element locator: "+ this.element.getValue() +" by:"+ this.element.getBy() + ", test-data: "+ this.testData.getValue());
    AndroidDriver d = (AndroidDriver)this.driver;
    WebElement webElement = element.getElement();
    
   if(webElement.isDisplayed() || webElement.isEnabled()) {
	   
	   logger.info("Element located going for next steps");
	   
   }else {
	   result=com.testsigma.sdk.Result.FAILED;
	   logger.debug("NO SUCH ELEMENT FOUND");
   }
   
   try{
	   
 	  char[] arr = testData.getValue().toString().toCharArray();
 	  
    
     for(char c:arr){
    	 
    	 if(String.valueOf(c).equals("1")) {
    		 d.pressKey(new KeyEvent(AndroidKey.NUMPAD_1)); 
    	 }
    	 else if(String.valueOf(c).equals("0")) {
    		 d.pressKey(new KeyEvent(AndroidKey.NUMPAD_0)); 
    	 }
    	 else if(String.valueOf(c).equals("2")) {
    		 d.pressKey(new KeyEvent(AndroidKey.NUMPAD_2)); 
    	 }
    	 else if(String.valueOf(c).equals("3")) {
    		 d.pressKey(new KeyEvent(AndroidKey.NUMPAD_3)); 
    	 }
    	 else if(String.valueOf(c).equals("4")) {
    		 d.pressKey(new KeyEvent(AndroidKey.NUMPAD_4)); 
    	 }
    	 else if(String.valueOf(c).equals("5")) {
    		 d.pressKey(new KeyEvent(AndroidKey.NUMPAD_5)); 
    	 } else if(String.valueOf(c).equals("6")) {
    		 d.pressKey(new KeyEvent(AndroidKey.NUMPAD_6)); 
    	 }
    	 else if(String.valueOf(c).equals("7")) {
    		 d.pressKey(new KeyEvent(AndroidKey.NUMPAD_7)); 
    	 }
    	 else if(String.valueOf(c).equals("8")) {
    		 d.pressKey(new KeyEvent(AndroidKey.NUMPAD_8)); 
    	 }
    	 else if(String.valueOf(c).equals("9")) {
    		 d.pressKey(new KeyEvent(AndroidKey.NUMPAD_9)); 
    	 }
    	 else if(String.valueOf(c).equals("+")) {
    		 d.pressKey(new KeyEvent(AndroidKey.NUMPAD_ADD)); 
    	 }
     	
      
        else {
        	
        	 logger.debug("Operation failed with data provided");
        }
   }
     
   }catch(Exception e) {
	   
	   e.printStackTrace();
	   logger.debug(e.getMessage()+e.getCause());
	   result=com.testsigma.sdk.Result.FAILED;
	   setErrorMessage("Operation failed. Please check the log for more details");
	     
   }
   return result;
  }
  
}
   