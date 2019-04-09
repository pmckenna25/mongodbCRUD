package repository;

import com.mongodb.client.MongoCollection;
import model.Person;

import javax.ws.rs.NotFoundException;
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

        List<Person> fullList = collection.find().into(new LinkedList<>());
        if(fullList.size() == 0){
            throw new NotFoundException();
        }else{
            return fullList;
        }
    }
    public void addOne(Person person){

        collection.insertOne(person);
    }

    public void delete(Person person){

        if(collection.find(eq("email", person.getEmail())).into(new LinkedList<>()).size() == 0){
            throw new NotFoundException();
        }else {
            collection.deleteOne(eq("email", person.getEmail()));
        }
    }

    public void updateOne(Person person){

        if(collection.find(eq("email", person.getEmail())).into(new LinkedList<>()).size() == 0){
            throw new NotFoundException();
        }else {
            collection.replaceOne(eq("email", person.getEmail()), person);
        }
    }

    public Person findPersonByEmail(String email){

        List<Person> people = getAll();
        for(Person person: people){
            if(person.getEmail().equals(email)){
                return person;
            }
        }
        throw new NotFoundException();
    }
}
