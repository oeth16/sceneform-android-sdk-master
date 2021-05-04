package com.example.sound_zones;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

public class Dialog extends AppCompatDialogFragment {
    private SoundZone _zone;
    private TextInputEditText zoneNameInput;
    private TextInputLayout shapeDropdown;
    private TextInputLayout typeDropdown;
    private AutoCompleteTextView shapeText;
    private AutoCompleteTextView typeText;
    ArrayList<SoundZoneType> types;
    ArrayList<String> shapes;
    ArrayAdapter<SoundZoneType> typesAdapter;
    ArrayAdapter<String> shapeAdapter;
    private MainActivity activity;
    private Button deleteButton;
    public Dialog (SoundZone zone){
        _zone = zone;
    }

    @NonNull
    @Override
    public android.app.Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        activity = (MainActivity) getActivity();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.menu_popup, null);

        builder.setView(view)
                .setTitle(_zone.GetName())
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        })
        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                _zone.SetName(zoneNameInput.getText().toString());
                _zone.SetType(GetSelectedType());
                _zone.SetShape(GetSelectedShape());
                activity.UpdateZoneModel();
            }
        });
        zoneNameInput = view.findViewById(R.id.zoneNameInput);
        zoneNameInput.setText(_zone.GetName());
        shapeText = view.findViewById(R.id.shapeText);
        typeText = view.findViewById(R.id.typeText);
        typeText.setText(_zone.GetType().toString());
        shapeText.setText(_zone.GetShape().toString());
        shapeDropdown = view.findViewById(R.id.shapeDropdown);
        typeDropdown = view.findViewById(R.id.typeDropdown);


        types = new ArrayList<>();
        shapes = new ArrayList<>();

        types.add(SoundZoneType.PRIVATE);
        types.add(SoundZoneType.MIXED);
        types.add(SoundZoneType.SOCIAL);

        shapes.add(SoundZoneShape.DOME.toString());
        shapes.add(SoundZoneShape.EGG.toString());
        shapes.add(SoundZoneShape.ENTITY.toString());
        shapes.add(SoundZoneShape.ORB.toString());



        typesAdapter = new ArrayAdapter<>(getContext(),R.layout.support_simple_spinner_dropdown_item, types);
        shapeAdapter = new ArrayAdapter<>(getContext(),R.layout.support_simple_spinner_dropdown_item, shapes);

        typeText.setAdapter(typesAdapter);
        shapeText.setAdapter(shapeAdapter);

        deleteButton = view.findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.DeleteZone();
                getDialog().dismiss();
            }
        });
        return builder.create();
    }

    private SoundZoneType GetSelectedType(){
        return SoundZoneType.valueOf(typeText.getText().toString());
    }

    private SoundZoneShape GetSelectedShape(){
        return SoundZoneShape.valueOf(shapeText.getText().toString());
    }


}
