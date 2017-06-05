package com.mal.walid.moviewalid.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import  android.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.mal.walid.moviewalid.JsonParsing.BoxOfficeMovieResponse;
import com.mal.walid.moviewalid.Adapter.GridViewAdabter;
import com.mal.walid.moviewalid.JsonParsing.JsonParsing;
import com.mal.walid.moviewalid.MainActivity;
import com.mal.walid.moviewalid.MovieDetailes;
import com.mal.walid.moviewalid.R;
import com.mal.walid.moviewalid.model.MovieObj;

import java.util.ArrayList;

public class MainActivityFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{
    public static ArrayList<MovieObj> MovieArray = new ArrayList<MovieObj>();
    public static SwipeRefreshLayout swipeRefreshLayout;
    static GridView grid;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_main_fragment,container,false);
    }
    public static GridViewAdabter adapter;
    static View view;
    static Context mContext;
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        SharedPreferences getData = PreferenceManager.getDefaultSharedPreferences(getActivity());
        final String sortedBy = getData.getString("sortedby", "popular");
        view =getView();
        mContext=getActivity();
        makeAdabter();
        swipeRefreshLayout = (SwipeRefreshLayout) getActivity().findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.post(new Runnable() {
         @Override
            public void run() {
            swipeRefreshLayout.setRefreshing(true);
             MainActivityFragment.updateAdabter();}
            }
        );
        grid = (GridView) view.findViewById(R.id.MainGrid);
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if(MainActivity.fragNum ==1) {
                Intent detail = new Intent(getActivity(), MovieDetailes.class);
                Bundle be = new Bundle();
                MovieObj obj = MovieArray.get(position);
                be.putSerializable("Movie", obj);
                if (MainActivity.realm.where(MovieObj.class).equalTo("Movie_id", obj.getMovie_id()).count() > 0) {
                    detail.putExtra("MovieID",obj.getMovie_id());
                }
                detail.putExtras(be);
                startActivity(detail);
            }
            }
        });

    }

    public static void makeAdabter(){
         grid = (GridView) view.findViewById(R.id.MainGrid);
        adapter = new GridViewAdabter(mContext, R.layout.movie_layout, MovieArray);
        grid.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        updateAdabter();
    }

    public static void updateAdabter() {
        swipeRefreshLayout.setRefreshing(true);
        BoxOfficeMovieResponse boxOfficeMovieResponse = new BoxOfficeMovieResponse();
        JsonParsing jsonParsing = new JsonParsing(mContext,view);
        jsonParsing.updateMovieData();
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onRefresh() {
        MainActivityFragment.updateAdabter();
    }
}
