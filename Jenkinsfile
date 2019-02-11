def server = Artifactory.server 'art-p-01'
def buildInfo = Artifactory.newBuildInfo()

pipeline {	
	agent any
	stages {		
		stage('Build') {		
		  steps {			
			dir('Cucumber-Swagger-Example') {			
				sh 'mvn -B -V -U compile -Dmaven.test.skip'
			}
		  }
		  post {
                success {
                    echo " Stage: ${STAGE_NAME} successful! "
                }
                failure {
                    echo " Stage: ${STAGE_NAME} failed! "
                }
            }
		}
		
		stage('Validate') {		
		  steps {			
			dir('Cucumber-Swagger-Example') {			
				sh 'mvn -B -V -U clean test -Dmaven.test.skip'
			}
		  }
		  post {
				always {
					script {
						def testResults = findFiles(glob: '**/target/surefire-reports/TEST-*.xml')
						for(xml in testResults) {
							touch xml.getPath()
						}
					}
				
					junit(allowEmptyResults: true, testResults: '**/target/surefire-reports/TEST-*.xml')
				}
                success {
                    echo " Stage: ${STAGE_NAME} successful! "
                }
                failure {
                    echo " Stage: ${STAGE_NAME} failed! "
                }
            }
		}
		
		
		stage('Publish') {
			steps {
			dir('Cucumber-Swagger-Example') {
				sh 'mvn -B -V -U package -Dmaven.test.skip'
			}
			script {                  
					def uploadSpec = """{
					"files": [{
					   "pattern": "target/*.jar",
					   "target": "TakeOn-MVN-Release/${buildInfo.name}/${buildInfo.number}/",
					   "props": "type=jar;status=ready"
					}]
					}"""

					dir('Cucumber-Swagger-Example') {			
						server.upload spec: uploadSpec, buildInfo: buildInfo
					}
				}
			}
			post {
                success {
                    echo " Stage: ${STAGE_NAME} successful! "
                }
                failure {
                    echo " Stage: ${STAGE_NAME} failed! "
                }
            }
		}		
	}
}
