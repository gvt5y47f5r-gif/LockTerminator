package com.example.application;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;

public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setBackgroundColor(Color.parseColor("#0B0F1C"));
        layout.setGravity(Gravity.CENTER);
        layout.setPadding(40, 40, 40, 40);

        TextView title = new TextView(this);
        title.setText("锁机终结者\nLock Terminator");
        title.setTextSize(28);
        title.setTextColor(Color.WHITE);
        title.setGravity(Gravity.CENTER);
        title.setPadding(0, 0, 0, 60);
        layout.addView(title);

        Button btnAccessibility = new Button(this);
        btnAccessibility.setText("① 开启无障碍服务");
        btnAccessibility.setTextSize(16);
        btnAccessibility.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					startActivity(new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS));
				}
			});
        layout.addView(btnAccessibility);

        Button btnOverlay = new Button(this);
        btnOverlay.setText("② 开启悬浮窗权限");
        btnOverlay.setTextSize(16);
        btnOverlay.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					startActivity(new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION));
				}
			});
        layout.addView(btnOverlay);

        TextView tip = new TextView(this);
        tip.setText("\n使用方法：\n音量上键连按三次 → 触发逃生");
        tip.setTextSize(14);
        tip.setTextColor(Color.parseColor("#8892A8"));
        tip.setGravity(Gravity.CENTER);
        tip.setPadding(0, 60, 0, 0);
        layout.addView(tip);
		startService(new Intent(this, keepaliveservice.class));
        setContentView(layout);
    }
}