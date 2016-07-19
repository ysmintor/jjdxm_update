package com.dou361.update.download;

import com.dou361.update.ParseData;
import com.dou361.update.listener.OnlineCheckListener;
import com.dou361.update.util.HandlerUtil;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 *
 */
public class OnlineCheckWorker implements Runnable {

    protected String url;
    protected OnlineCheckListener checkCB;
    protected ParseData parser;

    public void setUrl(String url) {
        this.url = url;
    }

    public void setUpdateListener(OnlineCheckListener checkCB) {
        this.checkCB = checkCB;
    }

    public void setParser(ParseData parser) {
        this.parser = parser;
    }

    @Override
    public void run() {
        try {
            String response = check(url);
            String parse = parser.parse(response);
            if (parse == null) {
                throw new IllegalArgumentException("parse response to update failed by " + parser.getClass().getCanonicalName());
            }
            sendHasUpdate(parse);
        } catch (HttpException he) {
        } catch (Exception e) {
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

    private void sendHasUpdate(final String update) {
        if (checkCB == null) return;
        HandlerUtil.getMainHandler().post(new Runnable() {
            @Override
            public void run() {
                checkCB.hasParams(update);
            }
        });
    }
}
