<?php
require 'KeySMS.php';

$username = ''; // Your username
$authKey = ''; // Your api key, generated in-app

$keysms = new KeySMS;
$keysms->auth($username, $authKey);
$r = $keysms->sms('Hello world', array('99999999'));

print_r($r);
