package com.bofsoft.sdk.widget.phone;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;

public class PhotoHandler extends Activity {
    private final String IMAGE_FILE_LOCATION = "laioCameraTemp";
    private final String IMAGE_FILE_CROP = "laioCameraCrop";
    private File tempFile;
    private Uri imageUri;
    private File tempCrop;
    private Uri cropUri;
    private final int PHOTO_REQUEST_CAREMA = 01;
    private final int PHOTO_REQUEST_ALBUM = 02;
    private final int PHOTO_REQUEST_CUT = 03;

    private Intent cameraIntent;
    private Intent albumIntent;
    private int width;
    private int height;
    private int aspectX;
    private int aspectY;
    private boolean crop;
    private int actionType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (null == imageUri) {
            tempFile = new File(Environment.getExternalStorageDirectory(), IMAGE_FILE_LOCATION);
            imageUri = Uri.fromFile(tempFile);
        }

        width = getIntent().getIntExtra("width", 250);
        height = getIntent().getIntExtra("height", 250);
        aspectX = getIntent().getIntExtra("aspectX", 0);
        aspectY = getIntent().getIntExtra("aspectY", 0);
        crop = getIntent().getBooleanExtra("crop", true);
        actionType = getIntent().getIntExtra("actionType", 0);

        switch (actionType) {
            case 1:
                if (null == cameraIntent) {
                    cameraIntent = new Intent("android.media.action.IMAGE_CAPTURE");
                    if (isCanUseSdCard()) {
                        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                    }
                }
                this.startActivityForResult(cameraIntent, PHOTO_REQUEST_CAREMA);
                break;
            case 2:
                if (null == albumIntent) {
                    albumIntent = new Intent(Intent.ACTION_PICK);
                    albumIntent.setType("image/*");
                }
                this.startActivityForResult(albumIntent, PHOTO_REQUEST_ALBUM);
                break;
            default:
                break;
        }
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) {
            finish();
            return;
        }
        Photo photo = Photo.getInstance();
        switch (requestCode) {
            case PHOTO_REQUEST_CAREMA:
                if (isCanUseSdCard()) {
                    if (crop) {
                        crop(imageUri);
                    } else {
                        ContentResolver cr = this.getContentResolver();
                        try {
                            Bitmap bitmap = BitmapFactory.decodeStream(cr.openInputStream(imageUri));
                            photo.iphoto.complete(bitmap);
                        } catch (FileNotFoundException e) {
                        }
                        finish();
                    }
                } else {
                    Toast.makeText(this, "SD卡无法使用！", Toast.LENGTH_LONG).show();
                    finish();
                }
                break;
            case PHOTO_REQUEST_ALBUM:
                if (data != null) {
                    Uri uri = data.getData();
                    if (crop) {
                        crop(uri);
                    } else {
                        ContentResolver cr = this.getContentResolver();
                        try {
                            Bitmap bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));
                            photo.iphoto.complete(bitmap);
                        } catch (FileNotFoundException e) {
                        }
                        finish();
                    }
                } else {
                    finish();
                }
                break;
            case PHOTO_REQUEST_CUT:
//        if (data != null) {
//          Bitmap bitmap = data.getParcelableExtra("data");
//          photo.iphoto.complete(bitmap);
//        }
                try {
                    Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(cropUri));
                    photo.iphoto.complete(bitmap);
                } catch (Exception e1) {
                    Toast.makeText(this, "剪切失败", Toast.LENGTH_LONG).show();
                }
                finish();
                try {
                    tempFile.delete();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            default:
                finish();
                break;
        }
    }

    private void crop(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        if (aspectX > 0 || aspectY > 0) {
            intent.putExtra("aspectX", aspectX);
            intent.putExtra("aspectY", aspectY);
        }
        intent.putExtra("outputX", width);
        intent.putExtra("outputY", height);

        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true);

//    intent.putExtra("return-data", true);
        //解决剪切后不能返回问题
        if (null == cropUri) {
            tempCrop = new File(Environment.getExternalStorageDirectory(), IMAGE_FILE_LOCATION);
            cropUri = Uri.fromFile(tempCrop);
        }
        intent.putExtra(MediaStore.EXTRA_OUTPUT, cropUri);
        this.startActivityForResult(intent, PHOTO_REQUEST_CUT);
    }

    private boolean isCanUseSdCard() {
        try {
            return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
