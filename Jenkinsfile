pipeline{

    agent any

    stages{

        stage('Build jar'){
            steps{
                bat "mvn clean package -DskipTests"
            }

        }

        stage('Build Image'){
            steps{
                bat "docker build -t=saimen0/selenium:latest ."
            }
        }

        stage('Push Image'){
            environment{
                DOCKER_HUB = credentials('dockerhub-creds')
            }
            steps{
                bat 'docker login -u %DOCKER_HUB_USR% -p %DOCKER_HUB_PSW%'
                bat "docker push saimen0/selenium:latest"
                // bat "docker tag saimen0/selenium:latest saimen0/selenium:${env.BUILD_NUMBER}"
                // bat "docker push saimen0/selenium:${env.BUILD_NUMBER}"
            }
        }

    }

    post {
        always{
            bat "docker logout"
        }
    }

}