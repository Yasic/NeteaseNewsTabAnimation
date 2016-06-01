package com.yasic.neteasenewstabanimation;

/**
 * Created by Yasic on 2016/5/30.
 */
public interface onMoveAndSwipeListener {
    boolean onItemMove(int fromPosition, int toPosition);
    void onItemDismiss(int position);
}
