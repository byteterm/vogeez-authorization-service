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
        DISCORD_AVATAR = ' '
        DISCORD_USERNAME = ' '
        DISCORD_WEBHOOK = credentials('byteterm-discord-webhook')
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

                    DOCKER_REGISTRY = "docker.byteterm.de/vogeez"
                    DOCKER_REGISTRY_CREDENTIALS = 'byteterm-nexus-kaniko'

                    DISCORD_TITLE = "${ARTIFACT}"
                    DISCORD_FOOTER = "Version - ${VERSION}"
                    DISCORD_USERNAME = 'Vogeez'
                    DISCORD_AVATAR = 'https://th.bing.com/th/id/OIP.G6GDfaclhjv0irgMY5SQWgHaHE?pid=ImgDet&rs=1'
                }
            }
        }

        // Stage: Test
        stage('Test') {
            steps {
                script {
                    gv.testProject()
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

                    def discordMessage = gv.getUpdateMessage();
                    if (discordMessage != null) {
                        DISCORD_RESULT = "SUCCESS"
                        discordSend webhookURL: DISCORD_WEBHOOK, description: discordMessage,customAvatarUrl: DISCORD_AVATAR, customUsername: DISCORD_USERNAME, result: "SUCCESS", footer: DISCORD_FOOTER, thumbnail: DISCORD_THUMBNAIL,title: DISCORD_TITLE, image: DISCORD_IMAGE, link: DISCORD_LINK
                    }
                }
            }
        }
    }
}