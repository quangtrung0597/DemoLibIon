package com.example.quang.demolibion;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.ProgressCallback;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class MainActivity extends AppCompatActivity {

    ImageView imageView;

    public static final String[] PERMISSION_LIST = {
            android.Manifest.permission.INTERNET,
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE
    } ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            for (String permission: PERMISSION_LIST){
                if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED){
                    requestPermissions(PERMISSION_LIST,0);
                    return;
                }
            }
        }

        downloadFile();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        for (int result: grantResults){
            if (result == PackageManager.PERMISSION_DENIED){
                finish();
                return;
            }
        }
        downloadFile();
    }


    public void downloadFile() {

        String path = Environment.getExternalStorageDirectory().getPath()+"/hoho.jpg";


        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setTitle("Please wait");
        dialog.setMessage("Currently downloading " + "...");
        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        dialog.show();

        Ion.with(this)
                .load("https://images6.alphacoders.com/606/thumb-1920-606263.jpg")
                .progressDialog(dialog)
                .write(new File(path))
                .setCallback(new FutureCallback<File>() {
                    @Override
                    public void onCompleted(Exception e, File file) {
                        dialog.hide();
                    }
                });
    }













//    private static File createPngFile(String name) {
//        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "/Pictures");
//        if (!file.mkdirs()) {
//        } else {
//            file.mkdirs();
//        }
//        return new File(file.getAbsolutePath(), name);
//    }
//
//    public static File createPngFileFromBitmap(String name, Bitmap bitmap) throws FileNotFoundException {
//        File picture = createPngFile(name);
//        OutputStream stream = new FileOutputStream(picture);
//        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
//        return picture;
//    }
//
//    public static void addImageToGallery(final String filePath, final Context context) {
//        ContentValues values = new ContentValues();
//        values.put(MediaStore.Images.Media.MIME_TYPE, "image/png");
//        values.put(MediaStore.MediaColumns.DATA, filePath);
//        context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
//    }
//
//    public static void saveBitmap(Context context, Bitmap original, String name) {
//        try {
//            File imageFile = createPngFileFromBitmap(name, original);
//            addImageToGallery(imageFile.getAbsolutePath(), context);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//
//    }




}
