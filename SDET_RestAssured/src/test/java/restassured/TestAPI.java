package restassured;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import static org.hamcrest.Matchers.*;

import org.apache.commons.codec.binary.Base64;
import org.json.simple.JSONObject;

public class TestAPI {
	
	@Test(enabled=false)
	public void getmethod() {
		
		RestAssured.useRelaxedHTTPSValidation();
		
		Response r1=RestAssured.get("https://reqres.in/api/users?page=2");
		System.out.println(r1.getStatusCode());
		System.out.println(r1.getStatusLine());
		System.out.println(r1.body().asString());
		System.out.println(r1.headers());
		Assert.assertEquals(200,r1.getStatusCode());
	}
	
	@Test(enabled=false)
	public void getmethod_bddstyle() {
		
		RestAssured.useRelaxedHTTPSValidation();
		
		given().
		when().get("https://reqres.in/api/users?page=2").
		then().statusCode(200).statusLine("HTTP/1.1 200 OK").header("Content-Type", "application/json; charset=utf-8").
		body("data.id[2]", equalTo(9)).body("data.first_name",hasItems("Byeeeron"));
	}
	@Test(enabled=false)
	public void postmethod(String name, String job) {
		
		RestAssured.useRelaxedHTTPSValidation();
		
		JSONObject data=new JSONObject();
		
		data.put("name",name);
		data.put("job",job);
		System.out.println(data);
		
		given().
		when().body(data.toJSONString()).post("https://reqres.in/api/users").
		then().statusCode(201);
	}
	
	@DataProvider(name="dpadd")
	public Object[][] getdata(){
		
		Object[][] t1=new Object[][] {
			
			{"aaa","sdet"},
			{"bbb","developer"},
			{"cccc","tester"}
		};
		return t1;
		
	}
	
	@Test(enabled=false)
	public void deletemethod() {
		
		RestAssured.useRelaxedHTTPSValidation();
		
		given().
		when().delete("https://reqres.in/api/users/2").
		then().statusCode(204);
	}
	
	@Test
	public void auth() {
		
		RestAssured.useRelaxedHTTPSValidation();
		
		String data="postman:password";
		byte[] encodevalue=Base64.encodeBase64(data.getBytes());
		String encodedstring=new String(encodevalue);
		given().header("Authorization","Basic"+encodedstring).
		when().get("https://postman-echo.com/basic-auth").
		then().statusCode(200);
		
	}

}
