package es.appmaster.fifamaster.model;

import com.google.gson.annotations.SerializedName;

/**
 * Player entity
 *
 * @author manolovn
 */
public class Player {

    private String resourceId;
    private String baseId;
    private String firstName;
    private String lastName;
    private String nationId;

    @SerializedName("attribute1")
    private int pace;

    public String getBaseId() {
        return baseId;
    }

    public void setBaseId(String baseId) {
        this.baseId = baseId;
    }

    public Player(String resourceId, String firstName, String lastName) {
        this.resourceId = resourceId;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPlayerPhoto() {
        return "http://cdn.content.easports.com/fifa/fltOnlineAssets/C74DDF38-0B11-49b0-B199-2E2A11D1CC13/2014/fut/items/images/players/web/" + this.getBaseId() + ".png";
    }

    public String getCountryFlag() {
        return "http://cdn.content.easports.com/fifa/fltOnlineAssets/C74DDF38-0B11-49b0-B199-2E2A11D1CC13/2014/fut/items/images/cardflagssmall/web/" + this.getNationId()  + ".png";
    }

    public int getPace() {
        return pace;
    }

    public void setPace(int pace) {
        this.pace = pace;
    }

    public String getNationId() {
        return nationId;
    }

    public void setNationId(String nationId) {
        this.nationId = nationId;
    }
}
