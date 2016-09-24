package br.com.eventoseartigos.sefd.activity;

import android.annotation.TargetApi;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.ContentResolver;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import br.com.eventoseartigos.sefd.R;
import br.com.eventoseartigos.sefd.annotation.Transacao;
import br.com.eventoseartigos.sefd.converter.LoginConverter;
import br.com.eventoseartigos.sefd.converter.UsuarioConverter;
import br.com.eventoseartigos.sefd.dao.Prefs;
import br.com.eventoseartigos.sefd.model.Login;
import br.com.eventoseartigos.sefd.model.Usuario;
import br.com.eventoseartigos.sefd.service.LoginService;
import br.com.eventoseartigos.sefd.validator.LoginValidator;

import static android.Manifest.permission.READ_CONTACTS;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class LoginActivity extends BaseActivity implements LoaderCallbacks<Cursor>, Transacao {
    //Id to identity READ_CONTACTS permission request.
    private static final int REQUEST_READ_CONTACTS = 0;

    // UI references.
    private AutoCompleteTextView campo_email;
    private EditText campo_password;
    private String token = null;
    private String messageErrorLoginInvalid = null;
    private Login mLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String tokenPrefs = Prefs.getString(this, Login.TOKEN);
        //if (!tokenPrefs.equals("")) {
        if (!TextUtils.isEmpty(tokenPrefs)) {
            Intent intent = new Intent(this, MainActivity.class);
            //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.putExtra(Login.TOKEN, tokenPrefs);
            startActivity(intent);
            finish();
        }
        
        setContentView(R.layout.activity_login);
        // Set up the login form.
        campo_email = (AutoCompleteTextView) findViewById(R.id.et_email);
        populateAutoComplete();

        campo_password = (EditText) findViewById(R.id.et_password);
        campo_password.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        findViewById(R.id.email_sign_in_button).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        findViewById(R.id.tv_criar_conta).setOnClickListener(onClickCriarConta());

        setFormView(findViewById(R.id.login_form));
        setProgress(findViewById(R.id.login_progress));
    }

    private OnClickListener onClickCriarConta() {
        return new OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, UsuarioFormActivity.class));
            }
        };
    }

    private void populateAutoComplete() {
        if (!mayRequestContacts()) {
            return;
        }

        if (VERSION.SDK_INT >= 14) {
            // Use ContactsContract.Profile (API 14+)
            getLoaderManager().initLoader(0, null, this);
        } else if (VERSION.SDK_INT >= 8) {
            // Use AccountManager (API 8+)
            new SetupEmailAutoCompleteTask().execute(null, null);
        }
    }

    private boolean mayRequestContacts() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if (checkSelfPermission(READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        if (shouldShowRequestPermissionRationale(READ_CONTACTS)) {
            Snackbar.make(campo_email, R.string.permission_rationale, Snackbar.LENGTH_INDEFINITE)
                    .setAction(android.R.string.ok, new View.OnClickListener() {
                        @Override
                        @TargetApi(Build.VERSION_CODES.M)
                        public void onClick(View v) {
                            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
                        }
                    });
        } else {
            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
        }
        return false;
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_READ_CONTACTS) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                populateAutoComplete();
            }
        }
    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        if (!validator()) {
            startTrasacao(this);
        }
    }

    private boolean isEmailValid(String email) {
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 5;
    }

    private boolean validator() {
        campo_email.setError(null);
        campo_password.setError(null);
        boolean email_valido_not_null = LoginValidator.validateNotNull(campo_email, getString(R.string.error_field_required));
        if (!email_valido_not_null) {
            return true;
        }
        boolean email_valido = LoginValidator.valitadeEmail(campo_email.getText().toString());
        if (!email_valido) {
            campo_email.setError(getString(R.string.error_invalid_email));
            campo_email.setFocusable(true);
            campo_email.requestFocus();
            return true;
        }
        boolean password_valido_not_null = LoginValidator.validateNotNull(campo_password, getString(R.string.error_field_required));
        if (!password_valido_not_null) {
            return true;
        }
        boolean password_valido = LoginValidator.validatePassword(campo_password.getText().toString());
        if (!password_valido) {
            campo_password.setError(getString(R.string.error_invalid_password));
            campo_password.setFocusable(true);
            campo_password.requestFocus();
            return true;
        }
        return false;
    }


    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(this,
                // Retrieve data rows for the device user's 'profile' contact.
                Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
                        ContactsContract.Contacts.Data.CONTENT_DIRECTORY), ProfileQuery.PROJECTION,

                // Select only email addresses.
                ContactsContract.Contacts.Data.MIMETYPE +
                        " = ?", new String[]{ContactsContract.CommonDataKinds.Email
                .CONTENT_ITEM_TYPE},

                // Show primary email addresses first. Note that there won't be
                // a primary email address if the user hasn't specified one.
                ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        List<String> emails = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            emails.add(cursor.getString(ProfileQuery.ADDRESS));
            cursor.moveToNext();
        }

        addEmailsToAutoComplete(emails);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {

    }

    private void addEmailsToAutoComplete(List<String> emailAddressCollection) {
        //Create adapter to tell the AutoCompleteTextView what to show in its dropdown list.
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(LoginActivity.this,
                        android.R.layout.simple_dropdown_item_1line, emailAddressCollection);

        campo_email.setAdapter(adapter);
    }

    @Override
    public void executar() throws Exception {
        try {
            String json = null;
            mLogin = new Login();
            mLogin.setUserName(campo_email.getText().toString());
            mLogin.setPassword(campo_password.getText().toString());

            json = LoginService.getLogin(mLogin);
            if (json != null) {
                if (json.equals(LoginValidator.LOGIN_INVALIDO)) {
                    messageErrorLoginInvalid = LoginConverter.converteMessageErrorParaString(json);
                    Prefs.setString(this, Login.TOKEN, null);
                } else {
                    token = LoginConverter.converteTokenParaString(json);
                    Prefs.setString(this, Login.TOKEN, token);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void atualizarView() {
        if (token == null) {
            Toast.makeText(this, messageErrorLoginInvalid, Toast.LENGTH_SHORT).show();
        } else {
            mLogin.setToken(token);
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra(Login.KEY, mLogin);
            startActivity(intent);
            finish();
        }
    }

    private interface ProfileQuery {
        String[] PROJECTION = {
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
        };

        int ADDRESS = 0;
        int IS_PRIMARY = 1;
    }

    /**
     * Use an AsyncTask to fetch the user's email addresses on a background thread, and update
     * the email text field with results on the main UI thread.
     */
    class SetupEmailAutoCompleteTask extends AsyncTask<Void, Void, List<String>> {

        @Override
        protected List<String> doInBackground(Void... voids) {
            ArrayList<String> emailAddressCollection = new ArrayList<>();

            // Get all emails from the user's contacts and copy them to a list.
            ContentResolver cr = getContentResolver();
            Cursor emailCur = cr.query(ContactsContract.CommonDataKinds.Email.CONTENT_URI, null,
                    null, null, null);
            while (emailCur.moveToNext()) {
                String email = emailCur.getString(emailCur.getColumnIndex(ContactsContract
                        .CommonDataKinds.Email.DATA));
                emailAddressCollection.add(email);
            }
            emailCur.close();

            return emailAddressCollection;
        }

        @Override
        protected void onPostExecute(List<String> emailAddressCollection) {
            addEmailsToAutoComplete(emailAddressCollection);
        }
    }


}

