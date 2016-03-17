package com.example.mediademo;

import java.util.Locale;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.media.SoundPool.OnLoadCompleteListener;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

public class MainActivity extends Activity implements ActionBar.TabListener {

	SectionsPagerAdapter mSectionsPagerAdapter;
	ViewPager mViewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Create the adapter that will return a fragment for each of the three
		// primary sections of the activity.
		mSectionsPagerAdapter = new SectionsPagerAdapter(getFragmentManager());

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabs = (TabLayout) findViewById(R.id.tabs);
        tabs.setupWithViewPager(mViewPager);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onTabSelected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
		// When the given tab is selected, switch to the corresponding page in
		// the ViewPager.
		mViewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}

	@Override
	public void onTabReselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}

	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			switch (position) {
			case 0:
				return new Fragment1();
			case 1:
				return new Fragment2();
			case 2:
				return new Fragment3();
			}

			return null;
		}

		@Override
		public int getCount() {
			// Show 3 total pages.
			return 3;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			Locale l = Locale.getDefault();
			switch (position) {
			case 0:
				return getString(R.string.title_section1).toUpperCase(l);
			case 1:
				return getString(R.string.title_section2).toUpperCase(l);
			case 2:
				return getString(R.string.title_section3).toUpperCase(l);
			}
			return null;
		}
	}

	public static class Fragment1 extends Fragment implements OnTouchListener {

		private SoundPool soundPool;
		private int soundID;
		boolean loaded = false;

		public Fragment1() {
		}

		@Override
		public void onCreate(Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			super.onCreate(savedInstanceState);

			// Load the sound
			soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
            //soundPool = new SoundPool.Builder().setMaxStreams(10).build(); // Lollipop and above!
			soundPool.setOnLoadCompleteListener(new OnLoadCompleteListener() {
				@Override
				public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
					loaded = true;
				}
			});
            // load the sound
			soundID = soundPool.load(getActivity(), R.raw.boom8, 1);

		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
								 Bundle savedInstanceState) {

			View rootView = inflater.inflate(R.layout.fragment_main, container, false);
			rootView.setOnTouchListener(this);

			return rootView;
		}

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			if (event.getAction() == MotionEvent.ACTION_DOWN) {
				// Is the sound loaded already?
				if (loaded) {
					soundPool.play(soundID, 1, 1, 1, 2, 2f);
				}
			}
			return false;
		}
	}

	public static class Fragment2 extends Fragment implements OnClickListener {

		MediaPlayer mediaPlayer;
		
		public Fragment2() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {

			View rootView = inflater.inflate(R.layout.fragment_2, container, false);
			rootView.findViewById(R.id.btnPlay).setOnClickListener(this);
			rootView.findViewById(R.id.btnPause).setOnClickListener(this);
			rootView.findViewById(R.id.btnStop).setOnClickListener(this);
			
			mediaPlayer = MediaPlayer.create(getActivity(), R.raw.car);
			
			return rootView;
		}

		@Override
		public void onClick(View v) {
			switch(v.getId()){
			case R.id.btnPlay:
				if(!mediaPlayer.isPlaying())
					mediaPlayer.start();		
				break;
			case R.id.btnPause:
				mediaPlayer.pause();
				break;
			case R.id.btnStop:
				mediaPlayer.stop();
                Toast.makeText(getActivity(), "length is: " + mediaPlayer.getDuration(), Toast.LENGTH_LONG).show();
				break;
			}
		}
	}

	public static class Fragment3 extends Fragment implements OnClickListener {
		private VideoView videoView;
		private Button btnPlay;

		public Fragment3() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {

			View rootView = inflater.inflate(R.layout.fragment_3, container,
                    false);
			
			videoView = (VideoView) rootView.findViewById(R.id.videoView1);
			Uri video = Uri.parse("android.resource://" + getActivity().getPackageName() + "/" + R.raw.oggy);
			videoView.setVideoURI(video);
            //videoView.setMediaController(new MediaController(getActivity()));
		    btnPlay = (Button) rootView.findViewById(R.id.btnPlay);
		    
		    btnPlay.setOnClickListener(this);
			return rootView;
		}

		@Override
		public void onClick(View v) {
			videoView.start();
		}
	}
}
