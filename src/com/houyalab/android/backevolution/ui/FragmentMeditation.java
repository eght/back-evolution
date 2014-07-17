package com.houyalab.android.backevolution.ui;

import android.app.AlertDialog;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.houyalab.android.backevolution.R;
import com.houyalab.android.backevolution.base.BaseFragment;

public class FragmentMeditation extends BaseFragment implements
		View.OnClickListener {

	private Button mBtnMeditationSetting;
	private Button mBtnMeditationDo;
	private Button mBtnMeditationCheck;
	private MediaPlayer mPlayer;
	private boolean mPlayerLoopMode;
	private boolean mMeditationDoState;

	public FragmentMeditation() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.w_meditation, container,
				false);
		mBtnMeditationSetting = (Button) rootView
				.findViewById(R.id.btn_meditation_setting);
		mBtnMeditationDo = (Button) rootView
				.findViewById(R.id.btn_meditation_do);
		mBtnMeditationCheck = (Button) rootView
				.findViewById(R.id.btn_meditation_check);

		mBtnMeditationSetting.setOnClickListener(this);
		mBtnMeditationDo.setOnClickListener(this);
		mBtnMeditationCheck.setOnClickListener(this);

		return rootView;
	}

	@Override
	public void onClick(View view) {
		if (view.getId() == R.id.btn_meditation_setting) {
			AlertDialog.Builder dlgBuilder = new AlertDialog.Builder(getActivity());
			dlgBuilder.setTitle(R.string.title_meditation_setting);
			dlgBuilder.setMessage("a");
			dlgBuilder.show();
		} else if (view.getId() == R.id.btn_meditation_do) {
			try {
				mPlayer = MediaPlayer.create(getActivity(), R.raw.zenbell);
				mPlayerLoopMode = true;
				mPlayer.setLooping(mPlayerLoopMode);
				mPlayer.start();
			} catch (Exception e) {
			}
		} else if (view.getId() == R.id.btn_meditation_check) {
			AlertDialog.Builder dlgBuilder = new AlertDialog.Builder(getActivity());
			dlgBuilder.setTitle(R.string.title_meditation_check);
			dlgBuilder.setMessage("b");
			dlgBuilder.show();
		}
	}

}
