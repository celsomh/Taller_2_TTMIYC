package com.android.ttmiyc.taller_2_ttmiyc;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> listDatos;
    RecyclerView recycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new RequestAsyncTask().execute();
    }



    class RequestAsyncTask extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... voids) {
            String resul;
            try {
                resul = MyGETRequest();
                return resul;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String string) {
            super.onPostExecute(string);
            ListView lView=findViewById(R.id.lvMain);
            String[] from={"title","body"};
            int[] to={R.id.id_title,R.id.id_body};
            ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();
            HashMap<String, String> hashmap;

            try{
                JSONArray jArray = new JSONArray(string);
                for (int i = 0; i < jArray.length(); i++) {
                    JSONObject friend = jArray.getJSONObject(i);

                    String title=friend.getString("title");
                    String body=friend.getString("body");

                    hashmap = new HashMap<>();
                    hashmap.put("title",title);
                    hashmap.put("body",body);

                    arrayList.add(hashmap);
                }

                SimpleAdapter adapter = new SimpleAdapter(MainActivity.this, arrayList, R.layout.item_list, from, to);
                lView.setAdapter(adapter);
            }catch(JSONException e){

                e.printStackTrace();
            }

        }

    }

    public String MyGETRequest() throws IOException {
        URL urlForGetRequest = new URL("https://jsonplaceholder.typicode.com/posts");

        String readLine = null;

        HttpURLConnection conection = (HttpURLConnection) urlForGetRequest.openConnection();
        conection.setRequestProperty("charset","utf-8");
        conection.setRequestMethod("GET");
        conection.connect();

        InputStream inputStream=conection.getInputStream();
        StringBuffer buffer=new StringBuffer();

        BufferedReader reader=new BufferedReader(new InputStreamReader(inputStream));

        while ((readLine=reader.readLine()) != null){
            buffer.append(readLine);
        }

        String resultJSON=buffer.toString();

        return resultJSON;
    }

}
