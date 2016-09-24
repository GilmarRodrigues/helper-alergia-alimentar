package br.com.eventoseartigos.sefd.validator;

import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

/**
 * Created by gilmar on 18/09/16.
 */
public abstract class Validator {

    public static boolean validateNotNull(View view, String message) {
        if (view instanceof EditText) {
            EditText edText = (EditText) view;
            Editable text = edText.getText();
            if (text != null) {
                String strText = text.toString();
                if (!TextUtils.isEmpty(strText)) {
                    return true;
                }
            }
            edText.setError(message);
            edText.setFocusable(true);
            edText.requestFocus();
        }
        return false;
    }
}
