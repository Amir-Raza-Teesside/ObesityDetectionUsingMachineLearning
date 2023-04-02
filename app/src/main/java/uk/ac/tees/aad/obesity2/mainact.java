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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainact);

        bmrbutton = findViewById(R.id.bmract);

        bmrbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mainact.this,BMITracker.class);
                startActivity(intent);
            }
        });

        dialog = new Dialog(this);
        dialog.setContentView(R.layout.custom_dailoge_activityfactor);

        sharedPreferences = getSharedPreferences("Prefs", Context.MODE_PRIVATE);



        dbutton = dialog.findViewById(R.id.mybutton);
        q1 = dialog.findViewById(R.id.firstcheck);
        q2 = dialog.findViewById(R.id.secondcheck);
        q3 = dialog.findViewById(R.id.Thirdcheck);
        q4 = dialog.findViewById(R.id.Fourthcheck);

        dbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 SharedPreferences.Editor editor = sharedPreferences.edit();
                 editor.putFloat("ActivityFactor", activityFactor);
                editor.commit();
                dialog.dismiss();
                Toast.makeText(mainact.this,"dismiss",Toast.LENGTH_LONG).show();
            }
        });

        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        button = findViewById(R.id.bmr);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Toast.makeText(mainact.this,String.valueOf(activityFactor),Toast.LENGTH_SHORT).show();

                dialog.show();
            }
        });




        TextView txt;
        txt = findViewById(R.id.textView);
        user uservalue;
        String Gender;




        database.getReference().child("user").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                user = snapshot.getValue(uk.ac.tees.aad.obesity2.Model.user.class);

                Toast.makeText(mainact.this,user.getName(),Toast.LENGTH_LONG).show();
                txt.setText(user.getEmail() + "" + user.getHeight()+ "  " + user.getWeight());
                double bmr = calculateBmr(user.getGender());

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putLong("bmr", (long) bmr);
                editor.commit();

                Toast.makeText(mainact.this,String.valueOf(bmr),Toast.LENGTH_LONG).show();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    private double calculateBmr(String gender)
    {
      double bmr;
        if(gender.equals("Male"))
        {
            bmr = 88.63 + (13.397 * user.getAge()) + (4.799 * user.getHeight())-(5.677 * user.getAge());
            return  bmr;

        }


        if(gender == "FEMALE")
        {
           bmr = 447.593 + (9.247 * user.getAge()) + (3.098 * user.getHeight()) - (4.330 * user.getAge());
            return bmr;
        }
        return 0.0;
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

   public  void oncheckboxClicked(View view)
   {
       boolean ischecked = ((CheckBox) view).isChecked();
       switch (view.getId())
       {
           case R.id.firstcheck:
               if(ischecked)
               {
               q2.setChecked(false);
               q3.setChecked(false);
               q4.setChecked(false);
               activityFactor = 1.1f;
                   Toast.makeText(mainact.this,String.valueOf(activityFactor),Toast.LENGTH_LONG).show();

               }
               else{

               }
               break;

           case R.id.secondcheck:
               if(ischecked)
               {
                   q1.setChecked(false);
                   q3.setChecked(false);
                   q4.setChecked(false);
                   activityFactor = 1.3f;
                   Toast.makeText(mainact.this,String.valueOf(activityFactor),Toast.LENGTH_LONG).show();
               }
               else{

               }
               break;

           case R.id.Thirdcheck:
               if(ischecked)
               {
                   q1.setChecked(false);
                   q2.setChecked(false);
                   q4.setChecked(false);
                   activityFactor=1.5f;
                   Toast.makeText(mainact.this,String.valueOf(activityFactor),Toast.LENGTH_LONG).show();
               }
               else{

               }
               break;

           case R.id.Fourthcheck:
               if(ischecked)
               {
                   q1.setChecked(false);
                   q2.setChecked(false);
                   q3.setChecked(false);
                   activityFactor = 2.0f;
                   Toast.makeText(mainact.this,String.valueOf(activityFactor),Toast.LENGTH_LONG).show();
               }
               else{

               }
               break;





       }
   }
}