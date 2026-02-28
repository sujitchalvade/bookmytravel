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
                    cp target/*.jar target/bookmyplan-1.0.3.jar
                '''
                echo 'WAR Artifact Created Successfully!'
            }
        }
       stage('Build and Tag Docker Image'){
            steps {
                echo 'Building Docker Image with Tags...'
                sh "docker build -t sujitchalvade/bookmytravel:latest -t makemytrip:latest ."
                echo 'Docker Image Build Completed!'
            }
        }
/*       stage('Scan Docker Image'){
            steps {
                echo 'Scanning Docker Image with Trivy...'
                sh 'trivy image ${DOCKER_IMAGE}:latest || echo "Scan Failed - Proceeding with Caution"'
                echo 'Docker Image Scanning Completed!'
            }
        }*/
       stage('Upload Docker Image to AWS ECR'){
        steps {
                        script {
                            withDockerRegistry([credentialsId: 'ecr:ap-south-1:ecr-credentials', url: "030796673008.dkr.ecr.ap-south-1.amazonaws.com"]) {
                                echo 'Tagging and Pushing Docker Image to ECR...'
                                sh '''
                                    docker images
                                    docker tag bookmytravel:latest 030796673008.dkr.ecr.ap-south-1.amazonaws.com/bookmytravel:latest
                                    docker push 030796673008.dkr.ecr.ap-south-1.amazonaws.com/bookmytravel:latest
                                '''
                                echo 'Docker Image Pushed to Amazon ECR Successfully!'
                            }
                        }
                    }
        }
/*       stage('Upload Docker Image to Docker Hub'){
        }*/
       stage('Delete Docker Image from Jenkins'){
       steps {
                       echo 'Cleaning up Docker images...'
                       sh '''
                         docker images bookmytravel -q | xargs -r docker rmi -f
                         docker images sujitchalvade/bookmytravel -q | xargs -r docker rmi -f
                         docker images 030796673008.dkr.ecr.ap-south-1.amazonaws.com/bookmytravel -q | xargs -r docker rmi -f

                         docker image prune -f
                       '''
                       echo 'Docker cleanup completed!'
        }
    }
}