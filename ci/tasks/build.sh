#!/usr/bin/env bash
set -e

cd metrics-repo

sudo apt-get update
sudo apt-get install -y maven default-jdk

mvn -v
mvn clean install
