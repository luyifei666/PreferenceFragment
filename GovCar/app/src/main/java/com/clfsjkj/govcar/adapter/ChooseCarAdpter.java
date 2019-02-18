package com.clfsjkj.govcar.adapter;

import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.clfsjkj.govcar.R;
import com.clfsjkj.govcar.bean.ChooseCarBean;

import java.util.List;

public class ChooseCarAdpter extends BaseQuickAdapter<ChooseCarBean, BaseViewHolder> {
    public ChooseCarAdpter(int layoutResId, @Nullable List<ChooseCarBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, final ChooseCarBean item) {
        helper.setText(R.id.car_type,item.getCarName());
        helper.setBackgroundRes(R.id.img,R.drawable.add);
        final EditText et = helper.getView(R.id.car_num);
        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    item.setNeedNum(Integer.parseInt(et.getText().toString()));
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    item.setNeedNum(0);
                }
            }
        });
    }
}
