package com.example.gmf_aeroasia.iormobile;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.gmf_aeroasia.iormobile.service.MyCommand;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.kosalgeek.android.photoutil.CameraPhoto;
import com.kosalgeek.android.photoutil.ImageLoader;
import com.kosalgeek.android.photoutil.PhotoLoader;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReportGuestActivity extends AppCompatActivity {

    EditText e_name_rg,e_email_rg,e_company_rg,e_unit_rg,e_sub_rg,e_ref_rg,e_date_rg,e_des_rg;
    private int mYear, mMonth, mDay;
    RadioGroup radiogrup_rg;
    CameraPhoto cameraPhoto;
    FileOpen galleryPhoto;
    LinearLayout linearMain;
    RadioButton r_porter;
    final String TAG = "ReportGuestActivity";
    ArrayList<String> imageList = new ArrayList<>();
    final int GALLERY_REQUEST = 1200;
    final int CAMERA_REQUEST = 1332;
    Button bt_photo,bt_galllery,bt_submit;
    ProgressDialog dialogLoading;
    String path = null;
    Uri uri;
    public Calendar  calendar;
    public SimpleDateFormat dateFormat;
    public DatePickerDialog datePickerDialog;
    private static final int requestCode = 100;
    TextView tv_nama_file;
    Context mContext;
    @TargetApi(Build.VERSION_CODES.M)
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_report_guest);

        Toolbar mtoolbar = (Toolbar) findViewById(R.id.toolbarguetreport);
        setSupportActionBar(mtoolbar);
        mtoolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);

        e_name_rg = (EditText)findViewById(R.id.name_report);
        e_email_rg = (EditText)findViewById(R.id.email_report);
        e_company_rg = (EditText)findViewById(R.id.email_report);
        e_unit_rg = (EditText)findViewById(R.id.unit_report);
        e_sub_rg = (EditText)findViewById(R.id.sub_report);
        e_ref_rg = (EditText)findViewById(R.id.ref_report);
        e_date_rg = (EditText)findViewById(R.id.date_report);
        e_des_rg = (EditText)findViewById(R.id.des_report);
        radiogrup_rg = (RadioGroup) findViewById(R.id.radiogrup_report);
        bt_photo = (Button)findViewById(R.id.bt_photo);
        bt_galllery = (Button)findViewById(R.id.bt_gallery);
        linearMain = (LinearLayout)findViewById(R.id.linearMain);
        bt_submit = (Button)findViewById(R.id.submit_report);
        tv_nama_file = (TextView)findViewById(R.id.tv_name_file_g);

//        galleryPhoto = new GalleryPhoto(getApplicationContext());
        cameraPhoto = new CameraPhoto(getApplicationContext());

        final MyCommand myCommand = new MyCommand(getApplicationContext());

        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_DENIED)
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.CAMERA}, CAMERA_REQUEST);




        bt_galllery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                galleryPhoto = new FileOpen(getApplicationContext());
                startActivityForResult(galleryPhoto.openStorageIntent(), GALLERY_REQUEST);

            }
        });
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        bt_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                try {
//                    startActivityForResult(cameraPhoto.takePhotoIntent(), CAMERA_REQUEST);
//                    cameraPhoto.addToGallery();
//                } catch (IOException e) {
//                    Log.d("Photo : ", e.toString());
//
//                    Toast.makeText(getApplicationContext(),
//                            "Something Wrong while taking photos"+e, Toast.LENGTH_SHORT).show();
//                }
                startCamera();
            }
        });

        e_date_rg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get Current Date
                datePicker().show();

            }
        });

        mtoolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


        bt_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


//                if(!validasi()){
//                    Toast.makeText(getApplicationContext(), "Masukan Data Masjid Terlebih Dahulu", Toast.LENGTH_SHORT).show();
//                    return;
//                }
                dialogLoading = ProgressDialog.show(ReportGuestActivity.this, "",
                        "Loading. Please wait...", true);


                for( String imagePath: imageList){
                    try {

                        int selectedId = radiogrup_rg.getCheckedRadioButtonId();

                        // mencari radio button
                        r_porter = (RadioButton) findViewById(selectedId);
                        //menampilkan pesan teks / toast

//                        Bitmap bitmap = PhotoLoader.init().from(imagePath).requestSize(128, 128).getBitmap();
//
//                        final String encodedString1 = ImageBase64.encode(bitmap);

                        String url = "http://"+getApplicationContext().getString(R.string.ip_default)+"/API_IOR/input_guest_report.php";


                        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {

                                        //final JSONObject jsonObject = new JSONObject(response);
                                        //String status = response.toString();
                                        if (response.contains("success")) {
                                            e_name_rg.setText("");
                                            e_company_rg.setText("");
                                            e_des_rg.setText("");
                                            e_ref_rg.setText("");
                                            e_date_rg.setText("");
                                            e_unit_rg.setText("");
                                            e_email_rg.setText("");
                                            e_sub_rg.setText("");


                                            linearMain.removeViewAt(0);

                                            Toast.makeText(getApplicationContext(), "Thank You For Your Report", Toast.LENGTH_SHORT).show();
                                        } else {

                                            Toast.makeText(getApplicationContext(),
                                                    response.toString()+".", Toast.LENGTH_SHORT).show();
                                        }

                                        dialogLoading.hide();
                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                                    Toast.makeText(getApplicationContext(),
                                            "Koneksi Timeout , Silahkan Coba Kembali", Toast.LENGTH_SHORT).show();
                                } else if (error instanceof ServerError) {
                                    Toast.makeText(getApplicationContext(),
                                            "Server Mengalami Masalah , Silahkan Coba Kembali", Toast.LENGTH_SHORT).show();
                                } else if (error instanceof NetworkError) {
                                    Toast.makeText(getApplicationContext(),
                                            "Jaringan Mengalami Masalah, Silahkan Coba Kembali", Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(getApplicationContext(),
                                            "Tambah Data Masjid Gagal , Silahkan Coba Kembali", Toast.LENGTH_SHORT).show();
                                }
                                dialogLoading.hide();
                                error.printStackTrace();
                            }
                        }) {
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String, String> params = new HashMap<String, String>();
                                params.put("foto_report", getBase64(uri));
                                params.put("nama_guest", e_name_rg.getText().toString());
                                params.put("email_guest", e_email_rg.getText().toString());
                                params.put("company_guest", e_company_rg.
                                        getText().toString());
                                params.put("unit_guest", e_unit_rg.getText().toString());
                                params.put("sub_lapor", e_sub_rg.getText().toString());
                                params.put("ref_lapor", e_ref_rg.getText().toString());
                                params.put("date_lapor", e_date_rg.getText().toString());
                                params.put("des_lapor", e_des_rg.getText().toString());
                                params.put("porter_lapor", r_porter.getText().toString());

                                //params.put("api_token", apitoken.getText().toString());
                                Log.d("Response", String.valueOf(params));
                                return params;
                            }
                        };

//                        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
//                                10000,
//                                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                        MySingleton2.getmInstance(getBaseContext()).addToRequestque(stringRequest);

                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), "Error while loading image", Toast.LENGTH_SHORT).show();
                    }
                }


                myCommand.execute();

            }
        });



    }

    public void startCamera(){
        Dexter.withActivity(this).withPermissions(
                Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE).withListener(
                        new MultiplePermissionsListener(){
                            @Override
                            public void onPermissionsChecked(MultiplePermissionsReport report) {
                                if(report.areAllPermissionsGranted()){
                                    try {
                                        startActivityForResult(cameraPhoto.takePhotoIntent(), CAMERA_REQUEST);
                                        cameraPhoto.addToGallery();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }else if(report.isAnyPermissionPermanentlyDenied()){

                                }
                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                                token.continuePermissionRequest();
                            }

                        }
        ).check();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK){
            if(requestCode == GALLERY_REQUEST){
                galleryPhoto.setFileUri(data.getData());
                uri = data.getData();
                String photoPath = galleryPhoto.getPath();
                imageList.add(photoPath);
                Log.d(TAG, photoPath);
                try {

                    Bitmap bitmap = PhotoLoader.init().from(photoPath).requestSize(512, 512).getBitmap();

                    ImageView imageView = new ImageView(getApplicationContext());
                    LinearLayout.LayoutParams layoutParams =
                            new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                    ViewGroup.LayoutParams.MATCH_PARENT);
                    imageView.setLayoutParams(layoutParams);
                    imageView.setMaxHeight(300);
                    imageView.setMaxWidth(300);
                    imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                    imageView.setPadding(5, 5, 5, 10);
                    imageView.setAdjustViewBounds(true);
                    imageView.setImageBitmap(bitmap);

                    linearMain.addView(imageView);

                } catch (FileNotFoundException e) {
                    Toast.makeText(getApplicationContext(), "Error while loading image", Toast.LENGTH_SHORT).show();
                }
            }else if(requestCode == CAMERA_REQUEST){
                String photoPath = cameraPhoto.getPhotoPath();
                uri = Uri.fromFile(new File(photoPath));
                //ambil nilai lokasi gambar
                imageList.add(photoPath);
                Log.d(TAG, photoPath);
                try {
                    Bitmap bitmap = ImageLoader.init().from(photoPath).requestSize(512, 512).getBitmap();
                    //menampilkan image yang udah di pilih

                    //imageList.setImageBitmap(bitmap);

                    ImageView imageView = new ImageView(getApplicationContext());
                    LinearLayout.LayoutParams layoutParams =
                            new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                    ViewGroup.LayoutParams.MATCH_PARENT);
                    imageView.setLayoutParams(layoutParams);
                    imageView.setMaxHeight(300);
                    imageView.setMaxWidth(300);
                    imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    imageView.setPadding(0, 0, 0, 10);
                    imageView.setAdjustViewBounds(true);
                    imageView.setImageBitmap(bitmap);

                    linearMain.addView(imageView);

                } catch (FileNotFoundException e) {
                    Toast.makeText(getApplicationContext(),
                            "Something Wrong while loading photos", Toast.LENGTH_SHORT).show();
                }

            }
        }

    }

    public DatePickerDialog datePicker() {
        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(year, month, dayOfMonth);
                e_date_rg.setText(dateFormat.format(calendar.getTime()));

            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        return datePickerDialog;
    }

    class ImageFileFilter implements FileFilter {
        private final String[] okFileExtensions = new String[]{"jpg", "jpeg", "png"};

        public boolean accept(File file) {
            for (String extension : okFileExtensions) {
                if (file.getName().toLowerCase().endsWith(extension)) {
                    return true;
                }
            }
            return false;
        }
    }

    public String getBase64(Uri uri){
        String base64 = "";
        byte[] bytes;
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            InputStream in = getContentResolver().openInputStream(uri);
            bytes = getBytes(in);
            base64 = Base64.encodeToString(bytes, Base64.DEFAULT);
        }catch (Exception e){
            e.printStackTrace();
        }
        return base64;
    }

    public byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }


//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//
//        if (requestCode == CAMERA_REQUEST) {
//            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//
//                Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
//            } else {
//                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
//            }
//        }
//    }

    /*get Permissions Result*/
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case CAMERA_REQUEST: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    cameraPhoto = new CameraPhoto(getApplicationContext());
                    try {
                        cameraPhoto.takePhotoIntent();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    cameraPhoto.addToGallery();
                }
            }
        }
    }

    /*check permissions  for marshmallow*/

    private static boolean hasPermissions(Context context, String... permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

//    public boolean validasi() {
//        boolean valid = true;
//
//        final String a = namamasjid.getText().toString();
//        final String s = address.getText().toString();
//        final String d = latitude.getText().toString();
//        final String f = longitude.getText().toString();
//        final String g = deskripsi.getText().toString();
//
//        if (a.isEmpty()) {
//            namamasjid.setError("Nama Masjid Harus Terisi");
//            valid = false;
//        } else {
//            namamasjid.setError(null);
//        }
//
//        if (s.isEmpty()) {
//            address.setError("Alamat Harus Terisi");
//            valid = false;
//        } else {
//            address.setError(null);
//        }
//        if (d.isEmpty()) {
//            latitude.setError("Alamat Harus Terisi");
//            valid = false;
//        } else {
//            latitude.setError(null);
//        }
//        if (f.isEmpty()) {
//            longitude.setError("Alamat Harus Terisi");
//            valid = false;
//        } else {
//            longitude.setError(null);
//        }
//        if (g.isEmpty()) {
//            deskripsi.setError("Alamat Harus Terisi");
//            valid = false;
//        } else {
//            deskripsi.setError(null);
//        }
//        return valid;
//
//    }

}
