package com.example.cameraxversion2;

import android.content.Intent;
import android.media.Image;

class UploadGoodPhotoJava {

    int image = R.drawable.test_photo;

    Intent intent = new Intent(Intent.ACTION_PICK);
    intent.setType("image/*");



}
