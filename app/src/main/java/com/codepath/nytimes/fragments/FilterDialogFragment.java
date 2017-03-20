package com.codepath.nytimes.fragments;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.codepath.nytimes.R;
import com.codepath.nytimes.models.FilterSettings;
import com.codepath.nytimes.utils.DateUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.codepath.nytimes.properties.Properties.FRAGMENT_MODAL_OVERLAY_DATE_TITLE;

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
        getDialog().setTitle("Filter Settings");
        View fView = inflater.inflate(R.layout.fragment_filter, container);
        Bundle bundle = this.getArguments();
        ButterKnife.bind(this,fView);
        populateViewsfromObject(bundle);
        return fView;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnFilterSubmit.setOnClickListener(view1 ->
        populateObjectfromViews());

    }




    private void populateViewsfromObject(Bundle bundle) {

        if (bundle != null ) {
            filterObj = bundle.getParcelable("SettingObj");
            if (filterObj.getBeginDate() != null) {
                try {
                    etBeginDate.setText(new SimpleDateFormat("MM/dd/yyyy").format(new SimpleDateFormat("yyyyMMdd").parse(filterObj.getBeginDate())));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }if (filterObj.getNdArtsCheck() != null) {
                cbFilter1.setChecked(true);
            }if (filterObj.getNdFashionCheck() != null) {
                cbFilter2.setChecked(true);
            }if (filterObj.getNdSportsCheck() != null) {
                cbFilter3.setChecked(true);
            }if (filterObj.getSortOrder() != null) {
                ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.sort_order, android.R.layout.simple_spinner_item);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spSortOrder.setAdapter(adapter);
                if (!filterObj.getSortOrder().equals(null)) {
                    int spinnerPosition = adapter.getPosition(filterObj.getSortOrder());
                    spSortOrder.setSelection(spinnerPosition);
                }
            }


        }
        etBeginDate.setOnClickListener(v -> {
            // TODO Auto-generated method stub
            processCalendarDatePicker();
          });
    }



    private void processCalendarDatePicker() {
        int mYear;
        int mMonth;
        int mDay;
        if(filterObj.getBeginDate() == null){
            Calendar mcurrentDate = Calendar.getInstance();
            mYear = mcurrentDate.get(Calendar.YEAR);
            mMonth = mcurrentDate.get(Calendar.MONTH);
            mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);

        }else {
            Calendar mcurrentDate = Calendar.getInstance();
            mcurrentDate.setTime(DateUtil.formatDatefromAPI(filterObj.getBeginDate()));
            mYear = mcurrentDate.get(Calendar.YEAR);
            mMonth = mcurrentDate.get(Calendar.MONTH);
            mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);
        }

        DatePickerDialog mDatePicker=new DatePickerDialog(getContext(), (datepicker, selectedyear, selectedmonth, selectedday) -> {
            // TODO Auto-generated method stub
            Calendar newDate = Calendar.getInstance();
            newDate.set(selectedyear, selectedmonth, selectedday);
            etBeginDate.setText(DateUtil.formatDate(newDate));
        },mYear, mMonth, mDay);
        mDatePicker.setTitle(FRAGMENT_MODAL_OVERLAY_DATE_TITLE);
        mDatePicker.show();
    }


    private void populateObjectfromViews() {

        {
            filterObj.reset();
            if(etBeginDate.getText() != null){
                filterObj.setBeginDate(DateUtil.formatDatetoAPI(etBeginDate.getText().toString()));
            }
            filterObj.setSortOrder(spSortOrder.getSelectedItem().toString());

            if(cbFilter1.isChecked()) {
                filterObj.setNdArtsCheck(cbFilter1.getText().toString());
            }

            if(cbFilter2.isChecked()) {
                filterObj.setNdFashionCheck(cbFilter2.getText().toString());
            }

            if(cbFilter3.isChecked()) {
                filterObj.setNdSportsCheck(cbFilter3.getText().toString());
            }

            FilterDialogListener listener = (FilterDialogListener) getActivity();
            listener.onFinishFilterDialog(filterObj);
            dismiss();
        }
    }
}
