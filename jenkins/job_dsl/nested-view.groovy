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
