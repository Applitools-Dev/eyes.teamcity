FROM jetbrains/teamcity-server:latest

# Copy project configuration files
COPY projects/* /data/teamcity_server/datadir/config/projects/

# Install plugins
COPY target/applitools-eyes.zip /data/teamcity_server/datadir/plugins/applitools-eyes.zip

# Start TeamCity server
RUN /opt/teamcity/bin/teamcity-server.sh start
