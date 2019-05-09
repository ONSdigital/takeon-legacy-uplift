resource "aws_vpc" "Take-On-VPC"{
	cidr_block = "${var.vpc_cidr}"
	enable_dns_hostnames = true
	tags {
		Name = "Take-On-VPC"
		Team = "TakeOn"
	}
}

resource "aws_subnet" "public-subnet"{
	vpc_id = "${aws_vpc.Take-On-VPC.id}"
	cidr_block = "${var.takeon_public_subnet}"
	availability_zone = "${var.aws_zone}"
	
	tags{
		Name = "Take-On-Public"
		Team = "TakeOn"
	}
}

resource "aws_subnet" "private-subnet"{
	vpc_id = "${aws_vpc.Take-On-VPC.id}"
	cidr_block = "${var.takeon_private_subnet}"
	availability_zone = "${var.aws_zone}"
	
	tags{
		Name = "Take-On-Private"
		Team = "TakeOn"
	}
}

resource "aws_subnet" "private-subnet-backup"{
	vpc_id = "${aws_vpc.Take-On-VPC.id}"
	cidr_block = "${var.takeon_private_subnet_backup}"
	availability_zone = "eu-west-2a"
	
	tags{
		Name = "Take-On-Private-2a"
		Team = "TakeOn"
	}
}

resource "aws_security_group" "Take-On-IG-SG" {
  name = "Take-On-IG-SG"
  description = "TakeOn security group for our internet gateway"
  vpc_id = "${aws_vpc.Take-On-VPC.id}"
  ingress {
      from_port = 0
      to_port = 0
      protocol = "-1"
      cidr_blocks = ["0.0.0.0/0"]
  }
  egress {
      from_port = 0
      to_port = 0
      protocol = "-1"
      cidr_blocks = ["0.0.0.0/0"]
  } 
  tags = {
      Name = "Take-On-IG-SG"
      Team = "TakeOn"
  }
}
