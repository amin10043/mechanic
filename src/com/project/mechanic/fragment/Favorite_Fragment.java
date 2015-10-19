package com.project.mechanic.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.Toast;

import com.project.mechanic.R;
import com.project.mechanic.adapter.DataPersonalExpandAdapter;
import com.project.mechanic.adapter.FavoriteListAdapter;
import com.project.mechanic.entity.PersonalData;
import com.project.mechanic.entity.Ticket;
import com.project.mechanic.entity.Users;
import com.project.mechanic.model.DataBaseAdapter;
import com.project.mechanic.utility.Utility;

public class Favorite_Fragment extends Fragment {

	Utility util;
	Dialogeml dialog;
	DataBaseAdapter dbAdapter;
	List<Ticket> mylist;
	int id;
	int user;
	int ticket;
	Users u;
	View view;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		util = new Utility(getActivity());

		// ((MainActivity) getActivity()).setActivityTitle(R.string.favorite);
		view = inflater.inflate(R.layout.favorite_fragment, null);

		dbAdapter = new DataBaseAdapter(getActivity());

		dbAdapter.open();

		List<PersonalData> FroumData = dbAdapter.CustomFieldFavorite(util
				.getCurrentUser().getId(), 1);
		List<PersonalData> PaperData = dbAdapter.CustomFieldFavorite(util
				.getCurrentUser().getId(), 2);
		List<PersonalData> TicketData = dbAdapter.CustomFieldFavorite(util
				.getCurrentUser().getId(), 3);
		List<PersonalData> ObejctData = dbAdapter.CustomFieldFavorite(util
				.getCurrentUser().getId(), 4);

		dbAdapter.close();

		ExpandableListView Expandview = (ExpandableListView) view
				.findViewById(R.id.items);

		HashMap<String, List<PersonalData>> listDataChild = new HashMap<String, List<PersonalData>>();

		ArrayList<String> parentItems = new ArrayList<String>();

		Expandview.setDividerHeight(5);
		Expandview.setGroupIndicator(null);
		Expandview.setClickable(true);

		parentItems.add("صفحات");
		parentItems.add("آگهی ها");
		parentItems.add("مقالات");
		parentItems.add("تالار گفتگو");

		listDataChild.put(parentItems.get(0), ObejctData); // Header, Child data
		listDataChild.put(parentItems.get(1), TicketData);
		listDataChild.put(parentItems.get(2), PaperData);
		listDataChild.put(parentItems.get(3), FroumData);

		final SharedPreferences currentTime = getActivity()
				.getSharedPreferences("time", 0);

		String time = currentTime.getString("time", "-1");

		final FavoriteListAdapter listAdapter = new FavoriteListAdapter(
				getActivity(), parentItems, listDataChild, time,
				Favorite_Fragment.this);

		// setting list adapter

		Expandview.setAdapter(listAdapter);
		
		
		util.ShowFooterAgahi(getActivity(), false, 5);
		return view;

	}

	// public void updateView() {
	// dbAdapter.open();
	// mylist = dbAdapter.getTicketByusetId(u.getId());
	// dbAdapter.close();
	//
	// listFavorite = (ListView) view.findViewById(R.id.listView_favorite);
	// FavoriteListAdapter ListAdapter = new FavoriteListAdapter(
	// getActivity(), R.layout.row_favorite, mylist, this);
	//
	// listFavorite.setAdapter(ListAdapter);
	//
	// }

}
