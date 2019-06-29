package com.codepath.flicks;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.codepath.flicks.models.Movie;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import cz.msebera.android.httpclient.Header;

public class MovieDetailsActivity extends AppCompatActivity {

    // the movie to display
    Movie movie;

    // the view objects
    TextView tvtitle;
    TextView tvOverview;
    RatingBar rbVotingAverage;
    String videoId;
    ImageView ivBackdropimage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        // resolve the view objects
        tvtitle = findViewById(R.id.tvTitle);
        tvOverview = findViewById(R.id.tvOverview);
        rbVotingAverage = findViewById(R.id.rbVoteAverage);
        ivBackdropimage = findViewById(R.id.ivBackdropimage);

        // unwrap the movie passed in via intent, using its simple name as a key
        movie = Parcels.unwrap(getIntent().getParcelableExtra(Movie.class.getSimpleName()));
        Log.d("MovieDetailsActivity", String.format("Showing details for '%s'", movie.getTitle()));

        getTrailerKey(movie);

        // set the title and overview
        tvtitle.setText(movie.getTitle());
        tvOverview.setText(movie.getOverview());

        // vote average is 0..10, convert to 0..5 by dividing by 2
        float voteAverage = movie.getVoteAverage().floatValue();
        rbVotingAverage.setRating(voteAverage = (voteAverage > 0 ? voteAverage / 2.0f : voteAverage));


        ivBackdropimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (videoId != null) {
                    // create intent for the new activity
                    Intent intent = new Intent(view.getContext(), MovieTrailerActivity.class);

                    intent.putExtra("videoId", videoId);

                    // show the activity
                    view.getContext().startActivity(intent);
                }
            }
        });
    }

    // CONSTANTS
    // base url for the API
    public final static String API_BASE_URL = "https://api.themoviedb.org/3";
    // param name for API key
    public final static String API_KEY_PARAM = "api_key";

    // get the trailer by calling the API
    private void getTrailerKey(Movie movie) {
        // create the url
        String url = API_BASE_URL + "/movie/" + movie.getId() + "/videos";

        // set the request params
        RequestParams params = new RequestParams();
        params.put(API_KEY_PARAM, getString(R.string.api_key)); // API key always required

        // initialize client
        AsyncHttpClient client = new AsyncHttpClient();

        // execute GET request expecting a json object response
        client.get(url, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // load the results for the movie
                try {
                    JSONArray results = response.getJSONArray("results");

                    // selecting the first object
                    JSONObject first = results.getJSONObject(0);

                    String key = first.getString("key");

                    videoId = key;

                } catch (JSONException e) {
                    Log.e("MovieTrailerActivity", "Error finding the key");
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.e("MovieTrailerActivity", "Error requesting movie video");
            }
        });
    }


}
