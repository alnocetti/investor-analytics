package com.test.investor_analytics.repository;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import lombok.AllArgsConstructor;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class MongoHelper {

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * Diagnostic helper: returns DB name, collections, counts and a sample document.
     * Call this from a controller or a unit test to verify what the app sees.
     */
    public Map<String, Object> diagnose() {
        Map<String, Object> report = new LinkedHashMap<>();
        try {
            MongoDatabase db = mongoTemplate.getDb();
            report.put("databaseName", db.getName());

            List<String> collections = new ArrayList<>();
            for (String name : db.listCollectionNames()) {
                collections.add(name);
            }
            report.put("collections", collections);

            for (String collectionName : collections) {
                MongoCollection<Document> collection = db.getCollection(collectionName);
                long count = collection.countDocuments();
                report.put(collectionName + "_count", count);
            }
        return report;
        } catch (Exception e) {
            report.put("error", e.getMessage());
            return report;
        }
    }

}
