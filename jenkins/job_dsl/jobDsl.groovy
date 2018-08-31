job("HelloWorld")
{
  steps{
    shell('echo "Hello WORLD "')
  }
}

job("HelloPerson")
{ parameters{
     choiceParam('Salutation',['Mr.','Mrs.'],'Your Salutation')
     stringParam('Name','','Your Name')
  steps
  {
    shell(' echo "hi ${Salutation} ${Name}" ')
  }
}
}

job("git_contents")
{ description("job clone repository and list their directory")
  scm{
  git{
    remote{
    url("https://github.com/Aman-3016/ContinuousIntegration.git")
    }
    branch("*/master")
  }
}
 steps
 {

shell(' ls -al ')
}
}

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

job("poll_scm")
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
    
    scm('*/2 * * * *')
        
    }

  
 steps{

shell(' ls -al ')
}
}


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
job("build_Downstream_job")
{
 parameters
    {
     choiceParam('Salutation',['Mr.','Mrs.'],'Your Salutation')
     stringParam('Name','','Your Name')
    }

publishers
  {
    downstreamParameterized{
      trigger("HelloPerson")
      {condition('SUCCESS')
       parameters{
       currentBuild()
       }
      }
    }
  }

    }
nestedView('ninja-jobs') {
    views {
        listView('simple-jobs') {
           jobs {
             names('helloWorld', 'HelloPerson','git_contents', 'build_periodically','poll_scm')
             }
          columns {
                status()
                weather()
                name()
                lastSuccess()
                lastFailure()
            }
        }
      listView('complex-jobs') {

        jobs{

        names('build_after_upstream', 'build_Downstream_job')

        }
        columns {
           status()
                weather()
                name()
                lastSuccess()
                lastFailure()
            }

      }
    }
}
