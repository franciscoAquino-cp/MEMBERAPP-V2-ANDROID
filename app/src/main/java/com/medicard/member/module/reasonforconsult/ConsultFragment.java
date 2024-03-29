package com.medicard.member.module.reasonforconsult;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.medicard.member.R;
import com.medicard.member.core.constant.NewTest;
import com.medicard.member.core.session.ConsultSession;
import com.medicard.member.module.base.BaseFragment;
import com.medicard.member.module.newprocedure.NewTestMvp;

import butterknife.BindView;
import butterknife.OnClick;
import utilities.ViewUtilities;


public class ConsultFragment extends BaseFragment
        implements ConsultMvp.View {


    @BindView(R.id.etReasonForConsult) EditText etReasonForConsult;
    @BindView(R.id.btnProceed) Button btnProceed;

    private ConsultMvp.Presenter presenter;
    private NewTestMvp.View navigator;

    public static ConsultFragment newInstance() {
        
        Bundle args = new Bundle();
        
        ConsultFragment fragment = new ConsultFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public ConsultFragment() {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof NewTestMvp.View) {
            navigator = (NewTestMvp.View) context;
        }
    }

    @Override
    public int getLayoutResource() {
        return R.layout.fragment_consult;
    }

    @Override
    protected void initComponents(View view, Bundle savedInstanceState) {
        super.initComponents(view, savedInstanceState);
        presenter = new ConsultPresenter(context);
        presenter.attachView(this);

        etReasonForConsult.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                return (event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER);
            }
        });

    }

    @OnClick(R.id.btnProceed)
    public void onProceedClick() {
        System.out.println("Proceed");
        presenter.validateReason(etReasonForConsult.getText().toString());
    }

    @Override
    public void proceedSuccess() {
        String reasonForConsult = ViewUtilities.getEditValue(etReasonForConsult);
        NewTest.addReasonForConsult(reasonForConsult);
        ConsultSession.setReasonForConsult(reasonForConsult);
        navigator.proceedDoctorActivity();
    }

    @Override
    public void proceedError(String message) {
        etReasonForConsult.requestFocus();
        etReasonForConsult.setError(message);
    }
}
