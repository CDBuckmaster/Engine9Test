package com.engine9.test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import com.engine9.TimetableActivity;
import com.jayway.android.robotium.solo.Solo;

import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;
import android.widget.ListView;

public class TimetableActivityTest extends ActivityInstrumentationTestCase2<TimetableActivity> {
	
	private Solo solo;
	private TimetableActivity tActivity;
	
	public TimetableActivityTest(){
		super(TimetableActivity.class);
		
	}
	
	public void testNormal() throws Exception{
		assertTrue(tActivity.getIntent().getStringExtra("timeURL") != null);
		
		tActivity.tRequest.get(15000, TimeUnit.MILLISECONDS);
		Log.e("DEBUG", tActivity.tRequest.getStatus().toString());
		
		ListView lv = (ListView) solo.getView(com.engine9.R.id.list_view);
		assertFalse(lv.getAdapter().isEmpty());
		
	}
	
	protected void setUp() throws Exception{
		
		Intent i = new Intent();
		i.putExtra("timeURL", "https://dl.dropboxusercontent.com/u/26635718/timetableMilton.json");
		setActivityIntent(i);
		
		tActivity = getActivity();
		solo = new Solo(getInstrumentation(), tActivity);
		
		try {
		    Thread.sleep(12000);
		  } catch (InterruptedException e) {
		    e.printStackTrace();
		  }
		
		
		
	}
	
	@Override
	public void tearDown() throws Exception {
	        solo.finishOpenedActivities();
	}

}
