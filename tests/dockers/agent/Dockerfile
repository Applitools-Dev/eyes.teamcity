# Start from the TeamCity minimal agent image
FROM jetbrains/teamcity-minimal-agent:2024.03.1-linux

# Install curl and unzip (required to download and extract Node.js)
USER root
RUN apt-get update && apt-get install -y curl unzip

# Download and install Node.js (includes npm)
RUN curl -fsSL https://nodejs.org/dist/v14.18.1/node-v14.18.1-linux-x64.tar.xz | tar -xJv
RUN mv node-v14.18.1-linux-x64 /usr/local/lib/nodejs
ENV PATH="/usr/local/lib/nodejs/bin:${PATH}"

# Verify that Node.js and NPM were installed correctly
RUN node -v
RUN npm -v

# Set the working directory to the TeamCity agent's work directory
WORKDIR /opt/buildagent/work