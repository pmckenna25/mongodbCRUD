package controller;

import model.PropertyModel;
import repository.MongodbAPIRepo;
import repository.MongodbCharacterRepo;
import repository.MongodbPersonRepo;
import model.API_Key;
import model.CharacterClass;
import model.Person;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;

@Path("people/characters")
public class CrossCollectionController {

    private MongodbPersonRepo peopleQuery;
    private MongodbCharacterRepo characterQuery;
    private MongodbAPIRepo apiQuery;

    public CrossCollectionController() throws IOException, URISyntaxException {

        PropertyModel propertyModel = new PropertyModel();

        peopleQuery = new MongodbPersonRepo(propertyModel.getProperties().getProperty("my.dbname"),
                propertyModel.getProperties().getProperty("my.person.collection"));

        characterQuery = new MongodbCharacterRepo(propertyModel.getProperties().getProperty("my.dbname"),
                propertyModel.getProperties().getProperty("my.character.collection"));

        apiQuery = new MongodbAPIRepo(propertyModel.getProperties().getProperty("my.dbname"),
                propertyModel.getProperties().getProperty("my.api.collection"));
    }

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public List<CharacterClass> getPersonCharacters(String json){

        return getAllPersonOwnedCharacters(peopleQuery.findPersonByJson(json));
    }

    @GET
    @Path("/{email}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<CharacterClass> getEmailCharacters(@PathParam("email") String email){

        return getAllPersonOwnedCharacters(peopleQuery.findPersonByEmail(email));
    }

    private List<CharacterClass> getAllPersonOwnedCharacters(Person person) {

        List<API_Key> keys = apiQuery.getAll();
        List<CharacterClass> characters = characterQuery.getAll();

        List<Integer> characterIds = new LinkedList<>();
        List<CharacterClass> results = new LinkedList<>();

        for(API_Key key: keys){
            if(key.getEmail().equals(person.getEmail())){
                characterIds = key.getCharacters();
                break;
            }
        }

        for(Integer characterId: characterIds){
            for(CharacterClass character: characters){

                if(character.getCharacterId()==characterId){
                    results.add(character);
                }
            }
        }
        return results;
    }
}
