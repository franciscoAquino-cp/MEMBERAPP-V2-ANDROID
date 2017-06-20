package com.medicard.member.module.hospital.fragment;

import android.content.Context;

import com.medicard.member.module.hospital.fragment.HospitalMvp;

import java.util.ArrayList;
import java.util.List;

import database.dao.HospitalDao;
import database.entity.Doctor;
import model.HospitalList;
import timber.log.Timber;

/**
 * Created by casjohnpaul on 5/7/2017.
 */

public class HospitalPresenter implements HospitalMvp.Presenter {


    private HospitalMvp.View hospitalView;
    private HospitalDao hospitalDao;

    public HospitalPresenter(Context context) {
        hospitalDao = new HospitalDao(context);
    }

    @Override
    public void attachView(HospitalMvp.View view) {
        hospitalView = view;
    }

    @Override
    public void detachView() {
        hospitalView = null;
    }

    @Override
    public void attachCallback() {

    }

    @Override
    public void detachCallback() {

    }

    @Override
    public void getHospitalListByDoctor(Doctor doctor) {
//        hospitalDao.findAllHospitalByHospitalCode(doctor.get)
    }

    @Override
    public void filterHospitals(List<HospitalList> hospitals, String query) {
        try {
            query = query.toLowerCase();
            List<HospitalList> hospitalFilteredResults = new ArrayList<>();
            for (HospitalList hospital : hospitals) {
                String hospitalName =
                        hospital.getHospitalName() != null ? hospital.getHospitalName().toLowerCase() : "";
                if (hospitalName.contains(query)) {
                    hospitalFilteredResults.add(hospital);
                }
            }

            hospitalView.displayFilterHospitalClinics(hospitalFilteredResults);
        } catch (Exception e) {
            Timber.d("error message %s", e.toString());
        }
    }

    @Override
    public void loadHospitalClinic() {
        List<HospitalList> hospitals = hospitalDao.findAll();
        Timber.d("total number of hospital %s", hospitals.size());
        hospitalView.displayHospitalClinic(hospitals);
    }

}