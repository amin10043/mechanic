package com.project.mechanic.chatAdapter;

import java.util.List;

import com.project.mechanic.R;
import com.project.mechanic.utility.Utility;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class AddMemberAdapter extends ArrayAdapter<String> {
	List<String> items;
	Context context;
	Utility util;

	public AddMemberAdapter(Context context, int resource, List<String> items) {

		super(context, resource, items);
		this.context = context;
		this.items = items;
		util = new Utility(context);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		convertView = inflater.inflate(R.layout.row_select_member_admin, null);

		TextView name = (TextView) convertView.findViewById(R.id.Rowobjecttxt);

		name.setText(items.get(position));
		name.setTypeface(util.SetFontCasablanca());

		return convertView;
	}

}
