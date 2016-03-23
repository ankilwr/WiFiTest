package com.shihang.wifi;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;
import com.shihang.wifi.server.WifiTransmissionManager;
import com.shihang.wifi.server.WifiTransmissionManager.TransmissionResultListener;
import com.shihang.wifi.server.HtmlConst;
import com.shihang.wifi.util.FileAccessUtil;
import com.shihang.wifi.util.TShow;


public class MainActivity extends AppCompatActivity {

    TextView ipStr;

    private TransmissionResultListener resultListener = new TransmissionResultListener() {
        @Override
        public void transmissionResult(boolean success, String filePath, String msg) {
            if(success){
                TShow.showShort(MainActivity.this, msg);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);

        WifiTransmissionManager.addListener(resultListener);

        ipStr = (TextView) findViewById(R.id.ipStr);

        HtmlConst.DIR_NAME = FileAccessUtil.createDir(HtmlConst.DIR_NAME);
        WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        if(isWifiConnected(wifiManager, wifiInfo)){
            String ipAddress = int2Ip(wifiInfo.getIpAddress());
            ipStr.setText("请在浏览器地址栏中输入\n\n" + "http://" + ipAddress + ":" + HtmlConst.PORT);
            WifiTransmissionManager.startServer(HtmlConst.PORT);
        }else{
            ipStr.setText("您的设备没有连接WIFI");
        }
    }


    private boolean isWifiConnected(WifiManager wifiManager,WifiInfo wifiInfo) {
        int ipAddress = wifiInfo == null ? 0 : wifiInfo.getIpAddress();
        return wifiManager.isWifiEnabled() && ipAddress != 0;
    }

    private String int2Ip(int ip) {
        return (ip & 0xFF) + "." + ((ip >> 8) & 0xFF) + "."+ ((ip >> 16) & 0xFF) + "." + ((ip >> 24) & 0xFF);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        WifiTransmissionManager.stopServer();
        WifiTransmissionManager.removeListener(resultListener);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return true;
    }
}
