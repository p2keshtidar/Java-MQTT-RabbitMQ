package com.classes;

import com.mongodb.MongoException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class MongoDBConnectionHandlerImpl {

    public MongoClient mongo;
    public MongoDatabase mongoDatabase;
    Properties properties = new Properties();
    Properties rqServer = new Properties();
    public String rabbitHost;
    public Integer rabbitPort;



    public MongoDBConnectionHandlerImpl() {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        try (InputStream is = loader.getResourceAsStream("mongodb.properties")) {
            try (InputStream ir = loader.getResourceAsStream("rabbitMQ.properties")){
                this.rqServer.load(ir);
                 this.rabbitHost = this.rqServer.getProperty("host");
                 this.rabbitPort = Integer.valueOf(this.rqServer.getProperty("port"));
            }
            this.properties.load(is);
            String host = this.properties.getProperty("host");
            String port = this.properties.getProperty("port");
            String database = this.properties.getProperty("database");
            String username = this.properties.getProperty("username");
            String password = this.properties.getProperty("password");
            mongo = MongoClients.create( "mongodb://" + host + ":" + port );
            mongoDatabase = mongo.getDatabase(database);
        } catch (Exception e) {
            System.out.println("Error In Connection: " + e);
        }
    }

    /**
     * @param CollectionName = collection name
     */
    // Collection
    public void createCollection(String CollectionName) {
        try{
            mongoDatabase.createCollection(CollectionName);
            System.out.println("Collection <" + CollectionName + "> created successfully");
        } catch (Exception e) {
            System.out.println("Error In Create Collection: " + e);
        }
    }


    /**
     * @param CollectionName = collection name
     * @param oneDocument = eine Document
     */
    public void insertOneDocument(String CollectionName, Document oneDocument) {
        try {
            MongoCollection<Document> collection = mongoDatabase.getCollection(CollectionName);
            // Inserting document into the collection
            collection.insertOne(oneDocument);
            System.out.println("Document inserted successfully");
        } catch (Exception e) {
            System.out.println("Error In Insert One Document: " + e);
        }
    }

    public void deleteOneDocument(String CollectionName, Bson query){
        MongoCollection<Document> collection = mongoDatabase.getCollection(CollectionName);
        try {
            // Delete One document
            collection.deleteOne(query);
        } catch (MongoException me) {
            System.err.println("Unable to delete due to an error: " + me);
        }
    }

    public void deleteManyDocuments(String CollectionName, Bson query){
        MongoCollection<Document> collection = mongoDatabase.getCollection(CollectionName);
        try {
            // Delete One document
            collection.deleteMany(query);
        } catch (MongoException me) {
            System.err.println("Unable to delete due to an error: " + me);
        }
    }

    public long countDocument(String CollectionName){
        MongoCollection<Document> collection = mongoDatabase.getCollection(CollectionName);
        return collection.countDocuments();
    }

}