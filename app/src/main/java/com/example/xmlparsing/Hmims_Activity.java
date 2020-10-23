package com.example.xmlparsing;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Hmims_Activity extends AppCompatActivity {

    String url = "http://hsuxm.uplus.co.kr:80/videolte/v1/menu?sa_id=500199471544&stb_mac=v001.9947.1544&parent_cat=M048100&os_type=A";
    Response response = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hmims);

        new Thread() {
            public void run() {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .addHeader("Accept", "application/json")
                        .addHeader("Content-type", "application/json")
                        .addHeader("return-type", "application/json")
                        .url(url)
                        .build();
                InputStream myResponse;

                try {
                    response = client.newCall(request).execute();
                    myResponse = response.body().byteStream();
                    String serverData = inputStreamToString(myResponse);
                    Log.d("TAG", "서버 데이터 \n"+serverData);

                    JSONObject jsonObject = new JSONObject(serverData);
                    JSONObject jsonObject1 = jsonObject.getJSONObject("result");
                    JSONArray jsonArray = jsonObject1.getJSONArray("list");

                    for (int i=0; i < jsonArray.length() -1; i++){
                        System.out.println(jsonArray.getJSONObject(i).get("cat_id"));
                        //TODO 값은 나옴
                    }

                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            }
        }.start();

    }

    private String inputStreamToString(InputStream is) {
        String data = null;

        BufferedReader br;
        try {
            br = new BufferedReader(new InputStreamReader(is, "UTF-8"));

            StringBuilder url_content = new StringBuilder();
            while ((data = br.readLine()) != null) {
                url_content.append(data + "\n");
            }
            data = url_content.toString();

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            Log.e("TAG", "InputStreamToString exception = " + e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("TAG","InputStreamToString exception = " + e.getMessage());
        }

        return data;
    }
}
