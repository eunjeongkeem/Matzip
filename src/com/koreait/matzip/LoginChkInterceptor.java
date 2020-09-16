package com.koreait.matzip;

import javax.servlet.http.HttpServletRequest;

public class LoginChkInterceptor { //잘못된 경로로 갔을때 잡아주기 위함
	//null이 리턴되면 아무일 없음
	//문자열이 리턴되면 그 문자열로 sendRedirect함!! (주소값이동)
	public static String routerChk(HttpServletRequest request) {
		//로그인 되어있으면 login, join 접속 x
		//로그인에 따른 접속 가능여부 판단
		//(로그인이 안되어있으면 접속할 수 있는 주소만 여기서 체크, 나머지 전부 로그인이 되어 있어야함)
		String[] chkUserUriArr = {"login", "loginProc", "join", "joinProc", "ajaxIdChk"};
		
		boolean isLogout = SecurityUtils.isLogout(request); // 로그아웃 - true
		String[] targetUri = request.getRequestURI().split("/");
		if(targetUri.length < 3) { return null; } // 3보다 작으면 error 페이지
		
		if(isLogout) {//로그아웃 상태
			if(targetUri[1].equals(ViewRef.URI_USER)) { // user
				for(String uri : chkUserUriArr) {
					if(uri.equals(targetUri[2])) {
						return null;
					}
				}
			}
			return "/user/login";
		} else { //로그인 상태
			if(targetUri[1].equals(ViewRef.URI_USER)) { // user
				for(String uri : chkUserUriArr) {
					if(uri.equals(targetUri[2])) {
						return "/restaurant/restMap";
					}
				}		
			}	
		}
		return null;
	}
}
