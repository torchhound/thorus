import * as dynamoTools from "./tools/dynamoTools";
import { success, failure } from "./tools/responseTools";

const config = require('./config');

export async function main(event, context, callback) {
  const params = {
    TableName: config.tableName,
    //TODO(jcieslik) need to scan for all documents
  };

  try {
    const result = await dynamoTools.call("scan", params);
    callback(null, success(result.Items));
  } catch (e) {
    callback(null, failure({ status: false }));
  }
}