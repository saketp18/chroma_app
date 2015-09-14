/*Company Name : TheLanguidAsp
Author: Saket Poddar*/
package com.sec.android.chroma;

import java.util.ArrayList;
import java.util.Random;

import com.sec.android.chroma.util.CustomGridAdapter;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

public class MainGameActivity extends Activity {

	private CountDownTimer countDownTimer;
	private Typeface custom_font;
	private static MainGameActivity _instance;
	private static final String MyPREFERENCES = "MyPrefs";
	private static final String SCORE = "nameKey";
	private SharedPreferences sharedpreferences;
	private Editor sharedprefeditor;
	private long old_highScore;
	private long new_highScore = 0;
	private TextView scoreText;
	private TextView currScoreText;
	private TextView timerText;
	private TextView currTimerText;
	private Button pauseButton;
	private Button resumeButton;
	private final long startTime = 60000;
	private final long interval = 1000;
	private long timeRemaining = 0;
	private GridView grid;
	private int counter = 0;
	private CustomGridAdapter adapter;
	private ArrayList<String> list = new ArrayList<>();
	private GamerOverDialog dialog1;
	private GamerOverDialog dialog;
	private Random random;
	private int randomNumber;
	private int generatedColorLight;
	public static String DIALOG_TAG = "dialog";
	private boolean resumeClicked=false;
	
	public MainGameActivity(){
		_instance = this;
	}
	
	public static Context getContext(){
		return _instance;
	}
	
	public int getPixtoDimen(int resId){
		return getContext().getResources().getDimensionPixelSize(resId);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gamescreen);
		custom_font = Typeface.createFromAsset(getAssets(),"infini-gras.otf");
		overridePendingTransition(R.anim.left_in, R.anim.left_out);
		sharedpreferences = getSharedPreferences(MyPREFERENCES,
				Context.MODE_PRIVATE);
		sharedprefeditor = sharedpreferences.edit();
		old_highScore = sharedpreferences.getLong(SCORE, 0);
		prepareContentView();
		countDownTimer = new CustomCountDownTimer(startTime, interval);
		currTimerText.setText(currTimerText.getText() + String.valueOf(startTime / 1000));
		countDownTimer.start();
		showHighScore();
		
	}

	private void prepareContentView(){
		
		scoreText = (TextView)findViewById(R.id.scoreText);
		currScoreText = (TextView)findViewById(R.id.currScore);
		timerText = (TextView)findViewById(R.id.timeText);
		currTimerText = (TextView)findViewById(R.id.currTime);
		pauseButton = (Button)findViewById(R.id.pause_button);
		resumeButton= (Button)findViewById(R.id.resume_button);
		list.add("0");list.add("1");list.add("2");list.add("3");
		adapter = new CustomGridAdapter(MainGameActivity.this, list);
		random = new Random();
	    randomNumber = random.nextInt(4);
		adapter.getRandomNumber(randomNumber);
		adapter.getGeneratedColor(generateColor());
		adapter.getGeneratedLightColor(generatedColorLight);
		grid = (GridView)findViewById(R.id.gridview);
		grid.setAdapter(adapter);
		scoreText.setTypeface(custom_font);
		currScoreText.setTypeface(custom_font);
		currScoreText.setText(""+new_highScore);
		timerText.setTypeface(custom_font);
		currTimerText.setTypeface(custom_font);
		pauseButton.setTypeface(custom_font);
		resumeButton.setTypeface(custom_font);
		pauseButton.setOnClickListener(mClickListener);
		resumeButton.setOnClickListener(mClickListener);
		grid.setOnItemClickListener(mItemOnclickListener);
	}

	private void saveHighScore() {

		// Here take argument for the high score afterwards
		if (old_highScore <= new_highScore)
			sharedprefeditor.putLong(SCORE, new_highScore);
		sharedprefeditor.commit();
		
	}
	
	private long showHighScore() {
		sharedpreferences = getSharedPreferences(MyPREFERENCES,
			Context.MODE_PRIVATE);
		long highScore=0;
		if (sharedpreferences.contains(SCORE)) {
		 highScore = sharedpreferences.getLong(SCORE, 0);
		}
		return highScore;
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	protected void onResume(){
		super.onResume();
	}
	
	public class CustomCountDownTimer extends CountDownTimer {
		public CustomCountDownTimer(long startTime, long interval) {
			super(startTime, interval);
		}
		
		@Override
		public void onFinish() {
			if(resumeClicked==false){
				currTimerText.setText("0");
				try {
					dialog1 = new GamerOverDialog();
					dialog1.show(getFragmentManager(), DIALOG_TAG);
				} catch (IllegalStateException e) {
					e.printStackTrace();
				}
				GamescreenActivity.getInstance().onAdd(new_highScore);
				list.clear();
				list.add("1");list.add("2");list.add("3");list.add("4");
				counter = 55;
				grid.setNumColumns(2);
				adapter.setCounterLayout(counter);
				adapter.notifyDataSetChanged();
			}
		}
		@Override
		public void onTick(long millisUntilFinished) {
			currTimerText.setText("" + millisUntilFinished / 1000);
			timeRemaining = millisUntilFinished;
		}
		
	}

	private OnClickListener mClickListener = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			switch(v.getId()){
				case R.id.pause_button:
					    resumeClicked=true;
						countDownTimer.cancel();
						resumeButton.setVisibility(View.VISIBLE);
						pauseButton.setVisibility(View.GONE);
						grid.setAlpha((float) 0.009);
						grid.setOnItemClickListener(null);
					
					break;
				case R.id.resume_button:
					resumeClicked=false;
					countDownTimer = new CustomCountDownTimer(timeRemaining, interval);
					currTimerText.setText(currTimerText.getText() + String.valueOf(timeRemaining / 1000));
					countDownTimer.start();
					resumeButton.setVisibility(View.GONE);
					pauseButton.setVisibility(View.VISIBLE);
					grid.setAlpha((float) 1.0);
					grid.setOnItemClickListener(mItemOnclickListener);
				default:
					break;
			}
		}
	};
	private OnItemClickListener mItemOnclickListener = new AdapterView.OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
			
			if (position == randomNumber) {
				adapter.setCounterLayout(++counter);
				int red = (int) (Math.random() * 236) +20;
				int green = (int) (Math.random() * 236)+20;
				int blue = (int) (Math.random() * 236)+20;
				switch (counter) {
				case 0:
					list.clear();
					for (int i = 0; i < 4; i++) {
						list.add("" + i);
					}
					randomNumber = random.nextInt(4);
					adapter.getRandomNumber(randomNumber);
					generatedColorLight = Color.argb(100, red, green, blue);
					grid.setNumColumns(2);
					break;
				case 1:
					list.clear();
					for (int i = 0; i < 9; i++) {
						list.add("" + i);
					}
					randomNumber = random.nextInt(9);
					adapter.getRandomNumber(randomNumber);
					grid.setNumColumns(3);
					generatedColorLight = Color.argb(150, red, green, blue);
					break;
				case 2:	case 3:
					list.clear();
					for (int i = 0; i < 16; i++) {
						list.add("" + i);
					}
					randomNumber = random.nextInt(16);
					adapter.getRandomNumber(randomNumber);
					generatedColorLight = Color.argb(170, red, green, blue);
					grid.setNumColumns(4);
					break;
				case 4:	case 5:	case 6:	case 7:	case 8:
					list.clear();
					for (int i = 0; i < 25; i++) {
						list.add("" + i);
					}
					randomNumber = random.nextInt(25);
					adapter.getRandomNumber(randomNumber);
					generatedColorLight = Color.argb(190, red, green, blue);
					grid.setNumColumns(5);
					break;
				case 9:	case 10:case 11:case 12:case 13:
					list.clear();
					for (int i = 0; i < 36; i++) {
						list.add("" + i);
					}
					randomNumber = random.nextInt(36);
					adapter.getRandomNumber(randomNumber);
					generatedColorLight = Color.argb(195, red, green, blue);
					grid.setNumColumns(6);
					break;
				case 14:case 15:case 16:case 17:case 18:case 19:case 20:case 21:case 22:case 23:
					list.clear();
					for (int i = 0; i < 49; i++) {
						list.add("" + i);
					}
					randomNumber = random.nextInt(49);
					adapter.getRandomNumber(randomNumber);
					generatedColorLight = Color.argb(200, red, green, blue);
					grid.setNumColumns(7);
					break;
				case 24:case 25:case 26:case 27:case 28:case 29:case 30:case 31:case 32:case 33:case 34:case 35:case 36:case 37:case 38:case 39:case 40:case 41:case 42:
					list.clear();
					for (int i = 0; i < 64; i++) {
						list.add("" + i);
					}
					randomNumber = random.nextInt(64);
					adapter.getRandomNumber(randomNumber);
					generatedColorLight = Color.argb(215, red, green, blue);
					grid.setNumColumns(8);
					break;
				case 43:case 44:case 45:case 46:case 47:case 48:case 49:case 50:case 51:case 52:
					list.clear();
					for (int i = 0; i < 81; i++) {
						list.add("" + i);
					}
					randomNumber = random.nextInt(81);
					adapter.getRandomNumber(randomNumber);
					generatedColorLight = Color.argb(230, red, green, blue);
					grid.setNumColumns(9);
					break;
				case 53:case 54:
					list.clear();
					for (int i = 0; i < 100; i++) {
						list.add("" + i);
					}
					randomNumber = random.nextInt(100);
					adapter.getRandomNumber(randomNumber);
					generatedColorLight = Color.argb(250, red, green, blue);
					grid.setNumColumns(10);
					break;
				case 55:
					list.clear();
					for (int i = 0; i < 4; i++) {
						list.add("" + i);
					}
					randomNumber = random.nextInt(4);
					adapter.getRandomNumber(randomNumber);
					grid.setNumColumns(2);
					try{
						dialog = new GamerOverDialog();
						if(dialog!=null && !dialog.getDialog().isShowing())
							dialog.show(getFragmentManager(), DIALOG_TAG);
					}catch(IllegalStateException e){
						e.printStackTrace();
					}
					countDownTimer.cancel();
					resumeButton.setVisibility(View.VISIBLE);
					pauseButton.setVisibility(View.GONE);
					break;
				default:
					break;
				}
				++new_highScore;
				if (new_highScore < 56) {
					currScoreText.setText("" + new_highScore);
					saveHighScore();
				}
				adapter.getGeneratedColor(Color.argb(255, red, green, blue));
				adapter.getGeneratedLightColor(generatedColorLight);
				adapter.notifyDataSetChanged();
			}
		}
	};

	private int generateColor() {
		int red = (int) (Math.random() * 236) +20;
		int green = (int) (Math.random() * 236)+20;
		int blue = (int) (Math.random() * 236)+20;
		generatedColorLight = Color.argb(100, red, green, blue);
		return Color.argb(255, red, green, blue);
	}
	
	public class GamerOverDialog extends DialogFragment {
		private AlertDialog mProgDlg = null;
		private  TextView score = null;
		private  TextView highscore = null;
		private  Button playAgain = null;
		private Button share = null;
		private View dialogView;
		
		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
			dialogView = getActivity().getLayoutInflater().inflate(R.layout.gameover,null,false);
			builder.setView(dialogView);
			score = (TextView)dialogView.findViewById(R.id.score);
			highscore = (TextView)dialogView.findViewById(R.id.highscore);
			playAgain = (Button)dialogView.findViewById(R.id.playAgain);
			share = (Button)dialogView.findViewById(R.id.share);
			score.setText(""+new_highScore);
			highscore.setText(""+showHighScore());
			playAgain.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					dismiss();
					countDownTimer.cancel();
					countDownTimer = new CustomCountDownTimer(
							startTime, interval);
					currTimerText.setText(currTimerText.getText()
							+ String.valueOf(startTime / 1000));
					countDownTimer.start();
					pauseButton.setEnabled(true);
					pauseButton.setVisibility(View.VISIBLE);
					resumeButton.setEnabled(true);
					resumeButton.setVisibility(View.GONE);
					new_highScore = 0;
					counter = 0;
					list.clear();
					list.add("1");list.add("2");list.add("3");list.add("4");
					randomNumber = random.nextInt(4);
					currScoreText.setText(""+new_highScore);
					adapter.getGeneratedColor(generateColor());
					adapter.getGeneratedLightColor(generatedColorLight);
					adapter.getRandomNumber(randomNumber);
					adapter.setCounterLayout(counter);
					adapter.notifyDataSetChanged();
				}
			});
			share.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {

					String uri = "https://play.google.com/store/apps/details?id="
							+"com.sec.android.chroma";
					String text = "Color Perception Test\n"
							+"--------------------\n"
							+"Check your eyes. Press/touch the different color cube.\n"
							+"I have scored: "+new_highScore+"\n\n"
							+"Chroma "+uri+"\n\n"
							+"35> Pilot\n"
							+"30-35 Good Eyes\n"
							+"25-30 Normal\n"
							+"15-25 Weak\n"
							+"11-15 God help them please!!\n"
							+ "\nLet us know your result.....\n\n"
							+"Very interesting test\n"
							+"Have fun!!\n";
					Intent sendIntent = new Intent();
					sendIntent.setAction(Intent.ACTION_SEND);
					sendIntent.putExtra(Intent.EXTRA_TEXT, text);
					sendIntent.setType("text/plain");
					startActivity(Intent
							.createChooser(sendIntent, "Share with"));
					
				}
			});
			mProgDlg = builder.create();
			mProgDlg.onBackPressed();
			mProgDlg.setCanceledOnTouchOutside(false);
			return mProgDlg;
		}
		
		 @Override
		    public void onCancel(DialogInterface dialog) {
		        super.onCancel(dialog);
		        MainGameActivity.this.finish();
		    }
		
	}
}
