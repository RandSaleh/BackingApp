package com.example.actc.myapplication;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.actc.myapplication.Adapters.RecipieAdapter;
import com.example.actc.myapplication.JsonUtils.JsonParsing;
import com.example.actc.myapplication.Model.Recipe;
import com.example.actc.myapplication.NetworkUtils.NetworkUtils;

import org.json.JSONException;

import java.io.IOException;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;


public class MainActivity extends AppCompatActivity implements RecipieAdapter.ListMovieClickListener, Serializable {
    public static ArrayList<Recipe> allRecipe = new ArrayList<Recipe>();
    @BindView(R.id.RecyclerViewRecipe)
    RecyclerView rv;

    RecipieAdapter adapter;
    public String NAME = "name";
    public static final String BUNDLE = "bundle";
    public static final String INGREDIANT = "ingrediants";
    public static String SEND_OBJECT = "sendObject";

    @Override
    public void onclickListener(int pos) {
        Recipe temp = allRecipe.get(pos);

        Intent intent = new Intent(MainActivity.this, MainActivityDetails.class);
        intent.putExtra(SEND_OBJECT, temp);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        try {

            if (allRecipe.isEmpty())
                new FetchingDataJson().execute(new URL("https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json"));

            else {
                nowDisplayRecipe(NAME);

            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Timber.plant(new Timber.DebugTree());

    }


    public class FetchingDataJson extends AsyncTask<URL, Void, String> implements Serializable {


        @Override
        protected String doInBackground(URL... urls) {
            String result = null;
            try {
                result = NetworkUtils.getResponseFromHttpUrl(urls[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s != null && s != "") {
                try {

                    allRecipe = JsonParsing.parseRecipes(s);
                    nowDisplayRecipe(NAME);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }


        }
    }


    public void nowDisplayRecipe(String typeDisplay) {
        if (typeDisplay.equals(NAME)) {
            ArrayList<String> names = new ArrayList<String>();
            for (int i = 0; i < allRecipe.size(); i++) {
                Recipe temp = allRecipe.get(i);
                names.add(temp.getName());
            }

            adapter = new RecipieAdapter(this, 4, names, this);


        }
        GridLayoutManager manager = new GridLayoutManager(this, 2);
        rv.setLayoutManager(manager);
        rv.setHasFixedSize(true);
        rv.setAdapter(adapter);


    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("ALLRECIPIE", allRecipe);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null)
            allRecipe = (ArrayList<Recipe>) savedInstanceState.getSerializable("ALLRECIPIE");
    }
}
