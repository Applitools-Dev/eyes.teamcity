FROM jetbrains/teamcity-server:latest

# Copy data directory
COPY docker/datadir/* /data/teamcity_server/datadir/

# Install plugins
COPY docker/target/applitools-eyes.zip /data/teamcity_server/datadir/plugins/applitools-eyes.zip

# Start TeamCity server
CMD ["/opt/teamcity/bin/teamcity-server.sh", "run"]