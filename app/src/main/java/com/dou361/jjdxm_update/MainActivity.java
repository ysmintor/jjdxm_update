package com.dou361.jjdxm_update;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.dou361.update.UpdateHelper;
import com.dou361.update.UpdateStrategy;
import com.dou361.update.bean.Update;
import com.dou361.update.listener.UpdateListener;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        update = (Button) findViewById(R.id.update);
        update.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        // UpdateBuilder中可设置的配置与UpdateConfig中一致。检查更新入口调用check方法
        // 对于UpdateBuilder中未设置的参数。会默认使用UpdateConfig中的配置
        UpdateHelper.getInstance()
                .strategy(new UpdateStrategy() {
                    @Override
                    public boolean isShowUpdateDialog(Update update) {
                        return true;
                    }

                    @Override
                    public boolean isAutoInstall() {
                        return false;
                    }

                    @Override
                    public boolean isShowInstallDialog() {
                        return true;
                    }

                    @Override
                    public boolean isShowDownloadDialog() {
                        return true;
                    }
                })
                .setUpdateListener(new UpdateListener() {
                    @Override
                    public void hasUpdate(Update update) {

                    }

                    @Override
                    public void noUpdate() {

                    }

                    @Override
                    public void onCheckError(int code, String errorMsg) {

                    }

                    @Override
                    public void onUserCancel() {

                    }
                })
                .check(MainActivity.this);
    }
}
