package com.example.gmf_aeroasia.iormobile.create_laporan;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.StrictMode;
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
import com.kosalgeek.android.photoutil.CameraPhoto;
import com.kosalgeek.android.photoutil.ImageBase64;
import com.kosalgeek.android.photoutil.ImageLoader;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

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
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        try {
            startActivityForResult(cameraPhoto.takePhotoIntent(), CAMERA);
            cameraPhoto.addToGallery();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
                    Glide.with(this)
                            .load(path)
                            .fitCenter()
                            .into(ivReport);
                    break;
                case GALLERY:
                    galleryPhoto.setFileUri(data.getData());
                    path = galleryPhoto.getPath();
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
        galleryPhoto = new FileOpen(this);
        startActivityForResult(galleryPhoto.openStorageIntent(), GALLERY);
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

    public String getBase64(String path) {
        String base64 = "";
        try {
            File file = new File(path);
            byte[] buffer = new byte[(int) file.length() + 100];
            @SuppressWarnings("resource")
            int length = new FileInputStream(file).read(buffer);
            base64 = Base64.encodeToString(buffer, 0, length, Base64.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return base64;
    }

    void sendReport() {
        if (validate()) {
            dialogLoading = ProgressDialog.show(this, "",
                    "Loading. Please wait...", true);
            queue = Volley.newRequestQueue(this);
            String url = "http://" + getResources().getString(R.string.ip_default) + "/API_IOR/occ/Create.php";
            try {
                Bitmap bitmap = ImageLoader.init().from(path).requestSize(1024, 1024).getBitmap();
                stringImage = ImageBase64.encode(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

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
            data.put("attachment", getBase64(path));
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
                            startActivity(getIntent());
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
