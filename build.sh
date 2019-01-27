#!/bin/sh
# Try install this, when you can't build. 
# sudo apt-get install ia32-libs
./mvnw clean package assembly:single -Dproject.version=0.`date +%Y%m%d`
