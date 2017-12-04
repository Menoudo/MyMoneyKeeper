package ru.dis_ip.mymoneykeeper;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MoneyViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.moneyview);
    }

    @Override
    protected  void onStart() {
        super.onStart();
        WifiManager wifiManager = (WifiManager) this.getApplicationContext().getSystemService(Context.WIFI_SERVICE);

        if (wifiManager != null) {
            wifiManager.setWifiEnabled(true);
            //wifiManager.setWifiEnabled(false);
        }
    }

}
