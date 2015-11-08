package com.project.mechanic.fragment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.Map;

import com.project.mechanic.R;
import com.project.mechanic.entity.Users;
import com.project.mechanic.inter.AsyncInterface;
import com.project.mechanic.inter.SaveAsyncInterface;
import com.project.mechanic.model.DataBaseAdapter;
import com.project.mechanic.utility.Utility;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.CursorJoiner.Result;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class DialogpostTitleFragment extends DialogFragment {
	
	Context mContext;
	ImageView btnPickFile;
	ImageView ShowImage;
	Button btnClearImage;
	Utility util;
	Users user;
	View view;
	byte[] ImageConvertedToByte = null;
	
	LinearLayout.LayoutParams lp2;
	private Uri mImageCaptureUri;
	private File mFileTemp;
	public static final String TEMP_PHOTO_FILE_NAME = "temp_photo.jpg";
	private static final int CAMERA_CODE = 101, GALLERY_CODE = 201,
			CROPING_CODE = 301;
	final int PIC_CROP = 10;
	
	public DialogpostTitleFragment() {
        mContext = getActivity();
    }
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.dialog_addtitlepostfragment, container,
				false);
		getDialog().setTitle("ایجاد پست جدید");
		
		ShowImage = (ImageView) rootView.findViewById(R.id.btnPickFile);
		
		btnPickFile = (ImageView) rootView.findViewById(R.id.btnPickFile);
		btnPickFile.setOnClickListener(new View.OnClickListener() {
			   public void onClick(View v) {
				   selectImageOption();
			   }        
			});
		
		return rootView;
	}
	
	public static DialogpostTitleFragment newInstance() {
		DialogpostTitleFragment f = new DialogpostTitleFragment();
        return f;
    }
	
	
	private void selectImageOption() {
		final CharSequence[] items = { "از دوربین", "از گالری تصاویر", "انصراف" };

		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle("افزودن تصویر");
		builder.setItems(items, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int item) {

				if (items[item].equals("از دوربین")) {

					Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

					File f = new File(android.os.Environment
							.getExternalStorageDirectory(), "temp1.jpg");

					mImageCaptureUri = Uri.fromFile(f);
					intent.putExtra(MediaStore.EXTRA_OUTPUT, mImageCaptureUri);

					//fragment.getActivity().startActivityForResult(intent, CAMERA_CODE);

				} else if (items[item].equals("از گالری تصاویر")) {

					Intent i = new Intent(
							Intent.ACTION_PICK,
							android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

					getActivity().startActivityFromFragment(
							DialogpostTitleFragment.this, i, GALLERY_CODE);
					// Intent intent = new Intent();
					// intent.setType("image/*");
					// intent.setAction(Intent.ACTION_GET_CONTENT);
					// try {
					// intent.putExtra("return-data", true);
					// startActivityForResult(
					// Intent.createChooser(intent, "تکمیل کار با"),
					// GALLERY_CODE);
					// } catch (ActivityNotFoundException e) {
					// }

				} else if (items[item].equals("انصراف")) {
					dialog.dismiss();
				}
			}
		});

		builder.show();
	}

	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		switch (requestCode) {
		case GALLERY_CODE:
			if (resultCode == this.getActivity().RESULT_OK) {

				mImageCaptureUri = data.getData();
				try {
					Bitmap bitmapImage = decodeBitmap(mImageCaptureUri);
					ShowImage.setImageBitmap(bitmapImage);
					ImageConvertedToByte = Utility.CompressBitmap(bitmapImage);

					if( ImageConvertedToByte != null ){
						ShowImage.setVisibility(View.VISIBLE);
						btnClearImage.setVisibility(View.VISIBLE);
					}
					
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public Bitmap decodeBitmap(Uri selectedImage) throws FileNotFoundException {
		BitmapFactory.Options o = new BitmapFactory.Options();
		o.inJustDecodeBounds = true;
		BitmapFactory.decodeStream(this.getActivity().getContentResolver()
				.openInputStream(selectedImage), null, o);

		final int REQUIRED_SIZE = 100;

		int width_tmp = o.outWidth, height_tmp = o.outHeight;
		int scale = 1;
		while (true) {
			if (width_tmp / 2 < REQUIRED_SIZE || height_tmp / 2 < REQUIRED_SIZE) {
				break;
			}
			width_tmp /= 2;
			height_tmp /= 2;
			scale *= 2;
		}

		BitmapFactory.Options o2 = new BitmapFactory.Options();
		o2.inSampleSize = scale;
		return BitmapFactory.decodeStream(this.getActivity()
				.getContentResolver().openInputStream(selectedImage), null, o2);
	}
	
}