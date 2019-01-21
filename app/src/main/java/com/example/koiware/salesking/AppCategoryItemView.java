package com.example.koiware.salesking;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.bumptech.glide.Glide;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class AppCategoryItemView extends LinearLayout {
    TextView textView2;
    TextView textView3;
    TextView textView4;
    ImageView imageView2;
    Handler handler = new Handler();

    //RequestQueue queue;
    Context contextVal;
    private ImageLoader mImageLoader ;

    public AppCategoryItemView(Context context) {
        super(context);
        init(context);
    }

    public void init(Context context) {
        contextVal = context;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //inflater.inflate(R.layout.app_item, this, true);
        inflater.inflate(R.layout.app_category_item, this, true);

        textView2 = (TextView) findViewById(R.id.textView2) ;
        //textView3 = (TextView) findViewById(R.id.textViewRegNm) ;
        textView4 = (TextView) findViewById(R.id.textView4) ;
        imageView2 = (ImageView) findViewById(R.id.imageView2) ;

    }

    public void setCategoryCd(String categoryCd) {
        //textView2.setText(categoryCd);
    }

    public void setCategoryImgUrl(String categoryImgUrl) {
        //textView3.setText(categoryImgUrl);
        String categoryIconImgUrl = "";
        categoryIconImgUrl = CommonUtil.getIconImgUrl(categoryImgUrl);

        Glide.with(contextVal)
                .load(categoryIconImgUrl)
                .into(imageView2);
    }

    public void setCategoryNm(String categoryNm) {
        textView4.setText(categoryNm);
    }

    public void setImage(String resId) {
        String urlPath = "";

        /*if(resId.equals("")) {
            urlPath = "https://firebasestorage.googleapis.com/v0/b/login-c05ab.appspot.com/o/images%2Fnoimage.png?alt=media&token=d151f7a2-d27a-4ede-979c-a3db615d0622";
        }else {
            urlPath = resId;
        }*/
        urlPath = "https://firebasestorage.googleapis.com/v0/b/login-c05ab.appspot.com/o/images%2Fnoimage.png?alt=media&token=d151f7a2-d27a-4ede-979c-a3db615d0622";

        final FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReferenceFromUrl("gs://login-c05ab.appspot.com");

        StorageReference fileUrlPathRef = storageRef.child("images").child(urlPath);

        Glide.with(contextVal)
                .load(urlPath)
                .fitCenter()
                .centerCrop()
                .into(imageView2);


    }
}
