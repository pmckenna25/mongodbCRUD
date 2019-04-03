package controller;

import repository.MongodbCharacterRepo;
import model.CharacterClass;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("character")
public class CharacterController {

    private MongodbCharacterRepo characterQuery;

    public CharacterController(){

        characterQuery = new MongodbCharacterRepo("people","characters");
    }
    @GET
    @Path("/list")
    @Produces(MediaType.APPLICATION_JSON)
    public List<CharacterClass> getAllCharacters(){

        return characterQuery.getAll();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void addNewCharacter(CharacterClass character){

        characterQuery.addOne(character);
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void updateCharacter(CharacterClass character){

        characterQuery.updateOne(character);
    }

    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    public void removeCharacter(CharacterClass character){

        characterQuery.delete(character);
    }
}
