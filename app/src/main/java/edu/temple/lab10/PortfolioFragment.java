package edu.temple.lab10;

import android.app.Activity;
import android.content.Context;
import android.content.SyncStats;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class PortfolioFragment extends Fragment {


    PortfolioFragmentInterface parents;
    ListView stockList;
    Portfolio portfolio;
    PortfolioDetailsFragment portfolioDetailsFragment;


    Stock stock;

    public static String BUNDLE_KEY = "portfolio";


    public PortfolioFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_portfolio, container, false);

        stockList = (ListView) view.findViewById(R.id.stockList);

        Bundle bundle = this.getArguments();


        if(bundle != null){
            portfolio = (Portfolio) bundle.getSerializable(BUNDLE_KEY);
        }


        final PortfolioAdapter myAdapter = new PortfolioAdapter(getActivity(), portfolio);

       // String[] foods = {"Bacon", "Ham"};

        //ListAdapter testAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, foods);

        //stockList.setAdapter(testAdapter);

        stockList.setAdapter(myAdapter);
        portfolioDetailsFragment = new PortfolioDetailsFragment();


        stockList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String stockSymbol =  portfolio.get(position).getSymbol();
                ParsingPriceService parsingPriceService = new ParsingPriceService();
                parsingPriceService.setSymbol(stockSymbol);
                parents.sendStock(stockSymbol);
                //getFragmentManager().executePendingTransactions();


            }
        });



        stockList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                portfolio.remove(position);

                myAdapter.notifyDataSetChanged();
                return true;
            }
        });

        return view;
    }



    /*@Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof PortfolioFragmentInterface) {
            parents = (PortfolioFragmentInterface) context;
        } else {
            throw new RuntimeException(context.toString()
                    + "must implement PortfolioFragmentInterface");
        }
    }*/


    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof PortfolioFragmentInterface) {
            parents = (PortfolioFragmentInterface) activity;
        } else {
            throw new RuntimeException("Not Implemented");
        }
    }

   @Override
    public void onDetach() {
        super.onDetach();
        parents = null;
    }


    public interface PortfolioFragmentInterface {
        void sendStock(String symbol);
    }

    public void addStock(Stock stock){
        portfolio.add(stock);
        ((PortfolioAdapter) stockList.getAdapter()).notifyDataSetChanged();
    }
}
