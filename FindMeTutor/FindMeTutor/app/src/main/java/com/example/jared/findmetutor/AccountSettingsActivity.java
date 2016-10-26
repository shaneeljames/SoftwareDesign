package com.example.jared.findmetutor;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class AccountSettingsActivity extends AppCompatActivity  implements AsyncResponse{

    private String stdid, stdNum, firstName,lastName, email, number, password, balance;
    private String firstName2,lastName2, email2, number2, password2,ConfirmPassword2, stdNum2;
    private EditText inputFirstName,inputLastName, inputEmail, inputNumber, inputPassword,inputConfirmPassword, studentNumber, studentBalance;
    private TextInputLayout inputLayoutFName, inputLayoutLName, inputLayoutEmail, inputLayoutNumber, inputLayoutPass,inputLayoutCPass, inputStudentNumber ;
    ImageButton imgpp;
    private String test;

    Button addFunds, editProfile, update, cancel;

    SharedPreferences myprefs;
    Toolbar toolbar;

    updateProfile updatePro;

    public static final int RESULT_LOAD_IMAGE = 1;

    AccountSettingsActivity accountSettingsActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_settings);

        //get and assign toolbar entered information
         toolbar = (Toolbar) findViewById(R.id.toolbarAccount);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        accountSettingsActivity = this;

        //Get layouts
         /*inputLayoutFName = (TextInputLayout) findViewById(R.id.input_layout_Firstname);
         inputLayoutLName = (TextInputLayout) findViewById(R.id.input_layout_Lastname);
         inputLayoutEmail = (TextInputLayout) findViewById(R.id.input_layout_email);
         inputLayoutNumber = (TextInputLayout) findViewById(R.id.input_layout_number) ;
         inputLayoutPass = (TextInputLayout) findViewById(R.id.input_layout_password);
         inputLayoutCPass = (TextInputLayout) findViewById(R.id.input_layout_passwordConfirm);
         inputStudentNumber =(TextInputLayout) findViewById(R.id.input_layout_stdNum);

        //


        //Get Strings from those objects
         firstName = inputFirstName.getText().toString();
         lastName = inputLastName.getText().toString();
         email = inputEmail.getText().toString();
         number = inputNumber.getText().toString();
         password = inputPassword.getText().toString();
         ConfirmPassword = inputConfirmPassword.getText().toString();
         stdNum = studentNumber.getText().toString();


        inputFirstName.addTextChangedListener(new AccountSettingsActivity.MyTextWatcher(inputFirstName));
        inputLastName.addTextChangedListener(new AccountSettingsActivity.MyTextWatcher(inputLastName));
        inputEmail.addTextChangedListener(new AccountSettingsActivity.MyTextWatcher(inputEmail));
        inputPassword.addTextChangedListener(new AccountSettingsActivity.MyTextWatcher(inputPassword));
        inputConfirmPassword.addTextChangedListener(new AccountSettingsActivity.MyTextWatcher(inputConfirmPassword));

        //get button object
        btnRegister = (Button) findViewById(R.id.btn_register);


        //Submit form when login is clicked
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitForm();
            }
        });

        //if Login is clicked take you back to login screen
        TextView log = (TextView) findViewById(R.id.tvLogin);

        log.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent loginAct = new Intent(AccountSettingsActivity.this, LoginActivity.class);
                AccountSettingsActivity.this.startActivity(loginAct);
            }
        });*/

        //Get EditText objects
        inputFirstName = (EditText) findViewById(R.id.input_Firstname);
        inputLastName = (EditText) findViewById(R.id.input_Lastname);
        inputEmail = (EditText) findViewById(R.id.input_email);
        inputNumber = (EditText) findViewById(R.id.input_Number);
        inputPassword = (EditText) findViewById(R.id.input_password);
        inputConfirmPassword =(EditText) findViewById(R.id.input_passwordConfirm);
        studentNumber = (EditText) findViewById(R.id.stdNum);
        studentBalance = (EditText)findViewById(R.id.input_balance);
        imgpp = (ImageButton)findViewById(R.id.studentDp);
        addFunds = (Button)findViewById(R.id.btn_addFunds);
        editProfile = (Button)findViewById(R.id.editProfile);
        update = (Button)findViewById(R.id.update);
        cancel = (Button)findViewById(R.id.cancel);

        myprefs= this.getSharedPreferences("user", MODE_PRIVATE);


        setPrefs();

        toDefault();
        setEditableFalse();

        editProfile.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View v){
                setEditableTrue();
            }
        });


        update.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String fn = inputFirstName.getText().toString();
                String ln = inputLastName.getText().toString();
                String num = inputNumber.getText().toString();
                String pw = inputPassword.getText().toString();
                String cpw = inputConfirmPassword.getText().toString();

                if(pw.equals(cpw)){

                myprefs.edit().putString("student_password",pw).apply();
                myprefs.edit().putString("student_lname", ln).apply();
                myprefs.edit().putString("student_fname", fn).apply();
                myprefs.edit().putString("student_contact_number", num).apply();

               // Toast.makeText(getApplicationContext(), fn+ln+num+pw , Toast.LENGTH_SHORT).show();
                updatePro = new updateProfile(getParent(), stdid, fn, ln, num, pw);
                updatePro.delegate = accountSettingsActivity;
                updatePro.execute();
                }
                else{
                    Toast.makeText(getApplicationContext(), "Please enter your password correctly", Toast.LENGTH_LONG).show();
                }




            }
        });

        cancel.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View v){
                setEditableFalse();
                toDefault();
            }
        });

        Random r = new Random();
        int i1 = r.nextInt(999999-111111)+111111;
        String ran = Integer.toString(i1);

        try {
            Picasso.with(getApplicationContext()).load("http://neural.net16.net/pictures/s" + stdNum + "JPG?"+ran ).into(imgpp);
        }catch (Exception e)
        {

            Toast.makeText(getApplicationContext(), "No profile picture", Toast.LENGTH_SHORT).show();
            imgpp.setImageResource(R.drawable.ic_profile_greenp);

        }







    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_account, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        Toast.makeText(getApplicationContext(), "Selection :"+id, Toast.LENGTH_LONG);


        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if(id == R.id.action_search){
            Toast.makeText(getApplicationContext(), "Search action is selected!", Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void setEditableFalse(){

        studentNumber.setEnabled(false);


        inputFirstName.setEnabled(false);

        inputLastName.setEnabled(false);

        inputEmail.setEnabled(false);

        inputNumber.setEnabled(false);

        inputPassword.setEnabled(false);

        studentBalance.setEnabled(false);

        studentNumber.setEnabled(false);



    }

    public void setPrefs(){
        stdid=myprefs.getString("student_id",null);
        stdNum= myprefs.getString("student_student_num", null);
        firstName= myprefs.getString("student_fname", null);
        lastName= myprefs.getString("student_lname", null);
        number= myprefs.getString("student_contact_number", null);
        email= myprefs.getString("student_email", null);
        password= myprefs.getString("student_password", null);
        balance= myprefs.getString("student_current_balance", null);
    }

    public void toDefault(){
        studentNumber.setText(stdNum);
        inputFirstName.setText(firstName);
        inputLastName.setText(lastName);
        inputEmail.setText(email);
        inputNumber.setText(number);
        inputPassword.setText(password);
        studentBalance.setText(balance);
        inputConfirmPassword.setVisibility(View.GONE);
        editProfile.setVisibility(View.VISIBLE);
        update.setVisibility(View.GONE);
        cancel.setVisibility(View.GONE);
    }

    public void setEditableTrue(){

        inputFirstName.setEnabled(true);


        inputLastName.setEnabled(true);


        inputNumber.setEnabled(true);

        inputPassword.setEnabled(true);

        inputConfirmPassword.setVisibility(View.VISIBLE);
        inputConfirmPassword.setEnabled(true);

        editProfile.setVisibility(View.GONE);
        update.setVisibility(View.VISIBLE);
        cancel.setVisibility(View.VISIBLE);


    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Toast.makeText(getApplicationContext(), "req Code : "+requestCode+" Result code : "+resultCode+" data: "+data, Toast.LENGTH_SHORT).show();
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            imgpp.setImageURI(selectedImage);

            Bitmap bitmap = ((BitmapDrawable)  imgpp.getDrawable()).getBitmap() ;
            //  Bitmap b2 = Bitmap.createScaledBitmap()
            new UploadToServer(bitmap, "s" +stdNum).execute() ;
            Toast.makeText(getApplicationContext(), "Picture Updated", Toast.LENGTH_SHORT).show();
        }

    }

    public void DisplayPicture(View v)
    {
        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, RESULT_LOAD_IMAGE);

       //

       // Toast.makeText(this, "Show some text on the screen.", Toast.LENGTH_LONG).show();
    }



   private void submitForm() {

        if (!validateFName()) {
            return;
        }
        if(!validateLName()){
            return;
        }

        if (!validateEmail()) {
            return;
        }
        if(!validateNumber()){
            return;
        }

        if (!validatePassword()) {
            return;
        }

       if(!validateConfirmPassword())
       {
           return ;
       }

       //if everyhing checks out then go back to login page
        if(validateFName() && validateLName() && validateEmail() && validatePassword() && validateConfirmPassword())
        {


           // String sNum = getStdNum(inputEmail.getText().toString());
            register connect2server = new register(this, firstName2,lastName2, number2,studentNumber.getText().toString(), email2,password2 );
            connect2server.execute();

            Toast.makeText(getApplicationContext(), "Thank You for registering!", Toast.LENGTH_SHORT).show();
           // Toast.makeText(getApplicationContext(), "StudentNum : " +studentNumber., Toast.LENGTH_SHORT).show();
            Intent logInAct = new Intent(AccountSettingsActivity.this, LoginActivity.class);
            AccountSettingsActivity.this.startActivity(logInAct);
        }


    }

    public  String getStdNum(String emai)
    {
        int index = emai.indexOf("@");
        String sub = emai.substring(0, index);
        return sub;
    }




    private boolean validateFName() {
        firstName2 = inputFirstName.getText().toString().trim();
        if (firstName2.isEmpty()) {
            inputLayoutFName.setError(getString(R.string.err_msg_Fname));
            requestFocus(inputFirstName);
            return false;
        } else {
            inputLayoutFName.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateLName() {
        lastName2 = inputLastName.getText().toString().trim();
        if (lastName2.isEmpty()) {
            inputLayoutLName.setError(getString(R.string.err_msg_Lname));
            requestFocus(inputLastName);
            return false;
        } else {
            inputLayoutLName.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateEmail() {
        email2 = inputEmail.getText().toString().trim();
        if (email2.isEmpty() || !isValidEmail(email2)) {
            inputLayoutEmail.setError(getString(R.string.err_msg_email));
            requestFocus(inputEmail);
            return false;
        } else {
            inputLayoutEmail.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateNumber() {
        number2 = inputNumber.getText().toString().trim();
        if (number2.length()<10) {
            inputLayoutNumber.setError(getString(R.string.err_msg_number));
            requestFocus(inputNumber);
            return false;
        } else {
            inputLayoutNumber.setErrorEnabled(false);
        }

        return true;
    }


    private boolean validatePassword() {
        password2 = inputPassword.getText().toString().trim();
        if (password2.isEmpty()) {
            inputLayoutPass.setError(getString(R.string.err_msg_password));
            requestFocus(inputPassword);
            return false;
        } else {
            inputLayoutPass.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateConfirmPassword() {
        ConfirmPassword2 = inputConfirmPassword.getText().toString().trim();
        if (!(ConfirmPassword2.equals(password2))) {
            inputLayoutCPass.setError("Confirm password mismatch");
            requestFocus(inputConfirmPassword);
            return false;
        } else {
            inputLayoutCPass.setErrorEnabled(false);
        }

        return true;
    }


    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    @Override
    public void processFinish(String output) {

        setPrefs();
        toDefault();
        setEditableFalse();

    }

    @Override
    public void processFinish2(String out) {

    }

    @Override
    public void processFinish3(String outp) {

    }


    private class MyTextWatcher implements TextWatcher {

        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.input_Firstname:
                    validateFName();
                    break;
                case R.id.input_Lastname:
                    validateLName();
                    break;
                case R.id.input_email:
                    validateEmail();
                    break;
                case R.id.input_Number:
                    validateNumber();
                    break;
                case R.id.input_password:
                    validatePassword();
                    break;
                case R.id.input_layout_passwordConfirm:
                    validateConfirmPassword();
                    break;
            }
        }
    }



}



