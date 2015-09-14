/*Company Name : TheLanguidAsp
Author: Saket Poddar*/
package com.sec.android.chroma;

import java.util.ArrayList;
import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;


public class Leaderboard extends Activity {
	private ArrayList scoreList = new ArrayList();
	private Typeface custom_font;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		scoreList = getIntent().getCharSequenceArrayListExtra("scores");
		setContentView(R.layout.high_score_list);
		custom_font = Typeface.createFromAsset(getAssets(), "infini-gras.otf");
		ListView list = (ListView) findViewById(R.id.listview);
		HighScoreListAdapter adapter = new HighScoreListAdapter();
		list.setAdapter(adapter);
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}
	
	class HighScoreListAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			return scoreList.size();
		}

		@Override
		public Object getItem(int position) {
			return scoreList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			
			LayoutInflater layInflate = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			
			if(convertView == null)
				convertView = layInflate.inflate(R.layout.highscore_item, parent, false);
			
			TextView rank = (TextView)convertView.findViewById(R.id.rank);
			TextView score = (TextView)convertView.findViewById(R.id.score);
			rank.setTypeface(custom_font);
			score.setTypeface(custom_font);
			rank.setText(String.valueOf(position+1));
			
			if((long)scoreList.get(position)==-1)
				score.setText(String.valueOf(0));
			else
				score.setText(scoreList.get(position).toString());
			
			return convertView;
		}
		
	}
	
}
