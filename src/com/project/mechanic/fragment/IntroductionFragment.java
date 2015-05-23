package com.project.mechanic.fragment;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.project.mechanic.MainActivity;
import com.project.mechanic.R;
import com.project.mechanic.adapter.ExpandIntroduction;
import com.project.mechanic.entity.CommentInObject;
import com.project.mechanic.entity.Object;
import com.project.mechanic.entity.Users;
import com.project.mechanic.model.DataBaseAdapter;
import com.project.mechanic.utility.Utility;

public class IntroductionFragment extends Fragment {

	Utility ut;
	Users CurrentUser;
	PersianDate datePersian;
	String currentDate;
	View header;
	ExpandableListView exListView;
	ExpandIntroduction exadapter;
	int ObjectID;

	ArrayList<CommentInObject> commentGroup, ReplyGroup;
	Map<CommentInObject, List<CommentInObject>> mapCollection;

	private ImageView peykan6, peykan5;
	public RelativeLayout link1, link2;

	public DialogcmtInobject dialog;
	Fragment fragment;

	public LinearLayout AddLike;
	public LinearLayout AddComment;

	public ImageButton Comment;

	LinearLayout.LayoutParams profileParams, headerParams;

	ArrayList<CommentInObject> mylist;
	DataBaseAdapter adapter;
	LinearLayout headImageLinear, profileLinear;

	TextView txtFax, txtAddress, txtPhone, txtCellphone, txtEmail, txtDesc,
			CountLikeIntroduction, CountCommentIntroduction;

	ImageView headerImage, advertise2, profileImage;
	ImageButton Facebook, Instagram, LinkedIn, Google, Site, Twitter, Pdf1,
			Pdf2, Pdf3, Pdf4, phone, cphone, map, email, EditPage, CreatePage;
	Object object;
	byte[] headerbyte, profilebyte, footerbyte;

	SharedPreferences sendDataID;

	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_introduction, null);
		//((MainActivity) getActivity()).setActivityTitle(R.string.brand);

		adapter = new DataBaseAdapter(getActivity());
		ut = new Utility(getActivity());
		datePersian = new PersianDate();
		currentDate = datePersian.todayShamsi();
		header = getActivity().getLayoutInflater().inflate(
				R.layout.header_introduction, null);
		CurrentUser = ut.getCurrentUser();

		// start find view

		exListView = (ExpandableListView) view
				.findViewById(R.id.ExpandIntroduction);
		peykan6 = (ImageView) header.findViewById(R.id.imageButton6);
		peykan5 = (ImageView) header.findViewById(R.id.imageButton7);
		headerImage = (ImageView) header
				.findViewById(R.id.imgvadvertise_Object);
		advertise2 = (ImageView) header
				.findViewById(R.id.imgvadvertise2_Object);
		profileImage = (ImageView) header.findViewById(R.id.icon_pro);

		headImageLinear = (LinearLayout) header
				.findViewById(R.id.headerlinerpageintroduction);
		link1 = (RelativeLayout) header.findViewById(R.id.Layoutlink1);
		link2 = (RelativeLayout) header.findViewById(R.id.Layoutlink2);

		txtFax = (TextView) header.findViewById(R.id.txtFax_Object);
		txtAddress = (TextView) header.findViewById(R.id.txtAddress_Object);
		txtPhone = (TextView) header.findViewById(R.id.txtPhone_Object);
		txtCellphone = (TextView) header.findViewById(R.id.txtCellphone_Object);
		txtDesc = (TextView) header.findViewById(R.id.txtDesc_Object);
		txtEmail = (TextView) header.findViewById(R.id.txtEmail_Object);
		CountLikeIntroduction = (TextView) header
				.findViewById(R.id.countLikeIntroduction);
		CountCommentIntroduction = (TextView) header
				.findViewById(R.id.CountCommentIntroduction);

		AddLike = (LinearLayout) header
				.findViewById(R.id.AddLikeIntroductionLinear);
		AddComment = (LinearLayout) header
				.findViewById(R.id.AddcommentIntroductionLinear);

		Facebook = (ImageButton) header.findViewById(R.id.nfacebook);
		Instagram = (ImageButton) header.findViewById(R.id.ninstagram);
		LinkedIn = (ImageButton) header.findViewById(R.id.nlinkedin);
		Google = (ImageButton) header.findViewById(R.id.ngoogle);
		Site = (ImageButton) header.findViewById(R.id.nsite);
		Twitter = (ImageButton) header.findViewById(R.id.ntwtert);

		phone = (ImageButton) header.findViewById(R.id.phonebtn);
		cphone = (ImageButton) header.findViewById(R.id.cphonebtn);
		map = (ImageButton) header.findViewById(R.id.mapbtn);
		email = (ImageButton) header.findViewById(R.id.emailbtn);

		Pdf1 = (ImageButton) header.findViewById(R.id.btnPdf1_Object);
		Pdf2 = (ImageButton) header.findViewById(R.id.btnPdf2_Object);
		Pdf3 = (ImageButton) header.findViewById(R.id.btnPdf3_Object);
		Pdf4 = (ImageButton) header.findViewById(R.id.btnPdf4_Object);
		profileLinear = (LinearLayout) header
				.findViewById(R.id.linear_id_profile_introduction_page);
		EditPage = (ImageButton) header.findViewById(R.id.ImgbtnEdit);
		CreatePage = (ImageButton) header.findViewById(R.id.ImgbtnCreate);

		sendDataID = getActivity().getSharedPreferences("Id", 0);
		final int ObjectID = sendDataID.getInt("main_Id", -1);

		adapter.open();
		commentGroup = adapter.getAllCommentInObjectById(ObjectID, 0);
		mapCollection = new LinkedHashMap<CommentInObject, List<CommentInObject>>();

		List<CommentInObject> reply = null;
		for (CommentInObject comment : commentGroup) {
			reply = adapter.getReplyCommentIntroduction(ObjectID,
					comment.getId());
			mapCollection.put(comment, reply);
		}

		adapter.close();

		exadapter = new ExpandIntroduction(getActivity(),
				(ArrayList<CommentInObject>) commentGroup, mapCollection, this,
				ObjectID);
		exListView.addHeaderView(header);

		exListView.setAdapter(exadapter);
		adapter.open();

		if (CurrentUser == null) {
		} else {
			if (adapter.isUserLikeIntroductionPage(CurrentUser.getId(),
					ObjectID))
				AddLike.setBackgroundResource(R.drawable.like_froum);
			else
				AddLike.setBackgroundResource(R.drawable.like_froum_off);
		}
		adapter.close();

		profileParams = new LinearLayout.LayoutParams(
				profileLinear.getLayoutParams());

		profileParams.height = ut.getScreenwidth() / 5;
		profileParams.width = ut.getScreenwidth() / 5;

		profileImage.setLayoutParams(profileParams);

		headerParams = new LinearLayout.LayoutParams(
				headImageLinear.getLayoutParams());
		headerParams.width = ut.getScreenwidth();
		headerParams.height = (int) (ut.getScreenHeight() / 2.5);
		headImageLinear.setPadding(0, 0, 0, 20);

		adapter.open();
		int countcmt = adapter.CommentInObject_count(ObjectID);
		CountCommentIntroduction.setText(String.valueOf(countcmt));

		int countlike = adapter.LikeInObject_count(ObjectID);
		CountLikeIntroduction.setText(String.valueOf(countlike));

		object = adapter.getObjectbyid(ObjectID);
		adapter.close();
		if (object == null) {
			return view;
		}

		headerImage.setLayoutParams(headerParams);
		// imagedisplay.setBackgroundResource(R.drawable.profile_account);
		Users user = ut.getCurrentUser();
		if (user == null || ObjectID != user.getId()) {
			EditPage.setVisibility(View.INVISIBLE);

		} else
			EditPage.setVisibility(View.VISIBLE);

		byte[] bitmapbyte = object.getImage1();

		if (bitmapbyte != null) {
			Bitmap bmp1 = BitmapFactory.decodeByteArray(bitmapbyte, 0,
					bitmapbyte.length);
			headerImage.setImageBitmap(bmp1);

		}

		byte[] bitmap = object.getImage2();
		if (bitmap != null) {
			Bitmap bmp2 = BitmapFactory.decodeByteArray(bitmap, 0,
					bitmap.length);
			profileImage.setImageBitmap(bmp2);

		}
		byte[] bitm = object.getImage3();
		if (bitm != null) {
			Bitmap bmp3 = BitmapFactory.decodeByteArray(bitm, 0, bitm.length);
			advertise2.setImageBitmap(bmp3);

		}

		txtFax.setText(object.getFax());
		txtPhone.setText(object.getPhone());
		txtCellphone.setText(object.getCellphone());
		txtEmail.setText(object.getEmail());
		txtAddress.setText(object.getAddress());
		txtDesc.setText(object.getDescription());

		if (object.getObjectBrandTypeId() == 2)
			link2.setVisibility(View.GONE);
		else if (object.getObjectBrandTypeId() == 3)
			link1.setVisibility(View.GONE);
		else if (object.getObjectBrandTypeId() == 1) {
			link1.setVisibility(View.GONE);
			link2.setVisibility(View.GONE);
		}

		if (object.getFacebook() != null)
			Facebook.setImageResource(R.drawable.facebook);
		else
			Facebook.setImageResource(R.drawable.facebook_off);

		if (object.getInstagram() != null)
			Instagram.setImageResource(R.drawable.insta);
		else
			Instagram.setImageResource(R.drawable.insta_off);

		if (object.getLinkedIn() != null)
			LinkedIn.setImageResource(R.drawable.lnkin);
		else
			LinkedIn.setImageResource(R.drawable.lnkin_off);

		if (object.getGoogle() != null)
			Google.setImageResource(R.drawable.google);
		else
			Google.setImageResource(R.drawable.google_off);

		if (object.getSite() != null)
			Site.setImageResource(R.drawable.internet);
		else
			Site.setImageResource(R.drawable.internet_off);

		if (object.getTwitter() != null)
			Twitter.setImageResource(R.drawable.twtr);
		else
			Twitter.setImageResource(R.drawable.twtr_off);

		if (object.getPdf1() != null)
			Pdf1.setImageResource(R.drawable.ic_catalog);
		else
			Pdf1.setImageResource(R.drawable.ic_catalog_off);

		if (object.getPdf2() != null)
			Pdf2.setImageResource(R.drawable.ic_price);

		else
			Pdf2.setImageResource(R.drawable.ic_price_off);

		if (object.getPdf3() != null)
			Pdf3.setImageResource(R.drawable.ic_pdf);
		else
			Pdf3.setImageResource(R.drawable.ic_pdf_off);

		if (object.getPdf4() != null)
			Pdf4.setImageResource(R.drawable.ic_video);
		else
			Pdf4.setImageResource(R.drawable.ic_video_off);

		headerbyte = object.getImage1();
		profilebyte = object.getImage2();
		footerbyte = object.getImage3();

		if (profilebyte != null) {
			Bitmap bmp = BitmapFactory.decodeByteArray(profilebyte, 0,
					profilebyte.length);
			profileImage.setImageBitmap(bmp);
		}
		// advertise.setimage
		Facebook.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (object.getFacebook() != null) {
					String url = "http://" + object.getFacebook();
					FragmentTransaction trans = getActivity()
							.getSupportFragmentManager().beginTransaction();
					trans.replace(R.id.content_frame, new FragmentShowSite(url));
					trans.commit();
				}
			}
		});
		Instagram.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (object.getInstagram() != null) {

					String url = "http://" + object.getInstagram();

					FragmentTransaction trans = getActivity()
							.getSupportFragmentManager().beginTransaction();
					trans.replace(R.id.content_frame, new FragmentShowSite(url));
					trans.commit();
				}

			}
		});

		LinkedIn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (object.getLinkedIn() != null) {

					String url = "http://" + object.getLinkedIn();

					FragmentTransaction trans = getActivity()
							.getSupportFragmentManager().beginTransaction();
					trans.replace(R.id.content_frame, new FragmentShowSite(url));
					trans.commit();
				}

			}
		});

		Google.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (object.getGoogle() != null) {

					String url = "http://" + object.getGoogle();

					FragmentTransaction trans = getActivity()
							.getSupportFragmentManager().beginTransaction();
					trans.replace(R.id.content_frame, new FragmentShowSite(url));
					trans.commit();
				}

			}
		});

		Site.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (object.getSite() != null) {

					String url = "http://" + object.getSite();

					FragmentTransaction trans = getActivity()
							.getSupportFragmentManager().beginTransaction();
					trans.replace(R.id.content_frame, new FragmentShowSite(url));
					trans.commit();
				}

			}
		});

		Twitter.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (object.getTwitter() != null) {

					String url = "http://" + object.getTwitter();

					FragmentTransaction trans = getActivity()
							.getSupportFragmentManager().beginTransaction();
					trans.replace(R.id.content_frame, new FragmentShowSite(url));
					trans.commit();
				}

			}
		});

		Pdf1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (object.getPdf1() != null) {
					String url = "http://" + object.getPdf1();

					FragmentTransaction trans = getActivity()
							.getSupportFragmentManager().beginTransaction();
					trans.replace(R.id.content_frame, new FragmentShowSite(url));
					trans.commit();
				}

			}
		});

		Pdf2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (object.getPdf2() != null) {

					String url = "http://" + object.getPdf2();

					FragmentTransaction trans = getActivity()
							.getSupportFragmentManager().beginTransaction();
					trans.replace(R.id.content_frame, new FragmentShowSite(url));
					trans.commit();
				}

			}
		});

		Pdf3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (object.getPdf3() != null) {

					String url = "http://" + object.getPdf3();

					FragmentTransaction trans = getActivity()
							.getSupportFragmentManager().beginTransaction();
					trans.replace(R.id.content_frame, new FragmentShowSite(url));
					trans.commit();
				}

			}
		});

		Pdf4.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (object.getPdf4() != null) {

					String url = "http://" + object.getPdf4();

					FragmentTransaction trans = getActivity()
							.getSupportFragmentManager().beginTransaction();
					trans.replace(R.id.content_frame, new FragmentShowSite(url));
					trans.commit();
				}

			}
		});

		// IntroductionListAdapter listAdapter = new IntroductionListAdapter(
		// getActivity(), R.layout.raw_froumcmt, mylist);
		// lst.setAdapter(listAdapter);

		// resizeListView(lst);

		link2 = (RelativeLayout) header.findViewById(R.id.Layoutlink2);

		link1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				FragmentTransaction trans = getActivity()
						.getSupportFragmentManager().beginTransaction();
				trans.replace(R.id.content_frame, new ProvinceFragment());
				trans.commit();

			}
		});

		AddComment.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {

				if (CurrentUser == null) {
					Toast.makeText(getActivity(), "ابتدا باید وارد شوید",
							Toast.LENGTH_SHORT).show();
					return;
				} else {
					dialog = new DialogcmtInobject(IntroductionFragment.this,
							getActivity(), R.layout.dialog_addcomment,
							ObjectID, 0);
					dialog.show();
					exadapter.notifyDataSetChanged();
				}

			}
		});

		AddLike.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				adapter.open();

				if (CurrentUser == null) {
					Toast.makeText(getActivity(), "ابتدا باید وارد شوید",
							Toast.LENGTH_SHORT).show();
					return;

				} else {
					if (adapter.isUserLikeIntroductionPage(CurrentUser.getId(),
							ObjectID)) {
						AddLike.setBackgroundResource(R.drawable.like_froum_off);
						adapter.deleteLikeIntroduction(CurrentUser.getId(),
								ObjectID);
						int countlike = adapter.LikeInObject_count(ObjectID);
						CountLikeIntroduction.setText(String.valueOf(countlike));
					} else {
						adapter.insertLikeInObjectToDb(CurrentUser.getId(),
								ObjectID, currentDate, 0);
						AddLike.setBackgroundResource(R.drawable.like_froum);

						int countlike = adapter.LikeInObject_count(ObjectID);
						CountLikeIntroduction.setText(String.valueOf(countlike));
					}

				}
				adapter.close();
			}

		});

		link2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				FragmentTransaction trans = getActivity()
						.getSupportFragmentManager().beginTransaction();
				trans.replace(R.id.content_frame, new ProvinceFragment());
				trans.commit();

			}
		});

		peykan6.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				FragmentTransaction trans = getActivity()
						.getSupportFragmentManager().beginTransaction();
				trans.replace(R.id.content_frame, new ProvinceFragment());
				trans.commit();

			}
		});

		peykan5.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				FragmentTransaction trans = getActivity()
						.getSupportFragmentManager().beginTransaction();
				trans.replace(R.id.content_frame, new ProvinceFragment());
				trans.commit();

			}
		});

		phone.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// Toast.makeText(getActivity(), "ok",
				// Toast.LENGTH_SHORT).show();

				// startActivityForResult(new
				// Intent("android.intent.action.call",Uri.parse("tel:"+
				// txtCellphone.getText().toString())), 1);

				Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"
						+ txtCellphone.getText().toString()));
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(intent);

			}
		});

		cphone.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// Toast.makeText(getActivity(), "ok",
				// Toast.LENGTH_SHORT).show();
				// startActivityForResult(new
				// Intent("android.intent.action.call",Uri.parse("tel:"+
				// txtPhone.getText().toString())), 1);

				Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"
						+ txtPhone.getText().toString()));
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(intent);

			}
		});

		email.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				String b_email = txtEmail.getText().toString();
				Intent email = new Intent(Intent.ACTION_SEND);
				email.putExtra(Intent.EXTRA_EMAIL, new String[] { "b_email" });
				email.setType("message/rfc822");

				startActivity(Intent.createChooser(email,
						"Choose an Email client :"));

			}

		});

		map.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Toast.makeText(getActivity(), "ok", Toast.LENGTH_SHORT).show();
				Intent intent = new Intent(
						android.content.Intent.ACTION_VIEW,
						Uri.parse("https://www.google.com/maps/dir/36.2476613,59.4998502/Mashhad,+Khorasan+Razavi/Khorasan+Razavi,+Mashhad,+Kolahdooz+Blvd,+No.+47/@36.2934197,59.5606058,15z/data=!4m15!4m14!1m0!1m5!1m1!1s0x3f6c911abe4131d7:0xc9c57e3a9318753b!2m2!1d59.6167549!2d36.2604623!1m5!1m1!1s0x3f6c91798c9d172b:0xaf638c4e2e2ac720!2m2!1d59.5749626!2d36.2999667!3e2"));
				startActivity(intent);
			}
		});

		EditPage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				FragmentTransaction trans = getActivity()
						.getSupportFragmentManager().beginTransaction();
				trans.replace(R.id.content_frame,
						new IntroductionEditFragment());
				trans.commit();

			}
		});

		CreatePage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				FragmentTransaction trans = getActivity()
						.getSupportFragmentManager().beginTransaction();
				trans.replace(R.id.content_frame,
						new CreateIntroductionFragment());
				trans.commit();

			}
		});
		return view;

	}

	public void updateList() {
		sendDataID = getActivity().getSharedPreferences("Id", 0);
		final int ObjectID = sendDataID.getInt("main_Id", -1);

		adapter.open();
		commentGroup = adapter.getAllCommentInObjectById(ObjectID, 0);
		mapCollection = new LinkedHashMap<CommentInObject, List<CommentInObject>>();

		List<CommentInObject> reply = null;
		for (CommentInObject comment : commentGroup) {
			reply = adapter.getReplyCommentIntroduction(ObjectID,
					comment.getId());
			mapCollection.put(comment, reply);
		}

		int countcmt = adapter.CommentInObject_count(ObjectID);
		CountCommentIntroduction.setText(String.valueOf(countcmt));
		exadapter = new ExpandIntroduction(getActivity(),
				(ArrayList<CommentInObject>) commentGroup, mapCollection, this,
				ObjectID);
		adapter.close();
		exadapter.notifyDataSetChanged();

		exListView.setAdapter(exadapter);

	}

	// public void updateView3() {
	// adapter.open();
	// sendDataID = getActivity().getSharedPreferences("Id", 0);
	// final int cid = sendDataID.getInt("main_Id", -1);
	// mylist = adapter.getAllCommentInObjectById(cid);
	// CountCommentIntroduction.setText(adapter.CommentInObject_count()
	// .toString());
	// adapter.close();
	// IntroductionListAdapter x = new IntroductionListAdapter(getActivity(),
	// R.layout.raw_froumcmt, mylist);
	// x.notifyDataSetChanged();
	// // lst.setAdapter(x);
	// }

}
