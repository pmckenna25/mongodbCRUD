package repository;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.client.MongoDatabase;
import model.PropertyModel;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;


import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class MongoConnect implements DBConnect{

    private MongoDatabase database;
    private String name;

    public MongoConnect(String name){

        this.name = name;
    }

    @Override
    public void connect() {

        database = connectWithDatabase();
    }

    private MongoDatabase connectWithDatabase() {

        PropertyModel propertyModel = new PropertyModel();

        CodecRegistry pojoCodecRegistry = fromRegistries(MongoClient
                .getDefaultCodecRegistry(), fromProviders(PojoCodecProvider
                .builder()
                .automatic(true)
                .build())
        );

        MongoClient mongoClient = new MongoClient(propertyModel
                .getProperties()
                .getProperty("my.mongodb"), MongoClientOptions
                .builder()
                .codecRegistry(pojoCodecRegistry)
                .build()
        );

        return mongoClient.getDatabase(this.name);

    }

    public MongoDatabase getDatabase() {
        return database;
    }
}
