package br.com.titomilton.bakingapp.ui.step;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import br.com.titomilton.bakingapp.R;
import br.com.titomilton.bakingapp.entity.Step;
import br.com.titomilton.bakingapp.ui.MainViewModel;

public class StepListFragment extends Fragment {
    private static final String LOG = StepListFragment.class.getSimpleName();
    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnStepListListener mListener;
    private MainViewModel mainViewModel;
    private StepRecyclerViewAdapter stepRecyclerViewAdapter;

    public StepListFragment() {
    }

    public static StepListFragment newInstance(int columnCount) {
        StepListFragment fragment = new StepListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_step_list, container, false);
        Log.d(LOG, "onCreateView");


        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }


            stepRecyclerViewAdapter = new StepRecyclerViewAdapter(new ArrayList<Step>(), mListener);

            recyclerView.setAdapter(stepRecyclerViewAdapter);
        }
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(LOG, "onActivityCreated");
        mainViewModel = ViewModelProviders.of(getActivity()).get(MainViewModel.class);
        List<Step> steps = mainViewModel.getRecipe().getSteps();
        stepRecyclerViewAdapter.getSteps().addAll(steps);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnStepListListener) {
            mListener = (OnStepListListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnStepListListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnStepListListener {
        void onStepListClick(Step item);
    }
}
