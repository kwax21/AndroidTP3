package com.example.tp3;

import android.util.JsonReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Process the response to a GET request to the Web service
 * https://www.thesportsdb.com/api/v1/json/1/searchteams.php?t=R
 * Responses must be provided in JSON.
 *
 */


public class JSONResponseHandlerRanks {

    private static final String TAG = JSONResponseHandlerTeam.class.getSimpleName();

    private Team team;


    public JSONResponseHandlerRanks(Team team) {
        this.team = team;
    }

    /**
     * @param response done by the Web service
     * @return A Team with attributes filled with the collected information if response was
     * successfully analyzed
     */
    public void readJsonStream(InputStream response) throws IOException {
        JsonReader reader = new JsonReader(new InputStreamReader(response, "UTF-8"));
        try {
            readRanks(reader);
        } finally {
            reader.close();
        }
    }

    public void readRanks(JsonReader reader) throws IOException {
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("teams")) {
                readArrayRanks(reader);
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
    }


    private void readArrayRanks(JsonReader reader) throws IOException {
        reader.beginArray();
        int nb = 0; // only consider the first element of the array
        while (reader.hasNext() ) {
            reader.beginObject();
            while (reader.hasNext()) {
                String name = reader.nextName();
                if (nb==0) {
                    if (name.equals("teamid")) {
                        team.setRanking(reader.nextInt());
                    } else if (name.equals("total")) {
                        team.setTotalPoints(reader.nextInt());
                    } else {
                        reader.skipValue();
                    }
                }  else {
                    reader.skipValue();
                }
            }
            reader.endObject();
            nb++;
        }
        reader.endArray();
    }
}
