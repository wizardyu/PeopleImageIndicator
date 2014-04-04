package com.wizard.constant;

public class AppConstant {
	/** ACACHE_KEY 缓存KEY**/
	//首页置顶内容
	public static final String ACACHE_KEY_HOMEPAGEFANGTAN ="acache_key_homepagefangtan";
	//最新访谈
	public static final String ACACHE_KEY_LATESTFANGTAN ="acache_key_latestfangtan";
	
	/**访谈各个数据XML地址**/
	public static final String FANGTAN_XML_HOMEPAGEFANGTANS = "xml/fangtan_homePage_0_1.xml";
	public static final String FANGTAN_XML_FEATUREDFANGTANS = "/api/fangtanApi.do?action=featuredFangtans";
	public static final String FANGTAN_XML_GUESTLIST = "";
	
	/**全局常量**/
	public static final String GLOBAL_CONSTANTS_DOMAIN = "http://10.100.4.99/";
	public static final String GLOBAL_CONSTANTS_SNSDOMAIN = "http://sns.people.com.cn/";
	
	/** xml 字段信息**/
	public static final String KEY_ITEM = "item"; // parent node
    public static final String KEY_ID = "id";
    public static final String KEY_USERNICK = "userNick";
    public static final String KEY_USERTITLE = "userTitle";
    public static final String KEY_LASTFANGTANDATE = "lastFangTanDate";
    public static final String KEY_USERIMAGE_URL = "userImage";
    public static final String KEY_FANGTANTITLE = "fangtanTitle";
    public static final String KEY_FANGTANIMAGE = "fangtanImage";
}