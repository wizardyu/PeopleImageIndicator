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
import android.widget.ListView;

import com.image.indicator.R;
import com.wizard.adapter.NetMusicAdapter;
import com.wizard.constant.AppConstant;
import com.wizard.util.XmlParser;

public class RadioGuestActivity extends BaseActivity {
    // 所有的静态变量
    static final String URL = AppConstant.GLOBAL_CONSTANTS_DOMAIN+"/xml/fangtanGuest_1.xml";// xml目的地址,打开地址看一下
    // XML 节点
    static final String KEY_ITEM = "item"; // parent node
    static final String KEY_ID = "id";
    public static final String KEY_USERNICK = "userNick";
    public static final String KEY_USERTITLE = "userTitle";
    public static final String KEY_LASTFANGTANDATE = "lastFangTanDate";
    public static final String KEY_USERIMAGE_URL = "userImage";
    
    
    NetMusicAdapter adatper;
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
            NodeList nl = doc.getElementsByTagName(KEY_ITEM);
            // 循环遍历所有的歌节点 <song>
            for (int i = 0; i < nl.getLength(); i++) {
                    // 新建一个 HashMap
                    HashMap<String, String> map = new HashMap<String, String>();
                    Element e = (Element) nl.item(i);
                    // 每个子节点添加到HashMap关键= >值
                    map.put(KEY_ID, parser.getValue(e, KEY_ID));
                    map.put(KEY_USERNICK, parser.getValue(e, KEY_USERNICK));
                    map.put(KEY_USERTITLE, parser.getValue(e, KEY_USERTITLE));
                    map.put(KEY_LASTFANGTANDATE, parser.getValue(e, KEY_LASTFANGTANDATE));
                    map.put(KEY_USERIMAGE_URL, parser.getValue(e, KEY_USERIMAGE_URL));

                    // HashList添加到数组列表
                    songsList.add(map);
            }
            listview = (ListView) findViewById(R.id.guest_list);
            adatper = new NetMusicAdapter(this, songsList);
            listview.setAdapter(adatper);

    }


}
