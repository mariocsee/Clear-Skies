package mariocsee.android.worldwideweather.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

import mariocsee.android.worldwideweather.R;
import mariocsee.android.worldwideweather.ViewWeatherActivity;
import mariocsee.android.worldwideweather.model.WeatherResult;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by mariocsee on 12/1/16.
 */

public class FragmentDetails extends Fragment {

    private TextView tvDate;
    private TextView tvTempMin;
    private TextView tvTempMax;
    private TextView tvHumid;
    private TextView tvWindSpeed;
    private TextView tvSunrise;
    private TextView tvSunset;
    private Call<WeatherResult> weatherResult;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_details, null);
        weatherResult = ((ViewWeatherActivity)getContext()).getWeatherCall();

        tvDate = (TextView) rootView.findViewById(R.id.tvDate);
        tvTempMin = (TextView) rootView.findViewById(R.id.tvTempMin);
        tvTempMax = (TextView) rootView.findViewById(R.id.tvTempMax);
        tvHumid = (TextView) rootView.findViewById(R.id.tvHumid);
        tvWindSpeed = (TextView) rootView.findViewById(R.id.tvWindSpeed);
        tvSunrise = (TextView) rootView.findViewById(R.id.tvSunrise);
        tvSunset = (TextView) rootView.findViewById(R.id.tvSunset);

        setValues();

        return rootView;
    }
    private void setDate() {
        long date = System.currentTimeMillis();

        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy");
        String currDate = sdf.format(date);
        tvDate.setText(currDate);
    }

    private void setValues() {
        setDate();
        weatherResult.clone().enqueue(new Callback<WeatherResult>() {
            @Override
            public void onResponse(Call<WeatherResult> call, Response<WeatherResult> response) {
                String tempMin = (response.body().getMain().getTempMin()).toString();
                tvTempMin.setText(getString(R.string.min_temp, tempMin));

                String tempMax = (response.body().getMain().getTempMax()).toString();
                tvTempMax.setText(getString(R.string.max_temp, tempMax));

                String humid = (response.body().getMain().getHumidity()).toString();
                tvHumid.setText(getString(R.string.humidity, humid));

                String windSpeed = (response.body().getWind().getSpeed()).toString();
                tvWindSpeed.setText(getString(R.string.wind_speed, windSpeed));

                Long sunriseNah = Long.valueOf(response.body().getSys().getSunrise());
                Date sunriseDate = new Date(1000 * sunriseNah);
                String sunrise = new SimpleDateFormat("MM dd, yyyy hh:mma").format(sunriseDate);
                tvSunrise.setText(getString(R.string.sunrise, sunrise));

                Long sunsetNah = Long.valueOf(response.body().getSys().getSunset());
                Date sunsetDate = new Date(1000 * sunsetNah);
                String sunset = new SimpleDateFormat("MM dd, yyyy hh:mma").format(sunsetDate);
                tvSunset.setText(getString(R.string.sunset, sunset));
            }

            @Override
            public void onFailure(Call<WeatherResult> call, Throwable t) {
                Toast.makeText(getContext(), "City not recognized",
                        Toast.LENGTH_SHORT).show();
                Log.d("TAG_API", t.toString());
            }
        });
    }
}
