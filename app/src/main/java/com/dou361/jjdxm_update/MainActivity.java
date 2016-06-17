package com.dou361.jjdxm_update;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.dou361.update.UpdateHelper;
import com.dou361.update.listener.UpdateListener;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button update;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        update = (Button) findViewById(R.id.update);
        update.setOnClickListener(this);
        UpdateHelper.getInstance()
                .check(MainActivity.this);
    }

    @Override
    public void onClick(View v) {
        UpdateHelper.getInstance()
                .setUpdateType(UpdateHelper.UpdateType.checkupdate)
                .setUpdateListener(new UpdateListener() {
                    @Override
                    public void noUpdate() {
                        Toast.makeText(mContext, "已经是最新版本了", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onCheckError(int code, String errorMsg) {
                        Toast.makeText(mContext, "检测更新失败：" + errorMsg, Toast.LENGTH_LONG).show();
                    }
                })
                .check(MainActivity.this);
    }
}
