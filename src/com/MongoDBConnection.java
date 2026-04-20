package com;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class MongoDBConnection {
    private static MongoDatabase db;

    public static MongoDatabase getDB() {
        if (db == null) {
            MongoClient client = MongoClients.create("mongodb://localhost:27017");
            db = client.getDatabase("scholarshipDB");
        }
        return db;
    }
}