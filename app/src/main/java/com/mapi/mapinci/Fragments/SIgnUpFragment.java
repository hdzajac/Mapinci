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

public class SignUpFragment extends Fragment implements View.OnClickListener{
    
    private Button signUpButton;
    private Button goToLoginButton;
    private EditText inputEmail;
    private EditText inputPassword;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        LinearLayout fragment_view = new LinearLayout(getActivity());
        fragment_view.setOrientation(LinearLayout.VERTICAL);
        fragment_view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));


        //input fields email + pass
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

        // add Sign up Button
        signUpButton = new Button(getActivity());
        signUpButton.setText(R.string.button_signup);
        signUpButton.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        signUpButton.setOnClickListener(this);
        signUpButton.setEnabled(false);
        signUpButton.setId(R.id.button_signup_fragment_signup);
        fragment_view.addView(signUpButton);

        // text + button to login fragment
        TextView goToLoginMessage = new TextView(getActivity());
        goToLoginMessage.setText(R.string.message_to_login);
        goToLoginMessage.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        fragment_view.addView(goToLoginMessage);

        goToLoginButton = new Button(getActivity());
        goToLoginButton.setText(R.string.button_login);
        goToLoginButton.setVisibility(View.VISIBLE);
        goToLoginButton.setBackgroundColor(Color.TRANSPARENT);
        goToLoginButton.setOnClickListener(this);
        goToLoginButton.setId(R.id.button_login_fragment_signup);
        fragment_view.addView(goToLoginButton);

        return fragment_view;
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.button_login_fragment_signup:
                Log.i("Login Fragment", "%%%%%%%%%%%%%%%%%%\nLoged IN\n%%%%%%%%%%%%%%");
                // initializing fragment that will replace the old one
                LoginFragment loginFragment = new LoginFragment();
                //if there was something
                loginFragment.setArguments(getActivity().getIntent().getExtras());

                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_start_container, loginFragment );
                transaction.commit();
                break;

            case R.id.button_signup_fragment_signup:
                Log.i("Login Fragment", "%%%%%%%%%%%%%%%%%%\nSigned UP\n%%%%%%%%%%%%%%");
                break;

            default:
                break;
        }
    }

    private TextWatcher mTextWatcher = new TextWatcher() {
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
            lb.add(signUpButton);
            InputValidator.enableButtonsWhenNotEmpty(l, lb);
        }
    };
    
}
