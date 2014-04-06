#!/bin/sh
# Try install this, when you can't build. 
# sudo apt-get install ia32-libs
mvn clean package assembly:single -Dproject.version=`date +%Y%m%d`
