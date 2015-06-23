package com.wtyt.yzone.cloudpush.baidu;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.baidu.yun.channel.auth.ChannelKeyPair;
import com.baidu.yun.channel.client.BaiduChannelClient;
import com.baidu.yun.channel.exception.ChannelClientException;
import com.baidu.yun.channel.exception.ChannelServerException;
import com.baidu.yun.channel.model.BindInfo;
import com.baidu.yun.channel.model.PushBroadcastMessageRequest;
import com.baidu.yun.channel.model.PushBroadcastMessageResponse;
import com.baidu.yun.channel.model.PushTagMessageRequest;
import com.baidu.yun.channel.model.PushTagMessageResponse;
import com.baidu.yun.channel.model.PushUnicastMessageRequest;
import com.baidu.yun.channel.model.PushUnicastMessageResponse;
import com.baidu.yun.channel.model.QueryBindListRequest;
import com.baidu.yun.channel.model.QueryBindListResponse;
import com.baidu.yun.core.log.YunLogEvent;
import com.baidu.yun.core.log.YunLogHandler;
import com.wtyt.yzone.cloudpush.DeviceInfo;
import com.wtyt.yzone.cloudpush.exception.PushMsgException;
import com.wtyt.yzone.cloudpush.face.IdentityInfo;
import com.wtyt.yzone.cloudpush.face.MessageInfo;
import com.wtyt.yzone.cloudpush.face.PlatInfo;

public class BaiduPlatInfo implements PlatInfo {
	
	public BaiduMessageInfo baiduMessageInfo;
	public BaiduIdentityInfo baiduIdentityInfo;
	
	
	/**
	 * 执行定向推送信息
	 * @param messageInfo
	 * @param identityInfo
	 * @throws PushMsgException 
	 */
	public void pushLocateMsg(MessageInfo messageInfo,IdentityInfo identityInfo) throws PushMsgException{
		BaiduChannelClient channelClient = intializedMsgIden(messageInfo,identityInfo);
		try {			
			//创建请求类对象
			PushUnicastMessageRequest request = new PushUnicastMessageRequest();
			request.setDeviceType(baiduIdentityInfo.getDeviceType());	// device_type => 1: web 2: pc 3:android 4:ios 5:wp		
			request.setChannelId(baiduMessageInfo.getChannelId());	
			request.setUserId(baiduMessageInfo.getUserId());
			JSONObject jsob = new JSONObject();
			jsob.put("title",baiduMessageInfo.getTitle());
			jsob.put("description",baiduMessageInfo.getContent());
				// 若要通知，
			request.setMessageType(1);
			request.setMessage(jsob.toString());								
			//调用pushMessage接口
			PushUnicastMessageResponse response = channelClient.pushUnicastMessage(request);				
			//认证推送成功
			System.out.println("push amount : " + response.getSuccessAmount()+", device type :"+baiduIdentityInfo.getDeviceType()); 			
		} catch (ChannelClientException e) {
			//处理客户端错误异常
			e.printStackTrace();
		} catch (ChannelServerException e) {
			//处理服务端错误异常
			System.out.println(
					String.format("request_id: %d, error_code: %d, error_message: %s" , 
						e.getRequestId(), e.getErrorCode(), e.getErrorMsg()
						)
			);
		} catch (JSONException e) {
				e.printStackTrace();
		}
		
		
		
		
	}
	
	/**
	 * 将消息体与鉴权体
	 * @throws PushMsgException
	 */
	public BaiduChannelClient intializedMsgIden(MessageInfo messageInfo,IdentityInfo identityInfo) throws PushMsgException{		
		if(!(messageInfo instanceof BaiduMessageInfo)){
			throw new PushMsgException("消息体异常");
		}
		if(!(identityInfo instanceof BaiduIdentityInfo)){
			throw new PushMsgException("鉴权体异常");
		}		
		baiduMessageInfo = (BaiduMessageInfo)messageInfo;		
		baiduIdentityInfo = (BaiduIdentityInfo)identityInfo;		
		String apiKey =baiduIdentityInfo.getApiKey();
		String secretKey =baiduIdentityInfo.getSecretKey();
		ChannelKeyPair pair = new ChannelKeyPair(apiKey, secretKey);		
		// 2. 创建BaiduChannelClient对象实例
		BaiduChannelClient baiduChannelClient = new BaiduChannelClient(pair);
		//若要了解交互细节，请注册YunLogHandler类
		baiduChannelClient.setChannelLogHandler(new YunLogHandler() {
			public void onHandle(YunLogEvent event) {
				System.out.println(event.getMessage());
			}
		});	
		return baiduChannelClient;
	}

	/**
	 * 推送广播信息
	 */
	public void pushBroadcastMsg(MessageInfo messageInfo,IdentityInfo identityInfo) throws PushMsgException {
		BaiduChannelClient baiduChannelClient = intializedMsgIden(messageInfo,identityInfo);
		try {			
			// 4. 创建请求类对象
			PushBroadcastMessageRequest request = new PushBroadcastMessageRequest();
			request.setDeviceType(baiduIdentityInfo.getDeviceType());	// device_type => 1: web 2: pc 3:android 4:ios 5:wp		
			JSONObject jsob = new JSONObject();
			jsob.put("title", baiduMessageInfo.getTitle());
			jsob.put("description", baiduMessageInfo.getContent());
				// 若要通知，
			request.setMessageType(1);
			request.setMessage(jsob.toString());			
			// 5. 调用pushMessage接口
			PushBroadcastMessageResponse response = baiduChannelClient.pushBroadcastMessage(request);
				
			// 6. 认证推送成功
			System.out.println("push amount : " + response.getSuccessAmount()+", device type :"+baiduIdentityInfo.getDeviceType()); 
			
		} catch (ChannelClientException e) {
			// 处理客户端错误异常
			e.printStackTrace();
		} catch (ChannelServerException e) {
			// 处理服务端错误异常
			System.out.println(
					String.format("request_id: %d, error_code: %d, error_message: %s" , 
						e.getRequestId(), e.getErrorCode(), e.getErrorMsg()
						)
					);
		} catch (JSONException e) {
			
		}
	}

	/**
	 * 推送标签信息
	 */
	public void pushTagMsg(MessageInfo messageInfo, IdentityInfo identityInfo) throws PushMsgException {
		BaiduChannelClient baiduChannelClient = intializedMsgIden(messageInfo,identityInfo);
		try {
			//创建请求类对象
			PushTagMessageRequest request = new PushTagMessageRequest();
			request.setDeviceType(baiduIdentityInfo.getDeviceType()); 	// device_type => 1: web 2: pc 3:android 4:ios 5:wp	
			request.setTagName(baiduMessageInfo.getTagName());
			JSONObject jsob = new JSONObject();
			jsob.put("title", baiduMessageInfo.getTitle());
			jsob.put("description", baiduMessageInfo.getContent());
			// 若要通知，
			request.setMessageType(1);
			request.setMessage(jsob.toString());			
			//调用pushMessage接口
			PushTagMessageResponse response = baiduChannelClient.pushTagMessage(request);				
			//认证推送成功
			System.out.println("push amount : " + response.getSuccessAmount()+", device type :"+baiduIdentityInfo.getDeviceType()); 			
		} catch (ChannelClientException e) {
			// 处理客户端错误异常
			e.printStackTrace();
		} catch (ChannelServerException e) {
			// 处理服务端错误异常
			System.out.println(
					String.format("request_id: %d, error_code: %d, error_message: %s" , 
						e.getRequestId(), e.getErrorCode(), e.getErrorMsg()
						)
					);
		} catch (JSONException e) {
			
		}
		
	}

	/**
	 * 获取设备的信息
	 */
	public List<DeviceInfo> getDeviceInfo(MessageInfo messageInfo,IdentityInfo identityInfo) throws PushMsgException {
		BaiduChannelClient baiduChannelClient = intializedMsgIden(messageInfo,identityInfo);
		List<DeviceInfo>  bdList = new ArrayList<DeviceInfo>();				
		try {
			//创建请求类对象
			//手机端的UserId，
			QueryBindListRequest request = new QueryBindListRequest();
			request.setUserId(baiduMessageInfo.getUserId());			
			// 5. 调用queryBindList接口
			QueryBindListResponse response = baiduChannelClient.queryBindList(request);						
			// 6. 对返回的结果对象进行操作
			List<BindInfo> bindInfos = response.getBinds();
			for ( BindInfo bindInfo : bindInfos ) {
				long channelId = bindInfo.getChannelId();
				String userId = bindInfo.getUserId();
				int status = bindInfo.getBindStatus();
				System.out.println("channel_id:" + channelId + ", user_id: " + userId + ", status: " + status);				
				String bindName = bindInfo.getBindName();
				long bindTime = bindInfo.getBindTime();
				String deviceId = bindInfo.getDeviceId();
				int deviceType = bindInfo.getDeviceType();
				long timestamp = bindInfo.getOnlineTimestamp();
				long expire = bindInfo.getOnlineExpires();
				DeviceInfo  bdean = new DeviceInfo();
				bdean.setChannelId(channelId);
				bdean.setUserId(userId);
				bdean.setStatus(status);
				bdean.setBindName(bindName);
				bdean.setDeviceId(deviceId);
				bdean.setDeviceType(deviceType);
				bdean.setTimestamp(timestamp);
				bdean.setExpire(expire);				
				System.out.println("bind_name:" + bindName + "\t" + "bind_time:" + bindTime);
				System.out.println("device_type:" + deviceType + "\tdeviceId" + deviceId);
				System.out.println(String.format("timestamp: %d, expire: %d", timestamp, expire));
				bdList.add(bdean);
			}			
		} catch (ChannelClientException e) {
			// 处理客户端错误异常
			e.printStackTrace();
		} catch (ChannelServerException e) {
			// 处理服务端错误异常
			System.out.println(
					String.format("request_id: %d, error_code: %d, error_message: %s" , 
						e.getRequestId(), e.getErrorCode(), e.getErrorMsg()
						)
					);
		}		
		return bdList;		
		
	}

}
