import uuid from "uuid";
import AWS from "aws-sdk";

AWS.config.update({ region: "us-east-2" });
const dynamoDb = new AWS.DynamoDB.DocumentClient();
const s3 = new aws.S3({ region: "us-east-2 "});

export function main(event, context, callback) {
}