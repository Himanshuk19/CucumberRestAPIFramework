package StepDefinitions;

import static org.junit.Assert.assertEquals;

import org.json.JSONArray;
import org.json.JSONObject;

import JSON.JSON_Body;
import io.cucumber.java.en.And;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;


public class APITest {

	Response response = null;

	public static String baseURI = "https://gorest.co.in/public/v2";

	// Tokens
	public static String validAccessToken = "be442412bf877822d5561fa2349051016684ff0a7a978ff62bf2e1a1d8716429";
	public static String expiredAccessToken = "8630bfa179b139bd93485116a0bc9031e1fccbfa5424c6dfb957d3f9ccce2cb0";
	public static String invalidAccessToken = "1234";
	int statusCodeOk = 200;
	int statusCodeCreated = 201;
	int statusCodeNoContent = 204;
	int statusCodeUnauthorized = 401;
	int statusCodeNotFound = 404;

	// Messages
	public static String tokenExpired = "Token expired";
	public static String authenticationFailed = "Authentication failed";
	public static String resourceNotFound = "Resource not found";
	public int userId;
	JSONArray Jsonarray =  new JSONArray();
	JSONObject Jsonobject = new JSONObject();
	public static String invalidId = "abc";
	String requestBody;
	JSON_Body jb = new JSON_Body();

	@And("Hit All User API and verify the details")
	public void test1() {

		response = RestAssured.get(baseURI+"/users");
		System.out.println(response.prettyPrint());

		// Validations
		assertEquals("Status Code of API Call is : " + response.getStatusCode() + " Expected : " + statusCodeOk, statusCodeOk, response.getStatusCode());

	}

	@And("Hit an Inidividual user API with valid id")
	public void test2() {

		// All User API call
		response = RestAssured.get(baseURI+"/users");
		Jsonarray = new JSONArray(response.asString());
		userId = Jsonarray.getJSONObject(0).getInt("id");
		Jsonobject = Jsonarray.getJSONObject(0);
		System.out.println("Valid ID : " + userId);

		// Valid User API call
		response = RestAssured.get(baseURI+"/users/"+userId);
		System.out.println(response.prettyPrint());
		JSONObject responseObject = new JSONObject(response.asString());

		// Validations
		assertEquals("Status Code of API Call is : " + response.getStatusCode() + " Expected : " + statusCodeOk, statusCodeOk, response.getStatusCode());
		assertEquals("Actual id : "+ responseObject.getInt("id")+" Expected id :" + Jsonobject.getInt("id"),Jsonobject.getInt("id"),responseObject.getInt("id"));
		assertEquals("Actual name : "+ responseObject.getString("name")+" Expected name :" + Jsonobject.getString("name"),Jsonobject.getString("name"),responseObject.getString("name"));
		assertEquals("Actual email : "+ responseObject.getString("email")+" Expected email :" + Jsonobject.getString("email"),Jsonobject.getString("email"),responseObject.getString("email"));
		assertEquals("Actual gender : "+ responseObject.getString("gender")+" Expected gender :" + Jsonobject.getString("gender"),Jsonobject.getString("gender"),responseObject.getString("gender"));
		assertEquals("Actual status : "+ responseObject.getString("status")+" Expected status :" + Jsonobject.getString("status"),Jsonobject.getString("status"),responseObject.getString("status"));
	
	}

	@And("Hit an Inidividual user API with invalid id")
	public void test3() {

		// Invalid User API call
		response = RestAssured.get(baseURI+"/users/"+invalidId);
		System.out.println(response.prettyPrint());
		Jsonobject = new JSONObject(response.asString());

		// Validations
		assertEquals("Status Code of API Call is : " + response.getStatusCode() + " Expected : " + statusCodeNotFound, statusCodeNotFound, response.getStatusCode());
		assertEquals("Actual message : "+ Jsonobject.getString("message")+" Expected message :" + resourceNotFound,resourceNotFound,Jsonobject.getString("message"));
	
	}	

	@And("Hit Create an Inidividual user API with valid Access Token")
	public void test4() {

		// Adding Headers, Query Params and Body to the Request
		RequestSpecification httpRequest = RestAssured.given(); 
		requestBody = jb.createUserBody();
		httpRequest.queryParam("access-token", validAccessToken);
		httpRequest.header("Accept", "application/json");
		httpRequest.header("Content-Type", "application/json");
		httpRequest.header("Authorization", "Bearer ACCESS-TOKEN");
		httpRequest.body(requestBody);

		// Create Individual User API call
		response =httpRequest.post(baseURI+"/users");
		System.out.println("Response is : " + response.asString());
		Jsonobject = new JSONObject(requestBody);
		JSONObject responseObject = new JSONObject(response.asString());

		// Validations
		assertEquals("Status Code of API Call is : " + response.getStatusCode() + " Expected : " + statusCodeCreated, statusCodeCreated, response.getStatusCode());
		assertEquals("Actual name : "+ responseObject.getString("name")+" Expected name :" + Jsonobject.getString("name"),Jsonobject.getString("name"),responseObject.getString("name"));
		assertEquals("Actual email : "+ responseObject.getString("email")+" Expected email :" + Jsonobject.getString("email"),Jsonobject.getString("email"),responseObject.getString("email"));
		assertEquals("Actual gender : "+ responseObject.getString("gender")+" Expected gender :" + Jsonobject.getString("gender"),Jsonobject.getString("gender"),responseObject.getString("gender"));
		assertEquals("Actual status : "+ responseObject.getString("status")+" Expected status :" + Jsonobject.getString("status"),Jsonobject.getString("status"),responseObject.getString("status"));

	}

	@And("Hit Create an Inidividual user API with invalid Access Token")
	public void test5() {

		// Adding Headers, Query Params and Body to the Request
		RequestSpecification httpRequest = RestAssured.given(); 
		requestBody = jb.createUserBody();
		httpRequest.queryParam("access-token", invalidAccessToken);
		httpRequest.header("Accept", "application/json");
		httpRequest.header("Content-Type", "application/json");
		httpRequest.header("Authorization", "Bearer ACCESS-TOKEN");
		httpRequest.body(requestBody);

		// Create Individual User API call
		response =httpRequest.post(baseURI+"/users");
		System.out.println("Response is : " + response.asString());
		Jsonobject = new JSONObject(response.asString());

		// Validations
		assertEquals("Status Code of API Call is : " + response.getStatusCode() + " Expected : " + statusCodeUnauthorized, statusCodeUnauthorized, response.getStatusCode());
		assertEquals("Actual message : "+ Jsonobject.getString("message")+" Expected message :" + authenticationFailed,authenticationFailed,Jsonobject.getString("message"));

	}

	@And("Hit Create an Inidividual user API with expired Access Token")
	public void test6() {

		// Adding Headers, Query Params and Body to the Request
		RequestSpecification httpRequest = RestAssured.given(); 
		requestBody = jb.createUserBody();
		httpRequest.queryParam("access-token", expiredAccessToken);
		httpRequest.header("Accept", "application/json");
		httpRequest.header("Content-Type", "application/json");
		httpRequest.header("Authorization", "Bearer ACCESS-TOKEN");
		httpRequest.body(requestBody);

		// Create Individual User API call
		response =httpRequest.post(baseURI+"/users");
		System.out.println("Response is : " + response.asString());
		Jsonobject = new JSONObject(response.asString());

		// Validations
		assertEquals("Status Code of API Call is : " + response.getStatusCode() + " Expected : " + statusCodeUnauthorized, statusCodeUnauthorized, response.getStatusCode());
		assertEquals("Actual message : "+ Jsonobject.getString("message")+" Expected message :" + tokenExpired,tokenExpired,Jsonobject.getString("message"));

	}

	@And("Hit Update an Inidividual user API with valid Access Token")
	public void test7() {

		// All User API call
		response = RestAssured.get(baseURI+"/users");
		Jsonarray = new JSONArray(response.asString());
		userId = Jsonarray.getJSONObject(0).getInt("id");
		Jsonobject = Jsonarray.getJSONObject(0);
		System.out.println("Valid ID : " + userId);

		// Adding Headers, Query Params and Body to the Request
		RequestSpecification httpRequest = RestAssured.given(); 
		requestBody = jb.updateUserBody();
		httpRequest.queryParam("access-token", validAccessToken);
		httpRequest.header("Accept", "application/json");
		httpRequest.header("Content-Type", "application/json");
		httpRequest.header("Authorization", "Bearer ACCESS-TOKEN");
		httpRequest.body(requestBody);

		// Update Individual User API call
		response =httpRequest.put(baseURI+"/users/"+userId);
		System.out.println("Response is : " + response.asString());
		Jsonobject = new JSONObject(requestBody);
		JSONObject responseObject = new JSONObject(response.asString());

		// Validations
		assertEquals("Status Code of API Call is : " + response.getStatusCode() + " Expected : " + statusCodeOk, statusCodeOk, response.getStatusCode());
		assertEquals("Actual name : "+ responseObject.getString("name")+" Expected name :" + Jsonobject.getString("name"),Jsonobject.getString("name"),responseObject.getString("name"));
		assertEquals("Actual gender : "+ responseObject.getString("gender")+" Expected gender :" + Jsonobject.getString("gender"),Jsonobject.getString("gender"),responseObject.getString("gender"));
		assertEquals("Actual status : "+ responseObject.getString("status")+" Expected status :" + Jsonobject.getString("status"),Jsonobject.getString("status"),responseObject.getString("status"));

	}

	@And("Hit Update an Inidividual user API with invalid Access Token")
	public void test8() {

		// All User API call
		response = RestAssured.get(baseURI+"/users");
		Jsonarray = new JSONArray(response.asString());
		userId = Jsonarray.getJSONObject(0).getInt("id");
		Jsonobject = Jsonarray.getJSONObject(0);
		System.out.println("Valid ID : " + userId);

		// Adding Headers, Query Params and Body to the Request
		RequestSpecification httpRequest = RestAssured.given(); 
		requestBody = jb.updateUserBody();
		httpRequest.queryParam("access-token", invalidAccessToken);
		httpRequest.header("Accept", "application/json");
		httpRequest.header("Content-Type", "application/json");
		httpRequest.header("Authorization", "Bearer ACCESS-TOKEN");
		httpRequest.body(requestBody);

		// Update Individual User API call
		response =httpRequest.put(baseURI+"/users/"+userId);
		System.out.println("Response is : " + response.asString());
		Jsonobject = new JSONObject(response.asString());

		// Validations
		assertEquals("Status Code of API Call is : " + response.getStatusCode() + " Expected : " + statusCodeUnauthorized, statusCodeUnauthorized, response.getStatusCode());
		assertEquals("Actual message : "+ Jsonobject.getString("message")+" Expected message :" + authenticationFailed,authenticationFailed,Jsonobject.getString("message"));
	
	}

	@And("Hit Update an Inidividual user API with expired Access Token")
	public void test9() {

		// All User API call
		response = RestAssured.get(baseURI+"/users");
		Jsonarray = new JSONArray(response.asString());
		userId = Jsonarray.getJSONObject(0).getInt("id");
		Jsonobject = Jsonarray.getJSONObject(0);
		System.out.println("Valid ID : " + userId);

		// Adding Headers, Query Params and Body to the Request
		RequestSpecification httpRequest = RestAssured.given(); 
		requestBody = jb.updateUserBody();
		httpRequest.queryParam("access-token", expiredAccessToken);
		httpRequest.header("Accept", "application/json");
		httpRequest.header("Content-Type", "application/json");
		httpRequest.header("Authorization", "Bearer ACCESS-TOKEN");
		httpRequest.body(requestBody);

		// Update Individual User API call
		response =httpRequest.put(baseURI+"/users/"+userId);
		System.out.println("Response is : " + response.asString());
		Jsonobject = new JSONObject(response.asString());

		// Validations
		assertEquals("Status Code of API Call is : " + response.getStatusCode() + " Expected : " + statusCodeUnauthorized, statusCodeUnauthorized, response.getStatusCode());
		assertEquals("Actual message : "+ Jsonobject.getString("message")+" Expected message :" + tokenExpired,tokenExpired,Jsonobject.getString("message"));

	}

	@And("Hit Delete an Inidividual user API with valid Access Token with valid user id")
	public void test10() {

		// All User API call
		response = RestAssured.get(baseURI+"/users");
		Jsonarray = new JSONArray(response.asString());
		userId = Jsonarray.getJSONObject(0).getInt("id");
		Jsonobject = Jsonarray.getJSONObject(0);
		System.out.println("Valid ID : " + userId);

		// Adding Headers, Query Params and Body to the Request
		RequestSpecification httpRequest = RestAssured.given(); 
		requestBody = jb.updateUserBody();
		httpRequest.queryParam("access-token", validAccessToken);
		httpRequest.header("Accept", "application/json");
		httpRequest.header("Content-Type", "application/json");
		httpRequest.header("Authorization", "Bearer ACCESS-TOKEN");
		httpRequest.body(requestBody);

		// Update Individual User API call
		response =httpRequest.delete(baseURI+"/users/"+userId);
		System.out.println("Response is : " + response.asString());

		// Validations
		assertEquals("Status Code of API Call is : " + response.getStatusCode() + " Expected : " + statusCodeNoContent, statusCodeNoContent, response.getStatusCode());

	}

	@And("Hit Delete an Inidividual user API with invalid Access Token with valid user id")
	public void test11() {

		// All User API call
		response = RestAssured.get(baseURI+"/users");
		Jsonarray = new JSONArray(response.asString());
		userId = Jsonarray.getJSONObject(0).getInt("id");
		Jsonobject = Jsonarray.getJSONObject(0);
		System.out.println("Valid ID : " + userId);

		// Adding Headers, Query Params and Body to the Request
		RequestSpecification httpRequest = RestAssured.given(); 
		requestBody = jb.updateUserBody();
		httpRequest.queryParam("access-token", invalidAccessToken);
		httpRequest.header("Accept", "application/json");
		httpRequest.header("Content-Type", "application/json");
		httpRequest.header("Authorization", "Bearer ACCESS-TOKEN");
		httpRequest.body(requestBody);

		// Update Individual User API call
		response =httpRequest.delete(baseURI+"/users/"+userId);
		System.out.println("Response is : " + response.asString());
		Jsonobject = new JSONObject(response.asString());

		// Validations
		assertEquals("Status Code of API Call is : " + response.getStatusCode() + " Expected : " + statusCodeUnauthorized, statusCodeUnauthorized, response.getStatusCode());
		assertEquals("Actual message : "+ Jsonobject.getString("message")+" Expected message :" + authenticationFailed,authenticationFailed,Jsonobject.getString("message"));
	
	}

	@And("Hit Delete an Inidividual user API with expired Access Token with valid user id")
	public void test12() {

		// All User API call
		response = RestAssured.get(baseURI+"/users");
		Jsonarray = new JSONArray(response.asString());
		userId = Jsonarray.getJSONObject(0).getInt("id");
		Jsonobject = Jsonarray.getJSONObject(0);
		System.out.println("Valid ID : " + userId);

		// Adding Headers, Query Params and Body to the Request
		RequestSpecification httpRequest = RestAssured.given(); 
		requestBody = jb.updateUserBody();
		httpRequest.queryParam("access-token", expiredAccessToken);
		httpRequest.header("Accept", "application/json");
		httpRequest.header("Content-Type", "application/json");
		httpRequest.header("Authorization", "Bearer ACCESS-TOKEN");
		httpRequest.body(requestBody);

		// Update Individual User API call
		response =httpRequest.delete(baseURI+"/users/"+userId);
		System.out.println("Response is : " + response.asString());
		Jsonobject = new JSONObject(response.asString());

		// Validations
		assertEquals("Status Code of API Call is : " + response.getStatusCode() + " Expected : " + statusCodeUnauthorized, statusCodeUnauthorized, response.getStatusCode());
		assertEquals("Actual message : "+ Jsonobject.getString("message")+" Expected message :" + tokenExpired,tokenExpired,Jsonobject.getString("message"));

	}

	@And("Hit Delete an Inidividual user API with valid Access Token with invalid user id")
	public void test13() {

		// All User API call
		response = RestAssured.get(baseURI+"/users");
		Jsonarray = new JSONArray(response.asString());
		userId = Jsonarray.getJSONObject(0).getInt("id");
		Jsonobject = Jsonarray.getJSONObject(0);
		System.out.println("Valid ID : " + userId);

		// Adding Headers, Query Params and Body to the Request
		RequestSpecification httpRequest = RestAssured.given(); 
		requestBody = jb.updateUserBody();
		httpRequest.queryParam("access-token", validAccessToken);
		httpRequest.header("Accept", "application/json");
		httpRequest.header("Content-Type", "application/json");
		httpRequest.header("Authorization", "Bearer ACCESS-TOKEN");
		httpRequest.body(requestBody);

		// Update Individual User API call
		response =httpRequest.delete(baseURI+"/users/"+invalidId);
		System.out.println("Response is : " + response.asString());
		Jsonobject = new JSONObject(response.asString());
		
		// Validations
		assertEquals("Status Code of API Call is : " + response.getStatusCode() + " Expected : " + statusCodeNotFound, statusCodeNotFound, response.getStatusCode());
		assertEquals("Actual message : "+ Jsonobject.getString("message")+" Expected message :" + resourceNotFound,resourceNotFound,Jsonobject.getString("message"));
	
	}
}
