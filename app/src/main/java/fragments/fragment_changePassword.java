package fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.medicard.member.R;

import net.cachapa.expandablelayout.ExpandableLayout;

import InterfaceService.ChangePasswordWithPinCallback;
import InterfaceService.ChangePasswordWithPinRetrieve;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import mehdi.sakout.fancybuttons.FancyButton;
import model.Disclaimer;
import model.Pinned;
import model.ReturnChangePassword;
import utilities.AlertDialogCustom;
import utilities.ErrorMessage;
import utilities.Loader;
import utilities.NetworkTest;
import utilities.SharedPref;

public class fragment_changePassword extends Fragment implements ChangePasswordWithPinCallback {

    @BindView(R.id.cv_new)
    CardView cv_new;
    @BindView(R.id.et_pin)
    EditText et_pin;
    @BindView(R.id.et_retype_pin)
    EditText et_retype_pin;

    @BindView(R.id.cv_old)
    CardView cv_old;
    @BindView(R.id.et_old_pin)
    EditText et_old_pin;
    @BindView(R.id.et_new_pin)
    EditText et_new_pin;
    @BindView(R.id.et_retype_pin_new)
    EditText et_retype_pin_new;

    @BindView(R.id.btn_changePin_new)
    Button btn_changePin_new;
    @BindView(R.id.btn_changePin)
    Button btn_changePin;
    @BindView(R.id.btn_changePassword)
    Button btn_changePassword;

    @BindView(R.id.et_emailAdd)
    TextView et_emailAdd;

    @BindView(R.id.tv_current_pin)
    TextView tv_current_pin;

    @BindView(R.id.et_oldPass)
    EditText et_oldPass;

    @BindView(R.id.et_newPass)
    EditText et_newPass;

    @BindView(R.id.et_retypePass)
    EditText et_retypePass;

    @BindView(R.id.btn_disclamer)
    Button btn_disclamer;

    @BindView(R.id.pd_disclaimer)
    ProgressBar pb_disclaimer;

    @BindView(R.id.expandable_layout)
    ExpandableLayout expandable_layout;

    @BindView(R.id.show_disc)
    FancyButton show_disc;
    Loader loader;
    AlertDialogCustom alertDialogCustom = new AlertDialogCustom();

    @BindView(R.id.tvHideShowPassword) FancyButton tvHideShowPassword;
    @BindView(R.id.tvHideShowPin) FancyButton tvHideShowPin;
    @BindView(R.id.btnRegisterPin) FancyButton btnRegisterPin;

    @BindView(R.id.llChangePassword) LinearLayout llChangePassword;
    @BindView(R.id.llChangePin) LinearLayout llChangePin;

    @BindView(R.id.elChangePassword) ExpandableLayout elChangePassword;
    @BindView(R.id.elChangePin) ExpandableLayout elChangePin;

    @BindView(R.id.elRegisterPin) ExpandableLayout elRegisterPin;

    Context context;
    ChangePasswordWithPinRetrieve implement;
    ChangePasswordWithPinCallback callback;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getContext();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fragment_change_password, container, false);
        ButterKnife.bind(this, view);
        callback = this;
        loader = new Loader(context);
        implement = new ChangePasswordWithPinRetrieve(getActivity(), callback);

        implement.setEmailAdd(et_emailAdd);

        implement.setVisibility(cv_new, cv_old, tv_current_pin);

        expandable_layout.collapse(false);


        if (NetworkTest.isOnline(getContext()))
            implement.getDisclaimer(SharedPref.getStringValue(SharedPref.USER, SharedPref.MEMBERCODE, context), callback);

        implement.setDisclaimerUI(false, pb_disclaimer, btn_disclamer);
        return view;

    }

    @OnClick({R.id.btn_disclamer, R.id.btn_changePin_new, R.id.btn_changePin, R.id.btn_changePassword, R.id.show_disc})
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btn_changePin:
                implement.testInput(et_old_pin, et_new_pin, et_retype_pin_new);
                break;


            case R.id.btn_changePin_new:
                implement.testInput(et_pin, et_retype_pin);
                break;

            case R.id.btn_changePassword:
                implement.testChangePassword(et_emailAdd, et_newPass, et_oldPass, et_retypePass);
                break;

            case R.id.btn_disclamer:
                if (NetworkTest.isOnline(context)) {
                    loader.startLad();
                    loader.setMessage("Updating...");
                    implement.setDisclaimer(SharedPref.getStringValue(SharedPref.USER,
                            SharedPref.MEMBERCODE, context), callback);
                } else
                    alertDialogCustom.showMe(context, alertDialogCustom.HOLD_ON_title, alertDialogCustom.NO_Internet, 1);
                break;

            case R.id.show_disc:
                if (expandable_layout.isExpanded()) {
                    expandable_layout.collapse();
                    show_disc.setText("Show Disclosure Agreement");
                } else {
                    expandable_layout.expand();
                    show_disc.setText("Close Disclosure Agreement");
                }

                break;

        }
    }

    @OnClick(R.id.tvHideShowPassword)
    public void hideShowPassword() {
        if (elChangePassword.isExpanded()) {
            elChangePassword.collapse();
        } else {
            elChangePassword.expand();
        }
    }

    @OnClick(R.id.tvHideShowPin)
    public void hideShowPin() {
        if (elChangePin.isExpanded()) {
            elChangePin.collapse();
        } else {
            elChangePin.expand();
        }
    }

    @OnClick(R.id.btnRegisterPin)
    public void hideShowRegisterPin() {
        if (elRegisterPin.isExpanded()) {
            elRegisterPin.collapse();
        } else {
            elRegisterPin.expand();
        }
    }

    @Override
    public void onSuccess() {

    }

    @Override
    public void onError() {

    }

    @Override
    public void incorrectFiledListener() {
        alertDialogCustom.showMe(context, alertDialogCustom.HOLD_ON_title, alertDialogCustom.errorEmptyFields, 1);
    }

    @Override
    public void passwordWrongFormatListener() {
        alertDialogCustom.showMe(getContext(), alertDialogCustom.HOLD_ON_title, alertDialogCustom.PASSWORD_CRED_message, 1);
    }

    @Override
    public void sendPasswordListener() {
        loader.startLad();
        loader.setMessage("Updating...");
        implement.changePassword(et_emailAdd.getText().toString(), et_oldPass.getText().toString(), et_newPass.getText().toString());
    }

    @Override
    public void onErrorChangePassword() {
        loader.stopLoad();
        alertDialogCustom.showMe(getContext(), alertDialogCustom.HOLD_ON_title, alertDialogCustom.errorUnableToChangePass, 1);

    }

    @Override
    public void onSuccessListener(ReturnChangePassword returnChangePassword) {

        Log.d("RESPONSE", returnChangePassword.getResponseCode());
        loader.stopLoad();
        if (returnChangePassword.getResponseCode().equals("200")) {
            alertDialogCustom.showMe(getContext(), alertDialogCustom.success, alertDialogCustom.successChangePass, 2);
            clearChangePasswordFields();
        } else {
            alertDialogCustom.showMe(getContext(), alertDialogCustom.HOLD_ON_title, alertDialogCustom.errorUnableToChangePass, 1);
        }
    }

    @Override
    public void noInternetConnectionListener() {
        alertDialogCustom.showMe(getContext(), alertDialogCustom.NO_Internet_title, alertDialogCustom.NO_Internet, 1);
    }

    @Override
    public void notEqualPasswordListener() {
        alertDialogCustom.showMe(getContext(), alertDialogCustom.HOLD_ON_title, alertDialogCustom.PASSWORD_RETYPEPASS_MATCH_message, 1);

    }

    @Override
    public void testInputListener() {
        alertDialogCustom.showMe(getContext(), alertDialogCustom.HOLD_ON_title, alertDialogCustom.errorEmptyFields, 1);
    }

    @Override
    public void updatePin(String newPIN, String oldPIN) {
        implement.testupdatePin(newPIN, oldPIN);
    }

    @Override
    public void onSuccessUpdatePin(Pinned pinned, String newPIN) {

        if (pinned.getResponseCode().equals("230"))
            alertDialogCustom.showMe(context, alertDialogCustom.HOLD_ON_title, alertDialogCustom.didnt_match_old_new_pin, 1);
        else {
            SharedPref.setStringValue(SharedPref.USER, SharedPref.PIN, newPIN, context);
            implement.setVisibility(cv_new, cv_old, tv_current_pin);
            alertDialogCustom.showMe(context, alertDialogCustom.success, alertDialogCustom.success_update_pin, 2);
            implement.setToEmpty(et_old_pin, et_new_pin, et_retype_pin_new);

        }
    }

    @Override
    public void onErrorUpdatePin(String message) {
        alertDialogCustom.showMe(context, alertDialogCustom.HOLD_ON_title, ErrorMessage.setErrorMessage(message), 1);
    }

    @Override
    public void registerPin(String text) {
        implement.testRegisterPin(text);
    }

    @Override
    public void onSuccessRegisterPin(String responseCode, String newPin) {
        SharedPref.setStringValue(SharedPref.USER, SharedPref.PIN, newPin, context);
        alertDialogCustom.showMe(context, alertDialogCustom.success, alertDialogCustom.success_pin_message, 2);
        implement.updatePinUI(cv_new, cv_old, tv_current_pin);
    }

    @Override
    public void onErrorRegisterPin(Throwable error) {
        alertDialogCustom.showMe(context, alertDialogCustom.HOLD_ON_title, ErrorMessage.setErrorMessage(error.getMessage()), 1);
    }

    @Override
    public void testInputPinListener() {
        alertDialogCustom.showMe(context, alertDialogCustom.HOLD_ON_title, alertDialogCustom.PIN_RETYPEPIN_MATCH_message, 1);

    }

    @Override
    public void testInputPinLengthListener() {
        alertDialogCustom.showMe(context, alertDialogCustom.HOLD_ON_title, alertDialogCustom.PIN_RETYPE_LENGTH_message, 1);
    }

    @Override
    public void onErrorDisclaimer(String message) {
        loader.stopLoad();
        alertDialogCustom.showMe(context, alertDialogCustom.HOLD_ON_title, ErrorMessage.setErrorMessage(message), 1);
    }

    @Override
    public void onSuccessDisclaimer() {
        loader.stopLoad();
        alertDialogCustom.showMe(context, alertDialogCustom.HOLD_ON_title, alertDialogCustom.successfully_updated, 2);
        implement.setDisclaimerStatus(btn_disclamer, "0", getString(R.string.disclaimer_btn));
    }

    @Override
    public void onErrorFetchDisclaimer(String message) {
        implement.setDisclaimerUI(true, pb_disclaimer, btn_disclamer);
        implement.setDisclaimerStatus(btn_disclamer, "0", "Unable to fetch serviceType");
    }

    @Override
    public void onSuccessFetchDisclaimer(Disclaimer responseBody) {

        implement.setDisclaimerUI(true, pb_disclaimer, btn_disclamer);

        implement.setDisclaimerStatus(btn_disclamer, responseBody.getHasDisclaimer(), getString(R.string.disclaimer_btn));
    }

    private void clearChangePasswordFields() {
        et_newPass.setText("");
        et_oldPass.setText("");
        et_retypePass.setText("");
    }

}
