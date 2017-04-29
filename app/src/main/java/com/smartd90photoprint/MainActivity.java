package com.smartd90photoprint;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private LinearLayout mImage, mPrint, mSettings;
    private int PICK_IMAGE_REQUEST = 1;
    private TouchImageView mPhoto;
    private ProgressBar mProgress;
    private Bitmap mBitmap;
    private  SettingFragment mSettingFragment = new SettingFragment();
    private File mImageFile;
    private FtpItem mFtpitem = new FtpItem(
            "192.168.110.1",
            "1212",
            "dps",
            "dps",
            "10x15 Portrait"  );
    private  static SharedPreferences setup;
    private static final int REQUEST_ACCESS_WIFI_STATE_PERMISSION = 1;
    private static final int REQUEST_CHANGE_WIFI_STATE_PERMISSION = 1;
    private static final int REQUEST_ACCESS_COARSE_LOCATION_PERMISSION = 1;
    private static final int REQUEST_ACCESS_NETWORK_STATE_PERMISSION = 1;
    private static final int REQUEST_READ_EXTERNAL_STORAGE = 1;
    private static final int REQUEST_MANAGE_DOCUMENTS = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setup = getSharedPreferences("config_pref", MODE_PRIVATE);
        mFtpitem.setFtphost(setup.getString("FTPHOST","192.168.110.1"));
        mFtpitem.setFtpport(setup.getString("FTPPORT","1212"));
        mFtpitem.setFtpuser(setup.getString("FTPUSER","dps"));
        mFtpitem.setFtppass(setup.getString("FTPPASS","dps"));
        mFtpitem.setHotfolder(setup.getString("HOTFOLDER","10x15 Portrait"));

        Intent getimage = getIntent();
        String accion = getimage.getAction();
        String type = getimage.getType();

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_NETWORK_STATE)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_NETWORK_STATE)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_NETWORK_STATE},
                        REQUEST_ACCESS_NETWORK_STATE_PERMISSION );

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_WIFI_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_WIFI_STATE)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_WIFI_STATE},
                        REQUEST_ACCESS_WIFI_STATE_PERMISSION );

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CHANGE_WIFI_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.CHANGE_WIFI_STATE)) {
                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CHANGE_WIFI_STATE},
                        REQUEST_CHANGE_WIFI_STATE_PERMISSION );
                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION)) {
                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                        REQUEST_ACCESS_COARSE_LOCATION_PERMISSION );
                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
                // Explain to the user why we need to read the contacts
            }
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    REQUEST_READ_EXTERNAL_STORAGE);
            // MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE is an
            // app-defined int constant that should be quite unique
        }
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.MANAGE_DOCUMENTS)
                != PackageManager.PERMISSION_GRANTED) {
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.MANAGE_DOCUMENTS)) {
                // Explain to the user why we need to read the contacts
            }
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.MANAGE_DOCUMENTS},
                    REQUEST_MANAGE_DOCUMENTS);
            // MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE is an
            // app-defined int constant that should be quite unique
        }
        mPhoto = (TouchImageView)findViewById(R.id.photo);
        mImage = (LinearLayout) findViewById(R.id.image);
        mImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                Uri uri = Uri.parse(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM ).getPath());
                intent.setDataAndType(uri,"image/*");
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
            }
        });
        mPrint = (LinearLayout) findViewById(R.id.print);
        mPrint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mImageFile!=null)
                new FtpFileTransfer(MainActivity.this, new PrintParam(
                        mFtpitem.getFtphost(),
                        mFtpitem.getFtpport(),
                        mFtpitem.getFtpuser(),
                        mFtpitem.getFtppass(),
                        mImageFile.getAbsolutePath(),
                        mFtpitem.getHotfolder()));
                else
                    Toast.makeText(MainActivity.this, getString(R.string.noimage),
                            Toast.LENGTH_SHORT).show();
            }
        });
        mSettings = (LinearLayout) findViewById(R.id.setting);
        mSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPrint.setVisibility(View.GONE);
                mSettings.setVisibility(View.GONE);
                mImage.setVisibility(View.GONE);
                mSettingFragment = new SettingFragment();
                Bundle argsSettingFragment = new Bundle();
                argsSettingFragment.putString("FTPHOST", mFtpitem.getFtphost());
                argsSettingFragment.putString("FTPPORT", mFtpitem.getFtpport());
                argsSettingFragment.putString("FTPUSER", mFtpitem.getFtpuser());
                argsSettingFragment.putString("FTPPASS", mFtpitem.getFtppass());
                argsSettingFragment.putString("HOTFOLDER", mFtpitem.getHotfolder());
                mSettingFragment.setArguments(argsSettingFragment);;
                getFragmentManager().beginTransaction()
                        .setCustomAnimations(R.animator.slide_in_right, R.animator.slide_out_left)
                        .replace(R.id.settingcontainer, mSettingFragment)
                        .show(mSettingFragment).commit();
            }
        });
        mProgress = (ProgressBar)findViewById(R.id.progress);
        mProgress.setVisibility(View.GONE);

        if (  Intent.ACTION_SEND.equals(accion )  && type != null)  {
            if (type.startsWith("image/")) {
                getimage(getimage); // Handle single image being sent
            }
        }
        else if (  Intent.ACTION_VIEW.equals(accion)  && type != null) {
            if (type.startsWith("image/")) {
                getimage(getimage); // Handle single image being sent
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            try{
                InputStream iStream =   this.getContentResolver().openInputStream(data.getData());
                byte[] bitmapdata = getBytes(iStream);
                mImageFile = getOutputMediaFile((new File(data.getData().toString()).getName()));//.getOutputMediaFile();
                if (mImageFile != null) {
                    try {
                        FileOutputStream fos = new FileOutputStream(mImageFile);
                        fos.write(bitmapdata);
                        fos.flush();
                        fos.close();
                    } catch (Exception e) {

                    }
                    Glide
                            .with(this)
                            .load(mImageFile)
                            .asBitmap()
                            .toBytes(Bitmap.CompressFormat.PNG, 100)
                            .atMost()
                            .override(2048, 2048)
                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                            .skipMemoryCache(true)
                            .into(new SimpleTarget<byte[]>() {
                                @Override public void onLoadStarted(Drawable ignore) {
                                    mImage.setVisibility(View.GONE);
                                    mSettings.setVisibility(View.GONE);
                                    mPrint.setVisibility(View.GONE);
                                    mPhoto.setImageResource(android.R.color.transparent);
                                    mProgress.setVisibility(View.VISIBLE);
                                }
                                @Override public void onResourceReady(byte[] resource, GlideAnimation<? super byte[]> ignore) {
                                    mBitmap = BitmapFactory.decodeByteArray(resource, 0, resource.length);
                                    mProgress.setVisibility(View.GONE);
                                    mPhoto.setImageBitmap(mBitmap);
                                    mPhoto.setMaxZoom(4f);
                                    mSettings.setVisibility(View.VISIBLE);
                                    mImage.setVisibility(View.VISIBLE);
                                    mPrint.setVisibility(View.VISIBLE);
                                }
                                @Override public void onLoadFailed(Exception ex, Drawable ignore) {
                                }
                            })
                    ;
               }
            }
            catch(Exception e)
            {
            }
        }
    }

    private static File getOutputMediaFile(String filename){
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "SmartD90");
        if (!mediaStorageDir.exists()){
            if (!mediaStorageDir.mkdirs()){
                return null;
            }
        }
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        return new File(mediaStorageDir.getPath() + File.separator +
                filename+"_"+timeStamp+"_"+".png");
    }

    public byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];
        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }

    public void savesettings( FtpItem ftpitem)
    {
        SharedPreferences.Editor editasetup = setup.edit();
        editasetup.putString("FTPHOST", ftpitem.getFtphost());
        editasetup.putString("FTPPORT", ftpitem.getFtpport());
        editasetup.putString("FTPUSER", ftpitem.getFtpuser());
        editasetup.putString("FTPPASS", ftpitem.getFtppass());
        editasetup.putString("HOTFOLDERPOLAROID", ftpitem.getHotfolder());
        editasetup.commit();
        this.mFtpitem = ftpitem;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mSettingFragment.isVisible()  && !mSettingFragment.isRemoving()) {
                mPrint.setVisibility(View.VISIBLE);
                mSettings.setVisibility(View.VISIBLE);
                mImage.setVisibility(View.VISIBLE);
                getFragmentManager().beginTransaction()
                        .setCustomAnimations(R.animator.slide_in_right, R.animator.slide_out_left)
                        .remove(mSettingFragment).commit();
            }
              else {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle(getString(R.string.exitapp))
                        .setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        })
                        .setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .show();
            }
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void getimage(Intent intent) {

        Uri imageUri = intent.getParcelableExtra(Intent.EXTRA_STREAM);
        mImageFile = new File(imageUri.toString());
        Glide
                .with(this)
                .load(imageUri)
                .asBitmap()
                .toBytes(Bitmap.CompressFormat.PNG, 100)
                .atMost()
                .override(2048, 2048)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(new SimpleTarget<byte[]>() {
                    @Override public void onLoadStarted(Drawable ignore) {
                        mImage.setVisibility(View.GONE);
                        mSettings.setVisibility(View.GONE);
                        mPrint.setVisibility(View.GONE);
                        mPhoto.setImageResource(android.R.color.transparent);
                        mProgress.setVisibility(View.VISIBLE);
                    }
                    @Override public void onResourceReady(byte[] resource, GlideAnimation<? super byte[]> ignore) {
                        mBitmap = BitmapFactory.decodeByteArray(resource, 0, resource.length);
                        mProgress.setVisibility(View.GONE);
                        mPhoto.setImageBitmap(mBitmap);
                        mPhoto.setMaxZoom(4f);
                        mSettings.setVisibility(View.VISIBLE);
                        mImage.setVisibility(View.VISIBLE);
                        mPrint.setVisibility(View.VISIBLE);
                    }
                    @Override public void onLoadFailed(Exception ex, Drawable ignore) {
                    }
                });
    }

}
