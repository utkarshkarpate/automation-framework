FROM bellsoft/liberica-openjdk-alpine:22
RUN apk add curl jq #this we need to check the status of selenium grid
#workspace, this directory will be created when we create the image
WORKDIR /home/selenium-docker

#after we have added the working directory, now all the commands will be run in that directory

# target/docker-resources contain all the details, we need to copy it to our working directory
ADD target/docker-resources /home/selenium-docker
ADD runner.sh runner.sh
#/home/selenium-docker can also be replaced with a ./


#we can run the test now
# while testing if things were in place in image, when tried to run the test suites,
# we got the error because there was no browser installed in the image
#this is the reason we created selenium grid

# to fetch the test ng reports, while we are running the container, we will do volume mapping
# docker run -it -v C:\Users\u1127187\AutomationFrameworkDocker\reports-docker:/home/selenium-docker/test-output uk040193/selenium
# check reports-docker path will be created

#if run the test in docker using the below command, we will get error. Check the error from emailable-report
# command- java "-Dselenium.grid.enabled=true" -cp "libs/*" org.testng.TestNG test-suites/flight-reservation.xml
#we will observe that setDriver method has failed, since connection is refused at localhost:4444
# when we say localhost, it means something is available in that local machine, in this case
#the container. But we have not installed selenium grid in the container so its failing
# instaed of using localhost, we will have to use the ip address
# use ipconfig and get the ip address of local machine, http://127.0.0.1:4444/ui/#

# now run
#java "-Dselenium.grid.enabled=true" -cp "libs/*" org.testng.TestNG test-suites/flight-reservation.xml




#After a lot of debugging using copilot, I was able to run the test successfully in docker using the below command
#java "-Dselenium.grid.enabled=true" "-Dselenium.grid.hub.host=host.docker.internal" -cp "libs/*" org.testng.TestNG test-suites/flight-reservation.xml

#in the course, Vinoth was using the system ip address but it was not working for me, so I used host.docker.internal and it worked. So I am using this in the dockerfile

#environment variables
#HOST, BROWSER_NAME, THREAD_COUNT, TEST_SUITE_NAME all these are env variables


#start the runner.sh
#ENTRYPOINT java "-Dselenium.grid.enabled=true"\
#            "-Dselenium.grid.hub.host=${HOST}"\
#            "-Dbroswer=${BROWSER_NAME}"\
#             -cp "libs/*"\
#            org.testng.TestNG \
#            -threadcount ${THREAD_COUNT} \
#            test-suites/${TEST_SUITE_NAME}.xml


#now re run using the below command using the env variables
#rebuild the image by using the docker build -t=uk040193/selenium-docker . command
#then run the below command
# docker run -e HOST=host.docker.internal -e BROWSER_NAME=chrome -e THREAD_COUNT=3 -e TEST_SUITE_NAME=flight-reservation -v C:\Users\u1127187\AutomationFrameworkDocker\reports-docker:/home/selenium-docker/test-output uk040193/selenium-docker
# once run, the test will start executing since we have given the entry point

ENTRYPOINT sh runner.sh
