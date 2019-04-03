package model;

public final class Attributes {

    private int strength;
    private int constitution;
    private int dexterity;

    public Attributes(){

    }

    public Attributes(int strength, int constitution, int dexterity) {
        this.strength = strength;
        this.constitution = constitution;
        this.dexterity = dexterity;
    }

    public int getStrength() {
        return strength;
    }

    public int getConstitution() {
        return constitution;
    }

    public int getDexterity() {
        return dexterity;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public void setConstitution(int constitution) {
        this.constitution = constitution;
    }

    public void setDexterity(int dexterity) {
        this.dexterity = dexterity;
    }
}
