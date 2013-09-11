package com.engine9.test;

import com.engine9.FavouriteActivity;
import com.engine9.FavouriteManager;
import com.engine9.StopMapActivity;
import com.jayway.android.robotium.solo.Solo;

import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;

public class StopMapActivityTest extends ActivityInstrumentationTestCase2<StopMapActivity>{

	private Solo solo;
	private StopMapActivity sActivity;
	private String location = "uq";
	public StopMapActivityTest() {
		super(StopMapActivity.class);
		// TODO Auto-generated constructor stub
	}
	
	public void testStopsFromInput() throws Exception{
		location = "uq";
		setUp();
		assertTrue(sActivity.stopVector.size() != 0);
	}
	
	public void testStopsFromLocation() throws Exception{
		location = "";
		setUp();
		assertTrue(sActivity.stopVector.size() != 0);
	}
	
	
	
	
	
	protected void setUp() throws Exception{
		
		Intent i = new Intent();
		i.putExtra("timeURL", "http://deco3801-005.uqcloud.net/stops-from-location/?location=" + location);
		setActivityIntent(i);
		
		sActivity = getActivity();
		solo = new Solo(getInstrumentation(), sActivity);
		
		try {
		    Thread.sleep(6000);
		  } catch (InterruptedException e) {
		    e.printStackTrace();
		  }
	}
	
	
	@Override
	public void tearDown() throws Exception {
	        solo.finishOpenedActivities();
	}

}
