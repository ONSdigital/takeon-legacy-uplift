
resource "aws_instance" "Test-Instance" {
  ami           = "${var.amis["take-on-test"]}"
  instance_type = "t2.micro"
  associate_public_ip_address = false
  tags {
      Team = "TakeOn"
      Name = "TakeOn-Test-ec2"
  }
}