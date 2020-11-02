package com.nsmiles.happybeingsdklib.Utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.nsmiles.happybeingsdklib.R;


/**
 * Created by Kavya on 4/27/2020.
 */
public class CustomToast extends Toast {
    public CustomToast(Context context) {
        super(context);
    }

    public static Toast makeText(Context context, CharSequence text, int duration) {
        Toast t = Toast.makeText(context,text,duration);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        View layout = inflater.inflate(R.layout.custom_toast,null);

        TextView textView = layout.findViewById(R.id.text);
        textView.setTextColor(context.getResources().getColor(R.color.red));
        textView.setText(text);

        t.setView(layout);


        return t;
    }

}