package tech.sutd.pickupgame.ui.auth.login;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;
import tech.sutd.pickupgame.R;
import tech.sutd.pickupgame.databinding.FragmentLoginBinding;
import tech.sutd.pickupgame.models.User;
import tech.sutd.pickupgame.ui.auth.UserViewModel;
import tech.sutd.pickupgame.viewmodels.ViewModelProviderFactory;

public class LoginFragment extends DaggerFragment implements View.OnClickListener {

    private FragmentLoginBinding binding;
    private NavController navController;

    private UserViewModel viewModel;

    @Inject
    FirebaseAuth firebaseAuth;

    @Inject
    ViewModelProviderFactory providerFactory;

    @Inject
    User user;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        binding.signUp.setOnClickListener(this);
        binding.login.setOnClickListener(this);

        viewModel = new ViewModelProvider(this, providerFactory).get(UserViewModel.class);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.signUp:
                navController.navigate(R.id.action_loginFragment_to_registerFragment);
                break;
            case R.id.login:
                login();
        }
    }

    private void login() {
        String email = String.valueOf(binding.userId.getText()).trim();
        String passwd = String.valueOf(binding.passwd.getText()).trim();

        if (TextUtils.isEmpty(email)) {
            binding.userId.setError("Email is Required");
            return;
        }

        if (TextUtils.isEmpty(passwd)) {
            binding.passwd.setError("Password is Required");
            return;
        }

        user.setEmail(email);
        user.setPasswd(passwd);

        binding.progress.setVisibility(View.VISIBLE);
        viewModel.login(getContext(), binding);
    }
}