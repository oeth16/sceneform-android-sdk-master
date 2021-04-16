package com.example.sound_zones;

import android.widget.Toast;

import com.google.ar.core.Config;
import com.google.ar.core.Session;
import com.google.ar.sceneform.ux.ArFragment;

public class CustomARFragment extends ArFragment {

    public boolean showPlaneDiscoveryController = true;

    @Override
    protected Config getSessionConfiguration(Session session) {
        Config config = new Config(session);
        config.setCloudAnchorMode(Config.CloudAnchorMode.ENABLED);
        config.setFocusMode(Config.FocusMode.AUTO);
        session.configure(config);

        this.getArSceneView().setupSession(session);
        return config;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(showPlaneDiscoveryController == true){
            getPlaneDiscoveryController().show();
        }
        else{
            getPlaneDiscoveryController().hide();
        }
    }
}
