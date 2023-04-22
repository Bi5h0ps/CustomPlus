package com.comp7506.customplus.UI.custom;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;

import com.comp7506.customplus.R;

public class CustomProgressDialog extends ProgressDialog {
    public CustomProgressDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_progress_dialog);
    }
}
