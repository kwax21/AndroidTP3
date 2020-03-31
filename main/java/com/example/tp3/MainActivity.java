package com.example.tp3;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Toolbar toolbar = findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NewTeamActivity.class);
                startActivity(intent);
            }
        });

        final ListView lists = findViewById(R.id.liste);
        final SportDbHelper spt;
        spt = new SportDbHelper(lists.getContext());
        spt.populate();

        SimpleCursorAdapter csr = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_2, spt.fetchAllTeams(),
                new String[]{SportDbHelper.COLUMN_TEAM_NAME, SportDbHelper.COLUMN_LEAGUE_NAME},
                new int[]{android.R.id.text1, android.R.id.text2});
        lists.setAdapter(csr);
        lists.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Cursor currentCursor = (Cursor) adapterView.getItemAtPosition(i);
                Intent intent = new Intent(MainActivity.this, TeamActivity.class);
                intent.putExtra("team", spt.cursorToTeam(currentCursor));
                startActivity(intent);
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
