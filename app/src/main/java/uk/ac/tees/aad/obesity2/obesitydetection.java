package uk.ac.tees.aad.obesity2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class obesitydetection extends AppCompatActivity {


    Spinner FamilyHistory, Smoke, Transport, Caec, Calc;
    Button next;
    public static String Familyhistoryvlue, smokevalue, transportvalue, CaecValue,CalcValue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_obesitydetection);
        getSupportActionBar().hide();

        FamilyHistory = findViewById(R.id.familyhistory);
        Smoke = findViewById(R.id.smoke);
        Transport = findViewById(R.id.Transport);
        Caec = findViewById(R.id.CAEC);
        Calc = findViewById(R.id.HighCaloric);
        next = findViewById(R.id.nextdata);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(obesitydetection.this,obesitydetectiontwo.class);
                intent.putExtra("familyhistory", Familyhistoryvlue);
                intent.putExtra("smoke",smokevalue);
                intent.putExtra("transport", transportvalue);
                intent.putExtra("caec",CaecValue);
                intent.putExtra("calc",CalcValue);
                startActivity(intent);
            }
        });



        ArrayAdapter<CharSequence> calc = ArrayAdapter.createFromResource(this,
                R.array.CALC, android.R.layout.simple_spinner_item);
        calc.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Calc.setAdapter(calc);
        Calc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                switch ((int) id)
                {
                    case 0:
                        CalcValue = "0";
                        break;
                    case 1:
                        CalcValue= "1";
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        ArrayAdapter<CharSequence> caec = ArrayAdapter.createFromResource(this,
                R.array.CAEC, android.R.layout.simple_spinner_item);
        caec.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Caec.setAdapter(caec);
        Caec.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                switch ((int) id)
                {
                    case 0:
                        CaecValue = "0";
                        break;
                    case 1:
                        CaecValue= "1";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        ArrayAdapter<CharSequence> transport = ArrayAdapter.createFromResource(this,
                R.array.Transport, android.R.layout.simple_spinner_item);
        transport.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Transport.setAdapter(transport);
        Transport.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                switch ((int) id)
                {
                    case 0:
                        transportvalue = "3";
                        break;
                    case 1:
                        transportvalue = "4";
                    case 2:
                        transportvalue = "0";
                        break;
                    case 3:
                        transportvalue = "2";
                        break;
                    case 4:
                        transportvalue = "1";
                        break;


                }



            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        ArrayAdapter<CharSequence> smoke = ArrayAdapter.createFromResource(this,
                R.array.smoke, android.R.layout.simple_spinner_item);
        smoke.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Smoke.setAdapter(smoke);
        Smoke.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                switch ((int) id)
                {
                    case 0:
                        smokevalue = "0";
                        break;
                    case 1:
                        smokevalue = "1";
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        ArrayAdapter<CharSequence> Family = ArrayAdapter.createFromResource(this,
                R.array.familyhistory, android.R.layout.simple_spinner_item);
        Family.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        FamilyHistory.setAdapter(Family);
        FamilyHistory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                switch ((int) id)
                {
                    case 0:
                        Familyhistoryvlue = "0";
                        break;
                    case 1:
                        Familyhistoryvlue = "1";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}