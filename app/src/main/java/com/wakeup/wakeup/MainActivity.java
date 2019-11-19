package com.wakeup.wakeup;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.firebase.ui.auth.util.ExtraConstants;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.annotations.Nullable;
import com.firebase.ui.auth.AuthUI;


public class MainActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN = 100;

    @BindView(R.id.root) View mRootView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        signIn();
        super.onCreate(savedInstanceState);
    }

    public void signIn() {
        startActivityForResult(buildSignInIntent(/*link=*/null), RC_SIGN_IN);
    }

    @NonNull
    public Intent buildSignInIntent(@Nullable String link) {
        AuthUI.SignInIntentBuilder builder = AuthUI.getInstance().createSignInIntentBuilder();

        FirebaseAuth auth = FirebaseAuth.getInstance();

        if (auth.getCurrentUser() != null && auth.getCurrentUser().isAnonymous()) {
            builder.enableAnonymousUsersAutoUpgrade();
        }

        return builder.build();
    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            handleSignInResponse(resultCode, data);
        }
    }

    private void handleSignInResponse(int resultCode, @Nullable Intent data) {
        IdpResponse response = IdpResponse.fromResultIntent(data);

        // Successfully signed in
        if (resultCode == RESULT_OK) {
            startSignedInActivity(response);
            finish();
        } else {
            // Sign in failed
            if (response == null) {
                // User pressed back button
                showSnackbar(R.string.sign_in_cancelled);
                return;
            }

            if (response.getError().getErrorCode() == ErrorCodes.NO_NETWORK) {
                showSnackbar(R.string.no_internet_connection);
                return;
            }

            if (response.getError().getErrorCode() == ErrorCodes.ANONYMOUS_UPGRADE_MERGE_CONFLICT) {
                Intent intent = new Intent(this, AnonymousUpgradeActivity.class).putExtra
                        (ExtraConstants.IDP_RESPONSE, response);
                startActivity(intent);
            }

            if (response.getError().getErrorCode() == ErrorCodes.ERROR_USER_DISABLED) {
                showSnackbar(R.string.account_disabled);
                return;
            }

            showSnackbar(R.string.unknown_error);
            Log.e(TAG, "Sign-in error: ", response.getError());
        }
    }

    private void showSnackbar(@StringRes int errorMessageRes) {
        Snackbar.make(mRootView, errorMessageRes, Snackbar.LENGTH_LONG).show();
    }

    private void startSignedInActivity(@Nullable IdpResponse response) {
//        startActivity(SignedInActivity.createIntent(this, response));
        Intent home = new Intent(this, Home.class);
        startActivity(home);
//        Toast.makeText(this, "SignedIn", Toast.LENGTH_LONG).show();
    }
}
