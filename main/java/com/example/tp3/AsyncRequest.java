package com.example.tp3;

import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class AsyncRequest extends AsyncTask<Team, Void, Team> {

    TeamActivity activity;
    Team team;

    protected AsyncRequest(TeamActivity activity, Team team) {
        this.activity = activity;
        this.team = team;
    }

    @Override
    protected Team doInBackground(final Team... team) {
        WebServiceUrl web = new WebServiceUrl();
        HttpURLConnection urls = null;
        JSONResponseHandlerTeam json = new JSONResponseHandlerTeam(this.team);
        JSONResponseHandlerRanks rank = new JSONResponseHandlerRanks(this.team);
        URL url;

        try {
            url = web.buildSearchTeam(this.team.getName());
            urls = (HttpURLConnection) url.openConnection();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        try {
            urls.setRequestMethod("GET");
        } catch (ProtocolException e) {
            e.printStackTrace();
        }

        InputStream in = null;
        try {
            in = urls.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            json.readJsonStream(in);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        try {
            rank.readJsonStream(in);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            urls.disconnect();
        }



        if(activity != null)
        {
            new SportDbHelper(activity).updateTeam(this.team);
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    activity.updateView();
                }
            });
        }
        return this.team;
    }
}