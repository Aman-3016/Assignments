
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
    
  
     
    
