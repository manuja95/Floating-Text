package com.floats.floatingtexts;

import java.util.ArrayList;

import com.floats.floatingtexts.R;
import com.larswerkman.colorpicker.ColorPicker;

import android.net.Uri;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.app.Service;
import android.telephony.SmsManager;
import android.text.InputType;
import android.text.method.ScrollingMovementMethod;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;

import android.app.Service;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.IBinder;
import android.view.Display;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class PopUpService extends Service {
  
  private ColorPicker myPick;
  private WindowManager mWindowManager;
  private ImageView popupAlert;
  private TextView alertText;
  private TextView sender;
  private Button exitBtn;
  private View myView;
  private ImageView right;
   private ImageView left;
   private String text;
  private Context myContext;
  private RelativeLayout layout;
  private int timeToExit =40000;
  private boolean autoExitFlag = true;
  private RelativeLayout fullPopup;
  private TextView replyText;
  private MessageQueue myQueue = new MessageQueue();
  private boolean afterQueue = false;
  int counter=0;
  int width;
  int height;
  //Dynamic Coding
  private TextView title;
  private TextView message;
  private Button send;
  private EditText response;
  private RelativeLayout messageBar;
  private String typeResponse;
  private String phoneNumber;
  private boolean alreadyWent = false;
  static ArrayList<Integer> visitedList = new ArrayList<Integer>();
  private GestureDetector gestureDetector;
  Holder myHold = new Holder();
  
  public PopUpService()
  {
	  
  }
  @Override 
  public void onCreate() {
    super.onCreate();

  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    if (popupAlert != null) mWindowManager.removeView(popupAlert);
  }

  public void modifyText(String t)
	{
	   
		if (myHold.getTextView()!=null)
		myHold.getTextView().setText(t);

				
	}
  
  public void setVisibleArrows()
  {
	    if (myHold.getTempLeft()!=null||myHold.getTempRight()!=null)
	    {
	     myHold.getTempLeft().setVisibility(View.VISIBLE);
	     myHold.getTempRight().setVisibility(View.VISIBLE);
	    }
	    
	  
  }
  @Override
  public int onStartCommand(Intent intent, int flags, int startId) {
	  final Intent myInt = intent;
	 
	  text = new String();
	  message = new TextView(this);
	  send = new Button(this);
	  
	  response = new EditText(this);
	  text = (String) intent.getExtras().get("message"); 
	  myHold.setPhone((String)intent.getExtras().get("phoneNum"));
	  myHold.setBody(text);

	  mWindowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
	  LayoutInflater layoutInflater = (LayoutInflater)this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	  myView = layoutInflater.inflate(R.layout.popups, null );
	  message = (TextView) myView.findViewById(R.id.message);
	  message.setText(text);
	  response =(EditText)myView.findViewById(R.id.editText1);
	  exitBtn = (Button)myView.findViewById(R.id.exit);
	  send = (Button)myView.findViewById(R.id.sendBtn);
	  left = (ImageView)myView.findViewById(R.id.left);
	  right = (ImageView)myView.findViewById(R.id.right);
	  
	  if (myQueue.getList().size()<=1)
	  {
		  left.setVisibility(View.INVISIBLE);
		  right.setVisibility(View.INVISIBLE);
	  }
	  title = (TextView)myView.findViewById(R.id.title);
	  messageBar = (RelativeLayout)myView.findViewById(R.id.bottom_bar);
	  fullPopup = (RelativeLayout)myView.findViewById(R.id.popupbar);
	
	  replyText = (TextView)myView.findViewById(R.id.reply_text);
	  
	  exitBtn.bringToFront();
	  title.setText((String)intent.getExtras().get("phoneNum"));
	  myHold.setTextView(message);
	  myHold.setTempLeft(left);
	  myHold.setTempRight(right);
	  
	  if (myHold.getGreen()==true)
	  {
		  setGreen(); 		  
	  }
	  else if (myHold.getPurple()==true)
	  {
		  setPurple();
	  }
	  else if (myHold.getBlue()==true)
	  {
		  setBlue();
	  }
	  else if (myHold.getSolidColors()==true)
	  {
		  //Log.d("Brady","hi1");
		  setSolidColors();
	  }
	  else if (myHold.getHoloDark()==true)
	  {
		  this.setHoloDark();
	  }
	  else if (myHold.getHoloLight()==true)
	  {
		  this.setHoloLight();
	  }
	  else if (myHold.getRandom()==true)
	  {
		  
		  int rand = 1 + (int)(Math.random() * ((3 - 1) + 1));
		  if (rand==1)
		  {
			  setGreen();
		  }
		  else if (rand==2)
		  {
			  setBlue();
		  }
		  else
		  {
			  setPurple();
		  }
	    
	  }
	  message.setMovementMethod(new ScrollingMovementMethod());
	  
	  if (myHold.getCheck()==false)
	  {
		  startExitHanlder();
	  }
	
	    

	  final WindowManager.LayoutParams params = new WindowManager.LayoutParams(
	        WindowManager.LayoutParams.FILL_PARENT,
	        WindowManager.LayoutParams.WRAP_CONTENT,
	        WindowManager.LayoutParams.TYPE_SYSTEM_ALERT,
	        WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE |
	        WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH,
	        PixelFormat.TRANSLUCENT );
	    params.gravity=Gravity.TOP|Gravity.CENTER_HORIZONTAL;
	    mWindowManager.addView(myView, params);
	    
	    myView.setOnTouchListener(new View.OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if(event.getAction() != MotionEvent.ACTION_OUTSIDE){ 
					params.flags = params.flags & ~WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;

					InputMethodManager imm = (InputMethodManager)getSystemService(
						      Context.INPUT_METHOD_SERVICE);
						imm.hideSoftInputFromWindow(response.getWindowToken(), 0);
				      //update the view
					  mWindowManager.updateViewLayout(myView, params);
				}
				return false;
			}
		});
	    
	    
	    replyText.setOnClickListener(new View.OnClickListener()
	    {

			@Override
			public void onClick(View v) {
				 autoExitFlag = false;		
	             myHold.setOn(true);
	             alreadyWent=true;
	             messageBar.setVisibility(View.VISIBLE);
	             response.setVisibility(View.VISIBLE);
	             send.setVisibility(View.VISIBLE);
	             response.setFocusable(true);	             
				 params.flags = params.flags & ~WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;


			      //update the view
				  mWindowManager.updateViewLayout(myView, params);
				  InputMethodManager mgr = (InputMethodManager) getSystemService   (Context.INPUT_METHOD_SERVICE);
				    // only will trigger it if no physical keyboard is open
				  mgr.showSoftInput(response, InputMethodManager.SHOW_IMPLICIT);

				
			}
	    	
	    });
	    response.setOnClickListener(new View.OnClickListener()
		  {

			@Override
			public void onClick(View v) {
				
				  autoExitFlag = false;		
	              myHold.setOn(true);
	              //params.flags = params.flags & ~WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;


			       //update the view
				  //mWindowManager.updateViewLayout(myView, params);
			}
			  
		  });
		  
		  exitBtn.setOnClickListener(new View.OnClickListener()
		  {

			@Override
			public void onClick(View v) {
				 mWindowManager.removeView(myView);
				 autoExitFlag = false;
	             myHold.setBodyBlank("");
				 myHold.setOn(false);
				 markMessageRead(PopUpService.this, myQueue.getList().get(myQueue.getCurrentId()).getPhoneNum(),myQueue.getList().get(myQueue.getCurrentId()).getMessage() );
				 clearText();
				 stopSelf();

			}
			  
		  });
		  
		  send.setOnClickListener(new OnClickListener()
		  {

			@Override
			public void onClick(View v) {
				typeResponse = response.getText().toString();
				//Put if statement
				if (counter==0&&myQueue.getProgress()==false)
				{
				  phoneNumber = (String)myInt.getExtras().get("number");
				}
				else
				{
				  phoneNumber = myQueue.getList().get(counter).getPhoneNum();
				}
				
				if (!(typeResponse.isEmpty())&&(!(phoneNumber.isEmpty())))
				   {

				     SmsManager sms = SmsManager.getDefault();
				     sms.sendTextMessage(phoneNumber, null, typeResponse, null, null);
				     Toast.makeText(PopUpService.this, "Message sent!", Toast.LENGTH_SHORT).show();

				     insertSMS(phoneNumber,typeResponse);
				     
				     if(myQueue.getList().get(myQueue.getCurrentId()).getMessage().contains("\n"))
				     {
				    	 
				     }
				     else
				     {
				       markMessageRead(PopUpService.this, myQueue.getList().get(myQueue.getCurrentId()).getPhoneNum(),myQueue.getList().get(myQueue.getCurrentId()).getMessage() );
				      //Log.d("Brady", myQueue.getList().get(myQueue.getCurrentId()).getMessage());
				     }
				     if (myQueue.getProgress()==true)
				     {
				    	 afterSendQueue();
				    	 myHold.setBodyBlank("");
				    	 response.setText("");
				     }
				     else
				     {
				       mWindowManager.removeView(myView);
		               myHold.setBodyBlank("");

					   myHold.setOn(false);
					   clearText();
					   stopSelf();
				     }
				   }
				else
				   response.setHint("Please type a message");
				 
			}
			  
		  });
			  
		  message.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				markMessageRead(PopUpService.this, myQueue.getList().get(counter).getPhoneNum(),myQueue.getList().get(counter).getMessage() );
			    //Log.d("Brady12", myQueue.getList().get(myQueue.getCurrentId()).getMessage());

			}
			  
		  });
		  
		  message.setOnTouchListener(new OnSwipeTouchListener() {

			    public void onSwipeLeft() {
			    	if (myQueue.getProgress()==true)
			    	{
				      if ((left.getVisibility()==View.INVISIBLE)||(right.getVisibility()==View.INVISIBLE))
				      {
				         left.setVisibility(View.VISIBLE);
				         right.setVisibility(View.VISIBLE);
				      }
				     
			    	 autoExitFlag = false;		
		             myHold.setOn(true);

		        	 if(counter < myQueue.getList().size()-1)
		        	 {
		              counter++;
		     
		        	 }
		 
		            myQueue.setCurrentId(counter);
		            message.setText(myQueue.getList().get(counter).getMessage());
		            title.setText(myQueue.getList().get(counter).getPerson());
		            markMessageRead(PopUpService.this, myQueue.getList().get(counter).getPhoneNum(),myQueue.getList().get(counter).getMessage() );			       }
				      // Log.d("Brady", myQueue.getList().get(myQueue.getCurrentId()).getMessage());

			    }
			    public void onSwipeRight() {
			    	if (myQueue.getProgress()==true)
			    	{
			         if ((left.getVisibility()==View.INVISIBLE)||(right.getVisibility()==View.INVISIBLE))
			         {
			        	 left.setVisibility(View.VISIBLE);
			        	 right.setVisibility(View.VISIBLE);
			         }
			    	 autoExitFlag = false;		
		             myHold.setOn(true);
		             //("popup", "right "+ myQueue.getList().size()+"");
		             if (counter > 0)
		             {
		            	 
		              counter--;
		              //Toast.makeText(PopUpService.this, ""+myQueue.getList().size()+" " + counter+"", Toast.LENGTH_SHORT).show();
		             }
	
		             myQueue.setCurrentId(counter);
		             message.setText(myQueue.getList().get(counter).getMessage());
		             title.setText(myQueue.getList().get(counter).getPerson());
		             markMessageRead(PopUpService.this, myQueue.getList().get(counter).getPhoneNum(),myQueue.getList().get(counter).getMessage() );
				    // Log.d("Brady", myQueue.getList().get(myQueue.getCurrentId()).getMessage());

			    	}
			      }
			    
			    public void onSwipeBottom()
			    {
			    	message.setMovementMethod(new ScrollingMovementMethod());
			    }
			    
			    public void onSwipeTop()
			    {
			    	message.setMovementMethod(new ScrollingMovementMethod());
			    }
			});
           
	    
	stopSelf();
	return startId;
	  
  }
  
  public void afterSendQueue()
  {
	  afterQueue=true;
	  if (myQueue.getCurrentId() == 0)
	  {
		    
		  myQueue.getList().remove(myQueue.getCurrentId());
		    if(myQueue.getList().size()!=0)
		    {
	         message.setText(myQueue.getList().get(counter).getMessage());
	         title.setText(myQueue.getList().get(counter).getPerson());
	         myQueue.setCurrentId(counter);
	         if (myQueue.getList().size()==1)
	         {
	        	 left.setVisibility(View.INVISIBLE);
			     right.setVisibility(View.INVISIBLE);
	         }
		    }
		    else
		    {

		    	myQueue.setProgress(false);
		        left.setVisibility(View.INVISIBLE);
		    	right.setVisibility(View.INVISIBLE);
			       mWindowManager.removeView(myView);
	               myHold.setBodyBlank("");

				   myHold.setOn(false);
				   clearText();
				   stopSelf();
		    }
	       
	  }
	  else
	  {
		  myQueue.getList().remove(myQueue.getCurrentId());
		 
		  counter=0;  
		 // int temp = myQueue.getCurrentId();
		  myQueue.setCurrentId(counter);
	      message.setText(myQueue.getList().get(counter).getMessage());
	      title.setText(myQueue.getList().get(counter).getPerson());
	         if (myQueue.getList().size()==1)
	         {
	        	 left.setVisibility(View.INVISIBLE);
			     right.setVisibility(View.INVISIBLE);
	         }
	      
	  }
 	
  }
  
  private void markMessageRead(Context context, String number, String body) 
  {

      Uri uri = Uri.parse("content://sms/inbox");
      Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
      try{

      while (cursor.moveToNext()) {
              if ((cursor.getString(cursor.getColumnIndex("address")).equals(number)) && (cursor.getInt(cursor.getColumnIndex("read")) == 0)) {
                  if (cursor.getString(cursor.getColumnIndex("body")).startsWith(body)) {
                      String SmsMessageId = cursor.getString(cursor.getColumnIndex("_id"));
                      ContentValues values = new ContentValues();
                      values.put("read", true);
                      context.getContentResolver().update(Uri.parse("content://sms/inbox"), values, "_id=" + SmsMessageId, null);
                      return;
                  }
              }
          }
       }catch(Exception e)
    {
      Log.e("Mark Read", "Error in Read: "+e.toString());
    }
  }
  
  public void setGreen()
  {
	  title.setBackgroundResource(R.drawable.text_pop_up_top_green);
	  message.setBackgroundResource(R.drawable.text_pop_up_bottom_green);
	  messageBar.setBackgroundResource(R.drawable.message_bar_green);
	  send.setBackgroundResource(R.drawable.send_layout_green);
	  setFontColors();
  }
  
  public void setHoloDark()
  {
	  title.setBackgroundResource(R.drawable.text_pop_up_top_black);
	  message.setBackgroundResource(R.drawable.text_pop_up_bottom_black);
	  messageBar.setBackgroundResource(R.drawable.message_bar_black);
	  send.setBackgroundResource(R.drawable.send_layout_dark);
	  GradientDrawable drawable = new GradientDrawable();
	  drawable.setCornerRadii(new float[] { 0, 0, 20, 20,
             0, 0, 0, 0 });
	  drawable.setColor(getResources().getColor(R.color.sendLightColorBlack ));
	  drawable.setStroke(3, getResources().getColor(R.color.borderHoloDark));
	  exitBtn.setBackgroundDrawable(drawable);
	  setFontColors();
  }
  
  public void setHoloLight()
  {
	  title.setBackgroundResource(R.drawable.text_pop_up_top_light);
	  message.setBackgroundResource(R.drawable.text_pop_up_bottom_light);
	  messageBar.setBackgroundResource(R.drawable.message_bar_light);
	  send.setBackgroundResource(R.drawable.send_layout_light);
	  GradientDrawable drawable = new GradientDrawable();
	  drawable.setCornerRadii(new float[] { 0, 0, 20, 20,
             0, 0, 0, 0 });
	  drawable.setColor(getResources().getColor(R.color.holoLight));
	  drawable.setStroke(3, getResources().getColor(R.color.holoLightBorder));
	  exitBtn.setBackgroundDrawable(drawable);
	  title.setTextColor(Color.BLACK);
		message.setTextColor(Color.BLACK);
		send.setTextColor(Color.BLACK);
	  replyText.setTextColor(Color.BLACK);
	  exitBtn.setTextColor(Color.BLACK);
	  setFontColors();
  }
  
  public void setBlue()
  {
	  title.setBackgroundResource(R.drawable.text_pop_up_top);
	  message.setBackgroundResource(R.drawable.text_pop_up_bottom);
	  messageBar.setBackgroundResource(R.drawable.message_bar);
	  send.setBackgroundResource(R.drawable.send_layout);
	  setFontColors();
  }
  
  public void setSolidColors()
  {
	  //Log.d("Brady", "solid colors out");
	  if (myHold.getColor() != -1)
	  {
			GradientDrawable drawable = new GradientDrawable();
			GradientDrawable drawableTitle = new GradientDrawable();
			GradientDrawable drawableBar = new GradientDrawable();
			GradientDrawable drawableSend = new GradientDrawable();
			drawable.setColor(myHold.getColor());
			drawableTitle.setColor(myHold.getColor());
			drawableBar.setColor(myHold.getColor());
			drawableSend.setColor(myHold.getColor());
			setFontColors();
			drawable.setCornerRadii(new float[] { 0, 0, 0, 0,
                   20, 20, 20, 20 });
			drawableTitle.setCornerRadii(new float[] { 35, 35, 35, 35,
	                   0, 0, 0, 0 });
			drawableBar.setCornerRadii(new float[] { 10, 10, 10, 10,
	                   10, 10, 10, 10 });
			drawableSend.setCornerRadii(new float[] { 15, 15, 15, 15,
	                   15, 15, 15, 15 });
			
			if (myHold.getBorderColor()!=-1)
			{
				drawable.setStroke(2, myHold.getBorderColor());
				drawableTitle.setStroke(2, myHold.getBorderColor());
				drawableBar.setStroke(2, myHold.getBorderColor());
				drawableSend.setStroke(2, myHold.getBorderColor());
			}
			else
			{
			    drawable.setStroke(2, Color.WHITE);
			    drawableTitle.setStroke(2, Color.WHITE);
			    drawableBar.setStroke(2, Color.WHITE);
			    drawableSend.setStroke(2, Color.WHITE);
			}
			message.setBackground(drawable);
			title.setBackground(drawableTitle);
			messageBar.setBackground(drawableBar);
			send.setBackground(drawableSend);
	  }
  }
  
  private void setFontColors()
  {
	  if (myHold.getFontColor()!=-1 && myHold.getFontChanged()==true)
	  {
		   
			title.setTextColor(myHold.getFontColor());
			message.setTextColor(myHold.getFontColor());
			send.setTextColor(myHold.getFontColor());
	   }
  }
  public void setPurple()
  {
	  title.setBackgroundResource(R.drawable.text_pop_up_top_purple);
	  message.setBackgroundResource(R.drawable.text_pop_up_bottom_purple);
	  messageBar.setBackgroundResource(R.drawable.message_bar_purple);
	  send.setBackgroundResource(R.drawable.send_layout_purple);
	  setFontColors();
	  
  }
  
  public void insertSMS(String address,String body){
	  ContentResolver resolver = null;
	  resolver = this.getContentResolver();//context is your instance of Activity 
	  ContentValues values = new ContentValues();
	  values.put("address", address);
	  values.put("body", body);
	  //values.put("read",true);
	  resolver.insert(Uri.parse("content://sms/sent"), values);
	  //getContentResolver().update(Uri.parse("content://sms/"),values, "_id="+SmsMessageId, null);	  
      Cursor cursor = this.getContentResolver().query(Uri.parse("content://sms/sent"), null, null, null, null);
          try{

          while (cursor.moveToNext()) {
                  if ((cursor.getString(cursor.getColumnIndex("address")).equals(phoneNumber)) && (cursor.getInt(cursor.getColumnIndex("read")) == 0)) {
                      if (cursor.getString(cursor.getColumnIndex("body")).startsWith(body)) {
                          String SmsMessageId = cursor.getString(cursor.getColumnIndex("_id"));        
                          values.put("read", true);
                          resolver.update(Uri.parse("content://sms/inbox"), values, "_id=" + SmsMessageId, null);
                          
                      }
                  }
              }
     }catch(Exception e)
     {
         Log.e("Mark Read", "Error in Read: "+e.toString());
     }
   }
  
  public void clearText()
  {
		message.setText("");
  }
  
  private void startExitHanlder()
	{
		myHold.setOn(true);
	    Handler handler = new Handler();
	    handler.postDelayed(new Runnable()
	    {

	        @Override
	        public void run()
	        {
	            if(autoExitFlag==true) 
	            {
	            	mWindowManager.removeView(myView);
	            	myHold.setOn(false);
	            	myHold.setBodyBlank("");
	            	clearText();
	            	 
	            }
	   
	        }
	    }, myHold.getDuration());
	   // myHold.setOn(false);
		
	}
  @Override
  public IBinder onBind(Intent arg0) {
	// TODO Auto-generated method stub
	return null;
  }
  
  

}
