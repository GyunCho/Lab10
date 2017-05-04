package edu.temple.lab10;

import android.app.Notification;
import android.content.DialogInterface;

import android.content.Intent;
import android.support.annotation.RequiresPermission;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.app.Fragment;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements PortfolioFragment.PortfolioFragmentInterface{

    PortfolioFragment portfolioFragment;
    PortfolioFragment sender;
    PortfolioDetailsFragment portfolioDetailsFragment;

    Portfolio portfolio;

    FileInputStream fileInputStream;

    Stock stock;

    boolean twoFragments;

    int state;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        portfolioFragment = new PortfolioFragment();

        portfolio = new Portfolio();
        //portfolio.add(new Stock("Microsoft", "MSFT"));
        //portfolio.add(new Stock("Google", "GOOG"));

        Bundle bundle = new Bundle();
        bundle.putSerializable(PortfolioFragment.BUNDLE_KEY, portfolio);
        portfolioFragment.setArguments(bundle);

        twoFragments = (findViewById(R.id.frag2) != null);

        //portfolioFragment = new PortfolioFragment();
        portfolioDetailsFragment = new PortfolioDetailsFragment();
        addFragment(R.id.frag1, portfolioFragment);
        if(twoFragments) {
            addFragment(R.id.frag2, portfolioDetailsFragment);
        }
/*
        try {
            String message;
            fileInputStream = openFileInput("internal_storage");
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuffer stringBuffer = new StringBuffer();
            List<String> list = new ArrayList<String>();
            while ((message = bufferedReader.readLine()) != null){
                list.add(message);
                int count = 0;
                portfolio.add(new Stock(list.get(count)));
                count++;
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
*/

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.actionbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:


                Log.d("Search Button", "Search button is clicked");
                final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);


                View mView = getLayoutInflater().inflate(R.layout.dialog_search, null);

                builder.setView(mView);

                final AlertDialog dialog = builder.create();

                final EditText editTextSearch = (EditText) mView.findViewById(R.id.editTextSearch);
                Button buttonSearch = (Button) mView.findViewById(R.id.buttonSearch);
                Button buttonCancel = (Button) mView.findViewById(R.id.buttonCancel);


                buttonSearch.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String textFromSearchBar = editTextSearch.getText().toString();

                        String file_name = "internal_storage";
                        try {
                            FileOutputStream fileOutputStream = new FileOutputStream(file_name,true);
                            fileOutputStream = openFileOutput(file_name, MODE_PRIVATE);


                            fileOutputStream.write(textFromSearchBar.getBytes());
                            fileOutputStream.write('\n');
                            //How can I append this?
                            fileOutputStream.close();
                            Toast.makeText(getApplicationContext(), "Stock Saved", Toast.LENGTH_LONG).show();
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }


                        stock = new Stock(textFromSearchBar);

                        portfolioFragment.addStock(stock);

                        Log.d("Search Button Working:", "It works");
                        dialog.dismiss();
                    }
                });

                buttonCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });


// Create the AlertDialog
                //builder.setView(mView);
                //AlertDialog dialog = builder.create();
                dialog.show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public void addStocks(){
        try {
            String message;
            fileInputStream = openFileInput("internal_storage");
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuffer stringBuffer = new StringBuffer();
            while ((message = bufferedReader.readLine()) != null){
                stringBuffer.append(message);
            }
            stock = new Stock(stringBuffer.toString());

            portfolioFragment.addStock(stock);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        //portfolio.add(new Stock("Microsoft", "MSFT"));
        //portfolio.add(new Stock("Google", "GOOG"));
    }

    public void addFragment (int containerId, Fragment fragment){
        getFragmentManager()
                .beginTransaction()
                .replace(containerId, fragment)
                .addToBackStack("tag")
                .commit();
    }


    @Override
    public void sendStock(String symbol) {
        if (twoFragments) {
            portfolioDetailsFragment.setPortfolioDetailsFragment(symbol);
        } else {
            addFragment(R.id.frag1, portfolioDetailsFragment);
            //portfolioDetailsFragment.logTest("GOOG");
            //portfolioDetailsFragment.setStockImage(symbol);
            //portfolioDetailsFragment.setStockImageTest();
            getFragmentManager().executePendingTransactions();
            portfolioDetailsFragment.setPortfolioDetailsFragment(symbol);

        }
    }

        public void startService(View view) {
            startService(new Intent(getBaseContext(), ParsingPriceService.class));
        }

}
