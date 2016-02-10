package com.newfeds.icare.helper;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by GT on 1/19/2016.
 */

public class ImageHelper {

    //Will not be used since Glide is the best
    public static void setImage(String imagePath, ImageView imageView){
        L.log("Seting image: "+ imagePath);
        if(imagePath.length()!=0) {
            int targetW = imageView.getWidth();
            int targetH = imageView.getHeight();

            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            bmOptions.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(imagePath, bmOptions);

            int photoW = bmOptions.outWidth;
            int photoH = bmOptions.outHeight;
            if(targetH == 0) targetH = 1;
            if(targetW == 0) targetW = 1;
            int scaleFactor = Math.min(photoW / targetW, photoH / targetH);

            bmOptions.inJustDecodeBounds = false;
            bmOptions.inSampleSize = scaleFactor;
            bmOptions.inPurgeable = true;

            Bitmap bitmap = BitmapFactory.decodeFile(imagePath, bmOptions);
            imageView.setImageBitmap(bitmap);
        }
        L.log("Image Set: "+ imagePath);
    }

    public static String saveImage(Context context,Uri selectedImagePath){
        String imageName = "";
        if(selectedImagePath!=null) {
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            Bitmap profileimage = null;
            try {
                profileimage = MediaStore.Images.Media.getBitmap(context.getContentResolver(), selectedImagePath);
                profileimage.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
                File directory = new File(Environment.getExternalStorageDirectory().toString() + "/icare_image");
                directory.mkdir();
                File destination = new File(directory,
                        System.currentTimeMillis() + ".jpg");

                L.log("Dest: " + destination.toString());
                imageName = destination.toString();
                FileOutputStream fo;
                try {
                    destination.createNewFile();
                    fo = new FileOutputStream(destination);
                    fo.write(bytes.toByteArray());
                    fo.close();
                } catch (FileNotFoundException e) {
                    L.log(e.toString());
                } catch (IOException e) {
                    L.log(e.toString());
                }
            } catch (Exception e) {
                L.log(e.toString());
            }
        }
        return imageName;
    }
}
