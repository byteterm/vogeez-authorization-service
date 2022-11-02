def gv

pipeline {
  agent {
    kubernetes {
      yaml '''
        apiVersion: v1
        kind: Pod
        spec:
          serviceAccountName: internal-kubectl
          containers:
          - name: gradle 
            image: gradle:7.5.1-jdk17
            command:
            - cat
            tty: true
            privileged: true
          - name: kaniko 
            image: gcr.io/kaniko-project/executor:debug
            command:
            - cat
            tty: true
            privileged: true
          - name: internal-kubectl 
            image: trstringer/internal-kubectl:latest
            command:
            - cat
            tty: true
            privileged: true
        '''
    }
  }

    environment {
        // Build Variables
        VERSION = ''
        GROUP = ''
        ARTIFACT = ''

        // Docker Variables
        DOCKER_REGISTRY = ''
        DOCKER_REGISTRY_CREDENTIALS = ''

        // Discord
        DISCORD_PICTURE = ' '
        DISCORD_FOOTER = ' '
        DISCORD_IMAGE = ' '
        DISCORD_LINK = ' '
        DISCORD_RESULT = ' '
        DISCORD_THUMBNAIL = ' '
        DISCORD_TITLE = ' '
        DISCORD_WEBHOOK = ' '
    }

    stages {
        // Stage: Init
        stage('Init') {
            steps {
                script {
                    gv = load "jenkins.groovy"
                    VERSION = gv.getVersion()
                    GROUP = gv.getGroup()
                    ARTIFACT = gv.getArtifact()

                    DOCKER_REGISTRY = 'docker.byteterm.de/vogeez'
                    DOCKER_REGISTRY_CREDENTIALS = 'byteterm-nexus-kaniko'

                    DISCORD_TITLE = "${ARTIFACT}"
                    DISCORD_FOOTER = "Version - ${VERSION}"

                    echo "VERSION: ${VERSION}"
                    echo "GROUP: ${GROUP}"
                    echo "ARTIFACT: ${ARTIFACT}"
                    echo "DOCKER_REGISTRY: ${DOCKER_REGISTRY}"
                }
            }
        }

        // Stage: Test
        stage('Test') {
            steps {
                script {
                    echo "Test"
                    //gv.testProject()
                }
            }
        }

        // Stage: Build
        stage('Build') {
            steps {
                script {
                    gv.buildProject()
                }
            }
        }

        // Stage: Publish DockerImage
        stage('Publish DockerImage') {
            // Publish only if it is a main branch
            when {
                expression {
                    BRANCH_NAME.startsWith('main')
                }
            }

            steps {
                script {
                    gv.publishDocker()
                }
            }
        }

        // Stage: Deploy to Kubernetes
        stage('Deploy to Kubernetes') {
            // Deploy only if it is a main branch
            when {
                expression {
                    BRANCH_NAME.startsWith('main')
                }
            }

            steps {
                script {
                    gv.deploy()
                }
            }
        }
    }
}