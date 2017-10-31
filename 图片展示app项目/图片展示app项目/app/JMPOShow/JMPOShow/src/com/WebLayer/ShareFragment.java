/**   
 * @Title: ShareFragment.java 
 * @Package com.hmkj.idate.share 
 * @Description: TODO(鐢ㄤ竴鍙ヨ瘽鎻忚堪璇ユ枃浠跺仛浠�涔�?) 
 * @author Ching Wang  
 * @date 2013-8-8 涓嬪�?2:36:39 
 * @version V1.0   
 */
package com.WebLayer;

import java.lang.reflect.Field;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.jmposhow.R;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.Toast;
import android.widget.ViewSwitcher.ViewFactory;

/**
 * @ClassName: ShareFragment
 * @Description: TODO(杩欓噷鐢ㄤ竴鍙ヨ瘽鎻忚堪杩欎釜绫荤殑浣滅�?)
 * @author: Ching Wang
 * @date 2013-8-8 涓嬪�?2:36:39
 * 
 */
@SuppressLint("HandlerLeak")
public class ShareFragment extends Fragment implements ViewFactory,
		OnTouchListener {
	/** 这是存获取图片编译之后存的地�? */
	private int[] imgIds;
	private ImageSwitcher mImageSwitcher;
	private int currentPosition = 0;
	private float downX;
	private LinearLayout linearLayout;
	private ImageView[] tips;
	private boolean flag = true;
	private ScheduledExecutorService scheduledExecutorService;
	private Message msg;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		initData();
	}

	private void initData() {
		String[] mSmileyTexts = getResources().getStringArray(R.array.sports);
		tips = new ImageView[mSmileyTexts.length];
		imgIds = new int[mSmileyTexts.length];
		for (int i = 0; i < mSmileyTexts.length; i++) {
			imgIds[i] = getImgId(mSmileyTexts[i]);
		}
	}

	private int getImgId(String mSmileyText) {
		int imgId = 0;
		try {
			Field field = R.drawable.class.getDeclaredField(mSmileyText
					.substring(1, mSmileyText.length() - 1));
			imgId = Integer.parseInt(field.get(null).toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return imgId;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_share, container, false);
		mImageSwitcher = (ImageSwitcher) view.findViewById(R.id.imageSwitcher1);
		mImageSwitcher.setFactory(this);
		mImageSwitcher.setOnTouchListener(this);
		linearLayout = (LinearLayout) view.findViewById(R.id.viewGroup);
		for (int i = 0; i < imgIds.length; i++) {
			ImageView mImageView = new ImageView(getActivity());
			tips[i] = mImageView;
			LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
					new ViewGroup.LayoutParams(LayoutParams.WRAP_CONTENT,
							LayoutParams.WRAP_CONTENT));
			layoutParams.rightMargin = 3;
			layoutParams.leftMargin = 3;

			mImageView.setBackgroundResource(R.drawable.item3);
			linearLayout.addView(mImageView, layoutParams);
		}
		mImageSwitcher.setImageResource(imgIds[currentPosition]);
		setImageBackground(currentPosition);
		return view;
	}

	@Override
	public void onStart() {
		initscheduledExecutorService();
		super.onStart();
	}

	private void initscheduledExecutorService() {
		/**
		 * 这是�?个线程执行任�?
		 * */
		scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
		/**
		 * 或排定某个工�?5秒后执行，之后每30秒执行一次： 当Activity显示出来后，每两秒钟切换�?次图片显�?
		 */
		scheduledExecutorService.scheduleAtFixedRate(new ScrollTask(), 1, 2,
				TimeUnit.SECONDS);
	}

	@Override
	public void onStop() {
		// 当Activity不可见的时�?�停止切�?
		scheduledExecutorService.shutdown();
		super.onStop();
	}

	/** 循环 */
	private class ScrollTask implements Runnable {

		public void run() {
			if (flag) {
				currentPosition++;
				msg = mhandler.obtainMessage(1, currentPosition, 0);
			} else {
				currentPosition--;
				msg = mhandler.obtainMessage(0, currentPosition, 0);
			}
			if (currentPosition == 0 || currentPosition == imgIds.length - 1) {
				flag = !flag;
			}
			mhandler.sendMessage(msg);
		}

	}

	private void setImageBackground(int selectItems) {
		for (int i = 0; i < tips.length; i++) {
			if (i == selectItems) {
				tips[i].setBackgroundResource(R.drawable.item1);
			} else {
				tips[i].setBackgroundResource(R.drawable.item2);
			}
		}
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN: {
			scheduledExecutorService.shutdown();
			// 手指按下的X坐标
			downX = event.getX();
			break;
		}
		case MotionEvent.ACTION_UP: {
			float lastX = event.getX();
			// 抬起的时候的X坐标大于按下的时候就显示上一张图�?
			if (lastX > downX) {
				if (currentPosition > 0) {
					currentPosition--;
					leftIn();
				} else {
					Toast.makeText(getActivity().getApplication(), "已经是第�?�?",
							Toast.LENGTH_SHORT).show();
				}
			}

			if (lastX < downX) {
				if (currentPosition < imgIds.length - 1) {
					currentPosition++;
					rightIn();
				} else {
					Toast.makeText(getActivity().getApplication(), "到了�?后一�?",
							Toast.LENGTH_SHORT).show();
				}
			}
			initscheduledExecutorService();
			break;
		}
		}

		return true;
	}

	private void leftIn() {
		// 设置动画，这里的动画比较�?单，不明白的去网上看看相关内�?
		mImageSwitcher.setInAnimation(AnimationUtils.loadAnimation(
				getActivity().getApplication(), android.R.anim.slide_in_left));
		mImageSwitcher
				.setOutAnimation(AnimationUtils.loadAnimation(getActivity()
						.getApplication(), android.R.anim.slide_out_right));
		mImageSwitcher
				.setImageResource(imgIds[currentPosition % imgIds.length]);
		setImageBackground(currentPosition);
	}

	private void rightIn() {
		mImageSwitcher.setInAnimation(AnimationUtils.loadAnimation(
				getActivity().getApplication(), android.R.anim.slide_in_left));
		mImageSwitcher
				.setOutAnimation(AnimationUtils.loadAnimation(getActivity()
						.getApplication(), android.R.anim.slide_out_right));
		mImageSwitcher.setImageResource(imgIds[currentPosition]);
		setImageBackground(currentPosition);
	}

	@Override
	public View makeView() {
		final ImageView i = new ImageView(getActivity());
		i.setBackgroundColor(0xff000000);
		i.setScaleType(ImageView.ScaleType.FIT_XY);
		i.setLayoutParams(new ImageSwitcher.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		return i;
	}

	// 通过handler来更新主界面
	// mgallery.setSelection(positon),选中第position的图片，然后调用OnItemSelectedListener监听改变图像
	private Handler mhandler = new Handler() {
		public void handleMessage(Message msg) {
			if (msg.what == 1) {
				rightIn();
			} else if (msg.what == 0) {
				leftIn();
			}
		}
	};

}
