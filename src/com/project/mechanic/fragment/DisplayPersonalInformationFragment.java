package com.project.mechanic.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.project.mechanic.MainActivity;
import com.project.mechanic.R;
import com.project.mechanic.PushNotification.DomainSend;
import com.project.mechanic.adapter.AnadListAdapter;
import com.project.mechanic.adapter.DataPersonalExpandAdapter;
import com.project.mechanic.entity.Paper;
import com.project.mechanic.entity.PersonalData;
import com.project.mechanic.entity.Settings;
import com.project.mechanic.entity.Ticket;
import com.project.mechanic.entity.Users;
import com.project.mechanic.inter.AsyncInterface;
import com.project.mechanic.inter.GetAsyncInterface;
import com.project.mechanic.model.DataBaseAdapter;
import com.project.mechanic.service.ServerDate;
import com.project.mechanic.service.UpdatingImage;
import com.project.mechanic.utility.Utility;

public class DisplayPersonalInformationFragment extends Fragment implements
		GetAsyncInterface, AsyncInterface {

	DataBaseAdapter dbAdapter;
	Utility utile1;
	UpdatingImage serviceImage;
	DialogPersonLikedFroum dia;
	ImageView img;
	Ticket tempItem;
	Users u;
	Settings setting;
	String serverDate;
	ServerDate date;
	int userId;
	RelativeLayout phoneLayout, emailLayout, faxLayout, mobileLayout,
			AddressLayout;

	AnadListAdapter anadGridAdapter;
	RelativeLayout.LayoutParams followParams, paramsLayout;

	@SuppressLint("NewApi")
	@Override
	public View onCreateView(final LayoutInflater inflater,
			ViewGroup container, Bundle savedInstanceState) {

		utile1 = new Utility(getActivity());
		dbAdapter = new DataBaseAdapter(getActivity());

		View rootView = inflater.inflate(R.layout.fragment_personal_data, null);

		final View header = inflater.inflate(
				R.layout.fragment_displaypersonalinformation, null);

		phoneLayout = (RelativeLayout) header.findViewById(R.id.laySabet);
		mobileLayout = (RelativeLayout) header.findViewById(R.id.layHamrah);
		AddressLayout = (RelativeLayout) header.findViewById(R.id.layaddress);
		faxLayout = (RelativeLayout) header.findViewById(R.id.layfax);
		emailLayout = (RelativeLayout) header.findViewById(R.id.layEmail);

		if (getArguments() != null) {
			if (getArguments().getInt("0") == 0) {
				Bundle bundle = this.getArguments();
				userId = bundle.getInt("0", 0);
			}

			if (getArguments().getInt("userId") != 0) {
				Bundle bundle = this.getArguments();
				userId = bundle.getInt("userId", 0);
			}
		}
		if (userId != 0) {

			dbAdapter.open();
			u = dbAdapter.getUserById(userId);
			dbAdapter.close();

		} else {
			u = utile1.getCurrentUser();
		}
		if (u == null) {
			// خطا . نباید این اتفاق بیفتد!
			return header;
		}

		date = new ServerDate(getActivity());
		date.delegate = this;
		date.execute("");

		TextView txtaddress = (TextView) header.findViewById(R.id.address);
		TextView txtcellphone = (TextView) header.findViewById(R.id.cellphone);
		TextView txtphone = (TextView) header.findViewById(R.id.phone);
		TextView txtemail = (TextView) header.findViewById(R.id.email);
		TextView txtname = (TextView) header.findViewById(R.id.displayname);
		TextView txtfax = (TextView) header.findViewById(R.id.fax);
		img = (ImageView) header.findViewById(R.id.img1);
		ImageView logout = (ImageView) header.findViewById(R.id.logout);
		RelativeLayout btnedit = (RelativeLayout) header
				.findViewById(R.id.btnedit);
		RelativeLayout birthDayUsers = (RelativeLayout) header
				.findViewById(R.id.birthdayUsers);

		TextView txtdate = (TextView) header.findViewById(R.id.txtdate);

		final LinearLayout lin2 = (LinearLayout) header.findViewById(R.id.lin2);

		LayoutParams lp1 = new LinearLayout.LayoutParams(lin2.getLayoutParams());
		lp1.width = utile1.getScreenwidth() / 4;
		lp1.height = utile1.getScreenwidth() / 4;
		img.setLayoutParams(lp1);
		String ImagePath = u.getImagePath();
		if (ImagePath != null) {
			Bitmap bmp = BitmapFactory.decodeFile(ImagePath);
			img.setImageBitmap(bmp);
		}

	

		img.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				String ImagePath = u.getImagePath();
				String name = u.getName();

				DialogShowImage showImage = new DialogShowImage(getActivity(),
						ImagePath, name);
				showImage.show();
			}
		});
		String name = u.getName();
		String email = u.getEmail();
		String address = u.getAddress();
		String phone = u.getPhonenumber();
		String cellphone = u.getMobailenumber();
		String fax = u.getFaxnumber();
		String d = u.getDate();

		txtdate.setText(utile1.getPersianDate(d));
		txtname.setText(name);
		txtemail.setText(email);
		txtcellphone.setText(cellphone);
		txtphone.setText(phone);
		txtfax.setText(fax);
		txtaddress.setText(address);


		dbAdapter.open();
		List<PersonalData> ObejctData = dbAdapter.CustomFieldObjectByUser(u
				.getId());
		List<PersonalData> FroumData = dbAdapter.CustomFieldFroumByUser(u
				.getId());
		List<PersonalData> PaperData = dbAdapter.CustomFieldPaperByUser(u
				.getId());
		List<PersonalData> TicketData = dbAdapter.CustomFieldTicketByUser(u
				.getId());
		dbAdapter.close();


		ExpandableListView Expandview = (ExpandableListView) rootView
				.findViewById(R.id.items);

		HashMap<String, List<PersonalData>> listDataChild = new HashMap<String, List<PersonalData>>();

		ArrayList<String> parentItems = new ArrayList<String>();

		Expandview.setDividerHeight(5);
		Expandview.setGroupIndicator(null);
		Expandview.setClickable(true);

		parentItems.add("مدیریت صفحات");
		parentItems.add("مدیریت آگهی ها");
		parentItems.add("مدیریت مقالات");
		parentItems.add("مدیریت تالار گفتگو");

		listDataChild.put(parentItems.get(0), ObejctData); // Header, Child data
		listDataChild.put(parentItems.get(1), TicketData);
		listDataChild.put(parentItems.get(2), PaperData);
		listDataChild.put(parentItems.get(3), FroumData);

		final SharedPreferences currentTime = getActivity()
				.getSharedPreferences("time", 0);

		String time = currentTime.getString("time", "-1");

		final DataPersonalExpandAdapter listAdapter = new DataPersonalExpandAdapter(
				getActivity(), parentItems, listDataChild, time,
				DisplayPersonalInformationFragment.this);

		// setting list adapter
		Expandview.addHeaderView(header);

		Expandview.setAdapter(listAdapter);

	
		followParams = new RelativeLayout.LayoutParams(lin2.getLayoutParams());

		followParams.width = utile1.getScreenwidth() / 3;
		followParams.setMargins(5, 5, 5, 5);
		// followParams.addRule(RelativeLayout.ALIGN_LEFT, R.id.lin2);
		followParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		followParams.addRule(RelativeLayout.BELOW, R.id.lin2);

		btnedit.setLayoutParams(followParams);

		paramsLayout = new RelativeLayout.LayoutParams(lin2.getLayoutParams());

		paramsLayout.width = utile1.getScreenwidth() / 3;
		paramsLayout.setMargins(5, 0, 5, 5);
		paramsLayout.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		paramsLayout.addRule(RelativeLayout.BELOW, R.id.btnedit);
		birthDayUsers.setLayoutParams(paramsLayout);

		logout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				// //////////////////////null get current user//////////////////

				SharedPreferences settings = getActivity()
						.getSharedPreferences("User", 0);
				SharedPreferences.Editor editor = settings.edit();

				editor.putBoolean("isLogin", false);

				editor.commit();
				// ////////////////////////////////////////////////
				dbAdapter.open();
				int ad = 0;
				dbAdapter.UpdateAdminAllUser(ad);
				dbAdapter.close();

				FragmentTransaction trans = getActivity()
						.getSupportFragmentManager().beginTransaction();
				trans.replace(R.id.content_frame, new LoginFragment());
				trans.commit();
				TextView txtlike = (TextView) (getActivity())
						.findViewById(R.id.txtlike);
				txtlike.setVisibility(View.GONE);
				TextView txtcm1 = (TextView) (getActivity())
						.findViewById(R.id.txtcm);
				txtcm1.setVisibility(View.GONE);

			}

		});

		btnedit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				FragmentTransaction trans = getActivity()
						.getSupportFragmentManager().beginTransaction();
				trans.replace(R.id.content_frame, new EditPersonalFragment());
				trans.addToBackStack(null);
				trans.commit();
			}
		});

		final SharedPreferences tashkhis = getActivity().getSharedPreferences(
				"Id", 0);

		birthDayUsers.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				DomainSend fragment = new DomainSend("BirthDay");

				FragmentTransaction trans = ((MainActivity) getActivity())
						.getSupportFragmentManager().beginTransaction();

				trans.replace(R.id.content_frame, fragment);
				trans.addToBackStack(null);
				trans.commit();

				tashkhis.edit().putString("enter", "DisplayPersonal").commit();
				tashkhis.edit().putString("FromTableName", "BirthDay").commit();

			}
		});

		utile1.ShowFooterAgahi(getActivity(), false, 1);

		return rootView;
	}

	@Override
	public void processFinish(byte[] output) {
		if (output != null) {
			Bitmap bmp = BitmapFactory
					.decodeByteArray(output, 0, output.length);
			img.setImageBitmap(Utility.getRoundedCornerBitmap(bmp, 50));
			dbAdapter.open();
			dbAdapter.UpdateUserImage(u.getId(), output, serverDate);
			dbAdapter.close();
		} else {
			if (u != null && u.getImage() != null) {
				Bitmap bmp = BitmapFactory.decodeByteArray(u.getImage(), 0,
						u.getImage().length);
				img.setImageBitmap(Utility.getRoundedCornerBitmap(bmp, 50));
			}
		}
	}

	@Override
	public void processFinish(String output) {
		if (!"".equals(output) && output != null) {
			serverDate = output;
			HashMap<String, String> params = new LinkedHashMap<String, String>();
			params.put("tableName", "Users");
			params.put("Id", String.valueOf(u.getId()));
			params.put("fromDate", u.getImageServerDate());
			Context context = getActivity();
			if (context != null) {

				serviceImage = new UpdatingImage(context);
				serviceImage.delegate = this;
				serviceImage.execute(params);

			}
		}

	}
}
