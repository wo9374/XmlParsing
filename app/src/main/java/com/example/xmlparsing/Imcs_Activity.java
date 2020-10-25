package com.example.xmlparsing;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static okhttp3.RequestBody.create;

public class Imcs_Activity extends AppCompatActivity {

    String url = "http://iptvimcs5.uplus.co.kr:80/servlets/CommSvl?CMD=getNSKidsHome&PARAM=SA_ID%3D500199471544%7CSTB_MAC%3Dv001.9947.1544%7CREQUEST_FLAG%3D10%7CLAST_USE_MENU%3DDL6MK%3BDL669%7C";
    Response response = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imcs);

       new Thread() {
           public void run() {
               OkHttpClient client = new OkHttpClient();

               //MediaType TEXT = MediaType.parse("application/json; charset=utf-8");
               //RequestBody body = create(TEXT, "application/json");

               Request request = new Request.Builder()
                       /*.addHeader("Accept", "application/json")
                       .addHeader("Content-type", "application/json")
                       .addHeader("return-type", "application/json")*/

                       //.addHeader("Accept", "*/*;")
                       //.addHeader("Content-type", "application/text")
                       //.addHeader("return-type", "application/text")

                       /*.addHeader("Accept", "application/json")
                       .addHeader("Content-type", "application/json")
                       .post(body)*/
                       .url(url)
                       .build();

               InputStream myResponse;

               try {
                   response = client.newCall(request).execute();
                   myResponse = response.body().byteStream();
                   String serverData = inputStreamToString(myResponse);

                   Log.d("TAG", "서버 데이터 : \n"+serverData);
               } catch (IOException e) {
                   e.printStackTrace();
               }
           }
       }.start();

    }

    private String inputStreamToString(InputStream is) {
        String data = null;

        BufferedReader br;
        try {
            //TODO EUR-KR 인코딩 시도 해보라 하셨음
            br = new BufferedReader(new InputStreamReader(is, "EUC-KR"));

            StringBuilder url_content = new StringBuilder();
            while ((data = br.readLine()) != null) {
                url_content.append(data + "\n");
            }
            data = url_content.toString();

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            Log.e("TAG","InputStreamToString exception = " + e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("TAG","InputStreamToString exception = " + e.getMessage());
        }

        return data;
    }
}
