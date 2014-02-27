package com.floats.floatingtexts;

import java.util.ArrayList;

import android.widget.ImageView;
import android.widget.TextView;

public class Holder{

	 static String myText;
	 static boolean stillOn = false;
	 static String pastString = "";
	 static TextView tempView;
	 static ImageView tempRight;
	 static ImageView tempLeft;
	 static String phoneNum;
	 static String firstNum;
	 static String lockedMsg ="";
	 static String lockedPhone;
	 static String lockedFirst = "";
	 int counter = 0;
	 static boolean alertStatus=true;
	 static int seconds = 5000;
	 static String lockedNumber="";
	 static boolean wasLocked = false;
	 static ArrayList<String>lockedMessageList=new ArrayList<String>();
	 static boolean blue;
	 static boolean purple;
	 static boolean green;
	 static boolean holoDark;
	 static boolean holoLight;
	 static boolean random=false;
	 static boolean checkSet = false;
	 static int color = -1;
	 static int fontColor = -1;
	 static int borderColor = -1;
	 static boolean solidColors;
	 static boolean fontColors;
	 static boolean fontChanged = false;

	public Holder() {
		// TODO Auto-generated constructor stub
	}
	
	public void setFontChanged(boolean t)
	{
		fontChanged = t;
	}
	
	public boolean getFontChanged()
	{
		return fontChanged;
	}
	public void setColor(int color)
	{
		this.color = color;
	}
	
	public void setBorderColor(int color)
	{
		borderColor = color;
	}
	
	public int getBorderColor()
	{
		return borderColor;
	}
	public void setFontColor(int color)
	{
		fontColor = color;
	}
	
	public void setFontColors(boolean t)
	{
		fontColors = t;
	}
	
	public boolean getFontColors()
	{
		return fontColors;
	}
	public int getFontColor()
	{
		return fontColor;
	}
	
	public void setSolidColors(boolean t)
	{
		solidColors = t;
	}
	
	public boolean getSolidColors()
	{
		return solidColors;
	}
	
	public int getColor()
	{
		return color;
	}
	public void setRandom(boolean t)
	{
		random = t;

	}
	
	public boolean getHoloDark()
	{
		return holoDark;
	}
	
	public void setHoloDark(boolean t)
	{
		holoDark = t;
	}
	
	public boolean getHoloLight()
	{
		return holoLight;
	}
	
	public void setHoloLight(boolean t)
	{
		holoLight = t;
	}
	public ImageView getTempLeft()
	{
		return tempLeft;
	}
	
	public void setTempLeft(ImageView t)
	{
		tempLeft = t;

	}
	
	public ImageView getTempRight()
	{
		return tempRight;
	}
	
	public void setTempRight(ImageView t)
	{
		tempRight = t;

	}
	
	public boolean getRandom()
	{
		return random;
	}
	
	public void setCheck(boolean t)
	{
		checkSet = t;

	}
	
	public boolean getCheck()
	{
		return checkSet;
	}
	
	public void setBlue(boolean t)
	{
		blue = t;
	}
	
	public boolean getBlue()
	{
		return blue;
	}
	
	public void setPurple(boolean t)
	{
		purple = t;
	}
	
	public boolean getPurple()
	{
		return purple;
	}
	
	public void setGreen(boolean t)
	{
		green = t;
	}
	
	public boolean getGreen()
	{
		return green;
	}
	
	public void setWasLocked(boolean t)
	{
		wasLocked = t;
	}
	
	public boolean getWasLocked()
	{
		return wasLocked;
	}
	public void setLockedNumber(String t)
	{
		lockedNumber = t;
	}
	
	public String getLockedNumber()
	{
		return lockedNumber;
		
	}
	
	public void setMessageList(String t)
	{
		lockedMessageList.add(t);
	}
	
	public ArrayList<String> getMessageList()
	{
		return lockedMessageList;
		
	}
	
	public void setDuration(int t)
	{
		seconds = t;
	}
	
	public int getDuration()
	{
		return seconds;
		
	}
	public void setStatus(boolean t)
	{
		alertStatus =t;
	}
	public boolean getStatus()
	{
		return alertStatus;
	}
	
	public void setText(String t)
	{
		myText = t;
	}
	public String getText()
	{
		return myText;
	}
	
	public void setLockFirst(String t)
	{
		lockedFirst = t;
	}
	public String getLockedFirst()
	{
		return lockedFirst;
	}
	
	public void setMsgLock(String t)
	{
		if (t!=null)
		lockedMsg += t;
	}
	
	public void setMsgLockNew(String t)
	{
		if (t!=null)
		lockedMsg = t;
	}
	public String getMsgLock()
	{
		return lockedMsg;
	}
	
	public void setMsgPhone(String t)
	{
		lockedPhone = t;
	}
	public String getMsgPhone()
	{
		return lockedPhone;
	}
	
	public void setFirst(String t)
	{
		firstNum = t;
	}
	public String getFirst()
	{
		return firstNum;
	}
	
	public void setPhone(String t)
	{
		phoneNum = t;
	}
	public String getPhone()
	{
		return phoneNum;
	}
	
	public void setTextView(TextView v)
	{
		tempView = v;
	}
	
	public TextView getTextView()
	{
		return tempView;
	}
	
	public void setBody(String t)
	{
		if (t!=null)
			if (counter==0)
			{
		      pastString +=t;
		      counter++;
			}
			else
				pastString +=t;
	}
	
	public void setBodyBlank(String t)
	{
		if (t!=null)
		pastString = t;
	}
	public String getBody()
	{
		return pastString;
	}
	
	public void setOn(boolean t)
	{
		stillOn = t;
	}
	public boolean getOn()
	{
		return stillOn;
	}
	
	
}
