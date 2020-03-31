package com.example.tp3;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class TeamActivity extends AppCompatActivity {
    private TeamActivity activity = this;
    private static final String TAG = TeamActivity.class.getSimpleName();
    private TextView textTeamName, textLeague, textManager, textStadium, textStadiumLocation, textTotalScore, textRanking, textLastMatch, textLastUpdate;


    private int totalPoints;
    private int ranking;
    private Match lastEvent;
    private String lastUpdate;

    private ImageView imageBadge;
    private Team team;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team);

        team = (Team) getIntent().getExtras().getParcelable("team");

        textTeamName = (TextView) findViewById(R.id.nameTeam);
        textLeague = (TextView) findViewById(R.id.league);
        textStadium = (TextView) findViewById(R.id.editStadium);
        textStadiumLocation = (TextView) findViewById(R.id.editStadiumLocation);
        textTotalScore = (TextView) findViewById(R.id.editTotalScore);
        textRanking = (TextView) findViewById(R.id.editRanking);
        textLastMatch = (TextView) findViewById(R.id.editLastMatch);
        textLastUpdate = (TextView) findViewById(R.id.editLastUpdate);

        imageBadge = (ImageView) findViewById(R.id.imageView);

        updateView();

        final Button but = (Button) findViewById(R.id.button);

        but.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                AsyncRequest async = new AsyncRequest(activity, team);
                async.execute(team);
            }
        });

    }

    @Override
    public void onBackPressed() {
        //TODO : prepare result for the main activity
        super.onBackPressed();
    }

    protected void updateView() {

        textTeamName.setText(team.getName());
        textLeague.setText(team.getLeague());
        textStadium.setText(team.getStadium());
        textStadiumLocation.setText(team.getStadiumLocation());
        textTotalScore.setText(Integer.toString(team.getTotalPoints()));
        textRanking.setText(Integer.toString(team.getRanking()));
        textLastMatch.setText(team.getLastEvent().toString());
        textLastUpdate.setText(team.getLastUpdate());

	//TODO : update imageBadge
    }

}
