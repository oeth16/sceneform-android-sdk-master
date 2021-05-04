package com.example.sound_zones;

import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.ux.TransformableNode;

public class SoundZone {
    private String _name;
    private SoundZoneShape _shape;
    private SoundZoneType _type;
    private String _model;
    private int _zoneID;
    private ModelRenderable _render;
    private TransformableNode _transformableNode;
    private AnchorNode _anchorNode;
    public boolean _overlapping = false;

    private boolean checker = true;
    private boolean transparent = false;
    private boolean icons = false;

    public SoundZone(String name, SoundZoneShape shape, SoundZoneType type, TransformableNode node){
        _name = name;
        _shape = shape;
        _type = type;
        _transformableNode = node;
        if(_overlapping == false){
            SetType();
        }
        else{
            SetInvalidType();
        }
    }



    public void SetAnchorNode(AnchorNode anchorNode){
        _anchorNode = anchorNode;
    }

    public AnchorNode GetAnchorNode(){
        return _anchorNode;
    }

    public String GetName(){
        return _name;
    }

    public void SetName(String name){
        _name = name;
    }

    public SoundZoneType GetType(){
        return _type;
    }

    public void SetType(SoundZoneType type){
        _type = type;
    }

    public SoundZoneShape GetShape(){
        return _shape;
    }

    public void SetShape(SoundZoneShape shape){
        _shape = shape;
    }

    public String GetModel(){
        return _model;
    }

    public void SetModel(String model){
        _model = model;
    }

    public void SetRenderable(ModelRenderable render){
        _render = render;
    }

    public ModelRenderable GetRender(){
        return _render;
    }

    public void SetZoneId(int id){
        _zoneID = id;
    }

    public int GetZoneId(){
        return _zoneID;
    }

    public void SetTransformableNode(TransformableNode node){
        _transformableNode = node;
    }

    public TransformableNode GetTransformableNode(){
        return _transformableNode;
    }

    public void UpdateModel(){
        if(_overlapping == false){
            SetType();
        }
        else{
            SetInvalidType();
        }
    }


    private void SetInvalidType(){
        switch (_type){
            case PRIVATE:
                InvalidPrivateShape();
                break;
            case MIXED:
                InvalidMixedShape();
                break;
            case SOCIAL:
                InvalidSocialShape();
                break;
        }
    }

    private void SetType(){
        switch (_type){
            case PRIVATE:
                PrivateShape();
                break;
            case MIXED:
                MixedShape();
                break;
            case SOCIAL:
                SocialShape();
                break;
        }
    }

    private void PrivateShape(){
        if(checker == true){
            switch (_shape){
                case EGG:
                    _model = "models/egg_c1.glb";
                    break;
                case ORB:
                    _model = "models/orb_c1.glb";
                    break;
                case DOME:
                    _model = "models/dome_c1.glb";
                    break;
                case ENTITY:
                    _model = "models/entity_c1.glb";
                    break;
            }
        }
        else if(icons == true){
            switch (_shape){
                case EGG:
                    _model = "models/egg_i1.glb";
                    break;
                case ORB:
                    _model = "models/orb_i1.glb";
                    break;
                case DOME:
                    _model = "models/dome_i1.glb";
                    break;
                case ENTITY:
                    _model = "models/entity_i1.glb";
                    break;
            }
        }
        else if(transparent == true){
            switch (_shape){
                case EGG:
                    _model = "models/egg_t1.glb";
                    break;
                case ORB:
                    _model = "models/orb_t1.glb";
                    break;
                case DOME:
                    _model = "models/dome_t1.glb";
                    break;
                case ENTITY:
                    _model = "models/entity_t1.glb";
                    break;
            }
        }

    }

    private void MixedShape(){
        if(checker == true){
            switch (_shape){
                case EGG:
                    _model = "models/egg_c2.glb";
                    break;
                case ORB:
                    _model = "models/orb_c2.glb";
                    break;
                case DOME:
                    _model = "models/dome_c2.glb";
                    break;
                case ENTITY:
                    _model = "models/entity_c2.glb";
                    break;
            }
        }
        else if(transparent == true){
            switch (_shape){
                case EGG:
                    _model = "models/egg_t2.glb";
                    break;
                case ORB:
                    _model = "models/orb_t2.glb";
                    break;
                case DOME:
                    _model = "models/dome_t2.glb";
                    break;
                case ENTITY:
                    _model = "models/entity_t2.glb";
                    break;
            }
        }
        else if(icons == true){
            switch (_shape){
                case EGG:
                    _model = "models/egg_i2.glb";
                    break;
                case ORB:
                    _model = "models/orb_i2.glb";
                    break;
                case DOME:
                    _model = "models/dome_i2.glb";
                    break;
                case ENTITY:
                    _model = "models/entity_i2.glb";
                    break;
            }
        }

    }

    private void SocialShape(){
        if(checker == true){
            switch (_shape){
                case EGG:
                    _model = "models/egg_c3.glb";
                    break;
                case ORB:
                    _model = "models/orb_c3.glb";
                    break;
                case DOME:
                    _model = "models/dome_c3.glb";
                    break;
                case ENTITY:
                    _model = "models/entity_c3.glb";
                    break;
            }
        }
        else if(transparent == true){
            switch (_shape){
                case EGG:
                    _model = "models/egg_t3.glb";
                    break;
                case ORB:
                    _model = "models/orb_t3.glb";
                    break;
                case DOME:
                    _model = "models/dome_t3.glb";
                    break;
                case ENTITY:
                    _model = "models/entity_t3.glb";
                    break;
            }
        }
        else if(icons == true){
            switch (_shape){
                case EGG:
                    _model = "models/egg_i3.glb";
                    break;
                case ORB:
                    _model = "models/orb_i3.glb";
                    break;
                case DOME:
                    _model = "models/dome_i3.glb";
                    break;
                case ENTITY:
                    _model = "models/entity_i3.glb";
                    break;
            }
        }
    }

    private void InvalidPrivateShape(){
        if(checker == true){
            switch (_shape){
                case EGG:
                    _model = "models/redEGG_C1.glb";
                    break;
                case ORB:
                    _model = "models/redORB_C1.glb";
                    break;
                case DOME:
                    _model = "models/redDOME_C1.glb";
                    break;
                case ENTITY:
                    _model = "models/redENTITY_C1.glb";
                    break;
            }
        }
        else if(transparent == true){
            switch (_shape){
                case EGG:
                    _model = "models/redEGG_T1.glb";
                    break;
                case ORB:
                    _model = "models/redORB_T1.glb";
                    break;
                case DOME:
                    _model = "models/redDOME_T1.glb";
                    break;
                case ENTITY:
                    _model = "models/redENTITY_T1.glb";
                    break;
            }
        }
        else if(icons == true){
            switch (_shape){
                case EGG:
                    _model = "models/redEGG_I1.glb";
                    break;
                case ORB:
                    _model = "models/redORB_I1.glb";
                    break;
                case DOME:
                    _model = "models/redDOME_I1.glb";
                    break;
                case ENTITY:
                    _model = "models/redENTITY_I1.glb";
                    break;
            }
        }

    }

    private void InvalidMixedShape(){
        if(checker == true){
            switch (_shape){
                case EGG:
                    _model = "models/redEGG_C2.glb";
                    break;
                case ORB:
                    _model = "models/redORB_C2.glb";
                    break;
                case DOME:
                    _model = "models/redDOME_C2.glb";
                    break;
                case ENTITY:
                    _model = "models/redENTITY_C2.glb";
                    break;
            }
        }
        else if(transparent == true){
            switch (_shape){
                case EGG:
                    _model = "models/redEGG_T2.glb";
                    break;
                case ORB:
                    _model = "models/redORB_T2.glb";
                    break;
                case DOME:
                    _model = "models/redDOME_T2.glb";
                    break;
                case ENTITY:
                    _model = "models/redENTITY_T2.glb";
                    break;
            }
        }
        else if(icons == true){
            switch (_shape){
                case EGG:
                    _model = "models/redEGG_I2.glb";
                    break;
                case ORB:
                    _model = "models/redORB_I2.glb";
                    break;
                case DOME:
                    _model = "models/redDOME_I2.glb";
                    break;
                case ENTITY:
                    _model = "models/redENTITY_I2.glb";
                    break;
            }
        }

    }

    private void InvalidSocialShape(){
        if(checker == true){
            switch (_shape){
                case EGG:
                    _model = "models/redEGG_C3.glb";
                    break;
                case ORB:
                    _model = "models/redORB_C3.glb";
                    break;
                case DOME:
                    _model = "models/redDOME_C3.glb";
                    break;
                case ENTITY:
                    _model = "models/redENTITY_C3.glb";
                    break;
            }
        }
        else if(transparent == true){
            switch (_shape){
                case EGG:
                    _model = "models/redEGG_T3.glb";
                    break;
                case ORB:
                    _model = "models/redORB_T3.glb";
                    break;
                case DOME:
                    _model = "models/redDOME_T3.glb";
                    break;
                case ENTITY:
                    _model = "models/redENTITY_T3.glb";
                    break;
            }
        }
        else if(icons == true) {
            switch (_shape){
                case EGG:
                    _model = "models/redEGG_I3.glb";
                    break;
                case ORB:
                    _model = "models/redORB_I3.glb";
                    break;
                case DOME:
                    _model = "models/redDOME_I3.glb";
                    break;
                case ENTITY:
                    _model = "models/redENTITY_I3.glb";
                    break;
            }
        }

    }
}
