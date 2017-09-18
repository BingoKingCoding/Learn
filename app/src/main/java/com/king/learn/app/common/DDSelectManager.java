package com.king.learn.app.common;

import com.king.learn.app.holder.DDObjectsHolder;
import com.king.learn.app.holder.ISDObjectsHolder;
import com.king.learn.app.listener.DDIterateCallback;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * <选择管理器>
 * Created by wwb on 2017/9/18 17:35.
 */

public class DDSelectManager<T>
{
    private List<T> mListItem = new ArrayList<>();
    private int mCurrentIndex = -1;
    private int mLastIndex = -1;
    private Map<Integer, T> mMapSelectedIndexItem = new LinkedHashMap<>();
    private Mode mMode = Mode.SINGLE_MUST_ONE_SELECTED;
    private boolean mIsEnable = true;

    private ReSelectCallback<T> mReSelectCallback;
    private ISDObjectsHolder<SelectCallback<T>> mSelectCallbackHolder = new DDObjectsHolder<>();


    /**
     * 添加选择状态回调
     *
     * @param selectCallback
     */
    public void addSelectCallback(SelectCallback<T> selectCallback)
    {
        mSelectCallbackHolder.add(selectCallback);
    }

    /**
     * 移除选择状态回调
     *
     * @param selectCallback
     */
    public void removeSelectCallback(SelectCallback<T> selectCallback)
    {
        mSelectCallbackHolder.remove(selectCallback);
    }

    public void setReSelectCallback(ReSelectCallback<T> reSelectCallback)
    {
        this.mReSelectCallback = reSelectCallback;
    }

    /**
     * 设置是否开启管理功能
     *
     * @param enable
     */
    public void setEnable(boolean enable)
    {
        this.mIsEnable = enable;
    }

    /**
     * 是否开启了管理功能，默认true，开启
     *
     * @return
     */
    public boolean isEnable()
    {
        return mIsEnable;
    }

    public int getLastIndex()
    {
        return mLastIndex;
    }

    public Mode getMode()
    {
        return mMode;
    }

    /**
     * 设置选中模式
     *
     * @param mode
     */
    public void setMode(Mode mode)
    {
        if (mode != null)
        {
            clearSelected();
            this.mMode = mode;
        }
    }

    /**
     * 是否单选模式
     *
     * @return
     */
    public boolean isSingleMode()
    {
        boolean single = false;
        switch (mMode)
        {
            case SINGLE:
            case SINGLE_MUST_ONE_SELECTED:
                single = true;
                break;
            case MULTI:
            case MULTI_MUST_ONE_SELECTED:
                single = false;
                break;
            default:
                break;
        }
        return single;
    }

    /**
     * 项是否被选中
     *
     * @param item
     * @return
     */
    public boolean isSelected(T item)
    {
        int index = indexOfItem(item);
        return isSelected(index);
    }

    /**
     * 项是否被选中
     *
     * @param index
     * @return
     */
    public boolean isSelected(int index)
    {
        boolean selected = false;
        if (index >= 0)
        {
            switch (mMode)
            {
                case SINGLE:
                case SINGLE_MUST_ONE_SELECTED:
                    if (index == mCurrentIndex)
                    {
                        selected = true;
                    }
                    break;
                case MULTI:
                case MULTI_MUST_ONE_SELECTED:
                    if (mMapSelectedIndexItem.containsKey(index))
                    {
                        selected = true;
                    }
                    break;

                default:
                    break;
            }
        }
        return selected;
    }

    public void synchronizeSelected()
    {
        synchronizeSelected(mListItem);
    }

    public void synchronizeSelected(List<T> items)
    {
        if (items != null)
        {
            for (T item : items)
            {
                synchronizeSelected(item);
            }
        }
    }

    public void synchronizeSelected(T item)
    {
        synchronizeSelected(indexOfItem(item));
    }

    private void synchronizeSelected(int index)
    {
        T model = getItem(index);
        if (model instanceof DDSelectManager.Selectable)
        {
            Selectable sModel = (Selectable) model;
            setSelected(index, sModel.isSelected());
        }
    }

    /**
     * 获得选中项的位置(单选模式)
     *
     * @return
     */
    public int getSelectedIndex()
    {
        return mCurrentIndex;
    }

    /**
     * 获得选中项(单选模式)
     *
     * @return
     */
    public T getSelectedItem()
    {
        return getItem(mCurrentIndex);
    }

    /**
     * 获得选中项的位置(多选模式)
     *
     * @return
     */
    public List<Integer> getSelectedIndexs()
    {
        List<Integer> listIndex = new ArrayList<>();
        if (!mMapSelectedIndexItem.isEmpty())
        {
            for (Map.Entry<Integer, T> item : mMapSelectedIndexItem.entrySet())
            {
                listIndex.add(item.getKey());
            }
        }
        return listIndex;
    }

    /**
     * 获得选中项(多选模式)
     *
     * @return
     */
    public List<T> getSelectedItems()
    {
        List<T> listItem = new ArrayList<T>();
        if (!mMapSelectedIndexItem.isEmpty())
        {
            for (Map.Entry<Integer, T> item : mMapSelectedIndexItem.entrySet())
            {
                listItem.add(item.getValue());
            }
        }
        return listItem;
    }

    public void setItems(T... items)
    {
        List<T> listItem = null;
        if (items != null && items.length > 0)
        {
            listItem = Arrays.asList(items);
        }
        setItems(listItem);
    }

    public void setItems(List<T> items)
    {
        if (items != null)
        {
            mListItem = items;
        } else
        {
            mListItem.clear();
        }
        resetIndex();
        initItems(mListItem);
    }

    public void appendItems(List<T> items, boolean addAll)
    {
        if (items != null && !items.isEmpty())
        {
            if (!mListItem.isEmpty())
            {
                if (addAll)
                {
                    mListItem.addAll(items);
                }
                initItems(items);
            } else
            {
                setItems(items);
            }
        }
    }

    public void appendItem(T item, boolean add)
    {
        if (!mListItem.isEmpty() && item != null)
        {
            if (add)
            {
                mListItem.add(item);
            }
            initItem(indexOfItem(item), item);
        }
    }

    private void initItems(List<T> items)
    {
        if (items != null && !items.isEmpty())
        {
            T item = null;
            int index = 0;
            for (int i = 0; i < items.size(); i++)
            {
                item = items.get(i);
                index = indexOfItem(item);
                initItem(index, item);
            }
        }
    }

    /**
     * 设置数据后会遍历调用此方法对每个数据进行初始化
     *
     * @param index
     * @param item
     */
    protected void initItem(int index, T item)
    {

    }

    private void resetIndex()
    {
        switch (mMode)
        {
            case SINGLE:
            case SINGLE_MUST_ONE_SELECTED:
                mCurrentIndex = -1;
                break;
            case MULTI:
            case MULTI_MUST_ONE_SELECTED:
                mMapSelectedIndexItem.clear();
                break;

            default:
                break;
        }

    }

    private boolean isIndexLegal(int index)
    {
        if (index >= 0 && index < mListItem.size())
        {
            return true;
        }
        return false;
    }

    /**
     * 设置最后一次选中的位置选中(单选模式)
     */
    public void selectLastIndex()
    {
        setSelected(mLastIndex, true);
    }

    /**
     * 选中全部(多选模式)
     */
    public void selectAll()
    {
        for (int i = 0; i < mListItem.size(); i++)
        {
            setSelected(i, true);
        }
    }

    /**
     * 模拟点击该项
     *
     * @param index
     */
    public void performClick(int index)
    {
        if (isIndexLegal(index))
        {
            boolean selected = isSelected(index);
            setSelected(index, !selected);
        }
    }

    /**
     * 模拟点击该项
     *
     * @param item
     */
    public void performClick(T item)
    {
        performClick(indexOfItem(item));
    }

    /**
     * 设置该项的选中状态
     *
     * @param item
     * @param selected
     */
    public void setSelected(T item, boolean selected)
    {
        int index = indexOfItem(item);
        setSelected(index, selected);
    }

    /**
     * 设置该位置的选中状态
     *
     * @param index
     * @param selected
     */
    public void setSelected(int index, boolean selected)
    {
        if (!mIsEnable)
        {
            return;
        }

        if (!isIndexLegal(index))
        {
            return;
        }

        switch (mMode)
        {
            case SINGLE_MUST_ONE_SELECTED:
                if (selected)
                {
                    selectItemSingle(index);
                } else
                {
                    if (mCurrentIndex == index)
                    {
                        if (mReSelectCallback != null)
                        {
                            mReSelectCallback.onSelected(mCurrentIndex, getItem(mCurrentIndex));
                        }
                    } else
                    {

                    }
                }
                break;
            case SINGLE:
                if (selected)
                {
                    selectItemSingle(index);
                } else
                {
                    if (mCurrentIndex == index)
                    {
                        final int tempCurrentIndex = mCurrentIndex;
                        mCurrentIndex = -1;

                        notifyNormal(tempCurrentIndex);
                    } else
                    {

                    }
                }
                break;
            case MULTI_MUST_ONE_SELECTED:
                if (selected)
                {
                    selectItemMulti(index);
                } else
                {
                    if (mMapSelectedIndexItem.containsKey(index))
                    {
                        if (mMapSelectedIndexItem.size() == 1)
                        {

                        } else
                        {
                            mMapSelectedIndexItem.remove(index);
                            notifyNormal(index);
                        }
                    } else
                    {

                    }
                }
                break;
            case MULTI:
                if (selected)
                {
                    selectItemMulti(index);
                } else
                {
                    if (mMapSelectedIndexItem.containsKey(index))
                    {
                        mMapSelectedIndexItem.remove(index);
                        notifyNormal(index);
                    } else
                    {

                    }
                }
                break;

            default:
                break;
        }
    }

    private void selectItemSingle(int index)
    {
        if (mCurrentIndex == index)
        {

        } else
        {
            final int tempCurrentIndex = mCurrentIndex;
            mCurrentIndex = index;

            notifyNormal(tempCurrentIndex);
            notifySelected(mCurrentIndex);

            mLastIndex = mCurrentIndex;
        }
    }

    private void selectItemMulti(int index)
    {
        if (mMapSelectedIndexItem.containsKey(index))
        {

        } else
        {
            mMapSelectedIndexItem.put(index, getItem(index));
            notifySelected(index);
        }
    }

    private void notifyNormal(int index)
    {
        if (isIndexLegal(index))
        {
            notifyNormal(index, getItem(index));
        }
    }

    private void notifySelected(int index)
    {
        if (isIndexLegal(index))
        {
            notifySelected(index, getItem(index));
        }
    }

    protected void notifyNormal(final int index, final T item)
    {
        mSelectCallbackHolder.foreach(new DDIterateCallback<SelectCallback<T>>()
        {
            @Override
            public boolean next(int i, SelectCallback<T> callback, Iterator<SelectCallback<T>> it)
            {
                callback.onNormal(index, item);
                return false;
            }
        });
    }

    protected void notifySelected(final int index, final T item)
    {
        mSelectCallbackHolder.foreach(new DDIterateCallback<SelectCallback<T>>()
        {
            @Override
            public boolean next(int i, SelectCallback<T> callback, Iterator<SelectCallback<T>> it)
            {
                callback.onSelected(index, item);
                return false;
            }
        });
    }

    /**
     * 获得该位置的item
     *
     * @param index
     * @return
     */
    public T getItem(int index)
    {
        T item = null;
        if (isIndexLegal(index))
        {
            item = mListItem.get(index);
        }
        return item;
    }

    /**
     * 返回item的位置
     *
     * @param item
     * @return
     */
    public int indexOfItem(T item)
    {
        int index = -1;
        if (item != null)
        {
            index = mListItem.indexOf(item);
        }
        return index;
    }

    /**
     * 清除选中
     */
    public void clearSelected()
    {
        switch (mMode)
        {
            case SINGLE:
            case SINGLE_MUST_ONE_SELECTED:
                if (mCurrentIndex >= 0)
                {
                    final int tempCurrentIndex = mCurrentIndex;
                    resetIndex();
                    notifyNormal(tempCurrentIndex);
                }
                break;
            case MULTI:
            case MULTI_MUST_ONE_SELECTED:
                if (!mMapSelectedIndexItem.isEmpty())
                {
                    final Iterator<Map.Entry<Integer, T>> it = mMapSelectedIndexItem.entrySet().iterator();
                    while (it.hasNext())
                    {
                        final Map.Entry<Integer, T> item = it.next();
                        it.remove();
                        notifyNormal(item.getKey());
                    }
                    resetIndex();
                }
                break;
            default:
                break;
        }
    }

    public enum Mode
    {
        /**
         * 单选，必须选中一项
         */
        SINGLE_MUST_ONE_SELECTED,
        /**
         * 单选，可以一项都没选中
         */
        SINGLE,
        /**
         * 多选，必须选中一项
         */
        MULTI_MUST_ONE_SELECTED,
        /**
         * 多选，可以一项都没选中
         */
        MULTI;
    }

    public interface Selectable
    {
        boolean isSelected();

        void setSelected(boolean selected);
    }

    public interface SelectCallback<T>
    {
        /**
         * item正常回调
         *
         * @param index
         * @param item
         */
        void onNormal(int index, T item);

        /**
         * item选中回调
         *
         * @param index
         * @param item
         */
        void onSelected(int index, T item);
    }

    public interface ReSelectCallback<T>
    {
        void onSelected(int index, T item);
    }






}
