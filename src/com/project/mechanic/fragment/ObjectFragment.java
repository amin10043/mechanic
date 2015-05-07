package com.project.mechanic.fragment;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.project.mechanic.MainActivity;
import com.project.mechanic.R;
import com.project.mechanic.adapter.ObjectListAdapter;
import com.project.mechanic.entity.Object;
import com.project.mechanic.model.DataBaseAdapter;

public class ObjectFragment extends Fragment {

	DataBaseAdapter adapter;
	private Intent intent;

	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		((MainActivity) getActivity()).setTitle(R.string.object);

		View view = inflater.inflate(R.layout.fragment_object, null);

		SharedPreferences sendData = getActivity()
				.getSharedPreferences("Id", 0);
		int id = sendData.getInt("main_Id", -1);
		int city_id = Integer.valueOf(getArguments().getString("cityId"));

		adapter = new DataBaseAdapter(getActivity());

		adapter.open();
		ArrayList<Object> mylist = adapter.getObjectBy_BTId_CityId(id, city_id);
		adapter.close();

		ListView lstObject = (ListView) view
				.findViewById(R.id.listvCmt_Introduction);
		ObjectListAdapter ListAdapter = new ObjectListAdapter(getActivity(),
				R.layout.row_object, mylist);

		lstObject.setAdapter(ListAdapter);

		return view;
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// Changes 'back' button action
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			FragmentTransaction trans = getActivity()
					.getSupportFragmentManager().beginTransaction();
			trans.replace(R.id.content_frame, new MainFragment());
			trans.addToBackStack(null);
			trans.commit();
		}
		return true;
	}

	// public void onBackPressed() {
	//
	// FragmentTransaction trans = ((MainActivity) context)
	// .getSupportFragmentManager().beginTransaction();
	// trans.replace(R.id.content_frame, new FroumtitleFragment());
	// trans.addToBackStack(null);
	// trans.commit();

	// FragmentTransaction trans = getActivity().getSupportFragmentManager()
	// .beginTransaction();
	// trans.replace(R.id.content_frame, new MainFragment());
	// trans.addToBackStack(null);
	// trans.commit();
	// }
}
