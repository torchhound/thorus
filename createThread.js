import uuid from "uuid";
import moment from "moment";
import AWS from "aws-sdk";
import * as dynamoTools from "./tools/dynamoTools";
import { success, failure } from "./tools/responseTools";

const config = require('./config');
AWS.config.update({ region: config.region });
const dynamoDb = new AWS.DynamoDB.DocumentClient();
const s3 = new AWS.S3({ region: config.region });

export function main(event, context, callback) {
    const data = JSON.parse(event.body);
    var initialPostOut = JSON.parse(data.posts[0]);
    const initialPostId = uuid.v4();
    initialPostOut.postId = initialPostId;
    const initialPostIn = JSON.stringify(initialPostOut)

    try {
        await s3.putObject({
            Bucket: config.postBucket,
            Key: initialPostId,
            Body: initialPostIn
        })
    } catch(e) {
        callback(null, failure({ status: false }));
    }

    const params = {
        TableName: config.tableName,
        Thread: {
            threadId: moment().unix(),
            threadTitle: data.threadTitle,
            posts: [initialPostId]
        }
    };

    try {
        await dynamoTools.call("put", params);
        callback(null, success(params.Item));
    } catch (e) {
        callback(null, failure({ status: false }));
    }
}