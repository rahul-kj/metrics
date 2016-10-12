#!/usr/bin/env bash
set -e

get_code="curl -I $APP_URL 2>/dev/null | head -n 1 | cut -d$' ' -f2"
status_code=`eval $get_code`
if [ "$status_code" != "200" ]
then
  echo "Expect status code from $APP_URL as 200, but got $status_code"
  exit 1
fi
