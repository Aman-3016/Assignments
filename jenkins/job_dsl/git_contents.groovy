
job("git_contents")
{scm{
  git{
    remote{
    url("https://github.com/Aman-3016/ContinuousIntegration.git")
    }
    branch("*/master")
  }
}steps{

shell(' ls -al ')
}
}
