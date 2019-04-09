package controller;

import model.Person;
import model.PropertyModel;
import repository.MongodbPersonRepo;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
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
    public Response getAllPeople(){

        try {

            List<Person> results = peopleQuery.getAll();
            return Response.ok(results, MediaType.APPLICATION_JSON)
                    .build();

        }catch(NotFoundException e){

            return get404Response("");
        }

    }

    @GET
    @Path("/{email}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOnePerson(@PathParam("email") String email){

        try {

            Person result = peopleQuery.findPersonByEmail(email);
            return Response.ok(result, MediaType.APPLICATION_JSON)
                    .build();

        }catch(NotFoundException e){

            return get404Response(email);
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addNewPerson(Person person){

        if(!checkPerson(person)){

            String result = "{\"Failed\" : \"All fields required\"}";
            return Response.status(500)
                    .entity(result)
                    .type(MediaType.APPLICATION_JSON)
                    .build();

        }else {

            peopleQuery.addOne(person);
            String result = "{\"Success\" : \"" + person.getEmail() + " added\"}";

            return Response.status(201)
                    .entity(result)
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updatePerson(Person person){

        try {
            peopleQuery.updateOne(person);
            String result = "{\"Success\" : \"" + person.getEmail() + " updated\"}";

            return Response.status(200)
                    .entity(result)
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }catch (NotFoundException e){

            return get404Response(person.getEmail());
        }
    }

    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    public Response removePerson(Person person){

        try {
            peopleQuery.delete(person);
            String result = "{\"Success\" : \"" + person.getEmail() + " deleted\"}";
            return Response.status(200)
                    .entity(result)
                    .type(MediaType.APPLICATION_JSON)
                    .build();

        }catch(NotFoundException e){

            return get404Response(person.getEmail());
        }
    }

    private boolean checkPerson(Person person) {

        return person.getEmail()!=null &&
                person.getForename()!=null &&
                person.getSurname()!=null &&
                person.getPassword()!=null;
    }

    private Response get404Response(String id) {

        String result = "{\"Failed\" : \"" + id + " Not Found\"}";
        return Response.status(404)
                .entity(result)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
