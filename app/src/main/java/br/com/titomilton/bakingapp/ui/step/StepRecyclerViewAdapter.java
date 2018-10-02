package br.com.titomilton.bakingapp.ui.step;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import br.com.titomilton.bakingapp.R;
import br.com.titomilton.bakingapp.entity.Step;
import br.com.titomilton.bakingapp.ui.step.StepListFragment.OnStepListListener;
import butterknife.BindView;
import butterknife.ButterKnife;
import lombok.Getter;


/**
 * {@link RecyclerView.Adapter} that can display a {@link Step} and makes a call to the
 * specified {@link StepListFragment.OnStepListListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class StepRecyclerViewAdapter extends RecyclerView.Adapter<StepRecyclerViewAdapter.ViewHolder> {

    @Getter
    private final List<Step> steps;

    private final OnStepListListener mListener;

    public StepRecyclerViewAdapter(List<Step> items, OnStepListListener listener) {
        steps = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_step_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = steps.get(position);
        holder.mIdView.setText(String.valueOf(steps.get(position).getId()));
        holder.mContentView.setText(steps.get(position).getShortDescription());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    Log.d(StepRecyclerViewAdapter.class.getSimpleName(), "Adapter Step item clicked " + holder.mItem.getDescription());
                    mListener.onStepListClick(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return steps.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;

        @BindView(R.id.item_number)
        public TextView mIdView;

        @BindView(R.id.content)
        public TextView mContentView;

        public Step mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            ButterKnife.bind(this, view);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
