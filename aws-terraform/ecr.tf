resource "aws_ecr_repository" "TakeOn-Repo" {
  name = "take-on-repo"
  tags{
      team = "TakeOn"
  }
}
