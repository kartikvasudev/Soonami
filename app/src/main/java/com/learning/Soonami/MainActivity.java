package com.learning.Soonami;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements androidx.loader.app.LoaderManager.LoaderCallbacks<ArrayList<Earthquakes>> {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String USGS_REQUEST_URL =
            "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&starttime=2014-01-01&endtime=2014-01-02";

    private ListView lv_earthquakes;
    private TextView tv_empty_view;
    private ProgressBar pb;
    private CustomListViewAdapter adapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       /*Initialize all views*/
        lv_earthquakes = (ListView) findViewById(R.id.lv_earthquake);
        tv_empty_view = (TextView) findViewById(R.id.tv_empty_view) ;
        pb = (ProgressBar) findViewById(R.id.pb);

        /*Check Internet Connectivity*/
        ConnectivityManager cm =
                (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        if(isConnected)
        {
            LoaderManager loaderManager = getSupportLoaderManager();
            loaderManager.initLoader(0,null, this);
        }
        else {
            pb.setVisibility(View.GONE);
            tv_empty_view.setVisibility(View.VISIBLE);
            tv_empty_view.setText(R.string.no_internet);
        }

        adapter = new CustomListViewAdapter(this,new ArrayList<Earthquakes>());
        lv_earthquakes.setAdapter(adapter);

        lv_earthquakes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Earthquakes earthquake = (Earthquakes) adapterView.getAdapter().getItem(i);
                String url = earthquake.getUrl();
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });

    }

    private void updateUi(ArrayList<Earthquakes> earthquakes)
    {
        pb.setVisibility(View.GONE);
        if(earthquakes!=null)
        {
            lv_earthquakes.setVisibility(View.VISIBLE);
            adapter = new CustomListViewAdapter(this ,earthquakes);
            lv_earthquakes.setAdapter(adapter);
        }
        else
        {
            tv_empty_view.setText(R.string.no_earthquakes_empty_view);
            tv_empty_view.setVisibility(View.VISIBLE);
        }
    }

    @NonNull
    @Override
    public Loader<ArrayList<Earthquakes>> onCreateLoader(int id, @Nullable Bundle args) {
        return new EarthquakesDataLoader(this,USGS_REQUEST_URL);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<ArrayList<Earthquakes>> loader, ArrayList<Earthquakes> data) {
        updateUi(data);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<ArrayList<Earthquakes>> loader) {
        adapter = new CustomListViewAdapter(this,new ArrayList<Earthquakes>());
    }
}