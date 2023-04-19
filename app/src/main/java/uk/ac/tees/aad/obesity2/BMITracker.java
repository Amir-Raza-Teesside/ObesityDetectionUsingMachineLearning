package uk.ac.tees.aad.obesity2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.ekn.gruzer.gaugelibrary.HalfGauge;
import com.ekn.gruzer.gaugelibrary.Range;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class BMITracker extends AppCompatActivity {


    HalfGauge guage;
    long bmi;
    TextView Yourbmi;
    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmitracker);

        bottomNavigationView = findViewById(R.id.bmibottomnavigation);
        bottomNavigationView.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {

                switch (item.getItemId())
                {
                    case R.id.bbb:

                        Intent intent = new Intent(BMITracker.this, BMITracker.class);
                        startActivity(intent);
                        break;
                    case R.id.tedd:

                        Intent tedd = new Intent(BMITracker.this, TEDD.class);
                        startActivity(tedd);
                        break;
                }

            }
        });

        guage = findViewById(R.id.guage);
        Yourbmi = findViewById(R.id.yourbmi);
        SharedPreferences sh = getSharedPreferences("Prefs",MODE_PRIVATE);


        long inch = sh.getLong("inches",0);
        long pound = sh.getLong("pound",0);




        bmi = (long) BMI(inch,pound);
        guage.setMaxValue(45);
        guage.setMinValue(10);
        guage.setValue(bmi);
        Range range = new Range();
        range.setFrom(10);
        range.setTo(bmi);
        range.setColor(Color.parseColor("#ffffff"));
        String ScaleText = YourScale(bmi);
        Yourbmi.setText(ScaleText);



        Range range2 = new Range();
        range2.setFrom(bmi);
        range2.setTo(45);
        range2.setColor(Color.parseColor("#00ff00"));



        guage.addRange(range);

        guage.addRange(range2);




    }

    private double BMI(long inch, long pound)
    {

        return  (703 *  (pound)/(inch*inch));
    }

    private String YourScale(long bmi)
    {
        String val = "";
        if(bmi < 16.0)
        {
            val = "Your Current BMI: Very Severely Underweight";
        }
        if(bmi >= 16.0 && bmi < 16.9)
        {
            val = "Your Current BMI: Severely Underweight";
        }
        if(bmi >= 17.0 && bmi < 18.4)
        {
            val = "Your Current BMI: Underweight";
        }
        if(bmi >= 18.5 && bmi < 24.9)
        {
            val = "Your Current BMI: Normal";
        }
        if(bmi >= 25.0 && bmi < 29.9)
        {
            val = "Your Current BMI: OverWeight";
        }
        if(bmi >= 30.0 && bmi < 34.9)
        {
            val = "Your Current BMI: Obese Class I";
        }
        if(bmi >= 35.0 && bmi < 39.9)
        {
            val = "Your Current BMI: Obese Class II";
        }
        if(bmi >= 40)
        {
            val = "Your Current BMI: Obese Class III";
        }


        return  val;
    }
}