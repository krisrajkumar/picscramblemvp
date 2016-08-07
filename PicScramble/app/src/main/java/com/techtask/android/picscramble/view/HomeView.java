package com.techtask.android.picscramble.view;

import com.techtask.android.picscramble.model.ScrambleItem;

import java.util.List;

public interface HomeView {
    void populateScrambleList(List<ScrambleItem> itemList);

    void updateTimerText(String time);

    void updateTimerText(int msg);

    void showHideProgress(boolean show);

    void showNoNetworkErr();

    void showHideDisplayImage(boolean show);

    void showDisplayImage(String url);

    void flipImageItems();

    void resetScrambler();

    void updateScrambleItemState(int id);

    void enableDisableStart(boolean enable);

    void showGameEndMsg();
}
