resource "aws_internet_gateway" "default"{
	vpc_id = "${aws_vpc.Take-On-VPC.id}"
	
	tags{
		Name = "Take-On-Gateway"
		Team = "TakeOn"
	}
}

