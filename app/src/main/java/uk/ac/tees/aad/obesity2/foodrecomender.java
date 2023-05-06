package uk.ac.tees.aad.obesity2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
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
    CheckBox HighProtien, Balanced, HighFiber, LowCarb,LowFat,LowSodium;
    static String DietLabel="high-protein";
    public  static String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foodrecomender);

        url= "https://api.edamam.com/api/recipes/v2?type=any&app_id=c39a0c06&app_key=7dc4102e52b4d2cc106eb7ee6338eceb&diet="+DietLabel;

     //   url = "https://api.edamam.com/api/recipes/v2?&&app_id=c39a0c06&app_key=7dc4102e52b4d2cc106eb7ee6338eceb";

        foodArrayList = new ArrayList<>();
        foodadapter = new foodadapter(foodArrayList,foodrecomender.this);
        recs = findViewById(R.id.recs);
        recs.setLayoutManager(new LinearLayoutManager(this));
        recs.setAdapter(foodadapter);




      //  foodArrayList.add(new food("s","s","Ss","S","S","S","S","S","s","s","s","s","s","S","S","S","S","S","S","s","S","S","S"));
        button = findViewById(R.id.btn);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Toast.makeText(foodrecomender.this, "done", Toast.LENGTH_SHORT).show();
                        //Toast.makeText(foodrecomender.this, response.toString(), Toast.LENGTH_SHORT).show();

                        try {


                            Toast.makeText(foodrecomender.this, "trying", Toast.LENGTH_SHORT).show();

                            JSONArray array = response.getJSONArray("hits");
                           // textView.setText(array.length()+"");

                           // button.setText(response.length() +"///" + array.length());
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
                               String cholestrolformat = cholestrol.substring(0,2)+" mg";

                                String sodium = Na.getString("quantity");
                                String sodiumFormat = sodium.substring(0,2)+ " mg";

                                String calcium =  CA.getString("quantity");
                                String calciumformat = calcium.substring(0,2)+ " mg";

                                String magnisium =  MG.getString("quantity");
                                String magnisumformat = magnisium.substring(0,2)+ " mg";

                                String Iron = FE.getString("quantity");
                                String IronFormat = Iron.substring(0,2) + " mg";




                                String fat = kcal.getString("quantity");
                                String fatformat = fat.substring(0,2)+" g";
                                String carbs = carb.getString("quantity");
                                String carbsformat = carbs.substring(0,2)+" g";
                                String Protien = Procnt.getString("quantity");
                                String protienformat = Protien.substring(0,2)+" g";

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
















                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(foodrecomender.this,e.getMessage(),Toast.LENGTH_LONG).show();
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

        DietFilterDailoge = new Dialog(this);
        DietFilterDailoge.setContentView(R.layout.custom_dailoge_foodselection);
        DietFilterDailoge.show();
        HighFiber = DietFilterDailoge.findViewById(R.id.HighFiber);
        HighProtien = DietFilterDailoge.findViewById(R.id.HighProtien);
        Balanced = DietFilterDailoge.findViewById(R.id.balanced);
        LowCarb = DietFilterDailoge.findViewById(R.id.LowCarb);
        LowFat = DietFilterDailoge.findViewById(R.id.LowFat);
        LowSodium = DietFilterDailoge.findViewById(R.id.LowSodium);

    }


    public void ondietFilter(View view)
    {
        boolean checked = ((CheckBox) view).isChecked();
        switch (view.getId())
        {

            case R.id.HighProtien:
                if(checked)
                {
                  HighProtien.setChecked(true);
                  Balanced.setChecked(false);
                  HighFiber.setChecked(false);
                  LowCarb.setChecked(false);
                  LowSodium.setChecked(false);
                  LowFat.setChecked(false);
                 DietLabel = "high-protein";
                //    Toast.makeText(foodrecomender.this, DietLabel, Toast.LENGTH_SHORT).show();

                }
                else {

                }
                break;
            case R.id.balanced:
                if(checked)
                {
                    HighProtien.setChecked(false);
                    Balanced.setChecked(true);
                    HighFiber.setChecked(false);
                    LowCarb.setChecked(false);
                    LowSodium.setChecked(false);
                    LowFat.setChecked(false);
                   DietLabel = "balanced";
                   // Toast.makeText(foodrecomender.this, DietLabel, Toast.LENGTH_SHORT).show();

                }
                else {

                }
                break;
            case R.id.HighFiber:
                if(checked)
                {
                    HighProtien.setChecked(false);
                    Balanced.setChecked(false);
                    HighFiber.setChecked(true);
                    LowCarb.setChecked(false);
                    LowSodium.setChecked(false);
                    LowFat.setChecked(false);
                    DietLabel = "high-fiber";
                  //  Toast.makeText(foodrecomender.this, DietLabel, Toast.LENGTH_SHORT).show();

                }
                else {

                }
                break;
            case R.id.LowCarb:
                if(checked)
                {
                    HighProtien.setChecked(false);
                    Balanced.setChecked(false);
                    HighFiber.setChecked(false);
                    LowCarb.setChecked(true);
                    LowSodium.setChecked(false);
                    LowFat.setChecked(false);
                    DietLabel = "low-carb";
                  //  Toast.makeText(foodrecomender.this, DietLabel, Toast.LENGTH_SHORT).show();

                }
                else {

                }
                break;
            case R.id.LowSodium:
                if(checked)
                {
                    HighProtien.setChecked(false);
                    Balanced.setChecked(false);
                    HighFiber.setChecked(false);
                    LowCarb.setChecked(false);
                    LowSodium.setChecked(true);
                    LowFat.setChecked(false);
                    DietLabel = "low-sodium";
                 //   Toast.makeText(foodrecomender.this, DietLabel, Toast.LENGTH_SHORT).show();

                }
                else {

                }
                break;
            case R.id.LowFat:
                if(checked)
                {
                    HighProtien.setChecked(false);
                    Balanced.setChecked(false);
                    HighFiber.setChecked(false);
                    LowCarb.setChecked(false);
                    LowSodium.setChecked(false);
                    LowFat.setChecked(true);
                    DietLabel = "low-fat";
                  //  Toast.makeText(foodrecomender.this, DietLabel, Toast.LENGTH_SHORT).show();

                }
                else {

                }
                break;






        }
    }
}