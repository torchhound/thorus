import moment from "moment";
import AWS from "aws-sdk/global";
import path from "path";
import fs from "fs";
import * as dynamoTools from "./tools/dynamoTools";
import { success, failure } from "./tools/responseTools";
import config from "./config.json"

export async function createThread(event, context, callback) {
    AWS.config.update({ region: config.region });
    const dynamoDb = new AWS.DynamoDB.DocumentClient();

    const data = JSON.parse(event.body);

    const putParams = {
        TableName: config.tableName,
        Item: {
            anonId: data.anonId,
            threadId: moment().valueOf(),
            threadTitle: data.threadTitle,
            posts: data.posts
        }
    };

    try {
        const result = await dynamoTools.call("put", putParams);
        callback(null, success({ status: true}));
    } catch (e) {
        console.log(e);
        callback(null, failure({ status: false }));
    }
}

export async function getAllThreads(event, context, callback) {
    AWS.config.update({ region: config.region });
    const dynamoDb = new AWS.DynamoDB.DocumentClient();

    const readParams = {
        TableName: config.tableName,
        KeyConditionExpression: "anonId = :id and threadId >= :timeStamp",
        ExpressionAttributeValues: {
            ":id": parseInt(event.pathParameters.id),
            ":timeStamp": moment().subtract(event.pathParameters.days, 'days').valueOf()
        }
    };

    try {
        const result = await dynamoTools.call("query", readParams);
        callback(null, success(result));
    } catch (e) {
        console.log(e);
        callback(null, failure({ status: false }));
    }
}

export async function createPost(event, context, callback) {
    AWS.config.update({ region: config.region });
    const dynamoDb = new AWS.DynamoDB.DocumentClient();

    const threadId = event.pathParameters.thread;
    const params = {
        TableName: config.tableName,
        Key: { "threadId": threadId },
        UpdateExpression: "ADD #posts :post", //SET list_append?
        ExpressionAttributeNames: { "#posts": "posts" },
        ExpressionAttributeValues: { ":post": dynamoDb.createSet([event.body]) }
        //ReturnValues:"UPDATED_NEW"
    };

    try {
        const result = await dynamoTools.call("update", params);
        callback(null, success(result.Item));
    } catch (e) {
        console.log(e);
        callback(null, failure({ status: false }));
    }
}

export async function scheduledDelete(event, context, callback) {
    AWS.config.update({ region: config.region });
    const dynamoDb = new AWS.DynamoDB.DocumentClient();

    const time = moment().subtract(30, 'days').valueOf()
    const queryParams = {
        TableName: config.tableName,
        KeyConditionExpression: "threadId >= :threadId",
        ExpressionAttributeValues: {
            ":threadId": time
        }
    };

    try {
        let queryArray = [];
        const queryResult = await dynamoTools.call("query", queryParams);
        for (let i = queryResult.Item.length - 1; i >= 0; i--) {
            queryArray.push(queryResult.Item[i].threadId); //turn into json DeleteRequest
        }
        const deleteParams = {
            TableName: config.tableName,
            RequestItems : {
                'Bag' : queryArray
            }
        };
        //const deleteResult = await dynamoTools.call("batchWrite", deleteParams);
        //callback(null, success(deleteResult.Item));
    } catch (e) {
        console.log(e);
        //callback(null, failure({ status: false }));
    }
}
