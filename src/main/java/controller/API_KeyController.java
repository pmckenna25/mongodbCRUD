package controller;

import model.PropertyModel;
import repository.MongodbAPIRepo;
import model.API_Key;
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

@Path("key")
public class API_KeyController {

    private MongodbAPIRepo apiQuery;

    public API_KeyController() {

        PropertyModel propertyModel = new PropertyModel();

        String dbName = propertyModel.getDatabaseName();
        String collectionName = propertyModel.getProperties().getProperty("my.api.collection");

        apiQuery = new MongodbAPIRepo(dbName, collectionName);
    }
    @GET
    @Path("/list")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllKeys(){

        try {

            List<API_Key> results = apiQuery.getAll();
            return Response.ok(results, MediaType.APPLICATION_JSON)
                    .build();

        }catch(NotFoundException e){

            return get404Response("");
        }
    }

    @GET
    @Path("/{email}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOneKey(@PathParam("email") String email){

        try {

            API_Key result = apiQuery.findOne(email);
            return Response.ok(result, MediaType.APPLICATION_JSON)
                    .build();

        }catch(NotFoundException e){

            return get404Response(email);
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addNewKey(API_Key key){

        if(!checkKey(key)){

            String result = "{\"Failed\" : \"All fields required\"}";
            return Response.status(500)
                    .entity(result)
                    .type(MediaType.APPLICATION_JSON)
                    .build();

        }else {

            apiQuery.addOne(key);
            String result = "{\"Success\" : \"" + key.getEmail() + " added\"}";

            return Response.status(201)
                    .entity(result)
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }
    }


    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateCharacter(API_Key key){

        try {
            apiQuery.updateOne(key);
            String result = "{\"Success\" : \"" + key.getEmail() + " updated\"}";

            return Response.status(200)
                    .entity(result)
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }catch (NotFoundException e){

            return get404Response(key.getEmail());
        }
    }

    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    public Response removeCharacter(API_Key key){

        try {
            apiQuery.delete(key);
            String result = "{\"Success\" : \"" + key.getEmail() + " deleted\"}";
            return Response.status(200)
                    .entity(result)
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }catch (NotFoundException e){

            return get404Response(key.getEmail());
        }
    }

    private boolean checkKey(API_Key key) {

        return key.getEmail()!=null &&
                key.getCharacters()!=null &&
                key.getCharacters().size()!= 0 &&
                key.getApi()!=null;
    }

    private Response get404Response(String id) {

        String result = "{\"Failed\" : \"" + id + " Not Found\"}";
        return Response.status(404)
                .entity(result)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
