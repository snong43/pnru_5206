package com.example.pnru;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MenuInsertActivity extends AppCompatActivity {
    public Button btnInsert;
    public EditText edt_id;
    public EditText edt_name;
    public EditText edt_q;
    public EditText edt_price;
    private ArrayList<String> itemsID;
    private ArrayList<String> items;
    private static final String USER_INSERT_PHP = "http://" + UrlConstantPHP.MY_LOCAL  + "/testmysql/insertInvData.php";
    private static final String USER_QUERY_PHP = "http://" + UrlConstantPHP.MY_LOCAL  + "/testmysql/queryInvData.php";
    private static final String USER_DELETE_PHP = "http://" + UrlConstantPHP.MY_LOCAL  + "/testmysql/delInvData.php";

    private ArrayAdapter<String> adapter;
    public ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_insert);

        btnInsert = (Button)findViewById(R.id.btn_insert_menu);
        edt_id = (EditText)findViewById(R.id.edt_inv_id);
        edt_name = (EditText)findViewById(R.id.edt_inv_name);
        edt_q = (EditText)findViewById(R.id.edt_inv_quan);
        edt_price = (EditText)findViewById(R.id.edt_inv_price);

        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new CallInsertPHP().execute();
            }
        });

        listView = findViewById(R.id.list_view_insert);
        items = new ArrayList<>();
        itemsID = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
        listView.setAdapter(adapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MenuInsertActivity.this);
                builder.setTitle("Do you want to delete");
                builder.setMessage("Are you sure to delete ID." + itemsID.get(position) + "?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        new CallDeletePHP().execute(itemsID.get(position));
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Toast.makeText(getApplicationContext(),
                                "Cancel",Toast.LENGTH_SHORT).show();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });


        new CallSelectPHP().execute();

    }

    public class CallInsertPHP extends AsyncTask<String,Void,String> {
        @Override
        protected String doInBackground(String... params) {
            try {
                String ed_id = edt_id.getText().toString();
                String ed_name = edt_name.getText().toString();
                String ed_qu = edt_q.getText().toString();
                String ed_price = edt_price.getText().toString();
                OkHttpClient client = new OkHttpClient();
                RequestBody postData = new FormEncodingBuilder()
                        .add("ed_id",ed_id)
                        .add("ed_name",ed_name)
                        .add("ed_qu",ed_qu)
                        .add("ed_price",ed_price)
                        .build();
                Request request = new Request.Builder()
                        .url(USER_INSERT_PHP)
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

                if(items != null){
                    items.clear();
                    itemsID.clear();
                    adapter.notifyDataSetChanged();
                }

                if(jsonResult.length() > 0){
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
                    Toast.makeText(getApplicationContext(),"เพิ่มข้อมูลเรียบร้อย",Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();

                }
            }catch(JSONException e){
                Toast.makeText(getApplicationContext(), "ไม่สามารถเพิ่มข้อมูลได้ "+e ,Toast.LENGTH_LONG).show();

            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public class CallSelectPHP extends AsyncTask<String,Void,String> {
        @Override
        protected String doInBackground(String... params) {
            try {
                OkHttpClient client = new OkHttpClient();
                RequestBody postData = new FormEncodingBuilder()
                        .build();
                Request request = new Request.Builder()
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
                    itemsID.add(id);
                }
                adapter.notifyDataSetChanged();
           //     Toast.makeText(getApplicationContext(),"เพิ่มข้อมูลเรียบร้อย",Toast.LENGTH_LONG).show();
            }catch(JSONException e){
                e.printStackTrace();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public class CallDeletePHP extends AsyncTask<String,Void,String> {
        @Override
        protected String doInBackground(String... params) {
            try {

              OkHttpClient client = new OkHttpClient();
                RequestBody postData = new FormEncodingBuilder()
                        .add("inv_id", params[0])
                        .build();
                Request request = new Request.Builder()
                        .url(USER_DELETE_PHP)
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

               if(items != null){
                    items.clear();
                    itemsID.clear();
                    adapter.notifyDataSetChanged();
                }

                if(jsonResult.length() > 0){
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
                   Toast.makeText(getApplicationContext(),"ลบข้อมูลเรียบร้อย",Toast.LENGTH_LONG).show();
                    adapter.notifyDataSetChanged();

                }else{
                    Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
                }
            }catch(JSONException e){
                Toast.makeText(getApplicationContext(), "ไม่สามารถลบข้อมูลได้ "+e ,Toast.LENGTH_LONG).show();

            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}