resource "aws_internet_gateway" "TakeOn-Ig"{
	vpc_id = "${aws_vpc.Take-On-VPC.id}"
	
	tags{
		Name = "Take-On-Gateway"
		Team = "${var.TeamName}"
	}
}
