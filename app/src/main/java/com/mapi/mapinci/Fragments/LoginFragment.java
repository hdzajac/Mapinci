package com.mapi.mapinci.Fragments;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mapi.mapinci.R;
import com.mapi.mapinci.Utils.InputValidator;

import java.util.LinkedList;
import java.util.List;

import static android.text.InputType.TYPE_CLASS_TEXT;
import static android.text.InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS;

public class LoginFragment extends Fragment implements View.OnClickListener{

    private Button loginButton;
    private Button goToSignUpButton;
    private EditText inputEmail;
    private EditText inputPassword;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        LinearLayout fragment_view = new LinearLayout(getActivity());
        fragment_view.setOrientation(LinearLayout.VERTICAL);
        fragment_view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));


        //input fields login + pass
        inputEmail = new EditText(getActivity());
        inputEmail.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT ));
        inputEmail.setHint(R.string.input_login);
        inputEmail.addTextChangedListener(mTextWatcher);
        inputEmail.setInputType(TYPE_CLASS_TEXT | TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        fragment_view.addView(inputEmail);

        inputPassword = new EditText(getActivity());
        inputPassword.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT ));
        inputPassword.setHint(R.string.input_password);
        inputPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
        inputPassword.addTextChangedListener(mTextWatcher);
        fragment_view.addView(inputPassword);

        // add Login Button
        loginButton = new Button(getActivity());
        loginButton.setText(R.string.button_login);
        loginButton.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        loginButton.setOnClickListener(this);
        loginButton.setEnabled(false);
        loginButton.setId(R.id.button_login_fragment_login);
        fragment_view.addView(loginButton);

        // text + button to sign up fragment
        TextView goToSignUpMessage = new TextView(getActivity());
        goToSignUpMessage.setText(R.string.message_to_signup);
        goToSignUpMessage.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        fragment_view.addView(goToSignUpMessage);

        goToSignUpButton = new Button(getActivity());
        goToSignUpButton.setText(R.string.button_signup);
        goToSignUpButton.setVisibility(View.VISIBLE);
        goToSignUpButton.setBackgroundColor(Color.TRANSPARENT);
        goToSignUpButton.setOnClickListener(this);
        goToSignUpButton.setId(R.id.button_signup_fragment_login);
        fragment_view.addView(goToSignUpButton);

        return fragment_view;
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.button_login_fragment_login:
                Log.i("Login Fragment", "%%%%%%%%%%%%%%%%%%\nLoged IN\n%%%%%%%%%%%%%%");
                break;

            case R.id.button_signup_fragment_login:
                Log.i("Login Fragment", "%%%%%%%%%%%%%%%%%%\nSigned UP\n%%%%%%%%%%%%%%");
                // initializing fragment that will replace the old one
                SignUpFragment signUpFragment = new SignUpFragment();
                //if there was something
                signUpFragment.setArguments(getActivity().getIntent().getExtras());

                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_start_container, signUpFragment );
                transaction.commit();
                break;

            default:
                break;
        }
    }

    private TextWatcher mTextWatcher  = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        @Override
        public void afterTextChanged(Editable editable) {
            List<EditText> l = new LinkedList<>();
            l.add(inputEmail);
            l.add(inputPassword);
            List<Button> lb = new LinkedList<>();
            lb.add(loginButton);
            InputValidator.enableButtonsWhenNotEmpty(l, lb);
        }
    };



}


