package com.codepath.flicks.models;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

@Parcel // indicating class is Parcelable
public class Movie {

    // values from API - public for parceler
    String title;
    String overview;
    String posterPath; // only the path - not full url
    String backdropPath; // only the path
    Double voteAverage;
    Integer id; // movie id

    // no argument, empty constructor required for parceler
    public Movie() {}

    // initialize from JSON data
    public Movie(JSONObject object) throws JSONException {
        title = object.getString("title");
        overview = object.getString("overview");
        posterPath = object.getString("poster_path");
        backdropPath = object.getString("backdrop_path");
        voteAverage = object.getDouble("vote_average");
        id = object.getInt("id");
    }

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }

    public Integer getId() {
        return id;
    }


}
