package controller;

import model.PropertyModel;
import repository.MongodbCharacterRepo;
import model.CharacterClass;
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
    public Response getAllCharacters(){

        try {

            List<CharacterClass> results = characterQuery.getAll();
            return Response.ok(results, MediaType.APPLICATION_JSON)
                    .build();

        }catch(NotFoundException e){

            String result = "{\"Failed\" : \" Not found\"}";
            return Response.status(404)
                    .entity(result)
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }
    }

    @GET
    @Path("/{characterId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOneCharacter(@PathParam("characterId") String characterId){

        try {

            CharacterClass result = characterQuery.findOne(characterId);
            return Response.ok(result, MediaType.APPLICATION_JSON)
                    .build();

        }catch(NotFoundException e){

            return get404Response(Integer.parseInt(characterId));
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addNewCharacter(CharacterClass character){

        if(!checkCharacter(character)) {

            String result = "{\"Failed\" : \"All fields required\"}";
            return Response.status(500)
                    .entity(result)
                    .type(MediaType.APPLICATION_JSON)
                    .build();

        }else {

            characterQuery.addOne(character);
            String result = "{\"Success\" : \"" + character.getCharacterId() + " added\"}";

            return Response.status(201)
                    .entity(result)
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }
    }


    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateCharacter(CharacterClass character){

        try {
            characterQuery.updateOne(character);
            String result = "{\"Response\" : \"" + character.getCharacterId() + " updated\"}";
            return Response.status(204)
                    .entity(result)
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }catch (NotFoundException e){

            return get404Response(character.getCharacterId());
        }
    }

    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    public Response removeCharacter(CharacterClass character){

        try {
            characterQuery.delete(character);
            String result = "{\"Success\" : \"" + character.getCharacterId() + " deleted\"}";
            return Response.status(200)
                    .entity(result)
                    .type(MediaType.APPLICATION_JSON)
                    .build();

        }catch(NotFoundException e){

            return get404Response(character.getCharacterId());
        }
    }

    private boolean checkCharacter(CharacterClass character) {

        return character.getCharacterId()!= 0 &&
                character.getLevel()!= 0 &&
                character.getAttributes()!= null &&
                character.getAttributes().getStrength()!= 0 &&
                character.getAttributes().getConstitution()!= 0 &&
                character.getAttributes().getDexterity()!= 0 &&
                character.getCharacterName()!= null &&
                character.getCharacterClass()!= null;
    }

    private Response get404Response(int id) {

        String result = "{\"Failed\" : \"" + id + " Not Found\"}";
        return Response.status(404)
                .entity(result)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
