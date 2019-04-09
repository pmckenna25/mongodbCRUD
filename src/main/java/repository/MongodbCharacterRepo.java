package repository;


import com.mongodb.client.MongoCollection;
import model.CharacterClass;

import javax.ws.rs.NotFoundException;
import java.util.LinkedList;
import java.util.List;
import static com.mongodb.client.model.Filters.eq;



public class MongodbCharacterRepo {

    private static MongoCollection<CharacterClass> collection;

    public MongodbCharacterRepo(String dbName, String collectionName){

        MongoConnect connection = new MongoConnect(dbName);
        connection.connect();
        collection = connection.getDatabase().getCollection(collectionName, CharacterClass.class);
    }

    public List<CharacterClass> getAll(){

        List<CharacterClass> fullList = collection.find().into(new LinkedList<>());
        if(fullList.size() == 0){
            throw new NotFoundException();
        }else{
            return fullList;
        }
    }

    public CharacterClass findOne(String characterId){

        List<CharacterClass> characters = getAll();
        for(CharacterClass characterClass: characters){
            if(characterClass.getCharacterId()==Integer.parseInt(characterId)){
                return characterClass;
            }
        }
        throw new NotFoundException();
    }

    public void addOne(CharacterClass character){

        collection.insertOne(character);
    }

    public void delete(CharacterClass character){

        if(collection.find(eq("characterId", character.getCharacterId())).into(new LinkedList<>()).size() == 0){
            throw new NotFoundException();
        }else {
            collection.deleteOne(eq("characterId", character.getCharacterId()));
        }
    }

    public void updateOne(CharacterClass character){

        if(collection.find(eq("email", character.getCharacterId())).into(new LinkedList<>()).size() == 0){
            throw new NotFoundException();
        }else {
            collection.replaceOne(eq("characterId", character.getCharacterId()), character);
        }
    }
}
