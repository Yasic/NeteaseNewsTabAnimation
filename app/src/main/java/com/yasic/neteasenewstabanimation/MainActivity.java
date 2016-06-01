package com.yasic.neteasenewstabanimation;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.graphics.Matrix;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements onAllTabsListener{
    private CoordinatorLayout crdRootView;
    private RelativeLayout liType;
    private RecyclerView rvAll, rvChoose;
    private List<String> chooseList = new ArrayList<>(), allList = new ArrayList<>();
    private AllTabsAdapter allTabsAdapter;
    private ChooseTabAdapter chooseTabAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        liType = (RelativeLayout) findViewById(R.id.rl_Type);
        rvAll = (RecyclerView) findViewById(R.id.rv_All);
        rvChoose = (RecyclerView) findViewById(R.id.rv_Choose);
        crdRootView = (CoordinatorLayout) findViewById(R.id.crd_RootView);
        //liType = crdRootView;
        chooseList.add("first");
        chooseList.add("two");
        chooseList.add("three");
        chooseList.add("four");
        chooseList.add("five");

        allList.add("first");
        allList.add("two");
        allList.add("three");
        allList.add("four");
        allList.add("five");
        allList.add("six");
        allList.add("seven");

        allTabsAdapter = new AllTabsAdapter(this, allList);
        chooseTabAdapter = new ChooseTabAdapter(this, chooseList);
        allTabsAdapter.setListener(this);

        rvChoose.setLayoutManager(new GridLayoutManager(this, 4));
        rvAll.setLayoutManager(new GridLayoutManager(this, 4));

        rvAll.setAdapter(allTabsAdapter);
        rvChoose.setAdapter(chooseTabAdapter);

        MyItemTouchHHelperCallback callback = new MyItemTouchHHelperCallback(chooseTabAdapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(rvChoose);
    }

    @Override
    public void allTabsItemClick(View view, final int position) {
        final PathMeasure pathMeasure;
        final float[] currentPosition = new float[2];
        int parentLocation[] = new int[2];
        liType.getLocationInWindow(parentLocation);

        final int startLocation[] = new int[2];
        view.getLocationInWindow(startLocation);

        final View startView = view;
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(view.getWidth(), view.getHeight());
        rvAll.removeView(view);
        liType.addView(startView, layoutParams);

        final View endView;
        float toX, toY;
        int endLocation[] = new int[2];
        int size = chooseList.size();

        if (size == 0){
            toX = view.getWidth();
            toY = view.getHeight();
        }
        else if (size % 4 == 0){
            endView = rvChoose.getChildAt(size - 4);
            endView.getLocationInWindow(endLocation);
            toX = endLocation[0] - parentLocation[0];
            toY = endLocation[1] + view.getHeight() - parentLocation[1];
        }else {
            endView = rvChoose.getChildAt(size - 1);
            endView.getLocationInWindow(endLocation);
            toX = endLocation[0] + view.getWidth() - parentLocation[0];
            toY = endLocation[1] - parentLocation[1];
        }

        final float startX = startLocation[0] - parentLocation[0];
        float startY = startLocation[1] - parentLocation[1];

        Path path = new Path();
        path.moveTo(startX, startY);
        path.lineTo(toX, toY);
        pathMeasure = new PathMeasure(path, false);
        final Matrix matrix = new Matrix();

        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, pathMeasure.getLength());
        valueAnimator.setDuration(500);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                pathMeasure.getPosTan(value, currentPosition, null);
                startView.setTranslationX(currentPosition[0]);
                startView.setTranslationY(currentPosition[1]);
            }
        });
        valueAnimator.start();

        valueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                rvAll.setItemAnimator(new DefaultItemAnimator());
                rvChoose.setItemAnimator(new DefaultItemAnimator());
                chooseList.add(chooseList.size(), allList.get(position));
                allList.remove(position);

                allTabsAdapter.notifyDataSetChanged();
                chooseTabAdapter.notifyDataSetChanged();

                allTabsAdapter.notifyItemRemoved(position);
                chooseTabAdapter.notifyItemRemoved(chooseList.size());
                liType.removeView(startView);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
