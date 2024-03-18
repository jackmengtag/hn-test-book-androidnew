package com.yangna.lbdsp.mall.bean;

/*******************************************************************
 *    * * * *   * * * *   *     *       Created by OCN.Yang
 *    *     *   *         * *   *       Time: 2018/6/21 9:05.
 *    *     *   *         *   * *       Email address: ocnyang@gmail.com
 *    * * * *   * * * *   *     *.Yang  Web site: www.ocnyang.com
 *******************************************************************/

public interface IChildItem extends ICartItem{
    int getGroupId();

    void setGroupId(int groupId);
}
