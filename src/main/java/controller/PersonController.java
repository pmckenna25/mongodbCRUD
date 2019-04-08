package controller;

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
import javax.ws.rs.core.Response;
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
    public Response addNewPerson(Person person){

        peopleQuery.addOne(person);
        String result = "{\"Response\" : \""+person.getEmail()+" added\"}";

        return Response.status(201)
                .entity(result)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updatePerson(Person person){

        peopleQuery.updateOne(person);
        String result = "{\"Response\" : \""+person.getEmail()+" updated\"}";

        return Response.status(200)
                .entity(result)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }

    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    public Response removePerson(Person person){

        peopleQuery.delete(person);
        String result = "{\"Response\" : \""+person.getEmail()+" deleted\"}";

        return Response.status(200)
                .entity(result)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
