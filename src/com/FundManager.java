package com;

import com.mongodb.client.*;
import org.bson.Document;

public class FundManager {

    static int TOTAL_FUND = 100000;

    public static int getDistributedFund() {
        MongoCollection<Document> col =
                MongoDBConnection.getDB().getCollection("scholarships");

        int sum = 0;
        for (Document d : col.find()) {
            sum += d.getInteger("amount", 0);
        }
        return sum;
    }

    public static int getRemainingFund() {
        return TOTAL_FUND - getDistributedFund();
    }
}