package com.example.koiware.salesking;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MenuManageView05 extends AppCompatActivity {

    //private FirebaseAuth mAuth;
    /*private Button btChoose;
    private Button btUpload;*/



    TextView btnChoose;
    TextView btnUpload;

    private ImageView ivPreview;

    private Uri filePath;

    String insertImgUrl = "";
    String insertEncImgUrl = "";

    TextView btnTypeSale;
    TextView btnTypePro;
    TextView btnTypeEvent;


    EditText insertRegNm05;
    EditText insertAppNm05;
    EditText insertAppLink05;
    EditText insertAppPrice05;

    EditText insertRegMail05;
    EditText insertAppSalePrice05;
    String insertRegTypeVal05 = "1";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_manage_view05);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().hide();
        /*ActionBar ab = getActionBar();
        ab.setDisplayShowCustomEnabled(true);
        //ab.setDisplayShowTitleEnabled(false);
        //ab.setIcon(R.drawable.ic_action_title);

        LayoutInflater inflator = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflator.inflate(R.layout.action_bar_title, null);
        ActionBar.LayoutParams layoutParams = new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParams.gravity = Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL;*/
        //getSupportActionBar().setCustomView(R.layout.action_bar_title);


        View appActionbarManage05 = findViewById(R.id.app_actionbar_manage05);
        //arrowBackActionBtn05 = appActionbarManage05.findViewById(R.id.arrowBackActionBtn05);

        ImageView arrowBackActionBtn05 = (ImageView)appActionbarManage05.findViewById(R.id.arrowBackActionBtn05);


        arrowBackActionBtn05.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TextView 클릭될 시 할 코드작성
                //ContextCompat.getColor(getApplicationContext(), R.color.colorLightSkyBlue);
                Log.i("left menu05", "back");
                finish();
            }
        });

        TextView saveActionBtn05 = appActionbarManage05.findViewById(R.id.saveActionBtn05);

        saveActionBtn05.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TextView 클릭될 시 할 코드작성
                try {
                    Log.i("left menu05", "save");

                    String result;
                    MenuManageView05.CustomTask task = new MenuManageView05.CustomTask();


                    //View appActionbarManage05_2 = findViewById(R.id.app_actionbar_manage05);

                    insertRegNm05           = findViewById(R.id.insertRegNm05);
                    insertAppNm05           = findViewById(R.id.insertAppNm05);
                    insertAppLink05         = findViewById(R.id.insertAppLink05);

                    insertAppPrice05        = findViewById(R.id.insertAppPrice05);
                    insertRegMail05         = findViewById(R.id.insertRegMail05);
                    insertAppSalePrice05   = findViewById(R.id.insertAppSalePrice05);


                    String insertRegNmVal05         = insertRegNm05.getText().toString();
                    String insertAppNmVal05         = insertAppNm05.getText().toString();
                    String insertAppLinkVal05       = insertAppLink05.getText().toString();

                    String insertAppPriceVal05      = insertAppPrice05.getText().toString();
                    String insertRegMailVal05       = insertRegMail05.getText().toString();
                    String insertAppSalePriceVal05  = insertAppSalePrice05.getText().toString();

                    //result = task.execute("rain483","1234").get();

                    //getEncIngUrl();

                    result = task.execute(insertRegNmVal05, insertAppNmVal05, insertAppLinkVal05
                            , insertAppPriceVal05, insertRegMailVal05, insertAppSalePriceVal05).get();

                    Intent MainView = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(MainView);
                } catch (Exception e) {
                    Log.d("Exception", e.getMessage());
                }
            }
        });



        /*FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();

        if(user!=null){
            mAuth = FirebaseAuth.getInstance();
            Log.i("a user is logged in: ",user.getEmail());
        }
        else{
            Log.i("a user is logged in: ","fail");
        }*/

        /*btChoose = (Button) findViewById(R.id.bt_choose);
        btUpload = (Button) findViewById(R.id.bt_upload);*/

        btnChoose = findViewById(R.id.btn_choose);
        btnUpload = findViewById(R.id.btn_upload);

        ivPreview = (ImageView) findViewById(R.id.iv_preview);

        //버튼 클릭 이벤트
        /*btChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("upload 이미지", "선택");
                //이미지를 선택
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "이미지를 선택하세요."), 0);
            }
        });*/

        btnChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("upload 이미지", "선택");
                //이미지를 선택
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "이미지를 선택하세요."), 0);
            }
        });


        btnTypeSale = findViewById(R.id.btn_type_sale);
        btnTypePro = findViewById(R.id.btn_type_pro);
        btnTypeEvent = findViewById(R.id.btn_type_event);

        btnTypeSale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TextView 클릭될 시 할 코드작성
                //ContextCompat.getColor(getApplicationContext(), R.color.colorLightSkyBlue);
                insertRegTypeVal05 = "1";
                btnTypeSale.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorLightSkyBlue));
                btnTypePro.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorWhite));
                btnTypeEvent.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorWhite));

                btnTypeSale.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorWhite));
                btnTypePro.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorBlack));
                btnTypeEvent.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorBlack));
            }
        });

        btnTypePro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TextView 클릭될 시 할 코드작성
                insertRegTypeVal05 = "2";
                btnTypeSale.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorWhite));
                btnTypePro.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorLightSkyBlue));
                btnTypeEvent.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorWhite));

                btnTypeSale.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorBlack));
                btnTypePro.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorWhite));
                btnTypeEvent.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorBlack));
            }
        });

        btnTypeEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TextView 클릭될 시 할 코드작성
                insertRegTypeVal05 = "3";
                btnTypeSale.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorWhite));
                btnTypePro.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorWhite));
                btnTypeEvent.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorLightSkyBlue));

                btnTypeSale.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorBlack));
                btnTypePro.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorBlack));
                btnTypeEvent.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorWhite));
            }
        });



        /*btUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //업로드
                uploadFile();
            }
        });*/

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //업로드
                uploadFile();
            }
        });

    }


    //****************** 메뉴 설정 및 선택 이벤트 start *********************
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.save_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_save) {
            try {
                Log.i("완료 선택", "성공적");

                String result;
                MenuManageView05.CustomTask task = new MenuManageView05.CustomTask();
                result = task.execute("rain483","1234").get();

                Intent MainView = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(MainView);

            } catch (Exception e) {
                Log.d("Exception", e.getMessage());
            }

        }else if (id == R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    //****************** 메뉴 설정 및 선택 이벤트 end *********************


    //****************** 이미지 업로드 이벤트 start *********************
    /*public void imageUloadFunc() {
        btChoose = (Button) findViewById(R.id.bt_choose);
        btUpload = (Button) findViewById(R.id.bt_upload);
        ivPreview = (ImageView) findViewById(R.id.iv_preview);

        //버튼 클릭 이벤트
        btChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("upload 이미지", "선택");
                //이미지를 선택
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "이미지를 선택하세요."), 0);
            }
        });

        btUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //업로드
                uploadFile();
            }
        });
    }*/
    //결과 처리
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //request코드가 0이고 OK를 선택했고 data에 뭔가가 들어 있다면
        if(requestCode == 0 && resultCode == RESULT_OK){
            filePath = data.getData();
            Log.d("MenuManageView05 img up", "uri:" + String.valueOf(filePath));
            try {
                //Uri 파일을 Bitmap으로 만들어서 ImageView에 집어 넣는다.
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                ivPreview.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //upload the file
    private void uploadFile() {
        //업로드할 파일이 있으면 수행
        if (filePath != null) {
            //업로드 진행 Dialog 보이기
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("업로드중...");
            progressDialog.show();

            //storage
            FirebaseStorage storage = FirebaseStorage.getInstance();

            //Unique한 파일명을 만들자.

            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMHH_mmss");
            Date now = new Date();
            String filename = formatter.format(now) + ".png";
            //storage 주소와 폴더 파일명을 지정해 준다.
            StorageReference storageRef = storage.getReferenceFromUrl("gs://login-c05ab.appspot.com").child("images/" + filename);

            insertImgUrl = filename;

            //올라가거라...
            storageRef.putFile(filePath)
                    //성공시
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss(); //업로드 진행 Dialog 상자 닫기
                            Toast.makeText(getApplicationContext(), "업로드 완료!", Toast.LENGTH_SHORT).show();

                            final FirebaseStorage storage = FirebaseStorage.getInstance();
                            StorageReference storageRef = storage.getReferenceFromUrl("gs://login-c05ab.appspot.com");

                            StorageReference fileUrlPathRef = storageRef.child("images").child(insertImgUrl);

                            fileUrlPathRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    //saveUrl = uri.toString();
                                    Log.d("AppItemView img url", "MyDownloadLink:  " + uri.toString());


                                    //Glide.with(contextVal).load(uri.toString()).into(imageView2);

                                    //.diskCacheStrategy(DiskCacheStrategy.NONE)
                                    //.dontAnimate()

                                    //mIvOrigin.setImageResource(R.drawable.doge);
                                    insertEncImgUrl = uri.toString();
                                }
                            });
                        }
                    })
                    //실패시
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "업로드 실패!", Toast.LENGTH_SHORT).show();
                        }
                    })
                    //진행중
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            @SuppressWarnings("VisibleForTests") //이걸 넣어 줘야 아랫줄에 에러가 사라진다. 넌 누구냐?
                                    double progress = (100 * taskSnapshot.getBytesTransferred()) /  taskSnapshot.getTotalByteCount();
                            //dialog에 진행률을 퍼센트로 출력해 준다
                            progressDialog.setMessage("Uploaded " + ((int) progress) + "% ...");
                        }
                    });
        } else {
            Toast.makeText(getApplicationContext(), "파일을 먼저 선택하세요.", Toast.LENGTH_SHORT).show();
        }


    }
    //****************** 이미지 업로드 이벤트 end *********************


    //****************** 앱 정보 등록 이벤트 start *********************
    class CustomTask extends AsyncTask<String, Void, String> {
        String sendMsg, receiveMsg;
        @Override
        protected String doInBackground(String... strings) {
            try {
                String str;

                //JSP 서버 URL을 통해 POST 방식으로 데이터를 요청하는 부분
                //URL url = new URL("https://172.30.1.28/android_board_server/androidTest.jsp");
                //URL url = new URL("http://172.30.1.48:8080/android_board_server/insertAppInfo.jsp");

                URL url = CommonUtil.getServerUrl("insertAppInfo.jsp");

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
                //sendMsg = "insertImgUrl="+insertImgUrl;
                /*sendMsg = "insertRegNm="+"코이웨어"
                        +"&" + "insertAppNm="+"코이웨어 앱"
                        +"&" + "insertAppLink="+"123453123"
                        +"&" + "insertAppPrice="+"9900"

                        +"&" + "insertImgUrl="+insertImgUrl
                        +"&" + "insertRegMail="+"koi@naver.com"
                        +"&" + "insertAppSalePrice="+"8800"
                        +"&" + "insertAppEventType="+"1"
                ;*/





                sendMsg = "insertRegNm="+strings[0]
                        +"&" + "insertAppNm="+strings[1]
                        +"&" + "insertAppLink="+strings[2]
                        +"&" + "insertAppPrice="+strings[3]

                        //+"&" + "insertImgUrl="+insertImgUrl
                        +"&" + "insertImgUrl="+insertEncImgUrl
                        +"&" + "insertRegMail="+strings[4]
                        +"&" + "insertAppSalePrice="+strings[5]
                        +"&" + "insertAppEventType="+insertRegTypeVal05
                ;

                Log.i("insert snedMng", sendMsg);
                osw.write(sendMsg);
                osw.flush();
                Log.d(this.getClass().getName(), "연동 성공 !!!!6");
                if(conn.getResponseCode() == conn.HTTP_OK) {
                    Log.d(this.getClass().getName(), "연동 성공 !!!!");

                    InputStreamReader tmp = new InputStreamReader(conn.getInputStream(), "UTF-8");
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

                    final String finalImgUrl = "";

                    /*if(jArr.length() > 0) {
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
                    }*/


                    Log.d(this.getClass().getName(), "jsp에서 받은 메시지 : " + json.getString("status"));

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
    //****************** 앱 정보 등록 이벤트 end *********************


    public void getEncIngUrl() {
        final FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReferenceFromUrl("gs://login-c05ab.appspot.com");

        StorageReference fileUrlPathRef = storageRef.child("images").child(insertImgUrl);

        fileUrlPathRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                //saveUrl = uri.toString();
                Log.d("AppItemView img url", "MyDownloadLink:  " + uri.toString());


                //Glide.with(contextVal).load(uri.toString()).into(imageView2);

                //.diskCacheStrategy(DiskCacheStrategy.NONE)
                //.dontAnimate()

                //mIvOrigin.setImageResource(R.drawable.doge);
                insertEncImgUrl = uri.toString();
            }
        });
    }


}
