package com.nsmiles.happybeingsdklib.mycoachfragment;

public interface HBView {

    void internetRequired();
    void showProgress();
    void hideProgress();
    void showErrorMessage(String errorMessage);
    void showSuccessMessage(String successMessage);


}
