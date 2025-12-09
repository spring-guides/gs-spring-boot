pipeline {
    agent any
    environment {
        TELEGRAM_TOKEN = credentials('telegram_token')
        TELEGRAM_CHAT_ID = credentials('telegram_chat_id')
    }
    tools {
        maven 'MAVEN_HOME'
    }
    stages {
        stage('Pre-build') {
            steps {
                echo 'Preparing to build...'
                sh  'curl -s -X POST https://api.telegram.org/bot$TELEGRAM_TOKEN/sendMessage -d chat_id=$TELEGRAM_CHAT_ID -d text="Start pipline"'
            }
        }
        stage('Build') {
            steps {
                echo 'Building...'
                sh 'mvn clean install'
            }
        }
        stage('Post-build'){
            steps {
                archiveArtifacts artifacts: '**/*.jar'
            }
        }
    }
    post {
        success {
            sh  'curl -s -X POST https://api.telegram.org/bot$TELEGRAM_TOKEN/sendMessage -d chat_id=$TELEGRAM_CHAT_ID -d text="Pipline success"'
            }
        failure {
            sh  'curl -s -X POST https://api.telegram.org/bot$TELEGRAM_TOKEN/sendMessage -d chat_id=$TELEGRAM_CHAT_ID -d text="Pipline failed"'
             }
    }
}
