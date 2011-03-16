package example;

/**
 * Method for invoking KeySMS
 * @package controller
 * @author ¯yvind Kvalnes / oyvind.kvalnes@keyteq.no
 * @since 10.03.2011
 */

import controller.*;
public class example {

	public static void main(String[] args) 
	{
		//Instansiate variables
		String userName 	= "98800000";							// Username used to authenicate with 
		String apiKey		= "79e7e90aeeb9db0e2db4f7e4a6430f07";	// API key. Key is obtained through ÇMin sideÈ at app.keysms.no
		String message 		= "SMS to myself";
		String[] recievers 	= {"98855555"};  
		//String[] recievers 	= {"98800000", "98800001"};			Could be several receivers : 
		String date 		= "2011-03-10";							// If prior to todays date - assumes date == today.
		String time			= "16:00";								// Time converted downwards to nearest quarter. eg 14:41 => 14:30
		
		//Instansiate KeySMS and send SMS
		KeySMS keySms 		= new KeySMS();
		keySms.auth(userName, apiKey);		 									// Define what user to authenticate with.
//		Object response = keySms.sms(message, recievers, date, time); 			// Send SMS to recievers. Returnes response from server. Date and time are optional.  
		Object response = keySms.sms(message, recievers);  
		System.out.println(response);
	}

}
