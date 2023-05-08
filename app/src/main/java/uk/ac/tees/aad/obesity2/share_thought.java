package uk.ac.tees.aad.obesity2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;

import uk.ac.tees.aad.obesity2.Model.TweetModel;

public class share_thought extends AppCompatActivity {


    EditText editText;
    Button button;
    FirebaseDatabase database;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_thought);



        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        editText = findViewById(R.id.dotweet);
        button = findViewById(R.id.btntweetsubmit);

        SharedPreferences sh = getSharedPreferences("Prefs",MODE_PRIVATE);
        String Name = sh.getString("name","");


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mytweet = editText.getText().toString();
                if(mytweet == null)
                {
                    editText.setError("required");
                    editText.requestFocus();
                    return;
                }

                Date date = new Date();
                TweetModel model = new TweetModel(Name,
                        auth.getCurrentUser().getEmail(),mytweet,"0");

                DatabaseReference reference = database.getReference("tweet")
                        .child(date.getTime()+"");
                reference.setValue(model);
                editText.setText("");







            }
        });


    }
}