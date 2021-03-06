package com.example.gmf_aeroasia.iormobile.create_laporan;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.gmf_aeroasia.iormobile.FileOpen;
import com.example.gmf_aeroasia.iormobile.Login.LoginActivity;
import com.example.gmf_aeroasia.iormobile.MySingleton2;
import com.example.gmf_aeroasia.iormobile.R;
import com.example.gmf_aeroasia.iormobile.adapter.GeneralSpinnerAdapter;
import com.example.gmf_aeroasia.iormobile.model.Category;
import com.example.gmf_aeroasia.iormobile.model.Probability;
import com.example.gmf_aeroasia.iormobile.model.Severity;
import com.example.gmf_aeroasia.iormobile.model.SubCategory;
import com.example.gmf_aeroasia.iormobile.model.SubCategorySpec;
import com.example.gmf_aeroasia.iormobile.model.Unit;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.kosalgeek.android.photoutil.CameraPhoto;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmConfiguration;

public class CreateIORActivity extends AppCompatActivity {

    private static final String TAG = "CreateIORActivity";
    private static final String CODE_SUCCES = "201";

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.sp_to)
    Spinner spTo;
    @BindView(R.id.cb_hide)
    CheckBox cbHide;
    @BindView(R.id.et_subject)
    EditText etSubject;
    @BindView(R.id.sp_category)
    Spinner spCategory;
    @BindView(R.id.sp_sub1category)
    Spinner spSub1category;
    @BindView(R.id.sp_sub2category)
    Spinner spSub2category;
    @BindView(R.id.rb_yes)
    RadioButton rbYes;
    @BindView(R.id.rb_no)
    RadioButton rbNo;
    @BindView(R.id.wrap_rb)
    RadioGroup wrapRb;
    @BindView(R.id.et_occ_date)
    EditText etOccDate;
    @BindView(R.id.et_est_finish)
    EditText etEstFinish;
    @BindView(R.id.iv_report)
    ImageView ivReport;
    @BindView(R.id.bt_photo)
    Button btPhoto;
    @BindView(R.id.bt_gallery)
    Button btGallery;
    @BindView(R.id.sp_lvl_type)
    Spinner spLvlType;
    @BindView(R.id.sp_risk_index)
    Spinner spRiskIndex;
    @BindView(R.id.sp_catastrophic)
    Spinner spCatastrophic;
    @BindView(R.id.et_desc)
    EditText etDesc;
    @BindView(R.id.bt_submit)
    Button btSubmit;

    public Realm realm;
    public Calendar calendar;
    public SimpleDateFormat dateFormat;
    public DatePickerDialog datePickerDialog;

    GeneralSpinnerAdapter<Unit> spinnerAdapterUnit;
    GeneralSpinnerAdapter<Category> spinnerAdapterCategory;
    GeneralSpinnerAdapter<SubCategory> spinnerAdapterSubCategory;
    GeneralSpinnerAdapter<SubCategorySpec> spinnerAdapterSub2Category;
    GeneralSpinnerAdapter<Severity> spinnerAdapterSeverity;
    GeneralSpinnerAdapter<Probability> spinnerAdapterProbability;

    Unit unit;
    Category category;
    SubCategory subCategory;
    SubCategorySpec subCategorySpec;
    Probability probability;
    Severity severity;


    CameraPhoto cameraPhoto;
    FileOpen galleryPhoto;
    RequestQueue queue;
    ProgressDialog dialogLoading;
    SharedPreferences sharedP;

    String path = null;
    String stringImage, stringAmbiguity, hideReport;
    int selectedRadio;
    Uri uri;

    //TestFile
    String upLoadServerUri = null;
    int serverResponseCode = 0;
    final String uploadFilePath = "/mnt/sdcard/";
    final String uploadFileName = "service_lifecycle.png";
    String filepath;

    private static final int CAMERA = 1001;
    private static final int GALLERY = 1002;
    @BindView(R.id.tv_name_file)
    TextView tvNameFile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_ior);
        ButterKnife.bind(this);

        cameraPhoto = new CameraPhoto(getApplicationContext());
        upLoadServerUri = "http://" + getResources().getString(R.string.ip_default) + "/API_IOR/occ/Create.php";

        initRealm();
        showButtonBack();
        getPref();
        getDataSpinner();
        initSpinnerUnit();
        initSpinnerCategory();
        initSpinnerSeverity();
        initSpinnerProbability();
        initSpinnerLvlType();
    }

    void initCameraPhoto() {
        Dexter.withActivity(this).withPermissions(
                Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE).withListener(
                new MultiplePermissionsListener(){
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if(report.areAllPermissionsGranted()){
                            try {
                                startActivityForResult(cameraPhoto.takePhotoIntent(), CAMERA);
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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case CAMERA:
                    if (ivReport.getVisibility() == View.GONE) {
                        ivReport.setVisibility(View.VISIBLE);
                        tvNameFile.setVisibility(View.GONE);
                    }
                    path = cameraPhoto.getPhotoPath();
                    uri = Uri.fromFile(new File(path));
                    Glide.with(this)
                            .load(path)
                            .fitCenter()
                            .into(ivReport);
                    filepath = path;
                    Log.d("Cek Path File", "Isinya photophat? "+filepath);

                    break;
                case GALLERY:
                    galleryPhoto.setFileUri(data.getData());
                    uri = data.getData();
                    path = galleryPhoto.getPath(this, uri);
                    filepath = path;
                    Log.d("Cek Path Camera", "Isinya photophat? "+filepath);

                    File file = new File(path);
                    if (new ImageFileFilter().accept(file)) {
                        ivReport.setVisibility(View.VISIBLE);
                        tvNameFile.setVisibility(View.GONE);
                        Glide.with(this)
                                .load(path)
                                .fitCenter()
                                .into(ivReport);
                    } else {
                        tvNameFile.setText("File : "+file.getName());
                        ivReport.setVisibility(View.GONE);
                        tvNameFile.setVisibility(View.VISIBLE);
                    }

                    break;
            }
        }
    }

    void showButtonBack() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    void getPref() {
        sharedP = getSharedPreferences(LoginActivity.PREF, Context.MODE_PRIVATE);
        if (sharedP == null) {
            Log.d(TAG, "getPref: SharedPref Kosong");
        } else {
            Log.d(TAG, "getPref Date: " + sharedP.getAll().toString());
        }
    }

    void initGalleryPhoto() {
        try {
            galleryPhoto = new FileOpen(this);
            startActivityForResult(galleryPhoto.openStorageIntent(), GALLERY);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void initRealm() {
        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration
                .Builder()
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(config);
        realm = Realm.getDefaultInstance();
    }

    public DatePickerDialog datePicker() {
        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(year, month, dayOfMonth);
                etOccDate.setText(dateFormat.format(calendar.getTime()));
                etEstFinish.setText(dateFormat.format(calendar.getTime()));
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        return datePickerDialog;
    }

    public void getDataSpinner() {
        String ip = getApplicationContext().getString(R.string.ip_default);
        final String[] listUrl = new String[]{
                "http://" + ip + "/API_IOR/unit/Read.php",
                "http://" + ip + "/API_IOR/category/Read.php",
                "http://" + ip + "/API_IOR/subcategory/Read.php",
                "http://" + ip + "/API_IOR/subcategoryspec/Read.php",
                "http://" + ip + "/API_IOR/severity/Read.php",
                "http://" + ip + "/API_IOR/probability/Read.php",
        };
        final Class[] listClass = new Class[]{
                Unit.class,
                Category.class,
                SubCategory.class,
                SubCategorySpec.class,
                Severity.class,
                Probability.class};
        for (int i = 0; i < listUrl.length; i++) {
            final int finalI = i;
            StringRequest request = new StringRequest(Request.Method.GET, listUrl[i], new Response.Listener<String>() {
                @Override
                public void onResponse(final String response) {
                    Log.d(TAG, "onResponse: " + response);
                    realm.executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            realm.createOrUpdateAllFromJson(listClass[finalI], response);
                        }
                    });
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d(TAG, "onErrorResponse: " + error);

                }
            });

            request.setRetryPolicy(new DefaultRetryPolicy(
                    5000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            MySingleton2.getmInstance(getBaseContext()).addToRequestque(request);
        }
    }

    public void initSpinnerUnit() {
        spinnerAdapterUnit = new GeneralSpinnerAdapter<Unit>(this, Unit.getAllUnit(realm)) {
            @Override
            public String getEntryText(int position) {
                return getData().get(position).getUnit();
            }
        };
        spTo.setAdapter(spinnerAdapterUnit);
        spTo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                unit = spinnerAdapterUnit.getData().get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void initSpinnerCategory() {
        spinnerAdapterCategory = new GeneralSpinnerAdapter<Category>(this, Category.getAllCategory(realm)) {
            @Override
            public String getEntryText(int position) {
                return getData().get(position).getCat_name();
            }

        };
        spCategory.setAdapter(spinnerAdapterCategory);
        spCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                category = spinnerAdapterCategory.getItem(position);
                initSpinnerSubCategory("0" + category.getCat_id());

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void initSpinnerSubCategory(String id) {
        spinnerAdapterSubCategory = new GeneralSpinnerAdapter<SubCategory>(this, SubCategory.getSubCategoryById(realm, id)) {
            @Override
            public String getEntryText(int position) {
                return getData().get(position).getCat_sub_desc();
            }
        };
        spSub1category.setAdapter(spinnerAdapterSubCategory);
        spSub1category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                subCategory = spinnerAdapterSubCategory.getData().get(position);
                initSpinnerSubCategorySpec(subCategory.getCat_sub_id());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void initSpinnerSubCategorySpec(String id) {
        spinnerAdapterSub2Category = new GeneralSpinnerAdapter<SubCategorySpec>(this, SubCategorySpec.getSubCategorySpecById(realm, id)) {
            @Override
            public String getEntryText(int position) {
                return getData().get(position).getCat_sub_spec_desc();
            }
        };
        spSub2category.setAdapter(spinnerAdapterSub2Category);
        spSub2category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                subCategorySpec = spinnerAdapterSub2Category.getData().get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void initSpinnerSeverity() {
        spinnerAdapterSeverity = new GeneralSpinnerAdapter<Severity>(this, Severity.getAllSeverity(realm)) {
            @Override
            public String getEntryText(int position) {
                return getData().get(position).getSeverity_value();
            }
        };
        spRiskIndex.setAdapter(spinnerAdapterSeverity);
        spRiskIndex.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                severity = spinnerAdapterSeverity.getData().get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void initSpinnerProbability() {
        Log.d("Cek", "initSpinnerProbability: ");

        spinnerAdapterProbability = new GeneralSpinnerAdapter<Probability>(this, Probability.getAllProbability(realm)) {
            @Override
            public String getEntryText(int position) {
                Log.d("Cek", "getEntryText: " + getData().get(position).getProbability_value().toString());
                return getData().get(position).getProbability_value();

            }
        };
        spCatastrophic.setAdapter(spinnerAdapterProbability);
        spCatastrophic.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d("Cek", "onItemSelected: " + spinnerAdapterProbability.getData().get(position).toString());

                probability = spinnerAdapterProbability.getData().get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void initSpinnerLvlType() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.arr_lvl_type, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spLvlType.setAdapter(adapter);
    }

    boolean validate() {
        boolean valid = true;
        String send_to = unit.getUnit();
        String occ_sub = etSubject.getText().toString();
        String occ_category = category.getCat_id();
        String sub_category = subCategory.getCat_sub_id();
        String category_spec = subCategorySpec.getCat_sub_spec_id();
        String ambiguity = stringAmbiguity;
        String date = etOccDate.getText().toString();
        String estfinish = etEstFinish.getText().toString();
        String attachment = stringImage;
        String lvl_type = spLvlType.getSelectedItem().toString();
        String risk_index = unit.getUnit();
        String detail = etDesc.getText().toString();

        if (send_to.isEmpty()) {
            ((TextView) spTo.getSelectedView()).setError("");
            valid = false;
        }
        if (occ_sub.isEmpty()) {
            etSubject.setError("");
            valid = false;
        }
        if (occ_category.isEmpty()) {
            ((TextView) spCategory.getSelectedView()).setError("");
            valid = false;
        }
        if (sub_category.isEmpty()) {
            ((TextView) spSub1category.getSelectedView()).setError("");
            valid = false;
        }
        if (category_spec.isEmpty()) {
            ((TextView) spSub2category.getSelectedView()).setError("");
            valid = false;
        }
        if (occ_category.isEmpty()) {
            ((TextView) spCategory.getSelectedView()).setError("");
            valid = false;
        }
        if (occ_category.isEmpty()) {
            ((TextView) spCategory.getSelectedView()).setError("");
            valid = false;
        }
        if (wrapRb.getCheckedRadioButtonId() == -1) {
            rbYes.setError("");
            rbNo.setError("");
            valid = false;
        }
        if (date.isEmpty()) {
            etOccDate.setError("");
            valid = false;
        }
        if (estfinish.isEmpty()) {
            etEstFinish.setError("");
            valid = false;
        }
//        if (path == null) {
//            Toast.makeText(this, "You must upload some image", Toast.LENGTH_SHORT).show();
//            valid = false;
//        }
        if (lvl_type.isEmpty()) {
            ((TextView) spLvlType.getSelectedView()).setError("");
            valid = false;
        }
        if (risk_index.isEmpty()) {
            ((TextView) spCatastrophic.getSelectedView()).setError("");
            ((TextView) spRiskIndex.getSelectedView()).setError("");
            valid = false;
        }
        if (detail.isEmpty()) {
            etDesc.setError("");
            valid = false;
        }
        return valid;
    }

//    public String getBase64(String path) {
//        String base64 = "";
//        try {
//            File file = new File(path);
//            byte[] buffer = new byte[(int) file.length() + 100];
//            byte[] b = FileUtils.readFileToByteArray(file);
//            @SuppressWarnings("resource")
//            int length = new FileInputStream(file).read(b);
//            base64 = Base64.encodeToString(b, 0, length, Base64.DEFAULT);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return base64;
//    }

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

    void sendReport() {
        if (validate()) {
            dialogLoading = ProgressDialog.show(this, "",
                    "Loading. Please wait...", true);
            queue = Volley.newRequestQueue(this);
            String url = "http://" + getResources().getString(R.string.ip_default) + "/API_IOR/occ/Create.php";

            uploadFile(filepath);

            if (cbHide.isChecked()) {
                hideReport = "1";
            } else {
                hideReport = "0";
            }

            selectedRadio = wrapRb.getCheckedRadioButtonId();
            RadioButton radioButton = findViewById(selectedRadio);
            stringAmbiguity = radioButton.getText().toString();
            String riskIndex = probability.getProbability_value() + "" + severity.getSeverity_value();

            HashMap data = new HashMap();
            data.put("occ_send_to", unit.getUnit());
            data.put("occ_sub", etSubject.getText().toString());
            data.put("occ_category", category.getCat_id());
            data.put("occ_sub_category", subCategory.getCat_sub_id());
            data.put("occ_sub_spec", subCategorySpec.getCat_sub_spec_id());
            data.put("occ_ambiguity", stringAmbiguity);
            data.put("occ_date", etOccDate.getText().toString());
            data.put("estfinish", etOccDate.getText().toString());
            data.put("attachment", getBase64(uri));
            data.put("occ_level_type", spLvlType.getSelectedItem().toString());
            data.put("occ_risk_index", riskIndex);
            data.put("occ_detail", etDesc.getText().toString());
            data.put("created_by", sharedP.getString(LoginActivity.KEY_ID, ""));
            data.put("created_by_unit", sharedP.getString(LoginActivity.KEY_UNIT, ""));
            data.put("created_by_name", sharedP.getString(LoginActivity.KEY_NAME, ""));
            data.put("created_hide", hideReport);


            Log.d(TAG, "sendReport: " + data.toString());
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(data), new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                    try {
                        if (response.getString("code").contains(CODE_SUCCES)) {
                            Log.d(TAG, "onResponse sukses: " + response);

                            Toast.makeText(CreateIORActivity.this, "Report Success", Toast.LENGTH_SHORT).show();
                            finish();
//                            startActivity(getIntent());
                        }
                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                        Log.d(TAG, "onResponse2: " + e);

                    }

                    Log.d(TAG, "onResponse: " + response);
                    dialogLoading.hide();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d(TAG, "onErrorResponse: " + error);
                    dialogLoading.hide();
                }
            });
            queue.add(request);
        }
//        Log.d(TAG, "sendReport: "+getBase64(uri));
    }

    @SuppressLint("LongLogTag")
    public int uploadFile(String sourceFileUri) {


        String fileName = sourceFileUri;

        HttpURLConnection conn = null;
        DataOutputStream dos = null;
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";
        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 1 * 1024 * 1024;
        File sourceFile = new File(sourceFileUri);

        if (!sourceFile.isFile()) {

            //dialog.dismiss();

            Log.e("uploadFile", "Source File not exist :"
                    +uploadFilePath + "" + uploadFileName);

            runOnUiThread(new Runnable() {
                public void run() {
                    Log.e("uploadFile", "Source File not exist :"
                            +uploadFilePath + "" + uploadFileName);

                }
            });

            return 0;

        }
        else
        {
            try {
                Log.d(TAG, "uploadFile: Cek URI to Server "+upLoadServerUri);
                // open a URL connection to the Servlet
                FileInputStream fileInputStream = new FileInputStream(sourceFile);
                URL url = new URL(upLoadServerUri);

                // Open a HTTP  connection to  the URL
                conn = (HttpURLConnection) url.openConnection();
                conn.setDoInput(true); // Allow Inputs
                conn.setDoOutput(true); // Allow Outputs
                conn.setUseCaches(false); // Don't use a Cached Copy
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Connection", "Keep-Alive");
                conn.setRequestProperty("ENCTYPE", "multipart/form-data");
                conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
                conn.setRequestProperty("uploaded_file", fileName);

                dos = new DataOutputStream(conn.getOutputStream());

                dos.writeBytes(twoHyphens + boundary + lineEnd);
                // dos.writeBytes("Content-Disposition: form-data; name=uploaded_file; filename="+fileName+"" +lineEnd);
                dos.writeBytes("Content-Disposition: form-data; name="+fileName+";filename="+ fileName + "" + lineEnd);
                dos.writeBytes(lineEnd);

                // create a buffer of  maximum size
                bytesAvailable = fileInputStream.available();

                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                buffer = new byte[bufferSize];

                // read file and write it into form...
                bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                while (bytesRead > 0) {

                    dos.write(buffer, 0, bufferSize);
                    bytesAvailable = fileInputStream.available();
                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
                    bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                }

                // send multipart form data necesssary after file data...
                dos.writeBytes(lineEnd);
                dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

                // Responses from the server (code and message)
                serverResponseCode = conn.getResponseCode();
                String serverResponseMessage = conn.getResponseMessage();

                Log.i("uploadFile", "HTTP Response is : "
                        + serverResponseMessage + ": " + serverResponseCode);

                if(serverResponseCode == 200){

                    runOnUiThread(new Runnable() {
                        public void run() {

//
//
//                            Toast.makeText(getApplicationContext(), "File Upload Complete. "+msg,
//                                    Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                //close the streams //
                fileInputStream.close();
                dos.flush();
                dos.close();

            } catch (MalformedURLException ex) {

//                dialog.dismiss();
                ex.printStackTrace();

                runOnUiThread(new Runnable() {
                    public void run() {

                        Toast.makeText(getApplicationContext(), "MalformedURLException",
                                Toast.LENGTH_SHORT).show();
                    }
                });

                Log.e("Upload file to server", "error: " + ex.getMessage(), ex);
            } catch (Exception e) {

//                dialog.dismiss();
                e.printStackTrace();

                runOnUiThread(new Runnable() {
                    public void run() {
//
//                        Toast.makeText(getApplicationContext(), "Got Exception : see logcat ",
//                                Toast.LENGTH_SHORT).show();
                    }
                });
                Log.e("Upload file to server Exception", "Exception : "+ e.getMessage(), e);
            }
//            dialog.dismiss();
            return serverResponseCode;

        } // End else block
    }


    @OnClick({R.id.et_occ_date, R.id.et_est_finish, R.id.bt_photo, R.id.bt_gallery, R.id.bt_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.et_occ_date:
                datePicker().show();
                break;
            case R.id.et_est_finish:
                break;
            case R.id.bt_photo:
                initCameraPhoto();
                break;
            case R.id.bt_gallery:
                initGalleryPhoto();
                break;
            case R.id.bt_submit:
                sendReport();
                break;
        }
    }

    class ImageFileFilter implements FileFilter {
        private final String[] okFileExtensions = new String[]{"jpg", "jpeg", "png", "gif"};

        public boolean accept(File file) {
            for (String extension : okFileExtensions) {
                if (file.getName().toLowerCase().endsWith(extension)) {
                    return true;
                }
            }
            return false;
        }
    }

}
