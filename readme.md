# KeySMS HTTP API

## Request signing

In order to gain authentication to act on behalf of a KeySMS user, each request must be signed.
Every user can generate an API key in the App itself, and this secret key is in time inputted into your application along with the username.
Request signing happens by signing the JSON data string, and a signed request requires three parameters : `signature`,`username` and `payload`.
`Payload` refers to the entire payload you would normally send in the HTTP POST request. The post data must be JSON encoded.
The following straight forward PHP code describes the required post data:

    $payload = json_encode($payloadArray);
    $postFields = array(
        'signature' => md5($payload),
        'username' => $username,
        'payload' => $payload
    );

## API methods

### Send message (POST /messages)
Supports the following arguments:
*  `message`     _*_ UTF-8 encoded message
*  `receivers`   _*_ Array of receivers
*  `sender`      _*_ Which of the accounts receivers to send from
*  `time`        A timestamp in the future to send at. If `date` is not set defaults to today
*  `date`        A date in the future to send at. If `time` is not set, defaults to now

'''curl -XPOST http://app.keysms.no/messages -d payload='{message:"Hello world", receivers:["99999999"]}' -d signature="your generated sign key" -d username="me"'''

#### Errors
The following errors may be returned

### Get account info (GET /auth/current)
Supports the following arguments:
*  `user`        bool Return information about logged in user
*  `account`     bool Return information about account for logged in user
*  `contacts`    bool Return the _full_ contact list
*  `groups`      bool Return groups
*  `aliases`     bool Return aliases
*  `keywords`    bool Return keywords

'''curl -XPOST http://app.keysms.no/messages -d user=1 -d account=0 -d groups=1 -d signature="foo" -d username="bar"'''

## PHP Client
You can find the PHP client here

### Send message

    $keysms = new KeySMS;
    $keysms->auth($username, $apiKey);
    $message = "SMS to myself";
    $receiver = "90640936"; // Could be several receivers : array($num, $num2, .., $numN)
    $response = $keysms->sms($message, $receiver);

This produces output along the lines of :
    Array
    (
        [message] => Array
            (
                [sent] => 1
                [updated] => 1298561868
                [parts] => Array
                    (
                        [total] => 1
                        [parts] => Array
                            (
                                [0] => Heisann
                            )

                    )

                [_id] => 4d667b4cc3b3990742000006
                [created] => 1298561868
                [receivers] => Array
                    (
                        [0] => Array
                            (
                                [number] => 90640936
                                [created] => 1298561868
                                [modified] => 
                                [status] => 0
                                [smsoutid] => 7717266
                            )

                    )

                [message] => Heisann
                [sender] => 90640936
                [timed] => 
            )

    )

### Get account information

    // See supported argumentsfor GET call
    $fields = array('users' => true, 'account' => true, 'keywords' => false);
    print_r($keysms->info($fields));

Output can be very hefty, you are advised to inspect the output yourself to make sure
Produces :
    array(
        'aliases' => array(
            array(
                'name' => '99999999', // String, either a number or a name
                'validated' => bool,
                'default' => bool
            )
        ),
        'account' => array(
            'name' => 'Account name',
            'sms' => array(
                max => 10000, // Upper limit of sms / month for this account
                sent => 500, // How many sent this month
                price => 0.39, // Price pr sms in NOK
                cost => 3900 // Total cost of sent smses
            )
            ....
        ),
        ....
    )
