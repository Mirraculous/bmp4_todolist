package com.mirra.bmp4;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ArrayList<UserEvent> events;
    ArrayList<TextView> tvs;
    LinearLayout svLayout;
    LinearLayout.LayoutParams lp;
    int lastId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Задачи");
        events = new ArrayList<>();
        tvs = new ArrayList<>();
        lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 200);
        lp.setMargins(0, 5, 0, 5);
        setContentView(R.layout.activity_main);
        svLayout = findViewById(R.id.svLayout); //?
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.itemAddEvent)
        {
            Intent intent = new Intent(this, AddEventActivity.class);
            startActivityForResult(intent, 1);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (data == null || resultCode != RESULT_OK)
            return;
        switch (requestCode) {
            case 1:
                AddEvent(data);
                break;
            case 2:
                onEInfoResult(data);
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    void onEInfoResult(Intent data)
    {
        Boolean del = data.getBooleanExtra("delete", false);
        if (del)
        {
            int id = data.getIntExtra("id", -1);
            if (id == -1)
                return;
            int i;
            for (i = 0; i < events.size(); i++)
                if (events.get(i).id == id)
                    break;
            if (i >= events.size())
                return;
            events.remove(i);
            svLayout.removeView(tvs.get(i));
            tvs.remove(i);
        }
        else {
            UserEvent event = (UserEvent) data.getSerializableExtra("event");
            int id = event.id;
            int i;
            for (i = 0; i < events.size(); i++)
                if (events.get(i).id == id)
                    break;
            if (i >= events.size())
                return;
            events.set(i, event);
            tvs.get(i).setTextColor(event.isFinished ?
                    getResources().getColor(R.color.colorSolved) : Color.BLACK);
        }
    }

    void AddEvent(Intent data)
    {
        UserEvent event = (UserEvent) data.getSerializableExtra("event");
        event.id = lastId + 1;
        lastId++;
        events.add(event);
        TextView tv = new TextView(this);
        tv.setLayoutParams(lp);
        tv.setTextColor(event.isFinished ?
                getResources().getColor(R.color.colorSolved) : Color.BLACK);
        tv.setTypeface(null, Typeface.BOLD);
        tv.setText(event.name);
        tv.setGravity(Gravity.CENTER_VERTICAL);
        tv.setOnClickListener(this);
        tv.setBackgroundColor(Color.parseColor("#EEEEEE"));
        svLayout.addView(tv);
        tv.setPadding(20, 0, 0, 0);
        tvs.add(tv);
    }

    @Override
    public void onClick(View v) {
        int ind = tvs.indexOf(v);
        if (ind != -1 )
        {
            Intent intent = new Intent(this, EventInfoActivity.class);
            intent.putExtra("event", events.get(ind));
            startActivityForResult(intent, 2);
        }
    }
}
