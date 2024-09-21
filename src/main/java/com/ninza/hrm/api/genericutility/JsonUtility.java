package com.ninza.hrm.api.genericutility;

import com.jayway.jsonpath.JsonPath;
import static io.restassured.RestAssured.*;

import java.util.List;

import io.restassured.response.Response;

public class JsonUtility {
	
	FileUtility fLib=new FileUtility();

	/**
	 * get the jsondata from based on json complex xpath
	 * 
	 * @param resp
	 * @param jsonXpath
	 * @return
	 */

	public String getDataOnJsonPath(Response resp, String jsonXpath) {
		List<Object> list = JsonPath.read(resp.asString(), jsonXpath);
		return list.get(0).toString();

	}

	/**
	 * get the xml data from based on xml complex xpath
	 * 
	 * @param resp
	 * @param xmlXpath
	 * @return
	 **/

	public String getDataOnXPath(Response resp, String xmlXPath) {

		return resp.xmlPath().getString(xmlXPath);

	}

	/**
	 * verify the data in jsonbody based jsonpath
	 * 
	 * @param resp
	 * @param jsonXPath
	 * @param expectedData
	 * @return
	 */

	public boolean verifyDataOnJsonPath(Response resp, String jsonXPath, String expectedData) {

		List<String> list = JsonPath.read(resp.asString(), jsonXPath);

		boolean flag = false;

		for (String str : list) {
			if (str.equals(expectedData))
				System.out.println(expectedData + "is available===========pass");
			flag = true;
		}
		if (flag == false) {
			System.out.println(expectedData + "is not available===========pass");
		}
		return flag;
	}

	public String getAccessToken() throws Throwable {

		Response resp = given().formParam("client_id", fLib.getDataFromPropertiesFile("client_id"))
				.formParam("client_secret",fLib.getDataFromPropertiesFile("client_secret"))
				.formParam("grant_type", "client_credentials").when()
				.post("http://49.249.28.218:8180/auth/realms/ninza/protocol/openid-connect/token");

		resp.then().log().all();

		//capture the data from response
		String token = resp.jsonPath().getString("access-token");
		return token;

	}

}
