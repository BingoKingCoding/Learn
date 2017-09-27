package com.king.learn.mvp.ui.widget.dialog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.king.learn.R;

import java.util.Arrays;
import java.util.List;

/**
 * 带取消按钮的菜单选择窗口
 */

public class DialogMenu extends DialogBase implements IDialogMenu
{
    public TextView tv_title;
    public TextView tv_cancel;
    public ListView lv_content;

    private List<Object> mListModel;

    private Callback mCallback;

    public DialogMenu(@NonNull Context context)
    {
        super(context);
        init();
    }

    private void init()
    {
        setContentView(R.layout.lib_dialog_dialog_menu);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_cancel = (TextView) findViewById(R.id.tv_cancel);
        lv_content = (ListView) findViewById(R.id.lv_content);

        tv_cancel.setOnClickListener(this);
        setTextTitle(null);
        setCanceledOnTouchOutside(true);
    }

    @Override
    public IDialogMenu setTextTitle(String text)
    {
        if (TextUtils.isEmpty(text))
        {
            tv_title.setVisibility(View.GONE);
        } else
        {
            tv_title.setVisibility(View.VISIBLE);
            tv_title.setText(text);
        }
        return this;
    }

    @Override
    public IDialogMenu setTextCancel(String text)
    {
        if (TextUtils.isEmpty(text))
        {
            tv_cancel.setVisibility(View.GONE);
        } else
        {
            tv_cancel.setVisibility(View.VISIBLE);
            tv_cancel.setText(text);
        }
        return this;
    }

    @Override
    public IDialogMenu setCallback(Callback callback)
    {
        mCallback = callback;
        return this;
    }

    @Override
    public IDialogMenu setItems(Object... objects)
    {
        List<Object> listObject = null;
        if (objects != null)
        {
            listObject = Arrays.asList(objects);
        }
        setItems(listObject);
        return this;
    }

    @Override
    public IDialogMenu setItems(List<Object> listObject)
    {
        mListModel = listObject;
        setAdapter(getAdapter());
        return this;
    }

    @Override
    public IDialogMenu setAdapter(BaseAdapter adapter)
    {
        lv_content.setAdapter(adapter);
        lv_content.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                if (mCallback != null)
                {
                    mCallback.onClickItem(view, (int) id, DialogMenu.this);
                }
                dismissAfterClickIfNeed();
            }
        });
        return this;
    }
    protected BaseAdapter getAdapter()
    {
        return mInternalAdapter;
    }
    private BaseAdapter mInternalAdapter = new BaseAdapter()
    {
        @Override
        public int getCount()
        {
            if (mListModel != null && !mListModel.isEmpty())
            {
                return mListModel.size();
            }
            return 0;
        }

        @Override
        public Object getItem(int position)
        {
            return getModel(position);
        }

        @Override
        public long getItemId(int position)
        {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            if (convertView == null)
            {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.lib_dialog_item_dialog_menu, parent, false);
            }
            TextView textView = (TextView) convertView.findViewById(R.id.tv_content);
            Object object = getItem(position);
            if (object != null)
            {
                textView.setText(String.valueOf(object));
            }
            return convertView;
        }
    };

    private Object getModel(int position)
    {
        if (mListModel != null && !mListModel.isEmpty()
                && position >= 0
                && position < mListModel.size())
        {
            return mListModel.get(position);
        }
        return null;
    }

    @Override
    public int getDefaultPadding()
    {
        return 0;
    }

    @Override
    public void onClick(View v)
    {
        if (v == tv_cancel)
        {
            if (mCallback != null)
            {
                mCallback.onClickCancel(v, this);
            }
            dismissAfterClickIfNeed();
        }
    }
}
