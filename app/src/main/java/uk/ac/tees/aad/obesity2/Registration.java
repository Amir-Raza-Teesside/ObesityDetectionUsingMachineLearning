package uk.ac.tees.aad.obesity2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.slider.Slider;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import uk.ac.tees.aad.obesity2.Model.user;

public class Registration extends AppCompatActivity {

    TextView Name,Email,Password,Age,Weight,Height;
    Slider AgeSlider,WeightSlider,HeightSlider;
    FirebaseAuth auth;
    Button register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        Name = findViewById(R.id.personName);
        Email = findViewById(R.id.editTextTextEmailAddress);
        Password = findViewById(R.id.password);
        Age = findViewById(R.id.age);
        Weight = findViewById(R.id.weight);
        Height = findViewById(R.id.height);

        register = findViewById(R.id.button2);
        auth = FirebaseAuth.getInstance();


        AgeSlider = findViewById(R.id.ageslider);
        WeightSlider = findViewById(R.id.weightslider);
        HeightSlider = findViewById(R.id.heighttslider);

        getSupportActionBar().hide();


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegisterUser();
            }
        });

        AgeSlider.addOnChangeListener(new Slider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull Slider slider, float value, boolean fromUser) {
                int ageholder = (int)value;
                Age.setText(String.valueOf(ageholder));
            }
        });

        WeightSlider.addOnChangeListener(new Slider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull Slider slider, float value, boolean fromUser) {
                int WeightHolder = (int)value;
                Weight.setText(String.valueOf(WeightHolder));
            }
        });

        HeightSlider.addOnChangeListener(new Slider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull Slider slider, float value, boolean fromUser) {
               int heightHolder = (int)value;
               Height.setText(String.valueOf(heightHolder));
            }
        });

    }




    private void RegisterUser()
    {
        String dummyName = Name.getText().toString();
        String dummyEmail = Email.getText().toString();
        String dummypassword = Password.getText().toString();
        String dummyAge = Age.getText().toString();
        String dummyHeight = Height.getText().toString();
        String dummyWeight = Weight.getText().toString();

        if(dummyEmail.isEmpty())
        {
            Email.setError("Email is Required");
            Email.requestFocus();
            return;
        }
        if(dummypassword.isEmpty())
        {
            Password.setError("Password is Required");
            Password.requestFocus();
            return;
        }
        if(dummypassword.length() <5)
        {
            Password.setError("Password Length");
            Password.requestFocus();
            return;
        }
        if(dummyName.isEmpty())
        {
            Name.setError("Required");
            Name.requestFocus();
            return;
        }
        if(dummyAge.isEmpty())
        {
            Age.setError("Required");
            Age.requestFocus();
            return;
        }
        if(dummyWeight.isEmpty())
        {
            Weight.setError("Required");
            Weight.requestFocus();
            return;
        }
        if(dummyHeight.isEmpty())
        {
            Height.setError("Required");
            Height.requestFocus();
            return;
        }


       auth.createUserWithEmailAndPassword(dummyEmail,dummypassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                   // Toast.makeText(Registration.this,"Successing",Toast.LENGTH_LONG).show();
                   user user = new user(dummyName,dummyEmail,Integer.parseInt(dummyAge),Float.parseFloat(dummyWeight),Float.parseFloat(dummyHeight),"Male");

                    FirebaseDatabase.getInstance().getReference("user")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            Toast.makeText(Registration.this,"Success",Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(Registration.this, uk.ac.tees.aad.obesity2.Login.class);
                            startActivity(intent);
                        }
                    });


                }
                else{
                    Toast.makeText(Registration.this,"Noo",Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}