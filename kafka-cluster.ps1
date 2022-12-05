param(
[string]$operation,
[string]$topicname
)


# powershell kafka startup
# Run a cluster                         -operation run
# create a topic                        -operation create-topic <topic_name>
# consume from a topic                  -operation start-consumer <topic_name>
# produce a message to a topic          -operation publish-message <topic_name> <message_string>

# commands
#  .\kafka-cluster.ps1 -operation start-consumer -topicname datacache
#  .\kafka-cluster.ps1 -operation start-producer -topicname datacache
#  .\kafka-cluster.ps1 -operation create-topic -topicname datacache
#  .\kafka-cluster.ps1 -operation run



# set the execution policy
Set-ExecutionPolicy RemoteSigned -Scope CurrentUser


# set the paths
$KAFKA_HOME="C:\Tools\Kafka\"

# set environmnet path
$Env:JAVA_HOME = "C:\Users\phoen\Documents\Tools\graalvm-ce-java11-windows-amd64-22.2.0\graalvm-ce-java11-22.2.0"
$Env:M2_HOME = "C:\Users\phoen\Documents\Tools\apache-maven-3.8.6"
$Env:Path +=";" + $Env:JAVA_HOME + "\bin;" + $Env:M2_HOME + "\bin"

cd $KAFKA_HOME

if ($operation -eq "run") {
    Start-Process bin\windows\zookeeper-server-start.bat config\zookeeper.properties
    Start-Sleep -Seconds 5
    Start-Process bin\windows\kafka-server-start.bat config\server.properties
} elseif ($operation -eq "create-topic") {
    bin\windows\kafka-topics.bat --create --topic $topicname --bootstrap-server localhost:9092
} elseif ($operation -eq "start-consumer") {
    Start-Process "cmd.exe" "/c bin\windows\kafka-console-consumer.bat --topic $topicname --bootstrap-server localhost:9092"
} elseif ($operation -eq "start-producer") {
    Start-Process "cmd.exe" "/c bin\windows\kafka-console-producer.bat --bootstrap-server localhost:9092 --topic $topicname"
} elseif ($operation -eq "start-producer-with-key") {
    Start-Process "cmd.exe" "/c bin\windows\kafka-console-producer.bat --bootstrap-server localhost:9092 --topic $topicname --property \"parse.key=true\" --property \"key.separator=:\""
}
 else {
    Write-Host "Unrecognised command : [$operation]"
}

cd C:\Users\Phoen\Desktop

