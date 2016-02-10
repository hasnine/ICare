package com.newfeds.icare.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
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
import com.newfeds.icare.helper.DBhelper;
import com.newfeds.icare.helper.ImageHelper;
import com.newfeds.icare.helper.L;
import com.newfeds.icare.model.Profile;

import java.io.File;
import java.util.UUID;

public class EditMyProfile extends AppCompatActivity {

    private EditText editTextEditMyProfileName;
    private EditText editTextEditMyProfileAge;
    private EditText editTextEditMyProfileHeight;
    private EditText editTextEditMyProfileWeight;
    private EditText editTextEditMyProfilePhone;
    private Spinner spinnerEditMyProfileGender;
    ImageView imageViewEditMyProfile;

    Button buttonUpdateMyProfile;

    Button buttonEditMyProfileChooseFromGallary;
    DBhelper dBhelper = null;
    final int REQUEST_CAMERA = 0, SELECT_FILE = 1;

    Uri outputFileUri = null;
    String selectedImagePath = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_my_profile);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        dBhelper = new DBhelper(this);

        Profile profile = dBhelper.getMyProfileMain();

        if(profile.PHOTO!=null && !profile.PHOTO.equals("")){
            outputFileUri = Uri.fromFile(new File(profile.PHOTO));
        }

        imageViewEditMyProfile  = (ImageView) findViewById(R.id.imageViewEditMyProfile);

        buttonUpdateMyProfile = (Button) findViewById(R.id.buttonEditMyProfileFinal);

        editTextEditMyProfileName = (EditText) findViewById(R.id.editTextEditMyProfileName);
        editTextEditMyProfileAge = (EditText) findViewById(R.id.editTextEditMyProfileAge);
        editTextEditMyProfileHeight = (EditText) findViewById(R.id.editTextEditMyProfileHeight);
        editTextEditMyProfileWeight = (EditText) findViewById(R.id.editTextEditMyProfileWeight);
        editTextEditMyProfilePhone = (EditText) findViewById(R.id.editTextEditMyProfilePhone);

        spinnerEditMyProfileGender = (Spinner) findViewById(R.id.spinnerEditMyProfileGender);


        editTextEditMyProfileName.setText(profile.NAME);
        editTextEditMyProfileAge.setText(profile.AGE);
        editTextEditMyProfileHeight.setText(profile.HEIGHT);
        editTextEditMyProfileWeight.setText(profile.WEIGHT);
        editTextEditMyProfilePhone.setText(profile.PHONE);


        buttonEditMyProfileChooseFromGallary = (Button) findViewById(R.id.buttonEditMyProfileChooseFromGallary);
        buttonEditMyProfileChooseFromGallary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });

        buttonUpdateMyProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addMember();
            }
        });

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
        L.log("Uri: " + uri);

        if(outputFileUri!=null){
            L.log(new File(outputFileUri.getPath()).getAbsolutePath());
            selectedImagePath = new File(outputFileUri.getPath()).getAbsolutePath();
            L.log("Selected: " + selectedImagePath);

            Glide.with(this).load(new File(selectedImagePath))
                    .skipMemoryCache(true)
                    .signature(new StringSignature(UUID.randomUUID().toString()))
                    .into(imageViewEditMyProfile);
        }
    }
    private void addMember(){
        L.log("Add Member Pressed");
        boolean allOK = true;
        if(editTextEditMyProfileName.getText().length()<6){
            editTextEditMyProfileName.setError("Must be at least 6 chars");
            allOK = false;
        }
        if(editTextEditMyProfileAge.getText().length()==0){
            editTextEditMyProfileAge.setError("Set your age");
            allOK = false;
        }
        if(editTextEditMyProfileHeight.getText().length()==0){
            editTextEditMyProfileHeight.setError("Set your height");
            allOK = false;
        }
        if(editTextEditMyProfileWeight.getText().length()==0){
            editTextEditMyProfileWeight.setError("Set your weight");
            allOK = false;
        }
        if(editTextEditMyProfilePhone.getText().length()<9){
            editTextEditMyProfilePhone.setError("Mustbe atleast 9 chars");
            allOK = false;
        }
        if(allOK){
            if(dBhelper!=null){
                String name = editTextEditMyProfileName.getText().toString();
                String age = editTextEditMyProfileAge.getText().toString();
                String height = editTextEditMyProfileHeight.getText().toString();
                String weight = editTextEditMyProfileWeight.getText().toString();
                String phone = editTextEditMyProfilePhone.getText().toString();
                String gender = spinnerEditMyProfileGender.getSelectedItem().toString();

                dBhelper.updateMyProfile(name,age,ImageHelper.saveImage(this,outputFileUri),height,weight,phone,gender);
                Toast.makeText(getBaseContext(), "Data saved", Toast.LENGTH_SHORT).show();
                L.log("Member saved");
                onBackPressed();
            }else{
                Toast.makeText(getApplicationContext(), "Error connecting to database", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(getBaseContext(), "Check your inputs", Toast.LENGTH_SHORT).show();
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
                .into(imageViewEditMyProfile);
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
