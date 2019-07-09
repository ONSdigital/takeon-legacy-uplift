
resource "aws_instance" "Test-Instance" {
  ami           = "${var.amis["take-on-test"]}"
  instance_type = "t2.micro"
  associate_public_ip_address = false
  subnet_id = "${aws_subnet.private-subnet.id}"
  key_name = "instance-test"
  tags {
      Team = "TakeOn"
      Name = "TakeOn-Test-ec2"
  }
}
