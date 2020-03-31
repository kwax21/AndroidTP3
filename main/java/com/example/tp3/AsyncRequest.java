package com.example.tp3;

import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class AsyncRequest extends AsyncTask<Team, Void, Team> {

    private TeamActivity activity;
    private Team team;

    protected AsyncRequest(TeamActivity activity, Team team) {
        this.activity = activity;
        this.team = team;
    }

    @Override
    protected Team doInBackground(Team... team) {
        WebServiceUrl web = new WebServiceUrl();
        HttpURLConnection urls = null;
        JSONResponseHandlerTeam json = new JSONResponseHandlerTeam(this.team);
        URL url;

        try {
            url = web.buildSearchTeam(this.team.getName());
            urls = (HttpURLConnection) url.openConnection();
            InputStream in = urls.getInputStream();
            json.readJsonStream(in);
            return this.team;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            urls.disconnect();
        }
        return this.team;
    }
}