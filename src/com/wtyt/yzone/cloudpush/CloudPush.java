package com.wtyt.yzone.cloudpush;

import java.util.List;

import com.wtyt.yzone.cloudpush.exception.PushMsgException;
import com.wtyt.yzone.cloudpush.face.IdentityInfo;
import com.wtyt.yzone.cloudpush.face.MessageInfo;
import com.wtyt.yzone.cloudpush.face.PlatInfo;

public class CloudPush {
	
	/**
	 * 推送，定向信息
	 * @throws PushMsgException 
	 */
	public static void pushLocateMsg(PlatInfo platInfo,MessageInfo messageInfo,IdentityInfo identityInfo) throws PushMsgException{
		platInfo.pushLocateMsg(messageInfo,identityInfo);
	}
	
	/**
	 * 推送，广播信息
	 * @throws PushMsgException 
	 */
	public static void pushBroadcastMsg(PlatInfo platInfo,MessageInfo messageInfo,IdentityInfo identityInfo) throws PushMsgException{
		platInfo.pushBroadcastMsg(messageInfo,identityInfo);
	}
	
	/**
	 * 推送tag组信息
	 * @throws PushMsgException 
	 */
	public static void pushTagMsg(PlatInfo platInfo,MessageInfo messageInfo,IdentityInfo identityInfo) throws PushMsgException{
		platInfo.pushTagMsg(messageInfo,identityInfo);
	}
	
	/**
	 * 获取返回的设备信息
	 * @throws PushMsgException 
	 */
	public static List<DeviceInfo> getDeviceInfo(PlatInfo platInfo,MessageInfo messageInfo,IdentityInfo identityInfo) throws PushMsgException{		
		return platInfo.getDeviceInfo(messageInfo,identityInfo);
	}

}
