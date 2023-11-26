package com.example.caroproject;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class ChangeUserInfoDialogFragment extends DialogFragment {

    public static final String REQUEST_KEY_DIALOG = "DIALOG_USER_INFO_CHANGE";

    private TextView textView;
    private TextInputLayout firstBox;
    private TextInputLayout secondBox;
    private TextInputLayout thirdBox;
    private TextInputEditText firstBoxText;
    private TextInputEditText secondBoxText;
    private TextInputEditText thirdBoxText;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_user_info_dialog, null);
        builder.setView(view);
        builder.setNegativeButton(R.string.cancel, null);

        textView = view.findViewById(R.id.txtViewName);
        firstBox = view.findViewById(R.id.firstBox);
        secondBox = view.findViewById(R.id.secondBox);
        thirdBox = view.findViewById(R.id.thirdBox);
        firstBoxText = view.findViewById(R.id.firstBoxText);
        secondBoxText = view.findViewById(R.id.secondBoxText);
        thirdBoxText = view.findViewById(R.id.thirdBoxText);

        Bundle args = getArguments();
        String userInfoType = args.getString("UserInfoType");
        textView.setText(userInfoType);
        switch (userInfoType) {
            case UserInfoFragment.USERNAME: {
                firstBox.setHint(R.string.username);
                firstBoxText.setText("USERNAME");
                firstBoxText.setEnabled(false);
                secondBox.setHint(R.string.nickname);
                thirdBoxText.setVisibility(View.GONE);
                builder.setPositiveButton(R.string.save, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        String newNickname = secondBoxText.getText().toString();
                        Bundle result = new Bundle();
                        result.putString(UserInfoFragment.NICKNAME, newNickname);
                        getParentFragmentManager().setFragmentResult(REQUEST_KEY_DIALOG, result);
                    }
                });
                return builder.create();
            }

            case UserInfoFragment.EMAIL: {
                firstBox.setHint(R.string.email);
                firstBoxText.setText("Your email");
                secondBox.setVisibility(View.GONE);
                thirdBoxText.setVisibility(View.GONE);
                builder.setPositiveButton(R.string.save, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String newEmail = firstBoxText.getText().toString();
                        Bundle result = new Bundle();
                        result.putString(UserInfoFragment.EMAIL, newEmail);
                        getParentFragmentManager().setFragmentResult(REQUEST_KEY_DIALOG, result);
                    }
                });
                return builder.create();
            }

            case UserInfoFragment.PHONE: {
                firstBox.setHint(R.string.phone);
                firstBoxText.setText("Your phone number");
                secondBox.setVisibility(View.GONE);
                thirdBoxText.setVisibility(View.GONE);
                builder.setPositiveButton(R.string.save, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String newPhone = firstBoxText.getText().toString();
                        Bundle result = new Bundle();
                        result.putString(UserInfoFragment.PHONE, newPhone);
                        getParentFragmentManager().setFragmentResult(REQUEST_KEY_DIALOG, result);
                    }
                });
                return builder.create();
            }

            case UserInfoFragment.PASSWORD: {
                firstBox.setHint("Old Password");
                firstBoxText.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD | InputType.TYPE_CLASS_TEXT);
                secondBox.setHint("New Password");
                secondBoxText.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD | InputType.TYPE_CLASS_TEXT);
                thirdBox.setHint("Retype");
                thirdBoxText.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD | InputType.TYPE_CLASS_TEXT);
                builder.setPositiveButton(R.string.save, null);
                AlertDialog dialog = builder.create();
                dialog.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface dialogInterface) {
                        ((AlertDialog) dialogInterface).getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String pass1 = secondBoxText.getText().toString();
                                String pass2 = thirdBoxText.getText().toString();
                                String oldPass = firstBoxText.getText().toString();

                                if(!oldPass.equals(null)) {
                                    firstBox.setError("Wrong password");
                                } else {
                                    if (pass1.equals(pass2)) {
                                        String newPass = secondBoxText.getText().toString();
                                        Bundle result = new Bundle();
                                        result.putString(UserInfoFragment.PASSWORD, newPass);
                                        getParentFragmentManager().setFragmentResult(REQUEST_KEY_DIALOG, result);
                                        dialog.dismiss();
                                    } else {
                                        thirdBox.setError("Password is not equal");
                                    }
                                }
                            }
                        });
                    }
                });
                return dialog;
            }
        }



        return builder.create();
    }

}
