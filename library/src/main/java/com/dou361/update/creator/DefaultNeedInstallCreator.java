package com.dou361.update.creator;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.dou361.update.R;
import com.dou361.update.model.Update;
import com.dou361.update.util.InstallUtil;
import com.dou361.update.util.NetworkUtil;
import com.dou361.update.util.SafeDialogOper;

/**
 * @author Administrator
 */
public class DefaultNeedInstallCreator extends InstallCreator implements View.OnClickListener {
    private View jjdxm_update_wifi_indicator;
    private TextView jjdxm_update_content;
    private CheckBox jjdxm_update_id_check;
    private Button jjdxm_update_id_ok;
    private Button jjdxm_update_id_cancel;
    private Button jjdxm_update_id_ignore;
    private Dialog dialog;
    private Activity mActivity;
    private String mPath;

    @Override
    public Dialog create(Update update, final String path, final Activity activity) {
        mActivity = activity;
        mPath = path;
        String updateContent = activity.getText(R.string.jjdxm_update_updatetitle)
                + ": " + update.getVersionName() + "\n\n\n"
                + update.getUpdateContent();
        LayoutInflater inflater = LayoutInflater.from(mActivity);
        View view = inflater.inflate(R.layout.umeng_update_dialog, null);
        jjdxm_update_wifi_indicator = view.findViewById(R.id.jjdxm_update_wifi_indicator);
        jjdxm_update_content = (TextView) view.findViewById(R.id.jjdxm_update_content);
        jjdxm_update_id_check = (CheckBox) view.findViewById(R.id.jjdxm_update_id_check);
        jjdxm_update_id_ok = (Button) view.findViewById(R.id.jjdxm_update_id_ok);
        jjdxm_update_id_cancel = (Button) view.findViewById(R.id.jjdxm_update_id_cancel);
        jjdxm_update_id_ignore = (Button) view.findViewById(R.id.jjdxm_update_id_ignore);

        if (NetworkUtil.isConnectedByWifi()) {
            //WiFi环境
            jjdxm_update_wifi_indicator.setVisibility(View.INVISIBLE);
        } else {
            jjdxm_update_wifi_indicator.setVisibility(View.VISIBLE);
        }
        jjdxm_update_content.setText(updateContent);
        jjdxm_update_id_check.setVisibility(View.GONE);
        jjdxm_update_id_ok.setText(R.string.jjdxm_update_installnow);
        jjdxm_update_id_ok.setOnClickListener(this);
        if (!update.isForced()) {
            jjdxm_update_id_cancel.setVisibility(View.VISIBLE);
        } else {
            jjdxm_update_id_cancel.setVisibility(View.GONE);
        }
        jjdxm_update_id_cancel.setOnClickListener(this);
        jjdxm_update_id_ignore.setOnClickListener(this);
        dialog = new AlertDialog.Builder(mActivity).create();
        dialog.setCancelable(true);
        dialog.show();
        dialog.getWindow().setContentView(view);
        return dialog;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.jjdxm_update_id_ok) {
            SafeDialogOper.safeDismissDialog(dialog);
            InstallUtil.installApk(mActivity, mPath);
        } else if (id == R.id.jjdxm_update_id_cancel) {
            sendUserCancel();
            SafeDialogOper.safeDismissDialog(dialog);
        } else if (id == R.id.jjdxm_update_id_ignore) {
        }
    }
}
