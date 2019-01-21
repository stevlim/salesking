package com.example.koiware.salesking;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.Target;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.InputStream;
import java.net.URL;

public class AppItemView extends LinearLayout {
    TextView textViewNo;


    TextView textView2;
    TextView textView3;
    TextView textView4;
    ImageView imageView2;



    Handler handler = new Handler();

    //RequestQueue queue;
    Context contextVal;
    private ImageLoader mImageLoader ;

    public AppItemView(Context context) {
        super(context);
        init(context);
    }

    public void init(Context context) {
        contextVal = context;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.app_item, this, true);

        textViewNo = (TextView) findViewById(R.id.textViewNo) ;

        textView2 = (TextView) findViewById(R.id.textView2) ;
        textView3 = (TextView) findViewById(R.id.textViewRegNm) ;
        textView4 = (TextView) findViewById(R.id.textView4) ;
        imageView2 = (ImageView) findViewById(R.id.imageView2) ;

    }

    public void setNo(String no) {
        textViewNo.setText(no);
    }


    public void setAppNm(String appNm) {
        textView2.setText(appNm);
    }

    public void setAppDesc(String appDesc) {
        textView3.setText(appDesc);
    }

    public void setDownCnt(int downCnt) {
        textView4.setText(String.valueOf(downCnt));
    }

    public void setImage(String resId) {
        String urlPath = "";

        if(resId.equals("")) {
            urlPath = "https://firebasestorage.googleapis.com/v0/b/login-c05ab.appspot.com/o/images%2Fnoimage.png?alt=media&token=d151f7a2-d27a-4ede-979c-a3db615d0622";
        }else {
            urlPath = resId;
        }


        final FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReferenceFromUrl("gs://login-c05ab.appspot.com");

        StorageReference fileUrlPathRef = storageRef.child("images").child(urlPath);

        Glide.with(contextVal)
                .load(urlPath)
                .fitCenter()
                .centerCrop()
                .into(imageView2);

        /*fileUrlPathRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                //saveUrl = uri.toString();
                Log.d("AppItemView img url", "MyDownloadLink:  " + uri.toString());


                //Glide.with(contextVal).load(uri.toString()).into(imageView2);


                Glide.with(contextVal)
                        .load(uri.toString())
                        .fitCenter()
                        .centerCrop()
                        .into(imageView2);

                //.diskCacheStrategy(DiskCacheStrategy.NONE)
                //.dontAnimate()

                //mIvOrigin.setImageResource(R.drawable.doge);
            }
        });*/



        /*storageRef.child("images/20190111_1316.png").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                // Got the download URL for 'users/me/profile.png'
                Log.i("img url", uri.toString());
                Glide.with(contextVal).load(uri.toString()).into(imageView2);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });*/


        //Glide.with(contextVal).load(urlPath).into(imageView2);
        //Glide.with(contextVal).load("https://firebasestorage.googleapis.com/v0/b/login-c05ab.appspot.com/o/images%2F20190111_1316.png?alt=media&token=4b6821e8-172d-465d-be69-5f9ff2c04730").into(imageView2);

        /*Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final ImageView iv = findViewById(R.id.imageView2);
                    //URL url = new URL("http://172.30.1.28:8080/android_board_server/images/icey.jpg");
                    URL url = new URL(urlPath);
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
    }
}
