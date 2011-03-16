## Java Client
You can find the Java client at https://github.com/KeyteqLabs/KeySMS

### Send message

		String userName 	= "98800000";							// Username used to authenicate with 
		String apiKey		= "79e7e90aeeb9db0e2db4f7e4a6430f07";	// API key. Key is obtained through ÇMin sideÈ at app.keysms.no
		String message 		= "SMS to myself";
		String[] recievers 	= {"98855555"};  						// Could be several receivers ({"98800000",.....,"91555555"}) 
		String date 		= "2011-03-10";							// If prior to todays date - assumes date == today.
		String time			= "16:00";								// Time converted downwards to nearest quarter. eg 14:41 => 14:30
		
		//Instansiate KeySMS and send SMS
		KeySMS keySms 		= new KeySMS();
		keySms.auth(userName, apiKey);		 									// Define what user to authenticate with.
		Object response = keySms.sms(message, recievers, date, time); 			// Send SMS to recievers. Returnes response from server. Date and time are optional.  
		System.out.println(response);
		
This produces output along the lines of :
    {"ok":true,"message":{"sent":true,"updated":"2011-03-16 12:02:29","parts":{"total":1,"parts":["SMS to myself"]},
    "_id":"4d8098c5c3b399b23e123006","created":"2011-03-16 12:02:29","message":"SMS to myself",
    "receivers":[{"number":"98800000","deliverystatus":0,"nextgwsynctime":1300273469}],
    "sender":"98800000","timed":null},"quantity":1,"cost":0.49,"smsPrice":0.49}

