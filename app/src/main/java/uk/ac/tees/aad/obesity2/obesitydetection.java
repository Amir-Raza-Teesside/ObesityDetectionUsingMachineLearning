package uk.ac.tees.aad.obesity2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class obesitydetection extends AppCompatActivity {


    Spinner FamilyHistory, Smoke, Transport, Caec, Calc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_obesitydetection);

        FamilyHistory = findViewById(R.id.familyhistory);


        ArrayAdapter<CharSequence> familydapter = ArrayAdapter.createFromResource(this,
                R.array.familyhistory, android.R.layout.simple_spinner_item);
        familydapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        FamilyHistory.setAdapter(familydapter);

        FamilyHistory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

    }
}