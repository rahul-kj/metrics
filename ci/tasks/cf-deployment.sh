#!/usr/bin/env bash
set -x

target="./cf api $API_ENDPOINT --skip-ssl-validation"
#echo $target
eval $target

login="./cf auth $USERNAME $PASSWORD"
#echo $login
eval $login

echo "Target Org and Space"
org_space="./cf target -o $ORG -s $SPACE"
eval $org_space

echo "push the app"
push="./cf push metrics -n $HOST -p metrics-release/metrics-*.jar -m 512m --no-start"
#echo $push
eval $push

./cf set-env metrics SPRING_PROFILES_ACTIVE jmx
./cf set-env metrics JBP_CONFIG_CONTAINER_CERTIFICATE_TRUST_STORE '{enabled: true}'
./cf set-env metrics jmx.host 172.16.1.46
./cf set-env metrics jmx.port 44444
./cf set-env metrics jmx.username jmxadmin
./cf set-env metrics jmx.password: jmxadmin
./cf set-env metrics cf.target $API_ENDPOINT
./cf set-env metrics cf.username $USERNAME
./cf set-env metrics cf.password $PASSWORD
./cf set-env metrics mail.protocol smtp
./cf set-env metrics mail.host $SMTP_HOST
./cf set-env metrics mail.port 587
./cf set-env metrics mail.username blackhawk
./cf set-env metrics mail.password Welcome12#
./cf set-env metrics mail.starttls true
./cf set-env metrics mail.auth true

./cf start metrics
