package tech.sutd.pickupgame.ui.main.main;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Calendar;

import javax.inject.Inject;

import tech.sutd.pickupgame.BaseFragment;
import tech.sutd.pickupgame.R;
import tech.sutd.pickupgame.data.Resource;
import tech.sutd.pickupgame.databinding.FragmentMainBinding;
import tech.sutd.pickupgame.models.ui.NewActivity;
import tech.sutd.pickupgame.models.ui.UpcomingActivity;
import tech.sutd.pickupgame.ui.main.BaseInterface;
import tech.sutd.pickupgame.ui.main.SuccessListenerTwo;
import tech.sutd.pickupgame.ui.main.main.adapter.NewActivityAdapter;
import tech.sutd.pickupgame.ui.main.main.adapter.UpcomingActivityAdapter;
import tech.sutd.pickupgame.ui.main.main.viewmodel.NewActViewModel;
import tech.sutd.pickupgame.ui.main.main.viewmodel.UpcomingActViewModel;
import tech.sutd.pickupgame.viewmodels.ViewModelProviderFactory;

public class MainFragment extends BaseFragment implements View.OnClickListener {

    private FragmentMainBinding binding;

    private UpcomingActViewModel upcomingActViewModel;
    private NewActViewModel newActViewModel;

    private BaseInterface listener;
    private SuccessListenerTwo successListenerTwo;

    private Observer<Resource<PagedList<UpcomingActivity>>> upcomingActObserver;
    private Observer<Resource<PagedList<NewActivity>>> newActObserver;

    @Inject UpcomingActivityAdapter<UpcomingActivity> adapter;
    @Inject NewActivityAdapter<NewActivity> newAdapter;
    @Inject ViewModelProviderFactory providerFactory;

    @Inject Handler handler;

    public BaseInterface getListener() {
        return listener;
    }

    public SuccessListenerTwo getSuccessListenerTwo() {
        return successListenerTwo;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentMainBinding.inflate(inflater, container, false);

        upcomingActViewModel = new ViewModelProvider(this, providerFactory).get(UpcomingActViewModel.class);
        newActViewModel = new ViewModelProvider(this, providerFactory).get(NewActViewModel.class);

        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        initViews();
        subscribeObserver();
    }

    private void subscribeObserver() {
        upcomingActObserver = pagedListResource -> {
            if (pagedListResource.status == Resource.Status.SUCCESS) {
                adapter.submitList(pagedListResource.data);
            }
        };

        newActObserver = pagedListResource -> {
            if (pagedListResource.status == Resource.Status.SUCCESS) {
                newAdapter.submitList(pagedListResource.data);
            }
        };

        upcomingActViewModel.getUpcomingActivitiesByClock2().observe(getViewLifecycleOwner(), upcomingActObserver);

        newActViewModel.getNewActivitiesByClock2().observe(getViewLifecycleOwner(), newActObserver);
    }

    private void initViews() {

        binding.upcomingRc.setAdapter(adapter);
        binding.upcomingRc.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.upcomingRc.setHasFixedSize(true);
        adapter.setNotifications(1);


        binding.newRc.setAdapter(newAdapter);
        binding.newRc.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.newRc.setHasFixedSize(true);
        newAdapter.setNotifications(getContext(), this, null, 1);

        binding.upcomingAct.setOnClickListener(this);
        binding.newAct.setOnClickListener(this);

        binding.swipeRefresh.setOnRefreshListener(() -> {
            pull();

            if (upcomingActViewModel.getUpcomingActivitiesByClock2().hasActiveObservers())
                upcomingActViewModel.getUpcomingActivitiesByClock2().removeObserver(upcomingActObserver);
            upcomingActViewModel.getUpcomingActivitiesByClock2().observe(getViewLifecycleOwner(), upcomingActObserver);

            if (newActViewModel.getNewActivitiesByClock2().hasActiveObservers())
                newActViewModel.getNewActivitiesByClock2().removeObserver(newActObserver);
            newActViewModel.getNewActivitiesByClock2().observe(getViewLifecycleOwner(), newActObserver);

            binding.swipeRefresh.setRefreshing(false);
        });
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == binding.upcomingAct.getId())
            getNavController().navigate(R.id.action_mainFragment_to_upcomingActFragment);
        else if (id == binding.newAct.getId())
            getNavController().navigate(R.id.action_mainFragment_to_newActFragment);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (BaseInterface) context;
            successListenerTwo = (SuccessListenerTwo) context;
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
    }
}