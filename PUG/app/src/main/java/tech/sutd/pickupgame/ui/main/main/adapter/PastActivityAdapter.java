package tech.sutd.pickupgame.ui.main.main.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import tech.sutd.pickupgame.data.Resource;
import tech.sutd.pickupgame.databinding.ItemlistActivitiesBinding;
import tech.sutd.pickupgame.models.ui.NewActivity;
import tech.sutd.pickupgame.models.ui.PastActivity;
import tech.sutd.pickupgame.models.ui.YourActivity;
import tech.sutd.pickupgame.util.DateConverter;

public class PastActivityAdapter extends RecyclerView.Adapter<PastActivityAdapter.ViewHolder> {

    private Resource<List<PastActivity>> source;

    private final RequestManager requestManager;

    @Inject
    public PastActivityAdapter(RequestManager requestManager) {
        this.requestManager = requestManager;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemlistActivitiesBinding binding = ItemlistActivitiesBinding.inflate(inflater, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (source.data == null || source.data.isEmpty())
            return;

        PastActivity pastActivity = source.data.get(position);

        int numOfViews = 10;
        if (position > numOfViews) {
            holder.binding.cardView.setVisibility(View.GONE);
            return;
        }

        holder.binding.sport.setText(pastActivity.getSport());

        String dateFormat = DateConverter.clockConverter(pastActivity.getClock());
        String dateEndFormat = DateConverter.endClockConverter(pastActivity.getEndClock());

        String time = dateFormat + " - " + dateEndFormat;

        holder.binding.time.setText(time);
        holder.binding.location.setText(pastActivity.getLocation());
        holder.binding.organizer.setText(pastActivity.getOrganizer());

        requestManager.load(pastActivity.getClockImg())
                .into(holder.binding.clockImg);
        requestManager.load(pastActivity.getLocationImg())
                .into(holder.binding.locationImg);
        requestManager.load(pastActivity.getOrganizerImg())
                .into(holder.binding.organizerImg);
        requestManager.load(pastActivity.getSportImg())
                .into(holder.binding.sportImg);

        // add margins to left, right & bottom if lesser numOfViews
        if (position < numOfViews) {
            RecyclerView.LayoutParams lp = (RecyclerView.LayoutParams) holder.binding.layout.getLayoutParams();
            lp.setMargins(20, 0, 20, 20);
        }

        // add margins to left & right if numOfViews reached
        if (position == numOfViews) {
            RecyclerView.LayoutParams lp = (RecyclerView.LayoutParams) holder.binding.layout.getLayoutParams();
            lp.setMargins(20, 0, 20, 0);
        }
    }

    @Override
    public int getItemCount() {
        if (source == null || source.data == null)
            return 0;
        return source.data.size();
    }

    public void setSource(List<PastActivity> data) {
        source = Resource.success(data);
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final ItemlistActivitiesBinding binding;

        public ViewHolder(@NonNull ItemlistActivitiesBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
