resource "aws_vpn_gateway" "Take-On-VPN-Gateway" {
  vpc_id = "${aws_vpc.Take-On-VPC.id}"
  tags{
	  Name = "Take-On-VPN-Gateway"
	  Team = "TakeOn"
  } 
}

resource "aws_ec2_client_vpn_endpoint" "main-endpoint" {
  description = "Test endpoint"
  server_certificate_arn = "arn:aws:acm:eu-west-2:014669633018:certificate/df9217f4-0d43-432a-a4b6-a3bcce950f2d"
  client_cidr_block = "178.0.0.0/16"

  connection_log_options{
	  enabled = false
  }

    authentication_options {
    type = "certificate-authentication"
    root_certificate_chain_arn = "arn:aws:acm:eu-west-2:014669633018:certificate/8690f3d0-fef4-410a-8fae-05068b043353"
  }
	tags = {
    	Team = "TakeOn"
		Name = "Take-On-VPN"
  }
}

resource "aws_ec2_client_vpn_network_association" "Private" {
  client_vpn_endpoint_id = "${aws_ec2_client_vpn_endpoint.main-endpoint.id}"
  subnet_id = "${aws_subnet.private-subnet.id}"
}
