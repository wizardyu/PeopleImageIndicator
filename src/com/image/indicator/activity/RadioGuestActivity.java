package com.image.indicator.activity;
/**
 * @author wizardyu
 * 嘉宾列表
 */
import java.util.ArrayList;
import java.util.HashMap;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;

import com.image.indicator.R;
import com.wizard.adapter.NetImageAdapter;
import com.wizard.constant.AppConstant;
import com.wizard.listener.FangtanGuestListListener;
import com.wizard.parser.XmlParser;

public class RadioGuestActivity extends BaseActivity {
    // 所有的静态变量
    static final String URL = AppConstant.GLOBAL_CONSTANTS_DOMAIN+"/xml/fangtanGuest_1.xml";// xml目的地址,打开地址看一下
    // XML 节点
  
    
    
    NetImageAdapter adatper;
    ListView listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.radio_guest);
            LoadData();

    }

    private void LoadData() {
            ArrayList<HashMap<String, String>> songsList = new ArrayList<HashMap<String, String>>();

            XmlParser parser = new XmlParser();
            String xml = parser.getXmlFromUrl(URL); // 从网络获取xml
            Document doc = parser.getDomElement(xml); // 获取 DOM 节点
            NodeList nl = doc.getElementsByTagName(AppConstant.KEY_ITEM);
            // 循环遍历所有的歌节点 <song>
            for (int i = 0; i < nl.getLength(); i++) {
                    // 新建一个 HashMap
                    HashMap<String, String> map = new HashMap<String, String>();
                    Element e = (Element) nl.item(i);
                    // 每个子节点添加到HashMap关键= >值
                    map.put(AppConstant.KEY_ID, parser.getValue(e, AppConstant.KEY_ID));
                    map.put(AppConstant.KEY_USERNICK, parser.getValue(e, AppConstant.KEY_USERNICK));
                    map.put(AppConstant.KEY_USERTITLE, parser.getValue(e,AppConstant.KEY_USERTITLE));
                    map.put(AppConstant.KEY_LASTFANGTANDATE, parser.getValue(e, AppConstant.KEY_LASTFANGTANDATE));
                    map.put(AppConstant.KEY_USERIMAGE_URL, parser.getValue(e, AppConstant.KEY_USERIMAGE_URL));

                    // HashList添加到数组列表
                    songsList.add(map);
            }
            listview = (ListView) findViewById(R.id.guest_list);
            adatper = new NetImageAdapter(this, songsList);
            listview.setAdapter(adatper);
            listview.setOnClickListener(new FangtanGuestListListener(this));
    }


}
