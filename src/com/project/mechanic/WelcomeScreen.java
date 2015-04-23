package com.project.mechanic;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Point;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.project.mechanic.adapter.WelcomeScreenAdapter;

public class WelcomeScreen extends Activity {
	// int SPLASH_DISPLAY_TIME = 30000;
	// Handler handler;
	// Runnable runnable;

	private WelcomeScreenAdapter adapter;
	private GridView gridView;
	private ImageButton btnNext, btnExit, btnins1, btnint1, btngp1, btnfb1,
			btntw1, btnlink1;

	LinearLayout row1, row2, row3, row4, row5, row6, row7, row8,
			row_Displacement, row_network;
	ImageButton img1, img2, img3, img4, img5, img6, img7, img8, img9, img10,
			img11, img12, img13, img14, img15, img16, img17, img18, img19,
			img20, img21, img22, img23, img24, next_btn, pre_btn, i1, i2, i3,
			i4, i5, i6;
	private int column = 3;
	int gridePadding = 1;
	private int columnWidth;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_welcome_screen);

		initialize();
		clickItem();
		//
		// handler = new Handler();
		// runnable = new Runnable() {
		// public void run() {
		//
		// Intent intent = new Intent();
		// intent.setClass(WelcomeScreen.this, MainActivity.class);
		//
		// startActivity(intent);
		// finish();
		//
		// overridePendingTransition(R.layout.splash_out,
		// R.layout.splash_in);
		//
		// }
		// };
		// handler.postDelayed(runnable, SPLASH_DISPLAY_TIME);

		int[] image = { R.drawable.up2, R.drawable.on2, R.drawable.or2,
				R.drawable.g1, R.drawable.g2, R.drawable.g3, R.drawable.tayan,
				R.drawable.tayan, R.drawable.tayan, R.drawable.tayan,
				R.drawable.tayan, R.drawable.tayan, R.drawable.tayan,
				R.drawable.tayan, R.drawable.tayan, R.drawable.tayan,
				R.drawable.tayan, R.drawable.tayan, R.drawable.tayan,
				R.drawable.tayan, R.drawable.tayan, R.drawable.tayan,
				R.drawable.tayan, R.drawable.tayan, };

		Resources r = getResources();
		float padding = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
				gridePadding, r.getDisplayMetrics());
		columnWidth = (int) (getScreenWidth() / column);

		initialize();
		setBackground();
		setMinMax();
		Adding();
		setParams();
		clickItem();

		// padding((int) padding);

	}

	@SuppressLint("NewApi")
	public int getScreenWidth() {
		int columnWidth;
		WindowManager wm = (WindowManager) this
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

	private void initialize() {
		row1 = (LinearLayout) findViewById(R.id.row1_linear);
		row2 = (LinearLayout) findViewById(R.id.row2_linear);
		row3 = (LinearLayout) findViewById(R.id.row3_linear);
		row4 = (LinearLayout) findViewById(R.id.row4_linear);
		row5 = (LinearLayout) findViewById(R.id.row5_linear);
		row6 = (LinearLayout) findViewById(R.id.row6_linear);
		row7 = (LinearLayout) findViewById(R.id.row7_linear);
		row8 = (LinearLayout) findViewById(R.id.row8_linear);
		row_Displacement = (LinearLayout) findViewById(R.id.row_Displacement);
		row_network = (LinearLayout) findViewById(R.id.row_network);

		img1 = new ImageButton(WelcomeScreen.this);
		img2 = new ImageButton(WelcomeScreen.this);
		img3 = new ImageButton(WelcomeScreen.this);

		img4 = new ImageButton(WelcomeScreen.this);
		img5 = new ImageButton(WelcomeScreen.this);
		img6 = new ImageButton(WelcomeScreen.this);

		img7 = new ImageButton(WelcomeScreen.this);
		img8 = new ImageButton(WelcomeScreen.this);
		img9 = new ImageButton(WelcomeScreen.this);

		img10 = new ImageButton(WelcomeScreen.this);
		img11 = new ImageButton(WelcomeScreen.this);
		img12 = new ImageButton(WelcomeScreen.this);

		img13 = new ImageButton(WelcomeScreen.this);
		img14 = new ImageButton(WelcomeScreen.this);
		img15 = new ImageButton(WelcomeScreen.this);

		img16 = new ImageButton(WelcomeScreen.this);
		img17 = new ImageButton(WelcomeScreen.this);
		img18 = new ImageButton(WelcomeScreen.this);

		img19 = new ImageButton(WelcomeScreen.this);
		img20 = new ImageButton(WelcomeScreen.this);
		img21 = new ImageButton(WelcomeScreen.this);

		img22 = new ImageButton(WelcomeScreen.this);
		img23 = new ImageButton(WelcomeScreen.this);
		img24 = new ImageButton(WelcomeScreen.this);

		next_btn = new ImageButton(WelcomeScreen.this);
		pre_btn = new ImageButton(WelcomeScreen.this);

		i1 = new ImageButton(WelcomeScreen.this);
		i2 = new ImageButton(WelcomeScreen.this);
		i3 = new ImageButton(WelcomeScreen.this);
		i4 = new ImageButton(WelcomeScreen.this);
		i5 = new ImageButton(WelcomeScreen.this);
		i6 = new ImageButton(WelcomeScreen.this);

	}

	private void setBackground() {
		img1.setBackgroundResource(R.drawable.up2);
		img2.setBackgroundResource(R.drawable.on2);
		img3.setBackgroundResource(R.drawable.or2);

		img4.setBackgroundResource(R.drawable.up1);
		img5.setBackgroundResource(R.drawable.on1);
		img6.setBackgroundResource(R.drawable.or1);

		img7.setBackgroundResource(R.drawable.up1);
		img8.setBackgroundResource(R.drawable.on1);
		img9.setBackgroundResource(R.drawable.or1);

		img10.setBackgroundResource(R.drawable.up2);
		img11.setBackgroundResource(R.drawable.on2);
		img12.setBackgroundResource(R.drawable.or2);

		img13.setBackgroundResource(R.drawable.up1);
		img14.setBackgroundResource(R.drawable.on1);
		img15.setBackgroundResource(R.drawable.or1);

		img16.setBackgroundResource(R.drawable.up2);
		img17.setBackgroundResource(R.drawable.on2);
		img18.setBackgroundResource(R.drawable.or2);

		img19.setBackgroundResource(R.drawable.up1);
		img20.setBackgroundResource(R.drawable.on1);
		img21.setBackgroundResource(R.drawable.or1);

		img22.setBackgroundResource(R.drawable.up2);
		img23.setBackgroundResource(R.drawable.on2);
		img24.setBackgroundResource(R.drawable.or2);

		next_btn.setBackgroundResource(R.drawable.next);
		pre_btn.setBackgroundResource(R.drawable.prev);

		i1.setBackgroundResource(R.drawable.i4);
		i2.setBackgroundResource(R.drawable.i6);
		i3.setBackgroundResource(R.drawable.i5);
		i4.setBackgroundResource(R.drawable.i2);
		i5.setBackgroundResource(R.drawable.i3);
		i6.setBackgroundResource(R.drawable.i1);

	}

	private void setMinMax() {
		img1.setMinimumHeight(columnWidth);
		img1.setMinimumWidth(columnWidth);

		img2.setMinimumHeight(columnWidth);
		img2.setMinimumWidth(columnWidth);

		img3.setMinimumHeight(columnWidth);
		img3.setMinimumWidth(columnWidth);

		img4.setMinimumHeight(columnWidth);
		img4.setMinimumWidth(columnWidth);

		img5.setMinimumHeight(columnWidth);
		img5.setMinimumWidth(columnWidth);

		img6.setMinimumHeight(columnWidth);
		img6.setMinimumWidth(columnWidth);

		img7.setMinimumHeight(columnWidth);
		img7.setMinimumWidth(columnWidth);

		img8.setMinimumHeight(columnWidth);
		img8.setMinimumWidth(columnWidth);

		img9.setMinimumHeight(columnWidth);
		img9.setMinimumWidth(columnWidth);

		img10.setMinimumHeight(columnWidth);
		img10.setMinimumWidth(columnWidth);

		img11.setMinimumHeight(columnWidth);
		img11.setMinimumWidth(columnWidth);

		img12.setMinimumHeight(columnWidth);
		img12.setMinimumWidth(columnWidth);

		img13.setMinimumHeight(columnWidth);
		img13.setMinimumWidth(columnWidth);

		img14.setMinimumHeight(columnWidth);
		img14.setMinimumWidth(columnWidth);

		img15.setMinimumHeight(columnWidth);
		img15.setMinimumWidth(columnWidth);

		img16.setMinimumHeight(columnWidth);
		img16.setMinimumWidth(columnWidth);

		img17.setMinimumHeight(columnWidth);
		img17.setMinimumWidth(columnWidth);

		img18.setMinimumHeight(columnWidth);
		img18.setMinimumWidth(columnWidth);

		img19.setMinimumHeight(columnWidth);
		img19.setMinimumWidth(columnWidth);

		img20.setMinimumHeight(columnWidth);
		img20.setMinimumWidth(columnWidth);

		img21.setMinimumHeight(columnWidth);
		img21.setMinimumWidth(columnWidth);

		img22.setMinimumHeight(columnWidth);
		img22.setMinimumWidth(columnWidth);

		img23.setMinimumHeight(columnWidth);
		img23.setMinimumWidth(columnWidth);

		img24.setMinimumHeight(columnWidth);
		img24.setMinimumWidth(columnWidth);

	}

	private void Adding() {

		row1.addView(img1);
		row1.addView(img2);
		row1.addView(img3);

		row2.addView(img4);
		row2.addView(img5);
		row2.addView(img6);

		row3.addView(img7);
		row3.addView(img8);
		row3.addView(img9);

		row4.addView(img10);
		row4.addView(img11);
		row4.addView(img12);

		row5.addView(img13);
		row5.addView(img14);
		row5.addView(img15);

		row6.addView(img16);
		row6.addView(img17);
		row6.addView(img18);

		row7.addView(img19);
		row7.addView(img20);
		row7.addView(img21);

		row8.addView(img22);
		row8.addView(img23);
		row8.addView(img24);

		row_Displacement.addView(pre_btn);
		row_Displacement.addView(next_btn);

		row_network.addView(i1);
		row_network.addView(i2);
		row_network.addView(i3);
		row_network.addView(i4);
		row_network.addView(i5);
		row_network.addView(i6);

	}

	private void padding(int padding) {
		row1.setPadding((int) padding, (int) padding, (int) padding,
				(int) padding);

		row2.setPadding((int) padding, (int) padding, (int) padding,
				(int) padding);
		row3.setPadding((int) padding, (int) padding, (int) padding,
				(int) padding);
		row4.setPadding((int) padding, (int) padding, (int) padding,
				(int) padding);
		row5.setPadding((int) padding, (int) padding, (int) padding,
				(int) padding);
		row6.setPadding((int) padding, (int) padding, (int) padding,
				(int) padding);
		row7.setPadding((int) padding, (int) padding, (int) padding,
				(int) padding);
		row8.setPadding((int) padding, (int) padding, (int) padding,
				(int) padding);

	}

	private void setParams() {

		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
				row1.getLayoutParams());
		lp.width = columnWidth;
		lp.height = columnWidth;
		lp.setMargins(1, 1, 1, 1);

		img1.setLayoutParams(lp);
		img2.setLayoutParams(lp);
		img3.setLayoutParams(lp);

		img4.setLayoutParams(lp);
		img5.setLayoutParams(lp);
		img6.setLayoutParams(lp);

		img7.setLayoutParams(lp);
		img8.setLayoutParams(lp);
		img9.setLayoutParams(lp);

		img10.setLayoutParams(lp);
		img11.setLayoutParams(lp);
		img12.setLayoutParams(lp);

		img13.setLayoutParams(lp);
		img14.setLayoutParams(lp);
		img15.setLayoutParams(lp);

		img16.setLayoutParams(lp);
		img17.setLayoutParams(lp);
		img18.setLayoutParams(lp);

		img19.setLayoutParams(lp);
		img20.setLayoutParams(lp);
		img21.setLayoutParams(lp);

		img22.setLayoutParams(lp);
		img23.setLayoutParams(lp);
		img24.setLayoutParams(lp);

		LinearLayout.LayoutParams ip = new LinearLayout.LayoutParams(
				row_network.getLayoutParams());

		ip.height = columnWidth / 2;
		ip.setMargins(3, 0, 1, 0);

		row_Displacement.setLayoutParams(ip);
		row_network.setLayoutParams(ip);

		LinearLayout.LayoutParams dd = new LinearLayout.LayoutParams(
				row1.getLayoutParams());

		dd.width = 3 * columnWidth / 2;
		dd.height = columnWidth / 2;
		next_btn.setLayoutParams(dd);
		pre_btn.setLayoutParams(dd);

		LinearLayout.LayoutParams np = new LinearLayout.LayoutParams(
				row1.getLayoutParams());
		np.height = columnWidth / 2;
		np.width = 3 * (columnWidth - 2) / 6;

		i1.setLayoutParams(np);
		i2.setLayoutParams(np);
		i3.setLayoutParams(np);
		i4.setLayoutParams(np);
		i5.setLayoutParams(np);
		i6.setLayoutParams(np);

	}

	private void clickItem() {

		next_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(WelcomeScreen.this, MainActivity.class);

				startActivity(intent);
				finish();

				overridePendingTransition(R.layout.splash_out,
						R.layout.splash_in);

			}
		});

		pre_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				new AlertDialog.Builder(WelcomeScreen.this)
						.setTitle("خروج از برنامه")
						.setMessage("آیا از خروج اطمینان دارید؟")
						.setNegativeButton("خیر", null)
						.setPositiveButton("بله",
								new DialogInterface.OnClickListener() {

									public void onClick(DialogInterface arg0,
											int arg1) {
										finish();
										System.exit(0);
									}
								}).create().show();

			}
		});

	}

}

//
// initialize();
// cliclItem();
// //
// // handler = new Handler();
// // runnable = new Runnable() {
// // public void run() {
// //
// // Intent intent = new Intent();
// // intent.setClass(WelcomeScreen.this, MainActivity.class);
// //
// // startActivity(intent);
// // finish();
// //
// // overridePendingTransition(R.layout.splash_out,
// // R.layout.splash_in);
// //
// // }
// // };
// // handler.postDelayed(runnable, SPLASH_DISPLAY_TIME);
//
// gridView = (GridView) findViewById(R.id.grid_view);
// int[] image = { R.drawable.up2, R.drawable.on2, R.drawable.or2,
// R.drawable.g1, R.drawable.g2, R.drawable.g3, R.drawable.tayan,
// R.drawable.tayan, R.drawable.tayan, R.drawable.tayan,
// R.drawable.tayan, R.drawable.tayan, R.drawable.tayan,
// R.drawable.tayan, R.drawable.tayan, R.drawable.tayan,
// R.drawable.tayan, R.drawable.tayan, R.drawable.tayan,
// R.drawable.tayan, R.drawable.tayan, R.drawable.tayan,
// R.drawable.tayan, R.drawable.tayan, };
//
// Resources r = getResources();
// float padding = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
// gridePadding, r.getDisplayMetrics());
//
// columnWidth = (int) ((getScreenWidth() - ((column + 1) * padding)) / column);
//
// gridView.setNumColumns(column);
// gridView.setColumnWidth(columnWidth);
// gridView.setStretchMode(GridView.NO_STRETCH);
// gridView.setPadding((int) padding, (int) padding, (int) padding,
// (int) padding);
// gridView.setHorizontalSpacing((int) 0);
// gridView.setVerticalSpacing((int) 0);
//
// gridView.setAdapter(new WelcomeScreenAdapter(this, image, columnWidth));
//
// }
//
// @SuppressLint("NewApi")
// public int getScreenWidth() {
// int columnWidth;
// WindowManager wm = (WindowManager) this
// .getSystemService(Context.WINDOW_SERVICE);
// Display display = wm.getDefaultDisplay();
//
// final Point point = new Point();
// try {
// display.getSize(point);
// } catch (java.lang.NoSuchMethodError ignore) { // Older device
// point.x = display.getWidth();
// point.y = display.getHeight();
// }
// columnWidth = point.x;
// return columnWidth;
//
// }
//
// private void initialize() {
//
// btnNext = (ImageButton) findViewById(R.id.btnnext);
// btnExit = (ImageButton) findViewById(R.id.btnprev);
//
// btnins1 = (ImageButton) findViewById(R.id.btninstegram);
// btnint1 = (ImageButton) findViewById(R.id.btn_internet);
// btngp1 = (ImageButton) findViewById(R.id.btngplas);
// btnfb1 = (ImageButton) findViewById(R.id.btnfb);
// btntw1 = (ImageButton) findViewById(R.id.btntwitter);
// btnlink1 = (ImageButton) findViewById(R.id.btnlinking);
//
// } // end initialize
//
// private void cliclItem() {
//
// btnNext.setOnClickListener(new OnClickListener() {
//
// @Override
// public void onClick(View arg0) {
// Intent intent = new Intent();
// intent.setClass(WelcomeScreen.this, MainActivity.class);
//
// startActivity(intent);
// finish();
//
// overridePendingTransition(R.layout.splash_out,
// R.layout.splash_in);
//
// }
// });
//
// btnExit.setOnClickListener(new OnClickListener() {
//
// @Override
// public void onClick(View arg0) {
// new AlertDialog.Builder(WelcomeScreen.this)
// .setTitle("خروج از برنامه")
// .setMessage("آیا از خروج اطمینان دارید؟")
// .setNegativeButton("خیر", null)
// .setPositiveButton("بله",
// new DialogInterface.OnClickListener() {
//
// public void onClick(DialogInterface arg0,
// int arg1) {
// finish();
// System.exit(0);
// }
// }).create().show();
//
// }
// });
//
// } // end cliclItem
//
// }
