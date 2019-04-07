package repository;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import model.API_Key;
import model.PropertyModel;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;


public class MongodbAPIRepo {

    private static MongoCollection<API_Key> collection;

    public MongodbAPIRepo(String dbName, String collectionName) throws IOException, URISyntaxException {

        PropertyModel propertyModel = new PropertyModel();

        CodecRegistry pojoCodecRegistry = fromRegistries(MongoClient.getDefaultCodecRegistry(),
                fromProviders(PojoCodecProvider.builder().automatic(true).build()));

        MongoClient mongoClient = new MongoClient(propertyModel.getProperties().getProperty("my.mongodb"), MongoClientOptions
                .builder().codecRegistry(pojoCodecRegistry).build());

        MongoDatabase database = mongoClient.getDatabase(dbName);

        collection = database.getCollection(collectionName, API_Key.class);
    }

    public List<API_Key> getAll(){

        return collection.find().into(new LinkedList<>());
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
