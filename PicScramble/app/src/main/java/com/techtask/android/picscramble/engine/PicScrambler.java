package com.techtask.android.picscramble.engine;

import android.os.CountDownTimer;
import android.util.SparseBooleanArray;

import com.techtask.android.picscramble.model.PhotoInfo;
import com.techtask.android.picscramble.util.AppUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Core component responsible for handling the pic scramble.
 */
public class PicScrambler {
    private static final long TIME_IN_MS = 15000;
    private static final long INTERVAL = 500;
    private static final int INITIAL_POS = 0;
    private static final int TOTAL_ITEMS_POS = 9;

    private List<PhotoInfo> photoInfos;
    private CountDownTimer countDownTimer;
    private int displayItem;

    /**
     * Maintains the flip state.
     */
    private SparseBooleanArray lockedItemState = new SparseBooleanArray();
    private Map<Integer,PhotoInfo> infoMap = new HashMap<>();

    private OnTimerListener gamerListener;
    private Communicator communicator;

    public PicScrambler(List<PhotoInfo> photoInfos, OnTimerListener timerListener, Communicator communicator) {
        this.photoInfos = photoInfos;
        this.gamerListener = timerListener;
        this.communicator = communicator;
    }

    private void shuffle() {
        Collections.shuffle(photoInfos);
    }

    public List<PhotoInfo> initNewGame(){
        shuffle();
        return pick();
    }

    public void gameOver() {
        if (lockedItemState != null) {
            lockedItemState.clear();
        }
        if(infoMap!=null){
            infoMap.clear();
        }
        displayItem = -1;
        stopCountDownTimer();
    }

    public List<PhotoInfo> pick() {
        List<PhotoInfo> infoList = photoInfos.subList(INITIAL_POS, TOTAL_ITEMS_POS);
        initGameState(infoList);
        return infoList;
    }

    private void initGameState(List<PhotoInfo> infoList) {
        lockedItemState.clear();
        infoMap.clear();
        for (PhotoInfo info : infoList) {
            lockedItemState.put(info.getId(), false);
            infoMap.put(info.getId(), info);
        }
    }

    private int genNextDisplayItem() {
        List<Integer> ids = new ArrayList<>();
        for (int i = 0; i < lockedItemState.size(); i++) {
            int key = lockedItemState.keyAt(i);
            if (!lockedItemState.get(key)) {
                ids.add(key);
            }
        }
        if (ids.isEmpty()) {
            displayItem = -1;
        } else {
            int randNum = ids.size() == 0 ? -1 : AppUtils.randRange(0, ids.size() - 1);
            displayItem = ids.get(randNum);
        }
        return displayItem;
    }

    public void startCountDownTimer() {
        if (countDownTimer != null) {
            countDownTimer.start();
        } else {
            countDownTimer = new CountDownTimer(TIME_IN_MS, INTERVAL) {
                @Override
                public void onTick(long millisUntilFinished) {
                    gamerListener.onTick(millisUntilFinished);
                }

                @Override
                public void onFinish() {
                    genNextDisplayItem();
                    gamerListener.onFinish(infoMap.get(displayItem).getUrl());
                }
            }.start();
        }
    }

    public void handleItemSelection(int id) {
        markItemAsLocked(id);
        if (lockedItemState.get(id)) {
            communicator.onItemLocked(id);
            genNextDisplayItem();
            if(displayItem == -1){
                communicator.gameFinished();
            } else {
                communicator.updateNextDisplayImg(infoMap.get(displayItem).getUrl());
            }
        }
    }

    private void markItemAsLocked(int id) {
        if (lockedItemState != null) {
            lockedItemState.put(id, true);
        }
    }

    /**
     * Checks whether the selected scrambler item is matched.
     *
     * @param id id of the matching item.
     * @return Returns <code>true</code>, if selected item is matched.
     */
    public boolean canFlipTheImage(int id) {
        return displayItem == id;
    }

    public void stopCountDownTimer() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }

    public interface OnTimerListener {
        void onTick(long millisUntilFinished);

        void onFinish(String url);
    }

    public interface Communicator {
        void onItemLocked(int id);

        void updateNextDisplayImg(String url);

        void gameFinished();
    }
}
