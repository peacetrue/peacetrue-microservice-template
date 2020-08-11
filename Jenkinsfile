pipeline {
    agent any
    tools {
        dockerTool 'docker'
    }
    stages {
        stage('show where are you') {
            steps {
                echo pwd()
            }
        }

        stage('build docker image') {
            steps {
                //sh './gradlew ${moduleName}:jib'
                //在本地 docker 构建镜像
                //sh 'docker pull openjdk:11.0.8-jre'
                sh './gradlew ${moduleName}:jibDockerBuild --stacktrace'
            }
        }
    }
}

