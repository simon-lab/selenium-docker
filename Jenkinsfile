pipeline{

    agent any

    stages{

        stage('Build jar'){
            steps{
                sh "mvn clean package -DskipTests"
            }

        }

        stage('Build Image'){
            steps{
                sh "docker build -t=saimen0/selenium ."
            }
        }

        stage('Push Image'){
            steps{
                sh "docker push saimen0/selenium"
            }
        }

    }

}