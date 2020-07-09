package com.falconssoft.centerbank;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.falconssoft.centerbank.Models.ChequeInfo;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.Collator;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import cn.pedant.SweetAlert.SweetAlertDialog;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.falconssoft.centerbank.EditerCheackActivity.CAMERA_PIC_REQUEST;
import static com.falconssoft.centerbank.LogInActivity.LOGIN_INFO;

public class JeroActivity extends AppCompatActivity {
    String serverLink;
    String AccountNo, serverPic;
    static String phoneNo;
    List<ChequeInfo> ChequeInfoGiro;
    ListView listGiro;
    TextView customName, dateText, cheqNo;
    public LinearLayout giroList, editLiner, barcodeLiner;
    public List<ChequeInfo> chequeInfoTilar;
    public static TextView getTrial;
    EditText nationalNo, phoneNos, company, notes, fName, sName, tName, fourthName;
    TextView Danier, phails;
    int index;
    Button pushCheque;
    TextView scanBarcode, AmouWord, date, amountTV;
    TableRow picRow;
    int flag = 0;
    CircleImageView CheckPic;
    ListAdapterGiro listAdapterLogHistory;
    Bitmap serverPicBitmap;
    ChequeInfo chequeInfos;
    String localNationlNo;
    CheckBox checkBox_CO,checkBox_firstpinifit;
    JSONObject jsonObject;
    TextView CheckPicText;
    Date currentTimeAndDate;
    SimpleDateFormat df;
    String today="";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jiro_activity);


        SharedPreferences loginPrefs1 = getSharedPreferences(LOGIN_INFO, MODE_PRIVATE);
        serverLink = loginPrefs1.getString("link", "");

        listGiro=findViewById(R.id.GiroList);
        getTrial=findViewById(R.id.getTrial);
        giroList=findViewById(R.id.giroList);
        editLiner=findViewById(R.id.linerEditing);
        barcodeLiner=findViewById(R.id.linerBarcode);
        phoneNos = findViewById(R.id.editorCheque_phoneNo);
        fName     = findViewById(R.id.first_name);
        sName     = findViewById(R.id.second_name);
        tName     = findViewById(R.id.thered_name);
        fourthName= findViewById(R.id.fourth_name);

        date = findViewById(R.id.editorCheque_date);
        checkBox_CO= findViewById(R.id.checkBox_CO);
        checkBox_firstpinifit= findViewById(R.id.checkBox_firstpinifit);
        Danier = findViewById(R.id.denier);
        phails = findViewById(R.id.Phils);
        AmouWord = findViewById(R.id.AmouWord);
        pushCheque = findViewById(R.id.SingUpButton);
        company = findViewById(R.id.editorCheque_company);
        notes = findViewById(R.id.editorCheque_notes);
        picRow = findViewById(R.id.editorCheque_picLinear);
        amountTV = findViewById(R.id.editorCheque_amountTV);
        CheckPicText=findViewById(R.id.CheckPicText);
        CheckPic = findViewById(R.id.CheckPic);
        nationalNo=findViewById(R.id.editorCheque_nationalNo);
        scanBarcode = findViewById(R.id.scanBarcode);

        currentTimeAndDate = Calendar.getInstance().getTime();
        df = new SimpleDateFormat("dd/MM/yyyy");
        today = df.format(currentTimeAndDate);
        date.setText(convertToEnglish(today));


        giroList.setVisibility(View.VISIBLE);
        editLiner.setVisibility(View.GONE);
        barcodeLiner.setVisibility(View.GONE);

        scanBarcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                readBarCode();
            }
        });

        SharedPreferences loginPrefs = getSharedPreferences(LOGIN_INFO, MODE_PRIVATE);
//        AccountNo = getIntent().getStringExtra("AccountNo");
        phoneNo = loginPrefs.getString("mobile", "");

        customName=findViewById(R.id.customName);
        dateText=findViewById(R.id.date);
        cheqNo=findViewById(R.id.chNo);


        CheckPic.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
//                Calendar cal = Calendar.getInstance();
//                File file = new File(Environment.getExternalStorageDirectory(),  "/DCIM/Camera/IMG_20200629_112400" +".jpg");//+(cal.getTimeInMillis()
//                if (ContextCompat.checkSelfPermission(EditerCheackActivity.this,
//                        Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED) {
//                    // Permission is not granted
//                    if (ActivityCompat.shouldShowRequestPermissionRationale(EditerCheackActivity.this,
//                            Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
//
//
//                    } else {
//                        // No explanation needed; request the permission
//                        ActivityCompat.requestPermissions(EditerCheackActivity.this,
//                                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
//                                1);
//                    }
//                }
//
//                if(!file.exists()){
//                    try {
//                        file.createNewFile();
//                    } catch (IOException e) {
//                        // TODO Auto-generated catch block
//                        e.printStackTrace();
//                    }
//                }else{
//                    file.delete();
//                    try {
//                        file.createNewFile();
//                    } catch (IOException e) {
//                        // TODO Auto-generated catch block
//                        e.printStackTrace();
//                    }
//                }
//
//                if (file.exists()){
//                    capturedImageUri = Uri.fromFile(file) ;
//                    Log.e("uri", capturedImageUri.getPath());
//                }
//                flag = 0;
//                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
////                startActivityForResult(cameraIntent, CAMERA_PIC_REQUEST);
//
////                file = Uri.fromFile(getFile());
////
////                //Setting the file Uri to my photo
//                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT,file);
//
//                if(cameraIntent.resolveActivity(getPackageManager())!=null)
//                {
//                    startActivityForResult(cameraIntent, CAMERA_PIC_REQUEST);
//                }
                flag = 0;
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_PIC_REQUEST);
            }
        });


        getTrial.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if(getTrial.getText().toString().equals("1")){
                   int index= Integer.parseInt(getTrial.getTag().toString());
                    Log.e("getTrialText","  "+getTrial.getText().toString());
                    Log.e("getTrial","  "+index+"   "+ChequeInfoGiro.get(index).getChequeNo());
                    giroList.setVisibility(View.GONE);
                    barcodeLiner.setVisibility(View.VISIBLE);
                    editLiner.setVisibility(View.GONE);
                    chequeInfos=ChequeInfoGiro.get(index);

                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        cheqNo.setTag("1");
        cheqNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(JeroActivity.this, "sort", Toast.LENGTH_SHORT).show();
                Collections.sort(ChequeInfoGiro, new JeroActivity.CheqNoSorter());
//                Log.e("Sort2",""+sortAlpha());
                if (cheqNo.getTag().toString().equals("1")) {
                     listAdapterLogHistory = new ListAdapterGiro(JeroActivity.this, ChequeInfoGiro,giroList);
                    listGiro.setAdapter(listAdapterLogHistory);
                    cheqNo.setTag("0");
                    cheqNo.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_upward_black_24dp, 0);
                } else if (cheqNo.getTag().toString().equals("0")) {
                    Collections.reverse(ChequeInfoGiro);
                     listAdapterLogHistory = new ListAdapterGiro(JeroActivity.this, ChequeInfoGiro,giroList);
                    listGiro.setAdapter(listAdapterLogHistory);
                    cheqNo.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_downward_black_24dp, 0);

                    cheqNo.setTag("1");
                }
            }
        });


        dateText.setTag("1");
        dateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(JeroActivity.this, "SortDate", Toast.LENGTH_SHORT).show();
                sortDate();
//                Log.e("Sort2",""+sortAlpha());
                if (dateText.getTag().toString().equals("1")) {
                     listAdapterLogHistory = new ListAdapterGiro(JeroActivity.this, ChequeInfoGiro,giroList);
                    listGiro.setAdapter(listAdapterLogHistory);
                    dateText.setTag("0");
                    dateText.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_downward_black_24dp, 0);
                } else if (dateText.getTag().toString().equals("0")) {
                    Collections.reverse(ChequeInfoGiro);
                     listAdapterLogHistory = new ListAdapterGiro(JeroActivity.this, ChequeInfoGiro,giroList);
                    listGiro.setAdapter(listAdapterLogHistory);
                    dateText.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_upward_black_24dp, 0);

                    dateText.setTag("1");
                }
            }
        });

        customName.setTag("0");
        customName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Toast.makeText(JeroActivity.this, "Sort", Toast.LENGTH_SHORT).show();
                sortAlpha();
//                Log.e("Sort2",""+sortAlpha());
                if (customName.getTag().toString().equals("1")) {
                    ListAdapterGiro listAdapterLogHistory = new ListAdapterGiro(JeroActivity.this, ChequeInfoGiro,giroList);
                    listGiro.setAdapter(listAdapterLogHistory);
                    customName.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_upward_black_24dp, 0);
                    customName.setTag("0");
                } else if (customName.getTag().toString().equals("0")) {
                    Collections.reverse(ChequeInfoGiro);
                    ListAdapterGiro listAdapterLogHistory = new ListAdapterGiro(JeroActivity.this, ChequeInfoGiro,giroList);
                    listGiro.setAdapter(listAdapterLogHistory);
                    customName.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_downward_black_24dp, 0);
                    customName.setTag("1");
                }

            }
        });



        pushCheque.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {
// CHECKINFO={"BANKNO":"004","BANKNM":"","BRANCHNO":"0099","CHECKNO":"390144","ACCCODE":"1014569990011000"
// ,"IBANNO":"","CUSTOMERNM":"الخزينة والاستثمار","QRCODE":"","SERIALNO":"720817C32F164968"
// ,"CHECKDUEDATE":"21/12/2020","TOCUSTOMERNM":"ALAA SALEM","AMTJD":"100","AMTFILS":"0"
// ,"AMTWORD":"One Handred JD","TOCUSTOMERMOB":"0798899716","TOCUSTOMERNATID":"123456","CHECKPIC":""}


                localNationlNo = nationalNo.getText().toString();
                String localPhoneNo = phoneNos.getText().toString();
//                String localSender = sender.getText().toString();

                String localReciever =""+ fName.getText().toString()+"sName"+sName.getText().toString()+"tName"+tName.getText().toString()+"fName"+fourthName.getText().toString();
                String localDinar = Danier.getText().toString();
                String localFils = "" + phails.getText().toString();
                String localMoneyInWord = AmouWord.getText().toString();
                String localDate = date.getText().toString();

                if (!TextUtils.isEmpty(localNationlNo) && localNationlNo.length() == 10)
                    if (!TextUtils.isEmpty(localPhoneNo) && localPhoneNo.length() == 10)
                        if (!TextUtils.isEmpty(localReciever))
                            if (!TextUtils.isEmpty(localDate))
                                if (!TextUtils.isEmpty(localDinar)) {
                                    if (!TextUtils.isEmpty(serverPic)) {
                                        SharedPreferences loginPrefs = getSharedPreferences(LOGIN_INFO, MODE_PRIVATE);
                                        String phoneNo1 = loginPrefs.getString("mobile", "");

                                        Danier.setError(null);
                                        date.setError(null);
                                        fName.setError(null);
                                        sName.setError(null);
                                        tName.setError(null);
                                        fourthName.setError(null);
                                        phoneNos.setError(null);
                                        nationalNo.setError(null);

                                        String checkBox_C="",checkBox_Fb="";

                                        if(checkBox_CO.isChecked()){
                                            checkBox_C="1";
                                        }else{
                                            checkBox_C="0";
                                        }

                                        if(checkBox_firstpinifit.isChecked()){
                                            checkBox_Fb="1";
                                        }else{
                                            checkBox_Fb="0";
                                        }

                                        ChequeInfo chequeInfo = new ChequeInfo();
                                        chequeInfo.setBankNo(chequeInfos.getBankNo());
                                        chequeInfo.setBankName("Jordan Bank");
                                        chequeInfo.setBranchNo(chequeInfos.getBranchNo());
                                        chequeInfo.setChequeNo(chequeInfos.getChequeNo());
                                        chequeInfo.setAccCode(chequeInfos.getAccCode());
                                        chequeInfo.setIbanNo(chequeInfos.getIbanNo());
                                        chequeInfo.setCustName(chequeInfos.getCustName());
                                        chequeInfo.setQrCode(chequeInfos.getQrCode());
                                        chequeInfo.setSerialNo(chequeInfos.getSerialNo());
                                        chequeInfo.setChequeData(localDate);
                                        chequeInfo.setToCustomerName(localReciever);
                                        chequeInfo.setMoneyInDinar(localDinar);
                                        chequeInfo.setMoneyInFils(localFils);
                                        chequeInfo.setMoneyInWord(localMoneyInWord);
                                        chequeInfo.setRecieverMobileNo(localPhoneNo);
                                        chequeInfo.setRecieverNationalID(localNationlNo);
                                        chequeInfo.setChequeImage(serverPic);
                                        chequeInfo.setUserName(phoneNo1);
                                        chequeInfo.setISCO(checkBox_C);
                                        chequeInfo.setISBF(checkBox_Fb);
                                        chequeInfo.setCompanyName(company.getText().toString());
                                        chequeInfo.setNoteCheck(notes.getText().toString());
                                        Log.e("showpic", serverPic);

                                        jsonObject = new JSONObject();
                                        jsonObject = chequeInfo.getJSONObject();

//                                    imageSend();
//                uploadMultipart(String.valueOf(creatFile(serverPicBitmap)));
//                new Image().execute();

                                        new SaveGiro().execute();
//                                    new GetAllTransaction().execute();
                                    } else {
                                        CheckPicText.setError("Required!");
                                    }
                                } else {
                                    Danier.setError("Required!");
                                }
                            else {
                                date.setError("Required!");
                            }
                        else {
                            fName.setError("Required!");
                            sName.setError("Required!");
                            tName.setError("Required!");
                            fourthName.setError("Required!");
                        }
                    else {
                        phoneNos.setError("Required!");
                    }
                else {
                    nationalNo.setError("Required!");
                }
            }

        });


        new GetGiro().execute();

    }


    public void readBarCode() {

        flag = 1;
//        barCodTextTemp = itemCodeText;
        Log.e("barcode_099", "in");
        IntentIntegrator intentIntegrator = new IntentIntegrator(JeroActivity.this);
        intentIntegrator.setDesiredBarcodeFormats(intentIntegrator.ALL_CODE_TYPES);
        intentIntegrator.setBeepEnabled(false);
        intentIntegrator.setCameraId(0);
        intentIntegrator.setPrompt("SCAN");
        intentIntegrator.setBarcodeImageEnabled(false);
        intentIntegrator.initiateScan();


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (flag == 1) {
            IntentResult Result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
            if (Result != null) {
                if (Result.getContents() == null) {
                    Log.d("MainActivity", "cancelled scan");
                    Toast.makeText(this, "cancelled", Toast.LENGTH_SHORT).show();
//                TostMesage(getResources().getString(R.string.cancel));
                } else {

                    Log.e("MainActivity", "" + Result.getContents());
                    Toast.makeText(this, "Scan ___" + Result.getContents(), Toast.LENGTH_SHORT).show();
//                TostMesage(getResources().getString(R.string.scan)+Result.getContents());
//                barCodTextTemp.setText(Result.getContents() + "");
//                openEditerCheck();

                    String ST = Result.getContents();
                    String []arr = ST.split(";");

                    String checkNo = arr[0];
//                    String bankNo = arr[1];
//                    String branchNo = arr[2];
//                    String accCode = arr[3];
//                    String ibanNo = arr[4];
//                    String custName= "";

                    if(checkNo.equals(chequeInfos.getChequeNo())) {
                        new TillerGetCheck(ChequeInfoGiro.get(index)).execute();
                    }else{
                        new SweetAlertDialog(JeroActivity.this, SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("Cheque")
                                .setContentText("This cheque is not the same as the cheque you want to send !!!")
                                .setConfirmText("Ok")
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @SuppressLint("WrongConstant")
                                    @Override
                                    public void onClick(SweetAlertDialog sDialog) {
                                        finish();
                                        Intent intent=new Intent(JeroActivity.this,JeroActivity.class);
                                        startActivity(intent);
                                        sDialog.dismissWithAnimation();
                                    }
                                }).show();
                    }


                    // showSweetDialog(true);

                }

            } else {
                super.onActivityResult(requestCode, resultCode, data);
            }
        } else {//
            try {
                if (requestCode == CAMERA_PIC_REQUEST) {
                    Bitmap image = (Bitmap) data.getExtras().get("data");
                    if (image != null) {
                        CheckPic.setImageBitmap(image);
                        serverPicBitmap=image;
                        serverPic = bitMapToString(image);
                    }
                }


            } catch (Exception e) {
                Log.e("not getCamera", "message " + e.toString());
            }

        }

    }


void FailGiroSave(){

    giroList.setVisibility(View.GONE);
    barcodeLiner.setVisibility(View.GONE);
    editLiner.setVisibility(View.VISIBLE);

    Danier.setText(""+chequeInfos.getMoneyInDinar());
    phails.setText(""+chequeInfos.getMoneyInFils());
    AmouWord.setText(""+chequeInfos.getMoneyInWord());

}

    private class GetGiro extends AsyncTask<String, String, String> {
        private String JsonResponse = null;
        private HttpURLConnection urlConnection = null;
        private BufferedReader reader = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            progressDialog = new ProgressDialog(context,R.style.MyTheme);
//            progressDialog.setCancelable(false);
//            progressDialog.setMessage("Loading...");
//            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//            progressDialog.setProgress(0);
//            progressDialog.show();

//            pd.getProgressHelper().setBarColor(Color.parseColor("#FDD835"));
//            pd.setTitleText(context.getResources().getString(R.string.importstor));

        }

        @Override
        protected String doInBackground(String... params) {
            try {

//
//                final List<MainSetting>mainSettings=dbHandler.getAllMainSetting();
//                String ip="";
//                if(mainSettings.size()!=0) {
//                    ip=mainSettings.get(0).getIP();
//                }

                String link = serverLink + "GetAcceptedCheck";

                //?ACCCODE=4014569990011000&MOBNO=&WHICH=0
                String data = "MOBILENO=" + URLEncoder.encode(phoneNo, "UTF-8");

                URL url = new URL(link);
                Log.e("link,3 ", serverLink+"   "+link+ "   "+data);

                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                httpURLConnection.setRequestMethod("POST");

                DataOutputStream wr = new DataOutputStream(httpURLConnection.getOutputStream());
                wr.writeBytes(data);
                wr.flush();
                wr.close();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                StringBuffer stringBuffer = new StringBuffer();

                while ((JsonResponse = bufferedReader.readLine()) != null) {
                    stringBuffer.append(JsonResponse + "\n");
                }

                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();

                Log.e("tag", "TAG_GetStor -->" + stringBuffer.toString());

                return stringBuffer.toString();

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e("tag", "Error closing stream", e);
                    }
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String JsonResponse) {
            super.onPostExecute(JsonResponse);


            if (JsonResponse != null && JsonResponse.contains("StatusDescreption\":\"OK")) {
                Log.e("GetLogSuccess", "****Success");

//
                try {

                    JSONObject parentArray = new JSONObject(JsonResponse);
                    JSONArray parentInfo = parentArray.getJSONArray("INFO");

                    ChequeInfoGiro = new ArrayList<>();

                    for (int i = 0; i < parentInfo.length(); i++) {
                        JSONObject finalObject = parentInfo.getJSONObject(i);

                        ChequeInfo obj = new ChequeInfo();

                        //[{"ROWID":"AAAp0DAAuAAAAC0AAC","BANKNO":"004","BANKNM":"","BRANCHNO":"0099","CHECKNO":"390144","ACCCODE":"1014569990011000","IBANNO":"","CUSTOMERNM":"الخزينة والاستثمار","QRCODE":"","SERIALNO":"720817C32F164968","CHECKISSUEDATE":"28\/06\/2020 10:33:57","CHECKDUEDATE":"21\/12\/2020","TOCUSTOMERNM":"ALAA SALEM","AMTJD":"100","AMTFILS":"0","AMTWORD":"One Handred JD","TOCUSTOMERMOB":"0798899716","TOCUSTOMERNATID":"123456","CHECKWRITEDATE":"28\/06\/2020 10:33:57","CHECKPICPATH":"E:\\00400991014569990011000390144.png","TRANSSTATUS":""}]}
//                        if(!finalObject.getString("ISFB").equals("1")&&finalObject.getString("TRANSTYPE").equals("1")&&finalObject.getString("STATUS").equals("1")){
                        obj.setRowId(finalObject.getString("ROWID"));
                        obj.setBankNo(finalObject.getString("BANKNO"));
                        obj.setBankName(finalObject.getString("BANKNM"));
                        obj.setBranchNo(finalObject.getString("BRANCHNO"));
                        obj.setChequeNo(finalObject.getString("CHECKNO"));
                        obj.setAccCode(finalObject.getString("ACCCODE"));
                        obj.setIbanNo(finalObject.getString("IBANNO"));
                        obj.setCustName(finalObject.getString("CUSTOMERNM"));
                        obj.setQrCode(finalObject.getString("QRCODE"));
                        obj.setSerialNo(finalObject.getString("SERIALNO"));
                        obj.setCheckIsSueDate(finalObject.getString("CHECKISSUEDATE"));//?
                        obj.setCheckDueDate(finalObject.getString("CHECKDUEDATE"));//?
                        obj.setToCustomerName(finalObject.getString("TOCUSTOMERNM"));
                        obj.setMoneyInDinar(finalObject.getString("AMTJD"));
                        obj.setMoneyInFils(finalObject.getString("AMTFILS"));
                        obj.setMoneyInWord(finalObject.getString("AMTWORD"));
                        obj.setToCustomerMobel(finalObject.getString("TOCUSTOMERMOB"));
                        obj.setToCustomerNationalId(finalObject.getString("TOCUSTOMERNATID"));
                        obj.setCustomerWriteDate(finalObject.getString("CHECKWRITEDATE"));//?
//                        obj.setCheqPIc(finalObject.getString("CHECKPICPATH"));
                        obj.setTransType(finalObject.getString("TRANSTYPE"));
                        obj.setStatus("1");
                        obj.setUserName(finalObject.getString("USERNO"));
                        obj.setCompanyName(finalObject.getString("COMPANY"));
                        obj.setNoteCheck(finalObject.getString("NOTE"));
                        obj.setISBF(finalObject.getString("ISFB"));
                        obj.setISCO(finalObject.getString("ISCO"));

                        obj.setISOpen("0");


                            ChequeInfoGiro.add(obj);
//                        }


                    }


//                  ChequeInfoLogHistoryMain.get(0).setCustName("مها");
//                    ChequeInfoLogHistoryMain.get(1).setCustName("تهاني");
//                    ChequeInfoLogHistoryMain.get(2).setCustName("عبير");
//                    ChequeInfoLogHistoryMain.get(3).setCustName("احمد");
                    sortAlpha();
                    ListAdapterGiro listAdapterLogHistory = new ListAdapterGiro(JeroActivity.this, ChequeInfoGiro,giroList);
                    listGiro.setAdapter(listAdapterLogHistory);


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } else {
                Log.e("TAG_GetStor", "****Failed to export data");
//                if(!isAssetsIn.equals("1")) {
//                    if (pd != null) {
//                        pd.dismiss();
//                        new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
//                                .setTitleText(context.getResources().getString(R.string.ops))
//                                .setContentText(context.getResources().getString(R.string.faildstore))
//                                .show();
//                    }
//                }else{
//                    pd.dismiss();
//                    new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
//                            .setTitleText(context.getResources().getString(R.string.ops))
//                            .setContentText(context.getResources().getString(R.string.faildstore))
//                            .show();
//                    new SyncGetAssest().execute();
//                }
            }
//            progressDialog.dismiss();

        }
    }

    public  class TillerGetCheck extends AsyncTask<String, String, String> {
        private String JsonResponse = null;
        private HttpURLConnection urlConnection = null;
        private BufferedReader reader = null;
ChequeInfo chequeInfo;

public TillerGetCheck(ChequeInfo chequeInfo) {
            this.chequeInfo=chequeInfo;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            progressDialog = new ProgressDialog(context,R.style.MyTheme);
//            progressDialog.setCancelable(false);
//            progressDialog.setMessage("Loading...");
//            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//            progressDialog.setProgress(0);
//            progressDialog.show();

//            pd.getProgressHelper().setBarColor(Color.parseColor("#FDD835"));
//            pd.setTitleText(context.getResources().getString(R.string.importstor));

        }

        @Override
        protected String doInBackground(String... params) {
            try {

//
//                final List<MainSetting>mainSettings=dbHandler.getAllMainSetting();
//                String ip="";
//                if(mainSettings.size()!=0) {
//                    ip=mainSettings.get(0).getIP();
//                }

                String link =serverLink + "TillerGetCheck";


//                ACCCODE=1014569990011000&IBANNO=""&SERIALNO=""&BANKNO=004&BRANCHNO=0099&CHECKNO=390144&USESERIAL=0

                //?ACCCODE=4014569990011000&MOBNO=&WHICH=0
                String data = "ACCCODE=" + URLEncoder.encode(chequeInfo.getAccCode(), "UTF-8") + "&" +
                        "IBANNO=" + URLEncoder.encode(chequeInfo.getIbanNo(), "UTF-8") + "&" +
                        "SERIALNO=" + URLEncoder.encode(chequeInfo.getSerialNo(), "UTF-8") + "&" +
                        "BANKNO=" + URLEncoder.encode(chequeInfo.getBankNo(), "UTF-8") + "&" +
                        "BRANCHNO=" + URLEncoder.encode(chequeInfo.getBranchNo(), "UTF-8") + "&" +
                        "CHECKNO=" + URLEncoder.encode(chequeInfo.getChequeNo(), "UTF-8") + "&" +
                        "USESERIAL=" + URLEncoder.encode("0", "UTF-8");

                URL url = new URL(link);
                Log.e("link,3 ", serverLink+"   "+link+ "   "+data);

                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                httpURLConnection.setRequestMethod("POST");

                DataOutputStream wr = new DataOutputStream(httpURLConnection.getOutputStream());
                wr.writeBytes(data);
                wr.flush();
                wr.close();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                StringBuffer stringBuffer = new StringBuffer();

                while ((JsonResponse = bufferedReader.readLine()) != null) {
                    stringBuffer.append(JsonResponse + "\n");
                }

                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();

                Log.e("tag", "TAG_GetStor -->" + stringBuffer.toString());

                return stringBuffer.toString();

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e("tag", "Error closing stream", e);
                    }
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String JsonResponse) {
            super.onPostExecute(JsonResponse);


            if (JsonResponse != null && JsonResponse.contains("StatusDescreption\":\"OK")) {
                Log.e("GetLogSuccess", "****Success");

//
                try {

                    JSONObject parentArray = new JSONObject(JsonResponse);
                    JSONArray parentInfo = parentArray.getJSONArray("INFO");

//                    INFO":[{"ROWID":"AABX2UAAPAAAACDAA1","BANKNO":"004","BANKNM":"","BRANCHNO":"0099","CHECKNO":"390144","ACCCODE":"1014569990011000","IBANNO":"","CUSTOMERNM":"الصقور للبرمجيات","QRCODE":"390144;004;0099;1014569990011000","SERIALNO":"635088CD7E6D405B","CHECKISSUEDATE":"7\/2\/2020 12:51:57 PM","CHECKDUEDATE":"","TOCUSTOMERNM":"","AMTJD":"","AMTFILS":"","AMTWORD":"","TOCUSTOMERMOB":"","TOCUSTOMERNATID":"","CHECKWRITEDATE":"","CHECKPICPATH":"","USERNO":"","ISCO":"","ISFB":"","COMPANY":"","NOTE":""}]}



                    chequeInfoTilar = new ArrayList<>();
                    boolean foundIn=false;

                    for (int i = 0; i < parentInfo.length(); i++) {
                        JSONObject finalObject = parentInfo.getJSONObject(i);

                        ChequeInfo obj = new ChequeInfo();

                        if (finalObject.getString("TOCUSTOMERMOB").equals(phoneNo)) {
                            //[{"ROWID":"AAAp0DAAuAAAAC0AAC","BANKNO":"004","BANKNM":"","BRANCHNO":"0099","CHECKNO":"390144","ACCCODE":"1014569990011000","IBANNO":"","CUSTOMERNM":"الخزينة والاستثمار","QRCODE":"","SERIALNO":"720817C32F164968","CHECKISSUEDATE":"28\/06\/2020 10:33:57","CHECKDUEDATE":"21\/12\/2020","TOCUSTOMERNM":"ALAA SALEM","AMTJD":"100","AMTFILS":"0","AMTWORD":"One Handred JD","TOCUSTOMERMOB":"0798899716","TOCUSTOMERNATID":"123456","CHECKWRITEDATE":"28\/06\/2020 10:33:57","CHECKPICPATH":"E:\\00400991014569990011000390144.png","TRANSSTATUS":""}]}

                            obj.setRowId(finalObject.getString("ROWID"));
                            obj.setBankNo(finalObject.getString("BANKNO"));


                            obj.setBankName(finalObject.getString("BANKNM"));
                            obj.setBranchNo(finalObject.getString("BRANCHNO"));

                            obj.setChequeNo(finalObject.getString("CHECKNO"));
                            obj.setAccCode(finalObject.getString("ACCCODE"));

                            obj.setIbanNo(finalObject.getString("IBANNO"));
                            obj.setCustName(finalObject.getString("CUSTOMERNM"));

                            obj.setQrCode(finalObject.getString("QRCODE"));
                            obj.setSerialNo(finalObject.getString("SERIALNO"));

                            obj.setCheckIsSueDate(finalObject.getString("CHECKISSUEDATE"));//?
                            obj.setCheckDueDate(finalObject.getString("CHECKDUEDATE"));//?

                            obj.setToCustomerName(finalObject.getString("TOCUSTOMERNM"));
                            obj.setMoneyInDinar(finalObject.getString("AMTJD"));

                            obj.setMoneyInFils(finalObject.getString("AMTFILS"));
                            obj.setMoneyInWord(finalObject.getString("AMTWORD"));

                            obj.setToCustomerMobel(finalObject.getString("TOCUSTOMERMOB"));

                            obj.setToCustomerNationalId(finalObject.getString("TOCUSTOMERNATID"));
                            obj.setCustomerWriteDate(finalObject.getString("CHECKWRITEDATE"));//?

//                        obj.setCheqPIc(finalObject.getString("CHECKPICPATH"));
//                            obj.setTransType(finalObject.getString("TRANSSTATUS"));
//                            obj.setStatus(finalObject.getString("STATUS"));
                            obj.setUserName(finalObject.getString("USERNO"));

                            obj.setISBF(finalObject.getString("ISFB"));
                            obj.setISCO(finalObject.getString("ISCO"));

                            obj.setISOpen("0");


                            chequeInfoTilar.add(obj);

                            foundIn=true;

                        }
                    }

                    if(foundIn){
                        FailGiroSave();
                    }else {
                        new SweetAlertDialog(JeroActivity.this, SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("Giro Cheque ")
                                .setContentText("This check is not Yours !!!")
                                .show();
                    }



                } catch (JSONException e) {
                    e.printStackTrace();
                }//

            } else  if (JsonResponse != null && JsonResponse.contains("StatusDescreption\":\"Check Data not found")) {
                Log.e("TAG_GetStor", "****Check Data not found");


                new SweetAlertDialog(JeroActivity.this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Giro Cheque ")
                        .setContentText("Check Data not found")
                        .show();


//                if(!isAssetsIn.equals("1")) {
//                    if (pd != null) {
//                        pd.dismiss();
//                        new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
//                                .setTitleText(context.getResources().getString(R.string.ops))
//                                .setContentText(context.getResources().getString(R.string.faildstore))
//                                .show();
//                    }
//                }else{
//                    pd.dismiss();

//                    new SyncGetAssest().execute();
//                }
            }
//            progressDialog.dismiss();

        }
    }

    public String convertToEnglish(String value) {
        String newValue = (((((((((((value + "").replaceAll("١", "1")).replaceAll("٢", "2")).replaceAll("٣", "3")).replaceAll("٤", "4")).replaceAll("٥", "5")).replaceAll("٦", "6")).replaceAll("٧", "7")).replaceAll("٨", "8")).replaceAll("٩", "9")).replaceAll("٠", "0").replaceAll("٫", "."));
        return newValue;
    }

    public String bitMapToString(Bitmap bitmap) {
        if (bitmap != null) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
            byte[] arr = baos.toByteArray();
//            byte[] encoded = Base64.encode(arr, Base64.URL_SAFE | Base64.NO_PADDING | Base64.NO_WRAP);

            String result = Base64.encodeToString(arr,  Base64.URL_SAFE | Base64.NO_PADDING | Base64.NO_WRAP);
            return result;
        }
        return "";
    }

    void sortAlpha() {
        Locale arabic = new Locale("ar");
        final Collator arabicCollator = Collator.getInstance(arabic);


        Collections.sort(ChequeInfoGiro, new Comparator<ChequeInfo>() {


            @Override
            public int compare(ChequeInfo one, ChequeInfo two) {
                // TODO Auto-generated method stub

                Log.e("Sort",""+arabicCollator.compare(one.getCustName(), two.getCustName()));

                return arabicCollator.compare(one.getCustName(), two.getCustName());
            }

        });
    }


    private void sortDate() {
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy"); //your own date format
//        if (reports != null) {
        Collections.sort(ChequeInfoGiro, new Comparator<ChequeInfo>() {
            @Override
            public int compare(ChequeInfo o1, ChequeInfo o2) {
                try {
                    return simpleDateFormat.parse(o2.getCheckDueDate()).compareTo(simpleDateFormat.parse(o1.getCheckDueDate()));
                } catch (ParseException e) {
                    e.printStackTrace();
                    return 0;
                }
            }
        });
//        }
    }



    class CheqNoSorter implements Comparator<ChequeInfo> {
        @Override
        public int compare(ChequeInfo one, ChequeInfo another) {
            int returnVal = 0;

            if (Integer.parseInt(one.getChequeNo()) < Integer.parseInt(another.getChequeNo())) {
                returnVal = -1;
            } else if (Integer.parseInt(one.getChequeNo()) > Integer.parseInt(another.getChequeNo())) {
                returnVal = 1;
            } else if (one.getChequeNo() == another.getChequeNo()) {
                returnVal = 0;
            }
            return returnVal;
        }

    }

//     ******************************************** Check Pending *************************************
    private class IsCheckPinding extends AsyncTask<String, String, String> {
        private String JsonResponse = null;
        private HttpURLConnection urlConnection = null;
        private BufferedReader reader = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            progressDialog = new ProgressDialog(context,R.style.MyTheme);
//            progressDialog.setCancelable(false);
//            progressDialog.setMessage("Loading...");
//            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//            progressDialog.setProgress(0);
//            progressDialog.show();

//            pd.getProgressHelper().setBarColor(Color.parseColor("#FDD835"));
//            pd.setTitleText(context.getResources().getString(R.string.importstor));

        }

        @Override
        protected String doInBackground(String... params) {
            try {

//
//                final List<MainSetting>mainSettings=dbHandler.getAllMainSetting();
//                String ip="";
//                if(mainSettings.size()!=0) {
//                    ip=mainSettings.get(0).getIP();
//                }
                String link =serverLink +"IsCheckPinding";

//ACCCODE=1014569990011000&IBANNO=""&SERIALNO=""&BANKNO=004&BRANCHNO=0099&CHECKNO=390144
//                String data = "ACCCODE=" + URLEncoder.encode(jsonObject.getString("ACCCODE"), "UTF-8") + "&"
//                        +"IBANNO=" + URLEncoder.encode(jsonObject.getString("IBANNO"), "UTF-8") + "&"
//                        +"SERIALNO=" + URLEncoder.encode(jsonObject.getString("SERIALNO"), "UTF-8") + "&"
//                        +"BANKNO=" + URLEncoder.encode(jsonObject.getString("BANKNO"), "UTF-8") + "&"
//                        +"BRANCHNO=" + URLEncoder.encode(jsonObject.getString("BRANCHNO"), "UTF-8") + "&"
//                        +"CHECKNO=" + URLEncoder.encode(jsonObject.getString("CHECKNO"), "UTF-8");
//

                String data = "ACCCODE=" + URLEncoder.encode(chequeInfos.getAccCode(), "UTF-8") + "&" +
                        "IBANNO=" + URLEncoder.encode(chequeInfos.getIbanNo(), "UTF-8") + "&" +
                        "SERIALNO=" + URLEncoder.encode(chequeInfos.getSerialNo(), "UTF-8") + "&" +
                        "BANKNO=" + URLEncoder.encode(chequeInfos.getBankNo(), "UTF-8") + "&" +
                        "BRANCHNO=" + URLEncoder.encode(chequeInfos.getBranchNo(), "UTF-8") + "&" +
                        "CHECKNO=" + URLEncoder.encode(chequeInfos.getChequeNo(), "UTF-8") ;





//  
                URL url = new URL(link);

                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                httpURLConnection.setRequestMethod("POST");

                DataOutputStream wr = new DataOutputStream(httpURLConnection.getOutputStream());
                wr.writeBytes(data);
                wr.flush();
                wr.close();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                StringBuffer stringBuffer = new StringBuffer();

                while ((JsonResponse = bufferedReader.readLine()) != null) {
                    stringBuffer.append(JsonResponse + "\n");
                }

                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();

                Log.e("tag", "TAG_GetStor -->" + stringBuffer.toString());
                Log.e("tag", "dataSave  -->" +data);

                return stringBuffer.toString();

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e("tag", "Error closing stream", e);
                    }
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.e("editorChequeActivity/", "saved//" + s);
            if (s != null) {
                if (s.contains("\"StatusDescreption\":\"OK\"")) {
//                    linerEditing.setVisibility(View.GONE);
//                   linerBarcode.setVisibility(View.VISIBLE);
                    new SweetAlertDialog(JeroActivity.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText(JeroActivity.this.getResources().getString(R.string.pending_))
                            .setContentText(JeroActivity.this.getResources().getString(R.string.cantsendchech))
                            .setConfirmText(JeroActivity.this.getResources().getString(R.string.ok))
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @SuppressLint("WrongConstant")
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    finish();
                                    sDialog.dismissWithAnimation();
                                }
                            }).show();

                }  else if (s.contains("\"StatusDescreption\":\"Check not pindding.\"")) {
//                new SweetAlertDialog(EditerCheackActivity.this, SweetAlertDialog.ERROR_TYPE)
//                        .setTitleText("WARNING")
//                        .setContentText("Check not pinding!")
//                        .setCancelText("Close").setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
//
//
//                    @Override
//                    public void onClick(SweetAlertDialog sweetAlertDialog) {
//                        sweetAlertDialog.dismissWithAnimation();
//
//                    }
//                })
//                        .show();

//                    new EditerCheackActivity.GetAllTransaction().execute();
//                    pushCheque.setEnabled(true);


                }}else {
                Log.e("tag", "****Failed to export data");
                new SweetAlertDialog(JeroActivity.this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText(JeroActivity.this.getResources().getString(R.string.warning))
                        .setContentText(JeroActivity.this.getResources().getString(R.string.failtoSend))
                        .setCancelText(JeroActivity.this.getResources().getString(R.string.close)).setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismissWithAnimation();

                    }
                })
                        .show();

            }

        }
    }


    private class SaveGiro extends AsyncTask<String, String, String> {
        private String JsonResponse = null;
        private HttpURLConnection urlConnection = null;
        private BufferedReader reader = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            progressDialog = new ProgressDialog(context,R.style.MyTheme);
//            progressDialog.setCancelable(false);
//            progressDialog.setMessage("Loading...");
//            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//            progressDialog.setProgress(0);
//            progressDialog.show();

//            pd.getProgressHelper().setBarColor(Color.parseColor("#FDD835"));
//            pd.setTitleText(context.getResources().getString(R.string.importstor));

        }

        @Override
        protected String doInBackground(String... params) {
            try {

//
//                final List<MainSetting>mainSettings=dbHandler.getAllMainSetting();
//                String ip="";
//                if(mainSettings.size()!=0) {
//                    ip=mainSettings.get(0).getIP();
//                }
                String link = serverLink + "SaveGero";


                String data = "CHECKINFO=" + URLEncoder.encode(jsonObject.toString(), "UTF-8");
//
                URL url = new URL(link);

                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                httpURLConnection.setRequestMethod("POST");

                DataOutputStream wr = new DataOutputStream(httpURLConnection.getOutputStream());
                wr.writeBytes(data);
                wr.flush();
                wr.close();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                StringBuffer stringBuffer = new StringBuffer();

                while ((JsonResponse = bufferedReader.readLine()) != null) {
                    stringBuffer.append(JsonResponse + "\n");
                }

                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();

                Log.e("tag", "TAG_GetStor -->" + stringBuffer.toString());
                Log.e("tag", "dataSave  -->" +data);

                return stringBuffer.toString();

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e("tag", "Error closing stream", e);
                    }
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.e("editorChequeActivity/", "saved//" + s);
            if (s != null) {
                if (s.contains("\"StatusDescreption\":\"OK\"")) {
                    Log.e("tag", "****saved Success In Edit");
//                    linerEditing.setVisibility(View.GONE);
//                   linerBarcode.setVisibility(View.VISIBLE);
                    new SweetAlertDialog(JeroActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                            .setTitleText("Successful")
                            .setContentText("Save Successful")
                            .setConfirmText("Ok")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @SuppressLint("WrongConstant")
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    finish();
                                    sDialog.dismissWithAnimation();
                                }
                            }).show();
                } else  if (s != null&&s.contains("\"StatusDescreption\":\"Error in Saving Check Gero.\"")) {
                    Log.e("tag", "****Failed to export data");
                    new SweetAlertDialog(JeroActivity.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("WARNING")
                            .setContentText("Error in Saving Check Giro !")
                            .setCancelText("Close").setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            sweetAlertDialog.dismissWithAnimation();

                        }
                    })
                            .show();
                }
            } else {
                Log.e("tag", "****Failed to export data Please check internet connection");
            }
        }
    }

}
