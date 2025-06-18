#!/bin/sh

#Cert decoded process, enable later
#mkdir -p /app/certs

#echo "$privateKey_B64" | base64 -d > /app/certs/privateKey.pem
#echo "$publicKey_B64" | base64 -d > /app/certs/publicKey.pem

exec java -jar tutor-net.jar