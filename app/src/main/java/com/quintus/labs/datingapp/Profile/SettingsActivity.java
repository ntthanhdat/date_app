package com.quintus.labs.datingapp.Profile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.quintus.labs.datingapp.Introduction.IntroductionMain;
import com.quintus.labs.datingapp.Login.Login;
import com.quintus.labs.datingapp.Main.MainActivity;
import com.quintus.labs.datingapp.R;
import com.yahoo.mobile.client.android.util.rangeseekbar.RangeSeekBar;

/**
 * Grocery App
 * https://github.com/quintuslabs/GroceryStore
 * Created on 18-Feb-2019.
 * Created by : Santosh Kumar Dash:- http://santoshdash.epizy.com
 */

public class SettingsActivity extends AppCompatActivity {
    private static final String TAG = "SettingsActivity";
    SeekBar distance;
    SwitchCompat man, woman;
    RangeSeekBar rangeSeekBar;
    TextView gender, distance_text, age_rnge, logout, deleteacc;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        mContext = SettingsActivity.this;

        TextView toolbar = findViewById(R.id.toolbartag);
        toolbar.setText("Profile");
        ImageButton back = findViewById(R.id.back);
        distance = findViewById(R.id.distance);
        man = findViewById(R.id.switch_man);
        woman = findViewById(R.id.switch_woman);
        distance_text = findViewById(R.id.distance_text);
        age_rnge = findViewById(R.id.age_range);
        rangeSeekBar = findViewById(R.id.rangeSeekbar);
        logout= findViewById(R.id.logout);
        deleteacc= findViewById(R.id.deleteacc);
        distance.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                distance_text.setText(progress + " Km");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        man.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    man.setChecked(true);
                    woman.setChecked(false);
                }
            }
        });
        woman.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    woman.setChecked(true);
                    man.setChecked(false);
                }
            }
        });
        rangeSeekBar.setOnRangeSeekBarChangeListener(new RangeSeekBar.OnRangeSeekBarChangeListener() {
            @Override
            public void onRangeSeekBarValuesChanged(RangeSeekBar bar, Object minValue, Object maxValue) {
                age_rnge.setText(minValue + "-" + maxValue);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
//log out
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(mContext, "you've been logout", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(SettingsActivity.this, Login.class);
                startActivity(intent);
                finish();
            }
        });


    deleteacc.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            FirebaseUser user =FirebaseAuth.getInstance().getCurrentUser();
            user.delete();
            FirebaseAuth.getInstance().signOut();
            Toast.makeText(mContext, "account deleted", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(SettingsActivity.this, Login.class);
            startActivity(intent);
            finish();
        }
    });
}


    public void Logout(View view) {
        startActivity(new Intent(getApplicationContext(), IntroductionMain.class));
        finish();

    }


}
