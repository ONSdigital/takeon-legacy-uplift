
#
# Variables Configuration
#

variable "cluster-name" {
  default = "terraform-eks-demo"
  type    = "string"
}

variable "aws_instance_size" {
  default = "t2.small"
  type = "string"
}
