import uuid from "uuid";
import moment from "moment";
import AWS from "aws-sdk";
import * as dynamoTools from "../tools/dynamoTools";
import { success, failure } from "../tools/responseTools";

const config = require('../config');
AWS.config.update({ region: config.region });
const dynamoDb = new AWS.DynamoDB.DocumentClient();
const s3 = new AWS.S3({ region: config.region });

export function main(event, context, callback) {
    const data = JSON.parse(event.body);
    var initialPostOut = JSON.parse(data.posts[0]);
    const initialPostId = uuid.v4();
    initialPostOut.postId = initialPostId;
    const initialPostIn = JSON.stringify(initialPostOut)

    var putParams = {
        Bucket: config.postBucket,
        Key: initialPostId,
        Body: initialPostIn
    }
    var putObjectPromise = s3.putObject(putParams).promise();
    putObjectPromise.then(function(data) {
        const dbParams = {
            TableName: config.tableName,
            Thread: {
                threadId: moment().valueOf(),
                threadTitle: data.threadTitle,
                posts: [initialPostId]
            }
        };

        var putDbPromise = dynamoTools.call("put", dbParams);
        putDbPromise.then(function(data) {
            callback(null, success(params.Item));
        }).catch(function(err) {
            callback(null, failure({ status: false }));
        })
    }).catch(function(err) {
        callback(null, failure({ status: false }));
    });
}