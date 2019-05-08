resource "aws_iam_role" "Take-On-Service" {
  name = "Take-On-Service"

  assume_role_policy = <<POLICY
{
  "Version": "2012-10-17",
  "Statement": [
    {
      "Effect": "Allow",
      "Principal": {
        "Service": "eks.amazonaws.com"
      },
      "Action": "sts:AssumeRole"
    }
  ]
}
POLICY
}

resource "aws_iam_role_policy_attachment" "Take-On-Cluster-AmazonEKSClusterPolicy" {
  policy_arn = "arn:aws:iam::aws:policy/AmazonEKSClusterPolicy"
  role       = "${aws_iam_role.Take-On-Service.name}"
}

resource "aws_iam_role_policy_attachment" "Take-On-Cluster-AmazonEKSServicePolicy" {
  policy_arn = "arn:aws:iam::aws:policy/AmazonEKSServicePolicy"
  role       = "${aws_iam_role.Take-On-Service.name}"
}

resource "aws_security_group" "Take-On-EKS-SG" {
  name        = "Take-On-EKS-SG"
  description = "Cluster communication with worker nodes"
  vpc_id      = "${aws_vpc.default.id}"

  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }

  tags = {
    Name = "Take-On-EKS-SG"
  }
}