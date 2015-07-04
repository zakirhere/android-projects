package com.zakir.java;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnItemSelectedListener {
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		
		Spinner spinner = (Spinner) findViewById(R.id.spinner1);
		// Create an ArrayAdapter using the string array and a default spinner layout
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
		        R.array.tip_array, android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		spinner.setAdapter(adapter);
		bindAllObjects();
		
	}
	public void bindAllObjects()
	{
		
		Button btnExit = (Button) findViewById(R.id.bExit);
		btnExit.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				System.exit(0);
				
			}
			
			
		});
		
		final Button bSumbit = (Button) findViewById(R.id.button1);
		bSumbit.setOnClickListener(new View.OnClickListener() {	
			@Override
			public void onClick(View v) {
				EditText billAmt = (EditText) findViewById(R.id.editText1);
				EditText tipAmt = (EditText) findViewById(R.id.editText2);
				EditText totAmt = (EditText) findViewById(R.id.editText3);
				
				if(!isValid_BillAmount(billAmt))
					Toast.makeText(MainActivity.this, "Please enter valid bill amount...", Toast.LENGTH_LONG).show();
				else {
					tipAmt.setText(this.calc_tipAmount());
					totAmt.setText(this.calc_totalAmount());
				}
			}
			
			public boolean isValid_BillAmount(EditText billAmt) {
				String val_billAmount;
				val_billAmount = billAmt.getText().toString().trim();
				if(val_billAmount.equals("")) {
					return false;
				}
				else 
					return val_billAmount.matches("-?\\d+");
				
				
			}
			
			public boolean isInteger(String s) {
			    try { 
			        Integer.parseInt(s); 
			    } catch(NumberFormatException e) { 
			        return false; 
			    }
			    // only got here if we didn't return false
			    return true;
			}
		
			public String calc_tipAmount() {
				double tipAmt = 0;
				EditText billAmt = (EditText) findViewById(R.id.editText1);
				Spinner sTipSpinner = (Spinner) findViewById(R.id.spinner1);
				
				tipAmt = Double.parseDouble(billAmt.getText().toString()) 
							* Integer.parseInt(sTipSpinner.getSelectedItem().toString()) / 100;
				
				return Double.toString(tipAmt);
			}
			
			public String calc_totalAmount() {
				double totAmt = 0;
				EditText billAmt = (EditText) findViewById(R.id.editText1);
				EditText tipAmt = (EditText) findViewById(R.id.editText2);
				
				totAmt = Double.parseDouble(billAmt.getText().toString()) 
						+ Double.parseDouble(tipAmt.getText().toString());
						
				return Double.toString(totAmt); 
			}
		
		});

	}
	

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.mnu_help:
			Toast.makeText(MainActivity.this, "USE YOUR BRAIN..HAHA", Toast.LENGTH_SHORT).show();
			return true;
		case R.id.mnu_about:
			Toast.makeText(MainActivity.this, "Thanks Zakir Sayed at zakirhere@gmail.com (2013)", Toast.LENGTH_LONG).show();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	public void onItemSelected(AdapterView<?> parent, View view, 
            int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)
		Spinner spinner = (Spinner) findViewById(R.id.spinner1);
		spinner.setOnItemSelectedListener(this);
		String s = spinner.getSelectedItem().toString();
		EditText billAmt = (EditText) findViewById(R.id.editText1);
		billAmt.setText(s);
		Toast.makeText(this, "Spinner item selected " + s, Toast.LENGTH_LONG).show(); 
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }

}
