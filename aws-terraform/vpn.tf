resource "aws_vpn_gateway" "Take-On-VPN-Gateway" {
  vpc_id = "${aws_vpc.Take-On-VPC.id}"
  tags{
	  Name = "Take-On-VPN-Gateway"
	  Team = "${var.TeamName}"
  } 
}

resource "aws_ec2_client_vpn_endpoint" "main-endpoint" {
  description = "Test endpoint"
  server_certificate_arn = "arn:aws:acm:eu-west-2:014669633018:certificate/5275f388-aefe-4dd7-bbee-8edb3a2c9e5c"
  client_cidr_block = "178.0.0.0/16"
  dns_servers = ["${var.vpn_dns}"]

  connection_log_options{
	  enabled = false
  }

    authentication_options {
    type = "certificate-authentication"
    root_certificate_chain_arn = "arn:aws:acm:eu-west-2:014669633018:certificate/5aed85ca-75c5-4836-9d49-f2a8f763a5ee"
  }
	tags = {
    	Team = "${var.TeamName}"
		Name = "Take-On-VPN"
  }
}

# resource "aws_ec2_client_vpn_network_association" "Private" {
#   client_vpn_endpoint_id = "${aws_ec2_client_vpn_endpoint.main-endpoint.id}"
#   subnet_id = "${aws_subnet.eks-subnet.id}"
# }

resource "aws_nat_gateway" "Take-On-Nat" {
    allocation_id = "${aws_eip.nat_eip.id}"
    subnet_id = "${aws_subnet.private-subnet.id}"
    tags{
        Name = "TakeOn-Nat"
        Team = "${var.TeamName}"
    }
}

resource "aws_eip" "nat_eip" {
    vpc = true
}

resource "aws_route_table" "nat_route_table" {
    vpc_id = "${aws_vpc.Take-On-VPC.id}"

    route{
        cidr_block = "0.0.0.0/0"
        nat_gateway_id = "${aws_nat_gateway.Take-On-Nat.id}"
    }
}
