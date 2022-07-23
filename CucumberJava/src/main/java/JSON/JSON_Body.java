package JSON;

import java.util.Random;

import org.json.JSONObject;

public class JSON_Body {

	JSONObject json;
	String text;
	Random random = new Random();
	public static String gender = "male";
	public static String status = "active";

	public String getRandomName() {

		text = "Test" + random.nextInt(10000); 

		return text;
	}


	public String getRandomEmail() {

		text = "Test" + random.nextInt(10000) + "@test.com";

		return text;
	}

	public String createUserBody() {

		// Creating New Object
		json = new JSONObject();

		// Adding Details in JSON Body
		json.put("name", getRandomName());
		json.put("gender", gender);
		json.put("email", getRandomEmail());
		json.put("status", status);

		return json.toString();
	}

	public String updateUserBody() {

		// Creating New Object
		json = new JSONObject();

		// Adding Details in JSON Body
		json.put("name", getRandomName());
		json.put("gender", gender);
		json.put("status", status);

		return json.toString();
	}
	
}
