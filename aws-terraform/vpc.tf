resource "aws_vpc" "Take-On-VPC"{
	cidr_block = "${var.vpc_cidr}"
	enable_dns_hostnames = true
	tags {
		Name = "Take-On-VPC"
		Team = "${var.TeamName}"
	}
}

resource "aws_subnet" "public-subnet"{
	vpc_id = "${aws_vpc.Take-On-VPC.id}"
	cidr_block = "${var.takeon_public_subnet}"
	availability_zone = "${var.aws_zone}"
	
	tags{
		Name = "Take-On-Public"
		Team = "${var.TeamName}"
	}
}

resource "aws_subnet" "private-subnet"{
	vpc_id = "${aws_vpc.Take-On-VPC.id}"
	cidr_block = "${var.takeon_private_subnet}"
	availability_zone = "${var.aws_zone}"
	
	tags{
		Name = "Take-On-Private"
		Team = "${var.TeamName}"
	}
}

resource "aws_subnet" "private-subnet-backup"{
	vpc_id = "${aws_vpc.Take-On-VPC.id}"
	cidr_block = "${var.takeon_private_subnet_backup}"
	availability_zone = "eu-west-2a"
	
	tags{
		Name = "Take-On-Private-2a"
		Team = "${var.TeamName}"
	}
}

resource "aws_route_table" "route-test" {
	vpc_id = "${aws_vpc.Take-On-VPC.id}"

	route {
		cidr_block = "0.0.0.0/0"
		gateway_id = "${aws_internet_gateway.TakeOn-Ig.id}"
	}

	tags {
		Team = "${var.TeamName}"
	}
  
}

resource "aws_route_table_association" "route-test-associate" {
  subnet_id = "${aws_subnet.private-subnet.id}"
  route_table_id = "${aws_route_table.route-test.id}"
}
