package com.example.caroproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

public class ForgotPasswordFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private FirebaseAuth auth;

    public ForgotPasswordFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SecondFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ForgotPasswordFragment newInstance(String param1, String param2) {
        ForgotPasswordFragment fragment = new ForgotPasswordFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        auth = FirebaseAuth.getInstance();

    }

    private Button btnReset, btnSignUpSecond;
    private String myData= "MyPreferences";
    private EditText edtUsername;
    private ProgressBar progressBar;
    private TextView floginNow;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_forgot_password, container, false);
        btnReset=view.findViewById(R.id.btnFResetSecond);
        edtUsername=view.findViewById(R.id.edtFUsernameSecond);
        progressBar = view.findViewById(R.id.FprogressBar);
        floginNow = view.findViewById(R.id.FloginNow);

        floginNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(v);
                navController.navigate(R.id.action_forgotPasswordFragment_to_SignInFragment);
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = edtUsername.getText().toString().trim();

                progressBar.setVisibility(View.VISIBLE);
                btnReset.setVisibility(View.GONE);
                auth.sendPasswordResetEmail(username)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    // Password reset email sent successfully
                                    Toast.makeText(requireContext(), "Password reset email sent.", Toast.LENGTH_SHORT).show();
                                    NavController navController = Navigation.findNavController(v);
                                    navController.navigate(R.id.action_forgotPasswordFragment_to_SignInFragment);
                                } else {
                                    // If the task is not successful, handle the error
                                    Exception exception = task.getException();

                                    if (exception instanceof FirebaseAuthInvalidUserException) {
                                        // If the user does not exist
                                        Toast.makeText(requireContext(), "User with this email does not exist.", Toast.LENGTH_SHORT).show();
                                    } else if (exception instanceof IllegalArgumentException) {
                                        // Handle invalid email address
                                        Toast.makeText(requireContext(), "Invalid email address", Toast.LENGTH_SHORT).show();
                                    }{
                                        // Handle other errors
                                        Toast.makeText(requireContext(), "Password reset email could not be sent. " + exception.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                                btnReset.setVisibility(View.VISIBLE);
                                progressBar.setVisibility(View.GONE);
                            }
                        });
            }
        });
        return view;
    }
}
