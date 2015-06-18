package com.project.mechanic.fragment;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.project.mechanic.MainActivity;
import com.project.mechanic.R;
import com.project.mechanic.adapter.ExpandableCommentFroum;
import com.project.mechanic.entity.CommentInFroum;
import com.project.mechanic.entity.Froum;
import com.project.mechanic.entity.Users;
import com.project.mechanic.inter.AsyncInterface;
import com.project.mechanic.model.DataBaseAdapter;
import com.project.mechanic.service.Deleting;
import com.project.mechanic.service.Saving;
import com.project.mechanic.service.ServerDate;
import com.project.mechanic.utility.Utility;

public class FroumFragment extends Fragment implements AsyncInterface {

	// start defined by masoud

	DataBaseAdapter adapter;
	ExpandableCommentFroum exadapter;

	TextView titletxt, descriptiontxt, dateTopic, countComment, countLike,
			nametxt;
	LinearLayout addComment, likeTopic;
	ImageButton sharebtn;
	ImageView profileImg;
	int froumid;

	Froum topics;

	DialogcmtInfroum dialog;
	ArrayList<CommentInFroum> commentGroup, ReplyGroup;
	// String currentDate;

	Map<CommentInFroum, List<CommentInFroum>> mapCollection;
	ExpandableListView exlistview;

	View header;
	Users CurrentUser;
	int IDcurrentUser;
	// PersianDate date;
	Utility util;
	int id;
	Users user;

	Saving saving;
	Deleting deleting;
	Map<String, String> params;

	ProgressDialog ringProgressDialog;

	String serverDate = "";
	ServerDate date;

	// end defined by masoud

	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceStdataate) {

		((MainActivity) getActivity()).setActivityTitle(R.string.Forums);
		View view = inflater.inflate(R.layout.fragment_froum, null);

		adapter = new DataBaseAdapter(getActivity());
		util = new Utility(getActivity());

		user = new Users();

		// date = new PersianDate();
		// currentDate = date.todayShamsi();

		header = getActivity().getLayoutInflater().inflate(
				R.layout.header_expandable, null);

		// start find view

		titletxt = (TextView) header.findViewById(R.id.title_topic);
		descriptiontxt = (TextView) header.findViewById(R.id.description_topic);
		dateTopic = (TextView) header.findViewById(R.id.date_cc);
		countComment = (TextView) header
				.findViewById(R.id.numberOfCommentTopic);
		countLike = (TextView) header.findViewById(R.id.txtNumofLike_CmtFroum);
		nametxt = (TextView) header.findViewById(R.id.name_cc);

		addComment = (LinearLayout) header.findViewById(R.id.addCommentToTopic);
		likeTopic = (LinearLayout) header.findViewById(R.id.LikeTopicLinear);

		sharebtn = (ImageButton) header.findViewById(R.id.sharefroumicon);
		profileImg = (ImageView) header.findViewById(R.id.iconfroumtitle);
		exlistview = (ExpandableListView) view.findViewById(R.id.commentlist);

		// end find view

		if (getArguments().getString("Id") != null)
			froumid = Integer.valueOf(getArguments().getString("Id"));

		adapter.open();
		CurrentUser = util.getCurrentUser();
		if (CurrentUser == null) {

		}

		else
			IDcurrentUser = CurrentUser.getId();

		topics = adapter.getFroumItembyid(froumid);
		Users u = adapter.getUserbyid(topics.getUserId());
		if (u != null) {

			nametxt.setText(u.getName());
			LinearLayout rl = (LinearLayout) header
					.findViewById(R.id.profileLinearcommenterinContinue);

			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
					rl.getLayoutParams());

			lp.width = util.getScreenwidth() / 7;
			lp.height = util.getScreenwidth() / 7;
			lp.setMargins(5, 5, 5, 5);
			profileImg.setLayoutParams(lp);

			if (u.getImage() == null) {
				profileImg.setImageResource(R.drawable.no_img_profile);
				profileImg.setLayoutParams(lp);

			} else {
				byte[] bytepic = u.getImage();

				Bitmap bmp = BitmapFactory.decodeByteArray(bytepic, 0,
						bytepic.length);
				profileImg.setImageBitmap(Utility.getRoundedCornerBitmap(bmp,
						50));

				profileImg.setLayoutParams(lp);
			}
		}
		titletxt.setText(topics.getTitle());
		descriptiontxt.setText(topics.getDescription());
		countComment.setText(adapter.CommentInFroum_count(froumid).toString());
		countLike.setText(adapter.LikeInFroum_count(froumid).toString());
		dateTopic.setText(util.getPersianDate(topics.getDate()));

		addComment.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {

				if (CurrentUser == null) {
					Toast.makeText(getActivity(),
							"برای درج کامنت ابتدا باید وارد شوید",
							Toast.LENGTH_SHORT).show();

				} else {
					dialog = new DialogcmtInfroum(FroumFragment.this, 0,
							getActivity(), froumid, R.layout.dialog_addcomment);
					dialog.show();
					exadapter.notifyDataSetChanged();
				}
			}
		});

		commentGroup = adapter.getCommentInFroumbyPaperid(froumid, 0);

		mapCollection = new LinkedHashMap<CommentInFroum, List<CommentInFroum>>();

		List<CommentInFroum> reply = null;
		for (CommentInFroum comment : commentGroup) {
			reply = adapter
					.getReplyCommentbyCommentID(froumid, comment.getId());
			mapCollection.put(comment, reply);
		}

		exlistview.addHeaderView(header);
		exadapter = new ExpandableCommentFroum(getActivity(),
				(ArrayList<CommentInFroum>) commentGroup, mapCollection, this,
				froumid);

		exadapter.notifyDataSetChanged();

		exlistview.setAdapter(exadapter);

		if (CurrentUser == null) {
			likeTopic.setBackgroundResource(R.drawable.like_froum_off);
		} else {
			if (adapter.isUserLikedFroum(CurrentUser.getId(), froumid))
				likeTopic.setBackgroundResource(R.drawable.like_froum);
			else

				likeTopic.setBackgroundResource(R.drawable.like_froum_off);
		}
		adapter.close();

		likeTopic.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				adapter.open();

				if (CurrentUser == null) {
					Toast.makeText(getActivity(),
							"برای درج لایک ابتدا باید وارد شوید",
							Toast.LENGTH_SHORT).show();
				} else {

					date = new ServerDate(getActivity());
					date.delegate = FroumFragment.this;
					date.execute("");

				}
				adapter.close();

			}

		});

		sharebtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				String body = topics.getDescription() + "\n"
						+ " مشاهده کامل گفتگو در: "
						+ "<a href=\"mechanical://SplashActivity\">اینجا</a> ";
				Intent sharingIntent = new Intent(
						android.content.Intent.ACTION_SEND);
				sharingIntent.setType("text/html");
				sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,
						topics.getTitle());
				sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT,
						Html.fromHtml(body));
				startActivity(Intent.createChooser(sharingIntent,
						"اشتراک از طریق"));
			}
		});

		return view;
	}

	public int getFroumId() {
		return id;
	}

	public int getDipsFromPixel(float pixels) {
		// Get the screen's density scale
		final float scale = getResources().getDisplayMetrics().density;
		// Convert the dps to pixels, based on density scale
		return (int) (pixels * scale + 0.5f);
	}

	public void updateList() {
		adapter.open();

		commentGroup = adapter.getCommentInFroumbyPaperid(froumid, 0);

		mapCollection = new LinkedHashMap<CommentInFroum, List<CommentInFroum>>();

		List<CommentInFroum> reply = null;
		for (CommentInFroum comment : commentGroup) {
			reply = adapter
					.getReplyCommentbyCommentID(froumid, comment.getId());
			mapCollection.put(comment, reply);
		}

		countComment.setText(adapter.CommentInFroum_count(froumid).toString());

		exadapter = new ExpandableCommentFroum(getActivity(),
				(ArrayList<CommentInFroum>) commentGroup, mapCollection, this,
				froumid);

		exadapter.notifyDataSetChanged();

		exlistview.setAdapter(exadapter);
		adapter.close();

	}

	public void expandingList(int position) {
		if (exlistview.isGroupExpanded(position)) {

			Toast.makeText(getActivity(), "collapse", 0).show();

		} else
			exlistview.expandGroup(position);

		exadapter.notifyDataSetChanged();

	}

	@Override
	public void processFinish(String output) {
		if (ringProgressDialog != null) {
			ringProgressDialog.dismiss();
		}

		int id = -1;

		try {
			id = Integer.valueOf(output);
			adapter.open();
			if (adapter.isUserLikedFroum(CurrentUser.getId(), froumid)) {
				adapter.deleteLikeFromFroum(CurrentUser.getId(), froumid);
				likeTopic.setBackgroundResource(R.drawable.like_froum_off);

				countLike
						.setText(adapter.LikeInFroum_count(froumid).toString());

			} else {
				adapter.insertLikeInFroumToDb(CurrentUser.getId(), froumid,
						serverDate, 0);
				likeTopic.setBackgroundResource(R.drawable.like_froum);

				countLike
						.setText(adapter.LikeInFroum_count(froumid).toString());
			}
			adapter.close();

		} catch (NumberFormatException ex) {
			if (output != null
					&& !(output.contains("Exception") || output
							.contains("java"))) {
				adapter.open();

				if (adapter.isUserLikedFroum(CurrentUser.getId(), froumid)) {
					adapter.open();
					// int c = adapter.LikeInFroum_count(ItemId) - 1;
					// countLikeFroum.setText(String.valueOf(c));

					params = new LinkedHashMap<String, String>();
					deleting = new Deleting(getActivity());
					deleting.delegate = FroumFragment.this;

					params.put("TableName", "LikeInFroum");
					params.put("UserId", String.valueOf(CurrentUser.getId()));
					params.put("FroumId", String.valueOf(froumid));

					deleting.execute(params);

					ringProgressDialog = ProgressDialog.show(getActivity(), "",
							"لطفا منتظر بمانید...", true);

					ringProgressDialog.setCancelable(true);
					new Thread(new Runnable() {

						@Override
						public void run() {

							try {

								Thread.sleep(10000);

							} catch (Exception e) {

							}
						}
					}).start();

					adapter.close();
				} else {
					adapter.open();
					params = new LinkedHashMap<String, String>();
					saving = new Saving(getActivity());
					saving.delegate = FroumFragment.this;

					params.put("TableName", "LikeInFroum");

					params.put("UserId", String.valueOf(CurrentUser.getId()));
					params.put("FroumId", String.valueOf(froumid));
					params.put("CommentId", "0");
					params.put("Date", output);
					params.put("IsUpdate", "0");
					params.put("Id", "0");

					serverDate = output;

					saving.execute(params);

					ringProgressDialog = ProgressDialog.show(getActivity(), "",
							"لطفا منتظر بمانید...", true);

					ringProgressDialog.setCancelable(true);
					new Thread(new Runnable() {

						@Override
						public void run() {

							try {

								Thread.sleep(10000);

							} catch (Exception e) {

							}
						}
					}).start();

					// countLikeFroum.setText(adapter
					// .LikeInFroum_count(ItemId).toString());

					adapter.close();

				}
				adapter.close();

			} else {
				Toast.makeText(getActivity(),
						"خطا در ثبت. پاسخ نا مشخص از سرور", Toast.LENGTH_SHORT)
						.show();
			}
		}

		catch (Exception e) {

			Toast.makeText(getActivity(), "خطا در ثبت", Toast.LENGTH_SHORT)
					.show();
		}
	}

}
