## PHP Client
You can find the PHP client here
Read the [HTTP Documentation](/KeyteqLabs/KeySMS/tree/master/readme.md "HTTP Documentation")

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
                [created] => "2011-03-01 10:00:00"
                [updated] => "2011-03-01 10:00:00"
                [parts] => Array
                    (
                        [total] => 1
                        [parts] => Array
                            (
                                [0] => Heisann
                            )

                    )

                [_id] => 4d667b4cc3b3990742000006
                [receivers] => Array
                    (
                        [0] => Array
                            (
                                [number] => 90640936
                            )

                    )

                [message] => "SMS to myself"
                [sender] => "90640936"
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
