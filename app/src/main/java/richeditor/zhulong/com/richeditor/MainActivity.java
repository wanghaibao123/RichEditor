package richeditor.zhulong.com.richeditor;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import richeditor.zhulong.com.richeditorlibrary.RichEditorControlBar;
import richeditor.zhulong.com.richeditorlibrary.RichEditorWebView;

public class MainActivity extends AppCompatActivity {
    private RichEditorWebView rew_edit;
    private RichEditorControlBar rew_richrditbar;
    private static final int IMAGE=10001;//权限请求
    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS=10002;//图片
    private InputMethodManager imm;//软键盘管理器
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        //权限请求
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST_READ_CONTACTS);
        }else{
        }
        initView();
    }

    public void initView(){
        rew_edit=findViewById(R.id.rew_edit);
        rew_richrditbar=findViewById(R.id.rew_richrditbar);
        rew_richrditbar.setRichEditorWebView(rew_edit);
        rew_edit.setEditorFontSize(15);
        rew_edit.setPadding(10, 10, 10, 50);
        rew_edit.setPlaceholder("请输入文本信息");
        rew_richrditbar.setCallBack(new RichEditorControlBar.richEditorCallBack() {
            @Override
            public void onClickImg() {
                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, IMAGE);

            }
        });

        rew_edit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    imm.toggleSoftInput(0, InputMethodManager.SHOW_FORCED);
                    rew_richrditbar.setVisibility(View.VISIBLE);
                } else {
                    imm.hideSoftInputFromWindow(rew_edit.getWindowToken(), 0); //强制隐藏键盘
                    rew_richrditbar.setVisibility(View.INVISIBLE);
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //获取图片路径
        if (requestCode == IMAGE && resultCode == RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            String[] filePathColumns = {MediaStore.Images.Media.DATA};
            Cursor c = getContentResolver().query(selectedImage, filePathColumns, null, null, null);
            c.moveToFirst();
            int columnIndex = c.getColumnIndex(filePathColumns[0]);
            String imagePath = c.getString(columnIndex);
            rew_richrditbar.insertImg(imagePath);
            c.close();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_CONTACTS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
        }
    }


}
