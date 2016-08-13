package com.example.jared.findmetutor;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;


public class  RegisterActivity extends AppCompatActivity {

    private String firstName,lastName, email, number, password,ConfirmPassword, securityQ, answer;
    private String firstName2,lastName2, email2, number2, password2,ConfirmPassword2, securityQ2, answer2;
    private EditText inputFirstName,inputLastName, inputEmail, inputNumber, inputPassword,inputConfirmPassword, inputSecurityQ, inputAnswer;
    private TextInputLayout inputLayoutFName, inputLayoutLName, inputLayoutEmail, inputLayoutNumber, inputLayoutPass,inputLayoutCPass, inputLayoutSecurityQ, inputLayoutAnswer;
    private Button btnRegister;


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
         inputLayoutSecurityQ = (TextInputLayout) findViewById(R.id.input_layout_SecurityQ);
         inputLayoutAnswer = (TextInputLayout) findViewById(R.id.input_layout_answer);

        //Get EditText objects
         inputFirstName = (EditText) findViewById(R.id.input_Firstname);
         inputLastName = (EditText) findViewById(R.id.input_Lastname);
         inputEmail = (EditText) findViewById(R.id.input_email);
         inputNumber = (EditText) findViewById(R.id.input_Number);
         inputPassword = (EditText) findViewById(R.id.input_password);
        inputConfirmPassword =(EditText) findViewById(R.id.input_passwordConfirm);
         inputSecurityQ = (EditText) findViewById(R.id.input_securityQ);
         inputAnswer = (EditText) findViewById(R.id.input_Answer);

        //Get Strings from those objects
         firstName = inputFirstName.getText().toString();
         lastName = inputLastName.getText().toString();
         email = inputEmail.getText().toString();
         number = inputNumber.getText().toString();
         password = inputPassword.getText().toString();
         ConfirmPassword = inputConfirmPassword.getText().toString();
         securityQ = inputSecurityQ.getText().toString();
         answer = inputAnswer.getText().toString();

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

            register connect2server = new register(this, firstName2,lastName2, number2, email2,password2, inputSecurityQ.getText().toString().trim(),inputAnswer.getText().toString().trim(), "Student");
            connect2server.execute();

            Toast.makeText(getApplicationContext(), "Thank You for registering!", Toast.LENGTH_SHORT).show();
            Intent logInAct = new Intent(RegisterActivity.this, LoginActivity.class);
            RegisterActivity.this.startActivity(logInAct);
        }


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



