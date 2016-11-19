package dianruisdk.fragment;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import dianruisdk.service.DataServices;
import dianruisdk.service.ParamInfo;

/**
 * Created by luqinglong on 2016/11/11.
 * Email :23503458@qq.com
 */
public class DianruiEmbedFragment extends BaseFragment
{

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LinearLayout layout = new LinearLayout(getActivity());
        RelativeLayout.LayoutParams layoutParams1 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        layoutParams1.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        layout.setLayoutParams(layoutParams1);//设置根布局的宽高属性以及margin等
        final ImageView imageView = new ImageView(getActivity());
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        imageView.setBackgroundColor(Color.BLACK);
        imageView.setLayoutParams(layoutParams);
        layout.addView(imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enterBrowser(params.gotourl);
                DataServices.getInstance().notifyClickReq(params.planid, 1,params.getImageTJ);
            }
        });
        Bitmap bitmap = (Bitmap) this.getArguments().getParcelable("bitmap");
        params = (ParamInfo) this.getArguments().getSerializable("params");
        imageView.setImageBitmap(bitmap);
        DataServices.getInstance().notifyClickReq(this.params.planid, 0, this.params.getImageTJ);
        return layout;
    }
}
