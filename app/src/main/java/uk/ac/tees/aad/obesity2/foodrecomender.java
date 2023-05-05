package uk.ac.tees.aad.obesity2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
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
import uk.ac.tees.aad.obesity2.Model.food;
import uk.ac.tees.aad.obesity2.Adapter.foodadapter;
import uk.ac.tees.aad.obesity2.NetworkRequest.Singleton;

import java.util.ArrayList;

public class foodrecomender extends AppCompatActivity {


    TextView textView;
    Button button;
    RecyclerView recs;
    ArrayList<food> foodArrayList;
    foodadapter foodadapter;
    Dialog DietFilterDailoge;
    public  static String url = "https://api.edamam.com/api/recipes/v2?type=any&q=chicken&app_id=c39a0c06&app_key=e5f6f66858d02c3d7640b05a87c5daa7&diet=high-protein";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foodrecomender);

        foodArrayList = new ArrayList<>();
        foodadapter = new foodadapter(foodArrayList,foodrecomender.this);
        recs = findViewById(R.id.recs);
        recs.setLayoutManager(new LinearLayoutManager(this));
        recs.setAdapter(foodadapter);
        DietFilterDailoge = new Dialog(this);
        DietFilterDailoge.setContentView(R.layout.custom_dailoge_foodselection);
        DietFilterDailoge.show();



        button = findViewById(R.id.btn);

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
                            for(int i =0; i< array.length();i++)
                            {
                                JSONObject objects = array.getJSONObject(i);
                                JSONObject recipedetail = objects.getJSONObject("recipe");

                                JSONArray dietlabel = recipedetail.getJSONArray("dietLabels");
                                String  imagesource = recipedetail.getString("image");
                                String foodlabel = recipedetail.getString("label");
                                String foodcalories = recipedetail.getString("calories");
                                String info1 = dietlabel.getString(0);
                                String info2 = dietlabel.getString(1);
                                JSONArray healthlabel = recipedetail.getJSONArray("healthLabels");
                                JSONArray cuisineType = recipedetail.getJSONArray("cuisineType");

                               JSONObject totalNutrients = recipedetail.getJSONObject("totalNutrients");



                               JSONObject kcal = totalNutrients.getJSONObject("FAT");
                               JSONObject carb = totalNutrients.getJSONObject("CHOCDF");
                               JSONObject Procnt = totalNutrients.getJSONObject("PROCNT");

                               JSONObject Chloe = totalNutrients.getJSONObject("CHOLE");
                               JSONObject Na = totalNutrients.getJSONObject("NA");
                               JSONObject CA = totalNutrients.getJSONObject("CA");
                               JSONObject MG = totalNutrients.getJSONObject("MG");
                               JSONObject FE = totalNutrients.getJSONObject("FE");

                               String cholestrol = Chloe.getString("quantity");
                               String cholestrolformat = cholestrol.substring(0,4)+" mg";

                                String sodium = Na.getString("quantity");
                                String sodiumFormat = sodium.substring(0,4)+ " mg";

                                String calcium =  CA.getString("quantity");
                                String calciumformat = calcium.substring(0,4)+ " mg";

                                String magnisium =  MG.getString("quantity");
                                String magnisumformat = magnisium.substring(0,4)+ " mg";

                                String Iron = FE.getString("quantity");
                                String IronFormat = Iron.substring(0,4) + " mg";




                                String fat = kcal.getString("quantity");
                                String fatformat = fat.substring(0,4)+" g";
                                String carbs = carb.getString("quantity");
                                String carbsformat = carbs.substring(0,4)+" g";
                                String Protien = Procnt.getString("quantity");
                                String protienformat = Protien.substring(0,4)+" g";

                               // button.setText(kcal.getString("label"));




                                String info3 = healthlabel.getString(0);
                                String info4 = healthlabel.getString(1);
                                String info5 = healthlabel.getString(2);
                                String info6 = healthlabel.getString(3);
                                String info7 = healthlabel.getString(4);
                                String info8 = healthlabel.getString(5);
                                String info9 = healthlabel.getString(6);
                                String info10 = healthlabel.getString(7);
                                String info11 = healthlabel.getString(8);
                                String info12 = healthlabel.getString(9);
                                String cousine = cuisineType.getString(0);



                                foodArrayList.add(new food(imagesource,foodlabel,foodcalories.substring(0,4),info1,info2,info3,info4,info5,info6,info7,info8,info9,info10,info11,info12,cousine,protienformat,fatformat,carbsformat,cholestrolformat,calciumformat,IronFormat,magnisumformat,sodiumFormat));

                                foodadapter.notifyDataSetChanged();
                            }



                           // JSONObject recipedetail = objects.getJSONObject("recipe");












                        } catch (JSONException e) {
                            e.printStackTrace();
                        }




                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(foodrecomender.this, error.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });

                Singleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectRequest);

            }
        });



    }
}