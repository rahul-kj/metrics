#!/usr/bin/env bash
set -x

target="cf api $API_ENDPOINT --skip-ssl-validation"
#echo $target
eval $target

login="cf auth $USERNAME $PASSWORD"
#echo $login
eval $login

echo "Target Org and Space"
org_space="cf target -o $ORG -s $SPACE"
eval $org_space

echo "push the app"
push="cf push metrics -n $HOST -p metrics-release/metrics-*.jar -m 512m --no-start"
#echo $push
eval $push

cf set-env SPRING_PROFILES_ACTIVE jmx
cf set-env JBP_CONFIG_CONTAINER_CERTIFICATE_TRUST_STORE '{enabled: true}'
cf set-env jmx.host 172.16.1.46
cf set-env jmx.port 44444
cf set-env jmx.username jmxadmin
cf set-env jmx.password: jmxadmin
cf set-env cf.target $API_ENDPOINT
cf set-env cf.username $USERNAME
cf set-env cf.password $PASSWORD
cf set-env mail.protocol smtp
cf set-env mail.host smtp.sendgrid.com
cf set-env mail.port 587
cf set-env mail.username blackhawk
cf set-env mail.password Welcome12#
cf set-env mail.starttls true
cf set-env mail.auth true

cf start metrics
