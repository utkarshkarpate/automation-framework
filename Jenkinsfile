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
                bat "docker build -t=uk040193/selenium-docker ." //this will build the image
            }
        }
        stage('Push-Image'){
            steps{
                bat "docker push uk040193/selenium-docker"
            }
        }
    }
}