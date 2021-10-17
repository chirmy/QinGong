package com.example.qingong2.signin;

import android.Manifest;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.viewpager.widget.ViewPager;

import com.example.qingong2.LocationActivity;
import com.example.qingong2.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener {

    public static final int TAKE_PHOTO = 1;

    public static final int CHOOSE_PHOTO = 2;

    private Button locationBtn;

    private Button photoBtn;

    private Button chooseFromAlbumBtn;

    private Uri imageUri;

    private ImageView picture;

    private String TAG = "SignInActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        locationBtn = findViewById(R.id.btn_signIn_location);
        photoBtn = findViewById(R.id.btn_signIn_photo);
        chooseFromAlbumBtn = findViewById(R.id.btn_signIn_chooseFromAlbum);
        picture = findViewById(R.id.imgView_photo);

        locationBtn.setOnClickListener(this);
        photoBtn.setOnClickListener(this);
        chooseFromAlbumBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_signIn_location:{//点击定位
                Intent intent = new Intent(SignInActivity.this, LocationActivity.class);
                startActivity(intent);
                break;
            }

            case R.id.btn_signIn_photo:{//点击拍照
                //创建File对象，用于存储拍照后的图片 并把图片存放在当前应用的应用关联目录下
                File outputImage  = new File(getExternalCacheDir(),"output_image.jpg");
                try{
                    if(outputImage.exists()){
                        outputImage.delete();
                    }
                    outputImage.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if(Build.VERSION.SDK_INT >= 24){
                    //FileProvider内容提供器
                    imageUri = FileProvider.getUriForFile(SignInActivity.this,"com.example.qingong2.fileprovider",outputImage);
                }
                else{
                    //转换成Uri对象
                    imageUri = Uri.fromFile(outputImage);
                }
                //启动相机
                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(intent, TAKE_PHOTO);
                break;
            }

            case R.id.btn_signIn_chooseFromAlbum:{//点击从相册选择
                if(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                    //动态申请危险权限（表示同时授权程序对SD卡读和写的能力）
                    ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},2);
                }else{
                    openAlbum();
                }
            }

        }
    }

    private void openAlbum(){
        Log.d(TAG,"openAlbum");
        Intent intent=new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent,CHOOSE_PHOTO);//打开相册程序选择照片
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {

            case TAKE_PHOTO:
                if (resultCode == RESULT_OK) {
                    try {
                        //将拍摄的图片显示出来 将照片解析成Bitmap对象
                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                        picture.setImageBitmap(bitmap);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;

            case CHOOSE_PHOTO:
                Log.d(TAG, "onActivityResult" + String.valueOf(requestCode));
                if(resultCode == -1){
                    //为了兼容新老版本手机
                    // 判断手机系统版本号
                    if(Build.VERSION.SDK_INT >=19){
                        Log.d(TAG,"444");
                        //4.4及以上系统
                        handleImageOnKitKat(data);
                    }else{
                        Log.d(TAG,"400");
                        //4.4以下系统
                        handleImageBeforeKitKat(data);
                    }
                }
                break;

            default:
                break;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void handleImageOnKitKat(Intent data) {

        String imagePath=null;
        Uri uri=data.getData();
        if(DocumentsContract.isDocumentUri(this,uri)){
            //document类型的Uri处理方式
            String docId=DocumentsContract.getDocumentId(uri);
            if("com.android.providers.media.documents".equals(uri.getAuthority())){
                String id=docId.split(":")[1];
                String seletion=MediaStore.Images.Media._ID+"="+id;
                imagePath=getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,seletion);
            }else if("com.android.providers.downloads.documents".equals(uri.getAuthority())){
                Uri contentUri= ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"),Long.valueOf(docId));
                imagePath=getImagePath(contentUri,null);
            }
        }else if("content".equalsIgnoreCase(uri.getScheme())){
            //content类型的Uri处理方式
            imagePath=getImagePath(uri,null);
        }else if("file".equalsIgnoreCase(uri.getScheme())){
            //file类型的Uri，直接获取路径即可
            imagePath=uri.getPath();
        }
        displayImage(imagePath);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void handleImageBeforeKitKat(Intent data) {
        Uri uri=data.getData();
        String imagePath=getImagePath(uri,null);
        displayImage(imagePath);
    }

    


    private void displayImage(String imagePath) {
        if(imagePath!=null){
            Bitmap bitmap=BitmapFactory.decodeFile(imagePath);
           // String strBitmap = bitmap.toString();
            Log.d(TAG,"before setImageBitmap");
            picture.setImageBitmap(bitmap);
            Log.d(TAG,"after setImageBitmap");
            Toast.makeText(this,"success",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this,"failed to get image",Toast.LENGTH_SHORT).show();
        }

    }

    private String getImagePath(Uri uri, String selection) {
        String path=null;
        //获取真实的图片路径
        Cursor cursor=getContentResolver().query(uri,null,selection,null,null);
        if(cursor!=null){
            if(cursor.moveToFirst()){
                path=cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 1:
            {
                if(grantResults.length > 0 && grantResults[0] ==PackageManager.PERMISSION_GRANTED){
                    openAlbum();
                }
                else{
                    Toast.makeText(this,"you denied the permission",Toast.LENGTH_LONG).show();
                }
                break;
            }
            default:

        }
    }
}
