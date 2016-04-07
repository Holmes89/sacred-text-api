# Pull base image
From tomcat:8-jre8

# Maintainer
MAINTAINER "Joel Holmes <Holmes89@gmail.com>"

# Copy to images tomcat path
COPY sacred-text-api/target/sacred-text-api-*.war /usr/local/tomcat/webapps/sacred-text-api.war
COPY religious-calendar-api/target/religious-calendar-api-*.war /usr/local/tomcat/webapps/religious-calendar-api.war
