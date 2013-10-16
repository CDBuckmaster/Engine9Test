package com.engine9.test;

import com.engine9.FavouriteActivity;
import com.engine9.FavouriteManager;
import com.engine9.TimetableActivity;
import com.jayway.android.robotium.solo.Solo;

import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class FavouriteActivityTest extends ActivityInstrumentationTestCase2<FavouriteActivity> {

	private Solo solo;
	private FavouriteActivity fActivity;
	
	public FavouriteActivityTest() {
		super(FavouriteActivity.class);
		// TODO Auto-generated constructor stub
	}
	
	public void testSaveFavourite() throws Exception{
		FavouriteManager.deleteAllFavourites(fActivity.getApplicationContext());
		solo.enterText((EditText) fActivity.findViewById(com.engine9.R.id.fav_text), "412");
		solo.clickOnButton("Add Favourite");
		
		assertTrue(FavouriteManager.inFavourites(fActivity.getApplicationContext(), "412"));
		
		ListView lv = (ListView) fActivity.findViewById(com.engine9.R.id.abstract_list);
		assertTrue(lv.getAdapter().getCount() == 1);
		
		TextView tv = (TextView) lv.getChildAt(0).findViewById(com.engine9.R.id.fav_text);
		Log.e("Debug",tv.getText().toString());
		assertTrue(tv.getText().toString().equals("412"));
		
	}
	
	public void testDuplicateFavourite() throws Exception{
		FavouriteManager.deleteAllFavourites(fActivity.getApplicationContext());
		
		solo.enterText((EditText) fActivity.findViewById(com.engine9.R.id.fav_text), "412");
		solo.clickOnButton("Add Favourite");
		
		solo.enterText((EditText) fActivity.findViewById(com.engine9.R.id.fav_text), "412");
		solo.clickOnButton("Add Favourite");
		
		assertTrue(FavouriteManager.inFavourites(fActivity.getApplicationContext(), "412"));
		
		ListView lv = (ListView) fActivity.findViewById(com.engine9.R.id.abstract_list);
		assertTrue(lv.getAdapter().getCount() == 1);
		
		
	}
	
	public void testInvalidService() throws Exception{
		FavouriteManager.deleteAllFavourites(fActivity.getApplicationContext());
		
		solo.enterText((EditText) fActivity.findViewById(com.engine9.R.id.fav_text), "10000");
		solo.clickOnButton("Add Favourite");
		
		assertFalse(FavouriteManager.inFavourites(fActivity.getApplicationContext(), "10000"));
		
		ListView lv = (ListView) fActivity.findViewById(com.engine9.R.id.abstract_list);
		assertTrue(lv.getAdapter().getCount() == 0);
	}
	
	public void testDeleteFavourite() throws Exception{
		FavouriteManager.deleteAllFavourites(fActivity.getApplicationContext());
		
		solo.enterText((EditText) fActivity.findViewById(com.engine9.R.id.fav_text), "412");
		solo.clickOnButton("Add Favourite");
		
		ListView lv = (ListView) fActivity.findViewById(com.engine9.R.id.abstract_list);
		final Button b = (Button)lv.getChildAt(0).findViewById(com.engine9.R.id.delete_fav);
		
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
		
		assertFalse(FavouriteManager.inFavourites(fActivity.getApplicationContext(), "412"));
		assertTrue(lv.getCount() ==  0);
	}
	
	public void testIllegal() throws Exception{
		FavouriteManager.deleteAllFavourites(fActivity.getApplicationContext());
		
		solo.enterText((EditText) fActivity.findViewById(com.engine9.R.id.fav_text), "%$%#$$$\n");
		solo.clickOnButton("Add Favourite");
		
		assertFalse(FavouriteManager.inFavourites(fActivity.getApplicationContext(), "412"));
		
		ListView lv = (ListView) fActivity.findViewById(com.engine9.R.id.abstract_list);
		assertTrue(lv.getAdapter().getCount() == 0);
		
	}
	
	protected void setUp() throws Exception{
		
		fActivity = getActivity();
		FavouriteManager.deleteAllFavourites(fActivity.getApplicationContext());
		solo = new Solo(getInstrumentation(), fActivity);
		
		
		
	}
	
	@Override
	public void tearDown() throws Exception {
	        solo.finishOpenedActivities();
	}

}
