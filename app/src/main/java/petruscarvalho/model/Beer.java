package petruscarvalho.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by petruscarvalho on 14/04/17.
 */

public class Beer {

    @SerializedName("name")
    @Expose
    private String beerName;

    @SerializedName("tagline")
    @Expose
    private String beerTag;

    @SerializedName("description")
    @Expose
    private String beerDesc;

    @SerializedName("image_url")
    @Expose
    private String beerImage;

    public Beer(String beerName) {
        this.beerName = beerName;
    }

    public String getBeerName() {
        return beerName;
    }

    public void setBeerName(String beerName) {
        this.beerName = beerName;
    }

    public String getBeerImage() {
        return beerImage;
    }

    public void setBeerImage(String beerImage) {
        this.beerImage = beerImage;
    }

    public String getBeerDesc() {
        return beerDesc;
    }

    public void setBeerDesc(String beerDesc) {
        this.beerDesc = beerDesc;
    }

    public String getBeerTag() {
        return beerTag;
    }

    public void setBeerTag(String beerTag) {
        this.beerTag = beerTag;
    }
}
