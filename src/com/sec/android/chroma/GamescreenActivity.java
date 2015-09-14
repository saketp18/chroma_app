/*Company Name : TheLanguidAsp
Author: Saket Poddar*/
package com.sec.android.chroma;

import java.util.ArrayList;
import java.util.Collections;


import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class GamescreenActivity extends Activity {

	private TextView titleText;
	private TextView helpText;
	private Button playButton;
	private Button rateButton;
	private Button helpButton;
	private Typeface custom_font;
	public static final String USERS = "users";
	private SharedPreferences sharedPrefs;
	private ArrayList scoreList = new ArrayList();
	private static GamescreenActivity _instance=null;

	public GamescreenActivity(){
		_instance = this;
	}
	
	public static GamescreenActivity getInstance(){
		return _instance;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mainscreen);
		custom_font = Typeface.createFromAsset(getAssets(), "infini-gras.otf");
		titleText = (TextView) findViewById(R.id.gameTitle);
		helpText = (TextView) findViewById(R.id.helpTitle);
		rateButton = (Button) findViewById(R.id.rate_button);
		playButton = (Button) findViewById(R.id.play_button);
		helpButton = (Button) findViewById(R.id.rank_button);
		titleText.setTypeface(custom_font);
		helpText.setTypeface(custom_font);
		rateButton.setTypeface(custom_font);
		playButton.setTypeface(custom_font);
		helpButton.setTypeface(custom_font);
		rateButton.setOnClickListener(mOnClickListener);
		playButton.setOnClickListener(mOnClickListener);
		helpButton.setOnClickListener(mOnClickListener);
		prepareLeaderBoard();
	}

	public void prepareLeaderBoard() {
		sharedPrefs = getSharedPreferences(USERS, 0);

		for (int i = 0; i < 10; i++) {
			scoreList.add(sharedPrefs.getLong(getRankString(i), -1));
		}
		Collections.sort(scoreList, Collections.reverseOrder());
	}

	private String getRankString(int i) {
		String rank = "";

		switch (i) {
		case 0:
			return "one";
		case 1:
			return "two";
		case 2:
			return "three";
		case 3:
			return "four";
		case 4:
			return "five";
		case 5:
			return "six";
		case 6:
			return "seven";
		case 7:
			return "eight";
		case 8:
			return "nine";
		case 9:
			return "ten";
		}
		return rank;
	}

	private OnClickListener mOnClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.rate_button:
				launchMarket();
				break;
			case R.id.play_button:
				Intent intent = new Intent(GamescreenActivity.this,
						MainGameActivity.class);
				startActivity(intent);
				break;
			case R.id.rank_button:
				Intent helpIntent = new Intent(GamescreenActivity.this,
						Leaderboard.class);
				helpIntent.putCharSequenceArrayListExtra("scores", scoreList);
				startActivity(helpIntent);
				break;
			default:
				break;
			}
		}
	};

	private void launchMarket() {
		Uri uri = Uri.parse("market://details?id=" + getPackageName());
		Intent myAppLinkToMarket = new Intent(Intent.ACTION_VIEW, uri);
		try {
			startActivity(myAppLinkToMarket);
		} catch (ActivityNotFoundException e) {
			Toast.makeText(this, " Unable to find app in market",
					Toast.LENGTH_LONG).show();
		}
	}

	public void onAdd(long score) {

		scoreList.add(score);

		Collections.sort(scoreList, Collections.reverseOrder());

		SharedPreferences.Editor edit = sharedPrefs.edit();
		for (int i = 0; i < scoreList.size(); i++) {
			if (i < 10) {
				edit.putLong(getRankString(i), (long) scoreList.get(i));
			} else
				scoreList.remove(i);
		}
		edit.commit();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.gamescreen, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
