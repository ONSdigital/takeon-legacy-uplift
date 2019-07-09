    
#
# Outputs
#

locals {
  config_map_aws_auth = <<CONFIGMAPAWSAUTH
apiVersion: v1
kind: ConfigMap
metadata:
  name: aws-auth
  namespace: kube-system
data:
  mapRoles: |
    - rolearn: ${aws_iam_role.take-on-node.arn}
      username: system:node:{{EC2PrivateDNSName}}
      groups:
        - system:bootstrappers
        - system:nodes
CONFIGMAPAWSAUTH

  kubeconfig = <<KUBECONFIG
apiVersion: v1
clusters:
- cluster:
    server: ${aws_eks_cluster.take-on-cluster.endpoint}
    certificate-authority-data: ${aws_eks_cluster.take-on-cluster.certificate_authority.0.data}
  name: kubernetes
contexts:
- context:
    cluster: kubernetes
    user: aws
  name: aws
current-context: aws
kind: Config
preferences: {}
users:
- name: aws
  user:
    exec:
      apiVersion: client.authentication.k8s.io/v1alpha1
      command: aws-iam-authenticator
      args:
        - "token"
        - "-i"
        - "${var.cluster-name}"
KUBECONFIG

  takeon-ui-test = <<TAKEONUITEST
apiVersion: apps/v1
kind: Deployment
metadata:
  name: takeon-ui-test
spec:
  selector:
    matchLabels:
      run: takeon-ui-test
  replicas: 3
  template:
    metadata:
      labels:
        run: takeon-ui-test
    spec:
      containers:
      - name: takeon-ui-test
        image: 014669633018.dkr.ecr.eu-west-2.amazonaws.com/takeon-ui-test
        ports:
        - containerPort: 5000
TAKEONUITEST

  takeon-ui-test-svc = <<TAKEONUITESTSVC
apiVersion: v1
kind: Service
metadata:
  name: takeon-ui-test
  labels:
    run: takeon-ui-test
spec:
  ports:
  - port: 5000
    protocol: TCP
  selector:
    run: takeon-ui-test
  type: LoadBalancer
TAKEONUITESTSVC

}



output "config_map_aws_auth" {
  value = "${local.config_map_aws_auth}"
}

output "kubeconfigtak" {
  value = "${local.kubeconfig}"
}

output "takeon-ui-test" {
  value = "${local.takeon-ui-test}"
}

output "takeon-ui-test-svc" {
  value = "${local.takeon-ui-test-svc}"
}