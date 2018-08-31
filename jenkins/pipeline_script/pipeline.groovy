pipeline 
{
  agent any
  parameters {
  string defaultValue: 'master', description: '', name: 'BRANCH', trim: false
}

  stages{

    stage('SCM')                    //it will pull the code from github
 {   
     steps
     {
      git branch: params.BRANCH ,url:'https://github.com/Aman-3016/ContinuousIntegration.git'
     
      sh '''
      cd Spring3HibernateApp/;
      git branch | awk '{print $2}';
      '''
     }
     
 }

  stage('Code_Quality') 
    //it will check the quality of the code  using findbugs and checkstyle 
 { steps 
   {sh '''
   cd Spring3HibernateApp/;
   mvn clean compile checkstyle:checkstyle;
   mvn findbugs:findbugs;
   '''
   checkstyle canComputeNew: false, defaultEncoding: '', healthy: '', pattern: ' **/checkstyle-result.xml', unHealthy: '';
   findbugs canComputeNew: false, defaultEncoding: '', excludePattern: '', healthy: '', includePattern: '', pattern: ' **/findbugs.xml', unHealthy: ''
   
   } 
     
 }
 stage('Code_Coverage')
 {
     steps{
     sh''' 
     cd Spring3HibernateApp/;
     mvn cobertura:cobertura;
'''
cobertura autoUpdateHealth: false, autoUpdateStability: false, coberturaReportFile: '**/target/site/cobertura/coverage.xml', conditionalCoverageTargets: '70, 0, 0', failUnhealthy: false, failUnstable: false, lineCoverageTargets: '80, 0, 0', maxNumberOfBuilds: 0, methodCoverageTargets: '80, 0, 0', onlyStable: false, sourceEncoding: 'ASCII', zoomCoverageChart: false//it will check the coverage of the code using cobertura
 }
     
 }
stage('Archieve')
{steps{
    sh '''
   
    cd Spring3HibernateApp/;
    mvn package''';         //it will generate the artifact
}    
    
}
stage('deploy')
{
    when {
        expression{ params.BRANCH == 'master'}
        }
        
    

    steps
    { sh '''
      ssh ec2-user@52.15.182.135 sudo chown -R ec2-user:ec2-user /var/lib/tomcat8
      ssh ec2-user@52.15.182.135 sudo rm -rf /var/lib/tomcat8/webapps/*;
      ssh ec2-user@52.15.182.135 sudo service tomcat8 stop;
            

      scp Spring3HibernateApp/target/Spring3HibernateApp.war ec2-user@52.15.182.135:/var/lib/tomcat8/webapps/;
      ssh ec2-user@52.15.182.135 sudo chown -R root:tomcat /var/lib/tomcat8
      ssh ec2-user@52.15.182.135 sudo service tomcat8 start; 
      '''
    //build 'SpringHibernate_deploy';   //it will deploy the artifacts in server
    }
    

    
}
    

}}

