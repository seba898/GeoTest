package com.example.cunts;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.ImageFormat;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.media.Image;
import android.os.Environment;
import android.util.Log;
import android.widget.ImageView;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.nio.ByteBuffer;

import static android.graphics.ImageFormat.YUV_420_888;
import static android.graphics.ImageFormat.YUV_444_888;
import static android.graphics.ImageFormat.YUY2;


public class KotlinSucks {



    public Bitmap uglies() {
        String filepath = "/storage/emulated/0/Android/media/com.example.cunts/cunts/2020-07-17-18-29-12-951.jpg";
//        File sd = Environment.getRootDirectory();
        File imageFile = new File(filepath);
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        Bitmap image = BitmapFactory.decodeFile(imageFile.getAbsolutePath(), bmOptions);
        return image;
    }


//    public YuvImage fuglies(Bitmap z ){
//
//        int mWidth = z.getWidth();
//        int mHeight = z.getHeight();
//
//        int[] mIntArray = new int[mWidth * mHeight];
//
//        // Copy pixel data from the Bitmap into the 'intArray' array
//
//
//        ByteArrayOutputStream stream = new ByteArrayOutputStream();
//        z.compress(Bitmap.CompressFormat.JPEG, 100, stream);
//        byte[] bitMapData = stream.toByteArray();
//
//
//    }



    public Bitmap toBitmap(Image image) {
        Image.Plane[] planes = image.getPlanes();
        ByteBuffer yBuffer = planes[0].getBuffer();
        ByteBuffer uBuffer = planes[1].getBuffer();
        ByteBuffer vBuffer = planes[2].getBuffer();

        int ySize = yBuffer.remaining();
        int uSize = uBuffer.remaining();
        int vSize = vBuffer.remaining();

        byte[] nv21 = new byte[ySize + uSize + vSize];
        //U and V are swapped
        yBuffer.get(nv21, 0, ySize);
        vBuffer.get(nv21, ySize, vSize);
        uBuffer.get(nv21, ySize + vSize, uSize);

        YuvImage yuvImage = new YuvImage(nv21, ImageFormat.NV21, image.getWidth(), image.getHeight(), null);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        yuvImage.compressToJpeg(new Rect(0, 0, yuvImage.getWidth(), yuvImage.getHeight()), 75, out);

        byte[] imageBytes = out.toByteArray();
        return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
    }


 /*
 0
 1
 2
 3
 4
 5
 6
 7
 8
 9
 10
 11
  */




    public int[] kotlinSucksAss(Bitmap a){
        int rtl = 0;//red top left
        int gtl = 0;
        int btl = 0;

        int rtr = 0;//red top right
        int gtr = 0;
        int btr = 0;

        int rbl = 0;//red bottom left
        int gbl = 0;
        int bbl = 0;

        int rbr = 0;//red bottom right
        int gbr = 0;
        int bbr = 0;



        for (int i = 0; i < 240; i++) { ///length vertical (BITMAP IS 480 TALL AND 640 WIDE)
            for (int j = 0; j < 320; j++) { //width horizontal

                //have to offest the index based on what quadrant of the photo we are looking at
                //attempt is to get slice each color into four separate arrays  (2d quadrant)

                int pixelTopLeft = a.getPixel(i, j);
                  rtl += Color.red(pixelTopLeft);

                Log.v("r=",  Integer.toString(Color.red(pixelTopLeft)));






//                rtl += Color.red(pixelTopLeft);
//                gtl += Color.green(pixelTopLeft);
//                btl += Color.blue(pixelTopLeft);


//                int pixelTopRight = a.getPixel(i, j);
//                rtr += Color.red(pixelTopRight);
//                gtr += Color.green(pixelTopRight);
//                btr += Color.blue(pixelTopRight);
//
//                int pixelBottomLeft = a.getPixel(i+239, j);
//                rbl += Color.red(pixelBottomLeft);
//                gbl += Color.green(pixelBottomLeft);
//                bbl += Color.blue(pixelBottomLeft);
//
//                int pixelBottomRight = a.getPixel(i+239, j+319);
//                rbr += Color.red(pixelBottomRight);
//                gbr += Color.green(pixelBottomRight);
//                bbr += Color.blue(pixelBottomRight);



            }
        }//end of nested for loops

        int[] avgs = new int[12];

        avgs[0] = (int)(rtl/76800);
//        avgs[1] = (int)(rtr/76800);
//        avgs[2] = (int)(rbl/76800);
//        avgs[3] = (int)(rbr/76800);

//        avgs[4] = (int)(gtl/76800);
//        avgs[5] = (int)(gtr/76800);
//        avgs[6] = (int)(gbl/76800);
//        avgs[7] = (int)(gbr/76800);

//        avgs[8] = (int)(btl/76800);
//        avgs[9] = (int)(btr/76800);
//        avgs[10] = (int)(bbl/76800);
//        avgs[11] = (int)(bbr/76800);




        return avgs;
    }




}
