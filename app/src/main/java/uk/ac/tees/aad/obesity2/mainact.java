package uk.ac.tees.aad.obesity2;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
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

import uk.ac.tees.aad.obesity2.Model.TweetModel;
import uk.ac.tees.aad.obesity2.Model.user;
import uk.ac.tees.aad.obesity2.Adapter.twiterAdapter;
import uk.ac.tees.aad.obesity2.service.MyService;

public class mainact extends AppCompatActivity {

    FirebaseDatabase database;
    TimePicker timePicker;
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

    Button savebutton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainact);
        recyclerView = findViewById(R.id.recs);
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
        savebutton = waterreminder.findViewById(R.id.save);
        timePicker = waterreminder.findViewById(R.id.timepicker);

        ActivityCompat.requestPermissions(this, new String[]
                {Manifest.permission.FOREGROUND_SERVICE}, PackageManager.PERMISSION_GRANTED);

        Intent intent = new Intent(mainact.this, MyService.class);

        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker timePicker, int i, int i1) {
                savebutton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ServiceExecuter(intent);
                        waterreminder.dismiss();
                        Toast.makeText(mainact.this,"Reminder Saved",Toast.LENGTH_LONG).show();
                    }
                });

            }

        });





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
                    case R.id.share:
                        Toast.makeText(mainact.this, "clicked", Toast.LENGTH_SHORT).show();
                        Intent intents = new Intent(mainact.this, share_thought.class);
                        startActivity(intents);
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

            case R.id.activitydialog:

                Intent myintent = new Intent(mainact.this,obesitydetection.class);
                startActivity(myintent);
                break;
            case R.id.foodrecommender:

                Intent foodIntent = new Intent(mainact.this,foodrecomender.class);
                startActivity(foodIntent);
                break;
            case R.id.privacypolicy:
                Intent myWebLink = new Intent(android.content.Intent.ACTION_VIEW);
                myWebLink.setData(Uri.parse("https://www.freeprivacypolicy.com/live/bdc579fa-0af9-4fb3-942e-94c1dba69036"));
                startActivity(myWebLink);
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


        return  user.getWeight() * 2.20462;
    }

    public double CentimeterToInch(user user)
    {

        return  user.getHeight()*0.393701;
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

    private void ServiceExecuter(Intent intent)
    {
        // startService(intent);
        int hour = timePicker.getCurrentHour();
        int minute = timePicker.getCurrentMinute();

        intent.putExtra("HOURS", hour);
        intent.putExtra("MINUTES", minute);
        startService(intent);


    }
    private  void StopService(Intent intent)
    {

        stopService(intent);

    }


   }


