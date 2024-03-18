package com.yangna.lbdsp.login.impl;

import com.yangna.lbdsp.login.model.LoginModel;

/**
 * artifact
 */

public interface LoginView {

    void onGetDataLoading(boolean isSuccess);

    void onNetLoadingFail();

    void onGetLoginData(LoginModel model);
}
