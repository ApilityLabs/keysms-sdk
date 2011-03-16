## Java Client
You can find the Java client at https://github.com/KeyteqLabs/KeySMS

### Send message

		KeySMS keySms = new KeySMS();
		keySms.auth(userName, apiKey);		 									// Define what user to authenticate with.
//		Object response = keySms.sms(message, recievers, date, time); 			// Send SMS to recievers. Returnes response from server. Date and time are optional.  
		Object response = keySms.sms(message, recievers);  
		System.out.println(response);
		
This produces output along the lines of :
    {"ok":true,"message":{"sent":true,"updated":"2011-03-16 12:02:29","parts":{"total":1,"parts":["SMS to myself"]},
    "_id":"4d8098c5c3b399b23e123006","created":"2011-03-16 12:02:29","message":"SMS to myself",
    "receivers":[{"number":"98800000","deliverystatus":0,"nextgwsynctime":1300273469}],
    "sender":"98800000","timed":null},"quantity":1,"cost":0.49,"smsPrice":0.49}

