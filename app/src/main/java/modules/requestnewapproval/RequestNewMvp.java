package modules.requestnewapproval;

import android.app.ProgressDialog;

import com.medicard.member.core.model.DiagnosisTests;

import java.io.File;
import java.util.List;

import model.Attachment;
import model.AttachmentModel;
import model.TestsModel;
import model.newtest.DiagnosisDetails;
import model.newtest.DiagnosisProcedure;
import model.newtest.NewTestRequest;
import modules.base.Mvp;
import services.model.DiagnosisTestRequest;
import services.model.HospitalsToDoctor;
import services.response.MaceRequestResponse;
import utilities.Loader;

/**
 * Created by casjohnpaul on 5/30/2017.
 */

public interface RequestNewMvp {

    interface View extends Mvp.View {

        void displayDoctorDetails(String doctorDetails);

        void displayDiagnosisDetails(List<DiagnosisDetails> diagnosisDetails);

        void displayDiagnosisTests(List<DiagnosisTests> diagnosisTests);

        void onRequestError(String message);

        void onRequestSuccess(MaceRequestResponse maceRequestResponse);

        void onSuccessTestsResponse(String requestCode);

        void onSuccessAttachmentResponse();

        void internetConnected(TestsModel testsModel);

        void noInternetConnection();




    }

    interface Presenter extends Mvp.Presenter<RequestNewMvp.View> {

        void loadDoctorDetails(HospitalsToDoctor doctor);

        void loadDiagnosisTests();

        void loadDiagnosisTest(List<DiagnosisProcedure> diagnosisProcedures);

        void submitNewRequest(NewTestRequest newTestRequest);

        void submitTestRequest(DiagnosisTestRequest request, List<Attachment> attachments);

        void requestForTests(TestsModel testsModel);

        void checkOnline(TestsModel TestsModel);

        void submitAttachments(File file, String requestCode);

        void submitFinalAttachment(File file ,String requestCode);

    }

}
