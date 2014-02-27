package com.floats.floatingtexts;

import java.util.ArrayList;


public class MessageQueue {

	int id=0;
	String phoneNum;
	String person;
	String message;
	static int currentId = 0;
	static boolean messageQueueInProgress=false;
	boolean empty=true;
    static ArrayList<MessageQueue> keepList = new ArrayList<MessageQueue>();	
	public MessageQueue()
	{
		
	}
	public  MessageQueue(int myId, String myPhoneNum, String myPerson, String myMessage)
	{
		id = myId;
		phoneNum = myPhoneNum;
		person = myPerson;
		message = myMessage;
		empty = false;
	}
	
	public void addMessage(String t)
	{
		message+=t;
	}
	public boolean getProgress()
	{
		return messageQueueInProgress;
	}
	
	public void setProgress(boolean t)
	{
		messageQueueInProgress=t;
	}
	public int getCurrentId()
	{
		return currentId;
	}
	
	public void setCurrentId(int t)
	{
		currentId = t;
	}
	public int containsString(String s, String newText)
	{
		for (MessageQueue m : keepList)
		{
			if (m.getPhoneNum().equals(s))
			{
				//Toast.makeText(this, m.getId(), Toast.LENGTH_SHORT).show();
				//this.getList().get(m.getId()).setMessage(m.getMessage()+"\n"+newText);
				return m.getId();
			}
		}
		return -1;
	}
	public void addList()
	{
		keepList.add( this);
	}
	
	public ArrayList<MessageQueue> getList()
	{
		return keepList;
	}
	
	public boolean getEmpty()
	{
		return empty;
	}
	
	public void setEmpty(boolean t)
	{
		empty=t;
	}
	
	public void clean()
	{
		phoneNum="";
		id=0;
		person="";
		message="";
		messageQueueInProgress = false;
		keepList = new ArrayList<MessageQueue>();
	}
	
	public int getId ()
	{
		return id;
	}
	
	public void setId(int t)
	{
		id = t;
	}
	
	public String getPhoneNum ()
	{
		return phoneNum;
	}
	
	public void setPhoneNum(String t)
	{
		phoneNum=t;
	}
	
	public String getPerson ()
	{
		return person;
	}
	
	public void setPerson(String t)
	{
		person=t;
	}
	
	public String getMessage ()
	{
		return message;
	}
	
	public void setMessage(String t)
	{
		message=t;
	}
}
