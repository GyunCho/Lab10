package edu.temple.lab10;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by GyunCho on 4/30/17.
 */

public class PortfolioAdapter extends BaseAdapter {
    Context context;
    Portfolio portfolio;
    Stock stock;

    public PortfolioAdapter(Context context, Portfolio portfolio){
        this.context = context;
        this.portfolio = portfolio;
    }

    @Override
    public int getCount() {
        return portfolio.size();
    }

    @Override
    public Object getItem(int position) {
        return portfolio.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView textView = new TextView(context);
        textView.setText(portfolio.get(position).getSymbol());
        return textView;
    }




}
