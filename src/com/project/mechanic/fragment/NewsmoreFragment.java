package com.project.mechanic.fragment;

import java.util.ArrayList;

import com.project.mechanic.MainActivity;
import com.project.mechanic.R;
import com.project.mechanic.adapter.NewspaperListAdapter;
import com.project.mechanic.entity.News;
import com.project.mechanic.model.DataBaseAdapter;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;


public class NewsmoreFragment extends Fragment {

	
	ArrayList<News> mylist;
	DataBaseAdapter mdb;
	

	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		((MainActivity) getActivity()).setActivityTitle(R.string.news);

		View view = inflater.inflate(R.layout.fragment_newspaper, null);

		mdb = new DataBaseAdapter(getActivity());
		mdb.open();
		mylist = mdb.getAllNews();
		mdb.close();


		ListView lstNews = (ListView) view.findViewById(R.id.listvnewspaper);
		NewspaperListAdapter ListAdapter = new NewspaperListAdapter(
				getActivity(), R.layout.row_newspaper, mylist);
	
		lstNews.setAdapter(ListAdapter);
		return view;
		

	


		
		

	
		

	

		
	
	}


}

	
	
