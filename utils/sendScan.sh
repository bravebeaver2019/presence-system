#!/usr/bin/env bash

echo use this tool to send scan messages in json format
echo example usage:: ./sendScan.sh wrong.json
echo example usage:: ./sendScan.sh scan.json

curl -XPOST http://localhost:8080/scan/read -d@$1 -H "Content-Type: application/json"
