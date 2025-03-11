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
                bat "docker build -t=saimen0/selenium ."
            }
        }

        stage('Push Image'){
            steps{
                bat "docker push saimen0/selenium"
            }
        }

    }

}