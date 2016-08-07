package com.techtask.android.picscramble.presenter.impl;

import android.support.annotation.Nullable;

import com.techtask.android.picscramble.R;
import com.techtask.android.picscramble.application.PicScrambleApplication;
import com.techtask.android.picscramble.engine.PicScrambler;
import com.techtask.android.picscramble.listener.ResponseListener;
import com.techtask.android.picscramble.model.PhotoInfo;
import com.techtask.android.picscramble.model.ScrambleItem;
import com.techtask.android.picscramble.presenter.HomePresenter;
import com.techtask.android.picscramble.transport.RequestProvider;
import com.techtask.android.picscramble.transport.response.FailureResponse;
import com.techtask.android.picscramble.transport.response.SuccessResponse;
import com.techtask.android.picscramble.util.AppUtils;
import com.techtask.android.picscramble.util.WebUtils;
import com.techtask.android.picscramble.view.HomeView;

import java.util.ArrayList;
import java.util.List;

public class HomePresenterImpl implements HomePresenter, ResponseListener, PicScrambler.OnTimerListener, PicScrambler.Communicator {
    private static final String EMPTY_CONTENT = "";

    private HomeView view;
    private PicScrambler scrambler;

    public HomePresenterImpl(@Nullable HomeView view) {
        this.view = view;
    }

    @Override
    public void onLoadContentRequested() {
        if (WebUtils.hasNetConnectivity(PicScrambleApplication.getAppContext())) {
            view.showHideProgress(true);
            RequestProvider.getProvider().fetchFlickrPublicPhotosApi(this);
        } else {
            view.showNoNetworkErr();
        }
    }

    @Override
    public void onSuccess(SuccessResponse response) {
        view.showHideProgress(false);
        List<PhotoInfo> infoList = (ArrayList<PhotoInfo>) response.getData();
        if (!infoList.isEmpty()) {
            handleResult(infoList);
        }
    }

    @Override
    public void onFailure(FailureResponse response) {
        view.showHideProgress(false);
    }

    private void handleResult(List<PhotoInfo> infoList) {
        scrambler = new PicScrambler(infoList, this, this);
        view.populateScrambleList(convertToLocalModel(scrambler.pick()));
    }

    @Override
    public void onTick(long millisUntilFinished) {
        view.updateTimerText(String.valueOf(AppUtils.convertMSToSeconds(millisUntilFinished)));
    }

    @Override
    public void onFinish(String url) {
        view.updateTimerText(R.string.label_start_game);
        view.resetScrambler();
        view.showDisplayImage(url);
        view.showHideDisplayImage(true);
    }

    @Override
    public void onStartRequested() {
        view.enableDisableStart(false);
        view.populateScrambleList(convertToLocalModel(scrambler.initNewGame()));
        view.flipImageItems();
        scrambler.startCountDownTimer();
    }

    @Override
    public void onResetRequested() {
        view.enableDisableStart(true);
        view.resetScrambler();
        view.updateTimerText(EMPTY_CONTENT);
        view.showHideDisplayImage(false);
        scrambler.gameOver();
    }

    @Override
    public void onCheckSelectedItemRequested(int id) {
        if (scrambler.canFlipTheImage(id)) {
            scrambler.handleItemSelection(id);
        }
    }

    @Override
    public void onItemLocked(int id) {
        view.updateScrambleItemState(id);
    }

    @Override
    public void updateNextDisplayImg(String url) {
        view.showDisplayImage(url);
    }

    @Override
    public void gameFinished() {
        view.showGameEndMsg();
    }

    private List<ScrambleItem> convertToLocalModel(List<PhotoInfo> infoList) {
        List<ScrambleItem> scrambleItems = new ArrayList<>();
        for (PhotoInfo photoInfo : infoList) {
            scrambleItems.add(new ScrambleItem(photoInfo.getId(), photoInfo.getUrl()));
        }
        return scrambleItems;
    }
}
