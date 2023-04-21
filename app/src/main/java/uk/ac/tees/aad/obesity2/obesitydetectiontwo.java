package uk.ac.tees.aad.obesity2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class obesitydetectiontwo extends AppCompatActivity {


    Spinner Favc;
    String familyhistory;
    String smoke;
    String transport;
    String caec;
    String calc;
    String Favcvalue;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_obesitydetectiontwo);
        getSupportActionBar().hide();



        Favc = findViewById(R.id.HighCaloric);
        ArrayAdapter<CharSequence> favc = ArrayAdapter.createFromResource(this,
                R.array.FAVC, android.R.layout.simple_spinner_item);
        favc.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Favc.setAdapter(favc);
        Favc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch ((int) id)
                {
                    case 0:
                        Favcvalue = "0";
                        break;
                    case 1:
                        Favcvalue = "1";
                        break;

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Intent recieved = getIntent();
        Bundle currentbundle = recieved.getExtras();
        if(currentbundle != null)
        {
             familyhistory = currentbundle.getString("familyhistory");
             smoke = currentbundle.getString("smoke");
             transport = currentbundle.getString("transport");
             caec = currentbundle.getString("caec");
             calc = currentbundle.getString("calc");

        }
        Toast.makeText(obesitydetectiontwo.this,familyhistory + smoke + transport + caec + calc,Toast.LENGTH_LONG).show();


        SharedPreferences sh = getSharedPreferences("Prefs",MODE_PRIVATE);
    }
}