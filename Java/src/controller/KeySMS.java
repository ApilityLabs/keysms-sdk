/**
* KeySMS API client.
*
* @copyright Keyteq AS
* @author ¯yvind Kvalnes <oyvind.kvalnes@keyteq.no>
* @since 10.03.2011
*/

package controller;

import org.json.simple.*;
import java.math.BigInteger;
import java.net.*;
import java.security.MessageDigest;
import java.util.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;

public class KeySMS 
{

	public HashMap<String, Serializable> options;
	private String message;

	/**
	 * Constructor, defines what address to connect to and other options
	 * @param array $options The API wide options
	 */
	public KeySMS() 
	{
		this.options = new HashMap<String, Serializable>();
		this.options.put("host", "app.keysms.no");
		this.options.put("scheme", "http");
	}

	/**
	 * Define what user to auth with. All actions taken will be tied to this user in KeySMS
	 */
	public void auth(String userName, String apiKey) 
	{
		HashMap<String, String> auth = new HashMap<String, String>();
		auth.put("username",	 userName);
		auth.put("apiKey",		 apiKey);
		options.put("auth",		 auth);
	}

	/**
	 * Send an SMS.
	 */
	public Object sms(String message, String[] recievers) 
	{
		return this.sms(message, recievers, null, null);
	}

	/**
	 * Send an SMS some time in the future.
	 */
	public Object sms(String message, String[] recievers, String date,	String time) {
		this.message = message;
		String response = this.call("/messages", recievers, date, time);
		return response;
	}

	
	/**
	 * Abstracts making HTTP calls through curl
	 */
	private String call(String inputUrl, String[] recievers, String date, String time) 
	{

		JSONArray jsonArray 		= new JSONArray();
		for (String tlf : recievers) 
		{
			jsonArray.add(tlf);
		}
		JSONObject jsonPayload 		= new JSONObject();
		jsonPayload.put("message", message);
		jsonPayload.put("receivers", jsonArray);
		jsonPayload.put("date", date);
		jsonPayload.put("time", time);
		
		String host 				= (String) options.get("host");
		String scheme 				= (String) options.get("scheme");
		String requestURL 			= scheme + "://" + host + inputUrl;
		String signature		 	= this.sign(jsonPayload);
		String username 			= (String)((HashMap)options.get("auth")).get("username");
		printVariablesToConsole(jsonPayload, host, requestURL, signature, username);
		
		//Build parameter string
		String data;
		try {
			data = 	"payload="+URLEncoder.encode(jsonPayload.toString(),"UTF-8") + 
					"&signature="+URLEncoder.encode(signature,"UTF-8")			 +
					"&username="+URLEncoder.encode(username,"UTF-8");
			System.out.println(data);
	        try {
	            
	            // Send the request
	            URL url = new URL(requestURL);
	            URLConnection conn = url.openConnection();
	            conn.setDoOutput(true);
	            OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());
	            
	            //write parameters
	            writer.write(data);
	            writer.flush();
	            
	            // Get the response
	            StringBuffer answer = new StringBuffer();
	            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	            String line;
	            while ((line = reader.readLine()) != null) {
	                answer.append(line);
	            }
	            writer.close();
	            reader.close();
	            
	            //Return response from server
	            return answer.toString();
	            
	        } catch (UnsupportedEncodingException e) 
	        {
	        	e.printStackTrace();
	        }
            
        } catch (MalformedURLException ex) 
        {
            ex.printStackTrace();
        } catch (IOException ex) 
        {
            ex.printStackTrace();
        }
        
		return null; 		//Returns null on exception. 
	}

	/**
	 * Print the variables to console to verify their content.
	 */
	private void printVariablesToConsole(JSONObject jsonPayload, String host, String requestURL, String signature, String username) 
	{
		System.out.println("Host: "			+ host);
		System.out.println("URL " 			+ requestURL);
		System.out.println("Signature: " 	+  signature);
		System.out.println("username: " 	+ username);
		System.out.println("Json Payload: " + jsonPayload);
		System.out.println();
	}
	

    /**
	* Create completely sign string based on payload
	*
	* @param array $payload The complete payload to ship
	* @return string Ready to use sign string, just include in request
	*/
	public String sign(JSONObject json) {

		String stringToEncode = json.toString() + (String)((HashMap)options.get("auth")).get("apiKey");
		
		try {
			MessageDigest md5 = java.security.MessageDigest.getInstance("MD5");		
			md5.update(stringToEncode.getBytes("UTF-8"));
			
			byte[] digest = md5.digest();
			BigInteger bigInt = new BigInteger(1,digest);
			String hashtext = bigInt.toString(16);
			while(hashtext.length() < 32 ){
			  hashtext = "0"+hashtext;
			}
			
			return hashtext;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
		
	}
	
}