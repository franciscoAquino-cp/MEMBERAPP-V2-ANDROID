package InterfaceService;

import model.Disclaimer;
import model.Pinned;
import model.ReturnChangePassword;

/**
 * Created by mpx-pawpaw on 2/6/17.
 */

public interface ChangePasswordWithPinCallback {

    void onSuccess();
    void onError();

    void incorrectFiledListener();

    void passwordWrongFormatListener();

    void sendPasswordListener();

    void onErrorChangePassword();

    void onSuccessListener(ReturnChangePassword returnChangePassword);

    void noInternetConnectionListener();

    void notEqualPasswordListener();

    void testInputListener();

    void updatePin(String newPIN, String oldPIN);

    void onSuccessUpdatePin(Pinned pinned, String newPIN);

    void onErrorUpdatePin(String message);

    void registerPin(String text);

    void onSuccessRegisterPin(String responseCode, String newPin);

    void onErrorRegisterPin(Throwable error);

    void testInputPinListener();

    void testInputPinLengthListener();

    void onErrorDisclaimer(String message);

    void onSuccessDisclaimer();

    void onErrorFetchDisclaimer(String message);

    void onSuccessFetchDisclaimer(Disclaimer responseBody);
}
