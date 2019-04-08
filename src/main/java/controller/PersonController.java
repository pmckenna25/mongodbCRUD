package controller;

import model.CharacterClass;
import model.Person;
import model.PropertyModel;
import repository.MongodbPersonRepo;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("person")
public class PersonController {

    private MongodbPersonRepo peopleQuery;

    public PersonController() {

        PropertyModel propertyModel = new PropertyModel();

        String dbName = propertyModel.getDatabaseName();
        String collectionName = propertyModel.getProperties().getProperty("my.person.collection");

        peopleQuery = new MongodbPersonRepo(dbName, collectionName);
    }

    @GET
    @Path("/list")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Person> getAllPeople(){

        return peopleQuery.getAll();
    }

    @GET
    @Path("/{email}")
    @Produces(MediaType.APPLICATION_JSON)
    public Person getOneCharacter(@PathParam("email") String email){

        return peopleQuery.findPersonByEmail(email);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void addNewPerson(Person person){

        peopleQuery.addOne(person);
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void updatePerson(Person person){

        peopleQuery.updateOne(person);
    }

    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    public void removePerson(Person person){

        peopleQuery.delete(person);
    }
}
