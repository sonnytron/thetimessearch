package com.sonnytron.sortatech.nytimessearch.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import com.sonnytron.sortatech.nytimessearch.R;
import com.sonnytron.sortatech.nytimessearch.fragments.DatePickerFragment;
import com.sonnytron.sortatech.nytimessearch.models.SearchFilters;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by sonnyrodriguez on 7/31/16.
 */
public class FiltersActivity extends FragmentActivity implements DatePickerFragment.DateCallback {
    private static final String DIALOG_DATE = "DialogDate";
    private static final int REQUEST_DATE = 0;
    private Button mApplyFilters;
    private SearchCallback mCallback;
    private CheckBox fashionCheckbox;
    private CheckBox sportsCheckbox;
    private CheckBox artsCheckbox;
    private Spinner mSortedSpinner;
    private Button mDateButton;
    private String mNewsDeskParameter;
    private String sortedString;
    private List<String> mNewsDeskStrings;
    private CompoundButton.OnCheckedChangeListener mChangeListener;
    private Date fromDate;

    public interface SearchCallback {
        void searchFiltersApplied(SearchFilters filters);
    }

    @Override
    public void dateSelected(Date date) {
        fromDate = date;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        if (requestCode == REQUEST_DATE) {
            Date date = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            fromDate = date;
        }
    }


    public FiltersActivity(Context context) {
        mCallback = (SearchCallback) context;
    }

    public FiltersActivity() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.search_filter_activity);
        mApplyFilters = (Button) findViewById(R.id.apply_filters_button);
        mDateButton = (Button) findViewById(R.id.date_button);
        fromDate = null;
        mDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getSupportFragmentManager();
                DatePickerFragment dialog = DatePickerFragment.newInstance(fromDate);
                dialog.show(manager, DIALOG_DATE);

            }
        });

        mNewsDeskStrings = new ArrayList<>();

        mChangeListener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                switch (buttonView.getId()) {
                    case R.id.arts_checkbox:
                        if (isChecked) {
                            mNewsDeskStrings.add("Arts");
                        } else {
                            mNewsDeskStrings.remove(mNewsDeskStrings.indexOf("Arts"));
                        }
                        break;
                    case R.id.sports_checkbox:
                        if (isChecked) {
                            mNewsDeskStrings.add("Sports");
                        } else {
                            mNewsDeskStrings.remove(mNewsDeskStrings.indexOf("Sports"));
                        }
                        break;
                    case R.id.fashion_checkbox:
                        if (isChecked) {
                            mNewsDeskStrings.add("Fashion & Style");
                        } else {
                            mNewsDeskStrings.remove(mNewsDeskStrings.indexOf("Fashion & Style"));
                        }
                        break;
                }
            }
        };

        fashionCheckbox = (CheckBox) findViewById(R.id.fashion_checkbox);
        artsCheckbox = (CheckBox)findViewById(R.id.arts_checkbox);
        sportsCheckbox = (CheckBox) findViewById(R.id.sports_checkbox);

        mSortedSpinner = (Spinner) findViewById(R.id.sort_spinner);

        fashionCheckbox.setOnCheckedChangeListener(mChangeListener);
        artsCheckbox.setOnCheckedChangeListener(mChangeListener);
        sportsCheckbox.setOnCheckedChangeListener(mChangeListener);

        mApplyFilters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchFilters searchFilters = new SearchFilters();
                Intent data = new Intent();
                if (mNewsDeskStrings != null) {
                    searchFilters.setNewsDeskParams(mNewsDeskStrings);
                }
                if (fromDate != null) {
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
                    String dateString = formatter.format(fromDate);
                    searchFilters.setStartDate(dateString);
                }
                if (mSortedSpinner.getSelectedItemPosition() == 0 || mSortedSpinner.getSelectedItemPosition() == 1) {
                    sortedString = "newest";
                } else {
                    sortedString = "oldest";
                }
                searchFilters.setSortString(sortedString);

                data.putExtra("filters", searchFilters);
                setResult(RESULT_OK, data);
                finish();
            }
        });
        super.onCreate(savedInstanceState);
    }

}
