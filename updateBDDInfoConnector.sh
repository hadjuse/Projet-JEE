#!/bin/bash
source .env
XML_TMP=./src/main/resources/META-INF/persistence.temp.xml
XML_OUT=./src/main/resources/META-INF/persistence.xml

export DB_URL=$MYSQL_URL
export DB_USR=$MYSQL_USER
export DB_PASS=$MYSQL_PASSWORD

envsubst "`printf '${%s} ' $(sh -c "env|cut -d'=' -f1")`" < $XML_TMP > $XML_OUT