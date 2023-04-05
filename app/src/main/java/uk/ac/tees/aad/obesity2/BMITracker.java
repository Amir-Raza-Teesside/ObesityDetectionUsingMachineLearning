package uk.ac.tees.aad.obesity2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.TextView;
import android.widget.Toast;

import com.ekn.gruzer.gaugelibrary.HalfGauge;
import com.ekn.gruzer.gaugelibrary.Range;

public class BMITracker extends AppCompatActivity {


    HalfGauge guage;
    long bmi;
    TextView Yourbmi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmitracker);

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
        range.setTo(18.9);
        range.setColor(Color.parseColor("#ffffff"));
        String ScaleText = YourScale(bmi);
        Yourbmi.setText(ScaleText);



        Range range2 = new Range();
        range2.setFrom(19);
        range2.setTo(24.9);
        range2.setColor(Color.parseColor("#00ff00"));

        Range range3 = new Range();
        range3.setFrom(25);
        range3.setTo(45);
        range3.setColor(Color.parseColor("#ff0000"));

        guage.addRange(range);

        guage.addRange(range2);
        guage.addRange(range3);



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