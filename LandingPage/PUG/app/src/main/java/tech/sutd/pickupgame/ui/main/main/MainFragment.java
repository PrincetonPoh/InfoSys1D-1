package tech.sutd.pickupgame.ui.main.main;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.RequestManager;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;
import tech.sutd.pickupgame.R;
import tech.sutd.pickupgame.databinding.FragmentMainBinding;
import tech.sutd.pickupgame.models.ui.NewActivity;
import tech.sutd.pickupgame.models.ui.UpcomingActivity;
import tech.sutd.pickupgame.ui.main.main.adapter.NewActivityAdapter;
import tech.sutd.pickupgame.ui.main.main.adapter.UpcomingActivityAdapter;
import tech.sutd.pickupgame.ui.main.main.viewmodel.NewActViewModel;
import tech.sutd.pickupgame.ui.main.main.viewmodel.UpcomingActViewModel;
import tech.sutd.pickupgame.viewmodels.ViewModelProviderFactory;

public class MainFragment extends DaggerFragment implements View.OnClickListener {

    private FragmentMainBinding binding;
    private NavController navController;

    private UpcomingActViewModel upcomingActViewModel;
    private NewActViewModel newActViewModel;

    @Inject
    UpcomingActivityAdapter<UpcomingActivity> adapter;

    @Inject
    NewActivityAdapter<NewActivity> newAdapter;

    @Inject
    ViewModelProviderFactory providerFactory;

    @Inject
    RequestManager requestManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentMainBinding.inflate(inflater, container, false);

        upcomingActViewModel = new ViewModelProvider(this, providerFactory).get(UpcomingActViewModel.class);
        newActViewModel = new ViewModelProvider(this, providerFactory).get(NewActViewModel.class);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
    }

    @Override
    public void onStart() {
        super.onStart();
        initViews();
        subscribeObserver();
    }

    private void subscribeObserver() {
        upcomingActViewModel.getUpcomingActivitiesByClock2().observe(getViewLifecycleOwner(), upcomingActivities ->
            adapter.submitList(upcomingActivities));

        newActViewModel.getNewActivitiesByClock2().observe(getViewLifecycleOwner(), newActivities ->
            newAdapter.submitList(newActivities));
    }

    private void initViews() {

        upcomingActViewModel.insert(new UpcomingActivity(0, "Cycling", R.drawable.ic_clock,
                String.valueOf(Long.valueOf(1600340400) * 1000), String.valueOf(Long.valueOf(1600351200) * 1000),
                R.drawable.ic_location, "S123456, East Coast Park", R.drawable.ic_profile, "John Doe", R.drawable.ic_cycling));
        upcomingActViewModel.insert(new UpcomingActivity(1, "Cycling", R.drawable.ic_clock,
                String.valueOf(Long.valueOf(1600254000) * 1000), String.valueOf(Long.valueOf(1600264800) * 1000),
                R.drawable.ic_location, "S123456, East Coast Park", R.drawable.ic_profile, "John Doe", R.drawable.ic_cycling));
        upcomingActViewModel.insert(new UpcomingActivity(2, "Cycling", R.drawable.ic_clock,
                String.valueOf(Long.valueOf(1600167600) * 1000), String.valueOf(Long.valueOf(1600178400) * 1000),
                R.drawable.ic_location, "S123456, East Coast Park", R.drawable.ic_profile, "John Doe", R.drawable.ic_cycling));
        upcomingActViewModel.insert(new UpcomingActivity(3, "Cycling", R.drawable.ic_clock,
                String.valueOf(Long.valueOf(1600081200) * 1000), String.valueOf(Long.valueOf(1600092000) * 1000),
                R.drawable.ic_location, "S123456, East Coast Park", R.drawable.ic_profile, "John Doe", R.drawable.ic_cycling));

        binding.upcomingRc.setAdapter(adapter);
        binding.upcomingRc.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.upcomingRc.setHasFixedSize(true);
        adapter.setNotifications(requestManager, 1);


        newActViewModel.insert(new NewActivity(0, "Cycling", R.drawable.ic_clock,
                String.valueOf(Long.valueOf(1600340400) * 1000), String.valueOf(Long.valueOf(1600351200) * 1000),
                R.drawable.ic_location, "S123456, East Coast Park", R.drawable.ic_profile, "John Doe", R.drawable.ic_cycling));
        newActViewModel.insert(new NewActivity(1, "Cycling", R.drawable.ic_clock,
                String.valueOf(Long.valueOf(1600254000) * 1000), String.valueOf(Long.valueOf(1600264800) * 1000),
                R.drawable.ic_location, "S123456, East Coast Park", R.drawable.ic_profile, "John Doe", R.drawable.ic_cycling));
        newActViewModel.insert(new NewActivity(2, "Cycling", R.drawable.ic_clock,
                String.valueOf(Long.valueOf(1600167600) * 1000), String.valueOf(Long.valueOf(1600178400) * 1000),
                R.drawable.ic_location, "S123456, East Coast Park", R.drawable.ic_profile, "John Doe", R.drawable.ic_cycling));
        newActViewModel.insert(new NewActivity(3, "Cycling", R.drawable.ic_clock,
                String.valueOf(Long.valueOf(1600081200) * 1000), String.valueOf(Long.valueOf(1600092000) * 1000),
                R.drawable.ic_location, "S123456, East Coast Park", R.drawable.ic_profile, "John Doe", R.drawable.ic_cycling));

        binding.newRc.setAdapter(newAdapter);
        binding.newRc.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.newRc.setHasFixedSize(true);
        newAdapter.setNotifications(requestManager, 1);

        binding.upcomingAct.setOnClickListener(this);
        binding.newAct.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.upcoming_act:
                navController.navigate(R.id.action_mainFragment_to_upcomingActFragment);
                break;
            case R.id.new_act:
                navController.navigate(R.id.action_mainFragment_to_newActFragment);
                break;
        }
    }
}