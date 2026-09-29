/*just like docker file, jenkins file will be part of the main project
aim is that jenkins will checkout the project from github and keep it in node workspace
whatever job name we have given it will go under that.
*/

pipeline {
    agent any

    stages(){
        stage('Build-jar'){
            steps{
                bat "mvn clean package -DskipTests" //build the jar
            }
        }
        stage('Build-Docker-Image'){
            steps{
                bat "docker build -t=uk040193/selenium-docker:latest ." //this will build the image
                //we are giving tag so that it picks up the latest changes
            }
        }
        stage('Push-Image'){
            environment {
                DOCKER_HUB = credentials("docker-hub")
            }
            /*Using the above nev block, we can specify our docker hub credentials
            For me it worked without giving credentials
            If it does not work, create global credentials in jenkins and use as below in steps*/
            steps{
                bat 'docker login -u ${DOCKER_HUB_USR} -p ${DOCKER_HUB_PSW}'
                //the above way of login will give warning in jenkins console, to fix it use below code
                //bat 'echo ${DOCKER_HUB_PSW} | docker login -u ${DOCKER_HUB_USR} --password-stdin'
                //the above comment might not work in windows
                bat "docker push uk040193/selenium-docker:latest"
                bat "docker tag uk040193/selenium-docker:latest uk040193/selenium-docker:${env.BUILD_NUMBER}"
                bat "docker push uk040193/selenium-docker:${env.BUILD_NUMBER}"
            // here we are replacing the latest tag and giving the tag current build number
            }
        }
    }
    post {
        always {
            bat "docker logout"
        }
    }
}


//If the node we are using does not have maven installed, we can use the approach of using docker container as a node or agent or slave
//the jenkinsfile for this approach is stored in below location