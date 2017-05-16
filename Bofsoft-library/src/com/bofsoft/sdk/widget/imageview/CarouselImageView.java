package com.bofsoft.sdk.widget.imageview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bofsoft.sdk.R;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class CarouselImageView extends LinearLayout {

    private int delay = 3; // 切换时间

    private int currentItem = 0;

    private ScheduledExecutorService scheduledExecutorService;

    private OnClickListener clickListener;

    private View v;
    private ViewPager vp;
    private TextView title;
    private LinearLayout nav;
    private MyAdapter adapter;
    private List<ImageView> imgList = new ArrayList<ImageView>();
    private List<String> titleList = new ArrayList<String>();
    private List<View> navList = new ArrayList<View>();

    public CarouselImageView(Context context) {
        super(context, null);
        init();
    }

    public CarouselImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void init() {
        v = inflate(getContext(), R.layout.picture_carousel_layout, null);
        addView(v);
        vp = (ViewPager) v.findViewById(R.id.vp);
        title = (TextView) v.findViewById(R.id.title_tv);
        nav = (LinearLayout) v.findViewById(R.id.nav_ll);
        adapter = new MyAdapter();
        vp.setAdapter(adapter);
        vp.setOnPageChangeListener(new MyPageChangeListener());
    }

    public void addImageView(ImageView img, String title) {
        img.setScaleType(ScaleType.CENTER_CROP);
        imgList.add(img);
        titleList.add(title);
        View v = new View(getContext());
        int r = getContext().getResources().getDimensionPixelOffset(R.dimen.DP_5);
        int g = getContext().getResources().getDimensionPixelOffset(R.dimen.DP_1p5);
        v.setBackgroundResource(R.drawable.picture_carousel_point_normal);
        LayoutParams lp = new LayoutParams(r, r);
        lp.setMargins(g, 0, g, 0);
        nav.addView(v, lp);
        navList.add(v);
        adapter.notifyDataSetChanged();
        img.setId(imgList.size() - 1);
        img.setOnClickListener(clistener);
    }

    public interface OnClickListener {
        void click(View v, int position);
    }

    public void setOnclickListener(OnClickListener listener) {
        this.clickListener = listener;
    }

    public void start() {
        start(delay);
    }

    public void start(int delay) {
        this.delay = delay;
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        // 当Activity显示出来后，每两秒钟切换一次图片显示
        scheduledExecutorService.scheduleAtFixedRate(new ScrollTask(), 1, delay, TimeUnit.SECONDS);
        title.setText(titleList.get(currentItem));
        navList.get(currentItem).setBackgroundResource(R.drawable.picture_carousel_point_focused);
    }

    public void stop() {
        // 当Activity不可见的时候停止切换
        scheduledExecutorService.shutdown();
    }

    private class ScrollTask implements Runnable {

        public void run() {
            synchronized (vp) {
                currentItem = (currentItem + 1) % imgList.size();
                handler.obtainMessage().sendToTarget(); // 通过Handler切换图片
            }
        }
    }

    // 切换当前显示的图片
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            vp.setCurrentItem(currentItem);// 切换当前显示的图片
        }

    };


    private android.view.View.OnClickListener clistener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            if (clickListener != null)
                clickListener.click(v, v.getId());
        }
    };

    private class MyPageChangeListener implements OnPageChangeListener {
        private int oldPosition = 0;

        /**
         * This method will be invoked when a new page becomes selected. position: Position index of the
         * new selected page.
         */
        public void onPageSelected(int position) {
            currentItem = position;
            title.setText(titleList.get(position));
            navList.get(oldPosition).setBackgroundResource(R.drawable.picture_carousel_point_normal);
            navList.get(position).setBackgroundResource(R.drawable.picture_carousel_point_focused);
            oldPosition = position;
        }

        public void onPageScrollStateChanged(int arg0) {

        }

        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }
    }

    private class MyAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return imgList.size();
        }

        @Override
        public Object instantiateItem(View arg0, int arg1) {
            ((ViewPager) arg0).addView(imgList.get(arg1));
            return imgList.get(arg1);
        }

        @Override
        public void destroyItem(View arg0, int arg1, Object arg2) {
            ((ViewPager) arg0).removeView((View) arg2);
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public void restoreState(Parcelable arg0, ClassLoader arg1) {

        }

        @Override
        public Parcelable saveState() {
            return null;
        }

        @Override
        public void startUpdate(View arg0) {

        }

        @Override
        public void finishUpdate(View arg0) {

        }
    }
}
