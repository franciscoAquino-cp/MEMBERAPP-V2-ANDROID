package fragments;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.medicard.member.R;

import java.util.ArrayList;

import InterfaceService.FragmentApiDocCallback;
import InterfaceService.FragmentDoctorListRetrieve;
import Sqlite.DatabaseHandler;
import Sqlite.SetDoctorToDatabase;
import adapter.DoctorListAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import mehdi.sakout.fancybuttons.FancyButton;
import model.CitiesAdapter;
import model.Doctors;
import model.GetDoctorsToHospital;
import model.ProvincesAdapter;
import model.SpecsAdapter;
import timber.log.Timber;
import utilities.AlertDialogCustom;
import utilities.Constant;
import utilities.ErrorMessage;
import utilities.Loader;
import utilities.SharedPref;
import v2.SortDoctorActivity;

import static android.app.Activity.RESULT_OK;

/**
 * Created by IPC on 11/16/2017.
 */

public class fragment_doctorList extends Fragment implements FragmentApiDocCallback, View.OnClickListener {

    @BindView(R.id.ed_searchDoctor)
    EditText ed_searchDoctor;
    @BindView(R.id.rv_doctor)
    RecyclerView rv_doctor;
    @BindView(R.id.tv_doc_not_found)
    TextView tv_doc_not_found;

    @BindView(R.id.cvDoctorDetails)
    CardView cvDoctorDetails;
    @BindView(R.id.tv_name)
    TextView tv_name;
    @BindView(R.id.tv_spec)
    TextView tv_spec;
    @BindView(R.id.tv_room)
    TextView tv_room;
    @BindView(R.id.tv_sched)
    TextView tv_sched;

    @BindView(R.id.btn_sort)
    LinearLayout btn_sort;

    int DOCTOR_CALL = 100;

    @BindView(R.id.btn_show_selected_hospital)
    FancyButton btn_show_selected_hospital;
    @BindView(R.id.btn_show_all)
    FancyButton btn_show_all;


    AlertDialogCustom alertDialogCustom = new AlertDialogCustom();
    ProgressDialog pd;
    Context context;
    FragmentApiDocCallback callback;
    DoctorListAdapter doctorAdapter;
    DatabaseHandler databaseHandler;
    Loader loader;
    FragmentDoctorListRetrieve implement;
    LinearLayoutManager llm;
    ArrayList<GetDoctorsToHospital> array = new ArrayList<>();
    ArrayList<SpecsAdapter> selectedSpec = new ArrayList<>();
    ArrayList<CitiesAdapter> selectedCity = new ArrayList<>();
    ArrayList<ProvincesAdapter> selectedProvince = new ArrayList<>();
    private String search_string = "";
    private String sort_by = "";
    private String room_number = "";
    private String DERMATOLOGY = "DERMATOLOGY";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_doctor_list, container, false);
        ButterKnife.bind(this, view);
        context = getContext();
        callback = this;
        databaseHandler = new DatabaseHandler(context);
        loader = new Loader(context);
        implement = new FragmentDoctorListRetrieve(context, callback, databaseHandler);
        llm = new LinearLayoutManager(getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);



        doctorAdapter = new DoctorListAdapter(context, array, callback);
        rv_doctor.setLayoutManager(llm);
        rv_doctor.setAdapter(doctorAdapter);

        getSearchDoctor(SharedPref.getStringValue(SharedPref.USER,SharedPref.HOSPITAL_CODE,context), "", doctorAdapter, selectedSpec, selectedCity, selectedProvince, sort_by, "");

        ed_searchDoctor.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                getSearchDoctor(SharedPref.getStringValue(SharedPref.USER,SharedPref.HOSPITAL_CODE,context), String.valueOf(s),doctorAdapter, selectedSpec, selectedCity, selectedProvince, sort_by, room_number);
                search_string = String.valueOf(s);
            }
        });
        init();

        btn_show_selected_hospital.setOnClickListener(this);
        btn_show_all.setOnClickListener(this);
        btn_sort.setOnClickListener(this);


//        implement.dropDoctors();
//        implement.getDoctors("");

        return view;
    }

    private void getSearchDoctor(String hosp_code, String editable, DoctorListAdapter doctorAdapter, ArrayList<SpecsAdapter> selectedSpec, ArrayList<CitiesAdapter> selectedCity, ArrayList<ProvincesAdapter> selectedProvince, String sort_by, String room_number) {

        array.clear();
        if(hosp_code.equalsIgnoreCase("TOP")){
            array.addAll(databaseHandler.retrieveTop100Doctor(selectedCity,selectedProvince,String.valueOf(editable), selectedSpec, implement.testSort(sort_by), room_number));
        }else {
            array.addAll(databaseHandler.retrieveDoctor(hosp_code,selectedCity,selectedProvince,String.valueOf(editable), selectedSpec, implement.testSort(sort_by), room_number));
        }


//        // get only the unique value from the set
//        Timber.d("original size with duplicate %s", array.size());
//        Set<GetDoctorsToHospital> uniqueSet = new LinkedHashSet<>(array);
//        array.clear();
//        array.addAll(uniqueSet);
//        Timber.d("new set without duplicate %s", array.size());


        if (array.size() == 0) {
            tv_doc_not_found.setVisibility(View.VISIBLE);
            cvDoctorDetails.setVisibility(View.GONE);
            rv_doctor.setVisibility(View.GONE);
        } else {
            doctorAdapter.notifyDataSetChanged();
            rv_doctor.setVisibility(View.VISIBLE);
            tv_doc_not_found.setVisibility(View.GONE);
        }
        doctorAdapter.notifyDataSetChanged();
    }

    private void init() {
        pd = new ProgressDialog(getContext(), R.style.MyTheme);
        pd.setCancelable(false);
        pd.setMessage("Requesting...");
        pd.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
    }


    @OnClick({R.id.btn_sort})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_sort:
                Intent intent = new Intent(getActivity(), SortDoctorActivity.class);
                intent.putExtra(Constant.SEARCH_STRING, search_string);
                intent.putParcelableArrayListExtra(Constant.SELECTED_SPECIALIZATION, selectedSpec);
                intent.putExtra(Constant.SORT_BY, sort_by);
                intent.putExtra(Constant.ROOM_NUMBER, room_number);
                intent.putParcelableArrayListExtra(Constant.SELECTED_CITY, selectedCity);
                intent.putParcelableArrayListExtra(Constant.SELECTED_PROVINCE, selectedProvince);
                startActivityForResult(intent, DOCTOR_CALL);
                break;
            case R.id.btn_show_selected_hospital:
                String hosp_code = SharedPref.getStringValue(SharedPref.USER, SharedPref.HOSPITAL_CODE, context);
                if (hosp_code != null) {
//                    implement.dropDoctors();
//                    implement.getDoctors(hosp_code);
                    getSearchDoctor(hosp_code,"", doctorAdapter, selectedSpec, selectedCity, selectedProvince, sort_by, "");
                } else {
                    tv_doc_not_found.setVisibility(View.VISIBLE);
                    tv_doc_not_found.setText("Please Select Hospital first");
                }
                break;
            case R.id.btn_show_all:
                getSearchDoctor("TOP","", doctorAdapter, selectedSpec, selectedCity, selectedProvince, sort_by, "");
                break;


        }
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onDoctorSelect(ArrayList<GetDoctorsToHospital> array, int position) {
        SharedPref.setStringValue(SharedPref.USER, SharedPref.DOCTOR_NAME, array.get(position).getDocLname() + ", " + array.get(position).getDocLname() + " " + array.get(position).getDocFname(), context);
        SharedPref.setStringValue(SharedPref.USER, SharedPref.DOCTOR_CODE, array.get(position).getDoctorCode(), context);
        SharedPref.setStringValue(SharedPref.USER, SharedPref.DOCTOR_DESC, array.get(position).getSpecDesc(), context);
        SharedPref.setStringValue(SharedPref.USER, SharedPref.DOCTOR_SCHED, array.get(position).getSchedule(), context);
        SharedPref.setStringValue(SharedPref.USER, SharedPref.DOCTOR_U, "", context);
        SharedPref.setStringValue(SharedPref.USER, SharedPref.DOCTOR_ROOM, array.get(position).getRoom(), context);

//        try{
//            cvDoctorDetails.setVisibility(View.VISIBLE);
//            tv_name.setText(SharedPref.getStringValue(SharedPref.USER, SharedPref.DOCTOR_NAME, context));
//            tv_spec.setText(SharedPref.getStringValue(SharedPref.USER, SharedPref.DOCTOR_DESC, context));
//            tv_sched.setText("Schedule: " + SetUnfilledField.setData(SharedPref.getStringValue(SharedPref.USER, SharedPref.DOCTOR_SCHED, context)));
//            tv_room.setText("Room: " + SetUnfilledField.setData(SharedPref.getStringValue(SharedPref.USER, SharedPref.DOCTOR_ROOM, context)));
//        }catch (Exception e){
//            cvDoctorDetails.setVisibility(View.GONE);
//        }

    }

    @Override
    public void internetConnected(String hospCode) {
        loader.startLad();
        loader.setMessage("Getting Doctor List...");
        implement.getDoctorList(hospCode);
    }

    @Override
    public void noInternetConnection() {
        alertDialogCustom.showMe(context, alertDialogCustom.NO_Internet_title, alertDialogCustom.NO_Internet, 1);
    }

    @Override
    public void onFragDoctorError(String message) {
        Log.e("DOCTOR", message);
        loader.stopLoad();
        alertDialogCustom.showMe(context, alertDialogCustom.HOLD_ON_title, ErrorMessage.setErrorMessage(message), 1);
    }

    @Override
    public void onFragDoctorSuccess(Doctors doctors) {
        SetDoctorToDatabase.insertToDb(databaseHandler, doctors, callback);
    }

    @Override
    public void onSuccess(ArrayList<GetDoctorsToHospital> getDoctorsToHospital) {
        loader.stopLoad();
        Timber.d("doctor list : %s", getDoctorsToHospital.toString());
        if (getDoctorsToHospital != null && getDoctorsToHospital.size() > 0) {
            alertDialogCustom.showMe(
                    context,
                    alertDialogCustom.HOLD_ON_title,
                    getString(R.string.success_load_doctors),
                    1);
        }
        getSearchDoctor(SharedPref.getStringValue(SharedPref.USER,SharedPref.HOSPITAL_CODE,context), "", doctorAdapter, selectedSpec, selectedCity, selectedProvince, sort_by, "");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == DOCTOR_CALL && resultCode == RESULT_OK) {
            selectedSpec = data.getParcelableArrayListExtra(Constant.SELECTED_SPECIALIZATION);
            search_string = data.getStringExtra(Constant.SEARCH_STRING);
            sort_by = data.getStringExtra(Constant.SORT_BY);
            room_number = data.getStringExtra(Constant.ROOM_NUMBER);
            selectedCity = data.getParcelableArrayListExtra(Constant.SELECTED_CITY);
            selectedProvince = data.getParcelableArrayListExtra(Constant.SELECTED_PROVINCE);
            getSearchDoctor(SharedPref.getStringValue(SharedPref.USER,SharedPref.HOSPITAL_CODE,context), search_string, doctorAdapter, selectedSpec,selectedCity,selectedProvince, sort_by, room_number);
            implement.setSearchStringtoUI(search_string, ed_searchDoctor);
        }
    }
}
