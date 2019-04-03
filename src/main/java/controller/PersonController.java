package controller;

import model.Person;
import repository.MongodbPersonRepo;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("person")
public class PersonController {

    private MongodbPersonRepo peopleQuery;

    public PersonController(){

        peopleQuery = new MongodbPersonRepo("people", "person");
    }

    @GET
    @Path("/list")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Person> getAllPeople(){

        return peopleQuery.getAll();
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
