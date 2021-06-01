package com.example.sound_zones;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.DragEvent;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;
import com.google.ar.core.Anchor;
import com.google.ar.core.Camera;
import com.google.ar.core.Frame;
import com.google.ar.core.HitResult;
import com.google.ar.core.Plane;
import com.google.ar.core.Pose;
import com.google.ar.core.Session;
import com.google.ar.core.exceptions.CameraNotAvailableException;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.ArSceneView;
import com.google.ar.sceneform.HitTestResult;
import com.google.ar.sceneform.Node;
import com.google.ar.sceneform.Scene;
import com.google.ar.sceneform.Sceneform;
import com.google.ar.sceneform.math.Quaternion;
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.rendering.Color;
import com.google.ar.sceneform.rendering.Material;
import com.google.ar.sceneform.rendering.MaterialFactory;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.rendering.Renderable;
import com.google.ar.sceneform.rendering.RenderableInstance;
import com.google.ar.sceneform.rendering.Texture;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.BaseArFragment;
import com.google.ar.sceneform.ux.DragGesture;
import com.google.ar.sceneform.ux.DragGestureRecognizer;
import com.google.ar.sceneform.ux.TransformableNode;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class MainActivity extends AppCompatActivity implements BaseArFragment.OnTapArPlaneListener {
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final double MIN_OPENGL_VERSION = 3.0;




    private enum AppAnchorState {
        NONE,
        HOSTING,
        HOSTED
    }
    private int zoneCount = 0;
    private int overlapFrameRate = 0;
    private int overlapFrameRateMax = 30;
    private AppAnchorState appAnchorState = AppAnchorState.NONE;
    public SoundZoneType selectionType = SoundZoneType.NONE;
    public SoundZoneShape zoneShape = SoundZoneShape.NONE;
    private Anchor cloudAnchor;
    private boolean isPlaced = true;
    private ArFragment arFragment;
    private Renderable renderable;
    private Texture texture;
    private SharedPreferences zone1Settings;
    private SharedPreferences zone2Settings;
    private SharedPreferences zone3Settings;


    private SharedPreferences.Editor zone1Editor;
    private SharedPreferences.Editor zone2Editor;
    private SharedPreferences.Editor zone3Editor;

    private Set<String> soundZoneSet = new HashSet<>();
    private FloatingActionButton fab;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private Button menuButton;
    private BottomSheetBehavior bottomSheetBehavior;
    private BottomSheetBehavior bottomSheetBehaviorMenu;
    private BottomSheetBehavior bottomSheetBehaviorVis;
    private View bottomSheet;
    private View bottomSheetMenu;
    private View bottomSheetVis;
    private ImageButton menuBackButton;
    private LinearLayout selectorMenu;
    private Button placeButton;

    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;
    private SoundZone currentZone;
    private boolean[] zonesCreated = new boolean[] {false, false, false};
    private SoundZone[] soundZones = new SoundZone[3];
    private Button menuButton1;
    private Button menuButton2;
    private Button menuButton3;
    private Button resetButton;
    private Button applyButton;

    private MaterialTextView overlapText;

    private TextInputLayout affordanceDropdown;
    private AutoCompleteTextView affordanceText;
    ArrayList<String> affordances;
    ArrayAdapter<String> affordancesAdapter;

    public boolean checker = true;
    public boolean transparent = false;
    public boolean icons = false;

    boolean overlapping = false;
    private Toast overlapToast;
    private Toast tapToast;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        zone1Settings = getSharedPreferences("SoundZone1", MODE_PRIVATE);
        zone2Settings = getSharedPreferences("SoundZone2", MODE_PRIVATE);
        zone3Settings = getSharedPreferences("SoundZone3", MODE_PRIVATE);

        zone1Editor = zone1Settings.edit();
        zone2Editor = zone2Settings.edit();
        zone3Editor = zone3Settings.edit();

        overlapToast = Toast.makeText(this, "Overlapping zones",Toast.LENGTH_LONG);
        overlapToast.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL,0,0);
        tapToast = Toast.makeText(this, "Tap the screen to place a zone" , Toast.LENGTH_LONG);
        tapToast.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL, 0, 0);

        //sheetBehavior = BottomSheetBehavior.from(findViewById(R.id.bottom_sheet));
        //sheetBehavior.setDraggable(true);
        //sheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        affordanceDropdown = findViewById(R.id.affordance_dropdown);
        affordanceText = findViewById(R.id.affordance_text);
        affordances = new ArrayList<>();
        affordances.add("Checker");
        affordances.add("Transparent");
        affordances.add("Icons");
        affordanceText.setText("Checker");
        affordancesAdapter = new ArrayAdapter<>(this,R.layout.support_simple_spinner_dropdown_item, affordances);
        affordanceText.setAdapter(affordancesAdapter);

        selectorMenu = findViewById(R.id.bottom_sheet);

        bottomSheet = findViewById(R.id.bottom_sheet);
        bottomSheetMenu = findViewById(R.id.bottom_sheet_menu);
        bottomSheetVis = findViewById(R.id.bottom_sheet_zone_vis);

        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
        bottomSheetBehaviorMenu = BottomSheetBehavior.from(bottomSheetMenu);
        bottomSheetBehaviorVis = BottomSheetBehavior.from(bottomSheetVis);

        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        bottomSheetBehaviorMenu.setState(BottomSheetBehavior.STATE_HIDDEN);
        bottomSheetBehaviorVis.setState(BottomSheetBehavior.STATE_HIDDEN);

        bottomSheetBehaviorMenu.setDraggable(false);

        menuBackButton = findViewById(R.id.menuBackButton);
        menuBackButton.setOnClickListener(view -> {
            bottomSheetBehaviorMenu.setState(BottomSheetBehavior.STATE_HIDDEN);
        });

        viewPager = findViewById(R.id.viewPager);
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(new type_fragment());
        viewPagerAdapter.addFragment(new shape_selection());
        viewPagerAdapter.addFragment(new place_zone());

        viewPager.setAdapter(viewPagerAdapter);

        placeButton = findViewById(R.id.placeButton);

        menuButton = findViewById(R.id.menuButton);
        menuButton.setOnClickListener(view -> {
            UpdateMenuNames();
            bottomSheetBehaviorMenu.setState(BottomSheetBehavior.STATE_EXPANDED);
        });

        overlapText = findViewById(R.id.overlapText);
        overlapText.setVisibility(View.INVISIBLE);

        menuButton1 = findViewById(R.id.menuItem1);
        menuButton1.setVisibility(View.INVISIBLE);
        menuButton1.setOnClickListener(view -> {
            bottomSheetBehaviorMenu.setState(BottomSheetBehavior.STATE_HIDDEN);
            currentZone = soundZones[0];
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    OpenDialog(soundZones[0]);
                }
            }, 400);
        });

        menuButton2 = findViewById(R.id.menuItem2);
        menuButton2.setVisibility(View.INVISIBLE);
        menuButton2.setOnClickListener(view -> {
            bottomSheetBehaviorMenu.setState(BottomSheetBehavior.STATE_HIDDEN);
            currentZone = soundZones[1];
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    OpenDialog(soundZones[1]);
                }
            }, 400);
        });

        menuButton3 = findViewById(R.id.menuItem3);
        menuButton3.setVisibility(View.INVISIBLE);
        menuButton3.setOnClickListener(view -> {
            bottomSheetBehaviorMenu.setState(BottomSheetBehavior.STATE_HIDDEN);
            currentZone = soundZones[2];
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    OpenDialog(soundZones[2]);
                }
            }, 400);
        });

        resetButton = findViewById(R.id.reset);
        resetButton.setOnClickListener(view -> {
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if(soundZones[0] != null){
                        soundZones[0].GetAnchorNode().setParent(null);
                        soundZones[0] = null;
                        zonesCreated[0] = false;
                    }
                    if(soundZones[1] != null){
                        soundZones[1].GetAnchorNode().setParent(null);
                        soundZones[1] = null;
                        zonesCreated[1] = false;
                    }
                    if(soundZones[2] != null){
                        soundZones[2].GetAnchorNode().setParent(null);
                        soundZones[2] = null;
                        zonesCreated[2] = false;
                    }
                    UpdateMenuNames();
                    zone1Editor.putBoolean("Zone1", false);
                    zone1Editor.apply();
                    zone2Editor.putBoolean("Zone2", false);
                    zone2Editor.apply();
                    zone3Editor.putBoolean("Zone3", false);
                    zone3Editor.apply();
                }
            }, 1000);
        });

        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(0);
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            }
        });
        arFragment = (CustomARFragment) getSupportFragmentManager().findFragmentById(R.id.arFragment);
        //((CustomARFragment) arFragment).showPlaneDiscoveryController = false;
        arFragment.getArSceneView().getPlaneRenderer().setVisible(true);

        //arFragment.getArSceneView().getPlaneRenderer().setEnabled(false);
        arFragment.setOnTapArPlaneListener(MainActivity.this);


        //arFragment.getPlaneDiscoveryController().setInstructionView(null);


        arFragment.getArSceneView().getScene().addOnUpdateListener(frameTime -> {
            if(currentZone != null){
                if(overlapFrameRate == 60){

                    if(arFragment.getArSceneView().getScene().overlapTestAll(currentZone.GetTransformableNode()).isEmpty()){
                        overlapping = false;
                        overlapToast.cancel();
                        //overlapText.setVisibility(View.INVISIBLE);
                    }
                    else{
                        if(overlapping != true){
                            overlapping = true;
                            overlapToast.show();
                        }
                        //overlapText.setVisibility(View.VISIBLE);
                    }
                    overlapFrameRate = 0;
                }
                overlapFrameRate++;
            }
            if(appAnchorState != AppAnchorState.HOSTING){
                return;
            }
            Anchor.CloudAnchorState cloudAnchorState = cloudAnchor.getCloudAnchorState();
            if(cloudAnchorState.isError()){
                Toast.makeText(this, cloudAnchorState.toString(), Toast.LENGTH_SHORT).show();
            }
            else if(cloudAnchorState == Anchor.CloudAnchorState.SUCCESS){
                appAnchorState = AppAnchorState.HOSTED;
                String anchorID = cloudAnchor.getCloudAnchorId();
                Toast.makeText(this, "Saved sound zone" , Toast.LENGTH_SHORT).show();
                if(currentZone == soundZones[0]){
                    zone1Editor.putString("AnchorID", anchorID);
                    zone1Editor.apply();
                }
                else if(currentZone == soundZones[1]){
                    zone2Editor.putString("AnchorID", anchorID);
                    zone2Editor.apply();

                }
                else if(currentZone == soundZones[2]){
                    zone3Editor.putString("AnchorID", anchorID);
                    zone3Editor.apply();
                }
                //editor.putString("anchorID",anchorID);
                //editor.apply();
            }
        });
        //loadModel();
        //loadTexture();
        if(arFragment.getArSceneView().getSession() != null){
            arFragment.getArSceneView().getSession().pause();
        }

        Button resolve = findViewById(R.id.resolve);
        resolve.setOnClickListener(view -> {
            /*
            String anchorID = prefs.getString("anchorID", "null");
            if(anchorID.equals("null")){
                Toast.makeText(this, "No ID found", Toast.LENGTH_SHORT).show();
                return;
            }
            Anchor resolvedAnchor = arFragment.getArSceneView().getSession().resolveCloudAnchor((anchorID));
            SoundZone myZone = new SoundZone("SomeName", SoundZoneShape.DOME, SoundZoneType.PRIVATE,new TransformableNode(arFragment.getTransformationSystem()));
            AssignZoneID(myZone);
            currentZone = myZone;
            loadModel(myZone);
            CreateModel(resolvedAnchor, myZone);
            Toast.makeText(this, "Found anchor with ID" + anchorID, Toast.LENGTH_SHORT).show();

             */
            LoadZones();
        });



    }



    public void LoadZones(){
        if(zone1Settings.getBoolean("Zone1", false)){
            //Load zone 1
            String anchorID = zone1Settings.getString("AnchorID", "null");
            String zoneName = zone1Settings.getString("Name", "null");
            SoundZoneType type = SoundZoneType.valueOf(zone1Settings.getString("Type", "null"));
            SoundZoneShape shape = SoundZoneShape.valueOf(zone1Settings.getString("Shape", "null"));
            if(anchorID.equals("null")){
                Toast.makeText(this, "No ID found", Toast.LENGTH_SHORT).show();
                return;
            }
            Anchor resolvedAnchor = arFragment.getArSceneView().getSession().resolveCloudAnchor((anchorID));
            SoundZone myZone = new SoundZone(zoneName, shape, type,new TransformableNode(arFragment.getTransformationSystem()));
            AssignZoneID(myZone);
            currentZone = myZone;
            loadModel(myZone);
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    CreateModel(resolvedAnchor, myZone);
                }
            }, 400);
            Toast.makeText(this, "Found anchor with ID" + anchorID, Toast.LENGTH_SHORT).show();
        }
        if(zone2Settings.getBoolean("Zone2", false)){
            //Load zone 2
            String anchorID = zone2Settings.getString("AnchorID", "null");
            String zoneName = zone2Settings.getString("Name", "null");
            SoundZoneType type = SoundZoneType.valueOf(zone2Settings.getString("Type", "null"));
            SoundZoneShape shape = SoundZoneShape.valueOf(zone2Settings.getString("Shape", "null"));
            if(anchorID.equals("null")){
                Toast.makeText(this, "No ID found", Toast.LENGTH_SHORT).show();
                return;
            }
            Anchor resolvedAnchor = arFragment.getArSceneView().getSession().resolveCloudAnchor((anchorID));
            SoundZone myZone = new SoundZone(zoneName, shape, type,new TransformableNode(arFragment.getTransformationSystem()));
            AssignZoneID(myZone);
            currentZone = myZone;
            loadModel(myZone);
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    CreateModel(resolvedAnchor, myZone);
                }
            }, 400);
            Toast.makeText(this, "Found anchor with ID" + anchorID, Toast.LENGTH_SHORT).show();
        }
        if(zone3Settings.getBoolean("Zone3", false)){
            //Load zone 3
            String anchorID = zone3Settings.getString("AnchorID", "null");
            String zoneName = zone3Settings.getString("Name", "null");
            SoundZoneType type = SoundZoneType.valueOf(zone3Settings.getString("Type", "null"));
            SoundZoneShape shape = SoundZoneShape.valueOf(zone3Settings.getString("Shape", "null"));
            if(anchorID.equals("null")){
                Toast.makeText(this, "No ID found", Toast.LENGTH_SHORT).show();
                return;
            }
            Anchor resolvedAnchor = arFragment.getArSceneView().getSession().resolveCloudAnchor((anchorID));
            SoundZone myZone = new SoundZone(zoneName, shape, type,new TransformableNode(arFragment.getTransformationSystem()));
            AssignZoneID(myZone);
            currentZone = myZone;
            loadModel(myZone);
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    CreateModel(resolvedAnchor, myZone);
                }
            }, 400);
            Toast.makeText(this, "Found anchor with ID" + anchorID, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event){
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (bottomSheetBehavior.getState()==BottomSheetBehavior.STATE_EXPANDED) {

                Rect outRect = new Rect();
                bottomSheet.getGlobalVisibleRect(outRect);

                if(!outRect.contains((int)event.getRawX(), (int)event.getRawY()))
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                    //NavigateTypeSelection();
            }
        }

        return super.dispatchTouchEvent(event);
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else{
            super.onBackPressed();
        }
    }

    public void loadModel(SoundZone zone) {
        WeakReference<MainActivity> weakActivity = new WeakReference<>(this);
        ModelRenderable.builder()
                .setSource(this, Uri.parse(zone.GetModel()))
                .setIsFilamentGltf(true)
                .build()
                .thenAccept(renderable -> {
                    zone.SetRenderable(renderable);
                    /*
                    MainActivity activity = weakActivity.get();
                    if (activity != null) {
                        activity.renderable = renderable;
                    }
                     */
                })
                .exceptionally(
                        throwable -> {
                            Toast.makeText(this, "Unable to load zone renderable", Toast.LENGTH_LONG).show();
                            return null;
                        });
    }

    public void loadTexture() {
        WeakReference<MainActivity> weakActivity = new WeakReference<>(this);
        Texture.builder()
                .setSampler(Texture.Sampler.builder()
                        .setMinFilter(Texture.Sampler.MinFilter.LINEAR_MIPMAP_LINEAR)
                        .setMagFilter(Texture.Sampler.MagFilter.LINEAR)
                        .setWrapMode(Texture.Sampler.WrapMode.REPEAT)
                        .build())
                .setSource(this, Uri.parse("textures/parquet.jpg"))
                .setUsage(Texture.Usage.COLOR)
                .build()
                .thenAccept(
                        texture -> {
                            MainActivity activity = weakActivity.get();
                            if (activity != null) {
                                activity.texture = texture;
                            }
                        })
                .exceptionally(
                        throwable -> {
                            Toast.makeText(this, "Unable to load texture", Toast.LENGTH_LONG).show();
                            return null;
                        });
    }

    @Override
    public void onTapPlane(HitResult hitResult, Plane plane, MotionEvent motionEvent) {
        /*
        Frame frame = arFragment.getArSceneView().getArFrame();
        Camera camera = frame.getCamera();
        Pose camPose = camera.getPose();
        Session.FeatureMapQuality featureQuality = arFragment.getArSceneView().getSession().estimateFeatureMapQualityForHosting(camPose);
        Log.i("Feature", "Feature quality is: " + featureQuality.toString());
         */
        if(!isPlaced){
            //Anchor anchor = hitResult.getTrackable().createAnchor(hitResult.getHitPose().compose(Pose.makeTranslation(0,1.0f,0))); //Default is hitResult.CreateAnchor();
            Anchor anchor = hitResult.createAnchor(); //Default is hitResult.CreateAnchor();

            cloudAnchor = arFragment.getArSceneView().getSession().hostCloudAnchor(anchor);
            appAnchorState = AppAnchorState.HOSTING;
            Toast.makeText(this, "Saving sound zone..." , Toast.LENGTH_SHORT).show();
            isPlaced = true;
            CreateModel(cloudAnchor, currentZone);
        }
    }



    private void CreateModel(Anchor anchor, SoundZone zone){
        AnchorNode anchorNode = new AnchorNode(anchor);
        anchorNode.setParent(arFragment.getArSceneView().getScene());

        zone.SetAnchorNode(anchorNode);
        /*
        zone.GetTransformableNode().setOnTapListener((HitTestResult hitTestResult, MotionEvent motionEvent) -> {
            TransformableNode node = ((TransformableNode) hitTestResult.getNode());
            if(node.isSelected() == false){
                node.select();
            }
        });
         */
        //arFragment.getArSceneView().getScene().overlapTest()
        // Create the transformable model and add it to the anchor.
        zone.GetTransformableNode().setParent(anchorNode);
        zone.GetTransformableNode().setRenderable(zone.GetRender());
        zone.GetRender().setShadowCaster(false);
        zone.GetRender().setShadowReceiver(false);
        zone.GetTransformableNode().select();


        /*
        zone.GetTransformableNode().addTransformChangedListener(new Node.TransformChangedListener() {
            @Override
            public void onTransformChanged(Node node, Node node1) {

            }
        });
         */


    }




    public void NavigateTypeSelection(){
        viewPager.setCurrentItem(0);
    }

    public void NavigateShapeSelection(){
        viewPager.setCurrentItem(1);
        shape_selection shape_fragment = (shape_selection)viewPagerAdapter.getItem(1);
        shape_fragment.AssignImages(selectionType);
    }

    public void NavigatePlaceZone(){
        viewPager.setCurrentItem(2);
        place_zone place_zoneObject = (place_zone)viewPagerAdapter.getItem(2);
        place_zoneObject.SetImage(zoneShape,selectionType);
    }

    public void PlaceZone(String zoneName){
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        isPlaced = false;
        NavigateTypeSelection();
        SoundZone myZone = new SoundZone(zoneName, zoneShape, selectionType,new TransformableNode(arFragment.getTransformationSystem()));
        AssignZoneID(myZone);
        currentZone = myZone;
        loadModel(myZone);
        tapToast.show();
    }

    private int AssignZoneID(SoundZone zone){
        int index = 0;
        if(zonesCreated[0] == false){
            zonesCreated[0] = true;
            soundZones[0] = zone;
            index = 0;
            zone.SetZoneId(index);
            menuButton1.setText(zone.GetName());
            menuButton1.setVisibility(View.VISIBLE);
            zone1Editor.putBoolean("Zone1", true);
            zone1Editor.putString("Name", zone.GetName());
            zone1Editor.putString("Type", zone.GetType().toString());
            zone1Editor.putString("Shape", zone.GetShape().toString());
            zone1Editor.apply();
        }
        else if(zonesCreated[1] == false){
            soundZones[1] = zone;
            zonesCreated[1] = true;
            index = 1;
            zone.SetZoneId(index);
            menuButton2.setText(zone.GetName());
            menuButton2.setVisibility(View.VISIBLE);
            zone2Editor.putBoolean("Zone2", true);
            zone2Editor.putString("Name", zone.GetName());
            zone2Editor.putString("Type", zone.GetType().toString());
            zone2Editor.putString("Shape", zone.GetShape().toString());
            zone2Editor.apply();
        }
        else if(zonesCreated[2] == false){
            soundZones[2] = zone;
            zonesCreated[2] = true;
            index = 2;
            zone.SetZoneId(index);
            menuButton3.setText(zone.GetName());
            menuButton3.setVisibility(View.VISIBLE);
            zone3Editor.putBoolean("Zone3", true);
            zone3Editor.putString("Name", zone.GetName());
            zone3Editor.putString("Type", zone.GetType().toString());
            zone3Editor.putString("Shape", zone.GetShape().toString());
            zone3Editor.apply();
        }
        return index;
    }


    public void OpenDialog(SoundZone zone) {
        Dialog menuDialog = new Dialog(zone);
        menuDialog.show(getSupportFragmentManager(), "Menu dialog");
    }

    public void UpdateMenuNames(){
        if(zonesCreated[0] == true){
            menuButton1.setVisibility(View.VISIBLE);
            menuButton1.setText(soundZones[0].GetName());
        }
        else{
            menuButton1.setVisibility(View.INVISIBLE);
        }
        if(zonesCreated[1] == true){
            menuButton2.setVisibility(View.VISIBLE);
            menuButton2.setText(soundZones[1].GetName());
        }
        else{
            menuButton2.setVisibility(View.INVISIBLE);
        }
        if(zonesCreated[2] == true){
            menuButton3.setVisibility(View.VISIBLE);
            menuButton3.setText(soundZones[2].GetName());
        }
        else{
            menuButton3.setVisibility(View.INVISIBLE);
        }
    }

    public void UpdateZoneModel() {
        AnchorNode anchor = currentZone.GetAnchorNode();
        TransformableNode old_node = currentZone.GetTransformableNode();
        currentZone.UpdateModel();
        if(currentZone == soundZones[0]){
            zone1Editor.putBoolean("Zone1", true);
            zone1Editor.putString("Name", currentZone.GetName());
            zone1Editor.putString("Type", currentZone.GetType().toString());
            zone1Editor.putString("Shape", currentZone.GetShape().toString());
            zone1Editor.apply();
        }
        else if(currentZone == soundZones[1]){
            zone2Editor.putBoolean("Zone2", true);
            zone2Editor.putString("Name", currentZone.GetName());
            zone2Editor.putString("Type", currentZone.GetType().toString());
            zone2Editor.putString("Shape", currentZone.GetShape().toString());
            zone2Editor.apply();
        }
        else if(currentZone == soundZones[2]){
            zone3Editor.putBoolean("Zone3", true);
            zone3Editor.putString("Name", currentZone.GetName());
            zone3Editor.putString("Type", currentZone.GetType().toString());
            zone3Editor.putString("Shape", currentZone.GetShape().toString());
            zone3Editor.apply();
        }
        loadModel(currentZone);
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                old_node.setRenderable(currentZone.GetRender());
            }
        }, 1000);
    }

    public void UpdateInvalidZoneModel(SoundZone zone){
        AnchorNode anchor = zone.GetAnchorNode();
        TransformableNode old_node = zone.GetTransformableNode();
        zone.UpdateModel();
        loadModel(zone);
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                old_node.setRenderable(zone.GetRender());
            }
        }, 1000);
    }


    public void DeleteZone(){
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                int index = currentZone.GetZoneId();
                currentZone.GetAnchorNode().setParent(null);
                soundZones[index] = null;
                zonesCreated[index] = false;
                UpdateMenuNames();
                if(index == 0){
                    zone1Editor.putBoolean("Zone1", false);
                    zone1Editor.apply();
                }
                if(index == 1){
                    zone2Editor.putBoolean("Zone2", false);
                    zone2Editor.apply();
                }
                if(index == 2){
                    zone3Editor.putBoolean("Zone3", false);
                    zone3Editor.apply();
                }
            }
        }, 1000);
    }

    public int GetZoneCount(){
        int count = 0;
        if(zonesCreated[0] == false){
            count = 1;
        }
        else if(zonesCreated[1] == false){
            count = 2;
        }
        else if(zonesCreated[2] == false){
            count = 3;
        }
        return count;
    }
}