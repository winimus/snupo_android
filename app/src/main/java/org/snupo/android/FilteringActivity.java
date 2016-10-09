package org.snupo.android;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;

public class FilteringActivity extends Activity
{

	EditText user_id;
	ExpListViewAdapterWithCheckbox listAdapter;
	ExpandableListView expListView;
	ArrayList<String> listDataHeader;
	HashMap<String, List<String>> listDataChild;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_filtering);

		Intent intent = new Intent(this.getIntent());

		//ID EditText
		user_id = (EditText) findViewById(R.id.userid);
		String text = SharedPreferenceUtil.getSharedString(getApplicationContext(), "userid");
		user_id.setText(text);


		// get the listview
		expListView = (ExpandableListView) findViewById(R.id.lvExp);

		// preparing list data
		prepareListData();

		listAdapter = new ExpListViewAdapterWithCheckbox(this, listDataHeader, listDataChild);

		// setting list adapter
		expListView.setAdapter(listAdapter);

		// Listview Group click listener
		expListView.setOnGroupClickListener(new OnGroupClickListener() {

			@Override
			public boolean onGroupClick(ExpandableListView parent, View v,
										int groupPosition, long id) {
				// Toast.makeText(getApplicationContext(),
				// "Group Clicked " + listDataHeader.get(groupPosition),
				// Toast.LENGTH_SHORT).show();
				return false;
			}
		});

		// Listview Group expanded listener
		expListView.setOnGroupExpandListener(new OnGroupExpandListener() {

			@Override
			public void onGroupExpand(int groupPosition) {
				//Toast.makeText(getApplicationContext(),
				//		listDataHeader.get(groupPosition) + " Expanded",
				//		Toast.LENGTH_SHORT).show();
			}
		});

		// Listview Group collasped listener
		expListView.setOnGroupCollapseListener(new OnGroupCollapseListener() {

			@Override
			public void onGroupCollapse(int groupPosition) {
				//Toast.makeText(getApplicationContext(),
				//		listDataHeader.get(groupPosition) + " Collapsed",
				//		Toast.LENGTH_SHORT).show();

			}
		});

		// Listview on child click listener
		expListView.setOnChildClickListener(new OnChildClickListener() {

			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
										int groupPosition, int childPosition, long id) {
				// TODO Auto-generated method stub
				/*Toast.makeText(
						getApplicationContext(),
						listDataHeader.get(groupPosition)
								+ " : "
								+ listDataChild.get(
								listDataHeader.get(groupPosition)).get(
								childPosition), Toast.LENGTH_SHORT)
						.show();
						*/
				return false;
			}
		});

		//Save button and Reset Button
		Button savebutton = (Button) findViewById(R.id.save);
		savebutton.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v)
			{
				SharedPreferenceUtil.putSharedString(getApplicationContext(), "userid", user_id.getText().toString());
			}
		});

		CheckBox checkall = (CheckBox) findViewById(R.id.all);
		checkall.setChecked(SharedPreferenceUtil.getSharedBoolean(getApplicationContext(), "all"));
		checkall.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
		{
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
			{

				boolean checked = true;
				if(isChecked)
				{
					SharedPreferenceUtil.putSharedString(getApplicationContext(), "midappend", "all,");
					SharedPreferenceUtil.putSharedBoolean(getApplicationContext(), "all", true);


				}
				else
				{
					SharedPreferenceUtil.putSharedString(getApplicationContext(), "midappend",
							SharedPreferenceUtil.getSharedString(getApplicationContext(), "midappend").replace("all,", ""));
					SharedPreferenceUtil.putSharedBoolean(getApplicationContext(), "all", false);
				}
			}
		});


	}
	@Override
	protected void onDestroy()
	{

		sendToDatabase();
		if(user_id.getText().toString()==null)
			Toast.makeText(getApplicationContext(),"Error: ID does net exist",Toast.LENGTH_LONG).show();
		else
			Toast.makeText(getApplicationContext(),"Saved Settings",Toast.LENGTH_LONG).show();
		super.onDestroy();
	}


	/*
	 * Preparing the list data
	 */
	private void prepareListData() {
		listDataHeader = new ArrayList<String>();
		listDataChild = new HashMap<String, List<String>>();

		// Adding child data
		listDataHeader.add("홈");
		listDataHeader.add("모두 모임");
		listDataHeader.add("파트별 모임");
		listDataHeader.add("부서별 모임");
		listDataHeader.add("SNUPO");

		// Adding child data
		List<String> home = new ArrayList<String>();
		home.add("공지사항");
		home.add("출석부");
		home.add("회의록");
		home.add("선거 유세");
		home.add("선곡 추천");
		home.add("앙상블 신청");
		home.add("레슨 신청");
		home.add("웹사이트 이슈");
		//home.add("모바일 이슈");

		List<String> everybd = new ArrayList<String>();
		everybd.add("CN");
		everybd.add("신입생 인사");
		everybd.add("객원 게시판");

		List<String> eachpart = new ArrayList<String>();
		eachpart.add("1st Violin");
		eachpart.add("2nd Violin");
		eachpart.add("Viola");
		eachpart.add("Cello");
		eachpart.add("Double Bass");
		eachpart.add("Flute");
		eachpart.add("Oboe/Fagott");
		eachpart.add("Clarinet");
		eachpart.add("Brass");

		List<String> eachdpt = new ArrayList<String>();
		eachdpt.add("회계부");
		eachdpt.add("홍보부");
		eachdpt.add("기획부");
		eachdpt.add("섭외부");
		eachdpt.add("학술문화부");
		eachdpt.add("자료관리부");
		eachdpt.add("악보계");
		//eachdpt.add("임원수석");

		List<String> snupogp = new ArrayList<String>();
		snupogp.add("질문과 답변");




		listDataChild.put(listDataHeader.get(0), home); // Header, Child data
		listDataChild.put(listDataHeader.get(1), everybd);
		listDataChild.put(listDataHeader.get(2), eachpart);
		listDataChild.put(listDataHeader.get(3), eachdpt);
		listDataChild.put(listDataHeader.get(4), snupogp);
	}


    public void sendToDatabase()
    {
        String user_id = SharedPreferenceUtil.getSharedString(getApplicationContext(), "userid");
        String token = SharedPreferenceUtil.getSharedString(getApplicationContext(), "token");
		String midappend = SharedPreferenceUtil.getSharedString(getApplicationContext(), "midappend");
        final String strurl = "https://www.snupo.org/?module=push_notification&act" +
                "=insertClient&user_id="+user_id+"&token=" + token +"&document_filter="+midappend;
    //    Log.i("url", strurl);


        new AsyncTask<Void, Void, Void>()
        {
            String result;
            @Override
            protected Void doInBackground(Void... voids)
            {

                URL url = null;
                try {
                    url = new URL(strurl);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                try {
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    Log.i("http", conn.getURL().toString());
                    conn.setDoInput(true);
                    conn.setDoOutput(true);
                    conn.setUseCaches(false);
                    conn.setDefaultUseCaches(false);
                    conn.setRequestMethod("GET");
                    conn.connect();
                    InputStream is = conn.getInputStream();        //input스트림 개방
                    BufferedReader reader = new BufferedReader(new InputStreamReader(is,"UTF-8"));  //문자열 셋 세팅
                    reader.readLine();


                }
                catch(MalformedURLException | ProtocolException exception)
                {
                    exception.printStackTrace();
                }
                catch(IOException io)
                {
                    io.printStackTrace();
                }
                return null;
            }
        }.execute();

    }

}
