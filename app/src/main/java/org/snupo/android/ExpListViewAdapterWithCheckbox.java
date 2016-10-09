package org.snupo.android;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

// Eclipse wanted me to use a sparse array instead of my hashmaps, I just suppressed that suggestion
@SuppressLint("UseSparseArrays")
public class ExpListViewAdapterWithCheckbox extends BaseExpandableListAdapter {

	// Define activity context
	private Context mContext;
	/*
	 * Here we have a Hashmap containing a String key
	 * (can be Integer or other type but I was testing
	 * with contacts so I used contact name as the key)
	*/
	private HashMap<String, List<String>> mListDataChild;

	// ArrayList that is what each key in the above
	// hashmap points to
	private ArrayList<String> mListDataGroup;

	// Hashmap for keeping track of our checkbox check states
	//private HashMap<Integer, String> mChildCheckStates;


	// Our getChildView & getGroupView use the viewholder patter
	// Here are the viewholders defined, the inner classes are
	// at the bottom
	private ChildViewHolder childViewHolder;
	private GroupViewHolder groupViewHolder;

	/*
		  *  For the purpose of this document, I'm only using a single
	 *	textview in the group (parent) and child, but you're limited only
	 *	by your XML view for each group item :)
	*/
	private String groupText;
	private String childText;

	/*  Here's the constructor we'll use to pass in our calling
	 *  activity's context, group items, and child items
	*/
	public ExpListViewAdapterWithCheckbox(Context context, ArrayList<String> listDataGroup,          HashMap<String, List<String>> listDataChild){

		mContext = context;
		mListDataGroup = listDataGroup;
		mListDataChild = listDataChild;

		// Initialize our hashmap containing our check states here
		//mChildCheckStates = new HashMap<Integer, String>();
	}

	@Override
	public int getGroupCount()
	{
		return mListDataGroup.size();
	}

	/*
	 * This defaults to "public object getGroup" if you auto import the methods
	 * I've always make a point to change it from "object" to whatever item
	 * I passed through the constructor
	*/
	@Override
	public String getGroup(int groupPosition)
	{
		return mListDataGroup.get(groupPosition);
	}

	@Override
	public long getGroupId(int groupPosition)
	{

		return groupPosition;
	}


	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
							 View convertView, ViewGroup parent) {

		//  I passed a text string into an activity holding a getter/setter
		//  which I passed in through "ExpListGroupItems".
		//  Here is where I call the getter to get that text
		groupText = getGroup(groupPosition);

		if (convertView == null)
		{

			LayoutInflater inflater = (LayoutInflater) mContext
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.list_group, null);

			// Initialize the GroupViewHolder defined at the bottom of this document
			groupViewHolder = new GroupViewHolder();
			groupViewHolder.mGroupText = (TextView) convertView.findViewById(R.id.lblListHeader);
			convertView.setTag(groupViewHolder);
		}
		else
		{
			groupViewHolder = (GroupViewHolder) convertView.getTag();
		}
		groupViewHolder.mGroupText.setText(groupText);

		return convertView;
	}

	@Override
	public int getChildrenCount(int groupPosition)
	{
		return mListDataChild.get(mListDataGroup.get(groupPosition)).size();
	}

	/*
	 * This defaults to "public object getChild" if you auto import the methods
	 * I've always make a point to change it from "object" to whatever item
	 * I passed through the constructor
	*/
	@Override
	public String getChild(int groupPosition, int childPosition)
	{
		return mListDataChild.get(mListDataGroup.get(groupPosition)).get(childPosition);
		//return SharedPreferenceUtil.getSharedString(mContext.getApplicationContext(),
		//		SharedPreferenceUtil.getSharedString(mContext.getApplicationContext(), String.valueOf(groupPosition)));
	}


	@Override
	public long getChildId(int groupPosition, int childPosition)
	{
		return childPosition;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

		final int mGroupPosition = groupPosition;
		final int mChildPosition = childPosition;

		//  I passed a text string into an activity holding a getter/setter
		//  which I passed in through "ExpListChildItems".
		//  Here is where I call the getter to get that text
		childText = getChild(mGroupPosition, mChildPosition);
		if (convertView == null)
		{

			LayoutInflater inflater = (LayoutInflater) this.mContext
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.list_item, null);

			childViewHolder = new ChildViewHolder();

			childViewHolder.mChildText = (TextView) convertView
					.findViewById(R.id.lblListItem);

			childViewHolder.mCheckBox = (CheckBox) convertView
					.findViewById(R.id.lstcheckBox);

			convertView.setTag(R.layout.list_item, childViewHolder);

		} else
		{

			childViewHolder = (ChildViewHolder) convertView
					.getTag(R.layout.list_item);
		}

		childViewHolder.mChildText.setText(childText);

		/*
		 * You have to set the onCheckChangedListener to null
		 * before restoring check states because each call to
		 * "setChecked" is accompanied by a call to the
		 * onCheckChangedListener
		*/
		childViewHolder.mCheckBox.setOnCheckedChangeListener(null);
		Log.i("CehckStates", SharedPreferenceUtil.getSharedString(mContext.getApplicationContext(), "CheckStates"));
		if (SharedPreferenceUtil.getSharedString(mContext.getApplicationContext(), "CheckStates")
				.contains(String.valueOf(mGroupPosition)))
		{
			/*
			 * if the hashmap mChildCheckStates<Integer, Boolean[]> contains
			 * the value of the parent view (group) of this child (aka, the key),
			 * then retrive the boolean array getChecked[]
			*/
			String getChecked = SharedPreferenceUtil.getSharedString(mContext.getApplicationContext(), String.valueOf(mGroupPosition)); //mChildCheckStates.get(mGroupPosition);

			// set the check state of this position's checkbox based on the
			// boolean value of getChecked[position]
			childViewHolder.mCheckBox.setChecked(getChecked.substring(mChildPosition, mChildPosition+1).equals("1"));

		} else
		{
			SharedPreferenceUtil.putSharedString(mContext.getApplicationContext(), "CheckStates",
					SharedPreferenceUtil.getSharedString(mContext.getApplicationContext(), "CheckStates")
							+String.valueOf(mGroupPosition));
			/*
			 * if the hashmap mChildCheckStates<Integer, Boolean[]> does not
			 * contain the value of the parent view (group) of this child (aka, the key),
			 * (aka, the key), then initialize getChecked[] as a new boolean array
			 *  and set it's size to the total number of children associated with
			 *  the parent group
			*/
			String getChecked = SharedPreferenceUtil.getSharedString(mContext.getApplicationContext(), String.valueOf(mGroupPosition));
			for(int i=0; i<getChildrenCount(mGroupPosition);i++)
				getChecked= getChecked + "0";
			SharedPreferenceUtil.putSharedString(mContext.getApplicationContext(), String.valueOf(mGroupPosition),
					getChecked);
			// set the check state of this position's checkbox based on the
			// boolean value of getChecked[position]
			childViewHolder.mCheckBox.setChecked(false);
		}

		childViewHolder.mCheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener()
		{

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
			{
				String getChecked = SharedPreferenceUtil.getSharedString(mContext.getApplicationContext(), String.valueOf(mGroupPosition)); //mChildCheckStates.get(mGroupPosition);
				getChecked= getChecked.substring(0, mChildPosition)+String.valueOf((isChecked)? 1: 0) + getChecked.substring(mChildPosition+1);	//getChecked[mChildPosition] = isChecked;
				SharedPreferenceUtil.putSharedString(mContext.getApplicationContext(), String.valueOf(mGroupPosition), getChecked);		//mChildCheckStates.put(mGroupPosition, getChecked);
				if(isChecked)
				{
					SharedPreferenceUtil.putSharedString(mContext.getApplicationContext(), "midappend",
							SharedPreferenceUtil.getSharedString(mContext.getApplicationContext(), "midappend")
									+ BrowserTitleConverter.positionsToMid(mGroupPosition, mChildPosition) + ",");
				}
				else
				{
					SharedPreferenceUtil.putSharedString(mContext.getApplicationContext(), "midappend",
							SharedPreferenceUtil.getSharedString(mContext.getApplicationContext(), "midappend").replace
									(BrowserTitleConverter.positionsToMid(mGroupPosition, mChildPosition) + ",", ""));
				}
			}
		});

		return convertView;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition)
	{
		return false;
	}

	@Override
	public boolean hasStableIds()
	{
		return false;
	}

	public final class GroupViewHolder {

		TextView mGroupText;
	}

	public final class ChildViewHolder {

		TextView mChildText;
		CheckBox mCheckBox;
	}
}