pipeline {
    agent any
    stages {
        stage('SCM') {
            steps {
                echo 'Checkout https://github.com/blitznihar/springboot-camel-restlet-sql.git'
                git url: 'https://github.com/blitznihar/springboot-camel-restlet-sql.git'
            }
        }
        stage('Build') {
            steps {
                echo 'Clean Build'
                sh '/opt/apache-maven-3.6.0/bin/mvn clean install'
            }
        }
        stage('Test') {
            steps {
                echo 'Testing'
                sh '/opt/apache-maven-3.6.0/bin/mvn test'
            }
        }
        stage('JaCoCo') {
            steps {
                echo 'Code Coverage'
                jacoco()
            }
        }
        stage('Sonar') {
            steps {
                echo 'Sonar Scanner'
               	//def scannerHome = tool 'SonarQube Scanner 3.0'
			    withSonarQubeEnv('sonar') {
			    	sh '/opt/apache-maven-3.6.0/bin/mvn sonar:sonar'
			    }
            }
        }
        stage('Package') {
            steps {
                echo 'Packaging'
                sh '/opt/apache-maven-3.6.0/bin/mvn package -DskipTests'
            }
        }
        stage('Deploy') {
	
            steps {
		    echo '## WAITING ON APPROVAL FOR DEPLOYMENT ##'
		    input(message: 'Deploy to Stage', ok: 'Yes, let\'s do it!')
                echo '## TODO DEPLOYMENT ##'
            }
        }
        stage ('Distribute binaries') { 
             steps {
                script {
                    
                    def SERVER_ID = 'artifactory' 
                    def server = Artifactory.server SERVER_ID
                    def uploadSpec = 
                    """
                    {
                    "files": [
                        {
                            "pattern": "target/(*).jar",
                            "target": "libs-snapshot-local/com/blitznihar/{1}/"
                        }
                    ]
                    }
                    """
                    def buildInfo = Artifactory.newBuildInfo()
                    buildInfo.env.capture = true 
                    buildInfo=server.upload(uploadSpec) 
                    server.publishBuildInfo(buildInfo) 

                }

             }
        }
    }
    
    post {
        always {
            echo 'JENKINS PIPELINE'
            junit 'target/surefire-reports/*.xml'
        }
        success {
            echo 'JENKINS PIPELINE SUCCESSFUL'
        }
        failure {
            echo 'JENKINS PIPELINE FAILED'
        }
        unstable {
            echo 'JENKINS PIPELINE WAS MARKED AS UNSTABLE'
        }
        changed {
            echo 'JENKINS PIPELINE STATUS HAS CHANGED SINCE LAST EXECUTION'
        }
    }
}