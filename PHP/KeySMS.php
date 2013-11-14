<?php
/**
 * KeySMS API client.
 *
 * @copyright Keyteq AS
 * @author Raymond Julin <raymond.julin@keyteq.no>
 */

/**
 * Make use of the KeySMS API to send SMS and poll your user account
 *
 * Usage:
 * #Send
 * <code>
 * $keysms = new KeySMS;
 * // Based on chosen auth method ties this session to this user
 * // API key is completely separated from password
 * $keysms->auth($username, $apiKey);
 *
 * $message = "Hello world from KeySMS!";
 * $receiver = "99999999";
 * $response = $keysms->sms($message, $receiver);
 * </code>
 */

class KeySMS
{

    const TIMEOUT = 30;

    /**
     * API options
     * @var array
     */
    protected $_options = array(
        'host' => 'app.keysms.no',
        'scheme' => 'https'
    );

    /**
     * Constructor, defines what address to connect to and other options
     * @param array $options The API wide options
     */
    public function __construct(array $options = array())
    {
        $this->_options = $options + $this->_options;
    }

    /**
     * Define what user to auth with
     * All actions taken will be tied to this user in keysms
     */
    public function auth($username, $apiKey)
    {
        $this->_options['auth'] = compact('username','apiKey');
    }

    /**
     * Actually send an SMS.
     * @param string $message
     * @param mixed $receivers
     * @param array $options Extras to amend to payload
     *
     * @return array Response information
     */
    public function sms($message, $receivers, array $options = array())
    {
        $options += array(
            'format' => 'array'
        );
        $format = $options['format'];
        unset($options['format']);
        if (!is_array($receivers))
            $receivers = array($receivers);
        $payload = compact('receivers', 'message');
        $payload += $options;

        $response = $this->_call('/messages', 'POST', $payload);
        return $this->cast($response, $format);
    }

    /**
     * Get account and user information to be used for making informed choices
     * in custom integrations
     *
     * @param array $fields Undocumented
     * @return array Response information
     */
    public function info(array $fields = array(), array $options = array())
    {
        $options += array(
            'format' => 'array'
        );
        $fields += array(
            'user' => true,
            'account' => true,
        );
        $format = $options['format'];
        unset($options['format']);

        $response = $this->_call('/auth/current.json', 'POST', $fields);
        return $this->cast($response, $format);
    }

    /**
     * Abstracts making HTTP calls through curl
     *
     * @param string $url Relative url to api host (/messages) 
     * @param string $method GET/POST
     * @param array $payload Completel payload
     */
    protected function _call($url, $method, $payload = array())
    {
        $method = strtoupper($method);

        $host = $this->_options['host'];
        $scheme = $this->_options['scheme'];
        $requestURL = $scheme . "://" . $host . $url;

        $conn = curl_init();

        curl_setopt($conn, CURLOPT_URL, $requestURL);
        curl_setopt($conn, CURLOPT_TIMEOUT, self::TIMEOUT);
        curl_setopt($conn, CURLOPT_PORT, $this->getPort());
        curl_setopt($conn, CURLOPT_RETURNTRANSFER, 1) ;

        curl_setopt($conn, CURLOPT_CUSTOMREQUEST, strtoupper($method));

        $signature = $this->sign($payload);
        $username = $this->_options['auth']['username'];
        $payload = json_encode($payload);

        curl_setopt($conn, CURLOPT_POSTFIELDS, compact('payload','signature','username'));

        $data = curl_exec($conn);
        if ($data !== false)
            return $data;
        else
        {
            /**
             * cUrl error code reference can be found here:
             * http://curl.haxx.se/libcurl/c/libcurl-errors.html
             */
            $errno = curl_errno($conn);
            switch ($errno)
            {
                case CURLE_FAILED_INIT:
                    $error = "Internal cUrl error?";
                    break;
                case CURLE_URL_MALFORMAT:
                    $error = "Malformed URL [$requestURL] -d " . json_encode($payload);
                    break;
                case CURLE_COULDNT_RESOLVE_PROXY:
                    $error = "Couldnt resolve proxy";
                    break;
                case CURLE_COULDNT_RESOLVE_HOST:
                    $error = "Couldnt resolve host";
                    break;
                case CURLE_COULDNT_CONNECT:
                    $error = "Couldnt connect to host [{$host}], DNS fail or API down";
                    break;
                case CURLE_OPERATION_TIMEDOUT:
                    $error = "Operation timed out on [$requestURL]";
                    break;
                default:
                    $error = "Unknown error";
                    if ($errno == 0)
                        $error .= ". Non-cUrl error";
                    break;
            }
            throw new RuntimeException($error);
        }
    }

    /**
     * Create completely sign string based on payload
     *
     * @param array $payload The complete payload to ship
     * @return string Ready to use sign string, just include in request
     */
    protected function sign($payload)
    {
        $payload = json_encode($payload);
        return md5($payload . $this->_options['auth']['apiKey']);
    }

    /**
     * Cast the raw data into any given data structure
     * @param string $data
     * @param string $format
     * @return mixed
     */
    protected function cast($data, $format)
    {
        switch ($format)
        {
        case 'array':
            return json_decode($data, true);
        case 'object':
            return json_decode($data);
        case 'json':
        default:
            return $data;
        }
    }

    /**
     * Get port to use for this connection
     * @return int
     */
    protected function getPort()
    {
        return $this->_options['scheme'] === 'https' ? 443 : 80;
    }
}
