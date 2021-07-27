package com.quintus.labs.datingapp.Login;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.wifi.hotspot2.pps.Credential;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.quintus.labs.datingapp.Main.MainActivity;
import com.quintus.labs.datingapp.R;
import com.quintus.labs.datingapp.Utils.User;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.quintus.labs.datingapp.Main.MainActivity;
import com.quintus.labs.datingapp.R;

import java.util.HashMap;
import java.util.Map;

/**
 * DatingApp
 * https://github.com/quintuslabs/DatingApp
 * Created on 25-sept-2018.
 * Created by : Santosh Kumar Dash:- http://santoshdash.epizy.com
 */

public class RegisterHobby extends AppCompatActivity {
    private static final String TAG = "RegisterHobby";

    //User Info
    User userInfo;
    String password;
FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    private Context mContext;
    private Button hobbiesContinueButton;
    private Button sportsSelectionButton;
    private Button travelSelectionButton;
    private Button musicSelectionButton;
    private Button fishingSelectionButton;


    private String append = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_hobby);
        mContext = RegisterHobby.this;
        fAuth =FirebaseAuth.getInstance(); fStore= FirebaseFirestore.getInstance();
        Log.d(TAG, "onCreate: started");

        Intent intent = getIntent();
        userInfo = (User) intent.getSerializableExtra("classUser");
        password = intent.getStringExtra("password");

        initWidgets();

        init();
    }

    private void initWidgets() {
        sportsSelectionButton = findViewById(R.id.sportsSelectionButton);
        travelSelectionButton = findViewById(R.id.travelSelectionButton);
        musicSelectionButton = findViewById(R.id.musicSelectionButton);
        fishingSelectionButton = findViewById(R.id.fishingSelectionButton);
        hobbiesContinueButton = findViewById(R.id.hobbiesContinueButton);

        // Initially all the buttons needs to be grayed out so this code is added, on selection we will enable it later
        sportsSelectionButton.setAlpha(.5f);
        sportsSelectionButton.setBackgroundColor(Color.GRAY);

        travelSelectionButton.setAlpha(.5f);
        travelSelectionButton.setBackgroundColor(Color.GRAY);

        musicSelectionButton.setAlpha(.5f);
        musicSelectionButton.setBackgroundColor(Color.GRAY);

        fishingSelectionButton.setAlpha(.5f);
        fishingSelectionButton.setBackgroundColor(Color.GRAY);


        sportsSelectionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sportsButtonClicked();
            }
        });

        travelSelectionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                travelButtonClicked();
            }
        });

        musicSelectionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                musicButtonClicked();
            }
        });

        fishingSelectionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fishingButtonClicked();
            }
        });


    }

    public void sportsButtonClicked() {
        // this is to toggle between selection and non selection of button
        if (sportsSelectionButton.getAlpha() == 1.0f) {
            sportsSelectionButton.setAlpha(.5f);
            sportsSelectionButton.setBackgroundColor(Color.GRAY);
            userInfo.setSports(false);
        } else {
            sportsSelectionButton.setBackgroundColor(Color.parseColor("#FF4081"));
            sportsSelectionButton.setAlpha(1.0f);
            userInfo.setSports(true);
        }
    }

    public void travelButtonClicked() {
        // this is to toggle between selection and non selection of button
        if (travelSelectionButton.getAlpha() == 1.0f) {
            travelSelectionButton.setAlpha(.5f);
            travelSelectionButton.setBackgroundColor(Color.GRAY);
            userInfo.setTravel(false);
        } else {
            travelSelectionButton.setBackgroundColor(Color.parseColor("#FF4081"));
            travelSelectionButton.setAlpha(1.0f);
            userInfo.setTravel(true);

        }

    }

    public void musicButtonClicked() {
        // this is to toggle between selection and non selection of button
        if (musicSelectionButton.getAlpha() == 1.0f) {
            musicSelectionButton.setAlpha(.5f);
            musicSelectionButton.setBackgroundColor(Color.GRAY);
            userInfo.setMusic(false);
        } else {
            musicSelectionButton.setBackgroundColor(Color.parseColor("#FF4081"));
            musicSelectionButton.setAlpha(1.0f);
            userInfo.setMusic(true);

        }

    }

    public void fishingButtonClicked() {
        // this is to toggle between selection and non selection of button
        if (fishingSelectionButton.getAlpha() == 1.0f) {
            fishingSelectionButton.setAlpha(.5f);
            fishingSelectionButton.setBackgroundColor(Color.GRAY);
            userInfo.setFishing(false);
        } else {
            fishingSelectionButton.setBackgroundColor(Color.parseColor("#FF4081"));
            fishingSelectionButton.setAlpha(1.0f);
            userInfo.setFishing(true);

        }

    }

    public void init() {
        hobbiesContinueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //----------------------------------------Firebase----------------------------------------
                //dangky trong firebase

                String username= userInfo.getUsername(),
                email = userInfo.getEmail(),
                        phone= userInfo.getPhone_number(),
                        des= userInfo.getDescription(),
                        date= userInfo.getDateOfBirth(),
                        presex= userInfo.getPreferSex(),
                        sex= userInfo.getSex();

                boolean fishing= userInfo.isFishing(),
                        music= userInfo.isMusic(),
                        sport=userInfo.isSports(),
                        travel =userInfo.isTravel();
                double lati= userInfo.getLatitude(),
                        longti= userInfo.getLongtitude();
                fAuth.createUserWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Toast.makeText(mContext, "Register successfully", Toast.LENGTH_SHORT).show();
                        //
                       // String userId = fAuth.getCurrentUser().getUid();
                        DocumentReference docref = fStore.collection("user").document();
                        Map<String, Object> userdoc=new HashMap<>();
                        userdoc.put("name",username);
                        userdoc.put("phone",phone);
                        userdoc.put("email",email);
                        userdoc.put("date",date);
                        userdoc.put("presex",presex);
                        userdoc.put("sex",sex);
                        userdoc.put("lat",lati);
                        userdoc.put("long",longti);
                        userdoc.put("fishing",fishing);
                        userdoc.put("music",music);
                        userdoc.put("sport",sport);
                        userdoc.put("travel",travel);
                        userdoc.put("des",des);
                        userdoc.put("profileImageUrl", "default");



                        fStore.collection("user")
                                .add(userdoc)
                                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                    @Override
                                    public void onSuccess(DocumentReference documentReference) {
                                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.w(TAG, "Error adding document", e);
                                    }
                                });
                        //
                        Intent intent = new Intent(RegisterHobby.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(mContext, "Register false", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(RegisterHobby.this, Login.class);
                        startActivity(intent);
                        finish();
                    }
                });

                //



            }
        });
    }





}
