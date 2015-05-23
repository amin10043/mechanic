package com.project.mechanic.fragment;

import java.util.ArrayList;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

import com.project.mechanic.R;
import com.project.mechanic.adapter.LikeFroumNotificationAdapter;
import com.project.mechanic.adapter.LikeNotificationAdapter;
import com.project.mechanic.adapter.LikePaperNotificationAdapter;
import com.project.mechanic.entity.LikeInFroum;
import com.project.mechanic.entity.LikeInObject;
import com.project.mechanic.entity.LikeInPaper;
import com.project.mechanic.model.DataBaseAdapter;

public class Dialog_notificationlike extends Dialog {
	private static final Context Dialog = null;
	private DataBaseAdapter dbadapter;
	private Context context;
	private Fragment fragment;
	private int seen;
	ListView listnotificationlike;
	ListView listnotificationlike1;
	ListView listnotificationlike2;

	public Dialog_notificationlike(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		this.context = context;
		dbadapter = new DataBaseAdapter(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_notificationlike);

		ImageButton btnshowlikef = (ImageButton) findViewById(R.id.btnshowlikef);
		ImageButton btnshowlikeo = (ImageButton) findViewById(R.id.btnshowlikeo);
		ImageButton btnshowlikep = (ImageButton) findViewById(R.id.btnshowlikep);
		final ListView listnewlike = (ListView) findViewById(R.id.listnewlike);

		// listnotificationlike= (ListView)
		// findViewById(R.id.listnotificationlike);
		// listnotificationlike1= (ListView)
		// findViewById(R.id.listnotificationlike1);
		// listnotificationlike2= (ListView)
		// findViewById(R.id.listnotificationlike2);

		dbadapter.open();

		ArrayList<LikeInObject> mylist = dbadapter.getUnseenlike();
		ArrayList<LikeInFroum> mylist1 = dbadapter.getUnseenlikeInFroum();
		ArrayList<LikeInPaper> mylist2 = dbadapter.getUnseenlikeInPaper();
		// CommentInFroum c = dbadapter.getCommentInFroumbyID(1);
		// String [] aa = {c.getDesk(),c.getDesk(),c.getDesk(),c.getDesk()};
		dbadapter.close();

		final LikeNotificationAdapter dataAdapter = new LikeNotificationAdapter(
				context, R.layout.row_notification_list, mylist);
		final LikeFroumNotificationAdapter dataAdapter1 = new LikeFroumNotificationAdapter(
				context, R.layout.row_notification_list, mylist1);
		final LikePaperNotificationAdapter dataAdapter2 = new LikePaperNotificationAdapter(
				context, R.layout.row_notification_list, mylist2);

		btnshowlikef.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				listnewlike.setAdapter(dataAdapter);

			}
		});
		btnshowlikeo.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				listnewlike.setAdapter(dataAdapter1);

			}
		});
		btnshowlikep.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				listnewlike.setAdapter(dataAdapter2);

			}
		});

		// if(listnotificationlike!=null){
		// listnotificationlike.setAdapter(dataAdapter);}
		//
		// else{listnotificationlike.setVisibility(View.INVISIBLE);
		//
		// }
		//
		//
		// if(listnotificationlike1!=null){
		// listnotificationlike1.setAdapter(dataAdapter1); }
		// else{listnotificationlike1.setVisibility(View.INVISIBLE);
		//
		// }
		// if(listnotificationlike2!=null){
		// listnotificationlike2.setAdapter(dataAdapter2); }
		// else{listnotificationlike2.setVisibility(View.INVISIBLE);
		//
		// }

		// listnotificationlike.setAdapter(dataAdapter);
		// listnotificationlike1.setAdapter(dataAdapter1);
		// listnotificationlike2.setAdapter(dataAdapter2);

	}

}
