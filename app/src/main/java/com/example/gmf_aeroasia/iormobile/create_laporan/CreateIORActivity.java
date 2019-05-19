package com.example.gmf_aeroasia.iormobile.create_laporan;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.gmf_aeroasia.iormobile.MySingleton2;
import com.example.gmf_aeroasia.iormobile.R;
import com.example.gmf_aeroasia.iormobile.adapter.GeneralSpinnerAdapter;
import com.example.gmf_aeroasia.iormobile.model.Category;
import com.example.gmf_aeroasia.iormobile.model.SubCategory;
import com.example.gmf_aeroasia.iormobile.model.SubCategorySpec;
import com.example.gmf_aeroasia.iormobile.model.Unit;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmConfiguration;

public class CreateIORActivity extends AppCompatActivity {


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
    public GeneralSpinnerAdapter spinnerAdapterTo, spinnerAdapterCategory, spinnerAdapterSubCategory, spinnerAdapterSub2Category;

    Category category;
    SubCategory subCategory;

    private static final String TAG = "CreateIORActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_ior);
        ButterKnife.bind(this);
        initRealm();

        getDataSpinner();
        initSpinnerUnit();
        initSpinnerCategory();
        initSpinnerSubCategory("01");
        initSpinnerSubCategorySpec("1");
    }

    void initRealm(){
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
        dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(year, month, dayOfMonth);
                etOccDate.setText(dateFormat.format(calendar.getTime()));
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        return datePickerDialog;
    }

    public void getDataSpinner() {
        String ip = getApplicationContext().getString(R.string.ip_default);
        final String[] listUrl = new String[]{
                "http://"+ip+"/API_IOR/unit/Read.php",
                "http://"+ip+"/API_IOR/category/Read.php",
                "http://"+ip+"/API_IOR/subcategory/Read.php",
                "http://"+ip+"/API_IOR/subcategoryspec/Read.php"};
        final Class[] listClass = new Class[]{Unit.class, Category.class, SubCategory.class, SubCategorySpec.class};
        for(int i = 0; i < listUrl.length; i++){
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
        spinnerAdapterTo = new GeneralSpinnerAdapter<Unit>(this, Unit.getAllUnit(realm)) {
            @Override
            public String getEntryText(int position) {
                return getData().get(position).getUnit();
            }
        };
        spTo.setAdapter(spinnerAdapterTo);
    }

    public void initSpinnerCategory(){
        spinnerAdapterCategory = new GeneralSpinnerAdapter<Category>(this, Category.getAllCategory(realm)) {
            @Override
            public String getEntryText(int position) {
                category = getData().get(position);
                return category.getCat_name();
            }
        };
        spCategory.setAdapter(spinnerAdapterCategory);
//        spCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                spinnerAdapterSubCategory.updateData(SubCategory.getSubCategoryById(realm, category.getCat_id()));
//                Toast.makeText(CreateIORActivity.this, ""+category.getCat_id(), Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
    }

    public void initSpinnerSubCategory(String id){
        spinnerAdapterSubCategory = new GeneralSpinnerAdapter<SubCategory>(this, SubCategory.getSubCategoryById(realm, id)) {
            @Override
            public String getEntryText(int position) {
                subCategory = getData().get(position);
                return subCategory.getCat_sub_desc();
            }
        };
        spSub1category.setAdapter(spinnerAdapterSubCategory);
//        spSub1category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                spinnerAdapterSub2Category.updateData(SubCategorySpec.getSubCategorySpecById(realm, subCategory.getCat_sub_id()));
//                Toast.makeText(CreateIORActivity.this, ""+subCategory.getCat_id(), Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
    }

    public void initSpinnerSubCategorySpec(String id){
        spinnerAdapterSub2Category = new GeneralSpinnerAdapter<SubCategorySpec>(this, SubCategorySpec.getSubCategorySpecById(realm, id)) {
            @Override
            public String getEntryText(int position) {
                return getData().get(position).getCat_sub_spec_desc();
            }
        };
        spSub2category.setAdapter(spinnerAdapterSub2Category);
    }


    @OnClick({R.id.et_occ_date, R.id.et_est_finish, R.id.bt_photo, R.id.bt_gallery, R.id.bt_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.et_occ_date:
                datePicker().show();
                Toast.makeText(this, "Occ. Date Clicked", Toast.LENGTH_SHORT).show();
                break;
            case R.id.et_est_finish:
                Toast.makeText(this, "Est. Date Clicked", Toast.LENGTH_SHORT).show();
                break;
            case R.id.bt_photo:
                Toast.makeText(this, "Open Camera Clicked", Toast.LENGTH_SHORT).show();
                break;
            case R.id.bt_gallery:
                Toast.makeText(this, "Open Gallery Clicked", Toast.LENGTH_SHORT).show();
                break;
            case R.id.bt_submit:
                Toast.makeText(this, "Submit Clicked", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onViewClicked: " + Category.getAllCategory(realm).toString());
                break;
        }
    }
}
