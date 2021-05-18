package gu_android_1089.mynotes.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import gu_android_1089.mynotes.R;
import gu_android_1089.mynotes.logic.AlertDialogClickListener;

public class AlertDialogRemoveFragment extends BottomSheetDialogFragment {
    private AlertDialogClickListener alertDialogClickListener;

    public  static AlertDialogRemoveFragment newInstance(){
        return new AlertDialogRemoveFragment();
    }

    public void setAlertDialogClickListener (AlertDialogClickListener alertDialogClickListener){
        this.alertDialogClickListener = alertDialogClickListener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.alert_remove_dialog, container, false);
        setCancelable(false);
        view.findViewById(R.id.alert_remove_yes_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if (alertDialogClickListener != null) alertDialogClickListener.onDialogYes();
            }
        });

        view.findViewById(R.id.alert_remove_abort_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        return  view;
    }
}
