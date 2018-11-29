package com.example.actc.myapplication.Model;

import java.io.Serializable;
import java.util.ArrayList;

public class Recipe implements Serializable {
    String name;
    int idRecipe;
   ArrayList<Ingredients> ingredient;
    ArrayList <Steps> step ;


    public  Recipe (String name, int ID, ArrayList<Ingredients>ingredient ,ArrayList <Steps> step){
        this.name=name;this.idRecipe =ID;this.ingredient=ingredient;this.step=step;
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setIdRecipe(int idRecipe) {
        this.idRecipe = idRecipe;
    }

    public void setIngredient(ArrayList<Ingredients> ingredient) {
        this.ingredient = ingredient;
    }

    public void setStep(ArrayList<Steps> step) {
        this.step = step;
    }

    public String getName() {
        return name;
    }

    public int getIdRecipe() {
        return idRecipe;
    }

    public ArrayList<Ingredients> getIngredient() {
        return ingredient;
    }

    public ArrayList<Steps> getStep() {
        return step;
    }
}
