package com.houyalab.android.backevolution.ui;

import java.lang.reflect.Field;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewConfiguration;

import com.houyalab.android.backevolution.R;
import com.houyalab.android.backevolution.adapter.BackendPagerAdapter;

public class MainActivity extends BaseActivity implements ActionBar.TabListener {

	private BackendPagerAdapter mBackendPagerAdapter;
	private ViewPager mViewPager;
	private ActionBar mActionBar;
	private ActionBarDrawerToggle mDrawerToggle;
	private DrawerLayout mDrawerLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.w_main);

		PreferenceManager.setDefaultValues(getApplicationContext(),
				R.xml.settings, false);
		initActionBar();
		initNavigatorDrawer();
		getOverflowMenu();
	}

	private void initActionBar() {
		mActionBar = getSupportActionBar();
		mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		mActionBar.setDisplayHomeAsUpEnabled(false);
		mActionBar.setHomeButtonEnabled(true);

		mBackendPagerAdapter = new BackendPagerAdapter(
				getSupportFragmentManager(), getApplicationContext());

		mViewPager = (ViewPager) findViewById(R.id.view_pager);
		mViewPager.setAdapter(mBackendPagerAdapter);

		mViewPager
				.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
					@Override
					public void onPageSelected(int position) {
						mActionBar.setSelectedNavigationItem(position);
					}
				});

		for (int i = 0; i < mBackendPagerAdapter.getCount(); i++) {
			mActionBar.addTab(mActionBar.newTab()
					.setText(mBackendPagerAdapter.getPageTitle(i))
					// .setIcon(mBackendPagerAdapter.getPageIcon(i))
					.setTabListener(this));
		}
		mActionBar.setSelectedNavigationItem(0);
	}

	private void initNavigatorDrawer() {
		// DrawerLayout
		/*
		 * mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		 * mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
		 * R.drawable.ic_drawer, R.string.app_name, R.string.app_name) {
		 * 
		 * @Override public void onDrawerClosed(View drawerView) {
		 * super.onDrawerClosed(drawerView); }
		 * 
		 * @Override public void onDrawerOpened(View drawerView) {
		 * super.onDrawerOpened(drawerView); }
		 * 
		 * @Override public void onDrawerStateChanged(int newState) {
		 * super.onDrawerStateChanged(newState); }
		 * 
		 * }; mDrawerLayout.post(new Runnable() {
		 * 
		 * @Override public void run() { mDrawerToggle.syncState(); } });
		 * 
		 * mDrawerLayout.setDrawerListener(mDrawerToggle);
		 */
	}

	private void getOverflowMenu() {
		try {
			ViewConfiguration config = ViewConfiguration.get(this);
			Field menuKeyField = ViewConfiguration.class
					.getDeclaredField("sHasPermanentMenuKey");
			if (menuKeyField != null) {
				menuKeyField.setAccessible(true);
				menuKeyField.setBoolean(config, false);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		// SearchView searchView = (SearchView)
		// menu.findItem(R.id.action_search); // maybe error
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		/*
		 * case R.id.action_profile: Intent profileIntent = new Intent(this,
		 * ProfileActivity.class); startActivity(profileIntent); return true;
		 * case R.id.action_signin: Intent signoutIntent = new Intent(this,
		 * ProfileActivity.class); signoutIntent.putExtra("do", "signout");
		 * startActivity(signoutIntent); return true; case R.id.action_signup:
		 * return true; case R.id.action_signout: return true;
		 */
		case R.id.action_about:
			DialogAbout dlgAbout = new DialogAbout(this);
			dlgAbout.setTitle(getResources().getString(R.string.title_about));
			dlgAbout.show();
			return true;
		case R.id.action_exit:
			confirmExit();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
		mViewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
	}

	@Override
	public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			confirmExit();
		}
		return super.onKeyDown(keyCode, event);
	}

	private void confirmExit() {
		AlertDialog.Builder dlgBuilder = new AlertDialog.Builder(
				this);
		dlgBuilder.setTitle(R.string.title_exit);
		dlgBuilder.setMessage(R.string.lbl_exit_confirm_info);
		dlgBuilder.setPositiveButton(R.string.lbl_ok,
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						MainActivity.this.finish();
					}
				});
		dlgBuilder.setNegativeButton(R.string.lbl_cancel,
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				});
		dlgBuilder.show();
	}
}
