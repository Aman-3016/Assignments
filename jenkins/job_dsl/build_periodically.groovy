job("build_periodically")
{
scm{
  git{
    remote{
    url("https://github.com/Aman-3016/ContinuousIntegration.git")
    }
    branch("*/master")
  }
}
  triggers{
    cron{
    
    spec('*/2 * * * *')
    
    }
  
  }
 steps{

shell(' ls -al ')
}
}
