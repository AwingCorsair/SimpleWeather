package com.awingcorsair.simpleweather.fragment;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.awingcorsair.simpleweather.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mao on 2016/4/11.
 */
public class SettingFragment extends Fragment {
    private ListView settingListView;
    private List<String> settingList=new ArrayList<String>();
    private ArrayAdapter<String> adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.setting_fragment,container,false);
//        settingListView=(ListView)view.findViewById(R.id.setting);
//        settingList.add(0, "关于");
//        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, settingList);
//        settingListView.setAdapter(adapter);
//        settingListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                if(position==0){
//                    Toast.makeText(getActivity(),"h",Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
        return view;
    }
}
