package com.example.displayapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class SelectActivity extends AppCompatActivity {
    private ArrayList<String> itemsID;
    private ArrayList<String> items;
    private ArrayAdapter<String> adapter;
    public ListView listView;
    private static final String URL_SELECT = "http://" + "192.168.1.33"  + "/invent/select_air.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);

        listView = findViewById(R.id.listSelect);
        items = new ArrayList<>();
        itemsID = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
        listView.setAdapter(adapter);
        new SelectActivity.callSelect().execute();

    }
    public class callSelect extends AsyncTask<String,Void,String> {

        @Override
        protected String doInBackground(String... strings) {

            try {
                OkHttpClient client = new OkHttpClient();
                RequestBody postData = new FormEncodingBuilder()
                        .build();
                Request request = new Request.Builder()
                        .url(URL_SELECT)
                        .post(postData).build();
                Response reponse = null;
                reponse = client.newCall(request).execute();
                String result = reponse.body().string();
                return result;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONObject jsonResult = new JSONObject(s);
                JSONObject object = new JSONObject();
                for(int i = 0; i < jsonResult.length()-1; i++){
                    object = jsonResult.getJSONObject(String.valueOf(i));

                    String id = object.get("inv_id").toString();
                    String name = object.get("inv_name").toString();;
                    String quan = object.get("inv_quan").toString();;
                    String price = object.get("inv_price").toString();;
                    String resp = "id="+id+" ,"+name+" ,จำนวน= "+quan+ " ,ราคา="+price;

                    items.add(resp);
                    itemsID.add(id);
                }
                adapter.notifyDataSetChanged();
            }catch(JSONException e){
                e.printStackTrace();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

}