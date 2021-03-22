FROM newimage
COPY src  home/seleniumframework/src
COPY extent-test-output  home/seleniumframework/extent-test-output
COPY pom.xml	home/seleniumframework/pom.xml
COPY testng.xml	home/seleniumframework/testng.xml
ENV DISPLAY=:99.0
WORKDIR home/seleniumframework
ENTRYPOINT mvn clean test