package com.k2infosoft.proprofile;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.k2infosoft.placesearch.PlaceSearchDialog;
import com.k2infosoft.proprofile.managers.SharedPrefs;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ProfileActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, View.OnClickListener {
    private static final String TAG = "ProfileActivity";
    @BindView(R.id.img_profile_pic)
    de.hdodenhof.circleimageview.CircleImageView _profile_pic;
    @BindView(R.id.et_firstname)
    com.rey.material.widget.EditText _name;
    @BindView(R.id.et_dob)
    com.rey.material.widget.EditText _dob;
    @BindView(R.id.et_email)
    com.rey.material.widget.EditText _email;
    @BindView(R.id.et_about)
    com.rey.material.widget.EditText _about;
    @BindView(R.id.et_design)
    com.rey.material.widget.EditText _design;
    @BindView(R.id.et_mobile)
    com.rey.material.widget.EditText _mobile;
    @BindView(R.id.et_website)
    com.rey.material.widget.EditText _website;
    @BindView(R.id.et_address)
    com.rey.material.widget.EditText _address;
    @BindView(R.id.et_address1)
    android.support.design.widget.TextInputEditText _address1;
    @BindView(R.id.btn_submit)
    Button _submit;
    @BindView(R.id.radioGrp)
    RadioGroup _radiogroup;
    @BindView(R.id.radio_male)
    RadioButton _male;
    @BindView(R.id.radio_female)
    RadioButton _female;
    @BindView(R.id.fab)
    FloatingActionButton _imageSelect;
    @BindView(R.id.edit_date)
    ImageView _edit_date;
    public String userprofile = "";


    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
    private Uri fileUri;
    private static String Image_uri = "";
    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;
    private int PICK_IMAGE_REQUEST = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit);
        ButterKnife.bind(this);

        _imageSelect.setOnClickListener(this);
        _submit.setOnClickListener(this);
        _edit_date.setOnClickListener(this);

        _address.setOnClickListener(this);


        _address1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    showPlacePickerDialog();
                }
                return false;
            }
        });


        _name.setText(SharedPrefs.getString(this, SharedPrefs.Username));
        _email.setText(SharedPrefs.getString(this, SharedPrefs.Email));
        _design.setText(SharedPrefs.getString(this, SharedPrefs.Designation));
        _dob.setText(SharedPrefs.getString(this, SharedPrefs.Dob));
        _about.setText(SharedPrefs.getString(this, SharedPrefs.about));
        _address.setText(SharedPrefs.getString(this, SharedPrefs.Address));
        _website.setText(SharedPrefs.getString(this, SharedPrefs.Website));
        _mobile.setText(SharedPrefs.getString(this, SharedPrefs.mobile));
        Uri myUri = Uri.parse(SharedPrefs.getString(this, SharedPrefs.Profilepic));
        _profile_pic.setImageURI(myUri);

        String userprofile = myUri.toString();
        Log.d(TAG, "onProfileSuccess: " + userprofile);


    }

    private void showPlacePickerDialog() {
        PlaceSearchDialog placeSearchDialog = new PlaceSearchDialog(this, new PlaceSearchDialog.LocationNameListener() {
            @Override
            public void locationName(String locationName) {
                _address.setText(locationName);
            }
        });
        placeSearchDialog.show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.edit_date:
                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        ProfileActivity.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd.show(getFragmentManager(), "Datepickerdialog");
                break;
            case R.id.btn_submit:

                profilesubmit();
                break;
            case R.id.fab:
                //openCameraForCapture();
                PopupMenu popup = new PopupMenu(ProfileActivity.this, _imageSelect);
                //Inflating the Popup using xml file
                popup.getMenuInflater().inflate(R.menu.menu_main, popup.getMenu());

                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {

                        if (item.getTitle().equals("Camera")) {
                            openCameraForCapture();
                        } else if (item.getTitle().equals("Open_Gallery")) {
                            openGallary();
                        } else if (item.getTitle().equals("Remove")) {

                        }

                        Toast.makeText(ProfileActivity.this, "You Clicked : " + item.getTitle() + " " + item.getItemId() + " " + item.getOrder(), Toast.LENGTH_SHORT).show();
                        return true;
                    }
                });

                popup.show();//showing popup menu
                break;

        }
    }

    public void profilesubmit() {

        if (!validate()) {
            onProfileFailed();
            return;
        } else {
            onProfileSuccess();
        }
    }

    private void onProfileSuccess() {


        final String name = _name.getText().toString();
        final String dob = _dob.getText().toString();
        final String mobile = _mobile.getText().toString();
        final String email = _email.getText().toString();
        final String website = _website.getText().toString();
        final String about = _about.getText().toString();
        final String designation = _design.getText().toString();
        final String address = _address.getText().toString();
        final String profile_pic = SharedPrefs.getString(this, SharedPrefs.Profilepic);

        Log.d(TAG, "onProfileSuccess: " + SharedPrefs.getString(this, SharedPrefs.Profilepic));


        SharedPrefs.save(ProfileActivity.this, SharedPrefs.Username, name);
        SharedPrefs.save(ProfileActivity.this, SharedPrefs.Email, email);
        SharedPrefs.save(ProfileActivity.this, SharedPrefs.Dob, dob);
        SharedPrefs.save(ProfileActivity.this, SharedPrefs.Email, email);
        SharedPrefs.save(ProfileActivity.this, SharedPrefs.mobile, mobile);
        SharedPrefs.save(ProfileActivity.this, SharedPrefs.about, about);
        SharedPrefs.save(ProfileActivity.this, SharedPrefs.Designation, designation);
        SharedPrefs.save(ProfileActivity.this, SharedPrefs.Address, address);
        SharedPrefs.save(ProfileActivity.this, SharedPrefs.Website, website);
        SharedPrefs.save(ProfileActivity.this, SharedPrefs.Profilepic, profile_pic);
        final String Profileset = "1";
        SharedPrefs.save(ProfileActivity.this, SharedPrefs.Profileset, Profileset);
        Log.d(TAG, "onProfile: " + SharedPrefs.getString(this, SharedPrefs.Profilepic));
        //Log.d(TAG, "onProfile: " + userprofile);
        Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
        startActivity(intent);

        Toast.makeText(ProfileActivity.this, "Done", Toast.LENGTH_SHORT).show();
    }

    private boolean validate() {
        boolean valid = true;
        final String name = _name.getText().toString();
        final String dob = _dob.getText().toString();
        final String mobile = _mobile.getText().toString();
        final String email = _email.getText().toString();
        final String website = _website.getText().toString();
        final String about = _about.getText().toString();
        final String designation = _design.getText().toString();
        final String address = _address.getText().toString();
        final String profile_pic = userprofile;

        Log.d(TAG, "onProfile: " + profile_pic);

        if (name.isEmpty()) {
            _name.setError("Please Enter Your Name");
            valid = false;
        }

        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _email.setError("enter a valid email address");
            valid = false;
        }

        if (website.isEmpty() || !Patterns.WEB_URL.matcher(website).matches()) {
            _website.setError("enter a website url");
            valid = false;
        }

        if (mobile.isEmpty() || !Patterns.PHONE.matcher(mobile).matches()) {
            _mobile.setError("enter a valid mobile no.");
            valid = false;
        }

        if (about.isEmpty()) {
            _about.setError("Please Enter Your About");
            valid = false;
        }
        return valid;
    }


    // Open Gallery Images
    public void openGallary() {
        Intent intent = new Intent();
        // Show only images, no videos or anything else
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        // Always show the chooser (if there are multiple options available)
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    // Open Camera For Capturing Image
    public void openCameraForCapture() {
        // create Intent to take a picture and return control to the calling application
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE); // create a file to save the image
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri); // set the image file name

        // start the image capture Intent
        startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
    }

    /**
     * Create a file Uri for saving an image or video
     */
    private static Uri getOutputMediaFileUri(int type) {
        Log.d("MyCameraApp", "log :  " + type);
        return Uri.fromFile(getOutputMediaFile(type));
    }

    /**
     * Create a File for saving an image or video
     */
    private static File getOutputMediaFile(int type) {
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.

        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "MyCameraApp");
        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d("MyCameraApp", "failed to create directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "IMG_" + timeStamp + ".jpg");
        } else if (type == MEDIA_TYPE_VIDEO) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "VID_" + timeStamp + ".mp4");
        } else {
            return null;
        }

        return mediaFile;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        // Open Camera After Result
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                // Image captured and saved to fileUri specified in the Intent
                Toast.makeText(this, "Image saved to:\n" +
                        fileUri.toString(), Toast.LENGTH_LONG).show();
                _profile_pic.setImageURI(fileUri);
                SharedPrefs.save(ProfileActivity.this, SharedPrefs.Profilepic, fileUri.toString());
            } else if (resultCode == RESULT_CANCELED) {
                // User cancelled the image capture
                _profile_pic.setImageURI(fileUri);
                SharedPrefs.save(ProfileActivity.this, SharedPrefs.Profilepic, fileUri.toString());
            } else {
                // Image capture failed, advise user
                _profile_pic.setImageURI(fileUri);
                SharedPrefs.save(ProfileActivity.this, SharedPrefs.Profilepic, fileUri.toString());
            }
            Image_uri = fileUri.toString();
            SharedPrefs.save(ProfileActivity.this, SharedPrefs.Profilepic, Image_uri);
        }

        // Gallery Image After Result
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri uri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                // Log.d(TAG, String.valueOf(bitmap));

                _profile_pic.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public void onProfileFailed() {
        Toast.makeText(getBaseContext(), "Invalid Profile details", Toast.LENGTH_LONG).show();
        _submit.setEnabled(true);
    }


    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
        _dob.setText(date);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.done, menu);
        return true;
    }


}

