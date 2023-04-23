package uk.ac.tees.aad.obesity2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class foodrecomender extends AppCompatActivity {


    TextView textView;
    Button button;
    public  static String url = "https://api.edamam.com/api/recipes/v2?type=any&q=chicken&app_id=c39a0c06&app_key=e5f6f66858d02c3d7640b05a87c5daa7&diet=high-protein&calories=1000-1500";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foodrecomender);

        textView = findViewById(R.id.tt);
        button = findViewById(R.id.btn);
        Toast.makeText(foodrecomender.this, "rtessd", Toast.LENGTH_SHORT).show();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Toast.makeText(foodrecomender.this, "done", Toast.LENGTH_SHORT).show();
                        try {


                            JSONArray array = response.getJSONArray("hits");
                           // textView.setText(array.length()+"");
                            JSONObject objects = array.getJSONObject(0);
                            JSONObject recipedetail = objects.getJSONObject("recipe");
                          //  textView.setText(recipedetail.getString("label"));
                            JSONArray dietlabel = recipedetail.getJSONArray("dietLabels");

                            textView.setText(dietlabel.toString());








                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        Toast.makeText(foodrecomender.this, response.toString(), Toast.LENGTH_SHORT).show();

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(foodrecomender.this, "done", Toast.LENGTH_SHORT).show();

                    }
                });

                RequestQueue queue = Volley.newRequestQueue(foodrecomender.this);
                queue.add(jsonObjectRequest);

            }
        });



    }
}