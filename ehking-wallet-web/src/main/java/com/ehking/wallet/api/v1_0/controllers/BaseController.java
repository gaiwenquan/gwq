package com.ehking.wallet.api.v1_0.controllers;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.mlw.vlh.ValueList;
import net.mlw.vlh.ValueListHandler;
import net.mlw.vlh.ValueListInfo;

import org.apache.commons.validator.GenericValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONObject;
import com.ehking.commons.utils.thread.ThreadContext;
import com.ehking.commons.valuelist.ValueListInfoExt;
import com.ehking.commons.web.Constant;
import com.ehking.member.dto.MemberDTO;
import com.ehking.member.facade.MemberFacade;
import com.ehking.member.merchant.dto.MerchantDTO;
import com.ehking.member.merchant.facade.MerchantFacade;

public abstract class BaseController {

	private static final Logger log = LoggerFactory
			.getLogger(BaseController.class);

	@Autowired
	protected ValueListHandler valueListHandler;

	@Autowired
	private MerchantFacade merchantFacade;

	@Autowired
	private MemberFacade memberFacade;

	final static String MESSAGE = "message";
	final static String ERROR_CODE = "errorCode";

	protected ValueListInfoExt getValueListInfo(int pageSize, int pageIndex) {
		int pageNumber = 1;
		if (GenericValidator.isInRange(pageIndex, 1, Integer.MAX_VALUE)) {
			pageNumber = pageIndex;
		}
		ValueListInfoExt valueListInfo = new ValueListInfoExt();
		valueListInfo.setPagingPage(pageNumber);
		valueListInfo.setPagingNumberPer(pageSize);
		return valueListInfo;
	}

	/**
	 * 根据BUI.grid定制的json串，格式： { "rows" : [{},{}], //数据集合 "results" : 100, //记录总数
	 * "hasError" : false, //是否存在错误 "error" : "" // 仅在 hasError : true 时使用 }
	 * 
	 * @Title: query
	 * @todo:
	 * @param adapterName
	 * @param valueListInfo
	 * @return String
	 * @author qgm
	 * @date 2014年9月25日 上午10:35:17
	 */
	protected String query(String adapterName, ValueListInfo valueListInfo) {
		ValueList valueList = valueListHandler.getValueList(adapterName,
				valueListInfo);
		return toBuiDataString(valueList);
	}

	protected ValueList search(String adapterName, ValueListInfo valueListInfo) {
		ValueList valueList = valueListHandler.getValueList(adapterName,
				valueListInfo);
		return valueList;
	}

	public String toBuiDataString(ValueList valueList) {
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("rows", valueList.getList());
		data.put("results", valueList.getValueListInfo()
				.getTotalNumberOfEntries());
		data.put("page", valueList.getValueListInfo().getPagingPage());
		// data.put("hasError", false);
		// data.put("error", "");
		return JSONObject.toJSONString(data);
	}

	/**
	 * 
	 * @Title: getCurrencyMember
	 * @todo: 查询当前登录会员信息
	 * @return MemberDTO
	 * @author majinchao
	 * @date 2015年5月10日 上午11:25:39
	 */
	public MemberDTO getCurrencyMember() {
		ThreadContext threadContext = ThreadContext.getContext();
		String memberId = threadContext.get(
				Constant.THREAD_CONTEXT_OAUTH_MEMBER_ID, String.class);
		log.info("memberId====" + memberId);
		MemberDTO dto = memberFacade.getById(memberId);
		return dto;
	}

	public MerchantDTO getCurrencyMerchant() {
		ThreadContext threadContext = ThreadContext.getContext();
		String memberId = threadContext.get(
				Constant.THREAD_CONTEXT_OAUTH_MEMBER_ID, String.class);
		log.info("memberId====" + memberId);
		MerchantDTO dto = merchantFacade.findByMemberId(memberId);
		return dto;
	}

	/**
	 * 
	 * @Title: getCurrencyMember
	 * @todo: 查询当前登录会员id
	 * @return MemberDTO
	 * @author majinchao
	 * @date 2015年7月10日 上午11:25:39
	 */
	public String getCurrencyMemberId() {
		ThreadContext threadContext = ThreadContext.getContext();
		String memberId = threadContext.get(
				Constant.THREAD_CONTEXT_OAUTH_MEMBER_ID, String.class);
		return memberId;
	}

	/**
	 * 
	 * @Title: getUniqueDeviceId
	 * @todo: 查询当前登录会员的推送设备唯一标识
	 * @date 2015年7月10日 上午11:25:39
	 */
	public String getUniqueDeviceId() {
		ThreadContext threadContext = ThreadContext.getContext();
		String uniqueDeviceId = threadContext.get(
				Constant.THREAD_CONTEXT_OAUTH_UNIQUE_DEVICE_ID, String.class);
		log.info("uniqueDeviceId====" + uniqueDeviceId);
		return uniqueDeviceId;
	}
	
	/**
	 * 获取ip
	 * @param request
	 * @return
	 */
	protected String getIpAddr(HttpServletRequest request) {
		String ipAddress = null;   
		ipAddress = request.getHeader("x-forwarded-for");   
		if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {   
			ipAddress = request.getHeader("Proxy-Client-IP");   
		}   
		if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {   
			ipAddress = request.getHeader("WL-Proxy-Client-IP");   
		}   
	    if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {   
	    	ipAddress = request.getRemoteAddr();   
	    	if(ipAddress.equals("127.0.0.1")){   
	    		//根据网卡取本机配置的IP   
	    		InetAddress inet=null;   
	    		try {   
	    			inet = InetAddress.getLocalHost();   
	    		} catch (UnknownHostException e) {   
	    			e.printStackTrace();   
	    		}   
	    		ipAddress= inet.getHostAddress();   
	    	}   
	    }   
	    //对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割   
	    if(ipAddress!=null && ipAddress.length()>15){ //"***.***.***.***".length() = 15   
	        if(ipAddress.indexOf(",")>0){   
	        	ipAddress = ipAddress.substring(0,ipAddress.indexOf(","));   
	        }   
	    }   
	    return ipAddress;    
	} 
}
