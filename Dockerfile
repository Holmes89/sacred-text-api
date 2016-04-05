# Pull base image
From tomcat:8-jre8

# Maintainer
MAINTAINER "Joel Holmes <Holmes89@gmail.com>"

# Copy to images tomcat path
COPY target/sacred-text-api-*.war /usr/local/tomcat/webapps/sacred-text-api.war
