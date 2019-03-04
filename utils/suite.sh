#!/usr/bin/env bash

curl -XPOST http://localhost:8080/scan/read -d@login.json -H "Content-Type: application/json"
curl -XPOST http://localhost:8080/scan/read -d@logout.json -H "Content-Type: application/json"
curl -XPOST http://localhost:8080/scan/read -d@login2.json -H "Content-Type: application/json"
curl -XPOST http://localhost:8080/scan/read -d@logout2.json -H "Content-Type: application/json"

echo "## daily presence"
curl -XGET "http://localhost:8080/reporting/employee?employeeId=4d8276c6732e92fd37fe6a3f9f58284a&period=day&date=2011-11-02"
echo ""
echo "## weekly presence"
curl -XGET "http://localhost:8080/reporting/employee?employeeId=4d8276c6732e92fd37fe6a3f9f58284a&period=week&date=2011-11-02"
echo ""
echo "## no presence"
curl -XGET "http://localhost:8080/reporting/employee?employeeId=4d8276c6732e92fd37fe6a3f9f58284a&period=day&date=2013-11-02"