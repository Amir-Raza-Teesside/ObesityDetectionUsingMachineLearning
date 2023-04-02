package uk.ac.tees.aad.obesity2;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Locale;

import uk.ac.tees.aad.obesity2.Model.user;

public class mainact extends AppCompatActivity {

    FirebaseDatabase database;
    uk.ac.tees.aad.obesity2.Model.user user;
    Button button;
    String Gender;
    FirebaseAuth auth;
    Dialog dialog;
    Button dbutton;
    CheckBox q1,q2,q3,q4;
    public static float activityFactor=1;
    SharedPreferences sharedPreferences;
    Button bmrbutton;

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainact);
        recyclerView = findViewById(R.id.rec);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mymenu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.signout:
               Toast.makeText(mainact.this,"signout",Toast.LENGTH_LONG).show();
            case R.id.bmr_track:
                Toast.makeText(mainact.this,"bmr",Toast.LENGTH_LONG).show();
                break;
        }
        return super.onOptionsItemSelected(item);

    }





   }


