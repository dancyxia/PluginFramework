package com.example.myplugindemo;

import android.app.Activity;
import android.support.v4.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Created by dancy on 8/12/2015.
 */
public class DateListFragment extends ListFragment {

    public static final String ARG_DATE_NO = "DATE_NO";
    private ListItemClickListener listItemClickListener;

    public interface ListItemClickListener {
        public void onItemClicked(int position);
    }

    public DateListFragment() {
    }

    public static DateListFragment getInstance(int dateNo) {
        DateListFragment fragment = new DateListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_DATE_NO, dateNo);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        listItemClickListener = (ListItemClickListener)activity;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        getArguments().putInt(ARG_DATE_NO, position);
        listItemClickListener.onItemClicked(position);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ListView topList;
        View rootView = inflater.inflate(R.layout.fragment_date_list, container, false);
        topList = (ListView) rootView.findViewById(android.R.id.list);
        topList.setAdapter(new ArrayAdapter<String>(this.getActivity(), R.layout.date_item, getResources().getStringArray(R.array.date)));
        topList.setSelection(getArguments().getInt(ARG_DATE_NO));
        return rootView;
    }
}
