def getUpdateMessage() {
    updateConfig = readProperties file: 'update.properties'

    if (updateConfig['message'] == null
            || VERSION != updateConfig['version'].replace("'", "")) {
        return null
    }

    return updateConfig['message'].replace("'", "")
}

def getVersion() {
    gradleConfig = readProperties file: 'build.gradle'
    return gradleConfig['version'].replace("'", "")
}

def getGroup() {
    gradleConfig = readProperties file: 'build.gradle'
    return gradleConfig['group'].replace("'", "")
}

def getArtifact() {
    gradleConfig = readProperties file: 'settings.gradle'
    return gradleConfig['rootProject.name'].replace("'", "")
}

def testProject() {
    container('gradle') {
        sh 'gradle test'
    }
}

def buildProject() {
    container('gradle') {
        sh 'gradle build'
    }
}

def publishDocker() {
    container('kaniko') {
        withCredentials([file(credentialsId: DOCKER_REGISTRY_CREDENTIALS, variable: 'DOCKER_CONFIG_JSON')]) {
            sh 'cp $DOCKER_CONFIG_JSON /kaniko/.docker/config.json'
            sh '''
              /kaniko/executor \
                --dockerfile=Dockerfile \
                --context=dir://. \
                --destination=${DOCKER_REGISTRY}/byteterm/${ARTIFACT}:${VERSION}
            '''
        }
    }
}

def deploy() {
    container('internal-kubectl') {
        sh '''
          kubectl apply -f k8s/dev-deployment.yaml
          kubectl apply -f k8s/dev-service.yaml
          kubectl apply -f k8s/dev-ingress.yaml
        '''
    }
}

return this