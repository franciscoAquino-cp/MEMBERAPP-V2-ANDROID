package v2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.medicard.member.R;

import java.util.ArrayList;

import InterfaceService.HospitalSortInterface;
import InterfaceService.HospitalSortRetrieve;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import mehdi.sakout.fancybuttons.FancyButton;
import model.CitiesAdapter;
import model.ProvincesAdapter;
import utilities.Constant;

/**
 * Created by IPC on 11/20/2017.
 */

public class SortDentistFragment extends AppCompatActivity implements HospitalSortInterface {

    @BindView(R.id.et_search)
    EditText et_search;

    @BindView(R.id.tv_sort_by)
    TextView tv_sort_by;
    @BindView(R.id.tv_province)
    TextView tv_province;
    @BindView(R.id.tv_city)
    TextView tv_city;

    @BindView(R.id.cb_med_clinic)
    CheckBox cb_med_clinic;

    @BindView(R.id.btn_reset)
    FancyButton btn_reset;

    @BindView(R.id.btn_show)
    FancyButton btn_sort;
    @BindView(R.id.btn_back)
    FancyButton btn_back;

    ArrayList<ProvincesAdapter> selectedProvince = new ArrayList<>();
    ArrayList<CitiesAdapter> selectedCity = new ArrayList<>();
    HospitalSortRetrieve implement;
    Context context;
    HospitalSortInterface callback;
    private int PROVINCE_CALL = 300;
    private int CITY_CALL = 200;
    private String searchString = "";
    private String CITY_SEARCH = "";
    private String PROV_SEARCH = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sort_dentist);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);
        context = this;
        callback = this;
        implement = new HospitalSortRetrieve(context, callback);
        selectedCity = getIntent().getParcelableArrayListExtra(Constant.SELECTED_CITY);
        selectedProvince = getIntent().getParcelableArrayListExtra(Constant.SELECTED_PROVINCE);
        searchString = getIntent().getStringExtra(Constant.SEARCH_STRING);
        et_search.setText(searchString);
        String sortBy = getIntent().getStringExtra(Constant.SORT_BY);
        sortBy = sortBy.equalsIgnoreCase("MediCard Clinics First") ? "" : sortBy;
        tv_sort_by.setText(sortBy);

        implement.setCityText(tv_city, selectedCity);
        implement.setProvinceText(tv_province, selectedProvince);
        implement.setCheckBox(cb_med_clinic, getIntent().getStringExtra(Constant.MEDICARD_ONLY));
    }

    @OnClick({R.id.tv_sort_by, R.id.tv_province, R.id.tv_city, R.id.btn_reset, R.id.btn_back, R.id.btn_show})
    public void onCLick(View v) {

        switch (v.getId()) {
            case R.id.tv_sort_by:
                implement.showSortBy();
                break;
            case R.id.tv_province:
                Intent gotoProvince = new Intent(SortDentistFragment.this, SelectProvinceActivity.class);
                gotoProvince.putParcelableArrayListExtra(Constant.SELECTED_PROVINCE, selectedProvince);
                gotoProvince.putExtra(Constant.SELECT, Constant.SELECT_PROVINCE);
                gotoProvince.putExtra(Constant.SPEC_SEARCH, PROV_SEARCH);
                startActivityForResult(gotoProvince, PROVINCE_CALL);
                break;
            case R.id.tv_city:
                Intent gotoSelection1 = new Intent(SortDentistFragment.this, SelectProvinceActivity.class);
                gotoSelection1.putParcelableArrayListExtra(Constant.SELECT_CODE, selectedProvince);
                gotoSelection1.putExtra(Constant.SPEC_SEARCH, CITY_SEARCH);
                gotoSelection1.putExtra(Constant.SELECT, Constant.SELECT_CITY);
                gotoSelection1.putParcelableArrayListExtra(Constant.SELECTED_CITY, selectedCity);
                startActivityForResult(gotoSelection1, CITY_CALL);
                break;

            case R.id.btn_reset:
                implement.resetDetails(tv_province, tv_city, tv_sort_by);
                implement.setResetCity(tv_city, selectedCity, et_search);
                implement.setResetProvince(tv_province, selectedProvince);
                implement.resetCheckBox(cb_med_clinic);
                break;
            case R.id.btn_show:
                Intent intent = new Intent();
                intent.putExtra(Constant.MEDICARD_ONLY, implement.getChecked(cb_med_clinic));
                intent.putExtra(Constant.SEARCH_STRING, et_search.getText().toString().trim());
                intent.putExtra(Constant.SORT_BY, tv_sort_by.getText().toString().trim());
                intent.putParcelableArrayListExtra(Constant.SELECTED_CITY, selectedCity);
                intent.putParcelableArrayListExtra(Constant.SELECTED_PROVINCE, selectedProvince);
                setResult(RESULT_OK, intent);
                finish();
                break;
            case R.id.btn_back:
                finish();
                break;

        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CITY_CALL) {
            if (resultCode == RESULT_OK) {
                selectedCity.clear();
                selectedCity = data.getParcelableArrayListExtra("CITY");
                implement.setCityText(tv_city, selectedCity);
                CITY_SEARCH = data.getStringExtra(Constant.SPEC_SEARCH);
            }
        } else if (requestCode == PROVINCE_CALL) {
            if (resultCode == RESULT_OK) {
                selectedProvince.clear();
                selectedProvince = data.getParcelableArrayListExtra("PROVINCE");
                PROV_SEARCH = data.getStringExtra(Constant.SPEC_SEARCH);
                implement.setProvinceText(tv_province, selectedProvince);
                implement.updateCityList(selectedCity, selectedProvince, tv_city);
            }

        }

    }

    @Override
    public void onSortByListener(String sort) {
        tv_sort_by.setText(sort);
    }

}
