package com.mirra.bmp4;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class EventInfoActivity extends AppCompatActivity
        implements DialogInterface.OnClickListener,
        View.OnClickListener {

    TextView tv_name, tv_comment, tv_date;
    Button btn_ok;
    CheckBox cb_solved;
    UserEvent event;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        event = (UserEvent) getIntent().getSerializableExtra("event");
        setTitle(event.name);
        setContentView(R.layout.activity_event_info);
        tv_name = findViewById(R.id.tv_einfo_name);
        tv_comment = findViewById(R.id.tv_einfo_comment);
        tv_date = findViewById(R.id.tv_einfo_date);
        btn_ok = findViewById(R.id.btn_einfo_ok);
        btn_ok.setOnClickListener(this);
        cb_solved = findViewById(R.id.cb_einfo_solved);
        tv_name.setText(event.name);
        tv_comment.setText(event.Comment);
        tv_date.setText(event.day + "." + event.month + "." + event.year);
        cb_solved.setChecked(event.isFinished);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_event_info, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.item_del_event)
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Точно удалить?");
            builder.setPositiveButton("Супер точно", this);
            builder.setNegativeButton("Не точно", this);
            builder.create().show();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        if (which == DialogInterface.BUTTON_POSITIVE)
        {
            Intent intent = new Intent();
            intent.putExtra("delete", true);
            intent.putExtra("id", event.id);
            setResult(RESULT_OK, intent);
            finish();
        }
    }

    @Override
    public void onClick(View v) {
        if (v == btn_ok)
        {
            event.isFinished = cb_solved.isChecked();
            Intent intent = new Intent();
            intent.putExtra("event", event);
            setResult(RESULT_OK, intent);
            finish();
        }
    }
}
