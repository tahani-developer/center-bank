package com.falconssoft.centerbank;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import de.hdodenhof.circleimageview.CircleImageView;
import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageButton;

public class MainActivity extends AppCompatActivity {
    CircleImageView imageView ;
    TextView barCodTextTemp,scanBarcode;
    private LinearLayout addAccount, chooseAccount, generateCheque, logHistory;
    private TextView closeDialog;
    private SearchView searchAccount;
    private RecyclerView recyclerViewSearchAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        addAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(MainActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_add_account);

                //TODO add dialog function
                dialog.show();

            }
        });

        chooseAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(MainActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//                dialog.setCancelable(false);
                dialog.setContentView(R.layout.dialog_choose_account);

                closeDialog = findViewById(R.id.dialog_search_close);
                searchAccount = findViewById(R.id.dialog_search_tool);
                recyclerViewSearchAccount = findViewById(R.id.dialog_search_recycler);


                //TODO add dialog function
                dialog.show();

            }
        });


//        gib.setImageResource(R.drawable.rscananimation);
//        final MediaController mc = new MediaController(MainActivity.this);
//        mc.setMediaPlayer((GifDrawable) gib.getDrawable());
//        mc.setAnchorView(gib);
//        mc.show();

//        scanBarcode.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                readBarCode();
//            }
//        });


    }

void init(){
//    imageView = findViewById(R.id.profile_image);
//    scanBarcode=findViewById(R.id.scanBarcode);

    addAccount = findViewById(R.id.main_addAccount);
    chooseAccount = findViewById(R.id.main_chooseAccount);
    generateCheque = findViewById(R.id.main_send);
    logHistory = findViewById(R.id.main_history);

}

//TextView itemCodeText, int swBarcode
    public void readBarCode() {

//        barCodTextTemp = itemCodeText;
        Log.e("barcode_099", "in");
        IntentIntegrator intentIntegrator = new IntentIntegrator(MainActivity.this);
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
                openEditerCheck();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void openEditerCheck() {

        Intent intent= new Intent(MainActivity.this,EditerCheackActivity.class);
        startActivity(intent);
    }

}
