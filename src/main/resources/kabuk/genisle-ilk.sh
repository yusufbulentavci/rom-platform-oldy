#!/bin/bash



msg='{"f":"localhost","t":"localhost","c":"genisle.grow","ani":true,"d":{"c":{"hostname":"localhost.com","lang":"tr","admin":"han","credential":"han","contact":{"email":"avci.yusuf@gmail.com","cc":"TR","firstname":"Adim","lastname":"Soyadim"}}}}'
echo $msg  > /dev/udp/127.0.0.1/9876