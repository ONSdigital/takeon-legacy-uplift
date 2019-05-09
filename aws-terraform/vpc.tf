resource "aws_vpc" "default"{
	cidr_block = "${var.vpc_cidr}"
	enable_dns_hostnames = true
	tags {
		Name = "take-on-vpc"
		Team = "TakeOn"
	}
}

resource "aws_subnet" "public-subnet"{
	vpc_id = "${aws_vpc.default.id}"
	cidr_block = "${var.takeon_public_subnet}"
	availability_zone = "${var.aws_zone}"
	
	tags{
		Name = "Take-On-Public"
		Team = "TakeOn"
	}
}

resource "aws_subnet" "private-subnet"{
	vpc_id = "${aws_vpc.default.id}"
	cidr_block = "${var.takeon_private_subnet}"
	availability_zone = "${var.aws_zone}"
	
	tags{
		Name = "Take-On-Private"
		Team = "TakeOn"
	}
}

resource "aws_subnet" "private-subnet-backup"{
	vpc_id = "${aws_vpc.default.id}"
	cidr_block = "${var.takeon_private_subnet_backup}"
	availability_zone = "eu-west-2a"
	
	tags{
		Name = "Take-On-Private-2a"
		Team = "TakeOn"
	}
}

resource "aws_internet_gateway" "default"{
	vpc_id = "${aws_vpc.default.id}"
	
	tags{
		Name = "Take-On-Gateway"
		Team = "TakeOn"
	}
}
