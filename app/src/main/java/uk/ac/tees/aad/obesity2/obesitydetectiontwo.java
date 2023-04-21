package uk.ac.tees.aad.obesity2;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import  uk.ac.tees.aad.obesity2.NetworkRequest.Singleton;
import com.google.firebase.auth.FirebaseAuth;

import java.util.HashMap;
import java.util.Map;

public class obesitydetectiontwo extends AppCompatActivity {


    Spinner Favc;
    String familyhistory;
    String smoke;
    String transport;
    String caec;
    String calc;
    String Favcvalue;
    public static String url = "https://obesitydetection.herokuapp.com/obese";
    EditText etwter;

    Button predict;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_obesitydetectiontwo);
        getSupportActionBar().hide();

        etwter = findViewById(R.id.water);

        SharedPreferences sh = getSharedPreferences("Prefs",MODE_PRIVATE);
        String gender = sh.getString("gender","");
        int age = sh.getInt("age",0);
        float height = sh.getFloat("height",0f);
        float weight = sh.getFloat("weight",0f);
        predict = findViewById(R.id.predict);
        predict.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              /*  String water = etwter.getText().toString();
                if(water.isEmpty())
                {
                    etwter.setError("Required");
                    etwter.requestFocus();
                    return;

                }*/

                Predict(GenderConveror(gender), String.valueOf(age),InchestoMeter(height), String.valueOf(weight)
                ,familyhistory, Favcvalue,caec,smoke,etwter.getText().toString(),calc,transport);
            }
        });



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
        //Toast.makeText(obesitydetectiontwo.this,familyhistory + smoke + transport + caec + calc,Toast.LENGTH_LONG).show();








    }

    private void Predict(String genders, String Ages, String heights,String weights
    , String Familyissues, String Favcs, String Caecs,String smokes
    , String ch2O, String calcs, String tansportss) {


        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Toast.makeText(obesitydetectiontwo.this,response.toString(),Toast.LENGTH_LONG).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(obesitydetectiontwo.this, error.toString(),Toast
                .LENGTH_LONG).show();
                predict.setText(error.getMessage());

            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> detectiondata = new HashMap<>();
                detectiondata.put("Gender",genders);
                detectiondata.put("Age",Ages);
                detectiondata.put("Height",heights);
                detectiondata.put("Weight",weights);
                detectiondata.put("family_history_with_overweight",Familyissues);
                detectiondata.put("FAVC",Favcs);
                detectiondata.put("FCVC","2.0");
                detectiondata.put("CAEC",Caecs);
                detectiondata.put("SMOKE",smokes);
                detectiondata.put("CH2O",ch2O);
                detectiondata.put("CALC",calcs);
                detectiondata.put("MTRANS",tansportss);


                return  detectiondata;
            }


        };

        RequestQueue queue = Volley.newRequestQueue(obesitydetectiontwo.this);
        queue.add(request);

    }

    public String GenderConveror(String gender)
    {
        String ConvertedGender="";
        if(gender.equals("Male"))
        {
            ConvertedGender = "1";
        }
        if(gender.equals("Female"))
        {
            ConvertedGender = "0";
        }

        return ConvertedGender;
    }
    public String InchestoMeter(float hieght)
    {
        return  hieght/100 +"";
    }
}