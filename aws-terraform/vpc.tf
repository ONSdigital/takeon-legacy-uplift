resource "aws_vpc" "default"{
	cidr_block = "${var.vpc_cidr}"
	enable_dns_hostnames = true
	tags {
		Name = "take-on-vpc"
	}
}

resource "aws_subnet" "public-subnet"{
	vpc_id = "${aws_vpc.default.id}"
	cidr_block = "${var.takeon_public_subnet}"
	availability_zone = "${var.aws_zone}"
	
	tags{
		Name = "Take-On-Public"
	}
}

resource "aws_subnet" "private-subnet"{
	vpc_id = "${aws_vpc.default.id}"
	cidr_block = "${var.takeon_private_subnet}"
	availability_zone = "${var.aws_zone}"
	
	tags{
		Name = "Take-On-Private"
	}
}

resource "aws_subnet" "private-subnet-backup"{
	vpc_id = "${aws_vpc.default.id}"
	cidr_block = "${var.takeon_private_subnet_backup}"
	availability_zone = "eu-west-2a"
	
	tags{
		Name = "Take-On-Private-2a"
	}
}

resource "aws_internet_gateway" "default"{
	vpc_id = "${aws_vpc.default.id}"
	
	tags{
		Name = "Take-On-Gateway"
	}
}

resource "aws_vpn_gateway" "Take-On-VPN-Gateway" {
  vpc_id = "${aws_vpc.default.id}"
  tags{
	  Name = "Take-On-VPN-Gateway"
  } 
}

resource "aws_customer_gateway" "Take-On-Customer-Gateway" {
  
}

