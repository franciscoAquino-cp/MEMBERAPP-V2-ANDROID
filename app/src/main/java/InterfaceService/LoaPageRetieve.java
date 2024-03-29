package InterfaceService;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import com.medicard.member.R;

import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import mehdi.sakout.fancybuttons.FancyButton;
import okhttp3.ResponseBody;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import services.App;
import services.AppInterface;
import services.AppService;
import utilities.AlertDialogCustom;
import utilities.Constant;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

/**
 * Created by mpx-pawpaw on 3/6/17.
 */

public class LoaPageRetieve {

    private Context context;
    private LoaPageInterface callback;
    private Dialog dialog;

    public LoaPageRetieve(Context context, LoaPageInterface callback) {
        this.context = context;
        this.callback = callback;
    }


    public void cancelRequest(String requestCode) {

        AppInterface appInterface;
        appInterface = AppService.createApiService(AppInterface.class, AppInterface.ENDPOINT);
        appInterface.setRequestCancel(requestCode)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Subscriber<ResponseBody>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                        if (e.getMessage().contains("failed to connect to"))
                            callback.noInternet();
                        else
                            callback.onError(e.getMessage());


                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        callback.onSuccess();
                    }
                });

    }


    public int setToLoadList(boolean b) {
        return b ? RESULT_OK : RESULT_CANCELED;
    }

//    public void setCancelButton(String remarks, FancyButton btn_cancel_req) {
//
//        Log.d("REMARKS", remarks);
//        if (remarks.contains("CANCELLED"))
//            btn_cancel_req.setVisibility(View.GONE);
//        else
//            btn_cancel_req.setVisibility(View.VISIBLE);
//    }

    public void showCancelConfirmation(final String requestCode) {


        FancyButton cancel;
        FancyButton proceed;

        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_select_cancel);

        cancel = (FancyButton) dialog.findViewById(R.id.cancel);
        proceed = (FancyButton) dialog.findViewById(R.id.proceed);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog.dismiss();
            }
        });
        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callback.onCancelRequestListener();
                dialog.dismiss();
            }
        });


        dialog.show();

        DisplayMetrics metrics = this.context.getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        dialog.getWindow().setLayout(width, Toolbar.LayoutParams.WRAP_CONTENT);


    }

    public void setExpiredStatus(FancyButton btn_download, FancyButton btn_cancel_req, String status) {

        if (status.equals("EXPIRED") || status.equals(Constant.CANCELLED) || status.equals("AVAILED") ) {
            btn_cancel_req.setVisibility(View.GONE);
            btn_download.setVisibility(View.GONE);
        } else {
            btn_cancel_req.setVisibility(View.VISIBLE);
            btn_download.setVisibility(View.VISIBLE);
        }
    }

    public String getStatus(String status) {
        String data = "";

        if (status.contains("OUTSTANDING"))
            data = "REQUEST SUBMITTED";
        //TODO To be changed in time
        else if(status.equalsIgnoreCase("APPROVED"))
            data = "REQUEST PENDING";


        return data;
    }


}
