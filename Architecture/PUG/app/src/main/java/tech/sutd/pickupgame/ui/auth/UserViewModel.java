package tech.sutd.pickupgame.ui.auth;

import android.app.Application;
import android.content.Context;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.navigation.NavController;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import java.util.List;

import javax.inject.Inject;

import tech.sutd.pickupgame.SessionManager;
import tech.sutd.pickupgame.data.UserRepository;
import tech.sutd.pickupgame.databinding.FragmentLoginBinding;
import tech.sutd.pickupgame.databinding.FragmentRegisterBinding;
import tech.sutd.pickupgame.models.User;
import tech.sutd.pickupgame.ui.auth.login.LoginFragment;

public class UserViewModel extends ViewModel {

    // TODO: Rmb to implement Visitor Pattern in a recycleradapter

    private SessionManager sessionManager;
    private UserRepository repository;
    private DatabaseReference reff;

    private LiveData<List<User>> users;

    @Inject
    public UserViewModel(@NonNull Application application, SessionManager sessionManager, DatabaseReference reff) {
        this.sessionManager = sessionManager;
        this.repository = new UserRepository(application);
        users = repository.getAllUsers();
        this.reff = reff;
    }

    public void register(Context context, NavController navController, FragmentRegisterBinding binding, User user) {
        repository.update(user);
        sessionManager.register(context, navController, reff, user);
        binding.progress.setVisibility(View.GONE);
    }

    public void login(LoginFragment fragment, Context context, FragmentLoginBinding binding, User user) {
        repository.update(user);
        sessionManager.login(fragment, context, user);
        binding.progress.setVisibility(View.GONE);
    }

    // Remember to reset the user details to -1
    public void logout(Context context, User user) {
        repository.update(user);
    }

    public void update(User user) {
        repository.update(user);
    }

    public void deleteAllUsers() {
        repository.deleteAllUsers();
    }

    public LiveData<List<User>> getUsers() {
        return users;
    }

}
