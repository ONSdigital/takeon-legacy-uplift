From the aws-terraform folder run `terraform init` to initialise terraform

You can change parameters in the variables.tf file to be specific to your application, such as cluster name and ec2 instance type

Next run `terraform apply` This will begin to create your infrastructure, this process takes around 10 minutes

Once this finishes run the shell script `./kubectlcommands.sh` This takes 3 parameters, the first is your cluster name, second is your namespace and the third is the path to your helm chart

The helm install will give you three commands to run, change the port numbers to the ones you need, in this case it was 5000:5000

Once this is complete your image should be running
