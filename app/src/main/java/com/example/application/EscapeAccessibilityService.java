package com.example.application;

import android.accessibilityservice.AccessibilityService;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.accessibility.AccessibilityEvent;

import java.util.ArrayList;

public class EscapeAccessibilityService extends AccessibilityService {
	public static String currentForegroundPackage = "";
    private final ArrayList<Long> pressTimestamps = new ArrayList<Long>();
    private static final long TRIGGERWINDOWMS = 1000L;
	@Override
	protected void onServiceConnected() {
		try {
			java.lang.reflect.Method getInfo =
				android.accessibilityservice.AccessibilityService.class
                .getMethod("getServiceInfo");
			Object info = getInfo.invoke(this);

			java.lang.reflect.Field flagsField = info.getClass().getField("flags");
			int flags = flagsField.getInt(info);

			flags |= 8;  // FLAGREQUESTFILTERKEYEVENTS

			flagsField.setInt(info, flags);

			java.lang.reflect.Method setInfo =
				android.accessibilityservice.AccessibilityService.class
                .getMethod("setServiceInfo", info.getClass());
			setInfo.invoke(this, info);
		} catch (Throwable t) {
		}
	}
    @Override
    public boolean onKeyEvent(KeyEvent event) {android.util.Log.d("LockTerm", "key=" + event.getKeyCode() + " action=" + event.getAction());
        if (event.getKeyCode() != KeyEvent.KEYCODE_VOLUME_UP
			|| event.getAction() != KeyEvent.ACTION_DOWN) {
            return super.onKeyEvent(event);
        }

        long now = System.currentTimeMillis();
        pressTimestamps.add(now);

        for (int i = pressTimestamps.size() - 1; i >= 0; i--) {
            if (now - pressTimestamps.get(i) > TRIGGER_WINDOW_MS) {
                pressTimestamps.remove(i);
            }
        }

        if (pressTimestamps.size() >= 3) {
            pressTimestamps.clear();
            launchEscape();
            return true;
        }

        return super.onKeyEvent(event);
    }
	
	
    private void launchEscape() {
        try {
            Intent intent = new Intent(this, EscapeOverlayActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
							| Intent.FLAG_ACTIVITY_CLEAR_TOP
							| Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
            startActivity(intent);
        } catch (Exception e) {
        }
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
    
	if (event == null) return;
	if (event.getEventType() != AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) return;
	CharSequence pkg = event.getPackageName();
	if (pkg != null) {
		currentForegroundPackage = pkg.toString();
	}
}

    @Override
    public void onInterrupt() {
    }
}