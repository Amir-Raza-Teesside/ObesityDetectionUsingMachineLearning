package uk.ac.tees.aad.obesity2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.ekn.gruzer.gaugelibrary.HalfGauge;
import com.ekn.gruzer.gaugelibrary.Range;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class TEDD extends AppCompatActivity {

    Dialog dialog;
    CheckBox q1,q2,q3,q4;
    Button submitButton;
    public static float activityFactor;
    SharedPreferences sharedPreferences;
    BottomNavigationView bottomNavigationView;
    HalfGauge guage;
    TextView tedtext;
    TextView oneday, twoday,threeday,threeandhalf,fourday,fourandhalf;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tedd);
        guage = findViewById(R.id.guagesec);
        guage.setMaxValue(4000);
        guage.setMinValue(0);
        tedtext = findViewById(R.id.teddtext);
        oneday = findViewById(R.id.oneday);
        twoday = findViewById(R.id.twoday);
        threeday = findViewById(R.id.threeday);
        threeandhalf = findViewById(R.id.threeandhalf);
        fourday = findViewById(R.id.fourday);
        fourandhalf = findViewById(R.id.fourandhalf);
        SharedPreferences sh = getSharedPreferences("Prefs",MODE_PRIVATE);


        long inch = sh.getLong("inches",0);
        long pound = sh.getLong("pound",0);
        long bmr = sh.getLong("bmr",0);
        //long bmi = (long) BMI(inch,pound);



        bottomNavigationView = findViewById(R.id.teedmenu);

        bottomNavigationView.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.bbb:

                        Intent intent = new Intent(TEDD.this, BMITracker.class);
                        startActivity(intent);
                        break;
                    case R.id.tedd:

                        Intent tedd = new Intent(TEDD.this, TEDD.class);
                        startActivity(tedd);
                        break;
                }
            }
        });

        dialog = new Dialog(this);
        dialog.setContentView(R.layout.custom_dailoge_activityfactor);
        dialog.show();
        q1 = dialog.findViewById(R.id.firstcheck);
        q2 = dialog.findViewById(R.id.secondcheck);
        q3 = dialog.findViewById(R.id.Thirdcheck);
        q4 = dialog.findViewById(R.id.Fourthcheck);
        submitButton = dialog.findViewById(R.id.mybutton);
        sharedPreferences = getSharedPreferences("Prefs",MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(activityFactor > 1) {
                    try {
                        int val = (int) (bmr*activityFactor);
                        Range range = new Range();
                        range.setFrom(0);
                        range.setTo(val);
                        range.setColor(Color.parseColor("#00ff00"));
                        guage.addRange(range);
                        guage.setValue(val);
                        tedtext.setText(val+"");
                        oneday.setText(val-250+" calories/day");
                        twoday.setText(val-500+" calories/day");
                        threeday.setText(val-700+" calories/day");
                        threeandhalf.setText(val-825+" calories/day");
                        fourday.setText(val-950+" calories/day");
                        fourandhalf.setText(val-1075+" calories/day");
                    } catch (Exception e) {

                        Toast.makeText(TEDD.this, "Select Activity Factor", Toast.LENGTH_LONG).show();
                    }
                    dialog.dismiss();
                }
                else
                {
                    Toast.makeText(TEDD.this,"Select Activity Factor",Toast.LENGTH_LONG).show();
                }

            }
        });



    }







    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.bmitopmenu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){

            case R.id.teddoption:
                dialog.show();
                break;
        }
        return super.onOptionsItemSelected(item);

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

    private double BMI(long inch, long pound)
    {

        return  (703 *  (pound)/(inch*inch));
    }

    }