package com.mavbids.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import com.mavbids.R;
import com.mavbids.app.SessionManager;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class PostAdvertisement extends Activity implements AdapterView.OnItemSelectedListener{

    private EditText itemName;
    private EditText description;
    private EditText price;
    private EditText adExpiryDate;
    private Button uploadImage;
    private Button submit;
    private Button cancel;
    private Spinner spinner;
    private Spinner spinner_cat;


    String strItem, strDesc, strPrice, adExpiryDateStr, strCat, strAuc;
    String adExpDateBackendFormat;
    private DialogFragment saveDialog;
    private DialogFragment mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_advertisement);

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_post_advertisement);

        itemName= (EditText) findViewById(R.id.itemName);
        description= (EditText) findViewById(R.id.description);
        price= (EditText) findViewById(R.id.price);
        adExpiryDate= (EditText) findViewById(R.id.adExpiryDate);
        uploadImage=(Button) findViewById(R.id.uploadImage);
        submit= (Button) findViewById(R.id.submit);
        cancel = (Button) findViewById(R.id.postAdCancelbutton);

        spinner = (Spinner) findViewById(R.id.auction_spinner);
        ArrayAdapter<CharSequence> auc_adapter = ArrayAdapter.createFromResource(this,R.array.auction_array, android.R.layout.simple_spinner_item);
        auc_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(auc_adapter);

        spinner_cat = (Spinner) findViewById(R.id.category_spinner);
        ArrayAdapter<CharSequence> cat_adapter = ArrayAdapter.createFromResource(this,R.array.category_array, android.R.layout.simple_spinner_item);
        cat_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_cat.setAdapter(cat_adapter);

        spinner.setOnItemSelectedListener(this);
        spinner_cat.setOnItemSelectedListener(this);

        adExpiryDate.setInputType(InputType.TYPE_NULL);
        adExpiryDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new DatePickerDialog(PostAdvertisement.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strItem = itemName.getText().toString().trim();
                strDesc = description.getText().toString().trim();
                strPrice = price.getText().toString().trim();
                adExpiryDateStr = adExpiryDate.getText().toString().trim();

                if (strItem.length() == 0 ||
                        strPrice.length() == 0 ||
                        adExpiryDateStr.length() == 0 ||
                        strCat.length() == 0 ||
                        strAuc.length() == 0) {
                    Toast.makeText(getApplicationContext(), "All fields are mandatory.",
                            Toast.LENGTH_SHORT).show();
                } else {
                    saveDialog = ProgressDialogFragment.newInstance();
                    saveDialog.show(getFragmentManager(), "Saving");
                    new PostAd().execute();
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private class PostAd extends AsyncTask<Void, Void , String> {

        @Override
        protected String doInBackground(Void... params){

            try {
                Thread.sleep(500);
            } catch (Exception e){}

            try {
                OkHttpClient client = new OkHttpClient();

                HttpUrl url = new HttpUrl.Builder()
                        .scheme("http")
                        .host(SessionManager.getInstance().getMavBidsHost())
                        .port(80)
                        .addPathSegment("MavBids")
                        .addPathSegment("postAdvertisement")
                        .build();

                RequestBody formBody = new FormEncodingBuilder()
                        .add("sellerId", SessionManager.getInstance().getUserId())
                        .add("itemName", strItem)
                        .add("description", strDesc)
                        .add("startingPrice", strPrice)
                        .add("auctionType", strAuc)
                        .add("categoryID", strCat)
                        .add("expiryDateStr",adExpDateBackendFormat)
                        .add("status","ON SALE")
                        .build();

                String sessionId = SessionManager.getInstance().getSessionId();

                Request request = new Request.Builder()
                        .url(url)
                        .addHeader("Cookie", sessionId)
                        .post(formBody)
                        .build();

                Response response = client.newCall(request).execute();

                return response.body().string();
            } catch (Exception e){
                Log.e("HTTP Error", e.getMessage());
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {

            saveDialog.dismiss();

            try {
                Thread.sleep(500);
            } catch (Exception e){}


            if(result != null) {
                try {
                    JSONObject obj = (JSONObject) new JSONTokener(result).nextValue();
                    String resultStr = obj.getString("result");

                    if(resultStr.equalsIgnoreCase("true")){
                        mDialog = AlertDialogFragment.newInstance();
                        mDialog.show(getFragmentManager(), "Info");
                    }
                } catch (Exception e) {
                    Log.e("JSON Parse", e.getMessage());
                }

            }

        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch(parent.getId()){
            case R.id.auction_spinner:
                TextView myText= (TextView) view;
                strAuc=myText.getText().toString();
                break;
            case R.id.category_spinner:
                TextView myText2= (TextView) view;
                strCat="" + parent.getSelectedItemPosition();
                break;
            default:
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void navigateToHomeScreen(){
        finish();
    }

    Calendar myCalendar = Calendar.getInstance();

    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        }

    };

    private void updateLabel() {

        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        SimpleDateFormat sdfB = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        adExpDateBackendFormat = sdfB.format(myCalendar.getTime());

        adExpiryDate.setText(sdf.format(myCalendar.getTime()));
    }

    public static class AlertDialogFragment extends DialogFragment {

        public static AlertDialogFragment newInstance() {
            return new AlertDialogFragment();
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            return new AlertDialog.Builder(getActivity())
                .setMessage("Successfully posted")
                .setCancelable(false)
                .setPositiveButton("Ok",
                        new DialogInterface.OnClickListener() {
                            public void onClick(
                                    final DialogInterface dialog, int id) {
                                ((PostAdvertisement) getActivity()).navigateToHomeScreen();
                            }
                        }).create();
        }
    }

    public static class ProgressDialogFragment extends DialogFragment {

        public static ProgressDialogFragment newInstance() {
            return new ProgressDialogFragment();
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            final ProgressDialog dialog = new ProgressDialog(getActivity());
            dialog.setMessage("Posting Advertisement...");
            dialog.setIndeterminate(true);

            return dialog;
        }
    }
}
