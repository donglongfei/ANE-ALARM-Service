package com.mark.android;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.widget.Toast;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;
import com.adobe.fre.FREInvalidObjectException;
import com.adobe.fre.FREObject;
import com.adobe.fre.FRETypeMismatchException;
import com.adobe.fre.FREWrongThreadException;


public class AlarmFunction implements FREFunction {

	private Activity activity;
	private PendingIntent mAlarmSender;
	private static final int START_SERVICE=0;
	private static final int STOP_SERVICE=1;
	
	private FREContext freContext;
	public FREObject call(FREContext context, FREObject[] passedArgs) {
		// TODO Auto-generated method stub
		
		FREObject result = null;
		freContext=context;
		 try{
			 
			 activity=context.getActivity();
			 Intent ntfIntent;
			 ntfIntent=new Intent("com.mark.android.notificationService");

			 /**retrieve resource from intent extras**/	
			 Bundle bundle = new Bundle();;
			 bundle.putInt("string.notification_finished", freContext.getResourceId("string.notification_finished"));
			 bundle.putInt("string.notification", freContext.getResourceId("string.notification"));
			 bundle.putInt("string.notification_started", freContext.getResourceId("string.notification_started"));
			 bundle.putInt("drawable.communication", freContext.getResourceId("drawable.communication"));
			 ntfIntent.putExtras(bundle);
			 
			 /**Don't create intent using Class for ANE,  ADT will change the package name in 
			  * AndroidManifest.xml of final APK file.  So it can't find the your Class using that pacakge name.
			   **/			 
			 
//			 mAlarmSender=PendingIntent.getService(activity, 0, 
//						new Intent(activity,Notification_Service.class),
//						0);

			 
			 /** use intent action name to locate the service. In Flex application descriptor file, we will 
			  * use full class name to specify the intent and the action name. 
			   **/	
			 
			 mAlarmSender=PendingIntent.getService(activity, 0, ntfIntent,0);
					 
			 int cmd;
			 cmd=passedArgs[0].getAsInt();
			 switch (cmd){
			 case START_SERVICE:
			 	startService();
			 	result=FREObject.newObject("STARTED"); 
			 	break;
			 case STOP_SERVICE:
			 	stopService();
			 	result=FREObject.newObject("STOPPED"); 

			 	break;
			 }
			 
			}catch(IllegalStateException e){
				e.printStackTrace();
				Toast.makeText(context.getActivity(), "IllegalStateException", Toast.LENGTH_SHORT).show();
			} catch (FRETypeMismatchException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Toast.makeText(context.getActivity(), "FRETypeMismatchException", Toast.LENGTH_SHORT).show();
			} catch (FREInvalidObjectException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Toast.makeText(context.getActivity(), "FREInvalidObjectException", Toast.LENGTH_SHORT).show();
			} catch (FREWrongThreadException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Toast.makeText(context.getActivity(), "FREWrongThreadException", Toast.LENGTH_SHORT).show();
			}
			return result;
	}
	
	
    private void startService(){
    	long firstTime=SystemClock.elapsedRealtime();
    	AlarmManager am=(AlarmManager)activity.getSystemService(Context.ALARM_SERVICE);
   		am.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, firstTime, 30*1000, mAlarmSender);
    	Toast.makeText(activity, freContext.getResourceId("string.repeating_scheduled") , Toast.LENGTH_SHORT).show();
    }
    
    private void stopService(){
    	AlarmManager am=(AlarmManager)activity.getSystemService(Context.ALARM_SERVICE);
    	am.cancel(mAlarmSender);
    	Toast.makeText(activity, freContext.getResourceId("string.repeating_unscheduled"), Toast.LENGTH_SHORT).show();
    }

	
}