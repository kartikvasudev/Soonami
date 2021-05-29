package com.learning.Soonami;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONException;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView lv_earthquakes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lv_earthquakes = (ListView) findViewById(R.id.lv_earthquake);
        CustomListViewAdapter adapter = null;
        try {
            adapter = new CustomListViewAdapter(this , QueryUtils.extractEarthquakes());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if(adapter!=null)
        lv_earthquakes.setAdapter(adapter);
        final CustomListViewAdapter finalAdapter = adapter;
        lv_earthquakes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String url = finalAdapter.getItem(i).getUrl();
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });
    }
}