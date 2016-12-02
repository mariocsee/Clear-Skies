package mariocsee.android.worldwideweather.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import mariocsee.android.worldwideweather.MainActivity;
import mariocsee.android.worldwideweather.R;
import mariocsee.android.worldwideweather.ViewWeatherActivity;
import mariocsee.android.worldwideweather.data.City;
import mariocsee.android.worldwideweather.touch.CityTouchHelperAdapter;


/**
 * Created by mariocsee on 11/29/16.
 */

public class CityRecyclerAdapter extends
        RecyclerView.Adapter<CityRecyclerAdapter.ViewHolder>
        implements CityTouchHelperAdapter {

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivWeatherIcon;
        private TextView tvCity;
        private TextView tvTemperature;
        private TextView tvWeather;
        private TextView tvTime;
        private Button btnDeleteCity;

        public ViewHolder(View itemView) {
            super(itemView);
            tvCity = (TextView) itemView.findViewById(R.id.tvCityRow);
            btnDeleteCity = (Button) itemView.findViewById(R.id.btnDeleteCity);
            itemView.setClickable(true);
        }
    }

    private List<City> citiesList;
    private Context context;

    public CityRecyclerAdapter(List<City> citiesList, Context context) {
        this.citiesList = citiesList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View cityRow = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.city_row, parent, false);
        return new ViewHolder(cityRow);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final String city = citiesList.get(position).getCityName();
        holder.tvCity.setText(citiesList.get(holder.getAdapterPosition()).getCityName());

        holder.btnDeleteCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemDismiss(holder.getAdapterPosition());
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) context).showWeatherPagerActivity(city);
            }
        });

    }

    @Override
    public int getItemCount() {
        return citiesList.size();
    }

    @Override
    public void onItemDismiss(int position) {
        citiesList.get(position).delete();
        citiesList.remove(position);
        notifyDataSetChanged();
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        citiesList.add(toPosition, citiesList.get(fromPosition));
        citiesList.remove(fromPosition);
        notifyItemMoved(fromPosition, toPosition);
    }

    public void addCity(City city) {
        city.save();
        citiesList.add(city);
        notifyDataSetChanged();
    }


}
