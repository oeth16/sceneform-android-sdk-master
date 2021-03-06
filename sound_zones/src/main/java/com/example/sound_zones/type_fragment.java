package com.example.sound_zones;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;


public class type_fragment extends Fragment {

    private MaterialCardView privateButton;
    private MaterialCardView mixedButton;
    private MaterialCardView socialButton;
    private MaterialButton typeHelpButton;
    private MainActivity activity;
    public SoundZoneType selectionType = SoundZoneType.NONE;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_type_fragment, container, false);

        privateButton = (MaterialCardView)view.findViewById(R.id.SelectPrivateButton);
        mixedButton = (MaterialCardView) view.findViewById(R.id.SelectMixedButton);
        socialButton = (MaterialCardView)view.findViewById(R.id.SelectSocialButton);
        typeHelpButton = (MaterialButton)view.findViewById(R.id.typeHelpButton);

        activity = (MainActivity) getActivity();

        privateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.selectionType = SoundZoneType.PRIVATE;
                activity.NavigateShapeSelection();
            }
        });

        mixedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.selectionType = SoundZoneType.MIXED;
                activity.NavigateShapeSelection();
            }
        });

        socialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.selectionType = SoundZoneType.SOCIAL;
                activity.NavigateShapeSelection();
            }
        });

        typeHelpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Feature", "Clicked on help");
                activity.NavigatehelpFragment();
            }
        });

        return view;
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}