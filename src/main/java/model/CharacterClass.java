package model;

import java.util.Objects;

public final class CharacterClass {

    private int characterId;
    private String characterName;
    private String characterClass;
    private int level;
    private Attributes attributes;

    public CharacterClass(){
    }

    public CharacterClass(int characterId, String characterName, String characterClass,
                          int level, Attributes attributes) {
        this.characterId = characterId;
        this.characterName = characterName;
        this.characterClass = characterClass;
        this.level = level;
        this.attributes = attributes;
    }

    public String getCharacterClass() {
        return characterClass;
    }

    public void setCharacterClass(String characterClass) {
        this.characterClass = characterClass;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public Attributes getAttributes() {
        return attributes;
    }

    public void setAttributes(Attributes attributes) {
        this.attributes = attributes;
    }

    public int getCharacterId() {
        return characterId;
    }

    public void setCharacterId(int characterId) {
        this.characterId = characterId;
    }

    public String getCharacterName() {
        return characterName;
    }

    public void setCharacterName(String characterName) {
        this.characterName = characterName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CharacterClass)) return false;
        CharacterClass that = (CharacterClass) o;
        return getCharacterId() == that.getCharacterId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCharacterId());
    }
}
