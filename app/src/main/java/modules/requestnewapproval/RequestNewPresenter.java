package modules.requestnewapproval;

import android.content.Context;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.gson.Gson;
import com.medicard.member.core.model.DiagnosisTests;
import com.medicard.member.core.session.DiagnosisTestSession;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import database.dao.DoctorDao;
import database.dao.HospitalDao;
import database.dao.ProcedureDao;
import model.newtest.DiagnosisDetails;
import model.newtest.DiagnosisProcedure;
import model.newtest.NewTestRequest;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import services.AppInterface;
import services.AppService;
import services.ServiceGenerator;
import services.client.DiagnosisClient;
import services.client.MemberLoaClient;
import services.model.Diagnosis;
import services.model.HospitalsToDoctor;
import services.model.Procedure;
import services.model.Test;
import services.response.DiagnosisResponse;
import services.response.MaceRequestResponse;
import timber.log.Timber;
import utilities.DiagnosisUtils;
import utilities.SharedPref;

/**
 * Created by casjohnpaul on 5/30/2017.
 */

public class RequestNewPresenter implements RequestNewMvp.Presenter {

    private DiagnosisClient diagnosisClient;

    private Context context;

    private HospitalDao hospitalDao;
    private ProcedureDao procedureDao;

    MemberLoaClient memberLoaClient;

    private RequestNewMvp.View requestNewView;


    public RequestNewPresenter(Context context) {
        this.context = context;

        hospitalDao = new HospitalDao(context);
        procedureDao = new ProcedureDao(context);
        diagnosisClient = AppService.createApiService(DiagnosisClient.class, AppInterface.ENDPOINT);

        memberLoaClient = ServiceGenerator.createApiService(MemberLoaClient.class);
    }

    @Override
    public void attachView(RequestNewMvp.View view) {
        this.requestNewView = view;
    }

    @Override
    public void detachView() {
        this.requestNewView = null;
    }

    @Override
    public void attachCallback() { }

    @Override
    public void detachCallback() { }

    @Override
    public void loadDoctorDetails(HospitalsToDoctor doctor) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(doctor.getFullName())
                .append("\n")
                .append("")
                .append(doctor.getSpecDesc());
    }

    @Override
    public void loadDiagnosisTests() {
        Timber.d("load all diagnosis was called...");
        Timber.d("diagnosis session %s", DiagnosisTestSession.getAllDiagnosisTests().size());
        List<DiagnosisTests> diagnosisTestsList = new ArrayList<>();

        DiagnosisTests diagnosisTest = new DiagnosisTests();
        Diagnosis diagnosis = new Diagnosis();

        boolean isDisplayAll = DiagnosisTestSession.isDisplayAll();
        diagnosisTestsList.addAll(DiagnosisTestSession.getAllDiagnosisTests());
        if (isDisplayAll && diagnosisTestsList.size() == 1) {
            diagnosis.setDiagCode("998");
            diagnosis.setDiagDesc("Laboratory");
            diagnosisTestsList.get(0).setDiagnosis(diagnosis);

            // display the diagnosis with test
            diagnosisTest = diagnosisTestsList.get(0);
            Timber.d("diagnosis code %s and name %s", diagnosisTest.getDiagnosis().getDiagCode(), diagnosisTest.getDiagnosis().getDiagDesc());
            for (Test test : diagnosisTest.getTests()) {
                Timber.d("test : %s", test.getProcedureName());
            }
            requestNewView.displayDiagnosisTests(diagnosisTestsList);

        }
    }

    @Override
    public void loadDiagnosisTest(final List<DiagnosisProcedure> diagnosisProcedures) {
        boolean isDisplayAll = SharedPref.getBooleanValue(context, SharedPref.KEY_DISPLAY_ALL_PROCEDURE);
        Timber.d("isDisplayAll %s", isDisplayAll);
        if (isDisplayAll) {
            List<DiagnosisDetails> diagnosisDetails = new ArrayList<>();
            List<Procedure> procedures = new ArrayList<>();

            Diagnosis diagnosis = new Diagnosis("998", "Laboratory");
            for (DiagnosisProcedure procedure : diagnosisProcedures) {
                Procedure temp = procedureDao.find(procedure.getProcedureCode());
                procedures.add(temp);
            }

            diagnosisDetails.add(new DiagnosisDetails(diagnosis, procedures));
            requestNewView.displayDiagnosisDetails(diagnosisDetails);

        } else {
            diagnosisClient.getDiagnosisList()
                    .enqueue(new Callback<DiagnosisResponse>() {
                        @Override
                        public void onResponse(Call<DiagnosisResponse> call, Response<DiagnosisResponse> response) {
                            if (response.isSuccessful()) {

                                Gson gson = new Gson();

                                List<DiagnosisDetails> diagnosisDetails = new ArrayList<>();

                                DiagnosisResponse diagnosisResponse = response.body();
                                List<Diagnosis> diagnosisList = diagnosisResponse.getDiagnosisList();

                                // get all the diagnosis original code
                                List<String> allOriginalDiagnosisCode = DiagnosisUtils.getAllOriginalDiagnosisCode(diagnosisProcedures);

                                // get the object of original diagnosis
                                List<Diagnosis> allDiagnosisByCode =
                                        DiagnosisUtils.getAllDignosisByCode(diagnosisList, allOriginalDiagnosisCode);

                            /*for (DiagnosisProcedure dp : diagnosisProcedures) {
                                for (Diagnosis diagnosis : allDignosisByCode) {
                                    if (dp.getDiagnosisCode().equalsIgnoreCase(diagnosis.getDiagCode())) {

                                    }
                                }
                            }*/
                                for (Diagnosis diagnosis : allDiagnosisByCode) {
                                    List<Procedure> procedures = new ArrayList<>();
                                    for (DiagnosisProcedure diagnosisProcedure : diagnosisProcedures) {
                                        if (diagnosis.getDiagCode().equals(diagnosisProcedure.getDiagnosisCode())) {
                                            Procedure procedure = procedureDao.find(diagnosisProcedure.getProcedureCode());
                                            procedures.add(procedure);
                                        }
                                    }
                                    diagnosisDetails.add(new DiagnosisDetails(diagnosis, procedures));
                                }

                            /*for (DiagnosisDetails details : diagnosisDetails) {
                                Timber.d("diagnosis description : %s", details.getDiagnosis().getDiagDesc());
                                Set<Procedure> procedures = new LinkedHashSet<Procedure>(details.getProcedures());
                                for (Procedure procedure : procedures) {
                                    Timber.i("procedure %s, procedureCode %s", procedure.getProcedureDesc(), procedure.getProcedureCode());
                                }
                            }*/
                                Set<DiagnosisDetails> uniqueDetails = new LinkedHashSet<>(diagnosisDetails);
                                requestNewView.displayDiagnosisDetails(new ArrayList<>(uniqueDetails));

                            }
                        }

                        @Override
                        public void onFailure(Call<DiagnosisResponse> call, Throwable t) {
                            Timber.e("errorMessage#%s", t.toString());
                        }
                    });
        }

    }

    @Override
    public void submitNewRequest(NewTestRequest newTestRequest) {
        memberLoaClient.requestBasicOrOtherTest(newTestRequest)
                .enqueue(new Callback<MaceRequestResponse>() {
                    @Override
                    public void onResponse(Call<MaceRequestResponse> call, Response<MaceRequestResponse> response) {
                        if (response.isSuccessful()) {
                            Timber.d("successfully submitted");
                            requestNewView.onRequestSuccess();
                        } else {
                            Timber.d("error#%s", response.errorBody().toString());
                            requestNewView.onRequestError(response.errorBody().toString());
                        }
                    }

                    @Override
                    public void onFailure(Call<MaceRequestResponse> call, Throwable t) {
                        requestNewView.onRequestError(t.toString());
                    }
                });
    }


}
