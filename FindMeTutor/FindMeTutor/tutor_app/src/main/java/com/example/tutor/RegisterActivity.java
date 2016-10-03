package com.example.tutor;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class  RegisterActivity extends AppCompatActivity {

    private String firstName,lastName, email, number, password,ConfirmPassword;
    private String firstName2,lastName2, email2, number2, password2,ConfirmPassword2, stdNum2;
    private EditText inputFirstName,inputLastName, inputEmail, inputNumber, inputPassword,inputConfirmPassword, studentNumber;
    private TextInputLayout inputLayoutFName, inputLayoutLName, inputLayoutEmail, inputLayoutNumber, inputLayoutPass,inputLayoutCPass, inputStudentNumber ;
    private Button btnRegister;
    private String test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //get and assign toolbar entered information
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Get layouts
         inputLayoutFName = (TextInputLayout) findViewById(R.id.input_layout_Firstname);
         inputLayoutLName = (TextInputLayout) findViewById(R.id.input_layout_Lastname);
         inputLayoutEmail = (TextInputLayout) findViewById(R.id.input_layout_email);
         inputLayoutNumber = (TextInputLayout) findViewById(R.id.input_layout_number) ;
         inputLayoutPass = (TextInputLayout) findViewById(R.id.input_layout_password);
         inputLayoutCPass = (TextInputLayout) findViewById(R.id.input_layout_passwordConfirm);
         inputStudentNumber =(TextInputLayout) findViewById(R.id.input_layout_stdNum);

        //Get EditText objects
         inputFirstName = (EditText) findViewById(R.id.input_Firstname);
         inputLastName = (EditText) findViewById(R.id.input_Lastname);
         inputEmail = (EditText) findViewById(R.id.input_email);
         inputNumber = (EditText) findViewById(R.id.input_Number);
         inputPassword = (EditText) findViewById(R.id.input_password);
         inputConfirmPassword =(EditText) findViewById(R.id.input_passwordConfirm);
         studentNumber = (EditText) findViewById(R.id.stdNum);


        //Get Strings from those objects
         firstName = inputFirstName.getText().toString();
         lastName = inputLastName.getText().toString();
         email = inputEmail.getText().toString();
         number = inputNumber.getText().toString();
         password = inputPassword.getText().toString();
         ConfirmPassword = inputConfirmPassword.getText().toString();
         stdNum2 = studentNumber.getText().toString();


        inputFirstName.addTextChangedListener(new RegisterActivity.MyTextWatcher(inputFirstName));
        inputLastName.addTextChangedListener(new RegisterActivity.MyTextWatcher(inputLastName));
        inputEmail.addTextChangedListener(new RegisterActivity.MyTextWatcher(inputEmail));
        inputPassword.addTextChangedListener(new RegisterActivity.MyTextWatcher(inputPassword));
        inputConfirmPassword.addTextChangedListener(new RegisterActivity.MyTextWatcher(inputConfirmPassword));

        //get button object
        btnRegister = (Button) findViewById(R.id.btn_register);


        //Submit form when login is clicked
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              //  Toast.makeText(getApplicationContext(),"student NUmber "+ studentNumber.getText().toString(), Toast.LENGTH_SHORT).show();
                submitForm();
            }
        });

        //if Login is clicked take you back to login screen
        TextView log = (TextView) findViewById(R.id.tvLogin);

        log.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent loginAct = new Intent(RegisterActivity.this, LoginActivity.class);
                RegisterActivity.this.startActivity(loginAct);
            }
        });




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

            String witsemail = email2.substring(email2.indexOf("@") , email2.length()) ;
          //  Toast.makeText(getApplicationContext(),"Wits email  "+ witsemail toString(), Toast.LENGTH_SHORT).show();


            if(witsemail.toString().equals("@wits.ac.za") || witsemail.toString().equals("@students.wits.ac.za")) {
               // register connect2server = new register(this, firstName2, lastName2, number2, studentNumber.getText().toString(), email2, password2);
               // connect2server.execute();

                tutor_checkRegister connect = new tutor_checkRegister(this,email2,firstName2,lastName2,number2,studentNumber.getText().toString(),password2) ;
                connect.execute() ;



             /*   if(id.equals("0"))
                {
                    Toast.makeText(getApplicationContext(),"Registration Successful!", Toast.LENGTH_SHORT).show();
                    register connect2server = new register(this, firstName2, lastName2, number2, studentNumber.getText().toString(), email2, password2);
                    connect2server.execute();

                    String fromEmail = "FindmetutorSD@gmail.com";
                    String fromPassword = "findmetutors";
                    String toEmails = "nivekranjith95@gmail.com";
                    String adminEmail = "admin@gmail.com";
                    String emailSubject = "Sent from FindMeTutor";
                    String adminSubject = "App Registration Mail";
                    String emailBody =
                            "Dear "+ firstName2 + " " +lastName2
                                    +"<br><br> You have successfully registered on FindMeTutor as a tutor!<br> " +
                                    "Simply use your email: " + email2.toString() + "and your Password: " + password2.toString()
                                    +"to log in" ;

                    String adminBody = "Your message";
                    new SendMailTask(getApplicationContext()).execute(fromEmail,
                            fromPassword, toEmails, emailSubject, emailBody);


                    Intent logInAct = new Intent(RegisterActivity.this, LoginActivity.class);
                    RegisterActivity.this.startActivity(logInAct);
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Registration Unsuccessful, Account already exist", Toast.LENGTH_SHORT).show();
                    inputEmail.setText("");
                    this.finish();
                    Intent logInAct = new Intent(RegisterActivity.this, RegisterActivity.class);
                    RegisterActivity.this.startActivity(logInAct);
                }*/


               // Toast.makeText(getApplicationContext(),"Check Register 3  "+ id , Toast.LENGTH_SHORT).show();

               // Intent logInAct = new Intent(RegisterActivity.this, LoginActivity.class);
              //  RegisterActivity.this.startActivity(logInAct);
            }
            else
            inputLayoutEmail.setError("Please enter a valid wits email");



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
        if (email2.isEmpty() || !isValidEmail(email2)  ) {
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
        if (password2.length()<7) {
            inputLayoutPass.setError(getString(R.string.err_msg_password) + " (Min 7 characters)");
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



