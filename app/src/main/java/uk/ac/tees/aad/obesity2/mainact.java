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

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Locale;

import uk.ac.tees.aad.obesity2.Model.TweetModel;
import uk.ac.tees.aad.obesity2.Model.user;
import uk.ac.tees.aad.obesity2.Adapter.twiterAdapter;

public class mainact extends AppCompatActivity {

    FirebaseDatabase database;
    uk.ac.tees.aad.obesity2.Model.user user;
    Button submitButton;
    String Gender;
    FirebaseAuth auth;
    Dialog dialog;
    Button dbutton;
    CheckBox q1,q2,q3,q4;
    public static float activityFactor=1;
    SharedPreferences sharedPreferences;
    Button bmrbutton;
    FloatingActionButton floatingActionButton;

    RecyclerView recyclerView;
    ArrayList<TweetModel> models;
    twiterAdapter adapter;
    FirebaseUser currentuser;
    BottomNavigationView bottomNavigationView;
    Dialog waterreminder;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainact);
        recyclerView = findViewById(R.id.rec);
        floatingActionButton = findViewById(R.id.floatingActionButton);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                waterreminder.show();
            }
        });

        currentuser = FirebaseAuth.getInstance().getCurrentUser();
        String UserId = currentuser.getUid();

        waterreminder = new Dialog(this);
        waterreminder.setContentView(R.layout.custom_dailoge_waterreminder);

        dialog = new Dialog(this);
        dialog.setContentView(R.layout.custom_dailoge_activityfactor);

        q1 = dialog.findViewById(R.id.firstcheck);
        q2 = dialog.findViewById(R.id.secondcheck);
        q3 = dialog.findViewById(R.id.Thirdcheck);
        q4 = dialog.findViewById(R.id.Fourthcheck);
        submitButton = dialog.findViewById(R.id.mybutton);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        bottomNavigationView.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.bmis:
                        Toast.makeText(mainact.this, "clicked", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(mainact.this, BMITracker.class);
                        startActivity(intent);
                        break;
                }
            }
        });

        String userid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        sharedPreferences = getSharedPreferences("Prefs",MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myEdit.putFloat("ActivityFactor", activityFactor);
                myEdit.commit();
                Intent intent = new Intent(mainact.this,BMITracker.class);
                startActivity(intent);
                dialog.dismiss();

            }
        });



        models = new ArrayList<>();
        database = FirebaseDatabase.getInstance();
        database.getReference().child("tweet").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                models.clear();
                for(DataSnapshot snapshot1: snapshot.getChildren())
                {
                      TweetModel model = snapshot1.getValue(TweetModel.class);
                      models.add(model);


                }
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        database.getReference().child("user").child(userid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                user user1 = snapshot.getValue(user.class);

                myEdit.putString("gender",user1.getGender());
                myEdit.putString("name",user1.getName());
                myEdit.putString("email",user1.getEmail());
                myEdit.putFloat("weight",user1.getWeight());
                myEdit.putFloat("height",user1.getHeight());
                myEdit.putInt("age",user1.getAge());
                double bmr = BMRCalculate(user1);
                double pound = KilogramToPound(user1);
                double Inch = CentimeterToInch(user1);
                myEdit.putLong("bmr", (long) bmr);
                myEdit.putLong("pound", (long) pound);
                myEdit.putLong("inches", (long) Inch);

                myEdit.commit();





            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




        adapter = new twiterAdapter(models,mainact.this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);



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
                Intent intent = new Intent(mainact.this,share_thought.class);
                startActivity(intent);
               Toast.makeText(mainact.this,"signout",Toast.LENGTH_LONG).show();
            case R.id.activitydialog:
                dialog.show();
                Toast.makeText(mainact.this,"bmr",Toast.LENGTH_LONG).show();
                break;
        }
        return super.onOptionsItemSelected(item);

    }

    private double BMRCalculate(user user)
    {
        double bmr=0;
        if(user.getGender().equals("Male"))
        {

            bmr = 88.362 + (13.397 * user.getWeight()) + (4.799 * user.getHeight()) - (5.677*user.getAge());
            return bmr;
        }
        if(user.getGender().equals("Female"))
        {
            bmr = 447.593 + (9.247 * user.getWeight()) + (3.098 * user.getHeight()) - (4.330 *user.getAge());

        }

        return  bmr;


    }

    public double KilogramToPound(user user)
    {


        return  user.getWeight() * 0.4535;
    }

    public double CentimeterToInch(user user)
    {

        return  user.getHeight()/2.54;
    }

  public void oncheckboxClicked(View view)
    {
        boolean checked = ((CheckBox) view).isChecked();
        switch (view.getId())
        {
            case R.id.firstcheck:
               if(checked)
               {
                   q2.setChecked(false);
                   q3.setChecked(false);
                   q4.setChecked(false);
                   activityFactor=1.1f;

               }
               else {

               }
               break;

            case R.id.secondcheck:
                if(checked)
                {
                    q1.setChecked(false);
                    q3.setChecked(false);
                    q4.setChecked(false);
                    activityFactor=1.4f;

                }
                else {

                }
                break;
            case R.id.Thirdcheck:
                if(checked)
                {
                    q1.setChecked(false);
                    q2.setChecked(false);
                    q4.setChecked(false);
                    activityFactor=1.7f;

                }
                else {

                }
                break;

            case R.id.Fourthcheck:check:
                if(checked)
                {
                    q1.setChecked(false);
                    q2.setChecked(false);
                    q3.setChecked(false);
                    activityFactor=2.0f;

                }
                else {

                }
                break;



        }



    }



   }


