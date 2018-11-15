package com.android.ttmiyc.taller_2_ttmiyc;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ProgressBar pbarProgreso;
    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn=findViewById(R.id.id_btn);
        btn.setOnClickListener(this);
        pbarProgreso=findViewById(R.id.id_pbar_progreso);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.id_btn){
            RequestAsyncTask resAsTa=new RequestAsyncTask();
            resAsTa.execute();
        }
    }

    public String MyGETRequest() throws IOException {

        URL urlForGetRequest = new URL("https://jsonplaceholder.typicode.com/posts/1");

        String readLine = null;

        HttpURLConnection conection = (HttpURLConnection) urlForGetRequest.openConnection();

        conection.setRequestMethod("GET");
        conection.setRequestProperty("userId", "a1bcdef"); // set userId its a sample here
        int responseCode = conection.getResponseCode();

        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(conection.getInputStream()));

            StringBuffer response = new StringBuffer();
            while ((readLine = in .readLine()) != null) {

                response.append(readLine);

            } in .close();

            // print result
            return response.toString();


            //GetAndPost.POSTRequest(response.toString());
        } else {
            System.out.println("GET NOT WORKED");
        }
        return readLine;
    }
    class RequestAsyncTask extends AsyncTask<Void,Void,String> {

        @Override
        protected void onPreExecute() {
            pbarProgreso.setMax(100);
            pbarProgreso.setProgress(0);
        }

        @Override
        protected String doInBackground(Void... voids) {
            String resul="";
            try {
                resul = MyGETRequest();
                return resul;
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(isCancelled())
                return resul;

            return resul;
        }

        @Override
        protected void onProgressUpdate(Void... voids) {

        }

        @Override
        protected void onPostExecute(String string) {
            Toast.makeText(MainActivity.this, string, Toast.LENGTH_LONG).show();
        }

        @Override
        protected void onCancelled() {

        }
    }
}
