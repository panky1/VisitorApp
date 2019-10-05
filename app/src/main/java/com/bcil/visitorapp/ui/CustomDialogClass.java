package com.bcil.visitorapp.ui;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.bcil.visitorapp.R;
import com.bcil.visitorapp.utils.AppConstants;
import com.bcil.visitorapp.utils.Validation;
import com.rengwuxian.materialedittext.MaterialEditText;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CustomDialogClass extends Dialog {

    public Activity c;
    @Bind(R.id.etCName)
    MaterialEditText etCName;

    public CustomDialogClass(Activity a) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_dialog);
        ButterKnife.bind(this);
        etCName.requestFocus();

    }

    @Override
    protected void onStart() {
        super.onStart();
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams wmlp = getWindow().getAttributes();
        wmlp.gravity = Gravity.TOP;

    }

    @OnClick(R.id.btSave)
    public void onViewClicked() {
        if (checkValidation()) {
            AppConstants.NAME = etCName.getText().toString().trim();
            dismiss();
        }else {
            Toast.makeText(c, "Please enter the required fields", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean checkValidation() {
        boolean ret = true;
        if (!Validation.hasText(etCName)) ret = false;
        return ret;
    }
}
