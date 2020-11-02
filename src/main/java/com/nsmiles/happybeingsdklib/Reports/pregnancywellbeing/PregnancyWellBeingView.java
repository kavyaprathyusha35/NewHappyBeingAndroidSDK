package com.nsmiles.happybeingsdklib.Reports.pregnancywellbeing;


import com.nsmiles.happybeingsdklib.mycoachfragment.HBView;

import java.util.List;

/**
 * Created by Sukumar on 6/1/2018.
 */

public interface PregnancyWellBeingView extends HBView {

    void showRadarChart();
    void setHappinessIndexText(Double indexText);
    void setActionsText(List<String> actionsList);
}
