package com.whale.nangua.toquan.act;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;

import com.whale.nangua.toquan.R;
import com.whale.nangua.toquan.frag.Guide1Fragment;
import com.whale.nangua.toquan.frag.Guide2Fragment;
import com.whale.nangua.toquan.frag.Guide3Fragment;
import com.whale.nangua.toquan.frag.Guide4Fragment;
import com.whale.nangua.toquan.view.NGGuidePageTransformer;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nangua on 2016/8/1.
 */
public class GuideActivity extends FragmentActivity implements ViewPager.OnPageChangeListener,View.OnClickListener {
    // 主界面适配器
    private FragmentPagerAdapter guidePagerAdapter;
    // 所有的Tab
    private List<View> views;
    // 碎片每个碎片为一个布局
    private ArrayList<Fragment> fragments;
    // 导航式Tab
    private ViewPager vp;
    //四个Choice按钮id
     private int[] choicebtnids = {R.id.imgbtn_guide_choice1, R.id.imgbtn_guide_choice2,
            R.id.imgbtn_guide_choice3, R.id.imgbtn_guide_choice4};
    //四个Choice按钮
    private ImageButton[] choicebtns;
    //右移按钮
    private ImageButton btn_guide_next;
    //跳过按钮
    private TextView btn_guide_skip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        // 创建碎片集合
        fragments = new ArrayList<Fragment>();
        initView();
    }

    private void initView() {
        WindowManager wm = this.getWindowManager();
        screenWith = wm.getDefaultDisplay().getWidth();    //屏幕宽度
        //右移按钮
        btn_guide_next = (ImageButton) findViewById(R.id.btn_guide_next);
        btn_guide_next.setOnClickListener(this);
        //跳过
        btn_guide_skip = (TextView) findViewById(R.id.btn_guide_skip);
        btn_guide_skip.setOnClickListener(this);
        //绑定按钮组件
        choicebtns = new ImageButton[4];
        for (int i = 0; i < 4; i++) {
            final int j = i;
            choicebtns[i] = (ImageButton) findViewById(choicebtnids[i]);
            choicebtns[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    changeTagView(j);
                }
            });
        }
        LayoutInflater inflater = LayoutInflater.from(this);
        // 添加滑动
        views = new ArrayList<>();
        views.add(inflater.inflate(R.layout.fragment_guide1, null));
        views.add(inflater.inflate(R.layout.fragment_guide2, null));
        views.add(inflater.inflate(R.layout.fragment_guide3, null));
        views.add(inflater.inflate(R.layout.fragment_guide4, null));
        vp = (ViewPager) findViewById(R.id.vp_guide);
        guidePagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public int getCount() {
                return fragments.size();
            }

            @Override
            public Fragment getItem(int arg0) {
                return fragments.get(arg0);
            }
        };
        // 声明各个Tab的实例
        Guide1Fragment guide1Fragment = new Guide1Fragment();
        Guide2Fragment guide2Fragment = new Guide2Fragment();
        Guide3Fragment guide3Fragment = new Guide3Fragment();
        Guide4Fragment guide4Fragment = new Guide4Fragment();
        fragments.add(guide1Fragment);
        fragments.add(guide2Fragment);
        fragments.add(guide3Fragment);
        fragments.add(guide4Fragment);
        ngGuidePageTransformer = new NGGuidePageTransformer();
        ngGuidePageTransformer.setCurrentItem(this, 0, fragments);
        vp.setPageTransformer(true, ngGuidePageTransformer);
        vp.setAdapter(guidePagerAdapter);
        vp.setOnPageChangeListener(this);
        //注意，设置Page 即缓存页面的个数，数过小时会出现fragment重复加载的问题
        vp.setOffscreenPageLimit(4);
    }

    NGGuidePageTransformer ngGuidePageTransformer;
    TranslationInterface tempfrag;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //下一个
            case R.id.btn_guide_next:
                int nextPage;
                if (nowPage == 3) {
                    nextPage = 0;
                }else {
                    nextPage = nowPage+1;
                }
                onPageSelected(nextPage);
                changeTagView(nextPage);
                break;
            //跳过
            case R.id.btn_guide_skip:
                //TODO 跳过操作

                break;
        }
    }

    public interface TranslationInterface {
        void translation(float x);
    }

    int screenWith ;//屏幕宽度

    /**
     * TODO 动画修改
     * @param position
     * @param positionOffset
     * @param positionOffsetPixels
     */
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        //如果向右
            switch (position) {
                case 0:
                    tempfrag = (Guide1Fragment) fragments.get(0);
                    tempfrag.translation(positionOffsetPixels);
                    break;
                case 1:
                    tempfrag = (Guide2Fragment) fragments.get(1);
                    tempfrag.translation(positionOffsetPixels);
                    break;
                case 2:
                    tempfrag = (Guide3Fragment) fragments.get(2);
                    tempfrag.translation(positionOffsetPixels);
                    break;
                case 3:
                    tempfrag = (Guide4Fragment) fragments.get(3);
                    tempfrag.translation(positionOffsetPixels);
                    break;
            }
    }

    private int nowPage = 0;
    /**
     * 自定义按钮选择的方法
     * @param position
     */
    private void pageCheck(int position) {
        choicebtns[position].setBackgroundResource(R.drawable.shape_guide_choice);
        for (int i = 0;i<4;i++) {
            if (i!= position) {
                choicebtns[i].setBackgroundResource(R.drawable.shape_guide_unchoice);
            }
        }
    }

    @Override
    public void onPageSelected(int position) {
        nowPage = position;
        ngGuidePageTransformer.setCurrentItem(position);
        pageCheck(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    // 更换标签
    private void changeTagView(int change) {
        vp.setCurrentItem(change, false);
    }
}