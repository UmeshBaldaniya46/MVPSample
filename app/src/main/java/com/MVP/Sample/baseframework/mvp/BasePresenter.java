package com.MVP.Sample.baseframework.mvp;

public interface BasePresenter {

    /**
     * Method that should signal the appropriate view to show the appropriate error with the
     * provided message.
     */
    void onError(String message);

    void registerBus();

    void unRegisterBus();

}
