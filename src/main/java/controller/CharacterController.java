package controller;

import model.PropertyModel;
import repository.MongodbCharacterRepo;
import model.CharacterClass;
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

@Path("character")
public class CharacterController {

    private MongodbCharacterRepo characterQuery;

    public CharacterController(){

        PropertyModel propertyModel = new PropertyModel();

        String dbName = propertyModel.getDatabaseName();
        String collectionName = propertyModel.getProperties().getProperty("my.character.collection");

        characterQuery = new MongodbCharacterRepo(dbName, collectionName);
    }
    @GET
    @Path("/list")
    @Produces(MediaType.APPLICATION_JSON)
    public List<CharacterClass> getAllCharacters(){

        return characterQuery.getAll();
    }

    @GET
    @Path("/{characterId}")
    @Produces(MediaType.APPLICATION_JSON)
    public CharacterClass getOneCharacter(@PathParam("characterId") String characterId){

        return characterQuery.findOne(characterId);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addNewCharacter(CharacterClass character){

        characterQuery.addOne(character);
        String result = "{\"Response\" : \""+character.getCharacterId()+" added\"}";
        return Response.status(201)
                .entity(result)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateCharacter(CharacterClass character){

        characterQuery.updateOne(character);
        String result = "{\"Response\" : \""+character.getCharacterId()+" updated\"}";
        return Response.status(204)
                .entity(result)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }

    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    public Response removeCharacter(CharacterClass character){

        characterQuery.delete(character);
        String result = "{\"Response\" : \""+character.getCharacterId()+" deleted\"}";
        return Response.status(204)
                .entity(result)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
