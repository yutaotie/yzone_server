package com.wtyt.yzone.cloudpush.test;

import com.wtyt.yzone.cloudpush.CloudPush;
import com.wtyt.yzone.cloudpush.baidu.BaiduIdentityInfo;
import com.wtyt.yzone.cloudpush.baidu.BaiduMessageInfo;
import com.wtyt.yzone.cloudpush.baidu.BaiduPlatInfo;
import com.wtyt.yzone.cloudpush.baidu.Constants;
import com.wtyt.yzone.cloudpush.exception.PushMsgException;
import com.wtyt.yzone.cloudpush.face.PlatInfo;




public class CloudPushTest {

	public static void main(String[] args) {		
		
		PlatInfo platInfo = new BaiduPlatInfo();
		BaiduMessageInfo messageInfo = new BaiduMessageInfo();
		messageInfo.setContent("波哥今晚请吃饭呀     伊雅伊   啦啦啦  ");
		messageInfo.setTitle("卧槽");		
		//messageInfo.setUserId("781528832237943880");
		messageInfo.setUserId("1101833238630935800");
		messageInfo.setTagName("我咯割草");
		//messageInfo.setChannelId(4445123488662521050L);
		messageInfo.setChannelId(3885558133581669631L);
		BaiduIdentityInfo identityInfo = new BaiduIdentityInfo();	
		identityInfo.setDeviceType(Constants.BD_DEVICE_TYPE_ANDROID);
		try {
			//CloudPush.pushBroadcastMsg(platInfo, messageInfo, identityInfo);		
			CloudPush.pushLocateMsg(platInfo, messageInfo, identityInfo);
			//CloudPush.pushTagMsg(platInfo, messageInfo, identityInfo);
			CloudPush.getDeviceInfo(platInfo, messageInfo, identityInfo);
		} catch (PushMsgException e) {
			System.out.println(e.getMessage());       
		}
		
	}

}
