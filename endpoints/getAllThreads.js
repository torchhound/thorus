import * as dynamoTools from "../tools/dynamoTools";
import moment from "moment";
import { success, failure } from "../tools/responseTools";

const config = require('../config');

export async function main(event, context, callback) {
  const params = {
    TableName: config.tableName,
    KeyConditionExpression: "threadId >= :threadId",
    ExpressionAttributeValues: {
      ":threadId": moment().subtract(7, 'days').valueOf()
    }
  };

  try {
    const result = await dynamoTools.call("query", params);
    callback(null, success(result.Item));
  } catch (e) {
    callback(null, failure({ status: false }));
  }
}