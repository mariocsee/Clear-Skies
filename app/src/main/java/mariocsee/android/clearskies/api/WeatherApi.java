package mariocsee.android.clearskies.api;

import mariocsee.android.clearskies.model.WeatherResult;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by mariocsee on 12/1/16.
 */

public interface WeatherApi {
    @GET("data/2.5/weather")
    Call<WeatherResult> getCityWeather(
//                                    @Query("base") String base,
            @Query("q") String base,
            @Query("units") String units,
            @Query("APPID") String apiid);
}
