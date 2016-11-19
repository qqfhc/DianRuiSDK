package dianruisdk.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;

import dianruisdk.service.ParamInfo;


/**
 * Base Fragment
 * Created by j on 2016/10/12.
 */

public abstract class BaseFragment extends Fragment {

    protected Activity mActivity;
    public ParamInfo params;


    /**
     * 获得全局的，防止使用getActivity()为空
     *
     * @param context
     */

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mActivity = getActivity();
    }


    public void destroy() {
        FragmentManager fragmentManager = mActivity.getFragmentManager();
        FragmentTransaction framentTransaction = fragmentManager.beginTransaction();
        framentTransaction.remove(this);
        framentTransaction.commitAllowingStateLoss();

    }

    public void enterBrowser(String gotourl) {
        FragmentManager fm = mActivity.getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        DianruiOpenAdFragment wvb = new DianruiOpenAdFragment(gotourl);
        ft.add(android.R.id.content, wvb);
        ft.commit();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}