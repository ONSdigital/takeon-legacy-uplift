resource "aws_eks_cluster" "Take-On-EKS" {
  name            = "${var.cluster-name}"
  role_arn        = "${aws_iam_role.Take-On-Service.arn}"

  vpc_config {
    security_group_ids = ["${aws_security_group.Take-On-EKS-SG.id}"]
    subnet_ids         = ["${aws_subnet.private-subnet.id}", "${aws_subnet.private-subnet-backup.id}"]
  }

  depends_on = [
    "aws_iam_role_policy_attachment.Take-On-Cluster-AmazonEKSClusterPolicy",
    "aws_iam_role_policy_attachment.Take-On-Cluster-AmazonEKSServicePolicy",
  ]
}
