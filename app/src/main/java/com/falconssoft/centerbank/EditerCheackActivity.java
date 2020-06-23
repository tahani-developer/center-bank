package com.falconssoft.centerbank;

import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.MovementMethod;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class EditerCheackActivity extends AppCompatActivity {

    LinearLayout linerEditing, linerBarcode;
    TextView scanBarcode, AmouWord;
    Button SingUpButton;
    EditText Danier, phails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editer_check_layout);
        initi();

        linerEditing.setVisibility(View.GONE);
        linerBarcode.setVisibility(View.VISIBLE);
        scanBarcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                readBarCode();
            }
        });

        AmouWord.setMovementMethod(new ScrollingMovementMethod());

        phails.addTextChangedListener(textWatcher);

        Danier.addTextChangedListener(textWatcher);


    }

    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            String amount = "";
            if (!Danier.getText().toString().equals("")) {

                if (!phails.getText().toString().equals("")) {
                    amount = Danier.getText().toString() + "." + phails.getText().toString();
                } else {
                    amount = Danier.getText().toString() + "." + "00";
                }
            }

            if (!phails.getText().toString().equals("")) {

                if (!Danier.getText().toString().equals("")) {
                    amount = Danier.getText().toString() + "." + phails.getText().toString();
                } else {
                    amount = "00" + "." + phails.getText().toString();
                }
            }


            NumberToArabic numberToArabic = new NumberToArabic();
            String amountWord = numberToArabic.getArabicString(amount);

            Log.e("Ammount", "Jd +" + amountWord);
            AmouWord.setText(amountWord);


        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };


    private void initi() {
        linerEditing = findViewById(R.id.linerEditing);
        linerBarcode = findViewById(R.id.linerBarcode);
        scanBarcode = findViewById(R.id.scanBarcode);
        Danier = findViewById(R.id.denier);
        phails = findViewById(R.id.Phils);
        AmouWord = findViewById(R.id.AmouWord);
        SingUpButton = findViewById(R.id.SingUpButton);
    }


    //TextView itemCodeText, int swBarcode
    public void readBarCode() {

//        barCodTextTemp = itemCodeText;
        Log.e("barcode_099", "in");
        IntentIntegrator intentIntegrator = new IntentIntegrator(EditerCheackActivity.this);
        intentIntegrator.setDesiredBarcodeFormats(intentIntegrator.ALL_CODE_TYPES);
        intentIntegrator.setBeepEnabled(false);
        intentIntegrator.setCameraId(0);
        intentIntegrator.setPrompt("SCAN");
        intentIntegrator.setBarcodeImageEnabled(false);
        intentIntegrator.initiateScan();


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        IntentResult Result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (Result != null) {
            if (Result.getContents() == null) {
                Log.d("MainActivity", "cancelled scan");
                Toast.makeText(this, "cancelled", Toast.LENGTH_SHORT).show();
//                TostMesage(getResources().getString(R.string.cancel));
            } else {
                Log.d("MainActivity", "Scanned");
                Toast.makeText(this, "Scan ___" + Result.getContents(), Toast.LENGTH_SHORT).show();
//                TostMesage(getResources().getString(R.string.scan)+Result.getContents());
//                barCodTextTemp.setText(Result.getContents() + "");
//                openEditerCheck();

                linerEditing.setVisibility(View.VISIBLE);
                linerBarcode.setVisibility(View.GONE);
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

}
