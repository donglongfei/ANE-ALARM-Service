package com.mark.aneinterface
{
	import flash.external.ExtensionContext;
	
	import mx.core.mx_internal;
	
	public class AlarmService
	{
		private var extContext:ExtensionContext = null;
		private static var _instance:AlarmService = null;
		private static var _flag:Boolean = false;
		
		public function AlarmService()
		{
			if (!extContext)
			{
				extContext = ExtensionContext.createExtensionContext("com.mark.alarmService",null);
			}
		}
		
		public function stopAlarm ():String
		{
			var result:String;
			result=extContext.call("invokeService",1) as String;
			return result;
		}
		
		public function startAlarm ():String
		{
			var result:String;
			trace("prepare to start ALARM");
			result=extContext.call("invokeService",0) as String;
			trace(result);
			return result;
		}
	}
}