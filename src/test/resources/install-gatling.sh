#!/bin/sh
# Increase the maximum number of open files
sudo ulimit -n 65536
echo "*       soft    nofile  65535" | sudo tee --append /etc/security/limits.conf
echo "*       hard    nofile  65535" | sudo tee --append /etc/security/limits.conf

# Replace Java 7 with 8 and install other requirements
sudo yum remove  --quiet --assumeyes java-1.7.0-openjdk.x86_64
sudo yum install --quiet --assumeyes java-1.8.0-openjdk-devel.x86_64 htop screen

# Install Gatling
  GATLING_VERSION=2.3.0
URL=https://repo1.maven.org/maven2/io/gatling/highcharts/gatling-charts-highcharts-bundle/${GATLING_VERSION}/gatling-charts-highcharts-bundle-${GATLING_VERSION}-bundle.zip
GATLING_ARCHIVE=gatling-charts-highcharts-bundle-${GATLING_VERSION}-bundle.zip

wget --quiet ${URL} -O ${GATLING_ARCHIVE}
unzip -q -o ${GATLING_ARCHIVE}

# Remove example code to reduce Scala compilation time at the beginning of load test
  rm -rf gatling-charts-highcharts-bundle-${GATLING_VERSION}/user-files/simulations/computerdatabase/