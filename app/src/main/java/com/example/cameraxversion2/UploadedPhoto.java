package com.example.cameraxversion2;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.YuvImage;
import android.media.Image;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import static android.graphics.ImageFormat.YUV_420_888;
import static android.graphics.ImageFormat.YUY2;
import static android.graphics.ImageFormat.YV12;

public class UploadedPhoto {
//

//    String name = c.getString(str_url);
//    URL url_value = new URL(name);
//    ImageView profile = (ImageView)v.findViewById(R.id.vdo_icon);
//            if (profile != null) {
//        Bitmap mIcon1 =
//                BitmapFactory.decodeStream(url_value.openConnection().getInputStream());
//        profile.setImageBitmap(mIcon1);
//    }
//
//    public UploadedPhoto() throws MalformedURLException {
//    } //need to figure out this shit also

//    ImageView image = (ImageView) findViewById(R.drawable.test_photo);
//    Bitmap bMap = BitmapFactory.decodeFile("/sdcard/test2.png");
//        image.setImageBitmap(bMap);

    private String sdCardFilePath;
    private Bitmap bitmap;
    private byte[] byteArray;
    private YuvImage yuvImage;

   public byte[] toByteArrayFunc(Bitmap rawBitamap) {

       try{
           ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
           rawBitamap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
           byteArray = outputStream.toByteArray();

       }catch (Exception e) { e.printStackTrace(); }

       return byteArray;
   }

   public Bitmap createBitMap(String sdFilePath){
       File file = new File(sdFilePath);
       bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
       return bitmap;
   }

   // this function might be useless
//   public YuvImage toYuvImageFunc(byte[] byteArr, int width, int height, int[] strides) {
//       yuvImage = new YuvImage(byteArr, YUY2, width, height, strides);
//       return  yuvImage;
//   }

   public UploadedPhoto(String sdCardFilePath) {
       this.sdCardFilePath = sdCardFilePath;
       bitmap = createBitMap(sdCardFilePath);
       byteArray = toByteArrayFunc(bitmap);
       yuvImage = new YuvImage(byteArray, YUV_420_888, 300, 300, null);
       // supposedly, strides are something to do with the border around the photo. but like can be null so gang
   }

}

