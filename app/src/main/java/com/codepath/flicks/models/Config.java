package com.codepath.flicks.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Config {

    // base url for loading images
    String imageBaseUrl;
    // poster size for fetching images, as part of url
    String posterSize;
    // the backdrop size to use when fetching images
    String backdropSize;

    public Config(JSONObject object) throws JSONException {
        JSONObject images = object.getJSONObject("images");
        // get the image base url
        imageBaseUrl = images.getString("secure_base_url");

        // get the poster size
        JSONArray posterSizeOptions = images.getJSONArray("poster_sizes");
        // use the option at index 3 or w342 as fallback
        posterSize = posterSizeOptions.optString(3, "w342");

        // parse the backdrop sizes and use the option at index 1 or w700 as a fallback
        JSONArray backdropSizeOptions = images.getJSONArray("backdrop_sizes");
        backdropSize = backdropSizeOptions.optString(1, "w700");

    }

    // helper method for creating urls
    public String getImageUrl(String size, String path) {
        return String.format("%s%s%s", imageBaseUrl, size, path);
    }

    public String getImageBaseUrl() {
        return imageBaseUrl;
    }

    public String getPosterSize() {
        return posterSize;
    }

    public String getBackdropSize() {
        return backdropSize;
    }
}
