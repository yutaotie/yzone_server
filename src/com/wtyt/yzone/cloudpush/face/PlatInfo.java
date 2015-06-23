package com.wtyt.yzone.cloudpush.face;

import java.util.List;

import com.wtyt.yzone.cloudpush.DeviceInfo;
import com.wtyt.yzone.cloudpush.exception.PushMsgException;

public interface PlatInfo {

	void pushLocateMsg(MessageInfo messageInfo, IdentityInfo identityInfo)  throws PushMsgException;

	void pushBroadcastMsg(MessageInfo messageInfo, IdentityInfo identityInfo)  throws PushMsgException;

	void pushTagMsg(MessageInfo messageInfo, IdentityInfo identityInfo)  throws PushMsgException;

	List<DeviceInfo> getDeviceInfo(MessageInfo messageInfo,IdentityInfo identityInfo)throws PushMsgException;

}
