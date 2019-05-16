##########################################
# Description
# 
# Made to speed up the setup of everyone's off network laptops. 
# Also made sure that the development enviroment was the same for everyone.
# Should contain everything used by the Take-On legacy uplift team.
#
#############################################

##########################################
#
# Author: kilbar
#
# Date created: 08/02/2019
#
# Change log
#
############################################
# Version   # Date		 # Reason
# 0.0		08/02/2019	 Initial version		
#
# 0.1		11/02/2019	 Changed JDK version, required change in env variables	
#
# 0.2		11/02/2019	 Split URLS to download into two batches, 
#						 I think that it was running out of threads and not DLing everything.
#
# 0.3		11/02/2019	 Chaged security protocol to use TSL 1.2 instad of 1.0
#
# 0.4		11/02/2019	 Removed change from 1.2, will be run as seperate command
#
# 0.5		11/02/2019	 Re-added the change from 1.0 to 1.2. This needed to be added into 
#						 the Start-Job part of the script, as this will be run in it's own process.
#
# 0.6 		11/02/2019	 Merged the URLS and files back together
#
# 0.7		11/02/2019	 Added git setup
#
# 0.8		11/02/2019	 Fixed issue with the path not being appended to
#
# 0.9		19/03/2019	 Added lombok plugin
#############################################


$URLS = @("https://www.python.org/ftp/python/3.7.2/python-3.7.2-amd64.exe",
		"https://notepad-plus-plus.org/repository/7.x/7.6.2/npp.7.6.2.Installer.x64.exe",
		"https://download.jetbrains.com/idea/ideaIC-2018.3.3.exe",
		"https://download.jetbrains.com/python/pycharm-community-2018.3.3.exe",
		"https://github.com/git-for-windows/git/releases/download/v2.20.1.windows.1/Git-2.20.1-64-bit.exe",
		"https://download.java.net/java/early_access/jdk12/30/GPL/openjdk-12-ea+30_windows-x64_bin.zip",
		"https://slack.com/ssb/download-win64",
		"https://aka.ms/win32-x64-user-stable",
		"https://nodejs.org/dist/v10.15.1/node-v10.15.1-x64.msi",
		"http://www.mirrorservice.org/sites/ftp.apache.org/maven/maven-3/3.6.0/binaries/apache-maven-3.6.0-bin.zip",
		"https://aka.ms/installazurecliwindows",
		"https://dl.pstmn.io/download/latest/win64",
		"https://www.7-zip.org/a/7z1806-x64.exe",
		"https://storage.googleapis.com/kubernetes-helm/helm-v2.12.3-windows-amd64.zip")
	
$Files = @("python.exe", "notepadpp.exe", "intellij.exe", "pycharm.exe", "git.exe", "openjdk.zip", "slack.exe", "vscode.exe", "nodejs.msi", "maven.zip", "Azure_cli.msi", "postman.exe", "7zip.exe", "helm.zip")

echo "==========================================================="
echo "Downloading files"
echo "==========================================================="
# Loop over URLs and save files using Files array
for($f=0;$f -lt $URLS.Count; $f++)
{
	$remote = $URLS[$f]
	$local = $Files[$f]
	Start-Job {[Net.ServicePointManager]::SecurityProtocol = [Net.SecurityProtocolType]::Tls12
				Invoke-WebRequest $using:remote -Method Get -OutFile $using:local}

}
Get-Job|Wait-Job

echo "==========================================================="
echo "Making Applications directory"
echo "==========================================================="
New-Item -ItemType directory -Path C:\Applications

#Unzip openjdk to C:\Applications and set env
echo "Unzipping openjdk to C:\Applications\"
Expand-Archive "openjdk.zip" -Force -DestinationPath "c:\Applications\"
echo "Unzipping Maven to C:\Applications\mvn"
Expand-Archive "maven.zip" -Force -DestinationPath "c:\Applications\mvn"
echo "Unzipping helm to C:\Applications\windows-amd64"
Expand-Archive "helm.zip" -Force -DestinationPath "c:\Applications\"

echo "==========================================================="
echo "Creating enviroment variables"
echo "==========================================================="
[Environment]::SetEnvironmentVariable("JAVA_HOME", "C:\Applications\jdk-12", "Machine")

echo "==========================================================="
echo "Creating enviroment variables"
echo "==========================================================="
[Environment]::SetEnvironmentVariable("M2_HOME", "C:\Applications\mvn\apache-maven-3.6.0\bin", "Machine")
[Environment]::SetEnvironmentVariable("MAVEN_HOME", "C:\Applications\mvn\apache-maven-3.6.0\bin", "Machine")

echo "==========================================================="
echo "Installing files"
echo "==========================================================="
for($f=0; $f -lt $Files.Count; $f++)
{
	$fileToInstall = ".\" + $Files[$f]
	echo "installing "$fileToInstall
	Start-Process -FilePath $fileToInstall -Wait
}

echo "==========================================================="
echo "Adding to path"
echo "==========================================================="
$env:Path += ";C:\Applications\jdk-12\bin"
$env:Path += ";C:\Program Files\Git\bin"
$env:Path += ";C:\Applications\mvn\apache-maven-3.6.0\bin"
$env:Path += ";C:\Applications\windows-amd64"
$env:Path += ";C:\Program Files\nodejs"
[Environment]::SetEnvironmentVariable("Path", $env:Path, "Machine")

$env:Path = [System.Environment]::GetEnvironmentVariable("Path","Machine") + ";" + [System.Environment]::GetEnvironmentVariable("Path","User")

# Configure git global
echo "==========================================================="
echo "Git global config"
echo "==========================================================="
$user_email = Read-Host "enter your email that you signed up to Git with"
$user_name = Read-Host "enter the name that you used to signed up to Git with"

git config --global user.email $user_email
git config --global user.name $user_name

# Path config check
echo "==========================================================="
echo "Checking Java and Maven have been configured correctly"
echo "If you get a chunk of red text here, then either java"
echo "or Maven hasn't been added to path correctly"
echo "==========================================================="
java --version
mvn --version
Start-Sleep 10
# Get TypeScript and Misc plugins
echo "==========================================================="
echo "Plugins and TypeScript"
echo "==========================================================="

echo "Installing typescript V 3.3.3 from npm"
npm install -g typescript@3.3.3

echo "==========================================================="
echo "Getting VS Code plugins"
echo "==========================================================="
echo "Installing typescript linter"
code --install-extension ms-vscode.vscode-typescript-tslint-plugin
echo "Installing python extension"
code --install-extension ms-python.python
echo "Installing Java language support"
code --install-extension redhat.java
echo "Installing maven for java"
code --install-extension vscjava.vscode-maven
echo "Installing Docker extension"
code --install-extension peterjausovec.vscode-docker
echo "Installing C/C++ extension"
code --install-extension ms-vscode.cpptools
echo "Installing YAML extension"
code --install-extension redhat.vscode-yaml
echo "Installing JSON as Code"
code --install-extension quicktype.quicktype

echo "==========================================================="
echo "Getting Intellij/PyCharm plugins"
echo "==========================================================="
echo "Downloading sonar"
[Net.ServicePointManager]::SecurityProtocol = [Net.SecurityProtocolType]::Tls12
Invoke-WebRequest "https://plugins.jetbrains.com/plugin/download?rel=true&updateId=53739" -Method Get -OutFile "sonar.zip"
echo "Got sonar, extracting to IntelliJ plugins"
Expand-Archive "sonar.zip" -Force -DestinationPath "C:\Program Files\JetBrains\IntelliJ IDEA Community Edition 2018.3.3\plugins\"
echo "Extracting to pycharm plugins"
Expand-Archive "sonar.zip" -Force -DestinationPath "C:\Program Files\JetBrains\PyCharm Community Edition 2018.3.3\plugins\"
echo "Downloading Lombok"
Invoke-WebRequest "https://plugins.jetbrains.com/plugin/download?rel=true&updateId=53293" -Method Get -OutFile "lombok.zip"
echo "Extracting to IntelliJ plugins"
Expand-Archive "lombok.zip" -Force -DestinationPath "C:\Program Files\JetBrains\IntelliJ IDEA Community Edition 2018.3.3\plugins\"