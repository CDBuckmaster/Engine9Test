package com.engine9.test;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import com.engine9.Listing;
import com.engine9.TimetableActivity;
import com.engine9.FavouriteManager;
import com.jayway.android.robotium.solo.Solo;

import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class TimetableActivityTest extends ActivityInstrumentationTestCase2<TimetableActivity> {
	
	private Solo solo;
	private TimetableActivity tActivity;
	
	public TimetableActivityTest(){
		super(TimetableActivity.class);
		
	}
	
	public void testNormalLaunch() throws Exception{
		assertTrue(tActivity.getIntent().getStringExtra("timeURL") != null);
		
		ListView lv = (ListView) solo.getView(com.engine9.R.id.list_view);
		assertFalse(lv.getAdapter().isEmpty());
		
	}
	
	public void testAddFavourite() throws Exception{
		ListView lv = (ListView) tActivity.findViewById(com.engine9.R.id.list_view);
		View v = lv.getChildAt(0);
		
		TextView tv = (TextView)  v.findViewById(com.engine9.R.id.code);
		String code = tv.getText().toString();
		
		final Button b = (Button) v.findViewById(com.engine9.R.id.add_fav_button);
		try {
			runTestOnUiThread(new Runnable() {

			    @Override
			    public void run() {
			      b.performClick();
			    }
			  });
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertTrue(FavouriteManager.inFavourites(tActivity.getApplicationContext(), code));
		
		
	}
	
	public void testDeleteFavourite() throws Exception{
		ListView lv = (ListView) tActivity.findViewById(com.engine9.R.id.list_view);
		View v = lv.getChildAt(0);
		
		TextView tv = (TextView)  v.findViewById(com.engine9.R.id.code);
		String code = tv.getText().toString();
		
		final Button b = (Button) v.findViewById(com.engine9.R.id.add_fav_button);
		try {
			runTestOnUiThread(new Runnable() {

			    @Override
			    public void run() {
			      b.performClick();
			    }
			  });
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertFalse(FavouriteManager.inFavourites(tActivity.getApplicationContext(), code));
	}
	
	public void testOnlyFavourites() throws Exception{
		solo.clickOnButton("Favourite routes only");
		
		ListView lv = (ListView) tActivity.findViewById(com.engine9.R.id.list_view);
		for(int i = 0; i < lv.getAdapter().getCount(); i ++){
			View v = lv.getChildAt(0);
			
			TextView tv = (TextView)  v.findViewById(com.engine9.R.id.code);
			String code = tv.getText().toString();
			
			assertTrue(FavouriteManager.inFavourites(tActivity.getApplicationContext(), code));
		}
		
	}
	
	
	public void testTimeUpdate() throws Exception{
		assertTrue(tActivity.getIntent().getStringExtra("timeURL") != null);
		
		ListView lv = (ListView) solo.getView(com.engine9.R.id.list_view);
		assertFalse(lv.getAdapter().isEmpty());
		
		ArrayList<Listing> al = new ArrayList<Listing>();
		
		for(int i = 0; i < lv.getAdapter().getCount(); i ++){
			Listing l = (Listing) lv.getAdapter().getItem(i);
			if (((l.time * 10  - System.currentTimeMillis())/ 60000) > -5){
				al.add(l);
			}
		}
		
		Thread.sleep(1000 * 60);
		
		assertTrue(al.size() == lv.getAdapter().getCount());
	}
	
	
	protected void setUp() throws Exception{
		
		Intent i = new Intent();
		i.putExtra("timeURL", "http://deco3801-005.uqcloud.net/cache/network/rest/stop-timetables/?stopIds=000268");
		setActivityIntent(i);
		
		tActivity = getActivity();
		solo = new Solo(getInstrumentation(), tActivity);
		
		
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
