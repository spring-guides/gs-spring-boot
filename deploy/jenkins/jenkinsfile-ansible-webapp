node {
  stage('Init') {
    checkout scm
  }

  stage('Build') {
    sh '''
        cd complete
        mvn clean package
        mv target/*.war ROOT.war
    '''
  }

  stage('Deploy') {
    withCredentials([azureServicePrincipal(credentialsId: env.AZURE_CRED_ID,
                                    subscriptionIdVariable: 'AZURE_SUBSCRIPTION_ID',
                                    clientIdVariable: 'AZURE_CLIENT_ID',
                                    clientSecretVariable: 'AZURE_SECRET',
                                    tenantIdVariable: 'AZURE_TENANT')]) {
                                      
      ansiblePlaybook installation: 'ansible',
      playbook: 'complete/deploy/ansible/webapp.yml'
    }

    azureWebAppPublish azureCredentialsId: env.AZURE_CRED_ID,
                       resourceGroup: env.RES_GROUP, 
                       appName: env.WEB_APP_NAME, 
                       filePath: "**/ROOT.war"
  }
}
