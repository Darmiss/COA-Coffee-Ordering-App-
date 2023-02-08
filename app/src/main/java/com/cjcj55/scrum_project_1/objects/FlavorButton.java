package com.cjcj55.scrum_project_1.objects;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

public class FlavorButton extends LinearLayout {
    private boolean isSelected = false;

    public FlavorButton(Context context) {
        super(context);
    }

    public FlavorButton(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public FlavorButton(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
