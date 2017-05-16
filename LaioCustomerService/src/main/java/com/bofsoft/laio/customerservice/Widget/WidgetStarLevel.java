package com.bofsoft.laio.customerservice.Widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;

import com.bofsoft.laio.common.MyLog;
import com.bofsoft.laio.customerservice.R;

/**
 * <b>推荐指数等级</b><br>
 * 0～1000分 一红星<br>
 * 1001～5000分 二红星<br>
 * 5001～25000分 三红星<br>
 * 25001～125000分 四红星<br>
 * 125001～625000分 五红星<br>
 * <p>
 * <b>信用等级</b><br>
 * 0<=x<=1分 一红星<br>
 * 1<x<=2分 二红星<br>
 * 2<x<=3分 三红星<br>
 * 3<x<=4分 四红星<br>
 * 4<x<=5分 五红星<br>
 *
 * @author yedong
 */
public class WidgetStarLevel extends LinearLayout {
    private MyLog myLog = new MyLog(getClass());
    /**
     * 推荐指数
     */
    public static int Type_Star_Recommend = 0x01;
    /**
     * 信用等级
     */
    public static int Type_Star_Credit = 0x02;

    private int Star_Type = Type_Star_Credit;

    private ImageView[] imageViews;

    private Context context;
    private int level = 0;// 当前选择的 序号
    private int maxLevel = 5; // 最大等级
    private int Recommend = 0; // 推荐指数值
    private double Credit = 0f; // 信用等级值

    private Drawable bgDraw = getResources().getDrawable(R.mipmap.icon_star1);
    private Drawable levelDraw = getResources().getDrawable(R.mipmap.icon_star2);

    private boolean isCanClickable = false;

    private LevelChangeListener levelChangeListener;

    /**
     * 选中level改变回调
     *
     * @author admin
     */
    public interface LevelChangeListener {
        void onLevelChangeListener(int position);
    }

    public WidgetStarLevel(Context context) {
        super(context);
        this.context = context;
        initView();
    }

    public WidgetStarLevel(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        TypedArray typeArray = context.obtainStyledAttributes(attrs, R.styleable.WidgetStarLevel);
        maxLevel = typeArray.getInt(R.styleable.WidgetStarLevel_max_level, maxLevel);
        level = typeArray.getInt(R.styleable.WidgetStarLevel_level, level);

        Drawable bgDrawable = typeArray.getDrawable(R.styleable.WidgetStarLevel_level_bgdrawable);
        Drawable levelDrawable = typeArray.getDrawable(R.styleable.WidgetStarLevel_level_drawable);
        if (bgDrawable != null) {
            bgDraw = bgDrawable;
        }
        if (levelDrawable != null) {
            levelDraw = levelDrawable;
        }
        isCanClickable =
                typeArray.getBoolean(R.styleable.WidgetStarLevel_level_canclick, isCanClickable);
        typeArray.recycle();
        initView();
    }

    /**
     * 设置推荐指数
     *
     * @param recommend
     */
    public void setRecommendLevel(int recommend) {
        this.Recommend = recommend;
        Star_Type = Type_Star_Recommend;
        if (recommend >= 0 && recommend <= 1000) {
            this.level = 1;
        } else if (recommend > 1000 && recommend <= 5000) {
            this.level = 2;
        } else if (recommend > 5000 && recommend <= 25000) {
            this.level = 3;
        } else if (recommend > 25000 && recommend <= 125000) {
            this.level = 4;
        } else if (recommend > 125001 && recommend <= 625000) {
            this.level = 5;
        } else {
            this.level = 0;
        }

        setLevel(level);

    }

    /**
     * 设置信用等级
     *
     * @param credit
     */
    public void setCreditLevel(double credit) {
        this.Credit = credit;
        Star_Type = Type_Star_Credit;
        level = (int) Math.ceil(credit);
        if (level <= 0) {
            level = 1;
        }
        setLevel(level);

    }

    private void setLevel(int level) {
        if (level >= 0 && level <= maxLevel) {
            this.level = level;
            for (int i = 0; i < level; i++) {
                imageViews[i].setImageDrawable(levelDraw);
            }
        }
        if (isCanClickable) {
            if (levelChangeListener != null) {
                levelChangeListener.onLevelChangeListener(level);
            } else {
                throw new NullPointerException("You must implement the LevelChangeListener bofore use it");
            }
        }
        // initLevelView(level);
        // initView(level);
    }

    private void setMaxLevel(int maxLevel) {
        if (maxLevel > 0) {
            this.level = 0;
            this.maxLevel = maxLevel;
            initView();
        }
    }

    public void initView() {
        this.removeAllViews();
        if (maxLevel > 0) {
            imageViews = new ImageView[maxLevel];
            LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            params.setMargins(5, 5, 2, 2);
            // params.weight = 1.0f;
            for (int i = 0; i < maxLevel; i++) {
                imageViews[i] = new ImageView(context);
                imageViews[i].setTag(i);
                imageViews[i].setOnTouchListener(onTouchListener);
                if (isCanClickable) {
                    imageViews[i].setOnClickListener(onClickListener);
                }
                imageViews[i].setLayoutParams(params);
                if (i < level) {
                    imageViews[i].setImageDrawable(levelDraw);
                } else if (i < maxLevel) {
                    imageViews[i].setImageDrawable(bgDraw);
                }
                imageViews[i].setScaleType(ScaleType.CENTER_INSIDE);
                this.addView(imageViews[i]);
            }
        }
    }

    public void initLevelView(int level) {
        this.removeAllViews();
        if (level > 0) {
            imageViews = new ImageView[level];
            LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            params.setMargins(5, 5, 2, 2);
            // params.weight = 1.0f;
            for (int i = 0; i < level; i++) {
                imageViews[i] = new ImageView(context);
                imageViews[i].setTag(i);
                imageViews[i].setOnTouchListener(onTouchListener);
                if (isCanClickable) {
                    imageViews[i].setOnClickListener(onClickListener);
                }
                imageViews[i].setLayoutParams(params);
                imageViews[i].setImageDrawable(levelDraw);
                imageViews[i].setScaleType(ScaleType.CENTER_INSIDE);
                this.addView(imageViews[i]);
            }
        }
    }

    public void setCanClickable(boolean clickable) {
        isCanClickable = clickable;
    }

    public void setTitleTabChangeListener(LevelChangeListener listener) {
        this.levelChangeListener = listener;
    }

    private OnClickListener onClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            int index = (Integer) v.getTag();
            if (index != level) {
                setLevel(index);
            }

        }
    };

    private OnTouchListener onTouchListener = new OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
            } else if (event.getAction() == MotionEvent.ACTION_UP
                    || event.getAction() == MotionEvent.ACTION_CANCEL) {
                int index = (Integer) v.getTag();

                if (index != level) {
                    performClick();
                } else {
                    // 如果点击相同按钮不输入点击事件
                    // v.setBackgroundColor(BT_SELECTED);
                }

            }
            return false;
        }
    };

    public void setLevelDrawable(Drawable bgDraw, Drawable levelDraw) {
        if (bgDraw != null) {
            this.bgDraw = bgDraw;
        }
        if (levelDraw != null) {
            this.levelDraw = levelDraw;
        }
        initView();
    }

    public void setLevelDrawable(int bgDrawId, int levelDrawId) {
        Drawable bgDrawable = getResources().getDrawable(bgDrawId);
        Drawable LevelDrawable = getResources().getDrawable(levelDrawId);
        if (bgDrawable != null) {
            this.bgDraw = bgDrawable;
        }
        if (LevelDrawable != null) {
            this.levelDraw = LevelDrawable;
        }
        initView();
    }

}
