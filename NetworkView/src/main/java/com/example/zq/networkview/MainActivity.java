package com.example.zq.networkview;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class MainActivity extends ActionBarActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";
    ImageView iv_incon;
    EditText et_url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.i(TAG,"oncreate");
        iv_incon = (ImageView) findViewById(R.id.iv_icon);

        et_url = (EditText)findViewById(R.id.et_url);

        findViewById(R.id.bt_submit).setOnClickListener(this);

    }

   @Override
   public void onClick(View v){
       Log.i(TAG,"onclick");

       String url = et_url.getText().toString();


       iv_incon.setImageBitmap(getImageFromNet(url));
   }

    /**
     *
     * @param url
     * @return url对应的图片
     */

   private Bitmap getImageFromNet(String url){
       HttpURLConnection conn=null;
       try {
           Log.i(TAG,"GETIMAGE");
           //创建一个url对象
           URL imageUrl = new URL(url);
           //得到http的链接对象

           conn = (HttpURLConnection) imageUrl.openConnection();
           //设置链接服务器的超时时间，如果过时则抛出异常
           conn.setConnectTimeout(10000);


//设置读取数据时超时时间
           conn.setReadTimeout(5000);
           //设置请求方法为get
           conn.setRequestMethod("GET");

           conn.connect();//开始链接


           int responseCode = conn.getResponseCode();

           if(responseCode==200){
               //访问成功
               InputStream is = conn.getInputStream();//获得服务器放回的流数据
               Bitmap bitmap = BitmapFactory.decodeStream(is);//根据流数据创建位图数据

               return bitmap;

           }else{
               Log.i("TAG","responsecode="+responseCode);
           }

       } catch (Exception e) {
           e.printStackTrace();
       }
       finally {
           if(conn!=null) {
               conn.disconnect();
           }
       }
       return null;

   }



}
