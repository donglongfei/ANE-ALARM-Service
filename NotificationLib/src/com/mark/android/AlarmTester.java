package com.mark.android;

import com.mark.android.R;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class AlarmTester extends Activity {

    /** Called when the activity is first created. */
	private PendingIntent mAlarmSender;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        mAlarmSender=PendingIntent.getService(AlarmTester.this, 0, 
        						new Intent("com.mark.android.notification_service"),
        						0);
        setContentView(R.layout.calllogbackupservice);
        Button button=(Button)findViewById(R.id.start_alarm);
        button.setOnClickListener(mStartAlarmListener);
        button=(Button)findViewById(R.id.stop_alarm);
        button.setOnClickListener(mStopAlarmListener);
    }
    
    private OnClickListener mStartAlarmListener=new OnClickListener(){
    	public void onClick(View v){
    		long firstTime=SystemClock.elapsedRealtime();
    		AlarmManager am=(AlarmManager)getSystemService(ALARM_SERVICE);
    		am.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, firstTime, 30*1000, mAlarmSender);
    		Toast.makeText(AlarmTester.this, R.string.repeating_scheduled, Toast.LENGTH_LONG).show();
    	}
    };
    
    private OnClickListener mStopAlarmListener=new OnClickListener(){
    	public void onClick(View v){
    		AlarmManager am=(AlarmManager)getSystemService(ALARM_SERVICE);
    		am.cancel(mAlarmSender);
    		Toast.makeText(AlarmTester.this, R.string.repeating_unscheduled, Toast.LENGTH_LONG).show();
    	}
    };
	
}
