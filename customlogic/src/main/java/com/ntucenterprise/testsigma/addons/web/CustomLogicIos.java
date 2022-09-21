package com.ntucenterprise.testsigma.addons.web;

import org.openqa.selenium.NoSuchElementException;

import com.testsigma.sdk.ApplicationType;
import com.testsigma.sdk.Result;
import com.testsigma.sdk.WebAction;
import com.testsigma.sdk.annotation.Action;
import com.testsigma.sdk.annotation.TestData;

import lombok.Data;

	@Data
	@Action(actionText = "Execute Custom Logic where eventName  requestURL user client secret",
	        description = "Executes custom logic based on user input",
	        applicationType = ApplicationType.IOS)
	public class CustomLogicIos extends CustomLogic {

	  @TestData(reference = "eventName")
	  private com.testsigma.sdk.TestData eventName;
	  
	  @TestData(reference = "requestURL")
	  private com.testsigma.sdk.TestData requestURL;
	 
	  @TestData(reference = "user")
	  private com.testsigma.sdk.TestData user;
	 
	  @TestData(reference = "client")
	  private com.testsigma.sdk.TestData client;
	 
	  @TestData(reference = "secret")
	  private com.testsigma.sdk.TestData secret;
	 
	 
	 

	  @Override
	  public Result execute() throws NoSuchElementException {
		  super.setClient(this.client);
		  super.setRequestURL(this.requestURL);
		  super.setSecret(this.secret);
		  super.setEventName(this.eventName);
		  super.setUser(this.user);
		  
		  return super.execute();
		  
		
		  
		  
	  }
		  
	  }
		  
		  
		 
	  


