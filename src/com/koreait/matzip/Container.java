package com.koreait.matzip;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/")
public class Container extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private HandlerMapper mapper;

	public Container() {
		mapper = new HandlerMapper();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		proc(request, response);
	}
       
	

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		proc(request, response);
	}
	
	private void proc(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	
		String routerCheckResult = LoginChkInterceptor.routerChk(request);
		
		if(routerCheckResult != null) {
			response.sendRedirect(routerCheckResult);
			return;
		}
		
		String temp = mapper.nav(request); // 보통 템플릿 파일명
		
		if(temp.indexOf(":") >= 0 ) {
			String prefix = temp.substring(0, temp.indexOf(":"));
			String value = temp.substring(temp.indexOf(":")+1);
			
			System.out.println("prefix : " + prefix);
			System.out.println("value : " + value);
			
			if("redirect".equals(prefix)) {//0이 시작index temp.indexof가 끝index		
				response.sendRedirect(value);
				return;
			} else if("ajax".equals(prefix)) {
				response.setCharacterEncoding("UTF-8");
				response.setContentType("application/json"); //응답시 json타입이라고 알려줌
				PrintWriter out = response.getWriter();	//서블릿에서 바로 응답하기 위한거,,,,,? 
//				System.out.println("value : " + value);
				out.print(value);
				return;
			}
		}
		switch(temp) {
		case "405":
			temp = "/WEB-INF/view/error.jsp";
			break;
		case "404":
			temp = "/WEB-INF/view/notFound.jsp";
			break;
		}
		request.getRequestDispatcher(temp).forward(request, response);
	}
}

//protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//	System.out.println("uri : " + request.getRequestURI());
//	String[] uriArr = request.getRequestURI().split("/"); //split 자르기 , 결과값이 /res/js/test.js
//	
//	for(int i=0; i<uriArr.length; i++) {
//		System.out.println("uriArr[" + i + "] : " + uriArr[i]); // 0-> 빈칸, 1-> res , 2-> js, 3-> test.js
//	}
//	
//	//response.sendRedirect(request.getRequestURI());
//	if(uriArr.length > 1 ) {
//		request.getRequestDispatcher(request.getRequestURI()).forward(request, response);
//	}
//}
