package com.example.tutor;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class AccountSettingsActivity2 extends AppCompatActivity {

    ImageView imgpp;
    Button btnUpload;
    TextView txtGetfile;
    Bitmap bitmapImage ;
    Context context ;
    SharedPreferences myprefs ;
    String id ;

    private static final int RESULT_LOAD_IMAGE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_settings);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Account Settings");

        imgpp = (ImageView) findViewById((R.id.imgProfile));
        btnUpload = (Button) findViewById(R.id.btnupload);
        //txtGetfile = (TextView) findViewById(R.id.txtGetFile);
        myprefs = getSharedPreferences("user",MODE_PRIVATE ) ;
        id = myprefs.getString("tutor_student_num", null) ;
        String sName =  myprefs.getString("tutor_fname", null) ;
        String sSurname =  myprefs.getString("tutor_lname", null) ;
        String sContact =  myprefs.getString("tutor_contact_num", null) ;
        String sEmail =  myprefs.getString("tutor_email", null) ;
        String sQual =  myprefs.getString("tutor_qualifications", null) ;
        final String sPassword =  myprefs.getString("tutor_password", null) ;
        final String sTutorid =  myprefs.getString("tutor_id", null) ;




        final EditText edtname = (EditText) findViewById(R.id.edtName) ;
        final EditText edtsurname = (EditText) findViewById(R.id.edtSurname) ;
        final EditText edtStudentNumber = (EditText) findViewById(R.id.edtStudentNUmber) ;
        final EditText edtContact = (EditText) findViewById(R.id.edtContact) ;
        final EditText edtEmail = (EditText) findViewById(R.id.edtEmail) ;
        final EditText edtQual = (EditText) findViewById(R.id.edtQualifications) ;
        final EditText edtOldP = (EditText) findViewById(R.id.edtOldPassword) ;
        final EditText edtNewP = (EditText) findViewById(R.id.edtNewPassword) ;
        final EditText edtConfirmP = (EditText) findViewById(R.id.edtCNewPassword) ;

        Button btnUpdate = (Button) findViewById(R.id.btnUpdate)  ;

        edtname.setText(sName);
        edtsurname.setText(sSurname);
        edtStudentNumber.setText(id);
        edtContact.setText(sContact);
        edtEmail.setText(sEmail);
        edtQual.setText(sQual);

        final TextView txtName = (TextView) findViewById(R.id.textView1) ;
        final TextView txtsurname = (TextView) findViewById(R.id.textView2) ;
        final TextView txtStudentNo = (TextView) findViewById(R.id.textView) ;
        final TextView txtContact = (TextView) findViewById(R.id.textView4) ;
        final TextView txtEmail = (TextView) findViewById(R.id.textView3) ;
        final TextView txtQual = (TextView) findViewById(R.id.textView5) ;
        final TextView txtPassword = (TextView) findViewById(R.id.textView6) ;




        try {
            Picasso.with(getApplicationContext()).load("http://neural.net16.net/pictures/t" + id.toString() + "JPG" ).into(imgpp);
        }catch (Exception e)
        {

        }




        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, RESULT_LOAD_IMAGE);

                Toast.makeText(getApplicationContext(), "Picture Updated", Toast.LENGTH_SHORT).show();


            }
        });


        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Toast.makeText(getApplicationContext(), "Password " + sPassword , Toast.LENGTH_SHORT).show();

                txtName.setTextColor(Color.BLACK);
                txtsurname.setTextColor(Color.BLACK);
                txtStudentNo.setTextColor(Color.BLACK);
                txtEmail.setTextColor(Color.BLACK);
                txtQual.setTextColor(Color.BLACK);
                txtContact.setTextColor(Color.BLACK);



                int Count = 0 ;

                 if(edtname.getText().length() ==0)
                {
                    Count++ ;
                    txtName.setTextColor(Color.RED);
                }

                if(edtsurname.getText().length() ==0)
                {
                    Count++ ;
                    txtsurname.setTextColor(Color.RED);
                }

                if(edtStudentNumber.getText().length() == 0)
                {
                    Count++ ;
                    txtStudentNo.setTextColor(Color.RED);
                }

                if(edtContact.getText().length() == 0)
                {
                    Count++ ;
                    txtContact.setTextColor(Color.RED);
                }

                if(edtEmail.getText().length() == 0)
                {
                    Count++ ;
                    txtEmail.setTextColor(Color.RED);
                }

                if(edtQual.getText().length() == 0)
                {
                    Count++ ;
                    txtQual.setTextColor(Color.RED);
                }


                if(Count > 0){

                    Toast.makeText(getApplicationContext(), "Profile attributes cannot be empty", Toast.LENGTH_SHORT).show();
                }
                else if(edtNewP.getText().length() == 0 && edtOldP.getText().length() == 0 && edtConfirmP.getText().length() == 0)
                {
                    //Update here
                    tutor_updateAccount connect2server = new tutor_updateAccount(AccountSettingsActivity2.this,edtEmail.getText().toString(),sPassword.toString(),sTutorid.toString(), edtContact.getText().toString(),edtStudentNumber.getText().toString(),edtQual.getText().toString(),edtname.getText().toString(),edtsurname.getText().toString()) ;
                    connect2server.execute() ;
                    Toast.makeText(getApplicationContext(), "Update Successful", Toast.LENGTH_SHORT).show();
                }
                else if(edtOldP.getText().toString().equals(sPassword.toString() ))
                {
                    Toast.makeText(getApplicationContext(), "Old Password Correct", Toast.LENGTH_SHORT).show();


                    if(edtNewP.getText().length() >=7) {

                        if (edtNewP.getText().toString().equals(edtConfirmP.getText().toString())) {

                            //Update here
                            tutor_updateAccount connect2server = new tutor_updateAccount(AccountSettingsActivity2.this,edtEmail.getText().toString(),edtNewP.getText().toString(),sTutorid.toString(), edtContact.getText().toString(),edtStudentNumber.getText().toString(),edtQual.getText().toString(),edtname.getText().toString(),edtsurname.getText().toString()) ;
                            connect2server.execute() ;
                            Toast.makeText(getApplicationContext(), "Update Successful", Toast.LENGTH_SHORT).show();

                            Toast.makeText(getApplicationContext(), "New passwords Match", Toast.LENGTH_SHORT).show();
                        } else
                            Toast.makeText(getApplicationContext(), "New Password mismatch", Toast.LENGTH_SHORT).show();
                    }
                    else
                        Toast.makeText(getApplicationContext(), "Password minimum length is 7 characters", Toast.LENGTH_SHORT).show();

                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Old Password incorrect", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            imgpp.setImageURI(selectedImage);

            Bitmap bitmap = ((BitmapDrawable)  imgpp.getDrawable()).getBitmap() ;
          //  Bitmap b2 = Bitmap.createScaledBitmap()
            new UploadToServer(bitmap, "t" +id.toString()).execute() ;
        }

    }





}
