package com.example.koiware.salesking;

        import android.app.Activity;
        import android.app.Application;
        import android.content.Context;
        import android.content.Intent;
        import android.graphics.Bitmap;
        import android.graphics.BitmapFactory;
        import android.graphics.Color;
        import android.net.Uri;
        import android.os.AsyncTask;
        import android.os.Bundle;
        import android.os.Handler;
        import android.support.annotation.NonNull;
        import android.support.v7.app.AppCompatActivity;
        import android.util.Log;
        import android.view.MenuItem;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.AdapterView;
        import android.widget.BaseAdapter;
        import android.widget.ImageView;
        import android.widget.LinearLayout;
        import android.widget.ListView;
        import android.widget.TextView;
        import android.widget.Toast;

        import com.bumptech.glide.Glide;
        import com.bumptech.glide.request.target.Target;
        import com.github.mikephil.charting.charts.LineChart;
        import com.github.mikephil.charting.components.Legend;
        import com.github.mikephil.charting.components.XAxis;
        import com.github.mikephil.charting.components.YAxis;
        import com.github.mikephil.charting.data.Entry;
        import com.github.mikephil.charting.data.LineData;
        import com.github.mikephil.charting.data.LineDataSet;
        import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
        import com.google.android.gms.tasks.OnFailureListener;
        import com.google.android.gms.tasks.OnSuccessListener;
        import com.google.firebase.storage.FirebaseStorage;
        import com.google.firebase.storage.StorageReference;

        import org.json.JSONArray;
        import org.json.JSONObject;

        import java.io.BufferedReader;
        import java.io.IOException;
        import java.io.InputStream;
        import java.io.InputStreamReader;
        import java.io.OutputStreamWriter;
        import java.net.HttpURLConnection;
        import java.net.MalformedURLException;
        import java.net.URL;
        import java.util.ArrayList;

public class SearchDetailView extends AppCompatActivity {
    ListView listView;
    SearchDetailView.AppAdapter adapter;
    String searchSeqVal = "";
    String searchImgUrl = "";
    String imgUrlSpace = "";

    ImageView imgUrlView;
    TextView textAppNm;
    TextView textGenre;
    TextView textRegNm;

    Context contextVal;
    Handler handler = new Handler();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.search_detail_view);
            contextVal = getApplicationContext();

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().hide();


            Intent intent = getIntent();

            //검색한 단어 가져오는 부분
            searchSeqVal = intent.getExtras().getString("searchSeq");

            //searchImgUrl = intent.getExtras().getString("searchImgUrl");

            if(intent.getExtras().getString("searchImgUrl").equals("")) {
                searchImgUrl = "https://firebasestorage.googleapis.com/v0/b/login-c05ab.appspot.com/o/images%2Fnoimage.png?alt=media&token=d151f7a2-d27a-4ede-979c-a3db615d0622";
            }else {
                searchImgUrl = intent.getExtras().getString("searchImgUrl");
            }

            Log.i("searchSeqVal view", searchSeqVal);


            final ImageView iv = findViewById(R.id.imgUrlView);

            Glide.with(contextVal).load(searchImgUrl).into(iv);


            //수평 스크롤 뷰에 이미지 넣는 부분
            LinearLayout searchDetailViewLinear001 = findViewById(R.id.search_detail_view_linear001);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, 600);
            lp.setMargins(5, 5, 5, 5);

            //1번 사진 (향후 for문 사용 예정)
            ImageView appImgView01 = new ImageView(contextVal);
            //appImgView01.setMaxHeight(10);
            appImgView01.setLayoutParams(lp);
            //appImgView01.setla
            Glide.with(contextVal).load(searchImgUrl).into(appImgView01);


            searchDetailViewLinear001.addView(appImgView01);


            //2번 사진 (향후 for문 사용 예정)
            ImageView appImgView02 = new ImageView(contextVal);
            //appImgView02.setMaxHeight(10);
            appImgView02.setLayoutParams(lp);

            Glide.with(contextVal).load(searchImgUrl).into(appImgView02);


            searchDetailViewLinear001.addView(appImgView02);


            String result;
            CustomTask task = new CustomTask();
            result = task.execute("rain483","1234").get();


            ImageView arrowBackActionSearchDetailBtn = findViewById(R.id.arrowBackActionLoginBtn);

            arrowBackActionSearchDetailBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TextView 클릭될 시 할 코드작성
                    //ContextCompat.getColor(getApplicationContext(), R.color.colorLightSkyBlue);
                    Log.i("search detail view", "back");
                    finish();
                }
            });

            TextView textLinkUrl = findViewById(R.id.textLinkUrl);

            textLinkUrl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Uri uriUrl = Uri.parse("https://www.daum.net/");
                    Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
                    startActivity(launchBrowser);
                }
            });



            //차트 생성부분
            createPriceHistoryChart();

        } catch (Exception e) {
            Log.d("Exception", e.getMessage());
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{ //toolbar의 back키 눌렀을 때 동작
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    class CustomTask extends AsyncTask<String, Void, String> {
        String sendMsg, receiveMsg;
        @Override
        protected String doInBackground(String... strings) {
            try {
                String str;

                //JSP 서버 URL을 통해 POST 방식으로 데이터를 요청하는 부분
                //URL url = new URL("https://172.30.1.28/android_board_server/androidTest.jsp");
                //URL url = new URL("http://172.30.1.48:8080/android_board_server/searchDetailView.jsp");

                URL url = CommonUtil.getServerUrl("searchDetailView.jsp");

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
                sendMsg = "searchSeqVal="+searchSeqVal;
                osw.write(sendMsg);
                osw.flush();
                Log.d(this.getClass().getName(), "연동 성공 !!!!6");
                if(conn.getResponseCode() == conn.HTTP_OK) {
                    Log.d(this.getClass().getName(), "연동 성공 !!!!");

                    //InputStreamReader tmp = new InputStreamReader(conn.getInputStream(), "UTF-8");
                    InputStreamReader tmp = new InputStreamReader(conn.getInputStream(), "EUC-KR");
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

                    final String finalImgUrl = "";

                    if(jArr.length() > 0) {
                        json = jArr.getJSONObject(0);

                        String  imgUrlViewVal = json.getString("imgUrl");
                        imgUrlSpace = imgUrlViewVal;

                        String  textAppNmVal = json.getString("appNm");
                        String  textGenreVal = json.getString("genre");
                        String  textRegNmVal = json.getString("regNm");


                        imgUrlView = (ImageView) findViewById(R.id.imgUrlView) ;
                        textAppNm = (TextView) findViewById(R.id.textAppNm) ;
                        textGenre = (TextView) findViewById(R.id.textGenre) ;
                        textRegNm = (TextView) findViewById(R.id.textRegNm) ;




                        //Glide.with(contextVal).load(imgUrlViewVal).into(imgUrlView);
                        textAppNm.setText(textAppNmVal);
                        textGenre.setText(textGenreVal);
                        textRegNm.setText(textRegNmVal);
                    }

                    /*Thread t = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                final ImageView iv = findViewById(R.id.imgUrlView);
                                final String imgUrlViewVal = imgUrlSpace;
                                //URL url = new URL("http://172.30.1.28:8080/android_board_server/images/icey.jpg");
                                URL url = new URL(imgUrlViewVal);
                                InputStream is = url.openStream();
                                final Bitmap bm = BitmapFactory.decodeStream(is);
                                handler.post(new Runnable() {

                                    @Override
                                    public void run() {  // 화면에 그려줄 작업
                                        iv.setImageBitmap(bm);
                                    }
                                });
                                iv.setImageBitmap(bm); //비트맵 객체로 보여주기

                            } catch (Exception e) {
                                Log.d("Exception AppItemView", e.getMessage());
                            }
                        }
                    });

                    t.start();*/

                    //adapter.addItem(new AppItem( json.getString("appNm"), json.getString("regNm"), json.getInt("downCnt") , json.getString("imgUrl")));

                    /*listView = (ListView)findViewById(R.id.listView);
                    adapter = new SearchDetailView().AppAdapter();

                    //JSP서버로부터 응답받은 JSON 데이터를 listView(레이아웃)에 넣어주는 부분
                    for(int i=0; i<jArr.length(); i++) {
                        json = jArr.getJSONObject(i);
                        adapter.addItem(new AppItem( json.getString("appNm"), json.getString("regNm"), json.getInt("downCnt") , json.getString("imgUrl")));

                    }

                    //리스트 레이아웃 세팅 부분
                    listView.setAdapter(adapter);

                    //리스트 클릭시 이벤트, 해당 데이터의 간단한 설명을 보여주는 부분
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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


    class AppAdapter extends BaseAdapter {
        ArrayList<AppItem> items = new ArrayList<AppItem>();

        //items의 개수
        @Override
        public int getCount() {
            return items.size();
        }

        //items을 itmes에 추가하는 메소드
        public void addItem(AppItem item) {
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
            AppItemView view = new AppItemView(getApplicationContext());

            //한행씩 SingerItem 어레이 리스트를 가져와 SingerItemView 에 세팅
            AppItem item = items.get(position);
            view.setAppNm(item.getAppNm());
            view.setAppDesc(item.getAppDesc());
            view.setDownCnt(item.getDownCnt());
            view.setImage(item.getResId());

            return view;
        }
    }






    /*앱 가격 변동 차트 생성 부분*/
    public void createPriceHistoryChart() {
        LineChart chart = findViewById(R.id.chart);

        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextColor(Color.BLACK);


        YAxis yLAxis = chart.getAxisLeft();
        yLAxis.setTextColor(Color.BLACK);

        YAxis yRAxis = chart.getAxisRight();
        yRAxis.setDrawLabels(false);
        yRAxis.setDrawAxisLine(false);
        yRAxis.setDrawGridLines(false);

        //yRAxis.setSpaceTop(1);

        ArrayList<Entry> valsComp1 = new ArrayList<Entry>();

        valsComp1.add(new Entry(100, 0));
        valsComp1.add(new Entry(300, 1));
        valsComp1.add(new Entry(75, 2));
        valsComp1.add(new Entry(50, 3));

        LineDataSet setComp1 = new LineDataSet(valsComp1, "price");
        setComp1.setAxisDependency(YAxis.AxisDependency.LEFT);
        setComp1.setCircleColor(Color.parseColor("#F44336"));
        setComp1.setColor(Color.parseColor("#F44336"));

        ArrayList<ILineDataSet> dataSet = new ArrayList<ILineDataSet>();
        dataSet.add(setComp1);

        ArrayList<String> xVals = new ArrayList<String>();

        xVals.add("12/18");
        xVals.add("12/19");
        xVals.add("12/30");
        xVals.add("01/06");
        LineData data = new LineData(xVals, dataSet);

        //Description 삭제 부분
        chart.setDescription("");
        Legend leg = chart.getLegend();
        leg.setEnabled(false);



        chart.setData(data);
        chart.invalidate();
    }
}
