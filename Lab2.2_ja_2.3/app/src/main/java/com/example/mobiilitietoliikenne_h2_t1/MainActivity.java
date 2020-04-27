package com.example.mobiilitietoliikenne_h2_t1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, GetDataAsyncTask.ReporterInterface {

    ListView listViewStocks;
    ArrayList<String> stocks = new ArrayList<>();
    ArrayList<String> stockPrices = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.buttonAddNewStock).setOnClickListener(this);
        listViewStocks = findViewById(R.id.listViewStocks);
        createStockList();
        getStocksList();
    }

    public void createStockList() {
        stocks.add("AAPL");
        stocks.add("GOOGL");
        stocks.add("FB");
        stocks.add("NOK");
    }

    public void getStocksList() {
        for (int i = 0; i < stocks.size(); i++) {
            GetDataAsyncTask task = new GetDataAsyncTask("https://financialmodelingprep.com/api/v3/company/profile/" + stocks.get(i));
            task.setCallbackInterface(this);
            task.execute();
        }
    }

    public String getStockIdText() {
        EditText stockId = findViewById(R.id.editTextAddNewStock);
        String stockIdText = stockId.getText().toString();
        stockId.setText(null);
        return stockIdText;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.buttonAddNewStock) {
            String stockId = getStockIdText();
            if (stockId != null && stockId.length() > 0) {
                GetDataAsyncTask task = new GetDataAsyncTask("https://financialmodelingprep.com/api/v3/company/profile/" + stockId);
                task.setCallbackInterface(this);
                task.execute();
            }



        }
    }

    @Override
    public void networkFetchDone(String data) {
        if (data == Utilities.ERROR_DATA_NOT_FOUND) {
            Toast.makeText(getApplicationContext(),"DATA not found with ID.",Toast.LENGTH_SHORT).show();
            Log.d("Error", "DATA not found with ID.");
        }
        else if (data == Utilities.ERROR_HTTP_REQUEST) {
            Toast.makeText(getApplicationContext(),"ERROR with HTTP Request.",Toast.LENGTH_SHORT).show();
            Log.d("Error", "ERROR with HTTP Request.");
        }
        else {
            stockPrices.add(data + " USD");
            final ArrayAdapter<String> aa;
            aa = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, stockPrices);
            listViewStocks.setAdapter(aa);
        }
    }
}
