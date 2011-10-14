package com.mark.android;

//import com.mark.android.R;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import android.widget.Toast;

public class NotificationService extends Service {
	NotificationManager mNM;
	Bundle bundle;
	
	@Override
	public void onCreate(){
		mNM=(NotificationManager)getSystemService(NOTIFICATION_SERVICE);
	}
	
	
	@Override
	public void onStart(Intent intent, int startId) {
		// TODO Auto-generated method stub
		super.onStart(intent, startId);
		 bundle = intent.getExtras();
			showNotification("string.notification_started");
			Thread thr=new Thread(null,mTask,"notificationService_service");
			thr.start();
	}


	@Override
	public void onDestroy(){
		//will remove the notification and wait for next notification (just for test)
//		mNM.cancel(bundle.getInt("string.notification_started"));
	}
	
	Runnable mTask=new Runnable() {
		public void run() {
			// TODO Auto-generated method stub
			long endTime=System.currentTimeMillis()+15*1000;
			while(System.currentTimeMillis()<endTime){
				synchronized(mBinder){
					try{
						mBinder.wait(endTime-System.currentTimeMillis());
					}catch(Exception e){
						
					}
				}
			}
			NotificationService.this.stopSelf();
		}
	};
	
	@Override
	public IBinder onBind(Intent intent){
		return mBinder;
	}
	
	private void showNotification(String key){
		CharSequence text=getText(bundle.getInt(key));
		int id = bundle.getInt("drawable.communication");
		Notification notification=new Notification(id, text, System.currentTimeMillis());
		PendingIntent contentIntent=PendingIntent.getActivity(this, 0, new Intent(this,AlarmFunction.class), 0);
		notification.setLatestEventInfo(this, getText(bundle.getInt("string.notification")), text, contentIntent);
		mNM.notify(bundle.getInt(key),notification);
	}
	
	private final IBinder mBinder=new Binder(){
		@Override
			protected boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException{
				return super.onTransact(code, data, reply, flags);
		}
	};
}
