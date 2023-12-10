package com.example.caroproject;

import static android.database.sqlite.SQLiteDatabase.openDatabase;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.caroproject.Adapter.FirebaseHelper;
import com.example.caroproject.Data.UserInfo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
//import com.example.caroproject.databinding

public class SignUpFragment extends Fragment {


    private FirebaseAuth auth;

    public SignUpFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth = FirebaseAuth.getInstance();

    }

    private Button btnReset, btnSignUpSecond;
    private String myData= "MyPreferences";
    private EditText edtPassword, edtRetype, edtEmail;
    private TextView loginNow;
    private ProgressBar progressBar;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        btnReset=view.findViewById(R.id.btnResetSecond);
        btnSignUpSecond=view.findViewById(R.id.btnSignUpSecond);
        edtPassword=view.findViewById(R.id.edtPasswordSecond);
        edtEmail = view.findViewById(R.id.edtEmail);
        edtRetype=view.findViewById(R.id.edtRetypeSecond);
        progressBar = view.findViewById(R.id.progressBar);
        loginNow = view.findViewById(R.id.loginNow);
        loginNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(v);
                navController.navigate(R.id.action_signUpFragment_to_signInFragment);
            }
        });
        btnSignUpSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edtEmail.getText().toString().trim();
                String password = edtPassword.getText().toString().trim();
                String retypePassword = edtRetype.getText().toString().trim();
                progressBar.setVisibility(View.VISIBLE);
                if(email.equals("")||password.equals("")||retypePassword.equals("")) {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(requireContext(), "All fields are mandatory", Toast.LENGTH_SHORT).show();
                } else{
                    if(password.equals(retypePassword)){
                        auth.createUserWithEmailAndPassword(email,password)
                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        progressBar.setVisibility(View.GONE);
                                        if (task.isSuccessful()) {
                                            //Add information of new user to firestore
                                            FirebaseUser user = task.getResult().getUser();
                                            UserInfo newUser = new UserInfo(user);
                                            FirebaseHelper.getInstance().addDataToDatabase("UserInfo", user.getUid(), newUser);

                                            // Sign in success, update UI with the signed-in user's information
                                            NavController navController = Navigation.findNavController(v);
                                            navController.navigate(R.id.action_signUpFragment_to_signInFragment);
                                        } else {
                                            // If sign in fails, display a message to the user.
                                            if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                                // If the email is already in use, handle it accordingly
                                                Toast.makeText(requireContext(), "Email address is already in use.",
                                                        Toast.LENGTH_SHORT).show();
                                            } else if (task.getException() instanceof FirebaseAuthWeakPasswordException) {
                                                // If the password is too weak, handle it accordingly
                                                Toast.makeText(requireContext(), "Password must be at least 6 characters",
                                                        Toast.LENGTH_SHORT).show();
                                            } else {
                                                Toast.makeText(requireContext(), "Account creation failed! Please try again! " + task.getException().getMessage(),
                                                        Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    }
                                });
                    }else{
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(requireContext(), "Password do not match !", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtEmail.setText("");
                edtPassword.setText("");
                edtRetype.setText("");
            }
        });
        return view;
    }
}