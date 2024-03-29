package com.medicard.member.module.diagnosistest;

import android.content.Context;

import com.medicard.member.core.model.DiagnosisTests;
import com.medicard.member.core.session.DiagnosisTestSession;

import java.util.ArrayList;
import java.util.List;

import database.dao.TestDao;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import services.ServiceGenerator;
import services.client.ProcedureClient;
import services.model.Test;
import services.response.ProcedureByDiagnosisCodeResponse;
import services.response.TestResponseEntity;
import timber.log.Timber;

/**
 * Created by casjohnpaul on 6/19/2017.
 */

public class TestByDiagnosisPresenter implements TestByDiagnosisMvp.Presenter {


    private Context context;
    private TestByDiagnosisMvp.View testView;

    private ProcedureClient procedureClient;

    private TestDao testDao;

    public TestByDiagnosisPresenter(Context context) {
        this.context = context;
        procedureClient = ServiceGenerator.createApiService(ProcedureClient.class);

        testDao = new TestDao(context);
    }

    @Override
    public void attachView(TestByDiagnosisMvp.View view) {
        testView = view;
    }

    @Override
    public void detachView() {
        testView = null;
    }

    @Override
    public void attachCallback() {

    }

    @Override
    public void detachCallback() {

    }

    @Override
    public void loadTestProcedureByDiagnosisCode(String diagnosisCode) {
        procedureClient.getTestsByDiagnosisCode(diagnosisCode)
                .enqueue(new Callback<TestResponseEntity>() {
                    @Override
                    public void onResponse(Call<TestResponseEntity> call, Response<TestResponseEntity> response) {

                        List<Test> tests = new ArrayList<>();

                        if (response.isSuccessful()) {
                           List<String> procedureCodes = response.body().getTestCodes();

                            for (String code : procedureCodes) {
                                Test test = testDao.find(code);
                                if (test != null) {
                                    tests.add(test);
                                    Timber.d("tests %s", test.getProcedureName());
                                }
                            }
                            }
                            Timber.d("total test %s", tests.size());

                            if (tests.isEmpty() || tests.size() == 0) {
                                List<Test> all = testDao.findAll();
                                testView.onSuccess(all);
                            } else {
                                testView.onSuccess(tests);
                            }

                        }

                    @Override
                    public void onFailure(Call<TestResponseEntity> call, Throwable t) {
                        Timber.d("error#%s", t.toString());
                        testView.onError(t.toString());
                    }


                });
    }

    @Override
    public void loadAllTests(boolean fromTest) {
        List<Test> all = testDao.findAll();
        if (fromTest) {
            List<DiagnosisTests> allDiagnosisTests = DiagnosisTestSession.getAllDiagnosisTests();
            List<Test> tests = allDiagnosisTests.get(0).getTests();

            // todo sprint6 currently this algorithm is working only to display all test
            for (int index = 0; index < all.size(); index++) {
                for (Test test : tests) {
                    if (all.get(index).getProcCode().equals(test.getProcCode())) {
                        all.get(index).setSelected(true);
                    }
                }
            }
        }
        Timber.d("all tests %s", all.size());
        testView.onSuccess(all);
    }

    @Override
    public void filterTest(List<Test> testList, String query) {
        try{
            query = query.toLowerCase();
            List<Test> newTestList = new ArrayList<>();
            for(Test test: testList){
                String testDescription =
                        test.getProcedureName() != null ? test.getProcedureName().toLowerCase(): "";

                if(testDescription.contains(query)){
                    newTestList.add(test);
                }
                testView.onSuccess(newTestList);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
