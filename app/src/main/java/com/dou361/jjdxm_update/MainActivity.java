package com.dou361.jjdxm_update;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.dou361.update.UpdateHelper;
import com.dou361.update.listener.ForceListener;
import com.dou361.update.listener.UpdateListener;
import com.dou361.update.type.UpdateType;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button autoUpdate;
    private Button update;
    private Context mContext;
    /**
     * 自动更新和手动更新放在一起监听方法会相互影响，这里做下过滤
     * 一般自动更新放到MainActivity里面的，手动检测更新放到设置里面的
     */
    private boolean isAutoUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        autoUpdate = (Button) findViewById(R.id.btn_auto_update);
        update = (Button) findViewById(R.id.btn_update);
        autoUpdate.setOnClickListener(this);
        update.setOnClickListener(this);
        autoUpdate();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_auto_update) {
            autoUpdate();
        } else if (v.getId() == R.id.btn_update) {
            update();
        }
    }

    /**
     * 自动检测更新
     */
    private void autoUpdate() {
        isAutoUpdate = true;
        UpdateHelper.getInstance()
                .setUpdateType(UpdateType.autoupdate)
                .setForceListener(new ForceListener() {
                    @Override
                    public void onUserCancel(boolean force) {
                        if (force) {
                            //退出应用
                            finish();
                        }
                    }
                })
                .check(MainActivity.this);
    }

    /**
     * 手动检测更新
     */
    private void update() {
        isAutoUpdate = false;
        UpdateHelper.getInstance()
                .setUpdateType(UpdateType.checkupdate)
                .setUpdateListener(new UpdateListener() {
                    @Override
                    public void noUpdate() {
                        if (!isAutoUpdate) {
                            Toast.makeText(mContext, "已经是最新版本了", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onCheckError(int code, String errorMsg) {
                        if (!isAutoUpdate) {
                            Toast.makeText(mContext, "检测更新失败：" + errorMsg, Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onUserCancel() {
                        if (!isAutoUpdate) {
                            Toast.makeText(mContext, "用户取消", Toast.LENGTH_LONG).show();
                        }
                    }
                })
                .check(MainActivity.this);
    }
}
