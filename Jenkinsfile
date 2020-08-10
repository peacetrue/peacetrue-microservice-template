pipeline {
    agent { docker 'docker'}
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
                sh './gradlew ${moduleName}:jibDockerBuild --stacktrace'
            }
        }
    }
}

