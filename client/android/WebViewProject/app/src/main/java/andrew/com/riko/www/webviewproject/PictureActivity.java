package andrew.com.riko.www.webviewproject;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class PictureActivity extends AppCompatActivity {

    ImageView image_view ;
    private static final int RESULT_GET_IMG = 55;
    private static final int REQUEST_CAMERA = 73;
    private static final int REQUEST_CAMERA_PERMISSION = 19;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture);
        image_view = (ImageView) findViewById(R.id.imageView);
    }

    public void takePicture(View v){

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                checkSelfPermission(android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            // 版本大於 android 6.0 且 尚無授權者 , 才需要 requestPermissions
            requestPermissions(new String[]{android.Manifest.permission.CAMERA},REQUEST_CAMERA_PERMISSION);
        }else {
           this.openCameraApp();
        }
    }

    private void openCameraApp(){
        Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(takePicture, REQUEST_CAMERA);//zero can be replaced with any action code
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            // Now user should be able to use camera
            this.openCameraApp();
        }
        else {
            // Your app will not have this permission. Turn off all functions
            // that require this permission or it will force close like your
            // original question
            Toast.makeText(this,"相機的使用沒有授權,無法拍照",Toast.LENGTH_SHORT).show();
        }

    }

    public void getPicture(View v){
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, RESULT_GET_IMG);
    }

    @Override
    protected void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);

        switch (reqCode){
            case RESULT_GET_IMG:this.getPictureFromGallery(resultCode,data);break;
            case REQUEST_CAMERA:this.takePictureFromCamera(resultCode,data);break;
        }

    }

    private void takePictureFromCamera(int resultCode,Intent data){
        if (resultCode == RESULT_OK) {
            this.takePictureFromCamera(data);
        }else {
            Toast.makeText(this, "你的手機沒有相機 或是 此程式被您禁用相機",Toast.LENGTH_LONG).show();
        }
    }

    private void takePictureFromCamera(Intent data){
        // 處理照片 , 要確認他如何儲存 跟 存在哪裡?
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File parentFile = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File destination = new File(parentFile, System.currentTimeMillis() + ".jpg");
        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        image_view.setImageBitmap(thumbnail);
    }

    private void getPictureFromGallery(int resultCode,Intent data){
        if (resultCode == RESULT_OK) {
            this.getPictureFromGallery(data);
        }else {
            Toast.makeText(this, "You haven't picked Image",Toast.LENGTH_LONG).show();
        }
    }

    private void getPictureFromGallery(Intent data){
        try {
            final Uri imageUri = data.getData();
            final InputStream imageStream = getContentResolver().openInputStream(imageUri);
            final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
            // Bitmap resizeBmp = Bitmap.createBitmap(selectedImage,0,0,image_view.getMaxWidth(),image_view.getMaxHeight());
            image_view.setImageBitmap(selectedImage);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show();
        }
    }


}
