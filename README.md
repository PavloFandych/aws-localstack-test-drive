bucket list:            aws --endpoint-url=http://localhost:4566 s3 ls
create bucket:          aws --endpoint-url=http://localhost:4566 s3 mb s3://test-bucket
upload file to bucket:  aws --endpoint-url=http://localhost:4566 s3 cp test-file.txt s3://test-bucket
bucket ls:              aws --endpoint-url=http://localhost:4566 s3 ls s3://test-bucket
create lambda:          aws lambda create-function --endpoint-url http://localhost:4566 --function-name test-lambda --runtime java8 --handler org.total.BucketHandler --region us-east-1 --zip-file fileb://aws-localstack-test-drive-1.0-SNAPSHOT.jar --role arn:aws:iam::12345:role/ignoreme
register lambda:        aws s3api put-bucket-notification-configuration --bucket test-bucket --notification-configuration file://s3hook.json --endpoint-url http://localhost:4566   

links:
https://codetinkering.com/localstack-s3-lambda-example-docker/

docker:
docker rm -vf $(docker ps -a -q)
docker rmi -f $(docker images -a -q)
docker volume rm $(docker volume ls -q)