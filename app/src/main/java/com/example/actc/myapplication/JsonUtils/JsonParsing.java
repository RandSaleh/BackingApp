package com.example.actc.myapplication.JsonUtils;


import com.example.actc.myapplication.Model.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonParsing {
    static String name;
    static int idRecipe;
    static Ingredients ingredientsObject;
    static JSONArray ingredientsJsonArray;
    static Steps step;
    static int idSteps;
    static String shortDescription;
    static String description;
    static String videoURL;
    static String thumbnailURL;
    static int quantity;
    static String measure;
    static String ingredient;
    static JSONArray stepsJsonArray;
    static ArrayList<Steps> ArrayListSteps = new ArrayList<Steps>();
    static ArrayList<Ingredients> ArrayListIngredients = new ArrayList<Ingredients>();
    static Recipe recipe;
    //--------------------------------------
    public static String ID_RECIPE = "id";
    public static String NAME = "name";
    public static String INGREDIANTS = "ingredients";
    public static String INGREDIANT = "ingredient";
    public static String QUANTITY = "quantity";
    public static String MEASURE = "measure";
    public static String STEPS = "steps";
    public static String ID_STEPS = "id";
    public static String SHORT_DESCRIPTION = "shortDescription";
    public static String DESCRIPTION = "description";
    public static String VIDEO_URL = "videoURL";
    public static String THUMBNAIL_URL = "thumbnailURL";

    //---------------------------------------
    public static ArrayList<Recipe> parseRecipes(String data) throws JSONException {
        ArrayList<Recipe> allRecipe = new ArrayList<Recipe>();

        JSONArray Starter = new JSONArray(data);
        for (int i = 0; i < Starter.length(); i++) {

            ArrayList<Ingredients> tempIngrediant;
            ArrayList<Steps> tempSteps;

            JSONObject tempRes = Starter.getJSONObject(i);
            idRecipe = tempRes.getInt(ID_RECIPE);
            name = tempRes.getString(NAME);
            ingredientsJsonArray = tempRes.getJSONArray(INGREDIANTS);
            stepsJsonArray = tempRes.getJSONArray(STEPS);
            tempIngrediant = parseingIngrediants(ingredientsJsonArray);
            tempSteps = parseingStepsJsonArray(stepsJsonArray);
            recipe = new Recipe(name, idRecipe, tempIngrediant, tempSteps);
            allRecipe.add(recipe);


        }
        return allRecipe;
    }

    public static ArrayList<JSONObject> convertToArrayList(JSONArray jsonArray) throws JSONException {

        ArrayList<JSONObject> tempArray = new ArrayList<JSONObject>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject temp = jsonArray.getJSONObject(i);
            tempArray.add(temp);
        }
        return tempArray;
    }


    public static ArrayList<Ingredients> parseingIngrediants(JSONArray ingredientsJsonArray) throws JSONException {
        ArrayList<Ingredients> tempIng = new ArrayList<Ingredients>();
        ArrayList<JSONObject> ingrediantsJsonArray = convertToArrayList(ingredientsJsonArray);
        for (int i = 0; i < ingrediantsJsonArray.size(); i++) {
            JSONObject tempIngrediant = ingrediantsJsonArray.get(i);
            quantity = tempIngrediant.getInt(QUANTITY);
            measure = tempIngrediant.getString(MEASURE);
            ingredient = tempIngrediant.getString(INGREDIANT);
            ingredientsObject = new Ingredients(quantity, measure, ingredient);
            tempIng.add(ingredientsObject);
        }

        return tempIng;

    }

    public static ArrayList<Steps> parseingStepsJsonArray(JSONArray stepsJsonArray) throws JSONException {
        ArrayList<Steps> tempStepReady = new ArrayList<Steps>();
        for (int i = 0; i < stepsJsonArray.length(); i++) {
            JSONObject tempStep = stepsJsonArray.getJSONObject(i);
            idSteps = tempStep.getInt(ID_STEPS);
            shortDescription = tempStep.getString(SHORT_DESCRIPTION);
            description = tempStep.getString(DESCRIPTION);
            videoURL = tempStep.getString(VIDEO_URL);
            thumbnailURL = tempStep.getString(THUMBNAIL_URL);
            step = new Steps(idSteps, shortDescription, description, videoURL, thumbnailURL);
            tempStepReady.add(step);
        }
        return tempStepReady;


    }

    public static ArrayList<Steps> getArrayListSteps() {
        return ArrayListSteps;
    }

    public static ArrayList<Ingredients> getArrayListIngredients() {
        return ArrayListIngredients;
    }


} ///JsonParsing class
