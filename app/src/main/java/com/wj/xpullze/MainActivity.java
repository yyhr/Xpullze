package com.wj.xpullze;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.wj.xpullze.com.wj.xpullze.utils.ScreenUtils;
import com.wj.xpullze.com.wj.xpullze.utils.com.wj.xpullze.adapter.GridPicListAdapter;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String IMAGE_TYPE = "image/*";
    private static final int IMAGE_RESULT = 100;
    private static final int CAMERA_RESULT = 200;
    private static String TMP_IMAGE_PATH;
    private TextView tv_activity_main_type_select;
    private View mPopupView;
    private GridView mGridView;
    private View mTvSelectTyp2;
    private View mTvSelectTyp3;
    private View mTvSelectTyp4;
    private PopupWindow mPopupWindow;
    private ArrayList<Bitmap> mPicList;
    private int[] mResPicId;
    private String[] mCustomItems;
    private int mType;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        initViews();

    }

    private void initData() {
        mCustomItems = new String[]{"本地图册", "相机"};
        TMP_IMAGE_PATH = Environment.getExternalStorageDirectory().getPath() + "/temp.png";
        mPicList = new ArrayList<Bitmap>();
        mResPicId = new int[]{
                R.drawable.pic1,
                R.drawable.pic2,
                R.drawable.pic3,
                R.drawable.pic4,
                R.drawable.pic5,
                R.drawable.pic6,
                R.drawable.pic7,
                R.drawable.pic8,
                R.drawable.pic9,
                R.drawable.pic10,
                R.drawable.pic11,
                R.drawable.pic12,
                R.drawable.pic13,
                R.drawable.pic14,
                R.drawable.pic15,
                R.mipmap.ic_launcher
        };
        Bitmap[] bitmaps = new Bitmap[mResPicId.length];
        for (int i = 0; i < bitmaps.length; ++i) {
            bitmaps[i] = BitmapFactory.decodeResource(getResources(), mResPicId[i]);
            mPicList.add(bitmaps[i]);
        }
    }

    private void initViews() {
        tv_activity_main_type_select = (TextView) findViewById(R.id.tv_activity_main_type_select);
        tv_activity_main_type_select.setOnClickListener(this);

        mGridView = (GridView) findViewById(R.id.gv_activity_main_pic_list);
        mGridView.setAdapter(new GridPicListAdapter(this, mPicList));
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == mPicList.size() - 1) {
                    //选择本地图库或相机
                    showDialogCustom();
                } else {
                    Intent intent = new Intent(MainActivity.this, PullzeMainActivity.class);
                    intent.putExtra("mType", mType);
                    intent.putExtra("picSelectID", mResPicId[position]);
                    startActivity(intent);
                }
            }
        });

        mPopupView = LayoutInflater.from(this).inflate(R.layout.activity_main_type_select, null);

        mTvSelectTyp2 = mPopupView.findViewById(R.id.tv_type_select_2);
        mTvSelectTyp3 = mPopupView.findViewById(R.id.tv_type_select_3);
        mTvSelectTyp4 = mPopupView.findViewById(R.id.tv_type_select_4);

        mTvSelectTyp2.setOnClickListener(this);
        mTvSelectTyp3.setOnClickListener(this);
        mTvSelectTyp4.setOnClickListener(this);
    }

    private void showDialogCustom() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setTitle("选择");
        builder.setItems(mCustomItems, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (0 == which) {
                    //本地图册
                    Intent intent = new Intent(Intent.ACTION_PICK, null);
                    intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                            IMAGE_TYPE);//IMAGE_TYPE="image/*"
                    startActivityForResult(intent, IMAGE_RESULT);
                } else if (1 == which) {
                    //相机
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    Uri uri = Uri.fromFile(new File(TMP_IMAGE_PATH));
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                    startActivityForResult(intent, CAMERA_RESULT);
                }
            }
        });
        builder.create().show();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tv_activity_main_type_select) {
            showPopup(v);
        } else if (v.getId() == R.id.tv_type_select_2) {
            mType = 2;
            tv_activity_main_type_select.setText("2X2");
            mPopupWindow.dismiss();
        } else if (v.getId() == R.id.tv_type_select_3) {
            mType = 3;
            tv_activity_main_type_select.setText("3X3");
            mPopupWindow.dismiss();
        } else if (v.getId() == R.id.tv_type_select_4) {
            mType = 4;
            tv_activity_main_type_select.setText("4X4");
            mPopupWindow.dismiss();
        }
    }

    private void showPopup(View v) {
        int density = (int) ScreenUtils.getDeviceDensity(this);
        mPopupWindow = new PopupWindow(mPopupView, 200 * (int) ScreenUtils.getDeviceDensity(this),
                50 * (int) ScreenUtils.getDeviceDensity(this));
        //可以获取焦点则可以点击按钮
        mPopupWindow.setFocusable(true);
        //在外面点击的时候可以消失
        mPopupWindow.setOutsideTouchable(true);
        //没有背景最好设置一个背景不然可能出现一些奇怪的问题
        Drawable bk_drawable = new ColorDrawable(Color.TRANSPARENT);
        mPopupWindow.setBackgroundDrawable(bk_drawable);
        int[] locations = new int[2];
        v.getLocationOnScreen(locations);
        int x = (v.getWidth() - 200 * density) / 2;
        mPopupWindow.showAtLocation(v, Gravity.NO_GRAVITY, locations[0] + x,
                locations[1] + v.getHeight());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == IMAGE_RESULT && data != null) {
                //相册 
                Cursor cursor = this.getContentResolver().query(data.getData(),
                        null, null, null, null);
                cursor.moveToFirst();
                String imagePath = cursor.getString(cursor.getColumnIndex("_data"));
                Log.d(TAG, "onActivityResult: " + imagePath);
                Intent intent = new Intent(this, PullzeMainActivity.class);
                intent.putExtra("picPath", imagePath);
                intent.putExtra("mType", mType);
                cursor.close();
                startActivity(intent);

            } else if (requestCode == CAMERA_RESULT) {
                //相机
                Intent intent = new Intent(this, PullzeMainActivity.class);
                intent.putExtra("picPath", TMP_IMAGE_PATH);
                intent.putExtra("mType", mType);
                startActivity(intent);
            }
        }
    }
}
