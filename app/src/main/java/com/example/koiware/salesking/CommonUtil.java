package com.example.koiware.salesking;

import android.util.Log;

import java.net.URL;

public class CommonUtil {
    //private static String serverUrl = "http://172.30.1.11:8080/android_board_server/";
    private static String serverUrl = "http://52.79.221.9:8080/";

    public static URL getServerUrl(String subPath) {
        URL url = null;
        try {
            //url = new URL("http://52.79.221.9:8080/" + subPath);
            //url = new URL("http://172.30.1.11:8080/android_board_server/" + subPath);

            url = new URL(serverUrl + subPath);

            return url;
        } catch (Exception e) {
            e.printStackTrace();
            Log.i("서버 통신 에러 : ", e.getMessage()+"에러");
        }

        return url;
    }

    public static String getIconImgUrl(String iconNm) {
        String iconImgUrl = "";

        iconImgUrl = serverUrl + "images/icon/" + iconNm;

        return iconImgUrl;
    }

}
