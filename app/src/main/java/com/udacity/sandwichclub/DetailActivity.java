package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    private static final String LOG_TAG = DetailActivity.class.getSimpleName();

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        final ImageView ingredientsIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv, new Callback() {
                    @Override
                    public void onSuccess() {
                        Log.d(LOG_TAG, "Image loading success.");
                    }

                    @Override
                    public void onError() {
                        Log.e(LOG_TAG, "Error retrieving image.");
                        ingredientsIv.setVisibility(View.GONE);
                    }
                });

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        TextView alsoKnownLabel = findViewById(R.id.also_known_label);
        TextView alsoKnownTextView = findViewById(R.id.also_known_tv);
        String alsoKnown = formatList(sandwich.getAlsoKnownAs());
        if (alsoKnown.isEmpty()) {
            alsoKnownLabel.setVisibility(View.GONE);
            alsoKnownTextView.setVisibility(View.GONE);
        } else {
            alsoKnownTextView.setText(alsoKnown);
        }

        TextView ingredientsLabel = findViewById(R.id.ingredients_label);
        TextView ingredientsTextView = findViewById(R.id.ingredients_tv);
        String ingredients = formatList(sandwich.getIngredients());
        if (ingredients.isEmpty()) {
            ingredientsLabel.setVisibility(View.GONE);
            ingredientsTextView.setVisibility(View.GONE);
        } else {
            ingredientsTextView.setText(ingredients);
        }

        TextView originLabel = findViewById(R.id.origin_label);
        TextView originTextView = findViewById(R.id.origin_tv);
        String origin = sandwich.getPlaceOfOrigin();
        if (origin.isEmpty()) {
            originLabel.setVisibility(View.GONE);
            originTextView.setVisibility(View.GONE);
        } else {
            originTextView.setText(origin);
        }

        TextView descriptionLabel = findViewById(R.id.description_label);
        TextView descriptionTextView = findViewById(R.id.description_tv);
        String description = sandwich.getDescription();
        if (description.isEmpty()) {
            descriptionLabel.setVisibility(View.GONE);
            descriptionTextView.setVisibility(View.GONE);
        } else {
            descriptionTextView.setText(description);
        }
    }

    private String formatList(List<String> list) {
        StringBuilder builder = new StringBuilder("");
        for (int i = 0; i < list.size(); i++) {
            if (i != 0) {
                builder.append("\n");
            }
            builder.append(list.get(i));
        }
        return builder.toString();
    }
}
