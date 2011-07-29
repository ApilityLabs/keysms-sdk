## C# Client
You can find the C# client at https://github.com/KeyteqLabs/KeySMS

### Send message

            String userName 	= "98800000";				                // Username used to authenicate with 
		    String apiKey		= "...";	                                // API key. Key is obtained through "Min side" at app.keysms.no
		    String message 		= "SMS to myself";
		    String reciever 	= "99999999";				                // Could be several receivers ({"98800000",.....,"91555555"})
            DateTime datetime = new DateTime(2011, 07, 29, 16, 0, 0);       // If prior to todays date - assumes date == today. Time should be converted downwards to nearest quarter. eg 14:41 => 14:30

            KeySMSOptions options = new KeySMSOptions(null, datetime);
		
		    //Instansiate KeySMS and send SMS
		    KeySMS keySms = new KeySMS();
		    keySms.auth(userName, apiKey);		 			                // Define what user to authenticate with.
		    SMSResponse response = keySms.sms(message, reciever, options);	// Send SMS to recievers. Returnes response from server. Date and time are optional.  		
		
Output is parsed to a container-class.