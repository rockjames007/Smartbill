package com.example.dipuj.smartbill.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.example.dipuj.smartbill.R;
import com.example.dipuj.smartbill.modal.Reading;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by ABC on 25-03-2018.
 */

public class ReadingExpandableListAdapter extends BaseExpandableListAdapter {

    private LayoutInflater mLayoutInflater;
    private ArrayList<String> headerList;
    private HashMap<String, ArrayList<Reading>> childList;

    public ReadingExpandableListAdapter(Context mContext, ArrayList<String> headerList, HashMap<String, ArrayList<Reading>> childList){
        mLayoutInflater=(LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.headerList = headerList;
        this.childList = childList;
    }

    @Override
    public int getGroupCount() {
        return this.headerList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this.childList.get(this.headerList.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition)
    {
        return this.headerList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon)
    {
        return this.childList.get(this.headerList.get(groupPosition)).get(childPosititon);
    }

    @Override
    public long getGroupId(int groupPosition)
    {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition)
    {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View view, ViewGroup parent) {

        final ViewholderParentMenu mViewHolder;

        if(view==null)
        {

            view=mLayoutInflater.inflate(R.layout.row_group_item, parent,false);

            mViewHolder=new ViewholderParentMenu(view);

            view.setTag(mViewHolder);

        }else
        {

            mViewHolder=(ViewholderParentMenu) view.getTag();
        }

        mViewHolder.mTextViewHeader.setText((String)getGroup(groupPosition));

        return view;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View view, ViewGroup child) {

        ViewHolderChild viewHolderChild;

        if(view==null)
        {
            view=mLayoutInflater.inflate(R.layout.row_child_item, child,false);

            viewHolderChild=new ViewHolderChild(view);

            view.setTag(viewHolderChild);

        }else
        {

            viewHolderChild=(ViewHolderChild) view.getTag();
        }

        Reading reading = (Reading)getChild(groupPosition,childPosition);

        viewHolderChild.mTextViewDate.setText(reading.getTimestamp().toString());

        viewHolderChild.mTextViewReading.setText(reading.getReading().toString());

        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }

    private class ViewholderParentMenu
    {
        private TextView mTextViewHeader;

        public ViewholderParentMenu(View view)
        {
            mTextViewHeader = view.findViewById(R.id.textview_week);
        }
    }

    private class ViewHolderChild
    {
        private TextView mTextViewDate;
        private TextView mTextViewReading;

        public ViewHolderChild(View view)
        {
            mTextViewDate = view.findViewById(R.id.textview_date);
            mTextViewReading = view.findViewById(R.id.textview_reading);
        }
    }
}
