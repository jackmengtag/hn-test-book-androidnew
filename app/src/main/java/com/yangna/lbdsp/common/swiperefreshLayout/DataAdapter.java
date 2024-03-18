package com.yangna.lbdsp.common.swiperefreshLayout;

import com.yangna.lbdsp.R;
import com.yangna.lbdsp.databinding.ItemHomeBinding;

import java.util.List;

/**
 * @author jingbin
 */
public class DataAdapter extends BaseBindingAdapter<DataItemBean, ItemHomeBinding> {

    public DataAdapter() {
        super(R.layout.item_home);
    }

    public DataAdapter(List<DataItemBean> data) {
        super(R.layout.item_home, data);
    }

    @Override
    protected void bindView(BaseBindingHolder holder, DataItemBean bean, ItemHomeBinding binding, int position) {
        binding.tvText.setText(bean.getTitle() + ": " + position);
    }

}
