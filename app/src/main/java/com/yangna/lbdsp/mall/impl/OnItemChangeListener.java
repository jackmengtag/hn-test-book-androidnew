package com.yangna.lbdsp.mall.impl;


import com.yangna.lbdsp.mall.bean.ICartItem;

import java.util.List;

public interface OnItemChangeListener {
    void normalCheckChange(List<ICartItem> beans, int position, boolean isChecked);

    void groupCheckChange(List<ICartItem> beans, int position, boolean isChecked);

    void childCheckChange(List<ICartItem> beans, int position, boolean isChecked);
}
