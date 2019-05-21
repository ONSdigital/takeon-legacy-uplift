#
# VPC Resources
#  * VPC
#  * Subnets
#  * Internet Gateway
#  * Route Table
#

resource "aws_vpc" "demo" {
  cidr_block = "10.0.0.0/16"

  tags = "${
    map(
      "Name", "terraform-eks-demo-node",
      "kubernetes.io/cluster/${var.cluster-name}", "shared",
    )
  }"
}

resource "aws_subnet" "eks-subnet" {
   count = 2

  # availability_zone = "${data.aws_availability_zones.available.names[count.index]}"s
  cidr_block        = "10.0.${count.index + 1}.0/24"
  vpc_id            = "${aws_vpc.Take-On-VPC.id}"

  tags = "${
    map(
      "Name", "take-on-eks-subnet",
      "kubernetes.io/cluster/${var.cluster-name}", "shared",
    )
  }"
}

resource "aws_route_table" "demo" {
  vpc_id = "${aws_vpc.Take-On-VPC.id}"

  route {
    cidr_block = "0.0.0.0/0"
    gateway_id = "${aws_internet_gateway.TakeOn-Ig.id}"
  }
}

resource "aws_route_table_association" "demo" {
  count = 2

  subnet_id      = "${aws_subnet.private-subnet.id}"
  route_table_id = "${aws_route_table.demo.id}"
}

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

# resource "aws_route_table_association" "route-test-associate" {
#   subnet_id = "${aws_subnet.private-subnet.id}"
#   route_table_id = "${aws_route_table.route-test.id}"
# }