pipeline {
    agent any
    stages {
        stage('show where are you') {
            steps {
                echo pwd()
            }
        }
        stage('build docker image') {
            steps {
                sh './gradlew ${moduleName}:jibDockerBuild'
            }
        }
    }
}
