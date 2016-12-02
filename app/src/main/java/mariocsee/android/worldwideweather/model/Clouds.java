package mariocsee.android.worldwideweather.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mariocsee on 12/1/16.
 */

public class Clouds {

    @SerializedName("all")
    @Expose
    public Integer all;

    public Integer getAll() {
        return all;
    }

    public void setAll(Integer all) {
        this.all = all;
    }
}