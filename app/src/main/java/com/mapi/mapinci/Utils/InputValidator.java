package com.mapi.mapinci.Utils;


import android.widget.Button;
import android.widget.EditText;

import java.util.List;

public class InputValidator {

    public static void enableButtonsWhenNotEmpty(List<EditText> inputs, List<Button> buttons) {
        for (Button buttonToEnable: buttons) {
            if (buttonToEnable != null) {
                for (EditText input : inputs) {
                    if (input != null) {
                        if (input.getText().toString().length() == 0) {
                            buttonToEnable.setEnabled(false);
                        }
                    } else {
                        buttonToEnable.setEnabled(false);
                    }
                }
                buttonToEnable.setEnabled(true);
            } else
                return;
        }
    }
}
