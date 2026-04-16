pipeline {
    agent any
    tools {
            jdk 'JDK21'
    }

    environment {
        PATH = "/opt/homebrew/bin:/usr/local/bin:${env.PATH}"
        DOCKERHUB_REPO = 'hyoneekim/shopping-cart'
        DOCKER_IMAGE_TAG = "latest"
        DOCKERHUB_CREDENTIALS_ID = 'docker_hub'
        SONARQUBE_SERVER = 'SonarQubeServer'
    }

    stages {
        stage('check') {
            steps {
                git branch: 'main',
                    url: 'https://github.com/hyoneekim/SEP2_w3_assignment.git'
            }
        }

        stage('build') {
            steps {
                sh 'mvn clean verify'
            }
        }


        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv('SonarQubeServer') {
                    withCredentials([string(credentialsId: 'SONAR_TOKEN', variable: 'SONAR_TOKEN')]) {
                        sh """
                            ${tool 'SonarScanner'}/bin/sonar-scanner \
                            -Dsonar.projectKey=shopping-cart \
                            -Dsonar.sources=src \
                            -Dsonar.projectName=shopping-cart \
                            -Dsonar.host.url=http://localhost:9000 \
                            -Dsonar.login=${SONAR_TOKEN} \
                            -Dsonar.java.binaries=${WORKSPACE}/target/classes
                        """
                    }
                }
            }
        }
        stage('Build Docker image') {
            steps {
                sh '''
                    docker build \
                        -t ${DOCKERHUB_REPO}:${DOCKER_IMAGE_TAG} \
                        .
                '''
            }
        }

        stage('Push Docker Image') {
            steps {
                withCredentials([usernamePassword(
                    credentialsId: "${DOCKERHUB_CREDENTIALS_ID}",
                    usernameVariable: 'DOCKER_USER',
                    passwordVariable: 'DOCKER_PASS'
                )]) {
                    sh '''
                        echo $DOCKER_PASS | docker login -u $DOCKER_USER --password-stdin
                        docker push ${DOCKERHUB_REPO}:${DOCKER_IMAGE_TAG}
                    '''
                }
            }
        }
    }
}