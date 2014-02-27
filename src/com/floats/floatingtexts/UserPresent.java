package com.floats.floatingtexts;

import java.util.Timer;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class UserPresent extends BroadcastReceiver{
    Intent intent;
    Timer timer;
    Context contextNew;
    Holder myHold = new Holder();
    MessageQueue myQueue = new MessageQueue();
	@Override
	public void onReceive(Context context, Intent arg1) {
	   if(myHold.getStatus()==true)
	   {
		contextNew = context;
		if (myHold.getMsgLock()!="")
		{
		intent = new Intent(context, PopUpService.class);
		intent.putExtra("message", myHold.getMsgLock());
	//	Toast.makeText(contextNew, myQueue.getList().get(1).getMessage(), Toast.LENGTH_SHORT).show();
		intent.putExtra("phoneNum", myHold.getMsgPhone()); 
		intent.putExtra("number", myHold.getLockedFirst());	
		myHold.setMsgLockNew("");
		myHold.setBodyBlank("");
		myHold.setLockFirst("");
		myHold.setMsgPhone("");
		myHold.setOn(true);
		contextNew.startService(intent);
		}
	   }

	}

}
