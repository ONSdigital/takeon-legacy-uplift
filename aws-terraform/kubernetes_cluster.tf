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

resource "aws_launch_configuration" "EKS-Config"{
    associate_public_ip_address = false
    instance_type = "${var.EKS_instance_type}"
    image_id = "${data.aws_ami.ubuntu.id}"

    lifecycle{
        create_before_destroy = true
    }
}

resource "aws_autoscaling_group" "scaling_test" {
  launch_configuration = "${aws_launch_configuration.EKS-Config.name}"
  vpc_zone_identifier = ["${aws_subnet.private-subnet.id}", "${aws_subnet.private-subnet-backup.id}"]
  min_size = "${var.master_node_min_instances}"
  max_size = "${var.master_node_max_instances}"
}
