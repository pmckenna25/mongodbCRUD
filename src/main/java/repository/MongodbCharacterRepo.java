package repository;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import model.CharacterClass;
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


public class MongodbCharacterRepo {

    private static MongoCollection<CharacterClass> collection;

    public MongodbCharacterRepo(String dbName, String collectionName) throws IOException, URISyntaxException {

        PropertyModel propertyModel = new PropertyModel();

        CodecRegistry pojoCodecRegistry = fromRegistries(MongoClient.getDefaultCodecRegistry(),
                fromProviders(PojoCodecProvider.builder().automatic(true).build()));

        MongoClient mongoClient = new MongoClient(propertyModel.getProperties().getProperty("my.mongodb"), MongoClientOptions
                .builder().codecRegistry(pojoCodecRegistry).build());

        MongoDatabase database = mongoClient.getDatabase(dbName);

        collection = database.getCollection(collectionName, CharacterClass.class);
    }

    public List<CharacterClass> getAll(){

        return collection.find().into(new LinkedList<>());
    }
    public void addOne(CharacterClass character){

        collection.insertOne(character);
    }

    public void delete(CharacterClass character){

        collection.deleteOne(eq("characterId", character.getCharacterId()));
    }

    public void updateOne(CharacterClass character){

        collection.replaceOne(eq("characterId", character.getCharacterId()), character);
    }
}
