package gu_android_1089.mynotes.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import gu_android_1089.mynotes.R;
import gu_android_1089.mynotes.logic.SignInClickListener;

public class SignInFragment extends Fragment implements SignInClickListener {

    private static final int SIGN_IN_KEY = 1;

    private GoogleSignInClient client;
    private SignInClickListener signInClickListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof SignInClickListener) {
            signInClickListener = (SignInClickListener) context;
        }
    }

    @Override
    public void onDetach() {
        signInClickListener = null;
        super.onDetach();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        GoogleSignInOptions googleOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        client = GoogleSignIn.getClient(requireContext(), googleOptions);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_app_sign_in, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.sign_in).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = client.getSignInIntent();
                startActivityForResult(intent, SIGN_IN_KEY);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable  Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == SIGN_IN_KEY){
            Task<GoogleSignInAccount> authResult = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = authResult.getResult(ApiException.class);
                assert account != null;
                signInClickListener(new MainListFragment(), account.getDisplayName(), account.getEmail());

            } catch (ApiException e) {
                Toast.makeText(requireContext(), "Authorization failed " + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }
    }

    @Override
    public void signInClickListener(Fragment fragment, String name, String email) {
        if (signInClickListener != null){
            signInClickListener.signInClickListener(fragment, name, email);
        }
    }
}
