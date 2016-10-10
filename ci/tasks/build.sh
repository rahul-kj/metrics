#!/usr/bin/env bash
set -e

cd metrics-repo
mvn -v

sudo apt-get install -y maven default-jdk

mvn clean install
