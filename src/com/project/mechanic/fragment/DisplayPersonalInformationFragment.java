//////////////////
package com.project.mechanic.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import com.project.mechanic.MainActivity;
import com.project.mechanic.R;
import com.project.mechanic.PushNotification.DomainSend;
import com.project.mechanic.adapter.AnadListAdapter;
import com.project.mechanic.adapter.DataPersonalExpandAdapter;
import com.project.mechanic.entity.LikeInObject;
import com.project.mechanic.entity.Object;
import com.project.mechanic.entity.PersonalData;
import com.project.mechanic.entity.Settings;
import com.project.mechanic.entity.Ticket;
import com.project.mechanic.entity.Users;
import com.project.mechanic.inter.AsyncInterface;
import com.project.mechanic.inter.GetAsyncInterface;
import com.project.mechanic.model.DataBaseAdapter;
import com.project.mechanic.service.ServerDate;
import com.project.mechanic.service.UpdatingImage;
import com.project.mechanic.service.UpdatingPersonalPage;
import com.project.mechanic.utility.Utility;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

public class DisplayPersonalInformationFragment extends Fragment implements AsyncInterface {

	DataBaseAdapter dbAdapter;
	Utility util;
	UpdatingImage serviceImage;
	DialogPersonLikedFroum dia;
	ImageView img, logout;
	Ticket tempItem;
	Users currentUser;
	Settings setting;
	String serverDate;
	ServerDate date;
	// int userId;
	RelativeLayout phoneLayout, emailLayout, faxLayout, mobileLayout, AddressLayout, btnedit, birthDayUsers;

	AnadListAdapter anadGridAdapter;
	View rootView, header;
	ExpandableListView Expandview;

	TextView txtaddress, txtcellphone, txtphone, txtemail, txtname, txtfax, txtdate;
	TextView txtEdit, txtBirthday;
	LinearLayout.LayoutParams lp1;
	RelativeLayout.LayoutParams editBtnParams, paramsLayout;

	UpdatingPersonalPage updating;
	ProgressDialog ringProgressDialog;

	boolean isFirstRun;
	String tableName;
	int selectTableId = 1;

	@SuppressLint("NewApi")
	@Override
	public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		util = new Utility(getActivity());
		dbAdapter = new DataBaseAdapter(getActivity());

		// define rootView and header Layout
		rootView = inflater.inflate(R.layout.test_expandable, null);
		header = inflater.inflate(R.layout.fragment_test_display_personal, null);

		// define Views : find View By Id
		findView();

		// get User
		CurrentUser();

		setFont();

		RunServiceDate();

		// set data for expandListView
		FillExpandListView();

		// setValue for Parameter and variable
		setValue();

		// set LauoutParams
		setLayoutParams();

		// on click action
		onClick();

		util.ShowFooterAgahi(getActivity(), false, 1);

		return rootView;
	}

	// @Override
	// public void processFinish(byte[] output) {
	// if (output != null) {
	// Bitmap bmp = BitmapFactory
	// .decodeByteArray(output, 0, output.length);
	// img.setImageBitmap(Utility.getRoundedCornerBitmap(bmp, 50));
	// dbAdapter.open();
	// dbAdapter.UpdateUserImage(u.getId(), output, serverDate);
	// dbAdapter.close();
	// }
	// else {
	// if (u != null && u.getImage() != null) {
	// Bitmap bmp = BitmapFactory.decodeByteArray(u.getImage(), 0,
	// u.getImage().length);
	// img.setImageBitmap(Utility.getRoundedCornerBitmap(bmp, 50));
	// }
	// }
	// Toast.makeText(getActivity(), "", 0).show();
	// }

	// @Override
	// public void processFinish(String output) {
	// if (!"".equals(output) && output != null) {
	// serverDate = output;
	// HashMap<String, String> params = new LinkedHashMap<String, String>();
	// params.put("tableName", "Users");
	// params.put("Id", String.valueOf(currentUser.getId()));
	// params.put("fromDate", currentUser.getImageServerDate());
	// Context context = getActivity();
	// if (context != null) {
	//
	// serviceImage = new UpdatingImage(context);
	// serviceImage.delegate = this;
	// serviceImage.execute(params);
	//
	// }
	// }
	//
	// }

	public void FillExpandListView() {

		dbAdapter.open();
		List<PersonalData> ObejctData = dbAdapter.CustomFieldObjectByUser(currentUser.getId());
		List<PersonalData> FroumData = dbAdapter.CustomFieldFroumByUser(currentUser.getId());
		List<PersonalData> PaperData = dbAdapter.CustomFieldPaperByUser(currentUser.getId());
		List<PersonalData> TicketData = dbAdapter.CustomFieldTicketByUser(currentUser.getId());
		List<PersonalData> AnadData = dbAdapter.CustomFieldAnadByUser(currentUser.getId());

		List<Object> myFollowingPages = new ArrayList<Object>();

		List<LikeInObject> likePages = dbAdapter.getAllPageFollowingMe(util.getCurrentUser().getId(), 0);

		for (int i = 0; i < likePages.size(); i++) {

			Object o = dbAdapter.getObjectbyid(likePages.get(i).getPaperId());
			myFollowingPages.add(o);
		}

		List<PersonalData> FollowedPageLsit = dbAdapter.CustomFieldObjectFollowByUser(myFollowingPages);

		dbAdapter.close();

		List<Integer> sizeTypeList = new ArrayList<Integer>();

		sizeTypeList.add(ObejctData.size());
		sizeTypeList.add(FollowedPageLsit.size());
		sizeTypeList.add(TicketData.size());
		sizeTypeList.add(PaperData.size());
		sizeTypeList.add(FroumData.size());
		sizeTypeList.add(AnadData.size());

		// Expandview = (ExpandableListView) rootView.findViewById(R.id.items);

		HashMap<String, List<PersonalData>> listDataChild = new HashMap<String, List<PersonalData>>();

		ArrayList<String> parentItems = new ArrayList<String>();

		// Expandview.setDividerHeight(20);

		// Drawable d =
		// getResources().getDrawable(R.drawable.indicator_expandable);
		// Expandview.setIndicatorBounds(345,375);

		// Expandview.setGroupIndicator(d);

		Expandview.setGroupIndicator(null);
		Expandview.setClickable(true);

		parentItems.add("مدیریت صفحات");
		parentItems.add("مدیریت صفحات دنبال شده");
		parentItems.add("مدیریت آگهی ها");
		parentItems.add("مدیریت مقالات");
		parentItems.add("مدیریت تالار گفتگو");
		parentItems.add("مدیریت تبلیغات");

		List<PersonalData> emptyItem = new ArrayList<PersonalData>();

		PersonalData prd = new PersonalData();

		if (ObejctData.size() == 0) {

			prd.setDateTicket("آیتمی اضافه نشده است");
			emptyItem.clear();
			emptyItem.add(prd);

			listDataChild.put(parentItems.get(0), emptyItem);

		} else {
			listDataChild.put(parentItems.get(0), ObejctData);

		}

		if (FollowedPageLsit.size() == 0) {

			prd.setDateTicket("آیتمی اضافه نشده است");
			emptyItem.clear();
			emptyItem.add(prd);

			listDataChild.put(parentItems.get(1), emptyItem);
		} else {

			listDataChild.put(parentItems.get(1), FollowedPageLsit);

		}

		if (TicketData.size() == 0) {
			prd.setDateTicket("آیتمی اضافه نشده است");
			emptyItem.clear();
			emptyItem.add(prd);

			listDataChild.put(parentItems.get(2), emptyItem);
		} else {
			listDataChild.put(parentItems.get(2), TicketData);

		}

		if (PaperData.size() == 0) {
			prd.setDateTicket("آیتمی اضافه نشده است");
			emptyItem.clear();
			emptyItem.add(prd);

			listDataChild.put(parentItems.get(3), emptyItem);
		} else {
			listDataChild.put(parentItems.get(3), PaperData);
		}
		if (FroumData.size() == 0) {
			prd.setDateTicket("آیتمی اضافه نشده است");
			emptyItem.clear();
			emptyItem.add(prd);

			listDataChild.put(parentItems.get(4), emptyItem);

		}else {

			listDataChild.put(parentItems.get(4), FroumData);
		}
		if (AnadData.size() == 0) {
			prd.setDateTicket("آیتمی اضافه نشده است");
			emptyItem.clear();
			emptyItem.add(prd);

			listDataChild.put(parentItems.get(5), emptyItem);

		}else {

			listDataChild.put(parentItems.get(5), AnadData);
		}

		final SharedPreferences currentTime = getActivity().getSharedPreferences("time", 0);

		String time = currentTime.getString("time", "-1");

		final DataPersonalExpandAdapter listAdapter = new DataPersonalExpandAdapter(getActivity(), parentItems,
				listDataChild, time, DisplayPersonalInformationFragment.this, sizeTypeList, true,
				util.getCurrentUser().getName());

		// setting list adapter

		Expandview.setAdapter(listAdapter);
	}

	private void findView() {

		phoneLayout = (RelativeLayout) header.findViewById(R.id.laySabet);
		mobileLayout = (RelativeLayout) header.findViewById(R.id.layHamrah);
		AddressLayout = (RelativeLayout) header.findViewById(R.id.layaddress);
		faxLayout = (RelativeLayout) header.findViewById(R.id.layfax);
		emailLayout = (RelativeLayout) header.findViewById(R.id.layEmail);

		Expandview = (ExpandableListView) rootView.findViewById(R.id.items);

		txtaddress = (TextView) header.findViewById(R.id.address);
		txtcellphone = (TextView) header.findViewById(R.id.cellphone);
		txtphone = (TextView) header.findViewById(R.id.phone);
		txtemail = (TextView) header.findViewById(R.id.email);
		txtname = (TextView) header.findViewById(R.id.displayname);
		txtfax = (TextView) header.findViewById(R.id.fax);

		img = (ImageView) header.findViewById(R.id.img1);
		logout = (ImageView) header.findViewById(R.id.logout);

		btnedit = (RelativeLayout) header.findViewById(R.id.btnedit);

		birthDayUsers = (RelativeLayout) header.findViewById(R.id.b);

		txtdate = (TextView) header.findViewById(R.id.txtdate);
		txtdate.setVisibility(View.GONE);

		Expandview.addHeaderView(header);

		txtEdit = (TextView) header.findViewById(R.id.labb1);
		txtBirthday = (TextView) header.findViewById(R.id.tst);

	}

	private Users CurrentUser() {

		dbAdapter.open();
		currentUser = dbAdapter.getUserById(util.getCurrentUser().getId());
		dbAdapter.close();

		return currentUser;

	}

	private void setLayoutParams() {

		RelativeLayout.LayoutParams f1 = new RelativeLayout.LayoutParams(phoneLayout.getLayoutParams());
		RelativeLayout.LayoutParams f2 = new RelativeLayout.LayoutParams(mobileLayout.getLayoutParams());
		RelativeLayout.LayoutParams f3 = new RelativeLayout.LayoutParams(emailLayout.getLayoutParams());
		RelativeLayout.LayoutParams f4 = new RelativeLayout.LayoutParams(faxLayout.getLayoutParams());
		RelativeLayout.LayoutParams f5 = new RelativeLayout.LayoutParams(AddressLayout.getLayoutParams());

		f1.width = util.getScreenwidth();
		f1.height = LayoutParams.WRAP_CONTENT;
		f1.setMargins(50, 0, 50, 0);
		f1.addRule(RelativeLayout.CENTER_HORIZONTAL);
		f1.addRule(RelativeLayout.BELOW, R.id.textView6);

		f2.width = util.getScreenwidth();
		f2.height = LayoutParams.WRAP_CONTENT;
		f2.setMargins(50, 0, 50, 0);
		f2.addRule(RelativeLayout.CENTER_HORIZONTAL);
		f2.addRule(RelativeLayout.BELOW, R.id.textView8);

		f3.width = util.getScreenwidth();
		f3.height = LayoutParams.WRAP_CONTENT;
		f3.setMargins(50, 0, 50, 0);
		f3.addRule(RelativeLayout.CENTER_HORIZONTAL);
		f3.addRule(RelativeLayout.BELOW, R.id.textView4);

		f4.width = util.getScreenwidth();
		f4.height = LayoutParams.WRAP_CONTENT;
		f4.setMargins(50, 0, 50, 0);
		f4.addRule(RelativeLayout.CENTER_HORIZONTAL);
		f4.addRule(RelativeLayout.BELOW, R.id.textView10);

		f5.width = util.getScreenwidth();
		f5.height = LayoutParams.WRAP_CONTENT;
		f5.setMargins(50, 0, 50, 0);
		f5.addRule(RelativeLayout.CENTER_HORIZONTAL);
		f5.addRule(RelativeLayout.BELOW, R.id.textView12);

		ImageView l11 = (ImageView) header.findViewById(R.id.i1);
		ImageView l22 = (ImageView) header.findViewById(R.id.i2);
		ImageView l33 = (ImageView) header.findViewById(R.id.i3);
		ImageView l44 = (ImageView) header.findViewById(R.id.i4);
		ImageView l55 = (ImageView) header.findViewById(R.id.i5);

		l11.setLayoutParams(f1);
		l22.setLayoutParams(f2);
		l33.setLayoutParams(f3);
		l44.setLayoutParams(f4);
		l55.setLayoutParams(f5);

		int marginTop = (util.getScreenHeight() / 3) - (util.getScreenwidth() / 8);

		// LinearLayout imageLinear = (LinearLayout)
		// header.findViewById(R.id.imageLinear);

		FrameLayout profileFrame = (FrameLayout) header.findViewById(R.id.frameLayoutHeader);
		FrameLayout.LayoutParams profileParams = new FrameLayout.LayoutParams(profileFrame.getLayoutParams());

		profileParams.height = util.getScreenwidth() / 4;
		profileParams.width = util.getScreenwidth() / 4;
		profileParams.gravity = Gravity.CENTER_HORIZONTAL;

		// profileParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		// profileParams.addRule(RelativeLayout.BELOW, R.id.namePage);
		profileParams.setMargins(0, marginTop, 0, 0);

		// LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(
		// imageLinear.getLayoutParams());
		// llp.width = (int) (util.getScreenwidth() / 4.5);
		// llp.height = (int) (util.getScreenwidth() / 4.5);

		img.setLayoutParams(profileParams);

		ImageView headerImageView = (ImageView) header.findViewById(R.id.imgvadvertise_Object);

		FrameLayout.LayoutParams headerparams = new FrameLayout.LayoutParams(profileFrame.getLayoutParams());

		headerparams.height = util.getScreenHeight() / 3;
		headerparams.width = util.getScreenwidth();
		headerparams.gravity = Gravity.CENTER_HORIZONTAL;

		headerImageView.setLayoutParams(headerparams);

	}

	private void setValue() {

		String ImagePath = currentUser.getImagePath();
		if (ImagePath != null) {
			Bitmap bmp = BitmapFactory.decodeFile(ImagePath);
			img.setBackgroundResource(R.drawable.circle_drawable);

			img.setImageBitmap(Utility.getclip(bmp));
		} else {
			img.setBackgroundResource(R.drawable.circle_drawable);
			img.setImageResource(R.drawable.no_img_profile);

		}

		String name = currentUser.getName();
		String email = currentUser.getEmail();
		String address = currentUser.getAddress();
		String phone = currentUser.getPhonenumber();
		String cellphone = currentUser.getMobailenumber();
		String fax = currentUser.getFaxnumber();
		String date = currentUser.getDate();

		txtname.setText(name);
		txtemail.setText(email);
		txtaddress.setText(address);
		txtphone.setText(phone);
		txtcellphone.setText(cellphone);
		txtfax.setText(fax);
		// txtdate.setText(utile1.getPersianDate(date));

	}

	private void onClick() {
		img.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				String ImagePath = currentUser.getImagePath();
				String name = currentUser.getName();

				DialogShowImage showImage = new DialogShowImage(getActivity(), ImagePath, name);
				showImage.show();
			}
		});

		logout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				// //////////////////////null get current user//////////////////

				SharedPreferences settings = getActivity().getSharedPreferences("User", 0);
				SharedPreferences.Editor editor = settings.edit();

				editor.putBoolean("isLogin", false);

				editor.commit();
				// ////////////////////////////////////////////////
				dbAdapter.open();
				int ad = 0;
				dbAdapter.UpdateAdminAllUser(ad);
				dbAdapter.close();

				FragmentTransaction trans = getActivity().getSupportFragmentManager().beginTransaction();
				trans.replace(R.id.content_frame, new LoginFragment());
				trans.commit();
				TextView txtlike = (TextView) (getActivity()).findViewById(R.id.txtlike);
				txtlike.setVisibility(View.GONE);
				TextView txtcm1 = (TextView) (getActivity()).findViewById(R.id.txtcm);
				txtcm1.setVisibility(View.GONE);

			}

		});

		btnedit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				FragmentTransaction trans = getActivity().getSupportFragmentManager().beginTransaction();
				trans.replace(R.id.content_frame, new EditPersonalFragment());
				trans.addToBackStack(null);

				trans.commit();

			}
		});

		final SharedPreferences tashkhis = getActivity().getSharedPreferences("Id", 0);

		birthDayUsers.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				DomainSend fragment = new DomainSend("BirthDay");

				FragmentTransaction trans = ((MainActivity) getActivity()).getSupportFragmentManager()
						.beginTransaction();

				trans.replace(R.id.content_frame, fragment);
				trans.addToBackStack(null);
				trans.commit();

				tashkhis.edit().putString("enter", "DisplayPersonal").commit();
				tashkhis.edit().putString("FromTableName", "BirthDay").commit();

			}
		});

	}

	private void setFont() {

		txtEdit.setTypeface(util.SetFontCasablanca());
		txtBirthday.setTypeface(util.SetFontCasablanca());

		txtaddress.setTypeface(util.SetFontCasablanca());
		txtcellphone.setTypeface(util.SetFontCasablanca());
		txtphone.setTypeface(util.SetFontCasablanca());
		txtemail.setTypeface(util.SetFontCasablanca());
		txtname.setTypeface(util.SetFontCasablanca());
		txtfax.setTypeface(util.SetFontCasablanca());

	}

	private void getAllDataFromServer(String tablename) {

		if (getActivity() != null) {
			updating = new UpdatingPersonalPage(getActivity());
			updating.delegate = DisplayPersonalInformationFragment.this;
			String[] params = new String[5];
			params[0] = tablename;
			params[1] = "201510210957407981";
			params[2] = serverDate;

			params[3] = "1";
			params[4] = String.valueOf(currentUser.getId());

			updating.execute(params);

			ringProgressDialog = ProgressDialog.show(getActivity(), "", "لطفا منتظر بمانید...", true);
			ringProgressDialog.setCancelable(true);
			isFirstRun = false;
		}

	}

	@Override
	public void processFinish(String output) {
		if (ringProgressDialog != null)
			ringProgressDialog.dismiss();

		if (isFirstRun == true) {

			serverDate = output;
			switch (selectTableId) {

			case 1: {
				tableName = "Object";
				break;
			}
			case 2: {
				tableName = "Anad";
				break;
			}
			case 3: {
				tableName = "Paper";
				break;
			}
			case 4: {
				tableName = "Froum";
				break;
			}
			}
			selectTableId++;
			getAllDataFromServer(tableName);

		} else {

		}
	}

	private void RunServiceDate() {

		if (getActivity() != null) {

			ringProgressDialog = ProgressDialog.show(getActivity(), "", "لطفا منتظر بمانید...", true);
			ringProgressDialog.setCancelable(true);
			date = new ServerDate(getActivity());
			date.delegate = DisplayPersonalInformationFragment.this;
			date.execute("");
			isFirstRun = true;

		}
	}

}
