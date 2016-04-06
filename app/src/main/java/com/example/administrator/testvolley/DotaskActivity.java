package com.example.administrator.testvolley;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.List;

public class DotaskActivity extends AppCompatActivity {

    public  String strUrl="http://10.2.21.239/api/usergen";
    final List<String> data = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dotask);

//        Button hellobtn = (Button)findViewById(R.id.button);
//        //设置监听按钮点击事件
//        hellobtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                getJson(strUrl);
//            }
//        });
        getJson(strUrl);
    }

    public void getJson(String url){
        final Handler handler = new Handler();

        new Thread(){
            @Override
            public void run()
            {
                String Url="http://10.2.21.239/api/DoTreatment?ActionStatus=1&CurrentPage=1";
                //把网络访问的代码放在这里
                String jsonRsult=HttpURLHelper.get(Url);
                try {
                    JSONTokener jsonParser = new JSONTokener(jsonRsult);
                    // 此时还未读取任何json文本，直接读取就是一个JSONObject对象。
                    // 如果此时的读取位置在"name" : 了，那么nextValue就是"yuanzhifei89"（String）
                    JSONObject person = (JSONObject) jsonParser.nextValue();
                    // 接下来的就是JSON对象的操作了
                    JSONArray jsonArray= person.getJSONArray("Data");
                    person.getString("PagesCount");
                    person.getString("IsSuccess");
                    person.getString("ErrorMsg");

                    for(int i = 0; i < jsonArray.length() ; i++){
                        JSONObject jsonObj = (JSONObject)jsonArray.get(i);

                        String ActionInfo = jsonObj.getString("ActionInfo");
                        String CreateTime = jsonObj.getString("CreateTime");
                        data.add(ActionInfo);

                    }

                } catch (JSONException ex) {
                    // 异常处理代码
                }
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        setlistViewData();
                    }
                });
            }
        }.start();
    }

    private  void setlistViewData()
    {
        ListView listview=(ListView)findViewById(R.id.listView);
        listview.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_expandable_list_item_1,data));

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
                // TODO Auto-generated method stub
                new AlertDialog.Builder(DotaskActivity.this)
                        .setTitle("查看")
                        .setMessage(data.get(0))
                        .setNegativeButton("取消", null)
                        .setPositiveButton("确定", null)
                        .show();
            }

        });
    }
}
