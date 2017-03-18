package com.codepath.nytimes.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.codepath.nytimes.R;
import com.codepath.nytimes.models.FilterSettings;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Saranu on 3/17/17.
 */

public class FilterDialogFragment extends DialogFragment {

    @BindView(R.id.tvBeginDate)public TextView tvBeginDate;
    @BindView(R.id.etBeginDate) public EditText etBeginDate;
    @BindView(R.id.tvSortOrder) public TextView tvSortOrder;
    @BindView(R.id.spSortOrder) public Spinner spSortOrder;
    @BindView(R.id.tvNewsDesk) public TextView tvNewsDesk;
    @BindView(R.id.cbFilter1) public CheckBox cbFilter1;
    @BindView(R.id.cbFilter2) public CheckBox cbFilter2;
    @BindView(R.id.cbFilter3) public CheckBox cbFilter3;
    @BindView(R.id.btnFilterSubmit) public Button btnFilterSubmit;
    FilterSettings filterObj ;



    public FilterDialogFragment() {
        // Empty constructor is required for DialogFragment
        // Make sure not to add arguments to the constructor
        // Use `newInstance` instead as shown below
    }

    public interface FilterDialogListener {
        void onFinishFilterDialog(FilterSettings filterObj);
    }

    public static FilterDialogFragment newInstance() {
        FilterDialogFragment frag = new FilterDialogFragment();
        Bundle args = new Bundle();
         return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fView = inflater.inflate(R.layout.fragment_filter, container);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            filterObj = bundle.getParcelable("SettingObj");
        }
        ButterKnife.bind(this,fView);
        return fView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnFilterSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                filterObj.setBeginDate(etBeginDate.getText().toString());
                //filterObj.setSortOrder(spSortOrder.getSelectedItem().toString());
                filterObj.setSortOrder("oldest");
                filterObj.setNdArtsCheck(cbFilter1.getText().toString());
                filterObj.setNdFashionCheck(cbFilter2.getText().toString());
                filterObj.setNdSportsCheck(cbFilter3.getText().toString());
                FilterDialogListener listener = (FilterDialogListener) getActivity();
                listener.onFinishFilterDialog(filterObj);
                // Close the dialog and return back to the parent activity
                dismiss();
            }
        });

    }
}
