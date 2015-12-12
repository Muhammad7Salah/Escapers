package com.example.ziko_.escapers;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ExpandableListView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BoomEvents extends AppCompatActivity {


    ExpandAdapter adapter;
    ExpandableListView expandlist;
    List<String> events;
    HashMap<String,String> eventDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boom_events);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        expandlist=(ExpandableListView)findViewById(R.id.exp_boom_list);

        prepareListData();

        adapter = new ExpandAdapter(this,events,eventDetail);
        expandlist.setAdapter(adapter);

    }

    private void prepareListData() {

        events = new ArrayList<String>();
        eventDetail = new HashMap<String,String>();

        events.add("Cairo Runners");
        events.add("Giza Runners");

        List<String> children = new ArrayList<String>();
        children.add("Cairo Cairo");
        children.add("Giza");

        for(int i=0;i<events.size();i++)
            eventDetail.put(events.get(i),children.get(i));


    }


}
