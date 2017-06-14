package com.mal.walid.moviewalid.Fragment;

import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.mal.walid.moviewalid.MainActivity;
import com.mal.walid.moviewalid.R;
import com.mal.walid.moviewalid.ReviewActivity;
import com.mal.walid.moviewalid.SqlLight.SQLite;
import com.mal.walid.moviewalid.TraillersActivity;
import com.mal.walid.moviewalid.model.MovieObj;
import com.squareup.picasso.Picasso;

/**
 * Created by walid4444 on 9/16/16.
 */
public class DetailesFragment extends Fragment {

    int MovieID;
    MovieObj obj;
    String sortedBy;
    Bundle bundle;



    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        bundle =  getArguments();
        SharedPreferences getData = PreferenceManager.getDefaultSharedPreferences(getActivity());
        sortedBy = getData.getString("sortedby", "popular");
        obj = (MovieObj) bundle.getSerializable("Movie");

        if (sortedBy.equals("favourite") || obj == null || getActivity().getIntent().getIntExtra("testID",0) !=0) {
            //MovieID=getActivity().getIntent().getIntExtra("MovieID",0);
            //obj = MainActivity.realm.where(MovieObj.class).equalTo("Movie_id", MovieID).findFirst();
        }


            Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
            toolbar.setTitle(obj.getMovie_name());

        MovieID = obj.getMovie_id();

        final FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        if( MainActivity.realm.where(MovieObj.class).equalTo("Movie_id",MovieID).count() >0)
            fab.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorSaved)));

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (MainActivity.realm.where(MovieObj.class).equalTo("Movie_id", obj.getMovie_id()).count() == 0) {
                    SQLite SQLite = new SQLite(getActivity());
                    SQLite.insert(obj.getMovie_name().toString(),obj.getMovie_id(),obj.getMovie_rate(),
                            obj.getOverview_text().toString(),obj.getImg_poster().toString(),
                            obj.getImage_film().toString(),obj.getPublish_time().toString());
                    Snackbar.make(view, "added to favourite", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
        });

        if ( MainActivity.realm.where(MovieObj.class).equalTo("Movie_id", obj.getMovie_id()).count() > 0){
            fab.setImageDrawable(getResources().getDrawable(R.drawable.star_filled));
        }

        TextView Overview = (TextView) getActivity().findViewById(R.id.MovieOverview);
        Picasso.with(getActivity()).load("http://image.tmdb.org/t/p/w185/" + obj.getImg_poster()).into((ImageView) getView().findViewById(R.id.bar_image));
        Overview.setText( obj.getOverview_text());
        ImageView[] RatingStars = new ImageView[5];
        RatingStars[0] = (ImageView) getView().findViewById(R.id.star1);
        RatingStars[1] = (ImageView) getView().findViewById(R.id.star2);
        RatingStars[2] = (ImageView) getView().findViewById(R.id.star3);
        RatingStars[3] = (ImageView) getView().findViewById(R.id.star4);
        RatingStars[4] = (ImageView) getView().findViewById(R.id.star5);
        makeRatingStars(RatingStars,obj.getMovie_rate());

        TextView realase_date = (TextView) getView().findViewById(R.id.realase_date);
        realase_date.setText(obj.getPublish_time());

        Button trailers = (Button)getActivity().findViewById(R.id.trailersButton);
        Button reviews = (Button)getActivity().findViewById(R.id.reviewsButton);

        trailers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bundle.putInt("MovieID", obj.getMovie_id());
                Intent i = new Intent(getActivity(),TraillersActivity.class);
                i.putExtras(bundle);
                startActivity(i);
            }
        });

        reviews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                bundle.putInt("MovieID", obj.getMovie_id());
                Intent i = new Intent(getActivity(),ReviewActivity.class);
                i.putExtras(bundle);
                startActivity(i);
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.movie_detailes_fragment, container, false);
    }


    private void makeRatingStars(ImageView[] RatingStars,float rate){
        float rRate = rate/2;
        int i = (int) rRate;
        int j = (int) rRate;
        float x = rRate-i;
        String uri ;
        int imageResource;
        Drawable res;
        for (; i >= 0 ; i--){
            uri = "@drawable/star_filled";
            imageResource = getResources().getIdentifier(uri, null, getActivity().getPackageName());
            res = getResources().getDrawable(imageResource);
            RatingStars[i].setImageDrawable(res);
        }
        if(x >= 0.5 && j < 5){
            uri = "@drawable/star_half";
            imageResource = getResources().getIdentifier(uri, null,  getActivity().getPackageName());
            res = getResources().getDrawable(imageResource);
            RatingStars[j].setImageDrawable(res);

        }
    }


}