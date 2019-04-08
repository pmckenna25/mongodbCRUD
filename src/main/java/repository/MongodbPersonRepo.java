package repository;

import com.mongodb.client.MongoCollection;
import model.Person;

import java.util.LinkedList;
import java.util.List;
import static com.mongodb.client.model.Filters.eq;



public class MongodbPersonRepo {

    private static MongoCollection<Person> collection;

    public MongodbPersonRepo(String dbName, String collectionName){

        MongoConnect connection = new MongoConnect(dbName);
        connection.connect();
        collection = connection.getDatabase().getCollection(collectionName, Person.class);
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
