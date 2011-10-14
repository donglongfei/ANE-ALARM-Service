package com.mark.android;
import java.util.HashMap;
import java.util.Map;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;


public class AlarmExtensionContext extends FREContext {

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	@Override
	public Map<String, FREFunction> getFunctions() {
		// TODO Auto-generated method stub

		Map<String, FREFunction> functionMap=new HashMap<String, FREFunction>();
		functionMap.put("invokeService", new AlarmFunction());
		System.out.println("return invokeService");
		return functionMap;
	}

}
