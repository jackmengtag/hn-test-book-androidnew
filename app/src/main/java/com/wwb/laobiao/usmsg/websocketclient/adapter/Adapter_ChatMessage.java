package com.wwb.laobiao.usmsg.websocketclient.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.wwb.laobiao.usmsg.websocketclient.modle.ChatMessage;
import com.yangna.lbdsp.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

//import com.qzy.laobiao.R;
//import com.qzy.laobiao.msg.websocketclient.modle.ChatMessage;

public class Adapter_ChatMessage extends BaseAdapter {
    List<ChatMessage> mChatMessageList;
    LayoutInflater inflater;
    Context context;
    private String chatobja="服务器";
    private int chaticona,chaticonb;
    private AdapterChatMessageInterface adapterChatMessageInterface;

    public Adapter_ChatMessage(Context context, List<ChatMessage> list) {
        this.mChatMessageList = list;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        if (mChatMessageList.get(position).getIsMeSend() == 1)
            return 0;// 返回的数据位角标
        else
            return 1;
    }

    @Override
    public int getCount() {
        return mChatMessageList.size();
    }


    @Override
    public Object getItem(int i) {
        return mChatMessageList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ChatMessage mChatMessage = mChatMessageList.get(i);
        String content = mChatMessage.getContent();
        String time = formatTime(mChatMessage.getTime());
        int isMeSend = mChatMessage.getIsMeSend();
        int isRead = mChatMessage.getIsRead();////是否已读（0未读 1已读）
        final ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            if (isMeSend == 0) {//对方发送
                view = inflater.inflate(R.layout.item_chat_receive_text, viewGroup, false);
                holder.tv_content = view.findViewById(R.id.tv_content);
                holder.tv_sendtime = view.findViewById(R.id.tv_sendtime);
                holder.tv_display_name = view.findViewById(R.id.tv_display_name);
            } else if (isMeSend == 1){
                view = inflater.inflate(R.layout.item_chat_send_text, viewGroup, false);
                holder.tv_content = view.findViewById(R.id.tv_content);
                holder.tv_sendtime = view.findViewById(R.id.tv_sendtime);
                holder.tv_isRead = view.findViewById(R.id.tv_isRead);
            }
            else {
                view = inflater.inflate(R.layout.item_chat_ring, viewGroup, false);
                holder.tv_content = view.findViewById(R.id.tv_content);
                holder.tv_sendtime = view.findViewById(R.id.tv_sendtime);
                holder.tv_display_name = view.findViewById(R.id.tv_display_name);//iv_ok

                Button buttonok = view.findViewById(R.id.iv_ok);
                buttonok.setOnClickListener(new View.OnClickListener() {


                    @Override
                    public void onClick(View v) {
                        if(adapterChatMessageInterface!=null)
                        {
                            adapterChatMessageInterface.onsel(0);
                        }
                    }
                });
                Button buttoncancel = view.findViewById(R.id.iv_cancel);
                buttoncancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(adapterChatMessageInterface!=null)
                        {
                            adapterChatMessageInterface.onsel(1);
                        }
                    }
                });
            }

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        if(isMeSend!=2)
        {
            holder.tv_sendtime.setText(time);
            holder.tv_content.setVisibility(View.VISIBLE);
            holder.tv_content.setText(content);
            if(chaticona!=0)
            {
//                if(holder.icon!=null)
//                {
//                    if(isMeSend==0)
//                    {
//                        holder.icon.setImageResource(chaticona);
//                    }
//                    else
//                    {
//                        holder.icon.setImageResource(chaticonb);
//                    }
//
//                }
            }
        }
        //如果是自己发送才显示未读已读
        if (isMeSend == 1) {
            if (isRead == 0) {
                holder.tv_isRead.setText("未读");
                holder.tv_isRead.setTextColor(context.getResources().getColor(R.color.jmui_jpush_blue));
            } else if (isRead == 1) {
                holder.tv_isRead.setText("已读");
                holder.tv_isRead.setTextColor(Color.GRAY);
            } else {
                holder.tv_isRead.setText("");
            }
            holder.tv_isRead.setVisibility(View.GONE);
        }else if(isMeSend == 0){
            holder.tv_display_name.setVisibility(View.VISIBLE);
            holder.tv_display_name.setText(chatobja);
        }
        else
        {

        }
        return view;
    }

    public void setchatIcon(int icon0,int idex) {
        if(idex==0)
        {
            chaticona=icon0;
        }
        else
        {
            chaticonb=icon0;
        }
    }

    public void setchatname(String name) {
        chatobja=name;
    }

    public void setinterface(AdapterChatMessageInterface adapterChatMessageInterface) {
        this.adapterChatMessageInterface=adapterChatMessageInterface;
    }

    class ViewHolder {
        private TextView tv_content, tv_sendtime, tv_display_name, tv_isRead;
        private ImageView icon;
    }


    /**
     * 将毫秒数转为日期格式
     *
     * @param timeMillis
     * @return
     */
    private String formatTime(String timeMillis) {
        long timeMillisl= Long.parseLong(timeMillis);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(timeMillisl);
        return simpleDateFormat.format(date);
    }

    public interface AdapterChatMessageInterface {
        void onsel(int rsv);
    }
}
