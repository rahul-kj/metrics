#!/usr/bin/env bash
set -e

cd metrics-repo

mvn -v
mvn clean install
