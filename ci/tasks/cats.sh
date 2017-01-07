#!/usr/bin/env bash
set -e

RESPONSE_CODE=`curl -I $APP_URL 2>/dev/null | head -n 1 | cut -d$' ' -f2`
if [ "$RESPONSE_CODE" != "200" ]
then
  echo "Expect status code from $APP_URL as 200, but got $RESPONSE_CODE"
  exit 1
fi
