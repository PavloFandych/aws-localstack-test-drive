bucket list:            aws --endpoint-url=http://localhost:4566 s3 ls
create bucket:          aws --endpoint-url=http://localhost:4566 s3 mb s3://test-bucket
upload file to bucket:  aws --endpoint-url=http://localhost:4566 s3 cp test-file.txt s3://test-bucket
bucket ls:              aws --endpoint-url=http://localhost:4566 s3 ls s3://test-bucket

links:
https://codetinkering.com/localstack-s3-lambda-example-docker/