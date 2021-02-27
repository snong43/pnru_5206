package com.example.pnru;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MenuSelectActivity extends AppCompatActivity {
    public Button btnLinkInsert ;
    public Intent intent;
    private static final String USER_QUERY_PHP = "http://" + UrlConstantPHP.MY_LOCAL  + "/testmysql/queryInvData.php";
   // private ArrayList<String> itemsID;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> items;
    public ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_select);
        btnLinkInsert = (Button)findViewById(R.id.btn_show_insert);
        listView = findViewById(R.id.list_view_show);
        items = new ArrayList<>();
   //     itemsID = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
        listView.setAdapter(adapter);
        new MenuSelectActivity.CallSelectPHP().execute();


        btnLinkInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(v.getContext(), MenuInsertActivity.class);
                startActivity(intent);
            }
        });

    }

    public class CallSelectPHP extends AsyncTask<String,Void,String> {
        @Override
        protected String doInBackground(String... params) {
            try {
                OkHttpClient client = new OkHttpClient();
                RequestBody postData = new FormEncodingBuilder()
                        // .add(null)
                        //.add("id","5")
                        .build();
                Request request = new Request.Builder()
                        //ข้างหน้าใส่ ip ของเรา
                        .url(USER_QUERY_PHP)
                        .post(postData).build();
                Response reponse = client.newCall(request).execute();
                String result = reponse.body().string();
                return result;

            } catch (Exception e) {
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
                //    itemsID.add(id);
                }
                adapter.notifyDataSetChanged();
                //     Toast.makeText(getApplicationContext(),"เพิ่มข้อมูลเรียบร้อย",Toast.LENGTH_LONG).show();
            }catch(JSONException e){
                e.printStackTrace();
            }catch (Exception e){
                e.printStackTrace();
            }
        }



        ////////////////////////////////////
    }


}

