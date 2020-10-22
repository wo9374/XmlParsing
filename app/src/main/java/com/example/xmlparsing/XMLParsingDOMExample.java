package com.example.xmlparsing;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class XMLParsingDOMExample  extends AppCompatActivity {
    TextView textview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textview = (TextView) findViewById(R.id.xml_txt);

        GetXMLTask task = new GetXMLTask();
        task.execute((String) null);
    }

    private class GetXMLTask extends AsyncTask<String, Void, Document>{

        //웹사이트에 연결하기위해서 url 클래스를 적용
        URL url;

        //연결할 사이트 주소 선택
        String uri = "http://rss.hankyung.com/new/news_column.xml";

        String tagname="", title="", description="";
        //제대로 데이터가 읽어졌는지를 판단해주는 변수

        boolean flag=false;

        @Override
        protected Document doInBackground(String... urls) {
            try {
                //안드로이드에서 xml문서를 읽고 파싱하는 객체를 선언
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                //네임스페이스 사용여부
                factory.setNamespaceAware(true);
                //실제 sax형태로 데이터를 파싱하는 객체 선언
                XmlPullParser xpp = factory.newPullParser();
                //웹사이트에 접속
                url = new URL(uri);
                //웹사이트를 통해서 읽어드린 xml문서를 안드로이드에 저장
                InputStream in = url.openStream();
                //xml문서를 일고 파싱하는 객체에 넘겨줌
                xpp.setInput(in,"UTF-8"); //xml문서의 인코딩 정확히 지정


                //item 태그를 안이라면
                boolean isInItemTag = false;

                //이벤트 타입을 얻어옴
                int eventType = xpp.getEventType();

                //문서의 끝까지 읽어 드리면서 title과 descripton을 추출해냄
                while(eventType != XmlPullParser.END_DOCUMENT){

                    if(eventType==XmlPullParser.START_TAG){
                        //태그명을 읽어드림
                        tagname = xpp.getName();

                        if(tagname.equals("item")){
                            isInItemTag = true;
                        }

                    }else if(eventType==XmlPullParser.TEXT){
                        //태그명이 title이거나 또는 description일때 읽어옴

                        if(tagname.equals("title")&&isInItemTag){
                            title += xpp.getText(); //text에 해당하는 모든 텍스트를 읽어드림 ( += )
                        }else if(tagname.equals("description")&&isInItemTag){
                            description += xpp.getText();
                        }
                    }else if(eventType==XmlPullParser.END_TAG){
                        //태그명을 읽어드림
                        tagname = xpp.getName();

                        //endtag일경우에만 벡터에 저장
                        if(tagname.equals("item")){
                            //벡터에 저장
                            String result ="";

                            result = title + "\n \n \n" + description;
                            textview.setText(result);

                            //변수 초기화
                            title="";
                            description="";

                            isInItemTag = false;

                        }//if-------

                    }//if----------
                    //다음 이벤트 타입을 저장
                    eventType = xpp.next();
                }//while---------

                //모든 데이터가 저장되었다면.
                flag=true; //true : 지정된 xml파일을 읽고 필요한 데이터를 추출해서 저장 완료된 상태


            }catch (Exception e){
                Log.e("doInBackground 에러", "ERROR", e);
            }

            return null;
        }

        @Override
        protected void onPostExecute(Document doc) {
            super.onPostExecute(doc);

        }
    }
}