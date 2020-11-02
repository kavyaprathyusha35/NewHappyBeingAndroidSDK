package com.nsmiles.happybeingsdklib.MindGym;

import android.view.View;

import java.util.List;

/**
 * Created by nSmiles on 12/13/2017.
 */

public interface RelaxActivityOnClickListener {

    void getFirstCardSize(int card_size, int list_size);
    void hideLastView(View view, int position, int list_count);
    void relaxActivityOnClickListener(List<String> activity_list, int position);
}
