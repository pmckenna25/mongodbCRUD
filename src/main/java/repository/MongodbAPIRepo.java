package repository;

import com.mongodb.client.MongoCollection;
import model.API_Key;
import model.Person;

import java.util.LinkedList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

public class MongodbAPIRepo {

    private static MongoCollection<API_Key> collection;

    public MongodbAPIRepo(String dbName, String collectionName){

        MongoConnect connection = new MongoConnect(dbName);
        connection.connect();
        collection = connection.getDatabase().getCollection(collectionName, API_Key.class);
    }

    public List<API_Key> getAll(){

        return collection.find().into(new LinkedList<>());
    }

    public API_Key findOne(String email){

        List<API_Key> apikeys = getAll();
        for(API_Key key: apikeys){
            if(key.getEmail().equals(email)){
                return key;
            }
        }
        return null;
    }

    public void addOne(API_Key key){

        collection.insertOne(key);
    }

    public void delete(API_Key key){

        collection.deleteOne(eq("email", key.getEmail()));
    }

    public void updateOne(API_Key key){

        collection.replaceOne(eq("email", key.getEmail()), key);
    }
}
