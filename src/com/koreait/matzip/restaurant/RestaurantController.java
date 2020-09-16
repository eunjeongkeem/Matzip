package com.koreait.matzip.restaurant;

import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import com.koreait.matzip.CommonDAO;
import com.koreait.matzip.CommonUtils;
import com.koreait.matzip.Const;
import com.koreait.matzip.FileUtils;
import com.koreait.matzip.SecurityUtils;
import com.koreait.matzip.ViewRef;
import com.koreait.matzip.vo.RestaurantRecommendMenuVO;
import com.koreait.matzip.vo.RestaurantVO;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
//import com.sun.corba.se.impl.protocol.giopmsgheaders.RequestMessage;


public class RestaurantController {
	private RestaurantService service;
	
	public RestaurantController() {
		service = new RestaurantService();
	}
	
	public String restMap(HttpServletRequest request) {
		request.setAttribute(Const.TITLE, "지도보기");
		request.setAttribute(Const.VIEW, "restaurant/restMap");
		return ViewRef.TEMP_MENU_TEMP;
	}
	
	public String restReg(HttpServletRequest request) {
		final int I_M = 1; //카테고리 코드
		request.setAttribute("categoryList", CommonDAO.selCodeList(I_M));
		
		request.setAttribute(Const.TITLE, "가게 등록");
		request.setAttribute(Const.VIEW, "restaurant/restReg");
		return ViewRef.TEMP_MENU_TEMP;
	}
	
	public String ajaxGetList(HttpServletRequest request) {
		return "ajax:" + service.getRestList(); 
	}
	
	public String restRegProc(HttpServletRequest request) {
		
		String nm = request.getParameter("nm");
		String strLat = request.getParameter("lat");
		double lat = Double.parseDouble(strLat);
		String strLng = request.getParameter("lng");
		double lng = Double.parseDouble(strLng);
		String addr = request.getParameter("addr");
		String strCd_category = request.getParameter("cd_category");
		int cd_category = Integer.parseInt(strCd_category);
		
		int i_user = SecurityUtils.getLoginUser(request).getI_user();
		
		RestaurantVO param = new RestaurantVO();
		param.setI_user(i_user);
		param.setNm(nm);
		param.setLat(lat);
		param.setLng(lng);
		param.setAddr(addr);
		param.setCd_category(cd_category);
		
		int result = service.insRest(param);
		
		return "redirect:/restaurant/restMap";
		
	}
	public String restDetail(HttpServletRequest request) {
		int i_rest = CommonUtils.getIntParameter("i_rest", request);
		
		RestaurantVO param = new RestaurantVO();
		param.setI_rest(i_rest);
		
		request.setAttribute("css", new String[]{"restaurant"});
		request.setAttribute("recommendMenuList", service.getRecommendMenuList(i_rest));
		request.setAttribute("data", service.getRest(param));
		
		request.setAttribute(Const.TITLE, "가게 정보");
		request.setAttribute(Const.VIEW, "restaurant/restDetail");
		return ViewRef.TEMP_MENU_TEMP;
	}
	
	public String addRecMenusProc(HttpServletRequest request) {
		int i_rest = service.addRecMenus(request);
		return "redirect:/restaurant/restDetail?i_rest=" + i_rest;
	}
	
	public String ajaxDelRecMenu(HttpServletRequest request) {
		int i_rest = CommonUtils.getIntParameter("i_rest", request);
		int seq = CommonUtils.getIntParameter("seq", request);
		String fileNm = request.getParameter("fileNm");
		int i_user = SecurityUtils.getLoginUserPk(request);
		
		RestaurantRecommendMenuVO param = new RestaurantRecommendMenuVO();
		param.setI_rest(i_rest);
		param.setSeq(seq);
		param.setI_user(i_user);
		
		int result = service.delRecMenu(param);
		String savePath = request.getServletContext().getRealPath("/res/img/restaurant/"+ i_rest + "/" + fileNm);
		File file = new File(savePath); // 파일 관련 작업할때 
		
		//파일에 있는 이미지까지 삭제 
		if(file.exists()) { //exists 경로지정했을때 파일 존재 여부
			if(file.delete()) {
				System.out.println("삭제성공");
			} else {
				System.out.println("삭제실패");
			}
		} else {
			System.out.println("파일존재 x");
		}
		return "ajax:" + result;
	}
}
