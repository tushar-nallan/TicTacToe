package com.tramsun.tictactoe.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.GridView;

import com.tramsun.tictactoe.R;
import com.tramsun.tictactoe.controllers.GameData;
import com.tramsun.tictactoe.views.SquareTextView;

/**
 * Created by Tushar on 19-03-2015.
 */
public class GameAdapter extends BaseAdapter {
    private Context context;
    private GameData gameData;


    public GameAdapter(Context context, GameData gameData) {
        this.gameData = gameData;
        this.context = context;
    }

    @Override
    public int getCount() {
        return gameData.getGridLength()*gameData.getGridLength();
    }

    @Override
    public Integer getItem(int position) {
        return gameData.getGrid().get(position);
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).hashCode();
    }
    public int dip2px( float dips ) {
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dips, context.getResources().getDisplayMetrics()));
    }

    String mapping = "XOPQ";
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Integer data = getItem(position);
        SquareTextView tv;
        if(convertView == null) {
            tv = new SquareTextView(context);
            if(tv.getLayoutParams() == null) {
                GridView.LayoutParams lp = new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                tv.setLayoutParams(lp);
            }
            tv.setGravity(Gravity.CENTER);
            tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
            tv.setTypeface(null, Typeface.BOLD);
            tv.setBackgroundResource(R.drawable.border);
        } else {
            tv = (SquareTextView) convertView;
        }
        String s = data == -1? "" : mapping.charAt(data)+"";
        tv.setText(s);
        return tv;
    }
}
