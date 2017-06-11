package com.mal.walid.moviewalid.SqlLight;

import android.provider.BaseColumns;

/**
 * Created by Khaled on 2017-06-06.
 */

public class MovieDataContract {


    public static class MovieDataEntity implements BaseColumns {

        final static String TABLE_NAME = "MovieData";
        final static String Movie_Name = "MovieName";
        final static String Movie_ID = _ID;
        final static String Img_poster = "MoviePoster";
        final static String Overview_text = "MovieOverview";
        final static String publish_time = "MoviePublishDate";
        final static String image_film = "MovieImg";
        final static String Movie_rate = "MovieRate";




    }
}
