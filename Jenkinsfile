pipeline {
    agent any
//     tools {
//         dockerTool 'docker'
//     }
    stages {
        stage('show where are you') {
            steps {
                echo pwd()
            }
        }

        stage('build docker image') {
            steps {
                //sh './gradlew ${moduleName}:jib'
                sh './gradlew ${moduleName}:jibDockerBuild'
            }
        }

        stage('run docker image') {
            steps {
                sh 'cd peacetrue-microservice-docker&&docker-compose --env-file=.env-file.prod up -d ${moduleName}'
//                 sh 'docker-compose --env-file=.env-file.prod down'
//                 sh 'docker-compose --env-file=.env-file.prod up -d ${moduleName}'
            }
        }
    }
}

