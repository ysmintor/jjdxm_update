package com.dou361.update.business;

import com.dou361.update.UpdateHelper;
import com.dou361.update.callback.UpdateCheckCB;
import com.dou361.update.model.Update;
import com.dou361.update.model.DataParser;
import com.dou361.update.util.HandlerUtil;
import com.dou361.update.util.InstallUtil;
import com.dou361.update.util.UpdateSP;

/**
 *
 */
public abstract class UpdateWorker implements Runnable{

    protected String url;
    protected UpdateCheckCB checkCB;
    protected DataParser parser;

    public void setUrl(String url) {
        this.url = url;
    }

    public void setCheckCB(UpdateCheckCB checkCB) {
        this.checkCB = checkCB;
    }

    public void setParser(DataParser parser) {
        this.parser = parser;
    }

    @Override
    public void run() {
        try {
            String response = check(url);
            Update parse = parser.parse(response);
            if (parse == null) {
                throw new IllegalArgumentException("parse response to update failed by " + parser.getClass().getCanonicalName());
            }
            int curVersion = InstallUtil.getApkVersion(UpdateHelper.getInstance().getContext());
            if (parse.getVersionCode() > curVersion && !UpdateSP.isIgnore(parse.getVersionCode()+"")) {
                sendHasUpdate(parse);
            } else {
                sendNoUpdate();
            }
        } catch (HttpException he) {
            sendOnErrorMsg(he.getCode(),he.getErrorMsg());
        } catch (Exception e) {
            sendOnErrorMsg(-1,e.getMessage());
        }
    }

    protected abstract String check(String url) throws Exception;

    private void sendHasUpdate(final Update update) {
        if (checkCB == null) return;
        HandlerUtil.getMainHandler().post(new Runnable() {
            @Override
            public void run() {
                checkCB.hasUpdate(update);
            }
        });
    }

    private void sendNoUpdate() {
        if (checkCB == null) return;
        HandlerUtil.getMainHandler().post(new Runnable() {
            @Override
            public void run() {
                checkCB.noUpdate();
            }
        });
    }

    private void sendOnErrorMsg(final int code, final String errorMsg) {
        if (checkCB == null) return;
        HandlerUtil.getMainHandler().post(new Runnable() {
            @Override
            public void run() {
                checkCB.onCheckError(code,errorMsg);
            }
        });
    }
}
