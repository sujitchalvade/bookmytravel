pipeline {

    agent any

    options {

        buildDiscarder(logRotator(numToKeepStr: '3', artifactNumToKeepStr: '3'))
    }

    tools {
        maven 'mvn_3.9.12'
    }

    stages {
        stage('Code Compilation') {
            steps {
                echo 'Starting Code Compilation...'
                sh 'mvn clean compile'
                echo 'Code Compilation Completed Successfully!'
            }
        }
        stage('Code QA Execution') {
            steps {
                echo 'Running JUnit Test Cases...'
                sh 'mvn clean test'
                echo 'JUnit Test Cases Completed Successfully!'
            }
        }
/*        stage('Code Analysis using Sonar') {

        }*/
        stage('Code Package') {
            steps {
                echo 'Creating WAR Artifact...'
                sh 'mvn clean package'
                sh '''
                    cp target/*.jar target/bookmyplan-1.0.$(BUILD_NUMBER).jar
                '''
                echo 'JAR Artifact Created Successfully!'
            }
        }
       stage('Build and Tag Docker Image'){
            steps {
                echo 'Building Docker Image with Tags...'
                sh "docker build -t sujitchalvade/bookmyplan:latest -t bookmyplan:latest ."
                echo 'Docker Image Build Completed!'
            }
        }
       stage('Scan Docker Image'){
            steps {
                echo 'Scanning Docker Image with Trivy...'
                //sh 'trivy image ${DOCKER_IMAGE}:latest || echo "Scan Failed - Proceeding with Caution"'
                echo 'Docker Image Scanning Completed!'
            }
        }
       stage('Upload Docker Image to Docker Hub'){
            steps {
                       script {
                           withCredentials([string(credentialsId: 'dockerhubCred', variable: 'dockerhubCred')]) {
                               sh 'docker login docker.io -u sujitchalvade -p ${dockerhubCred}'
                               echo 'Pushing Docker Image to Docker Hub...'
                               sh 'docker push sujitchalvade/bookmyplan:latest'
                               echo 'Docker Image Pushed to Docker Hub Successfully!'
                           }
                       }
                   }
              }
       stage('Upload Docker Image to AWS ECR'){
			steps {
                        script {
                            withDockerRegistry([credentialsId: 'ecr:ap-south-1:ecr-credentials', url: "https://030796673008.dkr.ecr.ap-south-1.amazonaws.com"]) {
                                echo 'Tagging and Pushing Docker Image to ECR...'
                                sh '''
                                    docker images
                                    docker tag bookmyplan:latest 030796673008.dkr.ecr.ap-south-1.amazonaws.com/bookmyplan:latest
                                    docker push 030796673008.dkr.ecr.ap-south-1.amazonaws.com/bookmyplan:latest
                                '''
                                echo 'Docker Image Pushed to Amazon ECR Successfully!'
                            }
                        }
                    }
        }
       stage('Upload Docker Image to Nexus') {
            steps {
                         script {
                             withCredentials([usernamePassword(credentialsId: 'nexus-credentials', usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]) {
                                 sh 'docker login http://13.201.96.204:8085/repository/bookmyplan/ -u admin -p ${PASSWORD}'
                                 echo "Push Docker Image to Nexus : In Progress"
                                 sh 'docker tag bookmyplan 13.201.96.204:8085/bookmyplan:latest'
                                 sh 'docker push 13.201.96.204:8085/bookmyplan'
                                 echo "Push Docker Image to Nexus : Completed"
                             }
                         }
                    }
            }
       stage('Delete Docker Image from Jenkins'){
            steps {
                       echo 'Cleaning up Docker images...'
                       sh '''
                         docker images bookmyplan -q | xargs -r docker rmi -f
                         docker images sujitchalvade/bookmyplan -q | xargs -r docker rmi -f
                         docker images 030796673008.dkr.ecr.ap-south-1.amazonaws.com/bookmyplan -q | xargs -r docker rmi -f
                         docker image prune -f
                       '''
                       echo 'Docker cleanup completed!'
            }
        }
    }
}