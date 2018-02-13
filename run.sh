#!/bin/bash
rm -rf logs.txt
java -Dspring.profiles.active=local -jar target/hibernate-examples-0.0.1-SNAPSHOT.jar

