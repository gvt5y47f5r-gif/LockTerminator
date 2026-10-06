import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class EscapeOverlayActivity extends Activity {
	

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(
			WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN,
			WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN
        );

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setBackgroundColor(Color.parseColor("#EE000000"));
        layout.setPadding(50, 150, 50, 50);

        TextView title = new TextView(this);
        title.setText("紧急逃生模式\n三击音量键已触发");
        title.setTextSize(22);
        title.setTextColor(Color.parseColor("#FF4444"));
        title.setGravity(Gravity.CENTER);
        title.setPadding(0, 0, 0, 50);
        layout.addView(title);
		TextView pkgInfo = new TextView(this);
		pkgInfo.setText("当前前台: " + EscapeAccessibilityService.currentForegroundPackage);
		pkgInfo.setTextSize(14);
		pkgInfo.setTextColor(Color.parseColor("#60A5FA"));
		pkgInfo.setGravity(Gravity.CENTER);
		pkgInfo.setPadding(0, 0, 0, 20);
		layout.addView(pkgInfo);
	

        Button btnSettings = new Button(this);
        btnSettings.setText("打开系统设置");
        btnSettings.setOnClickListener(new View.OnClickListener() {
			
					@Override
					public void onClick(View v) {
						Intent i = new Intent(Settings.ACTION_SETTINGS);
						i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						startActivity(i);
					}
				});
			layout.addView(btnSettings);
				
			
        Button btnApps = new Button(this);
        btnApps.setText("打开应用管理");
        btnApps.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent i = new Intent(Settings.ACTION_APPLICATION_SETTINGS);
					i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					startActivity(i);
				}
			});
        layout.addView(btnApps);

        Button btnAccessibility = new Button(this);
        btnAccessibility.setText("关闭无障碍服务");
        btnAccessibility.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent i = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
					i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					startActivity(i);
				}
			});
        layout.addView(btnAccessibility);

        Button btnClose = new Button(this);
        btnClose.setText("关闭");
        btnClose.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					finish();
				}
			});
        layout.addView(btnClose);

        TextView tip = new TextView(this);
        tip.setText("\n提示：如无法操作，请长按电源键重启，\n开机震动时按住音量下键进入安全模式");
        tip.setTextSize(13);
        tip.setTextColor(Color.parseColor("#8892A8"));
        tip.setGravity(Gravity.CENTER);
        tip.setPadding(0, 50, 0, 0);
        layout.addView(tip);

        setContentView(layout);
    }
}