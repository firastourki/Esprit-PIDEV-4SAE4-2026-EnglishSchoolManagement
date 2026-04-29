pipeline {
    agent any

    stages {

        stage('Checkout') {
            steps {
                echo 'Code déjà cloné'
            }
        }

        stage('Build assessment-service') {
            steps {
                dir('assessment-service') {
                    sh 'mvn clean package -DskipTests'
                }
            }
        }

        stage('Test assessment-service') {
            steps {
                dir('assessment-service') {
                    sh 'mvn test'
                }
            }
        }

        stage('Build resources-service') {
            steps {
                dir('resources-service') {
                    sh 'mvn clean package -DskipTests'
                }
            }
        }

        stage('Test resources-service') {
            steps {
                dir('resources-service') {
                    sh 'mvn test'
                }
            }
        }

    }

    post {
        success {
            echo '✅ Build et tests réussis !'
        }
        failure {
            echo '❌ Échec du pipeline.'
        }
    }
}

