package com.deepak.batteryinfo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

@SuppressWarnings("ALL")
public class MainActivity extends AppCompatActivity {
    TextView showBatteryStatus,showPowerPlug,showBatterLevel,
            showBatteryScale,showBatteryHealth,showBatteryVoltage,
            showBatteryTemperature;//showBatteryTechnology,showBootTime;

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            IntentFilter intentFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
            Intent batteryInfo = context.registerReceiver(null,intentFilter);

            int status = batteryInfo.getIntExtra(BatteryManager.EXTRA_STATUS, -1);

            int chargePlug = batteryInfo.getIntExtra(BatteryManager.EXTRA_PLUGGED,-1);

            int batteryLevel = batteryInfo.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
            int batteryScale = batteryInfo.getIntExtra(BatteryManager.EXTRA_SCALE,-1);

            int batteryHealth = batteryInfo.getIntExtra(BatteryManager.EXTRA_HEALTH,0);

            int voltage = batteryInfo.getIntExtra("voltage", 0);
            int temperature = batteryInfo.getIntExtra("temperature", 0);

            String temp = String.valueOf((float)temperature/10) + " degree C";
            String vol = String.valueOf(voltage) + " mV";

            int technology = batteryInfo.getIntExtra(BatteryManager.EXTRA_TECHNOLOGY,0);
            long bootTime = SystemClock.elapsedRealtime()*1000;
            bootTime = bootTime % (24*3600);
            int hour = (int)bootTime/3600;
            bootTime %= 3600;
            int minute = (int) (bootTime/60);
            bootTime %= 60;
            int sec = (int) bootTime;
            String bootTimeValue = String.valueOf(hour) + ":" + String.valueOf(minute) + ":" + String.valueOf(sec);

            /* Showing the Values of the Battery Information */

            if (status == BatteryManager.BATTERY_STATUS_CHARGING || status == BatteryManager.BATTERY_STATUS_FULL) {
                showBatteryStatus.setText(R.string.charging);
            }else {
                showBatteryStatus.setText(R.string.not_charging);
            }
            if (chargePlug == BatteryManager.BATTERY_PLUGGED_USB) {
                showPowerPlug.setText(R.string.usb_plugged);
            }else if (chargePlug == BatteryManager.BATTERY_PLUGGED_AC) {
                showPowerPlug.setText(R.string.ac_power);
            }else if (chargePlug == BatteryManager.BATTERY_PLUGGED_WIRELESS) {
                showPowerPlug.setText(R.string.wireless_charging);
            }else {
                showPowerPlug.setText(R.string.not_plugged);
            }
            showBatterLevel.setText(String.valueOf(batteryLevel) + "%");
            showBatteryScale.setText(String.valueOf(batteryScale));

            if (batteryHealth == BatteryManager.BATTERY_HEALTH_COLD) {
                showBatteryHealth.setText(R.string.cold);
            }else if (batteryHealth == BatteryManager.BATTERY_HEALTH_DEAD) {
                showBatteryHealth.setText(R.string.dead);
            }else if (batteryHealth == BatteryManager.BATTERY_HEALTH_GOOD) {
                showBatteryHealth.setText(R.string.good);
            }else if (batteryHealth == BatteryManager.BATTERY_HEALTH_OVERHEAT) {
                showBatteryHealth.setText(R.string.overheat);
            }else if (batteryHealth == BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE) {
                showBatteryHealth.setText(R.string.over_voltage);
            }else if (batteryHealth == BatteryManager.BATTERY_HEALTH_UNKNOWN) {
                showBatteryHealth.setText(R.string.unknown);
            }else if (batteryHealth == BatteryManager.BATTERY_HEALTH_UNSPECIFIED_FAILURE) {
                showBatteryHealth.setText(R.string.unspecified_failure);
            }
            showBatteryVoltage.setText(vol);
            showBatteryTemperature.setText(temp);
//            showBatteryTechnology.setText(String.valueOf(technology));
//            showBootTime.setText(bootTimeValue);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        showBatteryStatus = findViewById(R.id.showBatteryStatus);
        showPowerPlug = findViewById(R.id.showPowerPlug);
        showBatterLevel = findViewById(R.id.showBatterLevel);
        showBatteryScale = findViewById(R.id.showBatteryScale);
        showBatteryHealth = findViewById(R.id.showBatteryHealth);
        showBatteryVoltage = findViewById(R.id.showBatteryVoltage);
        showBatteryTemperature = findViewById(R.id.showBatteryTemperature);
//        showBatteryTechnology = findViewById(R.id.showBatteryTechnology);
//        showBootTime = findViewById(R.id.showBootTime);

        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        registerReceiver(broadcastReceiver,intentFilter);
    }
}