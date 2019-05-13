#### General AWS setup ####
variable "aws_region"{
	default = "eu-west-2"
}

variable "aws_zone"{
	default = "eu-west-2b"
}

variable "TeamName" {
  default = "TakeOn"
}

#### EC2 instence variable ####
variable "amis"{
	default = {
		take-on-test = "ami-14fb1073"
	}
}

#### VPC variables ####
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

#### EKS Cluster Variables ####
variable "cluster-name" {
  default = "Take-On-EKS"
}

variable "EKS_instance_type" {
  default = "t2.micro"
}

data "aws_ami" "ubuntu"{
	most_recent=true
   filter {
    name   = "name"
    values = ["ubuntu/images/hvm-ssd/ubuntu-trusty-14.04-amd64-server-*"]
  }

  filter {
    name   = "virtualization-type"
    values = ["hvm"]
  }

  owners = ["099720109477"] # Canonical
}

variable "master_node_min_instances"{
	default = 1
}

variable "master_node_max_instances" {
	default = 3
}

variable "pod_min_instances" {
	default = 0
}

variable "pod_max_instsances" {
	default = 3
}