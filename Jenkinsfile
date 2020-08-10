/*
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
                //sh './gradlew ${moduleName}:jib'
                //在本地 docker 构建镜像
                sh './gradlew ${moduleName}:jibDockerBuild --stacktrace'
            }
        }
    }
}
 */

node {
  stage 'show where are you'
  echo "pwd()"

  stage 'build docker image'
  sh './gradlew ${moduleName}:jibDockerBuild --stacktrace'
}
