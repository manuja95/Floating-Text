package com.floats.floatingtexts;


import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.app.Dialog;
import android.app.KeyguardManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.provider.ContactsContract.PhoneLookup;
import android.telephony.SmsMessage;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

public class Receiver extends BroadcastReceiver
{
    PopUpService myPop;
    private View myTextView;
	public String strMessage = "";
	Intent intent;
	Timer timer;
    Holder myHold = new Holder();
    static MessageQueue myQueue = new MessageQueue();
    LauncherFace myTemp = new LauncherFace();
    static int counter = 0;
    TextView myText;
    String changedText = "";
    String phoneNum = "";
    String person = "";
    String number = "";
    int tempId;
    
    private int findId()
    {
		 for (int x = 0; x < myQueue.getList().size(); x++)
		 {
			 if ((myQueue.getList().get(x).getPhoneNum()).equals(phoneNum))
			 {
			
				 return myQueue.getList().get(x).getId();
			 }
		 }
		 
		 return -1;
    }
	@Override
	public void onReceive(Context context, Intent arg1) {
	 //myTemp.loadSavedPreferences();
		SharedPreferences sp = context.getSharedPreferences("KeyChainPref",0);
		boolean toggleValue = sp.getBoolean("ToggleValue", true);
		String durationSeconds = sp.getString("DurationSeconds", "5");
		String color = sp.getString("ColorValue", "Blue");
		// String colorEx = sp.getString("Allcolor", "Kobe");
		boolean randomColor = sp.getBoolean("RandomColor", false);
		int colorVal = Integer.parseInt(sp.getString("ColorValue-Color", "0"));
		boolean infinityValue = sp.getBoolean("InfinityValue", false); 
		int fontColor = Integer.parseInt(sp.getString("FontColor", "-1"));
		int borderColor = Integer.parseInt(sp.getString("Border", "-1"));
		 //String name = sharedPreferences.getString("storedName", "YourName");
		myHold.setFontChanged(sp.getBoolean("FontChanged", false));
		/*
		 if (!(colorEx.equals("Kobe")))
		 {
			 color=colorEx;
			 Log.d("Brady", color);
		 }
		 */
		 if (durationSeconds !=null)
		 {
			 myHold.setDuration(Integer.parseInt(durationSeconds)*1000);
		 }
		 
		 if (infinityValue)
		 {
			   myHold.setCheck(true);
		 }
		 else
		 {
			   myHold.setCheck(false);

		 }
	
		 
		 if (borderColor != -1)
		 {
			 myHold.setBorderColor(borderColor);
		 }
		 
		 if (fontColor != -1&&myHold.getFontChanged())
		 {
			 Log.d("Brady", "Not supposed to come here");
			 myHold.setFontColor(fontColor);
		 }
		 if (color!=null)
		 {
			 if (color.equals("Blue"))
			 {
				 myHold.setBlue(true);
			 }
			 else if (color.equals("Green"))
			 {
				 myHold.setGreen(true);
			 }
			 else if (color.equals("Purple"))
			 {
				 myHold.setPurple(true);
			 }
			 else if (color.equals("Dark"))
			 {
				 myHold.setHoloDark(true);
			 }
			 else if (color.equals("Light"))
			 {
				 myHold.setHoloLight(true);
			 }
			 else if (color.equals("Solid"))
			 {
				 myHold.setSolidColors(true);
				 myHold.setColor(colorVal);
				 myHold.setBorderColor(borderColor);
			 }
		 }
		if (toggleValue) { 	
			myHold.setStatus(true);
		 } else {
        	myHold.setStatus(false);
		 }
		 
		 if (randomColor)
		 {
				myHold.setRandom(true);
				myHold.setBlue(false);
				myHold.setPurple(false);
				myHold.setGreen(false);
				myHold.setHoloDark(false);
				myHold.setHoloLight(false);
				myHold.setSolidColors(false);
		 }
	 if(myHold.getStatus()==true)
	 {
	    KeyguardManager myKM = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);
	    strMessage = "";
		myPop= new PopUpService();
		final Context contextNew = context;
		Bundle mBundle = arg1.getExtras();
		SmsMessage[] messages = null;
		if (mBundle != null)
		{
			Object[] duster = (Object[]) mBundle.get("pdus");
			messages = new SmsMessage[duster.length];
			for (int k = 0; k < messages.length; k++)
			{
				messages[k] = SmsMessage.createFromPdu((byte[])duster[k]);
				strMessage += messages[k].getMessageBody().toString();
				phoneNum = messages[k].getOriginatingAddress();
			}
			number=phoneNum;
			Uri phoneUri = Uri.withAppendedPath(PhoneLookup.CONTENT_FILTER_URI, Uri.encode(phoneNum));
			Cursor phonesCursor = context.getContentResolver().query(phoneUri, new String[] {PhoneLookup.DISPLAY_NAME}, null, null, null);

			

			if(phonesCursor != null && phonesCursor.moveToFirst()) {
			    person = phonesCursor.getString(0); // this is the contact name
			}//end if 
			
			if (person.equals(""))
			{
			    person = phoneNum;	
			}
			
			strMessage = strMessage.replace("\n", "").replace("\r", "");
			
			if (strMessage != null && ((myHold.getFirst()==null)||myHold.getFirst().equals(phoneNum)))
				if (myHold.getWasLocked()==true)
				{
				   myHold.setBody(strMessage);
				   myHold.setWasLocked(false);
				  // Toast.makeText(contextNew, "hi", Toast.LENGTH_SHORT).show();
				}
				else
				{
			       myHold.setBody("\n"+strMessage);
			       //Toast.makeText(contextNew, "hi", Toast.LENGTH_SHORT).show();
				}

			if( myKM.inKeyguardRestrictedInputMode()) {

				
				myHold.setWasLocked(true);
				
				if (myHold.getLockedFirst().equals(""))
				{
					myHold.setLockFirst(phoneNum);
					myQueue.clean();
					counter = 0;

				}
				//tempId = findId();
				if (myHold.getLockedFirst().equals(phoneNum))
				{
					tempId = findId();
					
					myHold.setMsgPhone(person);
					myHold.setFirst(phoneNum);
					myHold.setLockFirst(phoneNum);

					if (tempId != -1)
				    {
						 
					     myQueue.getList().get(tempId).setMessage(myQueue.getList().get(tempId).getMessage()+ "\n" + strMessage);
					    
					     if (tempId == 0)
					     {
					       myHold.setMsgLock(strMessage+"\n");
					     }
					     
				    }
				    else
				    {
				      
				      myHold.setMsgLock(strMessage+"\n");
					  myQueue = new MessageQueue(counter, phoneNum, person, strMessage);
					  myQueue.addList();
					  myQueue.setEmpty(false);
					  counter++;
					  myPop.setVisibleArrows();
				    }

				}
				else
				{
					myQueue.setProgress(true);
					myHold.setLockFirst(phoneNum);
					
					tempId = findId();
					
					if (tempId != -1)
				    {
					     myQueue.getList().get(tempId).setMessage(myQueue.getList().get(tempId).getMessage()+ "\n" + strMessage);
					     if (tempId == 0)
					     {
					    	 myHold.setMsgLock(strMessage+"\n");
					     }
				    }
				    else
				    {
					 myQueue = new MessageQueue(counter, phoneNum, person, strMessage);
					
					 
					 counter++;
					 myQueue.addList();
					 myQueue.setEmpty(false);
					 myPop.setVisibleArrows();
				    }
				}
				
				
				
				 //
			}
			else
		    {
				
				
			  if (myHold.getOn())
			  {           
				 tempId = findId(); 
				 if (!(myHold.getFirst().equals(phoneNum))&&tempId==-1)
				 {
					 tempId = findId();
					 myHold.setFirst(phoneNum);
					 
					 if (tempId != -1)
					 {
					
					     myQueue.getList().get(tempId).setMessage(myQueue.getList().get(tempId).getMessage()+ "\n" + strMessage);
					 }
					 else
					 {
						
						
					   myQueue = new MessageQueue(counter, phoneNum, person, strMessage);
					
					   myQueue.addList();
					   myQueue.setProgress(true);
					   counter++;
					   myPop.setVisibleArrows();
					   
					 }
				 }
				 else 
				 {
	
					
					if (myQueue.getProgress()==true)
					{
						 //Log.d("Brady", ""+myQueue.getCurrentId()+"");
						if (myQueue.getList().get(myQueue.getCurrentId()).getPhoneNum().equals(phoneNum))
						{
						
						 myQueue.getList().get(myQueue.getCurrentId()).addMessage("\n"+strMessage);
						 changedText = myQueue.getList().get(myQueue.getCurrentId()).getMessage();
						}
						else
						{
					     tempId = findId();
						 myQueue.getList().get(tempId).addMessage("\n"+strMessage); 
						 changedText = myQueue.getList().get(myQueue.getCurrentId()).getMessage();
						}
						//Log.d("Receiver", "fuck me -- " + myHold.getFirst());
					}
					else
					{
					  myQueue.getList().get(myQueue.getCurrentId()).addMessage("\n"+strMessage); 
				 	  changedText = myHold.getBody();
				    }
			     	myPop.modifyText(changedText);
			     //	myQueue.getList().get(myQueue.getCurrentId()).setMessage("");
				 }
				 //Toast.makeText(contextNew, "hi", Toast.LENGTH_SHORT).show();
			  }
			  else
			  {
				  
				//Toast.makeText(contextNew, "Hi!!!!!", Toast.LENGTH_SHORT).show();
				if ((myQueue.getEmpty()==false))
				{
					myQueue.clean();
					counter = 0;
				}
				intent = new Intent(context, PopUpService.class);
				intent.putExtra("message", strMessage);
				intent.putExtra("phoneNum", person);
				intent.putExtra("number", number);
				timer = new Timer();
				timer.schedule(new TimerTask() {

					@Override
					public void run() {
						
						contextNew.startService(intent);
					}
				
				},1000);
				myQueue = new MessageQueue(counter, phoneNum, person, strMessage);
				myQueue.addList();
				myQueue.setEmpty(false);
				counter++;
				//myPop.setVisibleArrows();
				strMessage = "";
				myHold.setText("");
				myHold.setBodyBlank("");
				myHold.setBody("");
				myHold.setFirst(phoneNum);
				myHold.setOn(true);
			  }
		    }

	  } 
	 }
	}
}