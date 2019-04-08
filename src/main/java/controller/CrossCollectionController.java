package controller;

import model.PropertyModel;
import repository.MongodbAPIRepo;
import repository.MongodbCharacterRepo;
import repository.MongodbPersonRepo;
import model.API_Key;
import model.CharacterClass;
import model.Person;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.LinkedList;
import java.util.List;

@Path("people/characters")
public class CrossCollectionController {

    private MongodbPersonRepo peopleQuery;
    private MongodbCharacterRepo characterQuery;
    private MongodbAPIRepo apiQuery;

    public CrossCollectionController(){

        PropertyModel propertyModel = new PropertyModel();
        String dbName = propertyModel.getDatabaseName();
        String personCollection = propertyModel.getProperties().getProperty("my.person.collection");
        String characterCollection = propertyModel.getProperties().getProperty("my.character.collection");
        String apiCollection = propertyModel.getProperties().getProperty("my.api.collection");

        peopleQuery = new MongodbPersonRepo(dbName, personCollection);

        characterQuery = new MongodbCharacterRepo(dbName, characterCollection);

        apiQuery = new MongodbAPIRepo(dbName, apiCollection);
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
