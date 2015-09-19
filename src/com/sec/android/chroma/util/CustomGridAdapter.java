/*Company Name : TheLanguidAsp
Author: Saket Poddar*/
package com.sec.android.chroma.util;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import com.sec.android.chroma.MainGameActivity;
import com.sec.android.chroma.R;

public class CustomGridAdapter extends BaseAdapter{
	  private Context mContext;
	  private int counter;
	  private int generatedcolor;
	  private int generatedLightcolor;
	  private int randomNumber;

	  public CustomGridAdapter(Context c) {
	        mContext = c;
	    }
	    
	    public int setCounterLayout(int counter){
	    	this.counter = counter;
	    	return this.counter;
	    }
	    public int getGeneratedColor(int color){
	    	this.generatedcolor = color;
	    	return this.generatedcolor;
	    }
	    
	    public int getGeneratedLightColor(int color){
	    	this.generatedLightcolor = color;
	    	return this.generatedLightcolor;
	    }
	    
	    public int getRandomNumber(int randomNumber){
	    	this.randomNumber = randomNumber;
	    	return this.randomNumber;
	    }

	    @Override
		public int getCount() {
			return 0;
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
            View grid = convertView;
            if (convertView == null) {
            	LayoutInflater inflater = (LayoutInflater) mContext
                         .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                grid = new View(mContext);
                grid = inflater.inflate(R.layout.grid_item, parent,false);
             }
            else{
            	grid = (View) convertView;
            }
                Button btn = (Button) grid.findViewById(R.id.grid_button);
                btn.setFocusable(false);
                btn.setClickable(false);
                btn.setText("");
                if(position==randomNumber)
                	btn.setBackgroundColor(generatedLightcolor);
                else
                	btn.setBackgroundColor(generatedcolor);
                switch(counter){
                
                case 0:case 55:
                	btn.setWidth(MainGameActivity.getContext().getResources().getDimensionPixelSize(R.dimen.heightfor2));
                	btn.setHeight(MainGameActivity.getContext().getResources().getDimensionPixelSize(R.dimen.heightfor2));
                	break;
                case 1:
                	btn.setWidth(MainGameActivity.getContext().getResources().getDimensionPixelSize(R.dimen.heightfor3));
                	btn.setHeight(MainGameActivity.getContext().getResources().getDimensionPixelSize(R.dimen.heightfor3));
                	break;
                case 2:case 3:
                	btn.setWidth(MainGameActivity.getContext().getResources().getDimensionPixelSize(R.dimen.heightfor4));
                	btn.setHeight(MainGameActivity.getContext().getResources().getDimensionPixelSize(R.dimen.heightfor4));
                	break;
                case 4:case 5:case 6:case 7:case 8:
                	btn.setWidth(MainGameActivity.getContext().getResources().getDimensionPixelSize(R.dimen.heightfor5));
                	btn.setHeight(MainGameActivity.getContext().getResources().getDimensionPixelSize(R.dimen.heightfor5));
                	break;
                case 9:case 10:case 11:case 12:case 13:
                	btn.setWidth(MainGameActivity.getContext().getResources().getDimensionPixelSize(R.dimen.heightfor6));
                	btn.setHeight(MainGameActivity.getContext().getResources().getDimensionPixelSize(R.dimen.heightfor6));
                	break;
                case 14:case 15:case 16:case 17:case 18:case 19:case 20:case 21:case 22:case 23:
                	btn.setWidth(MainGameActivity.getContext().getResources().getDimensionPixelSize(R.dimen.heightfor7));
                	btn.setHeight(MainGameActivity.getContext().getResources().getDimensionPixelSize(R.dimen.heightfor7));
                	break;
                case 24:case 25:case 26:case 27:case 28:case 29:case 30:case 31:case 32:case 33:
                	btn.setWidth(MainGameActivity.getContext().getResources().getDimensionPixelSize(R.dimen.heightfor8));
                	btn.setHeight(MainGameActivity.getContext().getResources().getDimensionPixelSize(R.dimen.heightfor8));
                	break;
                case 34:case 35:case 36:case 37:case 38:case 39:case 40:case 41:case 42:case 43:
                	btn.setWidth(MainGameActivity.getContext().getResources().getDimensionPixelSize(R.dimen.heightfor9));
                	btn.setHeight(MainGameActivity.getContext().getResources().getDimensionPixelSize(R.dimen.heightfor9));
                	break;
                case 44:case 45:case 46:case 47:case 48:case 49: case 50: case 51: case 52: case 53: case 54:
                	btn.setWidth(MainGameActivity.getContext().getResources().getDimensionPixelSize(R.dimen.heightfor10));
                	btn.setHeight(MainGameActivity.getContext().getResources().getDimensionPixelSize(R.dimen.heightfor10));
                	break;
                default:
                		break;
                }
            return grid;
        }
}
