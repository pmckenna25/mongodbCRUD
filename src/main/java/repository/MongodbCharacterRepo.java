package repository;


import com.mongodb.client.MongoCollection;
import model.CharacterClass;
import model.Person;

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

        return collection.find().into(new LinkedList<>());
    }

    public CharacterClass findOne(String characterId){

        List<CharacterClass> characters = getAll();
        for(CharacterClass characterClass: characters){
            if(characterClass.getCharacterId()==Integer.parseInt(characterId)){
                return characterClass;
            }
        }
        return null;
    }

    public void addOne(CharacterClass character){

        collection.insertOne(character);
    }

    public void delete(CharacterClass character){

        collection.deleteOne(eq("characterId", character.getCharacterId()));
    }

    public void updateOne(CharacterClass character){

        collection.replaceOne(eq("characterId", character.getCharacterId()), character);
    }
}
