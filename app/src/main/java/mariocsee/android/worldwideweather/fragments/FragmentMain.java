package mariocsee.android.worldwideweather.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;

import mariocsee.android.worldwideweather.R;
import mariocsee.android.worldwideweather.ViewWeatherActivity;
import mariocsee.android.worldwideweather.model.WeatherResult;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by mariocsee on 12/1/16.
 */

public class FragmentMain extends Fragment {

    public static final String TAG_API = "TAG_API";
    private TextView tvTemperature;
    private TextView tvDescription;
    private TextView tvLocation;
    private ImageView ivWeatherIcon;
    private Call<WeatherResult> weatherResult;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_main, null);

        weatherResult = ((ViewWeatherActivity) getContext()).getWeatherCall();

        tvTemperature = (TextView) rootView.findViewById(R.id.tvTemperature);
        tvDescription = (TextView) rootView.findViewById(R.id.tvDescription);
        tvLocation = (TextView) rootView.findViewById(R.id.tvLocation);
        ivWeatherIcon = (ImageView) rootView.findViewById(R.id.ivWeatherIcon);

        setValues();

        return rootView;
    }

    private void setValues() {
        weatherResult.clone().enqueue(new Callback<WeatherResult>() {
            @Override
            public void onResponse(Call<WeatherResult> call, Response<WeatherResult> response) {
                String idIcon = response.body().getWeather().get(0).getIcon();
                String url = "http://openweathermap.org/img/w/" + idIcon + ".png";
                Glide.with(getActivity()).load(url).into(ivWeatherIcon);

                String temperature = (response.body().getMain().getTemp()).toString();
                tvTemperature.setText(temperature);

                String description = response.body().getWeather().get(0).getDescription();
                description = description.toUpperCase();
                tvDescription.setText(description);

                String cityName = response.body().getName();
                String cityCountry = response.body().getSys().getCountry();
                tvLocation.setText(getString(R.string.city_country, cityName, cityCountry));
            }

            @Override
            public void onFailure(Call<WeatherResult> call, Throwable t) {
                Toast.makeText(getContext(), "City not recognized",
                        Toast.LENGTH_SHORT).show();
                Log.d(TAG_API, t.toString());
            }
        });
    }
}
