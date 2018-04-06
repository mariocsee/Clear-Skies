package mariocsee.android.clearskies.data;

import com.orm.SugarRecord;

import java.io.Serializable;

/**
 * Created by mariocsee on 12/1/16.
 */

public class City extends SugarRecord implements Serializable {

    private String cityName;

    public City() {

    }

    public City(String cityName) {
        this.cityName = cityName;

    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

}
