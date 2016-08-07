package com.techtask.android.picscramble.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.techtask.android.picscramble.R;
import com.techtask.android.picscramble.adapter.ScramblerAdapter;
import com.techtask.android.picscramble.component.ScramblerView;
import com.techtask.android.picscramble.model.ScrambleItem;
import com.techtask.android.picscramble.presenter.HomePresenter;
import com.techtask.android.picscramble.presenter.impl.HomePresenterImpl;
import com.techtask.android.picscramble.util.GlideUtils;
import com.techtask.android.picscramble.util.UIUtils;
import com.techtask.android.picscramble.view.HomeView;

import java.util.List;

public class HomeActivity extends BaseActivity implements HomeView, ScramblerAdapter.OnItemClickListener {

    private HomePresenter presenter;
    private ScramblerView scrambleView;
    private ProgressDialog progressBar;
    private TextView timerTextView;
    private ImageView displayImage;
    private Button startButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = UIUtils.createCircularProgress(this, getString(R.string.label_loading));
        timerTextView = (TextView) findViewById(R.id.timer_textview);
        presenter = new HomePresenterImpl(this);
        scrambleView = (ScramblerView) findViewById(R.id.game_view);
        scrambleView.setAdapter(new ScramblerAdapter(this));
        OnClickListener clickListener = new OnClickListener();
        startButton = (Button) findViewById(R.id.start_button);
        startButton.setOnClickListener(clickListener);
        findViewById(R.id.reset_button).setOnClickListener(clickListener);
        displayImage = (ImageView) findViewById(R.id.display_imageview);
        presenter.onLoadContentRequested();
    }

    @Override
    public void showHideProgress(boolean show) {
        if (progressBar != null) {
            if (show) {
                progressBar.show();
            } else {
                progressBar.hide();
                progressBar.cancel();
            }
        }
    }

    @Override
    public void showHideDisplayImage(boolean show) {
        int visibility  = View.INVISIBLE;
        if(show){
            visibility = View.VISIBLE;
        }
        displayImage.setVisibility(visibility);
    }

    @Override
    public void showDisplayImage(String url) {
        GlideUtils.displayImageFromUrl(this,url,displayImage);
    }

    @Override
    public void updateTimerText(String time) {
        timerTextView.setText(time);
    }

    @Override
    public void updateTimerText(int msg) {
        timerTextView.setText(getString(msg));
    }

    @Override
    public void flipImageItems() {
        ((ScramblerAdapter) scrambleView.getAdapter()).enableAllFlipStates();
        scrambleView.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void resetScrambler() {
        ((ScramblerAdapter) scrambleView.getAdapter()).resetFlipState();
        scrambleView.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void populateScrambleList(List<ScrambleItem> itemList) {
        ((ScramblerAdapter) scrambleView.getAdapter()).setPicList(itemList);
        scrambleView.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void onItemClicked(ScrambleItem item) {
        presenter.onCheckSelectedItemRequested(item.getId());
    }

    @Override
    public void showNoNetworkErr() {
        UIUtils.displaySnackBar(findViewById(android.R.id.content), R.string.err_no_network);
    }

    @Override
    public void showGameEndMsg() {
        UIUtils.displaySnackBar(findViewById(android.R.id.content), R.string.label_game_finish);
    }

    @Override
    public void updateScrambleItemState(int id) {
        ((ScramblerAdapter) scrambleView.getAdapter()).enableFlipStateForId(id);
        scrambleView.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void enableDisableStart(boolean enable) {
        startButton.setEnabled(enable);
    }

    class OnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.start_button) {
                presenter.onStartRequested();
            } else if (v.getId() == R.id.reset_button) {
                presenter.onResetRequested();
            }
        }
    }
}
