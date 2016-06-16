package com.dou361.update.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.dou361.update.R;
import com.dou361.update.Updater;
import com.dou361.update.bean.Update;
import com.dou361.update.listener.UpdateListener;
import com.dou361.update.util.InstallUtil;
import com.dou361.update.util.NetworkUtil;
import com.dou361.update.util.SafeDialogOper;
import com.dou361.update.util.UpdateSP;

import java.math.BigDecimal;

/**
 * ========================================
 * <p/>
 * 版 权：dou361.com 版权所有 （C） 2015
 * <p/>
 * 作 者：陈冠明
 * <p/>
 * 个人网站：http://www.dou361.com
 * <p/>
 * 版 本：1.0
 * <p/>
 * 创建日期：2016/6/15
 * <p/>
 * 描 述：
 * <p/>
 * <p/>
 * 修订历史：
 * <p/>
 * ========================================
 */
public class DialogUI implements CompoundButton.OnCheckedChangeListener, View.OnClickListener {

    private View jjdxm_update_wifi_indicator;
    private TextView jjdxm_update_content;
    private CheckBox jjdxm_update_id_check;
    private Button jjdxm_update_id_ok;
    private Button jjdxm_update_id_cancel;
    private Button jjdxm_update_id_ignore;
    private Dialog dialog;
    private Activity mActivity;
    private Update mUpdate;

    private UpdateListener checkCB;
    private String mPath;
    /**
     * 默认为新版本提示0,1为安装提示
     */
    private int mAction;

    public void setCheckCB(UpdateListener checkCB) {
        this.checkCB = checkCB;
    }

    public void sendDownloadRequest(Update update, Activity activity) {
        Updater.getInstance().downUpdate(activity, update);
    }

    public void sendUserCancel() {
        if (this.checkCB != null) {
            this.checkCB.onUserCancel();
        }
    }

    public Dialog create(int action, Update update, String path, Activity activity) {
        mAction = action;
        mActivity = activity;
        mPath = path;
        mUpdate = update;
        String updateContent = null;

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
        if (mAction == 0) {
            updateContent = activity.getText(R.string.jjdxm_update_newversion)
                    + update.getVersionName() + "\n"
                    + activity.getText(R.string.jjdxm_update_targetsize) + getFormatSize(update.getApkSize()) + "\n\n"
                    + activity.getText(R.string.jjdxm_update_updatecontent) + "\n" + update.getUpdateContent() +
                    "\n";
            jjdxm_update_id_ok.setText(R.string.jjdxm_update_updatenow);
            jjdxm_update_content.setText(updateContent);
        } else {
            updateContent = activity.getText(R.string.jjdxm_update_newversion)
                    + update.getVersionName() + "\n"
                    + activity.getText(R.string.jjdxm_update_dialog_installapk) + "\n\n"
                    + activity.getText(R.string.jjdxm_update_updatecontent) + "\n" + update.getUpdateContent() +
                    "\n";
            jjdxm_update_id_ok.setText(R.string.jjdxm_update_installnow);
            jjdxm_update_content.setText(updateContent);
        }
        jjdxm_update_id_check.setOnCheckedChangeListener(this);
        jjdxm_update_id_ok.setOnClickListener(this);
        jjdxm_update_id_cancel.setOnClickListener(this);
        jjdxm_update_id_ignore.setOnClickListener(this);
        dialog = new AlertDialog.Builder(mActivity).create();
        if (UpdateSP.isForced()) {
            jjdxm_update_id_check.setVisibility(View.GONE);
            dialog.setCancelable(false);
        } else {
            dialog.setCancelable(true);
            jjdxm_update_id_check.setVisibility(View.VISIBLE);
        }
        dialog.show();
        dialog.getWindow().setContentView(view);
        return dialog;
    }

    public static String getFormatSize(double size) {
        double kiloByte = size / 1024.0D;
        if (kiloByte < 1.0D) {
            return size + "Byte";
        } else {
            double megaByte = kiloByte / 1024.0D;
            if (megaByte < 1.0D) {
                BigDecimal gigaByte1 = new BigDecimal(Double.toString(kiloByte));
                return gigaByte1.setScale(2, 4).toPlainString() + "KB";
            } else {
                double gigaByte = megaByte / 1024.0D;
                if (gigaByte < 1.0D) {
                    BigDecimal teraBytes1 = new BigDecimal(Double.toString(megaByte));
                    return teraBytes1.setScale(2, 4).toPlainString() + "MB";
                } else {
                    double teraBytes = gigaByte / 1024.0D;
                    BigDecimal result4;
                    if (teraBytes < 1.0D) {
                        result4 = new BigDecimal(Double.toString(gigaByte));
                        return result4.setScale(2, 4).toPlainString() + "GB";
                    } else {
                        result4 = new BigDecimal(teraBytes);
                        return result4.setScale(2, 4).toPlainString() + "TB";
                    }
                }
            }
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.jjdxm_update_id_ok) {
            if (mAction == 0) {
                sendDownloadRequest(mUpdate, mActivity);
                SafeDialogOper.safeDismissDialog(dialog);
            } else {
                SafeDialogOper.safeDismissDialog(dialog);
                InstallUtil.installApk(mActivity, mPath);
            }
        } else if (id == R.id.jjdxm_update_id_cancel) {
            if (UpdateSP.isForced()) {
                sendUserCancel();
                SafeDialogOper.safeDismissDialog(dialog);
                mActivity.finish();
            } else {
                sendUserCancel();
                SafeDialogOper.safeDismissDialog(dialog);
            }
        } else if (id == R.id.jjdxm_update_id_ignore) {
            UpdateSP.setIgnore(mUpdate.getVersionCode() + "");
            sendUserCancel();
            SafeDialogOper.safeDismissDialog(dialog);
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        jjdxm_update_id_cancel.setVisibility(isChecked ? View.GONE : View.VISIBLE);
        jjdxm_update_id_ignore.setVisibility(isChecked ? View.VISIBLE : View.GONE);
    }

}
