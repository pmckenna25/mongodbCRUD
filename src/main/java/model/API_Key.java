package model;

import java.util.List;
import java.util.Objects;

public final class API_Key {

    private String email;
    private String api;
    private List<Integer> characters;

    public API_Key() {
    }

    public API_Key(String email, String api, List<Integer> characters) {
        this.email = email;
        this.api = api;
        this.characters = characters;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getApi() {
        return api;
    }

    public void setApi(String api) {
        this.api = api;
    }

    public List<Integer> getCharacters() {
        return characters;
    }

    public void setCharacters(List<Integer> characters) {
        this.characters = characters;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof API_Key)) return false;
        API_Key key = (API_Key) o;
        return Objects.equals(getApi(), key.getApi());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getApi());
    }
}
