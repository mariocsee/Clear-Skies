package mariocsee.android.worldwideweather.fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import mariocsee.android.worldwideweather.MainActivity;
import mariocsee.android.worldwideweather.R;
import mariocsee.android.worldwideweather.data.City;

/**
 * Created by mariocsee on 12/1/16.
 */

public class AddCityFragment extends DialogFragment {

    private OnAddCityAnswer onAddCityAnswer = null;

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
        View dialogLayout = inflater.inflate(R.layout.layout_dialog,null);
        final EditText etCity = (EditText) dialogLayout.findViewById(R.id.etCity);
        alertDialogBuilder.setView(dialogLayout);


        alertDialogBuilder.setTitle("Add a city");
        alertDialogBuilder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                ((MainActivity)getActivity()).saveCity(new City(etCity.getText().toString()));
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

}
