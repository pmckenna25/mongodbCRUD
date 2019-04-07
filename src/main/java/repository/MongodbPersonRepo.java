package repository;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import model.Person;
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


public class MongodbPersonRepo {

    private static MongoCollection<Person> collection;

    public MongodbPersonRepo(String dbName, String collectionName) throws IOException, URISyntaxException {

        PropertyModel propertyModel = new PropertyModel();

        CodecRegistry pojoCodecRegistry = fromRegistries(MongoClient.getDefaultCodecRegistry(),
                fromProviders(PojoCodecProvider.builder().automatic(true).build()));

        MongoClient mongoClient = new MongoClient(propertyModel.getProperties().getProperty("my.mongodb"), MongoClientOptions
                .builder().codecRegistry(pojoCodecRegistry).build());


        MongoDatabase database = mongoClient.getDatabase(dbName);

        collection = database.getCollection(collectionName, Person.class);
    }

    public List<Person> getAll(){

        return collection.find().into(new LinkedList<>());
    }
    public void addOne(Person person){

        collection.insertOne(person);
    }

    public void delete(Person person){

        collection.deleteOne(eq("email", person.getEmail()));
    }

    public void updateOne(Person person){

        collection.replaceOne(eq("email", person.getEmail()), person);
    }

    public Person findPersonByJson(String json){

        List<Person> people = getAll();

        String[] split = json.split(":");
        String trimmed = split[1].trim();
        String email = trimmed.substring(1);
        email = email.substring(0, email.indexOf("\""));

        for(Person person: people){
            if(person.getEmail().equals(email)){
                return person;
            }
        }
        return null;
    }
    public Person findPersonByEmail(String email){

        List<Person> people = getAll();
        for(Person person: people){
            if(person.getEmail().equals(email)){
                return person;
            }
        }
        return null;
    }
}
