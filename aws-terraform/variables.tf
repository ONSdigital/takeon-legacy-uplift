variable "aws_region"{
	default = "eu-west-2"
}

variable "aws_zone"{
	default = "eu-west-2b"
}

variable "amis"{
	default = {
		take-on-test = "ami-14fb1073"
	}
}

variable "vpc_cidr"{
	default = "10.0.0.0/16"
}

variable "takeon_public_subnet"{
	default = "10.0.0.0/24"
}

variable "takeon_private_subnet"{
	default	= "10.0.1.0/24"
}

variable "takeon_private_subnet_backup"{
	default	= "10.0.2.0/24"
}

variable "cluster-name" {
  default = "Take-On-EKS"
}
