package edu.temple.lab10;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;


public class PortfolioDetailsFragment extends Fragment {

    TextView stockName;
    ImageView stockImage;
    TextView stockPrice;


    ParsingPriceService parsingPriceService;

    Stock stock;

    public PortfolioDetailsFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_portfolio_details, container, false);

        stockName = (TextView) view.findViewById(R.id.stockName);
        stockImage = (ImageView) view.findViewById(R.id.stockImage);
        stockPrice = (TextView) view.findViewById(R.id.stockPrice);
        getActivity().startService(new Intent(getActivity(),ParsingPriceService.class));


        return view;
    }

    public void setCompanyName (String symbol){

        new JsonTaskName().execute("http://dev.markitondemand.com/MODApis/Api/v2/Quote/json/?symbol=" + symbol);

    }

    public void setStockImage (String symbol){
        Picasso.with(stockImage.getContext())
                .load("https://chart.yahoo.com/z?t=1d&s=" + symbol)
                .into(stockImage);
    }


    /*public void logTest (String symbol){
        Log.d(symbol, symbol);
    }*/

    public void setStockPrice (String symbol){
        new JsonTaskPrice().execute("http://dev.markitondemand.com/MODApis/Api/v2/Quote/json/?symbol=" + symbol);
        parsingPriceService = new ParsingPriceService();
        String price = parsingPriceService.getPrice();
        stockPrice.setText(price);

    }

    public void setPortfolioDetailsFragment(String symbol){
        setCompanyName(symbol);
        setStockImage(symbol);
        setStockPrice(symbol);
    }

    private class JsonTaskName extends AsyncTask<String, String, String> {


        @Override
        protected String doInBackground(String... params) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();


                InputStream stream = connection.getInputStream();

                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();
                String line = "";



                while ((line = reader.readLine()) != null) {
                    buffer.append(line+"\n");
                    Log.d("Response: ", "> " + line);   //here u ll get whole response...... :-)

                }

                return buffer.toString();


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }  finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            String name = "";
            if(result != null){

                try {
                    JSONObject json = new JSONObject(result);
                    //JSONObject jsonResponse = json.getJSONObject("result");
                    name = json.getString("Name");
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
            stockName.setText(name);
        }
    }


    private class JsonTaskPrice extends AsyncTask<String, String, String> {


        @Override
        protected String doInBackground(String... params) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();


                InputStream stream = connection.getInputStream();

                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();
                String line = "";



                while ((line = reader.readLine()) != null) {
                    buffer.append(line+"\n");
                    Log.d("Response: ", "> " + line);   //here u ll get whole response...... :-)

                }

                return buffer.toString();


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }  finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            String price = "";
            if(result != null){

                try {
                    JSONObject json = new JSONObject(result);
                    //JSONObject jsonResponse = json.getJSONObject("result");
                    price = json.getString("LastPrice");
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
            stockPrice.setText(price);
        }
    }



    /*
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }*/



}
