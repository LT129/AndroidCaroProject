package com.example.caroproject;

import static android.database.sqlite.SQLiteDatabase.openDatabase;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
//import com.example.caroproject.databinding
/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SignUpFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SignUpFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SignUpFragment() {
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
    public static SignUpFragment newInstance(String param1, String param2) {
        SignUpFragment fragment = new SignUpFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    SignUpFragment binding;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


    }

    private Button btnReset, btnSignUpSecond;
    private String myData= "MyPreferences";
    private EditText edtUsername, edtPassword, edtRetype;

    com.example.caroproject.MyDatabaseHelper myDatabaseHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        btnReset=view.findViewById(R.id.btnResetSecond);
        btnSignUpSecond=view.findViewById(R.id.btnSignUpSecond);
        edtPassword=view.findViewById(R.id.edtPasswordSecond);
        edtRetype=view.findViewById(R.id.edtRetypeSecond);
        edtUsername=view.findViewById(R.id.edtUsernameSecond);
        myDatabaseHelper = new com.example.caroproject.MyDatabaseHelper(requireContext());
        btnSignUpSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = edtUsername.getText().toString().trim();
                String password = edtPassword.getText().toString().trim();
                String retypePassword = edtRetype.getText().toString().trim();
                if(username.equals("")||password.equals("")||retypePassword.equals(""))
                    Toast.makeText(requireContext(), "All fields are mandatory", Toast.LENGTH_SHORT).show();
                else{
                    if(password.equals(retypePassword)){
                        Boolean checkUsername =  myDatabaseHelper.checkUsername(username);
                        if(checkUsername == false){
                            Boolean insert = myDatabaseHelper.UpgradePlayerInfo(username, password);
                            if(insert == true){
                                Toast.makeText(requireContext(), "Signup Successfully!", Toast.LENGTH_SHORT).show();
                                NavController navController = Navigation.findNavController(v);
                                navController.navigate(R.id.action_secondFragment_to_gameModeFragment);
                            }else{
                                Toast.makeText(requireContext(), "Signup Failed!", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else{
                            Toast.makeText(requireContext(), "User already exists! Please login", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(requireContext(), "Invalid Password!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtUsername.setText("");
                edtPassword.setText("");
                edtRetype.setText("");
            }
        });
        return view;
    }
}