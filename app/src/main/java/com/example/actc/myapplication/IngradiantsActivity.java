package com.example.actc.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.example.actc.myapplication.Adapters.IngradiantsAdapter;
import com.example.actc.myapplication.Model.Ingredients;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import widget.WidgetUpdateService;

public class IngradiantsActivity extends AppCompatActivity {
@BindView(R.id.RecyclerViewIngradiants) RecyclerView recyclerView;
    IngradiantsAdapter ingradiantsAdapter;
    ArrayList<Ingredients> ingradiants;
   @BindView(R.id.btn_home) Button btnHome;
public  static String INGREDIANT="Ingradiant";
    @OnClick(R.id.btn_home)
    public void goToHome(){
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingradiants);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        if (intent.hasExtra(INGREDIANT)) {
            ingradiants = (ArrayList<Ingredients>) intent.getSerializableExtra(INGREDIANT);
        }
        ingradiantsAdapter = new IngradiantsAdapter(ingradiants.size(), ingradiants);
        GridLayoutManager manager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(manager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(ingradiantsAdapter);
        startWidgetService();

    }
    void startWidgetService()
    {
        Intent i = new Intent(this, WidgetUpdateService.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(INGREDIANT, ingradiants);
        i.putExtra(MainActivity.BUNDLE, bundle);
        i.setAction(WidgetUpdateService.WIDGET_UPDATE_ACTION);
        startService(i);
    }



}
