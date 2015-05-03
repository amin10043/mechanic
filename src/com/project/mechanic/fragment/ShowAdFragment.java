package com.project.mechanic.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.mechanic.MainActivity;
import com.project.mechanic.R;
import com.project.mechanic.entity.Ticket;
import com.project.mechanic.model.DataBaseAdapter;

public class ShowAdFragment extends Fragment {

	int id;
	DataBaseAdapter dbAdapter;
	TextView desc, name, email, phone, mobile, fax;
	ImageView img;
	ImageButton share, like;

	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		((MainActivity) getActivity()).setActivityTitle(R.string.showad);
		id = Integer.valueOf(getArguments().getString("Id"));

		View view = inflater.inflate(R.layout.fragment_showad, null);

		img = (ImageView) view.findViewById(R.id.fragment_anad_imgadd);

		share = (ImageButton) view.findViewById(R.id.imgShare_showAd);
		like = (ImageButton) view.findViewById(R.id.imgLike_showAd);
		desc = (TextView) view.findViewById(R.id.fragment_showad_txt);
		name = (TextView) view.findViewById(R.id.fragment_showad_tx1);
		email = (TextView) view.findViewById(R.id.fragment_showad_tx2);
		phone = (TextView) view.findViewById(R.id.fragment_showad_tx3);
		mobile = (TextView) view.findViewById(R.id.fragment_showad_tx4);
		fax = (TextView) view.findViewById(R.id.fragment_showad_tx5);

		dbAdapter = new DataBaseAdapter(getActivity());

		dbAdapter.open();
		Ticket t = dbAdapter.getTicketById(id);

		byte[] bitmapbyte = t.getImage();
		if (bitmapbyte != null) {
			Bitmap bmp = BitmapFactory.decodeByteArray(bitmapbyte, 0,
					bitmapbyte.length);
			img.setImageBitmap(bmp);
		}
		share.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent sharingIntent = new Intent(
						android.content.Intent.ACTION_SEND);
				sharingIntent.setType("text/plain");
				String shareBody = "Here is the share content body";
				sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,
						"Subject Here");
				sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT,
						shareBody);
				startActivity(Intent.createChooser(sharingIntent,
						"اشتراک از طریق"));

			}
		});
		desc.setText(t.getDesc());
		name.setText(t.getUName());
		email.setText(t.getUEmail());
		phone.setText(t.getUPhone());
		mobile.setText(t.getUMobile());
		fax.setText(t.getUFax());

		dbAdapter.close();

		return view;
	}
}
