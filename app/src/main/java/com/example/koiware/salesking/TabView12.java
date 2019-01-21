package com.example.koiware.salesking;

        import android.app.Activity;
        import android.content.Intent;
        import android.os.AsyncTask;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.AdapterView;
        import android.widget.ArrayAdapter;
        import android.widget.BaseAdapter;
        import android.widget.EditText;
        import android.widget.ImageView;
        import android.widget.ListView;
        import android.widget.TextView;
        import android.widget.Toast;

        import org.json.JSONArray;
        import org.json.JSONObject;

        import java.io.BufferedReader;
        import java.io.IOException;
        import java.io.InputStreamReader;
        import java.io.OutputStreamWriter;
        import java.net.HttpURLConnection;
        import java.net.MalformedURLException;
        import java.net.URL;
        import java.util.ArrayList;
        import java.util.List;

public class TabView12 extends Activity {

    EditText editText;
    ListView listView;

    AppCategoryAdapter categoryAdapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.tab_view12);


            String result;
            CustomCategoryTask categoryTask = new CustomCategoryTask();
            result = categoryTask.execute("rain483","1234").get();


        } catch (Exception e) {
            Log.d("Exception", e.getMessage());
        }

    }



    class AppCategoryAdapter extends BaseAdapter {
        ArrayList<AppCategoryItem> items = new ArrayList<AppCategoryItem>();

        //items의 개수
        @Override
        public int getCount() {
            return items.size();
        }

        //items을 itmes에 추가하는 메소드
        public void addItem(AppCategoryItem item) {
            items.add(item);
        }

        public Object getItem(int position) {
            return items.get(position);
        }

        //리스트 내의 지정된 위치에 관련한 행 id를 가져옴
        @Override
        public long getItemId(int position) {
            return position;
        }

        //데이터 세트의 지정된 위치에 데이터를 표시하는 뷰를 가져옵니다.
        //int position : 보기에 필요한 항목의 어댑터 데이터 세트 내의 항목의 위치입니다.
        //View convertView : 올드 뷰
        //ViewGroup viewGroup : 이 뷰가 최종적으로 첨부되는 부모
        @Override
        public View getView(int position, View convertView, ViewGroup viewGroup) {
            //getApplicationContext() : 응용 프로그램 수준에 사용되며 모든 활동을 나타냄
            AppCategoryItemView view = new AppCategoryItemView(getApplicationContext());


            //한행씩 SingerItem 어레이 리스트를 가져와 SingerItemView 에 세팅
            AppCategoryItem item = items.get(position);
            view.setCategoryCd(item.getCategoryCd());
            view.setCategoryImgUrl(item.getCategoryImgUrl());
            view.setCategoryNm(item.getCategoryNm());

            return view;
        }
    }




    //카테고리 리스트 가져오는 부분
    class CustomCategoryTask extends AsyncTask<String, Void, String> {
        String sendMsg, receiveMsg;
        @Override
        protected String doInBackground(String... strings) {
            try {
                String str;

                //JSP 서버 URL을 통해 POST 방식으로 데이터를 요청하는 부분
                //URL url = new URL("https://172.30.1.28/android_board_server/androidTest.jsp");
                //URL url = new URL("http://172.30.1.48:8080/android_board_server/tabView12.jsp");

                URL url = CommonUtil.getServerUrl("tabView12.jsp");

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

                conn.setRequestMethod("POST");
                //conn.setDoOutput(true);
                //conn.setDoInput(true);
                Log.d(this.getClass().getName(), "연동 성공 !!!!4.5");
                OutputStreamWriter osw =  new OutputStreamWriter(conn.getOutputStream());
                Log.d(this.getClass().getName(), "연동 성공 !!!!5");
                //sendMsg = "id="+strings[0]+"&pwd="+strings[1];

                //앱에서 JSP서버로 데이터를 전송하는 부분
                sendMsg = "id="+"rain483"+"&pwd="+"12345";
                osw.write(sendMsg);
                osw.flush();
                Log.d(this.getClass().getName(), "연동 성공 !!!!6");
                if(conn.getResponseCode() == conn.HTTP_OK) {
                    Log.d(this.getClass().getName(), "연동 성공 !!!!");

                    InputStreamReader tmp = new InputStreamReader(conn.getInputStream(), "EUC-KR");
                    //InputStreamReader tmp = new InputStreamReader(conn.getInputStream(), "EUC-KR");
                    BufferedReader reader = new BufferedReader(tmp);
                    StringBuffer buffer = new StringBuffer();

                    //JSP서버로부터 응답받은 데이터를 가져오는 부분
                    while ((str = reader.readLine()) != null) {
                        buffer.append(str);
                    }


                    receiveMsg = buffer.toString();
                    Log.d(this.getClass().getName(), "jsp에서 받은 메시지 22 : " + receiveMsg);
                    JSONObject json = new JSONObject(receiveMsg);
                    JSONArray jArr = json.getJSONArray("dataSend");

                    listView = (ListView)findViewById(R.id.listView);
                    //adapter = new AppAdapter();
                    categoryAdapter = new AppCategoryAdapter();

                    //JSP서버로부터 응답받은 JSON 데이터를 listView(레이아웃)에 넣어주는 부분
                    for(int i=0; i<jArr.length(); i++) {
                        json = jArr.getJSONObject(i);
                        categoryAdapter.addItem(new AppCategoryItem(json.getString("categoryCd"), json.getString("categoryImgUrl"), json.getString("categoryNm")));

                    }

                    /*adapter.addItem(new SingerItem("aaa", "bbb", 20, R.drawable.ic_launcher_background));
                    adapter.addItem(new SingerItem("aaa2", "bbb2", 20, R.drawable.ic_launcher_background));
                    adapter.addItem(new SingerItem("aaa3", "bbb3", 20, R.drawable.ic_launcher_background));*/

                    //리스트 레이아웃 세팅 부분
                    listView.setAdapter(categoryAdapter);

                    //리스트 클릭시 이벤트, 해당 데이터의 간단한 설명을 보여주는 부분
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int position, long ld) {
                            //AppItem item = (AppItem)categoryAdapter.getItem(position);
                            try {
                                AppCategoryItem categoryItem = (AppCategoryItem)categoryAdapter.getItem(position);
                                //Toast.makeText(getApplicationContext(), "선택  : " + categoryItem.getCategoryCd(), Toast.LENGTH_LONG).show();;

                                Intent intent = new Intent(getApplicationContext(), TabView121.class);

                                intent.putExtra("searchCategoryCd", categoryItem.getCategoryCd());

                                startActivity(intent);


                            } catch (Exception e) {
                                Log.d("go tab121 Exception", e.getMessage());
                            }
                        }
                    });
                    /*listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int position, long ld) {
                            AppItem item = (AppItem)adapter.getItem(position);
                            Toast.makeText(getApplicationContext(), "선택  : " + item.getAppNm(), Toast.LENGTH_LONG).show();;
                        }
                    });*/



                    Log.d(this.getClass().getName(), "jsp에서 받은 메시지 : " + receiveMsg);

                } else {
                    Log.i("통신 결과", conn.getResponseCode()+"에러");
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
                Log.i("통신 에러1 : ", e.getMessage()+"에러");
            } catch (IOException e) {
                e.printStackTrace();
                Log.i("통신 에러2 : ", e.getMessage()+"에러");
            } catch (Exception e) {
                e.printStackTrace();
                Log.i("통신 에러3 : ", e.getMessage()+"에러");
            }
            return receiveMsg;
        }
    }
}
