FROM maven:3.6-jdk-11

COPY ./or-tools_Ubuntu-18.04-64bit_v7.4.7247 /usr/local/google-or-tools

RUN mvn install:install-file -Dfile=/usr/local/google-or-tools/lib/com.google.ortools.jar -DgroupId=com.google.ortools -DartifactId=com.google.ortools -Dversion=7.4.7 -Dpackaging=jar

ENV PATH="/usr/local/google-or-tools/lib:${PATH}"

CMD ["/bin/bash"]