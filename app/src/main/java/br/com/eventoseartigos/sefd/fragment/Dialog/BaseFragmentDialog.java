package br.com.eventoseartigos.sefd.fragment.Dialog;

import android.support.v4.app.DialogFragment;
import android.view.Window;

/**
 * Created by gilmar on 30/12/16.
 */

public class BaseFragmentDialog extends DialogFragment {

    @Override
    public void onStart() {
        super.onStart();
        if (getDialog() == null) {
            return;
        }

        Window window = getDialog().getWindow();
        window.setBackgroundDrawableResource(android.R.color.white);
    }
}
