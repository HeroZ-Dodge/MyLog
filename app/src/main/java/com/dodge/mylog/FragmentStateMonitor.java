package com.dodge.mylog;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;

import java.util.List;

/**
 * Created by linzheng on 2019/8/15.
 */

public class FragmentStateMonitor {

    private static final String TAG = "FragmentStateMonitor";

    private Fragment mFragment;

    private boolean mFragmentVisible = false;

    public FragmentStateMonitor(Fragment mFragment) {
        this.mFragment = mFragment;
    }

    public boolean isFragmentVisible() {
        return mFragmentVisible;
    }

    public void attachFragment(Fragment fragment) {
        this.mFragment = fragment;
    }

    public void checkFragmentVisible(boolean expect) {
        if (mFragment == null) {
            return;
        }
        if (expect == mFragmentVisible) {
            return;
        }
        boolean parentVisible = parentVisible();
        boolean currentVisible = $fragmentVisible(mFragment);
        boolean fragmentVisible = parentVisible && currentVisible;

        if (fragmentVisible != mFragmentVisible) {
            mFragmentVisible = fragmentVisible;
            checkChildVisible(mFragmentVisible);
            Log.d(TAG, "checkFragmentVisible: Fragment = " + mFragment.toString() + " | visible = " + mFragmentVisible);
            // TODO Dodge 添加监听
        }
    }

    /**
     * 检测子 Fragment 可见性
     * @param expect 期望值
     */
    private void checkChildVisible(boolean expect) {
        if (mFragment == null) {
            return;
        }

        FragmentManager fm = mFragment.getChildFragmentManager();
        List<Fragment> fragments = fm.getFragments();
        if (fragments == null || fragments.size() == 0) {
            return;
        }
        for (Fragment fragment : fragments) {
            if (fragment instanceof IFragmentMonitor) {
                FragmentStateMonitor monitor = ((IFragmentMonitor) fragment).getMonitor();
                monitor.checkFragmentVisible(expect);
            }
        }
    }


    private boolean parentVisible() {
        Fragment parent = mFragment.getParentFragment();
        if (parent instanceof IFragmentMonitor) {
            FragmentStateMonitor monitor = ((IFragmentMonitor) parent).getMonitor();
            return monitor.isFragmentVisible();
        } else if (parent != null) {
            return  $fragmentVisible(parent);
        } else {
            return true;
        }
    }


    private boolean $fragmentVisible(Fragment fragment) {
        boolean resume = fragment.isResumed();
        boolean hidden = fragment.isHidden();
        boolean userVisible = fragment.getUserVisibleHint();
        return resume && !hidden && userVisible;
    }


}
