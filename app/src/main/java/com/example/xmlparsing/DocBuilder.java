package com.example.xmlparsing;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

public class DocBuilder extends AppCompatActivity {
    Button builder_btn;

    String uri = "http://hsuxm.uplus.co.kr:80/videolte/v1/menu?sa_id=500199471544&stb_mac=v001.9947.1544&parent_cat=M048100&os_type=A";
    org.w3c.dom.Document doc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc_builder);
        builder_btn = (Button) findViewById(R.id.builder_btn);

    }

    public void onClick(View view){
        new GetXMLTask().execute("http://hsuxm.uplus.co.kr:80/videolte/v1/menu?sa_id=500199471544&stb_mac=v001.9947.1544&parent_cat=M048100&os_type=A");
    }

    private class GetXMLTask extends AsyncTask<String, Void, Document> {
        @Override
        protected Document doInBackground(String... urls) {
            URL url;
            try {
                url = new URL(urls[0]);
                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                DocumentBuilder db = dbf.newDocumentBuilder();
                doc = db.parse(new InputSource(url.openStream()));
                doc.getDocumentElement().normalize();

                System.out.println(" ddddddddddddddddddddddd  "+doc);

            } catch (Exception e) {
                //Toast.makeText(getBaseContext(), "Parsing Error", Toast.LENGTH_SHORT).show();
            }
            return doc;
        }

        @Override
        protected void onPostExecute(Document doc) {


            String s = "";
            NodeList nodeList = doc.getElementsByTagName("cat");

            for(int i = 0; i< nodeList.getLength(); i++){

                Node node = nodeList.item(i);
                Element fstElmnt = (Element) node;

                NodeList idx = fstElmnt.getElementsByTagName("cat_id");
                s += "cat_id = "+  idx.item(0).getChildNodes().item(0).getNodeValue() +"\n";

                NodeList gugun = fstElmnt.getElementsByTagName("cat_nm");
                s += "cat_nm = "+  gugun.item(0).getChildNodes().item(0).getNodeValue() +"\n";
            }

            System.out.println(s);

            super.onPostExecute(doc);
        }
    }
}