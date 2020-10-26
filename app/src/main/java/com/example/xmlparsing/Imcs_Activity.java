package com.example.xmlparsing;

import android.os.Bundle;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static okhttp3.RequestBody.create;

public class Imcs_Activity extends AppCompatActivity {

    String url = "http://iptvimcs5.uplus.co.kr:80/servlets/CommSvl?CMD=getNSKidsHome&PARAM=SA_ID%3D500199471544%7CSTB_MAC%3Dv001.9947.1544%7CREQUEST_FLAG%3D10%7CLAST_USE_MENU%3DDL6MK%3BDL669%7C";
    Response response = null;

    String id = "";
    String name = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imcs);

       new Thread() {
           public void run() {
               OkHttpClient client = new OkHttpClient();

               Request request = new Request.Builder()
                       .addHeader("Accept", "*/*;")
                       .addHeader("Content-type", "application/text")
                       .url(url)
                       .build();

               InputStream myResponse;

               try {
                   response = client.newCall(request).execute();
                   myResponse = response.body().byteStream();
                   String serverData = inputStreamToString(myResponse);
                   Log.d("TAG", "서버 데이터 : \n"+serverData);

                  /* JSONObject jsonObject = new JSONObject(serverData); //서버 데이터 스트링 json을 object로 변환
                   JSONObject jsonObject1 = jsonObject.getJSONObject("result"); //result 부분 찾기
                   JSONArray jsonArray = jsonObject1.getJSONArray("recordset"); //list 부분 찾기

                   for (int i=0; i < jsonArray.length() -1; i++){
                       id += jsonArray.getJSONObject(i).get("id") + "\t";
                       name += jsonArray.getJSONObject(i).get("name") + "\t";
                   }

                   System.out.println(id);
                   System.out.println(name);*/
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
            //TODO EUC-KR 인코딩 시도 해보라 하셨음
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
