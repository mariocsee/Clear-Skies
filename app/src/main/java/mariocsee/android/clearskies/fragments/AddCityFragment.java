package mariocsee.android.clearskies.fragments;

import android.app.Dialog;

import android.content.Context;
import android.content.DialogInterface;

import android.os.Bundle;

import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import android.view.LayoutInflater;
import android.view.View;

import android.widget.AutoCompleteTextView;
import android.widget.Toast;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.stream.JsonReader;
import java.io.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import mariocsee.android.clearskies.MainActivity;
import mariocsee.android.clearskies.R;
import mariocsee.android.clearskies.data.City;

/**
 * Created by mariocsee on 12/1/16.
 */

public class AddCityFragment extends DialogFragment {

    private OnAddCityAnswer onAddCityAnswer = null;

    private List<String> currentCities;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof OnAddCityAnswer) {
            onAddCityAnswer = (OnAddCityAnswer) context;
        } else {
            throw new RuntimeException(
                    "This Activity is not implementing the " +
                            "OnAddCityAnswer interface");
        }

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String message = getArguments().getString(MainActivity.KEY_MSG);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                getActivity());

        LayoutInflater inflater = LayoutInflater.from(getContext());
        View dialogLayout = inflater.inflate(R.layout.layout_dialog, null);

        final AutoCompleteTextView autocompleteCity = (AutoCompleteTextView) dialogLayout.findViewById(R.id.autocompleteCity);

        InputStream currentCitiesInputStream = getResources().openRawResource(R.raw.current_city_list);

        try {
            currentCities = readJsonStream(currentCitiesInputStream);
        } catch(IOException e) {
            e.printStackTrace();
        }

        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(((MainActivity)getActivity()), android.R.layout.simple_list_item_1, currentCities);

        autocompleteCity.setAdapter(adapter);

        alertDialogBuilder.setView(dialogLayout);
        alertDialogBuilder.setTitle("Add a city");

        alertDialogBuilder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                ((MainActivity)getActivity()).saveCity(new City(autocompleteCity.getText().toString()));
                onAddCityAnswer.onPositiveSelected();
            }
        });

        alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        return alertDialogBuilder.create();
    }

    public List<String> readJsonStream(InputStream in) throws IOException {
        JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
        try {
            return readCitiesArray(reader);
        } finally {
            reader.close();
        }
    }

    public List<String> readCitiesArray(JsonReader reader) throws IOException {
        List<String> cities = new ArrayList<String>();

        reader.beginArray();
        while (reader.hasNext()) {
            cities.add(readCity(reader));
        }
        reader.endArray();
        return cities;
    }

    public String readCity(JsonReader reader) throws IOException {
        String city = null;

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
             if (name.equals("name")) {
                city = reader.nextString();
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return city;
    }

}
