#!/bin/bash
source .env
XML_TMP=./src/main/resources/META-INF/persistence.temp.xml
XML_OUT=./src/main/resources/META-INF/persistence.xml

export DB_URL=$DB_URL
export DB_USR=$DB_USR
export DB_PASS=$DB_PASS

envsubst "`printf '${%s} ' $(sh -c "env|cut -d'=' -f1")`" < $XML_TMP > $XML_OUT