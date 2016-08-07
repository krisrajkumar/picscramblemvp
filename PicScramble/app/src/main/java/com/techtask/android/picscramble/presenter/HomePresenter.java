package com.techtask.android.picscramble.presenter;

public interface HomePresenter {
    void onLoadContentRequested();

    void onCheckSelectedItemRequested(int id);

    void onStartRequested();

    void onResetRequested();
}
