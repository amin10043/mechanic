package com.project.mechanic.fragment;

import java.io.ByteArrayOutputStream;

import android.R.color;
import android.annotation.SuppressLint;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.project.mechanic.R;
import com.project.mechanic.model.DataBaseAdapter;

public class RegisterFragment extends Fragment {

	protected static final Context Contaxt = null;
	int resourceId;
	Context context;
	Fragment fragment;
	int ticketTypeID;
	int ProvinceId;
	ImageView btnaddpic1;

	// byte[] byteImage1 = null;
	// ContentValues newValues = new ContentValues();
	// public RegisterFragment(Context context, int resourceId, Fragment
	// fragment,
	// int ticketTypeID, int ProvinceId) {
	//
	// // TODO Auto-generated constructor stub
	// this.resourceId = resourceId;
	// this.context = context;
	// this.fragment = fragment;
	// this.ticketTypeID = ticketTypeID;
	// this.ProvinceId = ProvinceId;
	// }

	protected static final int RESULT_LOAD_IMAGE = 1;
	DataBaseAdapter dbAdapter;
	private Activity view;

	
	
	private int columnWidth;
 LinearLayout l1,l2;

	

	public static byte[] getBitmapAsByteArray(Bitmap bitmap) {

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		bitmap.compress(CompressFormat.PNG, 0, outputStream);
		return outputStream.toByteArray();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_register, null);

		
		dbAdapter = new DataBaseAdapter(getActivity());
		 btnaddpic1=(ImageView) view.findViewById(R.id.btnaddpic);


		// dbAdapter = new DataBaseAdapter(getActivity());
		btnaddpic1 = (ImageView) view.findViewById(R.id.btnaddpic);

		Button btncan = (Button) view.findViewById(R.id.btncancle2);
		Button btnreg = (Button) view.findViewById(R.id.btnreg2);
		
		final EditText editname = (EditText) view
				.findViewById(R.id.editTextname);
		final EditText edituser = (EditText) view
				.findViewById(R.id.editTextuser);
		final EditText editpass = (EditText) view
				.findViewById(R.id.editTextpass);
		
		
//		 l1 = (LinearLayout) view.findViewById(R.id.l1);
	
 btnaddpic1.setBackgroundResource(R.drawable.i13);
//      columnWidth = (int) (getScreenWidth() /3);
//	   LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(l1.getLayoutParams());		
//	   lp.width=columnWidth;
//      lp.height=columnWidth;
//      btnaddpic1.setLayoutParams(lp);
//   l1.addView(btnaddpic1);
		    btnaddpic1.getLayoutParams().height = 150;
		    btnaddpic1.getLayoutParams().width = 150;
		    btnaddpic1.requestLayout();
		
		
		
		btnreg.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				final String Name = editname.getText().toString();
				final String Email = edituser.getText().toString();
				final String Pass = editpass.getText().toString();

				if (Name.equals("") && Email.equals("") && Pass.equals("")) {

					Toast.makeText(getActivity(),
							"لطفا فيلدهاي مورد نظر را پر کنيد  ",
							Toast.LENGTH_SHORT).show();

				}

				else {

					//
					// // String name = "CoderzHeaven";
					// // newValues.put("name", name);
					// try {
					// FileInputStream instream = new
					// FileInputStream(picturePath);
					// BufferedInputStream bif = new
					// BufferedInputStream(instream);
					// byteImage1 = new byte[bif.available()];
					// bif.read(byteImage1);
					// newValues.put("image", byteImage1);
					// // long ret = dbAdapter.insert(TABLE_NAME, null,
					// newValues);
					//
					// } catch (IOException e) {
					// // textView.append("Error Exception : " +
					// e.getMessage());
					// }
					// dbAdapter.close();
					// first Insert user to WS then insert to local
					dbAdapter = new DataBaseAdapter(getActivity());
					dbAdapter.open();
					Bitmap bitmap = ((BitmapDrawable) btnaddpic1.getDrawable())
							.getBitmap();
					byte[] Image = getBitmapAsByteArray(bitmap);
					Toast.makeText(getActivity(), "اطلاعات مورد نظر ثبت شد",
							Toast.LENGTH_SHORT).show();

					dbAdapter.inserUserToDb(Name, Email, Pass, null, Image, 0);

					dbAdapter.close();
					
					editname.setText("");
					edituser.setText("");
				editpass.setText("");
				 
				}

			}
		});

		btncan.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				FragmentTransaction trans = getActivity()
						.getSupportFragmentManager().beginTransaction();
				trans.replace(R.id.content_frame, new LoginFragment());
				trans.commit();
			}
		});

		btnaddpic1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// Toast.makeText(getActivity(), "ok",
				// Toast.LENGTH_LONG).show();

				Intent i = new Intent(
						Intent.ACTION_PICK,
						android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

				getActivity().startActivityFromFragment(RegisterFragment.this,
						i, RESULT_LOAD_IMAGE);
				
				
				
			}
		});

		return view;
	}

	public void onActivityResult(int requestCode, int resultCode, Intent data) {

		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == RESULT_LOAD_IMAGE
				&& resultCode == Activity.RESULT_OK && null != data) {
			Uri selectedImage = data.getData();
			String[] filePathColumn = { MediaStore.Images.Media.DATA };

			Cursor cursor = getActivity().getContentResolver().query(
					selectedImage, filePathColumn, null, null, null);
			cursor.moveToFirst();

			int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
			String picturePath = cursor.getString(columnIndex);
			cursor.close();

			// ImageView btnaddpic1 = (ImageView) view
			// .findViewById(R.id.btnaddpic);
			btnaddpic1.setImageBitmap(BitmapFactory.decodeFile(picturePath));
		}

	}

	private EditText findViewById(int edittextuser) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
	
	
	
	
	@SuppressLint("NewApi")
	public int getScreenWidth() {
		int columnWidth;
	WindowManager wm = (WindowManager) getActivity()
				.getSystemService(Context.WINDOW_SERVICE);
	Display display = wm.getDefaultDisplay();

		final Point point = new Point();
	try {
			display.getSize(point);
		} catch (java.lang.NoSuchMethodError ignore) { // Older device
		point.x = display.getWidth();
			point.y = display.getHeight();
		}
		columnWidth = point.x;
		return columnWidth;
}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
