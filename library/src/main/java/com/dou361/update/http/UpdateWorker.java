package com.dou361.update.http;

import com.dou361.update.ParseData;
import com.dou361.update.UpdateHelper;
import com.dou361.update.bean.Update;
import com.dou361.update.listener.UpdateListener;
import com.dou361.update.util.HandlerUtil;
import com.dou361.update.util.InstallUtil;
import com.dou361.update.util.UpdateSP;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 *
 */
public class UpdateWorker implements Runnable {

    protected String url;
    protected UpdateListener checkCB;
    protected ParseData parser;

    public void setUrl(String url) {
        this.url = url;
    }

    public void setUpdateListener(UpdateListener checkCB) {
        this.checkCB = checkCB;
    }

    public void setParser(ParseData parser) {
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
            if (parse.getVersionCode() > curVersion && !UpdateSP.isIgnore(parse.getVersionCode() + "")) {
                sendHasUpdate(parse);
            } else {
                sendNoUpdate();
            }
        } catch (HttpException he) {
            sendOnErrorMsg(he.getCode(), he.getErrorMsg());
        } catch (Exception e) {
            sendOnErrorMsg(-1, e.getMessage());
        }
    }

    protected String check(String urlStr) throws Exception {
        URL getUrl = new URL(urlStr);
        HttpURLConnection urlConn = (HttpURLConnection) getUrl.openConnection();
        urlConn.setDoInput(true);
        urlConn.setDoOutput(true);
        urlConn.setUseCaches(false);
        urlConn.setConnectTimeout(10000);
        urlConn.setRequestMethod("GET");
        urlConn.connect();

        int responseCode = urlConn.getResponseCode();
        if (responseCode < 200 || responseCode >= 300) {
            throw new HttpException(responseCode, urlConn.getResponseMessage());
        }

        BufferedReader bis = new BufferedReader(new InputStreamReader(urlConn.getInputStream(), "utf-8"));

        StringBuilder sb = new StringBuilder();
        String lines;
        while ((lines = bis.readLine()) != null) {
            sb.append(lines);
        }

        return sb.toString();
    }

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
                checkCB.onCheckError(code, errorMsg);
            }
        });
    }
}
