import moment from "moment";
import AWS from "aws-sdk";
import * as dynamoTools from "../tools/dynamoTools";
import { success, failure } from "../tools/responseTools";

var fs = require('fs');
var config;
fs.readFile('file', 'utf8', function (err, data) {
  if (err) throw err;
  config = JSON.parse(data);
});
AWS.config.update({ region: config.region });
const dynamoDb = new AWS.DynamoDB.DocumentClient();
const s3 = new AWS.S3({ region: config.region });

export function main(event, context, callback) {
    const data = JSON.parse(event.body);

    const dbParams = {
        TableName: config.tableName,
        Thread: {
            threadId: moment().valueOf(),
            threadTitle: data.threadTitle,
            posts: data.posts
        }
    };

    var putDbPromise = dynamoTools.call("put", dbParams);
    putDbPromise.then(function(data) {
        callback(null, success(params.Item));
    }).catch(function(err) {
        callback(null, failure({ status: false }));
    })
}