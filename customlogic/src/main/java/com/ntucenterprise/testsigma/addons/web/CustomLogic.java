package com.ntucenterprise.testsigma.addons.web;

import com.testsigma.sdk.WebAction;
import com.testsigma.sdk.ApplicationType;
import com.testsigma.sdk.annotation.Action;
import com.testsigma.sdk.annotation.TestData;


import io.restassured.response.Response;
import lombok.Data;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.openqa.selenium.NoSuchElementException;
import org.testng.asserts.SoftAssert;

import static io.restassured.RestAssured.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.SpecVersion;
import com.networknt.schema.ValidationMessage;
import io.restassured.response.Response;

@Data
@Action(actionText = "Execute Custom Logic where eventName  requestURL user client secret",
        description = "Executes custom logic based on user input",
        applicationType = ApplicationType.WEB)
public class CustomLogic extends WebAction {

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
  public com.testsigma.sdk.Result execute() throws NoSuchElementException {
	  
	  com.testsigma.sdk.Result result = com.testsigma.sdk.Result.SUCCESS;
    //Your Awesome code starts here
	  
	  Map<String, String> paramsFromUser = new HashMap<>();
      paramsFromUser.put("user", user.getValue().toString());
      paramsFromUser.put("direction", "latest");
      paramsFromUser.put("limit", "1");
      String output = null;
      
      
      for (int i = 0; i < 5; i++) {
          System.out.println("checking json has event name: " + eventName + "for iteration no " + i);
         
          Response responseBody = given().auth().basic(client.getValue().toString(), secret.getValue().toString()).relaxedHTTPSValidation()
                  .queryParams(paramsFromUser).log().all()
                  .when().get(requestURL.getValue().toString()).then().log().all().extract().response();
          // check whether event time is expected as per function's parameter (eventName)
         
          Object eventType = JsonPath.read(responseBody.asString(), "$.events[0].event_type");
          if (eventType.equals(eventName)) {
            
              Object event = JsonPath.read(responseBody.asString(), "$.events[0]");
              output = JsonPath.parse(event).jsonString();
              break;
          } else {
              //Thread.sleep(2000);
          }
      }
      logger.info(output);
      System.out.println(output);
      
   
      SoftAssert softAssertion = new SoftAssert();
      ObjectMapper objectMapper = new ObjectMapper();
      JsonSchemaFactory schemaFactory = JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V4);
      JsonNode json;
	try {
		InputStream is = getClass().getClassLoader()
                .getResourceAsStream("/Schema.json");
        BufferedReader jsonSchema1 = new BufferedReader(new InputStreamReader(is));
		json = objectMapper.readTree(output);
		
		 JsonSchema schema = schemaFactory.getSchema(String.valueOf(jsonSchema1));
	      Set<ValidationMessage> validationResult = schema.validate(json);
	      logger.info("==================================================================================");
	      logger.info("\"Checking json response body \" + output + \" with json schema \" + String.valueOf(jsonSchema1)");
	     
	      if (validationResult.isEmpty()) {
	    	  logger.info("Data types and Mandatory fields are expected.");
	          System.out.println("Data types and Mandatory fields are expected.");
	      } else {
	          validationResult.forEach(vm -> logger.info(vm.getMessage()));
	          validationResult.forEach(vm -> softAssertion.assertEquals(vm.getMessage(), ""));
	      }
	} catch (JsonProcessingException e) {
		e.printStackTrace();
	}
     
	switch (eventName.getValue().toString()) {
    case "logged in":
        softAssertion.assertEquals(JsonPath.read(output, "$.user_properties.['link plus id']"), "8109080029974727");
        softAssertion.assertEquals(JsonPath.read(output, "$.user_properties.['user id']"), "6818752");
        softAssertion.assertEquals(JsonPath.read(output, "$.user_properties.['user type']"), "existing");
        softAssertion.assertEquals(JsonPath.read(output, "$.user_properties.['account type']"), "Plus");
        softAssertion.assertEquals(JsonPath.read(output, "$.user_properties.['login/signup source']"), "grocery-online");
        softAssertion.assertEquals(JsonPath.read(output, "$.user_properties.['linkpass id']"), "2099717129513397");
        softAssertion.assertEquals(JsonPath.read(output, "$.event_properties.['store id']"), "003");
        softAssertion.assertEquals(JsonPath.read(output, "$.event_properties.['login/signup source']"), "grocery-online");
        softAssertion.assertEquals(JsonPath.read(output, "$.event_properties.['vertical']"), "grocery-online");
        break;
    case "product added to cart":
        System.out.println("a");
        break;
    case "search completed":
        softAssertion.assertEquals(JsonPath.read(output, "$.event_properties.['store id']"), "476"); //why  not in the requirement
        softAssertion.assertEquals(JsonPath.read(output, "$.event_properties.['user typed search term']"), "candy");
        softAssertion.assertEquals(JsonPath.read(output, "$.event_properties.['vertical']"), "grocery-online");
        softAssertion.assertEquals(JsonPath.read(output, "$.event_properties.['login/signup source']"), "grocery-online"); // not in the requirement
        softAssertion.assertEquals(JsonPath.read(output, "$.event_properties.['fulfillment type']"), "FFS");
        softAssertion.assertEquals(JsonPath.read(output, "$.event_properties.['call to action location']"), "grocery-online | home");
        softAssertion.assertEquals(JsonPath.read(output, "$.event_properties.['search term selected']"), "candy");
        break;
    
    default:
}
softAssertion.assertAll();
    
    return result;
  }
}