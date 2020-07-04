package com.falconssoft.centerbank;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.falconssoft.centerbank.Models.LoginINFO;
import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.falconssoft.centerbank.LogInActivity.LANGUAGE_FLAG;

public class SingUpActivity extends AppCompatActivity {
    private TextView date_text;
    private Date currentTimeAndDate;
    private SimpleDateFormat df;
    private Calendar myCalendar;
    private EditText natonalNo, phoneNo, address, email, password, firstName, secondName, thirdName, fourthName;
    private String language, today, selectedAccount = "Account Type", selectedGender = "Gender";
    private Animation animation;
    private LinearLayout linearLayout, coordinatorLayout, accountTypeLinear, genderLinear;
    private Button save;
    private Spinner spinnerAccountType, spinnerGender;
    private List<String> accountTypeList = new ArrayList<>();
    private List<String> genderList = new ArrayList<>();
    private ArrayAdapter arrayAdapter, genderArrayAdapter;
    private DatabaseHandler databaseHandler;
    private ProgressDialog progressDialog;
    private Snackbar snackbar;
    public static final String PAGE_NAME = "PAGE_NAME";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sing_up_layout);

//        language = getIntent().getStringExtra(LANGUAGE_FLAG);
        init();

        SharedPreferences prefs = getSharedPreferences(LANGUAGE_FLAG, MODE_PRIVATE);
        language = prefs.getString("language", "en");

        currentTimeAndDate = Calendar.getInstance().getTime();
        df = new SimpleDateFormat("dd/MM/yyyy");
        today = df.format(currentTimeAndDate);
        date_text.setText(convertToEnglish(today));
        checkLanguage();

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveMethod();
            }
        });

        Log.e("editing,signup ", language);
    }

    private void saveMethod() {
        String localNationalID = natonalNo.getText().toString();
        String localFirstName = firstName.getText().toString();
        String localSecondName = secondName.getText().toString();
        String localThirdName = thirdName.getText().toString();
        String localFourthName = fourthName.getText().toString();
        String localPhone = phoneNo.getText().toString();
        String localAddress = address.getText().toString();
        String localEmail = email.getText().toString();
        String localPassword = password.getText().toString();
        String localBirthDate = date_text.getText().toString();

        if (!selectedAccount.equals("Account Type"))
            if (!selectedGender.equals("Gender"))
                if (!TextUtils.isEmpty(localNationalID))
                    if (localNationalID.length() == 10)
                        if (!TextUtils.isEmpty(localFirstName))
                            if (!TextUtils.isEmpty(localSecondName))
                                if (!TextUtils.isEmpty(localThirdName))
                                    if (!TextUtils.isEmpty(localFourthName))
                                        if (!TextUtils.isEmpty("" + localPhone))
                                            if (localPhone.length() == 10)
                                                if (!TextUtils.isEmpty(localAddress))
                                                    if (!TextUtils.isEmpty(localEmail))
                                                        if (Patterns.EMAIL_ADDRESS.matcher(localEmail).matches())
                                                            if (!TextUtils.isEmpty(localPassword))
                                                                if (isValidPassword(localPassword)) {

                                                                    LoginINFO loginINFO = new LoginINFO();
                                                                    loginINFO.setNationalID(localNationalID);
                                                                    loginINFO.setFirstName(localFirstName);
                                                                    loginINFO.setSecondName(localSecondName);
                                                                    loginINFO.setThirdName(localThirdName);
                                                                    loginINFO.setFourthName(localFourthName);
                                                                    loginINFO.setUsername(localPhone);
                                                                    loginINFO.setAddress(localAddress);
                                                                    loginINFO.setEmail(localEmail);
                                                                    loginINFO.setPassword(localPassword);
                                                                    loginINFO.setBirthDate(localBirthDate);
                                                                    if (selectedGender.equals("Male"))
                                                                        loginINFO.setGender("0");
                                                                    else
                                                                        loginINFO.setGender("1");

                                                                    showDialog();
                                                                    new Presenter(SingUpActivity.this).saveSignUpInfo(this, loginINFO);

                                                                } else
                                                                    password.setError("Password must contains at least 6 digits, capital letter, number and special character");
                                                            else
                                                                password.setError("Required!");
                                                        else
                                                            email.setError("Not valid email!");
                                                    else
                                                        email.setError("Required!");
                                                else
                                                    address.setError("Required!");
                                            else
                                                phoneNo.setError("Phone number is less than 10 digits!");
                                        else
                                            phoneNo.setError("Required!");
                                    else
                                        fourthName.setError("Required!");
                                else
                                    thirdName.setError("Required!");
                            else
                                secondName.setError("Required!");
                        else
                            firstName.setError("Required!");
                    else
                        natonalNo.setError("National number is less than 10 digits!");
                else
                    natonalNo.setError("Required!");
            else
                showSnackbar("Please choose gender!", false);
        else
            showSnackbar("Please choose account type!", false);

    }

    public static boolean isValidPassword(String password) {
        if (password.length() > 5) {

            Pattern pattern;
            Matcher matcher;
            final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$";
            pattern = Pattern.compile(PASSWORD_PATTERN);
            matcher = pattern.matcher(password);

            return matcher.matches();
        } else
            return false;
    }

    private void init() {

        databaseHandler = new DatabaseHandler(this);
        coordinatorLayout = findViewById(R.id.signup_coordinatorLayout);
        save = findViewById(R.id.signUp_save);
        firstName = findViewById(R.id.signUp_first_name);
        secondName = findViewById(R.id.signUp_second_name);
        thirdName = findViewById(R.id.signUp_third_name);
        fourthName = findViewById(R.id.signUp_fourth_name);
        natonalNo = findViewById(R.id.signUp_IdNo);
        phoneNo = findViewById(R.id.signUp_phone);
        address = findViewById(R.id.signUp_address);
        email = findViewById(R.id.signUp_email);
        password = findViewById(R.id.signUp_password);
        linearLayout = findViewById(R.id.signup_nameLinear);
        date_text = (TextView) findViewById(R.id.Date);
        linearLayout = findViewById(R.id.signup_nameLinear);
        date_text = (TextView) findViewById(R.id.Date);
        spinnerAccountType = findViewById(R.id.signUp_accountType);
        spinnerGender = findViewById(R.id.signUp_gender);
        accountTypeLinear = findViewById(R.id.signUp_accountType_linear);
        genderLinear = findViewById(R.id.signUp_gender_linear);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Waiting...");

        accountTypeList.clear();
        accountTypeList.add("Account Type");
        accountTypeList.add("Individual");
        accountTypeList.add("Corporate");
        accountTypeList.add("Join Account");
        arrayAdapter = new ArrayAdapter(this, R.layout.spinner_layout, accountTypeList);
        arrayAdapter.setDropDownViewResource(R.layout.spinner_drop_down_layout);
        spinnerAccountType.setAdapter(arrayAdapter);
        spinnerAccountType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                selectedAccount = adapterView.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        genderList.clear();
        genderList.add("Gender");
        genderList.add("Male");
        genderList.add("Female");
        genderArrayAdapter = new ArrayAdapter(this, R.layout.spinner_layout, genderList);
        genderArrayAdapter.setDropDownViewResource(R.layout.spinner_drop_down_layout);
        spinnerGender.setAdapter(genderArrayAdapter);
        spinnerGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                selectedGender = adapterView.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        myCalendar = Calendar.getInstance();
        date_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(SingUpActivity.this, openDatePickerDialog(date_text), myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    void checkLanguage() {
        if (language.equals("ar")) {
            natonalNo.setCompoundDrawablesWithIntrinsicBounds(null, null
                    , ContextCompat.getDrawable(SingUpActivity.this, R.drawable.ic_person_black_24dp), null);
            phoneNo.setCompoundDrawablesWithIntrinsicBounds(null, null
                    , ContextCompat.getDrawable(SingUpActivity.this, R.drawable.ic_local_phone_black_24dp), null);
            address.setCompoundDrawablesWithIntrinsicBounds(null, null
                    , ContextCompat.getDrawable(SingUpActivity.this, R.drawable.ic_location_on_black_24dp), null);
            email.setCompoundDrawablesWithIntrinsicBounds(null, null
                    , ContextCompat.getDrawable(SingUpActivity.this, R.drawable.ic_email_black_24dp), null);
            password.setCompoundDrawablesWithIntrinsicBounds(null, null
                    , ContextCompat.getDrawable(SingUpActivity.this, R.drawable.ic_https_black_24dp), null);
            date_text.setCompoundDrawablesWithIntrinsicBounds(null, null
                    , ContextCompat.getDrawable(SingUpActivity.this, R.drawable.ic_date_range_black_24dp), null);
            date_text.setGravity(Gravity.RIGHT);
            linearLayout.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

        } else {
            natonalNo.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(SingUpActivity.this, R.drawable.ic_person_black_24dp), null
                    , null, null);
            phoneNo.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(SingUpActivity.this, R.drawable.ic_local_phone_black_24dp), null
                    , null, null);
            address.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(SingUpActivity.this, R.drawable.ic_location_on_black_24dp), null
                    , null, null);
            email.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(SingUpActivity.this, R.drawable.ic_email_black_24dp), null
                    , null, null);
            password.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(SingUpActivity.this, R.drawable.ic_https_black_24dp), null
                    , null, null);
            date_text.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(SingUpActivity.this, R.drawable.ic_date_range_black_24dp), null
                    , null, null);
            date_text.setGravity(Gravity.LEFT);
            linearLayout.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);

        }

        animation = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.move_to_right);
        accountTypeLinear.startAnimation(animation);

        animation = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.move_to_right);
        genderLinear.startAnimation(animation);

        animation = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.move_to_right);
        natonalNo.startAnimation(animation);

        animation = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.move_to_right);
        linearLayout.startAnimation(animation);

        animation = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.move_to_right);
        date_text.startAnimation(animation);

        animation = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.move_to_right);
        phoneNo.startAnimation(animation);

        animation = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.move_to_right);
        address.startAnimation(animation);

        animation = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.move_to_right);
        email.startAnimation(animation);

        animation = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.move_to_right);
        password.startAnimation(animation);
    }

    public void goToLoginPage(String message) {
        hideDialog();
        if (message.contains("\"StatusCode\":0,\"StatusDescreption\":\"OK\"")) {
            Intent intent = new Intent(SingUpActivity.this, LogInActivity.class);
            intent.putExtra(PAGE_NAME, 10);
            startActivity(intent);
            finish();
        } else if (message.contains("{\"StatusCode\" : 9,\"StatusDescreption\":\"Error in saving User.\" }"))
            showSnackbar("PLease check sent Info first!", false);
        else //(message.contains("{\"StatusCode\" : 4,\"StatusDescreption\":\"Error in Saving Check Temp.\" }") or error
            showSnackbar("Information Not Saved !", false);


    }

    void showSnackbar(String text, boolean showImage) {

        if (showImage) {
            snackbar = Snackbar.make(coordinatorLayout, Html.fromHtml("<font color=\"#3167F0\">" + text + "</font>"), Snackbar.LENGTH_SHORT);//Updated Successfully
            View snackbarLayout = snackbar.getView();
            TextView textViewSnackbar = (TextView) snackbarLayout.findViewById(R.id.snackbar_text);//android.support.design.R.id.snackbar_text
            textViewSnackbar.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_check_24dp, 0, 0, 0);
        } else {
            snackbar = Snackbar.make(coordinatorLayout, Html.fromHtml("<font color=\"#D11616\">" + text + "</font>"), Snackbar.LENGTH_SHORT);//Updated Successfully
            View snackbarLayout = snackbar.getView();
            TextView textViewSnackbar = (TextView) snackbarLayout.findViewById(R.id.snackbar_text);//android.support.design.R.id.snackbar_text
            textViewSnackbar.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_error, 0, 0, 0);
        }
        snackbar.show();
    }

    void showDialog() {
        progressDialog.show();
    }

    public void hideDialog() {
        progressDialog.dismiss();
    }

    public String convertToEnglish(String value) {
        String newValue = (((((((((((value + "").replaceAll("١", "1")).replaceAll("٢", "2")).replaceAll("٣", "3")).replaceAll("٤", "4")).replaceAll("٥", "5")).replaceAll("٦", "6")).replaceAll("٧", "7")).replaceAll("٨", "8")).replaceAll("٩", "9")).replaceAll("٠", "0").replaceAll("٫", "."));
        return newValue;
    }

    public DatePickerDialog.OnDateSetListener openDatePickerDialog(final TextView editText) {
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel(editText);
            }

        };
        return date;
    }

    private void updateLabel(TextView editText) {
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        editText.setText(sdf.format(myCalendar.getTime()));

    }
}
