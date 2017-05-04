package edu.temple.lab10;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by GyunCho on 4/30/17.
 */

public class Portfolio implements Serializable {
    private ArrayList<Stock> stocks;

    public Portfolio(ArrayList<Stock> stocks) {this.stocks = stocks;}

    public Portfolio() {this.stocks = new ArrayList<Stock>();}

    public void add (Stock stock) {stocks.add(stock);}

    public Stock get (int index) {return stocks.get(index);}

    public void remove (int position){
        stocks.remove(position);
    }

    public int size() {return stocks.size();}
}
