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

cf set-env metrics SPRING_PROFILES_ACTIVE jmx
cf set-env metrics JBP_CONFIG_CONTAINER_CERTIFICATE_TRUST_STORE '{enabled: true}'
cf set-env metrics jmx.host $JMX_HOST
cf set-env metrics jmx.port $JMX_PORT
cf set-env metrics jmx.username $JMX_USER
cf set-env metrics jmx.password $JMX_PASSWORD
cf set-env metrics cf.target $API_ENDPOINT
cf set-env metrics cf.username $USERNAME
cf set-env metrics cf.password $PASSWORD
cf set-env metrics mail.protocol smtp
cf set-env metrics mail.host $SMTP_HOST
cf set-env metrics mail.port $SMTP_PORT
cf set-env metrics mail.username $SMTP_USERNAME
cf set-env metrics mail.password $SMTP_PASSWORD
cf set-env metrics mail.starttls true
cf set-env metrics mail.auth true

cf start metrics
