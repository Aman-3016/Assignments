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
