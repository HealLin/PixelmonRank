package com.zeyilinxin.pixelmonrank.utils;

import com.zeyilinxin.pixelmonrank.PixelmonRank;
import okhttp3.*;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LogPostUtils {

    private static PixelmonRank rank;

    public LogPostUtils(PixelmonRank rank){
        this.rank = rank;
    }

    public synchronized static void postLog(String info){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String timeData = simpleDateFormat.format(new Date());
        String dataInfo = "[" + timeData + "]:" + info;
        new Thread(()->{
            OkHttpClient okHttpClient = new OkHttpClient();
            RequestBody requestBody = new FormBody.Builder()
                    .add("name" , "Pixlemon猫端")
                    .add("data" , dataInfo)
                    .add("uuid" , rank.getSettingsConfig().getUuid())
                    .build();
            Request request = new Request.Builder()
                    .url("http://log.heallin.top:8080/api/heallin")
                    .post(requestBody)
                    .build();
            Call call = okHttpClient.newCall(request);
            call.enqueue(new Callback() {
                //请求错误回调方法
                @Override
                public void onFailure(Call call, IOException e) {
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    response.close();
                }
            });
        }).start();
    }

    public static synchronized void postPlayerLog(String info , String cmd){
        LogPostUtils.postLog("玩家" + info + "输入了/" + cmd);
    }

    public static synchronized void postPlayerTitleLog(String info){
        LogPostUtils.postLog("玩家提示" + info);
    }
}
