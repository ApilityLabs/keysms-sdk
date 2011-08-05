# KeySMS HTTP API

## Request signing

In order to gain authentication to act on behalf of a KeySMS user, each request must be signed.
Every user can generate an API key in the App itself, and this secret key is in time inputted into your application along with the username.
Request signing happens by signing the JSON data string, and a signed request requires three parameters : `signature`,`username` and `payload`.
`Payload` refers to the entire payload you would normally send in the HTTP POST request. The post data must be JSON encoded.
The following straight forward PHP code describes the required post data:

    $payload = json_encode($payloadArray);
    $postFields = array(
        'signature' => md5($payload . $secretKey),
        'username' => $username,
        'payload' => $payload
    );

## API methods

### Send message (POST /messages)

Supports the following arguments:

* `message` **required** UTF-8 encoded message
* `receivers` **required** Array of receivers
* `sender` **required** Which of the accounts receivers to send from
* `time` A timestamp in the future to send at. Defaults to now
* `date` A date in the future to send at. Defaults to today

> curl -XPOST http://app.keysms.no/messages -d payload='{message:"Hello world", receivers:["99999999"]}' -d signature="your generated sign key" -d username="me"

#### Errors
The following errors may be returned

### Get account info (GET /auth/current)

Supports the following arguments:

* `user`        bool Return information about logged in user
* `account`     bool Return information about account for logged in user
* `contacts`    bool Return the _full_ contact list
* `groups`      bool Return groups
* `aliases`     bool Return aliases
* `keywords`    bool Return keywords

> curl -XPOST http://app.keysms.no/messages -d user=1 -d account=0 -d groups=1 -d signature="foo" -d username="bar"

## Clients

* [PHP Documentation][php]
* [C# Documentation][c#]
* [Java Documentation][Java]

[php]: /KeyteqLabs/KeySMS/tree/master/PHP/ "PHP Documentation"
[C#]: /KeyteqLabs/KeySMS/tree/master/C#/ "C# Documentation"
[Java]: /KeyteqLabs/KeySMS/tree/master/Java/ "Java Documentation"
