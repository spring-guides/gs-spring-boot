pipeline {
    agent any
    
    tools {
        maven 'Maven-3.9'  // Використовуємо налаштований Maven
    }
    
    environment {
        JAR_NAME = 'spring-boot-complete-0.0.1-SNAPSHOT.jar'
        DEPLOY_DIR = '/opt/spring-app'
    }
    
    stages {
        stage('Checkout') {
            steps {
                echo 'Checking out source code...'
                // Git checkout відбувається автоматично
                script {
                    echo "Building branch: ${env.BRANCH_NAME ?: 'main'}"
                    echo "Build number: ${env.BUILD_NUMBER}"
                }
            }
        }
        
        stage('Build') {
            steps {
                echo 'Building Maven project...'
                dir('complete') {
                    sh 'mvn clean install'
                }
            }
            post {
                always {
                    echo 'Archiving artifacts...'
                    archiveArtifacts artifacts: 'complete/target/*.jar', fingerprint: true
                }
                success {
                    echo 'Build completed successfully!'
                }
                failure {
                    echo 'Build failed!'
                }
            }
        }
        
        stage('Test Results') {
            steps {
                echo 'Publishing test results...'
                dir('complete') {
                    publishTestResults testResultsPattern: 'target/surefire-reports/*.xml'
                }
            }
        }
        
        stage('Deploy to EC2') {
            steps {
                echo 'Deploying to EC2...'
                script {
                    // Копіювання JAR файлу та деплой
                    sshPublisher(
                        publishers: [
                            sshPublisherDesc(
                                configName: 'test-pet',
                                transfers: [
                                    sshTransfer(
                                        sourceFiles: 'complete/target/*.jar',
                                        removePrefix: 'complete/target/',
                                        remoteDirectory: '/opt/spring-app/',
                                        execCommand: '''
                                            cd /opt/spring-app
                                            ls -la
                                            echo "JAR file deployed:"
                                            ls -la *.jar
                                            
                                            echo "Starting application..."
                                            ./start-app.sh
                                            
                                            echo "Waiting for application startup..."
                                            sleep 20
                                            
                                            # Перевіряємо процес
                                            if ps aux | grep java | grep -v grep; then
                                                echo "✅ Java process is running"
                                                
                                                # Перевіряємо HTTP відповідь
                                                for i in {1..6}; do
                                                    if curl -f -s http://localhost:8080; then
                                                        echo "✅ Application is responding on port 8080"
                                                        echo "🎉 Deployment successful!"
                                                        break
                                                    else
                                                        echo "⏳ Waiting for HTTP response... attempt $i/6"
                                                        sleep 5
                                                    fi
                                                done
                                            else
                                                echo "❌ Java process not found"
                                                echo "Recent logs:"
                                                tail -10 app.log || echo "No log file found"
                                                exit 1
                                            fi
                                            
                                            echo "=== Deployment completed ==="
                                        '''
                                    )
                                ]
                            )
                        ]
                    )
                }
            }
        }
    }
    
    post {
        always {
            echo 'Pipeline completed'
            // Очищення workspace (опційно)
            // cleanWs()
        }
        success {
            echo '🎉 Pipeline succeeded! Application deployed successfully.'
        }
        failure {
            echo '❌ Pipeline failed! Check the logs for details.'
        }
        unstable {
            echo '⚠️ Pipeline completed but with issues.'
        }
    }
}
