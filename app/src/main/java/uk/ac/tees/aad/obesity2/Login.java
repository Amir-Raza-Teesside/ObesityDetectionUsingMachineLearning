package uk.ac.tees.aad.obesity2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class Login extends AppCompatActivity {


    EditText email;
    EditText password;
    Button login;
    FirebaseAuth auth;
    Button Register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.editTextTextEmailAddress);
        password = findViewById(R.id.password);
        login = findViewById(R.id.button2);
        auth = FirebaseAuth.getInstance();

        Register = findViewById(R.id.button3);

        getSupportActionBar().hide();


        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this,Registration.class);
                startActivity(intent);
            }
        });


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               Login();
            }
        });




    }

    private void Login()
    {
        String currentEmail = email.getText().toString();
        String currentPass = password.getText().toString();
        if(currentEmail.isEmpty())
        {
            email.setError("Required");
            email.requestFocus();
            return;
        }
        if(currentPass.isEmpty())
        {
            password.setError("Required");
            password.requestFocus();
            return;
        }
        auth.signInWithEmailAndPassword(currentEmail,currentPass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful())
                {
                   Intent intent = new Intent(Login.this, uk.ac.tees.aad.obesity2.mainact.class);
                    startActivity(intent);
                }


           }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Login.this,"No user Found",Toast.LENGTH_LONG).show();
            }
        });
    }
}