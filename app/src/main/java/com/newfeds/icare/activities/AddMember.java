package com.newfeds.icare.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.signature.StringSignature;
import com.newfeds.icare.R;
import com.newfeds.icare.constants.Const;
import com.newfeds.icare.helper.Crypto;
import com.newfeds.icare.helper.DBhelper;
import com.newfeds.icare.helper.ImageHelper;
import com.newfeds.icare.helper.L;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

public class AddMember extends AppCompatActivity {

    Button buttonAddMemberChooseFromGallary;
    ImageView imageViewAddMember;

    final int REQUEST_CAMERA = 0, SELECT_FILE = 1;

    Uri outputFileUri = null;
    String selectedImagePath = null;

    private EditText editTextAddMemberName;
    private EditText editTextAddMemberAge;
    private EditText editTextAddMemberHeight;
    private EditText editTextAddMemberWeight;
    private EditText editTextAddMemberPhone;
    private Spinner spinnerAddMemberGender;
    private Spinner spinnerAddMemberRelationship;

    private Button buttonAddMemberFinal;
    DBhelper dBhelper = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_member);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        dBhelper = new DBhelper(this);
        imageViewAddMember  = (ImageView) findViewById(R.id.imageViewAddMember);

        editTextAddMemberName = (EditText) findViewById(R.id.editTextAddMemberName);
        editTextAddMemberAge = (EditText) findViewById(R.id.editTextAddMemberAge);
        editTextAddMemberHeight = (EditText) findViewById(R.id.editTextAddMemberHeight);
        editTextAddMemberWeight = (EditText) findViewById(R.id.editTextAddMemberWeight);
        editTextAddMemberPhone = (EditText) findViewById(R.id.editTextAddMemberPhone);

        spinnerAddMemberGender = (Spinner) findViewById(R.id.spinnerAddMemberGender);
        spinnerAddMemberRelationship = (Spinner) findViewById(R.id.spinnerAddMemberRelationship);

        buttonAddMemberChooseFromGallary = (Button) findViewById(R.id.buttonAddMemberChooseFromGallary);
        buttonAddMemberChooseFromGallary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });

        buttonAddMemberFinal = (Button) findViewById(R.id.buttonAddMemberFinal);
        buttonAddMemberFinal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addMember();
            }
        });
    }



    private void addMember(){
        L.log("Add Member Pressed");
        boolean allOK = true;
        if(editTextAddMemberName.getText().length()<6){
            editTextAddMemberName.setError("Must be at least 6 chars");
            allOK = false;
        }
        if(editTextAddMemberAge.getText().length()==0){
            editTextAddMemberAge.setError("Set your age");
            allOK = false;
        }
        if(editTextAddMemberHeight.getText().length()==0){
            editTextAddMemberHeight.setError("Set your height");
            allOK = false;
        }
        if(editTextAddMemberWeight.getText().length()==0){
            editTextAddMemberWeight.setError("Set your weight");
            allOK = false;
        }
        if(editTextAddMemberPhone.getText().length()<9){
            editTextAddMemberPhone.setError("Mustbe atleast 9 chars");
            allOK = false;
        }
        if(allOK){
            if(dBhelper!=null){
                String name = editTextAddMemberName.getText().toString();
                String age = editTextAddMemberAge.getText().toString();
                String height = editTextAddMemberHeight.getText().toString();
                String weight = editTextAddMemberWeight.getText().toString();
                String phone = editTextAddMemberPhone.getText().toString();
                String gender = spinnerAddMemberGender.getSelectedItem().toString();
                String relationship = spinnerAddMemberRelationship.getSelectedItem().toString();

                dBhelper.inputMember(name,ImageHelper.saveImage(this,outputFileUri),age,height,weight,gender,phone,relationship);
                Toast.makeText(getBaseContext(),"Data saved", Toast.LENGTH_SHORT).show();
                L.log("Member saved");

                onBackPressed();
            }else{
                Toast.makeText(getApplicationContext(), "Error connecting to database", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(getBaseContext(), "Check your inputs", Toast.LENGTH_SHORT).show();
        }
    }

    private void selectImage(){
        final CharSequence[] items = { "Take Photo", "Choose from Library",
                "Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Take Photo")) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    if(intent.resolveActivity(getPackageManager())!=null){
                        File photofile = new File(Environment.getExternalStorageDirectory(), "temp.jpg");
                        outputFileUri = Uri.fromFile(photofile);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
                        startActivityForResult(intent, REQUEST_CAMERA);
                    }
                } else if (items[item].equals("Choose from Library")) {
                    Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
                    getIntent.setType("image/*");

                    Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    pickIntent.setType("image/*");

                    Intent chooserIntent = Intent.createChooser(getIntent, "Select Image");
                    chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{pickIntent});
                    startActivityForResult(chooserIntent, SELECT_FILE);
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data);
            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
        }
    }

    private void onCaptureImageResult(Intent data) {
        String uri = outputFileUri.toString();
        L.log("Uri: "+uri);

        if(outputFileUri!=null){
            L.log(new File(outputFileUri.getPath()).getAbsolutePath());
            selectedImagePath = new File(outputFileUri.getPath()).getAbsolutePath();
            L.log("Selected: " + selectedImagePath);


            Glide.with(this).load(new File(selectedImagePath))
                    .skipMemoryCache(true)
                    .signature(new StringSignature(UUID.randomUUID().toString()))
                    .into(imageViewAddMember);
        }
    }

    private void onSelectFromGalleryResult(Intent data) {
        outputFileUri = data.getData();
        String[] projection = { MediaStore.MediaColumns.DATA };
        Cursor cursor = managedQuery(outputFileUri, projection, null, null,
                null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        cursor.moveToFirst();

        String selectedImagePath = cursor.getString(column_index);
        L.log("SelectedImagePath: " + selectedImagePath);


        Glide.with(this).load(new File(selectedImagePath))
                .skipMemoryCache(true)
                .signature(new StringSignature(UUID.randomUUID().toString()))
                .into(imageViewAddMember);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
