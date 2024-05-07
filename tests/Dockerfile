FROM jetbrains/teamcity-server:latest

# Copy project configuration files
COPY docker/projects/* /data/teamcity_server/datadir/config/projects/

# Install plugins
COPY docker/target/applitools-eyes.zip /data/teamcity_server/datadir/plugins/applitools-eyes.zip

# Copy preconfigured database.properties file
COPY docker/database.properties /data/teamcity_server/datadir/config/database.properties

# Copy prepopulated HSQLDB database files
COPY docker/system/*.* /data/teamcity_server/datadir/system/

# Start TeamCity server
CMD ["/opt/teamcity/bin/teamcity-server.sh", "run"]