package cuongd13.news;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TabHost;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import cuongd13.news.Adapter.ListAdapter;
import cuongd13.news.Async.AsyncTaskGetJson;
import cuongd13.news.Model.News;

public class MainActivity extends AppCompatActivity {
    ListView lvHome, lvKinhte, lvChinhtri, lvXahoi;
    TabHost tabHost;
    ArrayList<News> news;
    ListAdapter listAdapter;
    String dulieu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lvHome = (ListView)findViewById(R.id.lstViewHome);
        lvChinhtri = (ListView) findViewById(R.id.lstViewChinhtri);
        lvKinhte = (ListView)findViewById(R.id.lstViewKinhte);
        lvXahoi = (ListView) findViewById(R.id.lstViewXahoi);

        tabHost = (TabHost) findViewById(R.id.tabhost);
        tabHost.setup();
        TabHost.TabSpec tabHome = tabHost.newTabSpec("Home");
        tabHome.setIndicator("Trang Chủ");
        String tagHome  = tabHome.getTag();
        tabHome.setContent(R.id.lstHome);

        TabHost.TabSpec tabKinhte = tabHost.newTabSpec("Kinhte");
        tabKinhte.setIndicator("Kinh tế");
        tabKinhte.setContent(R.id.lstKinte);

        TabHost.TabSpec tabChinhtri = tabHost.newTabSpec("Chinhtri");
        tabChinhtri.setIndicator("Chính trị");
        tabChinhtri.setContent(R.id.lstChinhtri);

        TabHost.TabSpec tabXaHoi = tabHost.newTabSpec("Xahoi");
        tabXaHoi.setIndicator("Xã hội");
        tabXaHoi.setContent(R.id.lstXahoi);

        tabHost.addTab(tabHome);
        tabHost.addTab(tabChinhtri);
        tabHost.addTab(tabXaHoi);
        tabHost.addTab(tabKinhte);

        news = new ArrayList<News>();

        AsyncTaskGetJson getJson0 = new AsyncTaskGetJson();
        getJson0.execute("http://192.168.8.100/tienapi/api/new");
        try {
            dulieu = getJson0.get().toString();
            CustomJson(dulieu, lvHome);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                switch (tabHost.getCurrentTab()){
                    case 0:
                        news.clear();
                        AsyncTaskGetJson getJson0 = new AsyncTaskGetJson();
                        getJson0.execute("http://192.168.8.100/tienapi/api/new");
                        try {
                            dulieu = getJson0.get().toString();
                            CustomJson(dulieu, lvHome);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }
                        break;
                    case 1:
                        news.clear();
                        AsyncTaskGetJson getJson1 = new AsyncTaskGetJson();
                        getJson1.execute("http://192.168.8.100/tienapi/api/new?title=chinh-tri");
                        try {
                            dulieu = getJson1.get().toString();
                            CustomJson(dulieu, lvChinhtri);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }
                        break;
                    case 2:
                        news.clear();
                        AsyncTaskGetJson getJson2 = new AsyncTaskGetJson();
                        getJson2.execute("http://192.168.8.100/tienapi/api/new?title=xa-hoi");
                        try {
                            dulieu = getJson2.get().toString();
                            CustomJson(dulieu, lvXahoi);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }
                        break;
                    case 3:
                        news.clear();
                        AsyncTaskGetJson getJson = new AsyncTaskGetJson();
                        getJson.execute("http://192.168.8.100/tienapi/api/new?title=kinh-te");
                        try {
                            dulieu = getJson.get().toString();
                            CustomJson(dulieu, lvKinhte);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }
                        break;
                }
            }
        });

    }
    public void CustomJson(String str, ListView lv){
        try {
            JSONArray array = new JSONArray(str);
            for (int i = 0 ; i <array.length() ; i++){
                JSONObject object = array.getJSONObject(i);
                News new1 = new News();
                new1.setId(object.getLong("id"));
                new1.setTitle(object.getString("title"));
                new1.setContent(object.getString("Content"));
                new1.setImage(object.getString("image"));
                new1.setCreateDate(object.getString("CreateDate"));
                new1.setId_user(object.getLong("id_user"));
                new1.setId_type(object.getString("id_type"));
                new1.setPosition(Long.parseLong(String.valueOf(i)));
                news.add(new1);
            }
            Log.d("mang" , String.valueOf(news.size()));
            listAdapter = new ListAdapter(MainActivity.this,R.layout.row_news,news);

            listAdapter.notifyDataSetChanged();
            lv.setAdapter(listAdapter);

            lv.setOnItemClickListener(
                    new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            Intent intent = new Intent(MainActivity.this , DetailNewActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("title" , news.get(position).getTitle());
                            bundle.putString("content" , news.get(position).getContent());
                            bundle.putString("date" , news.get(position).getCreateDate());
                            intent.putExtra("dulieu",bundle);
                            startActivity(intent);

                        }
                    }
            );

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}