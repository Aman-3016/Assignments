
job("build_after_upstream")
{
  scm{
  git{
    remote{
    url("https://github.com/Aman-3016/ContinuousIntegration.git")
    }
    branch("*/master")
  }
}
triggers
  {
  
  upstream('HelloWorld','SUCCESS')
  
  }

  steps{
 shell('echo "Hello World this directory contains   " && ls -al ') 
  
  }




}
