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

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

public class Dialog extends AppCompatDialogFragment {
    private SoundZone _zone;
    private TextInputEditText zoneNameInput;
    private TextInputLayout shapeDropdown;
    private TextInputLayout typeDropdown;
    private TextInputLayout swapDropdown;
    private AutoCompleteTextView shapeText;
    private AutoCompleteTextView typeText;
    private AutoCompleteTextView swapDropdownText;
    ArrayList<SoundZoneType> types;
    ArrayList<String> shapes;
    ArrayList<String> swapTargets;
    ArrayList<SoundZone> swapZones;
    ArrayAdapter<SoundZoneType> typesAdapter;
    ArrayAdapter<String> shapeAdapter;
    ArrayAdapter<String> swapTargetsAdapter;
    private MainActivity activity;
    private Button deleteButton;
    private MaterialButton duplicateButton;
    private MaterialButton swapButton;
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
            .setTitle("Sound Zone Editor")
            .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        })
        .setNeutralButton("delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                activity.DeleteZone();
                getDialog().dismiss();
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
        //swapDropdownText = view.findViewById(R.id.swapDropdownText);

        typeText.setText(_zone.GetType().toString());
        shapeText.setText(_zone.GetShape().toString());


        shapeDropdown = view.findViewById(R.id.shapeDropdown);
        typeDropdown = view.findViewById(R.id.typeDropdown);
        //swapDropdown = view.findViewById(R.id.swapDropdown);


        types = new ArrayList<>();
        shapes = new ArrayList<>();
        //swapTargets = new ArrayList<>();
        //swapZones = new ArrayList<>();

        types.add(SoundZoneType.PRIVATE);
        types.add(SoundZoneType.MIXED);
        types.add(SoundZoneType.SOCIAL);

        shapes.add(SoundZoneShape.DOME.toString());
        shapes.add(SoundZoneShape.EGG.toString());
        shapes.add(SoundZoneShape.ENTITY.toString());
        shapes.add(SoundZoneShape.ORB.toString());

        /*
        if(activity.soundZones[0] != null && _zone != activity.soundZones[0]){
            swapTargets.add(activity.soundZones[0].GetName());
            swapZones.add(activity.soundZones[0]);
        }
        if(activity.soundZones[1] != null && _zone != activity.soundZones[1]){
            swapTargets.add(activity.soundZones[1].GetName());
            swapZones.add(activity.soundZones[1]);
        }
        if(activity.soundZones[2] != null && _zone != activity.soundZones[2]){
            swapTargets.add(activity.soundZones[2].GetName());
            swapZones.add(activity.soundZones[2]);
        }
        */

        //swapDropdownText.setText(swapTargets.get(0));


        typesAdapter = new ArrayAdapter<>(getContext(),R.layout.support_simple_spinner_dropdown_item, types);
        shapeAdapter = new ArrayAdapter<>(getContext(),R.layout.support_simple_spinner_dropdown_item, shapes);
        swapTargetsAdapter = new ArrayAdapter<>(getContext(),R.layout.support_simple_spinner_dropdown_item, swapTargets);

        typeText.setAdapter(typesAdapter);
        shapeText.setAdapter(shapeAdapter);
        //swapDropdownText.setAdapter(swapTargetsAdapter);

        duplicateButton = (MaterialButton)view.findViewById(R.id.duplicateButton);
        //swapButton = (MaterialButton)view.findViewById(R.id.swapButton);

        duplicateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.DuplicateZone(_zone);
                getDialog().dismiss();
            }
        });

        /*
        swapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.SwapZones(swapDropdownText.getText().toString(), _zone);
                getDialog().dismiss();
            }
        });

         */
        /*
                deleteButton = view.findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.DeleteZone();
                getDialog().dismiss();
            }
        });
         */


        return builder.create();
    }

    private SoundZoneType GetSelectedType(){
        return SoundZoneType.valueOf(typeText.getText().toString());
    }

    private SoundZoneShape GetSelectedShape(){
        return SoundZoneShape.valueOf(shapeText.getText().toString());
    }


}
