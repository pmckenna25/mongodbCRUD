package controller;

import model.CharacterClass;
import model.PropertyModel;
import repository.MongodbAPIRepo;
import model.API_Key;
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
    public List<API_Key> getAllKeys(){

        return apiQuery.getAll();
    }

    @GET
    @Path("/{email}")
    @Produces(MediaType.APPLICATION_JSON)
    public API_Key getOneCharacter(@PathParam("email") String email){

        return apiQuery.findOne(email);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void addNewKey(API_Key key){

        apiQuery.addOne(key);
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void updateCharacter(API_Key key){

        apiQuery.updateOne(key);
    }

    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    public void removeCharacter(API_Key key){

        apiQuery.delete(key);
    }
}
