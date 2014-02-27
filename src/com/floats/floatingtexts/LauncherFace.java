package com.floats.floatingtexts;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.floats.floatingtexts.R.color;
import com.google.analytics.tracking.android.EasyTracker;
import com.larswerkman.colorpicker.ColorPicker;
import com.larswerkman.colorpicker.ColorPicker.OnColorChangedListener;
import com.larswerkman.colorpicker.OpacityBar;
import com.larswerkman.colorpicker.SVBar;


public class LauncherFace extends Activity{
	
	private EditText feedbackText;
	private Button sendBtn;
	private Button submitBtn;
	private Button blue;
	private Button green;
	private Button purple;
	private Button holoDark;
	private Button holoLight;
	private ToggleButton random;
	private static ToggleButton toggle;
	private EditText duration;
	private String durationSeconds;
	private CheckBox myCheck;
	private static boolean alertStatus = true;
	private Holder myHold = new Holder();
	LinearLayout mLinearLayout;
	private TextView durationDisplay;
	private RelativeLayout myBack;
	private TextView instructionText;
	private int durationInt = 5;
	String email = null;
	private ColorPicker picker;
	private ColorPicker pickerFont;
	private ColorPicker pickerBorder;
	private SVBar svBar;
	private SVBar svBarFont;
	private SVBar svBarBorder;
	private TextView view1;
	private TextView view2;
	private TextView view3;
	private TextView view4;
	private TextView view5;
	private TextView view6;
	private TextView view7;
	private TextView view8;
	private TextView view9;
	private TextView view10;
	private TextView view11;
	private TextView view12;
	private TextView view13;
	int colorNew;
	private boolean fontChanged = false;

	private OpacityBar opacityBar;
	private static boolean alreadySaved=false;
	
	public LauncherFace()
	{
		
	}
	@Override
	public void onCreate (Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		
	    setContentView(R.layout.activity_main);
	    myBack = (RelativeLayout)findViewById(R.id.myBack);
	    feedbackText = (EditText)findViewById(R.id.feedbackResponse);
	    sendBtn = (Button)findViewById(R.id.sendFeedback);
	    submitBtn = (Button)findViewById(R.id.button1);
	    toggle = (ToggleButton)findViewById(R.id.toggleButton1);
	    duration = (EditText)findViewById(R.id.editText1);
	    durationDisplay=(TextView)findViewById(R.id.myDisplay);
	    mLinearLayout = (LinearLayout) findViewById(R.id.linearLayout_focus);
	    blue = (Button)findViewById(R.id.button2);
	    green = (Button)findViewById(R.id.button3);
	    purple = (Button)findViewById(R.id.Button02);
	    instructionText = (TextView)findViewById(R.id.TextView08);
	    myCheck = (CheckBox)findViewById(R.id.checkBox1);
	    myCheck.setBackgroundColor(color.red);
	    random = (ToggleButton)findViewById(R.id.Button01);
	    picker = (ColorPicker) findViewById(R.id.picker);
		svBar = (SVBar) findViewById(R.id.svbar);
		holoDark = (Button)findViewById(R.id.Button03);
		holoLight = (Button)findViewById(R.id.Button04);
		opacityBar = (OpacityBar) findViewById(R.id.opacitybar);
		pickerFont = (ColorPicker)findViewById(R.id.picker2);
		pickerBorder = (ColorPicker)findViewById(R.id.pickerBorder);
		svBarFont = (SVBar)findViewById(R.id.svbar2);
		svBarBorder = (SVBar)findViewById(R.id.SVBar01);
		picker.addSVBar(svBar);
		pickerBorder.addSVBar(svBarBorder);
	    colorNew = picker.getColor();
		pickerFont.addSVBar(svBarFont);
		picker.addOpacityBar(opacityBar);
		view1 = (TextView)findViewById(R.id.textView1);
		view2 = (TextView)findViewById(R.id.textView2);
		view3 = (TextView)findViewById(R.id.textView3);
		view4 = (TextView)findViewById(R.id.TextView01);
		view5 = (TextView)findViewById(R.id.TextView03);
		view6 = (TextView)findViewById(R.id.TextView05);
		view7 = (TextView)findViewById(R.id.TextView07);
		view8 = (TextView)findViewById(R.id.TextView10);
		view9 = (TextView)findViewById(R.id.TextView06);
		view10 = (TextView)findViewById(R.id.TextView04);
		view11 = (TextView)findViewById(R.id.TextView09);
		view12 = (TextView)findViewById(R.id.TextView11);
		view13 = (TextView)findViewById(R.id.TextView12);
		
	    loadSavedPreferences();
	    
	    holoLight.setOnClickListener(new OnClickListener()
	    {

			@Override
			public void onClick(View v) {
				setBGHoloLight();
				myHold.setFontChanged(false);
				savePreference("ColorValue", false,"Light");
				savePreference("FontChanged", false, null);
				
			}
	    	
	    });
	    
	    holoDark.setOnClickListener(new OnClickListener()
	    {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				setBGHoloDark();
				myHold.setFontChanged(false);
				savePreference("ColorValue", false,"Dark");
				savePreference("FontChanged", false, null);
			}
	    	
	    });
	    myCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

	    	   @Override
	    	   public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
	    		  if (isChecked)
	    		  {
	    			  onChecked();
	    			  durationDisplay.setText("Infinity!");
	    			  savePreference("InfinityValue", isChecked,null);
	    		  }
	    		  else
	    		  {
	    			  notChecked();
	    			  durationDisplay.setText("" +durationInt + " seconds");
	    			  savePreference("InfinityValue", isChecked,null);
	    		  }
	    			  
	    	   }
	    });

	    random.setOnCheckedChangeListener( new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (random.isChecked())
				{
					Log.d("Random", "Random On");
					myHold.setRandom(true);
					myHold.setBlue(false);
					myHold.setPurple(false);
					myHold.setGreen(false);
					myHold.setHoloDark(false);
					myHold.setHoloLight(false);
					myHold.setSolidColors(false);
					savePreference("RandomColor", true, null);
				}
				else
				{
					myHold.setRandom(false);
					savePreference("RandomColor", false, null);
					
				}
				
			}
	    	
	    });
	    

	    green.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				setBGGreen();
				myHold.setFontChanged(false);
		        savePreference("ColorValue", false,"Green");
		        savePreference("FontChanged", false, null);
			}
			
	    	
	    });
	    
	    purple.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				setBGPurple();
				myHold.setFontChanged(false);
		        savePreference("ColorValue", false,"Purple");
		        savePreference("FontChanged", false, null);
			}
	    	
	    });
	    
	    blue.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				setBGBlue();
				myHold.setFontChanged(false);
		        savePreference("ColorValue", false,"Blue");
		        savePreference("FontChanged", false, null);
				
			}
	    	
	    });

	    toggle.setOnCheckedChangeListener( new OnCheckedChangeListener() {
	        @Override
	        public void onCheckedChanged(CompoundButton toggleButton, boolean isChecked) {
	           if (toggle.isChecked())
	           {
	        	   alertStatus=true;
	        	   
	           }
	           else
	           {
	        	   alertStatus=false;  
	        	   //savePreference("ToggleValue", toggle.isChecked());

	           }
	           savePreference("ToggleValue", toggle.isChecked(),null);
	           myHold.setStatus(alertStatus);
	           
	        }
	    }) ;
	    
	    submitBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				int myTime;
				durationSeconds = duration.getText().toString();
				if (!durationSeconds.isEmpty())
				{
				  myTime = Integer.parseInt(durationSeconds);
				  if (myTime <0)
				  {
					  
				  }
				  else
				  {
				  myHold.setDuration(Integer.parseInt(durationSeconds)*1000);
				  durationInt = myTime;
				  duration.setText("");
				  durationDisplay.setText(durationSeconds + " seconds");
				  if (myCheck.isChecked())
				  {
					  myCheck.setChecked(false);
					  notChecked();
				  }
				  if (durationSeconds.equals("0"))
				  {
					  alertStatus = false;
					  toggle.setChecked(false);
				  }
				  else
				  {
					  alertStatus = true;
					  toggle.setChecked(true);
				  }
				  }
				  savePreference("DurationSeconds", false, durationSeconds);
				}
				  
			}
		});
	    
	    picker.setOnColorChangedListener(new ColorPicker.OnColorChangedListener()
	    {

			@Override
			public void onColorChanged(int color) {
				// TODO Auto-generated method stub
				myBack.setBackgroundColor(color); 
				
				myHold.setColor(color);
				myHold.setBlue(false);
				myHold.setPurple(false);
				myHold.setGreen(false);
				myHold.setHoloDark(false);
				myHold.setHoloLight(false);
				myHold.setRandom(false);
				random.setChecked(false);
				myHold.setSolidColors(true);
				pickerBorder.setVisibility(View.VISIBLE);
				svBarBorder.setVisibility(View.VISIBLE);
				view13.setVisibility(View.VISIBLE);
				savePreference("Allcolor", false,"Solid");
				savePreference("ColorValue-Color", false,""+color+"");
				
			}
	    	
	    });
	    
	    pickerFont.setOnColorChangedListener(new ColorPicker.OnColorChangedListener()
	    {
	    	@Override
			public void onColorChanged(int color) {
				// TODO Auto-generated method stub
	    		changeFonts(color);
				savePreference("FontColor", false, ""+color+"");
				myHold.setFontChanged(true);
				fontChanged = true;
				savePreference("FontChanged", fontChanged, null);
			}
	    });
	    
	    pickerBorder.setOnColorChangedListener(new ColorPicker.OnColorChangedListener()
	    {
	    	@Override
			public void onColorChanged(int color) {
				// TODO Auto-generated method stub
	    		myHold.setBorderColor(color);
				savePreference("Border", false, ""+color+"");

			}
	    });

	    
	    sendBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				email = feedbackText.getText().toString();
				if (!(email.isEmpty()))
				{
					Intent myEmailIntent = new Intent(android.content.Intent.ACTION_SEND);
					String emailAddress[] = {"floatingsms@gmail.com"};
					myEmailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, emailAddress);
					myEmailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Feedback");
					myEmailIntent.setType("plain/text");
					myEmailIntent.putExtra(android.content.Intent.EXTRA_TEXT, email);
					startActivity(myEmailIntent);
				}
			}
		});
	    
	    	
	    
	}
	
	private void savePreference(String key, boolean value, String value1) 
	{
		  // SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);	
		  // boolean toggleValue = sharedPreferences.getBoolean("ToggleValue", true);
		   SharedPreferences.Editor editor = getSharedPreferences("KeyChainPref",0).edit();
		   //Editor editor = sharedPreferences.edit();
		   if (value1!=null)
		   {
			   if (key.equals("Allcolor"))
			   {
				   key="ColorValue";
				   value1="Solid";
			   }
			   editor.putString(key, value1);
		   }
		   else
		   {
		      editor.putBoolean(key, value);
		   }
		   editor.commit();
		
	}
	

	private void changeFonts(int color)
	{
		
		if (myHold.getFontChanged()==true)
		{
			view1.setTextColor(color);
			view2.setTextColor(color);
			view3.setTextColor(color);
			view4.setTextColor(color);
			view5.setTextColor(color);
			view6.setTextColor(color);
			view7.setTextColor(color);
			view8.setTextColor(color);
			view9.setTextColor(color);
			view10.setTextColor(color);
			view11.setTextColor(color);
			view12.setTextColor(color);
			view13.setTextColor(color);
			durationDisplay.setTextColor(color);
			instructionText.setTextColor(color);
			myHold.setFontColor(color);
			toggle.setTextColor(color);
			submitBtn.setTextColor(color);
		}
	}
	
	public void changeFontsHoloLight(int color)
	{
		view1.setTextColor(color);
		view2.setTextColor(color);
		view3.setTextColor(color);
		view4.setTextColor(color);
		view5.setTextColor(color);
		view6.setTextColor(color);
		view7.setTextColor(color);
		view8.setTextColor(color);
		view9.setTextColor(color);
		view10.setTextColor(color);
		view11.setTextColor(color);
		view12.setTextColor(color);
		view13.setTextColor(color);
		durationDisplay.setTextColor(color);
		instructionText.setTextColor(color);
		myHold.setFontColor(color);
		toggle.setTextColor(color);
		submitBtn.setTextColor(color);
	}
	public void loadSavedPreferences() {
			
				 SharedPreferences sharedPreferences = getSharedPreferences("KeyChainPref",0);
				 boolean toggleValue = sharedPreferences.getBoolean("ToggleValue", true);
				 String durationSeconds = sharedPreferences.getString("DurationSeconds", "5");
				 String color = sharedPreferences.getString("ColorValue", "Blue");
				 String colorEx = sharedPreferences.getString("Allcolor", "Kobe");
				 boolean randomValue = sharedPreferences.getBoolean("RandomColor", false);
				 boolean infinityValue = sharedPreferences.getBoolean("InfinityValue", false);
				 int colorVal = Integer.parseInt(sharedPreferences.getString("ColorValue-Color", "0"));
				 int fontColor = Integer.parseInt(sharedPreferences.getString("FontColor", "-1"));
				 int border =  Integer.parseInt(sharedPreferences.getString("Border", "-1"));
				 myHold.setFontChanged(sharedPreferences.getBoolean("FontChanged", false));
				 
				 if (color!=null)
				 {
					 if (color.equals("Blue"))
					 {
						 setBGBlue();
					 }
					 else if (color.equals("Green"))
					 {
						 setBGGreen();
					 }
					 else if (color.equals("Purple"))
					 {
						 setBGPurple();
					 }
					 else if (color.equals("Dark"))
					 {
						 this.setBGHoloDark();
					 }
					 else if (color.equals("Light"))
					 {
						 this.setBGHoloLight();
						 changeFontsHoloLight(Color.BLACK);
					 }
					 else if (color.equals("Solid"))
					 {
						myBack.setBackgroundColor(colorVal); 
						myHold.setColor(colorVal);
						myHold.setBlue(false);
						myHold.setPurple(false);
						myHold.setGreen(false);
						myHold.setHoloDark(false);
						myHold.setHoloLight(false);
						myHold.setRandom(false);
						random.setChecked(false);
						myHold.setSolidColors(true);
						if (colorVal!=0)
							picker.setColor(colorVal);
						pickerBorder.setVisibility(View.VISIBLE);
						svBarBorder.setVisibility(View.VISIBLE);
						view13.setVisibility(View.VISIBLE);
						pickerBorder.setColor(border);
						myHold.setBorderColor(border);
					 }
				 }
				 
				 if (infinityValue)
				 {
					 onChecked();
	    			  durationDisplay.setText("Infinity!");
	    			  myCheck.setChecked(true);
				 }
				 else
				 {
					 notChecked();
	    			 durationDisplay.setText("" +durationInt + " seconds");
	    			 myCheck.setChecked(false);
				 }
				 
				 if (randomValue == true)
				 {
					Log.d("Random", "Random On-LOAD");
					random.setChecked(true);
					myHold.setRandom(true);
					myHold.setBlue(false);
					myHold.setPurple(false);
					myHold.setGreen(false);
					myHold.setHoloDark(false);
					myHold.setHoloLight(false);
					myHold.setSolidColors(false);
				 }
				 else
				 {
					 random.setChecked(false);
				 }
				 
				 if (toggleValue) {
		        	toggle.setChecked(true);
		        	alertStatus=true;
				 } else {
		        	toggle.setChecked(false);
		        	alertStatus=false;
				 }
				 //Log.d("Brady", ""+alertStatus+"");
				 if (durationSeconds !=null)
				 {
					 Log.d("Launch", durationSeconds);
					 durationDisplay.setText(durationSeconds + " seconds");
					 myHold.setDuration(Integer.parseInt(durationSeconds)*1000);
				 }
				 
				 if (fontColor != -1)
				 {
					 changeFonts(fontColor);
					 pickerFont.setColor(fontColor);
				 }
				 
				 
				 
				 myHold.setStatus(alertStatus);

			}

	public void onChecked()
	{
		   myHold.setCheck(true);
		   instructionText.setVisibility(View.VISIBLE);
	}
	
	public void notChecked()
	{
		   myHold.setCheck(false);
		   instructionText.setVisibility(View.INVISIBLE);
	}
	
	@Override
	protected void onResume()
	{
	    super.onResume();

	    //do not give the editbox focus automatically when activity starts
	    duration.clearFocus();
	    mLinearLayout.requestFocus();
	}
	
	public void setBGGreen()
	{
		picker.setColor (colorNew);
		pickerFont.setColor(colorNew);
		changeFonts(Color.WHITE);
		pickerBorder.setVisibility(View.INVISIBLE);
		view13.setVisibility(View.INVISIBLE);
		svBarBorder.setVisibility(View.INVISIBLE);
		myBack.setBackgroundResource(R.drawable.text_main_green);
		myHold.setGreen(true);
		toggle.setBackgroundResource(R.drawable.send_layout_green);
		submitBtn.setBackgroundResource(R.drawable.send_layout_green);
		sendBtn.setBackgroundResource(R.drawable.send_layout_green);
		myHold.setBlue(false);
		myHold.setSolidColors(false);
		myHold.setHoloDark(false);
		myHold.setHoloLight(false);
		myHold.setPurple(false);
		if (random.isChecked())
		{
			myHold.setRandom(false);
			random.setChecked(false);
		}
	}
	
	public void setBGHoloDark()
	{
		picker.setColor(colorNew);
		pickerFont.setColor(colorNew);
		changeFonts(Color.WHITE);
		pickerBorder.setVisibility(View.INVISIBLE);
		view13.setVisibility(View.INVISIBLE);
		svBarBorder.setVisibility(View.INVISIBLE);
		myBack.setBackgroundResource(R.drawable.text_main_dark);
		myHold.setBlue(false);
		toggle.setBackgroundResource(R.drawable.send_layout_dark);
		toggle.setTextColor(Color.WHITE);
		submitBtn.setBackgroundResource(R.drawable.send_layout_dark);
		submitBtn.setTextColor(Color.WHITE);
		sendBtn.setBackgroundResource(R.drawable.send_layout_dark);
		sendBtn.setTextColor(Color.WHITE);
		myHold.setGreen(false);
		myHold.setSolidColors(false);
		myHold.setHoloLight(false);
		myHold.setHoloDark(true);
		myHold.setPurple(false);
		
		if (random.isChecked())
		{
			myHold.setRandom(false);
			random.setChecked(false);
		}

	}
	
	public void setBGHoloLight()
	{
		picker.setColor(colorNew);
		pickerFont.setColor(colorNew);
		changeFonts(Color.BLACK);
		pickerBorder.setVisibility(View.INVISIBLE);
		view13.setVisibility(View.INVISIBLE);
		svBarBorder.setVisibility(View.INVISIBLE);
		myBack.setBackgroundResource(R.drawable.text_main_light);
		myHold.setBlue(false);
		toggle.setBackgroundResource(R.drawable.send_layout_light);
		toggle.setTextColor(Color.BLACK);
		submitBtn.setTextColor(Color.BLACK);
		sendBtn.setTextColor(Color.BLACK);
		submitBtn.setBackgroundResource(R.drawable.send_layout_light);
		sendBtn.setBackgroundResource(R.drawable.send_layout_light);
		myHold.setGreen(false);
		myHold.setSolidColors(false);
		myHold.setHoloLight(true);
		myHold.setHoloDark(false);
		myHold.setPurple(false);
		
		if (random.isChecked())
		{
			myHold.setRandom(false);
			random.setChecked(false);
		}

	}
	
	public void setBGBlue()
	{
		picker.setColor(colorNew);
		pickerFont.setColor(colorNew);
		changeFonts(Color.WHITE);
		pickerBorder.setVisibility(View.INVISIBLE);
		view13.setVisibility(View.INVISIBLE);
		svBarBorder.setVisibility(View.INVISIBLE);
		myBack.setBackgroundResource(R.drawable.text_main);
		myHold.setBlue(true);
		toggle.setBackgroundResource(R.drawable.send_layout);
		submitBtn.setBackgroundResource(R.drawable.send_layout);
		sendBtn.setBackgroundResource(R.drawable.send_layout);
		myHold.setGreen(false);
		myHold.setSolidColors(false);
		myHold.setHoloDark(false);
		myHold.setHoloLight(false);
		myHold.setPurple(false);
		
		if (random.isChecked())
		{
			myHold.setRandom(false);
			random.setChecked(false);
		}

	}
	
	public void setBGPurple()
	{
		picker.setColor(colorNew);
		pickerFont.setColor(colorNew);
		changeFonts(Color.WHITE);
		pickerBorder.setVisibility(View.INVISIBLE);
		view13.setVisibility(View.INVISIBLE);
		svBarBorder.setVisibility(View.INVISIBLE);
		myBack.setBackgroundResource(R.drawable.text_main_purple);
		myHold.setPurple(true);
		toggle.setBackgroundResource(R.drawable.send_layout_purple);
		submitBtn.setBackgroundResource(R.drawable.send_layout_purple);
		sendBtn.setBackgroundResource(R.drawable.send_layout_purple);
		myHold.setGreen(false);
		myHold.setBlue(false);
		myHold.setSolidColors(false);
		
		myHold.setHoloDark(false);
		myHold.setHoloLight(false);
		if (random.isChecked())
		{
			myHold.setRandom(false);
			random.setChecked(false);
		}
	}
	
	 @Override
	  public void onStart() {
	    super.onStart();
	    EasyTracker.getInstance(this).activityStart(this);  // Add this method.
	  }
	 
	 @Override
	  public void onStop() {
	    super.onStop();
	    EasyTracker.getInstance(this).activityStop(this);  // Add this method.
	  }
	 
}
	
	
