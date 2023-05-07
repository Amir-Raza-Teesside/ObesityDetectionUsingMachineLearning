package uk.ac.tees.aad.obesity2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.ekn.gruzer.gaugelibrary.HalfGauge;
import com.ekn.gruzer.gaugelibrary.Range;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.DecimalFormat;

public class BMITracker extends AppCompatActivity {


    HalfGauge guage;
    float bmi;
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

        guage = findViewById(R.id.guagesec);
        Yourbmi = findViewById(R.id.yourbmi);
        SharedPreferences sh = getSharedPreferences("Prefs",MODE_PRIVATE);


        float inch = sh.getFloat("height",0);
        float kgs = sh.getFloat("weight",0);

       float actualInches = (float) (inch *0.393701);

        long result = (long) (actualInches*actualInches*703);
        //long finalresult = result/pound;


        //Float weight

        float poundsss= (float) (kgs*2.20462);


       float innereresult = poundsss/(actualInches*actualInches);
        float finaalresult =  703*innereresult;

        float choped = finaalresult *100/100;



        //Wheught = 80;
        //POUNS
        //cm 160;

        //inches 62

        //2702332






       // bmi = (long) BMI(inch,pound);

        DecimalFormat df = new DecimalFormat("#.##");
       //
       Toast.makeText(BMITracker.this, String.valueOf(choped) , Toast.LENGTH_SHORT).show();
        guage.setMaxValue(45);
        guage.setMinValue(10);
        guage.setValue(Double.parseDouble(df.format(finaalresult)));
        Range range = new Range();
        range.setFrom(10);
        range.setTo(finaalresult*1.0);
        range.setColor(Color.parseColor("#00ff00"));
       String ScaleText = YourScale((long) finaalresult);
        Yourbmi.setText(ScaleText);



        Range range2 = new Range();
        range2.setFrom(finaalresult*1.0);
        range2.setTo(45);
        range2.setColor(Color.parseColor("#ffffff"));



        guage.addRange(range);

        guage.addRange(range2);




    }

    private float BMI(float inch, float pound)
    {

        return  (703 *  (pound)/(inch*inch));

        // 183 65

        // 2379

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