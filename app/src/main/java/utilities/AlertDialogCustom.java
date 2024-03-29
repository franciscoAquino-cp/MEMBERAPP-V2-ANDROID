package utilities;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;

import com.medicard.member.R;
import com.medicard.member.RegistrationActivity;

import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.util.Base64;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import model.Attachment;
import model.RequestResult;
import services.model.AttachmentObject;

/**
 * Created by mpx-pawpaw on 10/21/16.
 */

public class AlertDialogCustom {

    public String A_VALID_PIN = "A valid PIN is required to Request for Approval. Please register PIN through Account Settings";
    public String EMAIL_message = "Invalid email Address. Please try again.";
    public String success_pin_message = "You have successfully registered a PIN.";
    public String PASSWORD_CRED_message = "Password must have at least one (1) capitalized letter, one (1) number and a minimum of eight (8) characters.";
    public String PASSWORD_MATCH_message = "Password did not match. Please try again.";
    public String PASSWORD_RETYPEPASS_MATCH_message = "New Password and Re-Typed Password did not match. Please try again.";
    public String PIN_RETYPEPIN_MATCH_message = "PIN and Re-Typed PIN did not match. Please try again.";
    public String PIN_RETYPE_LENGTH_message = "PIN must be four (4) digits long";

    public String CONGRATULATIONS_title = "Success!";
    public String CONGRATULATIONS_message = "You have successfully created an account. Please login to begin.";

    public String Saved_Screenshot = "Approval Request has been saved to MediCard folder in your photo gallery.";

    public String ALREADY_message = "Account is already existing.";
    public String MemberAccountDidNotMatch = "Date of Birth does not match with MediCard ID number.";
    public String HOLD_ON_title = "Hold On";

    public String close_loa = "Are you sure you to cancel Approval request?";
    public String UsernameMinimumCharacter = "Username must contain a minimum of 3 characters.";
    public String InvalidUsername = "Username invalid. It must not contain blank spaces.";
    public String spec_not_good = "Please proceed to Coordinator to request for Approval.";

    public String noSpecialCharacter = "Please use only letters (a-z), numbers, and periods.";

    public String successfullyAddedDep = "You have successfully added a dependent.";
    public String INVALID_PASS_USER = "You have entered an incorrect password. After 3 unsuccessful attempts your account will be locked.";
    public String NO_Internet = "Please check your internet connection.";
    public String NO_Internet_title = "Hold On";
    public String unknown = "Alert";

    public String data_cancelled = "Request successfully cancelled.";
    public String didnt_match_old_new_pin = "Old PIN is incorrect. Please try again.";
    public String success = "Success";
    public String success_msg = "You have successfully uploaded your photo.";
    public String errorUploadPhoto = "Failed to upload photo. Please try again.";
    public String delete_msg = "Image successfully deleted.";

    public String addDependenceAlreadyAdded = "Account has already been added.";
    public String addDependenceNotDependent = "Member ID is not your dependent.";

    public String successChangePass = "You have successfully changed your password.";
    public String succesCheckEmailPassword = "Password has been sent to your email address.";
    public String errorUnableToChangePass = "Something went wrong. Unable to change your password. Please try again.";
    public String errorUnableToRequestPass = "Something went wrong. Unable to request a new password. Please try again.";
    public String errorUsernameOrPass = "It seems like you've got the wrong username/password. Please try again.";
    public String errorRequestUsernameOrPass = "Something went wrong. Incorrect Email Address or MemberCode.";
    public String errorAccountLocked = "Your account is locked. Please tap on the \"Forgot your Password\" link to reset your password.";
    public String errorNeedToChangePass = "You need to change your password.";
    public String errorNoUsername = "No User Account for entered username.";
    public String errorEmptyFields = "Please fill up the required fields.";
    public String warninginputMemberId = "Please input Dependent Member ID.";
    public String maternity_not_available = "Maternity Consultation is not available for male.";
    public String no_city_selected = "Please Select City";
    public String success_update_pin = "You have successfully changed your PIN";
    public String no_condition = "Please enter Problem/Condition.";
    public String no_doctor = "Please enter Doctor.";
    public String pick_start_Date = "Pick Start date first.";
    public String end_must_greater = "End date should be greater than Start Date.";
    public String start_must_lesser = "Start Date should be lesser than End Date.";
    public String data_not_avilable = "List is not available";
    public String no_list_1 = "No list for Test";
    public String no_list_2 = "No list for Diagnosis";
    public String no_list_3 = "No list for Clinic/Hospital";
    public String no_list_4 = "No list for Doctors";

    public String successfully_updated = "Setting is successfully updated.";
    public String skipDiagnosis = "You can skip this screen";
    public String skipDiagnosisAlert = "Alert";
    public String no_connection_to_server = "Cannot connect to server ";



    public static final String LOA_GENERATE_PDF_SUCCESS =
            "Saved to \"MediCard\" folder";

    public static final String SAVE_LOA_REQUEST = "Saved to \"My Approval Request\"";

    TextView tv_message, tv_title;
    CircleImageView ci_error_image;
    Button btn_accept, btn_cancel;

    private Button onViewPdf;

    onClickDialogListener callback;


    public void showMe(Context context, String title, String message, int errorImage) {

        final Dialog dialog = new Dialog(context);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.alertshow);
        dialog.getWindow().setWindowAnimations(R.style.CustomDialogAnimation);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCancelable(false);

        ci_error_image = (CircleImageView) dialog.findViewById(R.id.ci_error_image);
        tv_message = (TextView) dialog.findViewById(R.id.tv_message);
        tv_title = (TextView) dialog.findViewById(R.id.tv_title);
        btn_accept = (Button) dialog.findViewById(R.id.btn_accept);
        btn_accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        setDetails(context, message, title, errorImage, btn_accept);

        dialog.show();

        int width = (int) (context.getResources().getDisplayMetrics().widthPixels * 0.70);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.width = width;
        dialog.getWindow().setAttributes(lp);
    }

    /**
     * @param context
     * @param title
     * @param message
     * @param errorImage
     */
    public void showMe(Context context, String title, String message, int errorImage, final OnCustomDialogClickListener listener) {

        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.alert_view_pdf);
        dialog.getWindow().setWindowAnimations(R.style.CustomDialogAnimation);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCancelable(false);

        ci_error_image = (CircleImageView) dialog.findViewById(R.id.ci_error_image);
        tv_message = (TextView) dialog.findViewById(R.id.tv_message);
        tv_title = (TextView) dialog.findViewById(R.id.tv_title);
        btn_accept = (Button) dialog.findViewById(R.id.btnOk);
        btn_accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                listener.onOkClick();
            }
        });

        dialog.findViewById(R.id.btnViewLoa)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.onViewPdf();
                    }
                });

        setDetails(context, message, title, errorImage, btn_accept);

        dialog.show();

        int width = (int) (context.getResources().getDisplayMetrics().widthPixels * 0.70);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.width = width;
        dialog.getWindow().setAttributes(lp);
    }

    public static void alertNotification(final Context context, final String title, @StringRes int message, final OnDialogClickListener listener) {

        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.alertshow2button);
        dialog.getWindow().setWindowAnimations(R.style.CustomDialogAnimation);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCancelable(false);

        CircleImageView ci_error_image = (CircleImageView) dialog.findViewById(R.id.ci_error_image);
        TextView tv_message = (TextView) dialog.findViewById(R.id.tv_message);
        TextView tv_title = (TextView) dialog.findViewById(R.id.tv_title);
        Button btn_accept = (Button) dialog.findViewById(R.id.btn_accept);
        Button btn_cancel = (Button) dialog.findViewById(R.id.btn_cancel);
        btn_accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                listener.onOkClick();
            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                listener.onCancelClick();

            }
        });
        btn_cancel.setBackgroundColor(ContextCompat.getColor(context, R.color.BLACK));
//        setDetails(context, message, title, errorImage, btn_accept);

        ci_error_image.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.warning));
        btn_accept.setBackgroundColor(ContextCompat.getColor(context, R.color.BLACK));

        tv_message.setText(message);
        tv_title.setText(title);

        dialog.show();


        int width = (int) (context.getResources().getDisplayMetrics().widthPixels * 0.70);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.width = width;
        dialog.getWindow().setAttributes(lp);
    }

    public void successDialog(Context context, String title, String message, int errorImage, final OnDialogClickListener listener) {

        final Dialog dialog = new Dialog(context);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.alertshow);
        dialog.getWindow().setWindowAnimations(R.style.CustomDialogAnimation);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCancelable(false);

        ci_error_image = (CircleImageView) dialog.findViewById(R.id.ci_error_image);
        tv_message = (TextView) dialog.findViewById(R.id.tv_message);
        tv_title = (TextView) dialog.findViewById(R.id.tv_title);
        btn_accept = (Button) dialog.findViewById(R.id.btn_accept);
        btn_accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onOkClick();
                dialog.dismiss();
            }
        });

        setDetails(context, message, title, errorImage, btn_accept);

        dialog.show();

        int width = (int) (context.getResources().getDisplayMetrics().widthPixels * 0.70);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.width = width;
        dialog.getWindow().setAttributes(lp);
    }

    public void showMe(Context context, String title, String message, int errorImage, final onClickDialogListener callback) {

        this.callback = callback;

        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.alertshow2button);
        dialog.getWindow().setWindowAnimations(R.style.CustomDialogAnimation);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCancelable(false);

        ci_error_image = (CircleImageView) dialog.findViewById(R.id.ci_error_image);
        tv_message = (TextView) dialog.findViewById(R.id.tv_message);
        tv_title = (TextView) dialog.findViewById(R.id.tv_title);
        btn_accept = (Button) dialog.findViewById(R.id.btn_accept);
        btn_cancel = (Button) dialog.findViewById(R.id.btn_cancel);
        btn_accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                callback.onOkPress();
            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

            }
        });
        btn_cancel.setBackgroundColor(ContextCompat.getColor(context, R.color.BLACK));
        setDetails(context, message, title, errorImage, btn_accept);


        dialog.show();


        int width = (int) (context.getResources().getDisplayMetrics().widthPixels * 0.70);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.width = width;
        dialog.getWindow().setAttributes(lp);

    }


    public void showRegisterDone(final Context context, String title, String message, int errorImage) {

        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.alertshow);
        dialog.getWindow().setWindowAnimations(R.style.CustomDialogAnimation);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCancelable(false);

        ci_error_image = (CircleImageView) dialog.findViewById(R.id.ci_error_image);
        tv_message = (TextView) dialog.findViewById(R.id.tv_message);
        tv_title = (TextView) dialog.findViewById(R.id.tv_title);
        btn_accept = (Button) dialog.findViewById(R.id.btn_accept);
        btn_accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                EventBus.getDefault().post(new RegistrationActivity.MessageEvent(CONGRATULATIONS_title));
            }
        });


        setDetails(context, message, title, errorImage, btn_accept);


        dialog.show();


        int width = (int) (context.getResources().getDisplayMetrics().widthPixels * 0.70);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.width = width;
        dialog.getWindow().setAttributes(lp);

    }


    private void setDetails(Context context, String message, String title, int errorImage, Button button) {

        tv_message.setText(message);
        tv_title.setText(title);


        switch (errorImage) {

            case 1:
//error
                ci_error_image.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.warning));
                button.setBackgroundColor(ContextCompat.getColor(context, R.color.BLACK));
                break;


            case 2:
//congrats
                ci_error_image.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.success));
                button.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary));

                break;


            case 3:
//Log out
                ci_error_image.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.logout));
                button.setBackgroundColor(ContextCompat.getColor(context, R.color.BLACK));

                break;


        }
    }


    public void showMeValidateReq(final RequestResult requestResult, final onClickDialogListener callbackDialog, Context context) {

        final onClickDialogListener callback;
        callback = callbackDialog;
        Button btn_accept;
        Button btn_cancel;


        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.alertshow2buttonconfirmreq);
        dialog.getWindow().setWindowAnimations(R.style.CustomDialogAnimation);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCancelable(false);

        btn_accept = (Button) dialog.findViewById(R.id.btn_accept);
        btn_cancel = (Button) dialog.findViewById(R.id.btn_cancel);
        btn_accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                callbackDialog.loaApprovedListener(requestResult);
            }
        });

        try {
            tv_message.setText(requestResult.getResponseDesc());
        } catch (Exception e) {
            e.printStackTrace();
        }

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


        dialog.show();


        int width = (int) (context.getResources().getDisplayMetrics().widthPixels * 0.70);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.width = width;
        dialog.getWindow().setAttributes(lp);

    }

    //used for approval list file upload
    public void showSelectedAttachment(Context context, ArrayList<AttachmentObject> attachmentObjects, int position) {

        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_file_selected);
        dialog.getWindow().setWindowAnimations(R.style.CustomDialogAnimation);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
//        dialog.setCancelable(false);

        TextView tv_no,tv_filename,tv_close;
        ImageView iv_image;
        Button btn_close;
        tv_no = (TextView)dialog.findViewById(R.id.tv_no);
        tv_filename = (TextView)dialog.findViewById(R.id.tv_filename);
        iv_image = (ImageView)dialog.findViewById(R.id.iv_image);
        tv_close = (TextView)dialog.findViewById(R.id.tv_close);

        tv_no.setText(""+(position +1));
        tv_filename.setText(attachmentObjects.get(position).getOriginalFileName());
        byte[] decodedString = Base64.decode(attachmentObjects.get(position).getContent(), Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        iv_image.setImageBitmap(decodedByte);
        tv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();


        int width = (int) (context.getResources().getDisplayMetrics().widthPixels * .90);
        int height = (int) (context.getResources().getDisplayMetrics().widthPixels * .90);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.height = height;
        lp.width = width;
        dialog.getWindow().setAttributes(lp);

    }

    //used for selection of attachments
    public void showSelectedAttachment(Context context, List<Attachment> attachmentObjects, int position, String ms1) {

        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_file_selected);
        dialog.getWindow().setWindowAnimations(R.style.CustomDialogAnimation);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
//        dialog.setCancelable(false);

        TextView tv_no,tv_filename,tv_close;
        ImageView iv_image;
        Button btn_close;
        tv_no = (TextView)dialog.findViewById(R.id.tv_no);
        tv_filename = (TextView)dialog.findViewById(R.id.tv_filename);
        iv_image = (ImageView)dialog.findViewById(R.id.iv_image);
        tv_close = (TextView)dialog.findViewById(R.id.tv_close);

        tv_no.setText(""+(position +1));
        tv_filename.setText(attachmentObjects.get(position).getFileName());
        byte[] decodedString = Base64.decode(attachmentObjects.get(position).getContent(), Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        iv_image.setImageBitmap(decodedByte);
        tv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();


        int width = (int) (context.getResources().getDisplayMetrics().widthPixels * .90);
        int height = (int) (context.getResources().getDisplayMetrics().widthPixels * .90);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.height = height;
        lp.width = width;
        dialog.getWindow().setAttributes(lp);

    }

    //used for request details
    public void showSelectedAttachment(Context context, List<Attachment> attachmentObjects, int position, String ms1, String ms2) {

        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_file_selected);
        dialog.getWindow().setWindowAnimations(R.style.CustomDialogAnimation);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
//        dialog.setCancelable(false);

        TextView tv_no,tv_filename,tv_close;
        ImageView iv_image;
        Button btn_close;
        tv_no = (TextView)dialog.findViewById(R.id.tv_no);
        tv_filename = (TextView)dialog.findViewById(R.id.tv_filename);
        iv_image = (ImageView)dialog.findViewById(R.id.iv_image);
        tv_close = (TextView)dialog.findViewById(R.id.tv_close);

        tv_no.setText(""+(position +1));
        tv_filename.setText(attachmentObjects.get(position).getFileName());
        byte[] decodedString = Base64.decode(attachmentObjects.get(position).getContent(), Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        iv_image.setImageBitmap(decodedByte);
        tv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();


        int width = (int) (context.getResources().getDisplayMetrics().widthPixels * .90);
        int height = (int) (context.getResources().getDisplayMetrics().widthPixels * .90);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.height = height;
        lp.width = width;
        dialog.getWindow().setAttributes(lp);

    }

    public interface onClickDialogListener {

        void onOkPress();

        void onRequestDupliate();

        void loaApprovedListener(RequestResult requestResult);
    }

    public interface OnDialogClickListener {
        void onOkClick();

        void onCancelClick();
    }

    public interface OnCustomDialogClickListener {
        void onOkClick();

        void onViewPdf();
    }

}
