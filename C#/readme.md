## C# Client
You can find the C# client at https://github.com/KeyteqLabs/KeySMS

### Send message

``` c#
String userName = "98800000";
String apiKey = "...";
String message = "SMS to myself";

// Could be several receivers ({"98800000",.....,"91555555"})
String reciever = "99999999";

// Use KeySMSOptions to trigger SMS send in the future, optional
// If prior to todays date - assumes date == today. Time should be converted downwards to nearest quarter. eg 14:41 => 14:30
DateTime datetime = new DateTime(2011, 07, 29, 16, 0, 0);
KeySMSOptions options = new KeySMSOptions(null, datetime);

// Instantiate KeySMS client and send SMS
KeySMS keySms = new KeySMS();
keySms.auth(userName, apiKey); // Define what user to authenticate with.
SMSResponse response = keySms.sms(message, reciever, options); // Send SMS to recievers. Returnes response from server. Date and time are optional.
```

Output is parsed to a container-class which can be accessed in the following manner: Boolean status = response.ok; (transaction status).
