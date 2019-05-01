package com.example.gmf_aeroasia.iormobile.create_laporan;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.gmf_aeroasia.iormobile.R;
import com.example.gmf_aeroasia.iormobile.adapter.GeneralSpinnerAdapter;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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

    Calendar calendar;
    SimpleDateFormat dateFormat;
    DatePickerDialog datePickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_ior);
        ButterKnife.bind(this);
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
                break;
        }
    }
}
