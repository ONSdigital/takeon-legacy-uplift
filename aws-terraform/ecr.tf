resource "aws_ecr_repository" "TakeOn-Ui-Repo" {
  name = "take-on-ui"
  tags{
      team = "TakeOn"
  }
}

resource "aws_ecr_repository" "TakeOn-Bl-Repo" {
  name = "take-on-bl"
  tags{
      team = "TakeOn"
  }
}

resource "aws_ecr_repository" "TakeOn-Pl-Repo" {
  name = "take-on-pl"
  tags{
      team = "TakeOn"
  }
}

resource "aws_ecr_repository" "TakeOn-Vpl-Repo" {
  name = "take-on-vpl"
  tags{
      team = "TakeOn"
  }
}