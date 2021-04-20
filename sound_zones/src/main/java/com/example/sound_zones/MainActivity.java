package com.example.sound_zones;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.ar.core.Anchor;
import com.google.ar.core.HitResult;
import com.google.ar.core.Plane;
import com.google.ar.core.exceptions.CameraNotAvailableException;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.Sceneform;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.rendering.Renderable;
import com.google.ar.sceneform.rendering.RenderableInstance;
import com.google.ar.sceneform.rendering.Texture;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.BaseArFragment;
import com.google.ar.sceneform.ux.TransformableNode;

import java.lang.ref.WeakReference;

public class MainActivity extends AppCompatActivity implements BaseArFragment.OnTapArPlaneListener {
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final double MIN_OPENGL_VERSION = 3.0;



    private enum AppAnchorState {
        NONE,
        HOSTING,
        HOSTED
    }

    private AppAnchorState appAnchorState = AppAnchorState.NONE;
    private Anchor cloudAnchor;
    private boolean isPlaced = false;
    private ArFragment arFragment;
    private Renderable renderable;
    private Texture texture;
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;
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
    private Button privateButton;
    private ImageButton menuBackButton;
    private LinearLayout selectorMenu;
    private Button placeButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        prefs = getSharedPreferences("AnchorID", MODE_PRIVATE);
        editor = prefs.edit();
        //sheetBehavior = BottomSheetBehavior.from(findViewById(R.id.bottom_sheet));
        //sheetBehavior.setDraggable(true);
        //sheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
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


        privateButton = findViewById(R.id.SelectPrivateButton);
        privateButton.setOnClickListener(view -> {
            bottomSheetBehaviorVis.setState(BottomSheetBehavior.STATE_EXPANDED);
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        });

        menuButton = findViewById(R.id.menuButton);
        menuButton.setOnClickListener(view -> {
            bottomSheetBehaviorMenu.setState(BottomSheetBehavior.STATE_EXPANDED);
        });




        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            }
        });
        arFragment = (CustomARFragment) getSupportFragmentManager().findFragmentById(R.id.arFragment);
        ((CustomARFragment) arFragment).showPlaneDiscoveryController = false;
        arFragment.getArSceneView().getPlaneRenderer().setVisible(false);
        arFragment.getArSceneView().getPlaneRenderer().setEnabled(false);


        //arFragment.getPlaneDiscoveryController().setInstructionView(null);


        arFragment.getArSceneView().getScene().addOnUpdateListener(frameTime -> {
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
                Toast.makeText(this, "Anchor hosted with ID: " + anchorID.toString(), Toast.LENGTH_SHORT).show();
                editor.putString("anchorID", anchorID);
                editor.apply();
            }
        });
        loadModel();
        loadTexture();
        if(arFragment.getArSceneView().getSession() != null){
            arFragment.getArSceneView().getSession().pause();
        }

        placeButton = findViewById(R.id.placeButton);
        placeButton.setOnClickListener(view -> {
            bottomSheetBehaviorVis.setState(BottomSheetBehavior.STATE_HIDDEN);
            ((CustomARFragment) arFragment).showPlaneDiscoveryController = true;
            arFragment.getArSceneView().getPlaneRenderer().setEnabled(true);
            arFragment.getArSceneView().getPlaneRenderer().setVisible(true);
            arFragment.setOnTapArPlaneListener(MainActivity.this);
        });

        Button resolve = findViewById(R.id.resolve);
        resolve.setOnClickListener(view -> {
            String anchorID = prefs.getString("anchorID", "null");
            if(anchorID.equals("null")){
                Toast.makeText(this, "No ID found", Toast.LENGTH_SHORT).show();
                return;
            }
            Anchor resolvedAnchor = arFragment.getArSceneView().getSession().resolveCloudAnchor((anchorID));
            CreateModel(resolvedAnchor);
            Toast.makeText(this, "Found anchor with ID" + anchorID, Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event){
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (bottomSheetBehavior.getState()==BottomSheetBehavior.STATE_EXPANDED) {

                Rect outRect = new Rect();
                bottomSheet.getGlobalVisibleRect(outRect);

                if(!outRect.contains((int)event.getRawX(), (int)event.getRawY()))
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
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

    public void loadModel() {
        WeakReference<MainActivity> weakActivity = new WeakReference<>(this);
        ModelRenderable.builder()
                .setSource(this, Uri.parse("models/dome_c1.obj"))
                .setIsFilamentGltf(true)
                .build()
                .thenAccept(renderable -> {
                    MainActivity activity = weakActivity.get();
                    if (activity != null) {
                        activity.renderable = renderable;
                    }
                })
                .exceptionally(
                        throwable -> {
                            Toast.makeText(this, "Unable to load renderable", Toast.LENGTH_LONG).show();
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
        if (renderable == null || texture == null) {
            Toast.makeText(this, "Loading...", Toast.LENGTH_SHORT).show();
            return;
        }
        if(!isPlaced){
            cloudAnchor = arFragment.getArSceneView().getSession().hostCloudAnchor(hitResult.createAnchor());
            appAnchorState = AppAnchorState.HOSTING;
            Toast.makeText(this, "Trying to host cloud anchor: " , Toast.LENGTH_SHORT).show();
            isPlaced = true;
            CreateModel(cloudAnchor);
        }
        // Create the Anchor.
        //Anchor anchor = hitResult.createAnchor();

    }

    private void CreateModel(Anchor anchor){
        AnchorNode anchorNode = new AnchorNode(anchor);
        anchorNode.setParent(arFragment.getArSceneView().getScene());

        // Create the transformable model and add it to the anchor.
        TransformableNode model = new TransformableNode(arFragment.getTransformationSystem());
        model.setParent(anchorNode);
        model.setRenderable(renderable);
        model.select();
        RenderableInstance renderableInstance = model.getRenderableInstance();
        renderableInstance.getMaterial().setInt("baseColorIndex", 0);
        renderableInstance.getMaterial().setTexture("baseColorMap", texture);
    }
}