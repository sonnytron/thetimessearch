package com.sonnytron.sortatech.nytimessearch.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sonnyrodriguez on 7/31/16.
 */
public class SearchFilters implements Parcelable {

    private List<String> newsDeskParams;
    private String mSortString;
    private String mStartDate;
    private String newsDeskString;

    public void setNewsDeskString() {
        ArrayList<String> newsDeskItems = new ArrayList<>();
        String newsDeskString = "";
        if (newsDeskParams.size() > 0) {
            for (int i = 0; i < newsDeskParams.size(); i++) {
                String partialString = "\""+ newsDeskParams.get(i) + "\"";
                newsDeskItems.add(partialString);
            }
        }
        String newsDeskStrings = android.text.TextUtils.join(" ", newsDeskItems);
        if (newsDeskItems.size() > 0) {
            newsDeskString = String.format("news_desk:(%s)", newsDeskStrings);
        } else {
            newsDeskString = "";
        }
        this.newsDeskString = newsDeskString;
    }

    public void setStartDate(String startDate) {
        mStartDate = startDate;
    }

    public String getStartDate() {
        return mStartDate;
    }

    public String getSortString() {
        return mSortString;
    }

    public void setSortString(String sortString) {
        mSortString = sortString;
    }

    public void setNewsDeskParams(List<String> newsDeskParams) {
        this.newsDeskParams = newsDeskParams;
    }

    public String getNewsDeskString() {
        ArrayList<String> newsDeskItems = new ArrayList<>();
        if (newsDeskParams.size() > 0) {
            for (int i = 0; i < newsDeskParams.size(); i++) {
                String newsDeskString = "\""+ newsDeskParams.get(i) + "\"";
                newsDeskItems.add(newsDeskString);
            }
        }
        String newsDeskStrings = android.text.TextUtils.join(" ", newsDeskItems);
        if (newsDeskItems.size() > 0) {
            newsDeskString = String.format("news_desk:(%s)", newsDeskStrings);
        } else {
            newsDeskString = "";
        }
        return newsDeskString;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mStartDate);
        dest.writeString(this.mSortString);
        dest.writeStringList(this.newsDeskParams);
    }

    public SearchFilters() {
    }

    protected SearchFilters(Parcel in) {
        this.mStartDate = in.readString();
        this.mSortString = in.readString();
        this.newsDeskParams = in.createStringArrayList();
    }

    public static final Creator<SearchFilters> CREATOR = new Creator<SearchFilters>() {
        @Override
        public SearchFilters createFromParcel(Parcel source) {
            return new SearchFilters(source);
        }

        @Override
        public SearchFilters[] newArray(int size) {
            return new SearchFilters[size];
        }
    };
}
