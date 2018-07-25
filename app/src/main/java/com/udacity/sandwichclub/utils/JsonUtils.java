package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonUtils {

    private static final String LOG_TAG = JsonUtils.class.getSimpleName();

    public static Sandwich parseSandwichJson(String json) {
        if (json == null) {
            return null;
        }

        Sandwich sandwich = null;

        try {
            JSONObject item = new JSONObject(json);

            JSONObject name = item.getJSONObject("name");
            String mainName = name.getString("mainName");

            JSONArray alsoKnownAsJsonArray = name.getJSONArray("alsoKnownAs");
            ArrayList<String> alsoKnownAs = new ArrayList<>();
            for (int i = 0; i < alsoKnownAsJsonArray.length(); i++) {
                String otherName = alsoKnownAsJsonArray.get(i).toString();
                alsoKnownAs.add(otherName);
            }

            String placeOfOrigin = item.getString("placeOfOrigin");
            String description = item.getString("description");
            String image = item.getString("image");

            JSONArray ingredientsJsonArray = item.getJSONArray("ingredients");
            ArrayList<String> ingredients = new ArrayList<>();
            for (int i = 0; i < ingredientsJsonArray.length(); i++) {
                String ingredient = ingredientsJsonArray.get(i).toString();
                ingredients.add(ingredient);
            }

            sandwich = new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description, image, ingredients);
        } catch (JSONException e) {
            Log.e(LOG_TAG, "Error parsing JSON.");
        }

        return sandwich;
    }
}
