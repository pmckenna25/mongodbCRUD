package repository;

import com.mongodb.client.MongoCollection;
import model.API_Key;

import javax.ws.rs.NotFoundException;
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

        List<API_Key> fullList = collection.find().into(new LinkedList<>());
        if(fullList.size() == 0){
            throw new NotFoundException();
        }else{
            return fullList;
        }
    }

    public API_Key findOne(String email){

        List<API_Key> apikeys = getAll();
        for(API_Key key: apikeys){
            if(key.getEmail().equals(email)){
                return key;
            }
        }
        throw new NotFoundException();
    }

    public void addOne(API_Key key){

        collection.insertOne(key);
    }

    public void delete(API_Key key){

        if(collection.find(eq("email", key.getEmail())).into(new LinkedList<>()).size() == 0){
            throw new NotFoundException();
        }else {
            collection.deleteOne(eq("email", key.getEmail()));
        }
    }

    public void updateOne(API_Key key){

        if(collection.find(eq("email", key.getEmail())).into(new LinkedList<>()).size() == 0){
            throw new NotFoundException();
        }else {
            collection.replaceOne(eq("email", key.getEmail()), key);
        }
    }
}
