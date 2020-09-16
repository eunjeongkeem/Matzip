<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>${title }</title>
<link href="https://fonts.googleapis.com/icon?family=Material+Icons"rel="stylesheet">
<link rel="stylesheet" type="text/css" href="/res/css/common.css">
<c:forEach items="${css}" var="item">
	<link rel="stylesheet" type="text/css" href="/res/css/${item}.css">
</c:forEach>
</head>
<body>
	<div id="container">
		<header>
			<div id="headerLeft">
				<div class="containerPImg">
					<c:choose>
						<c:when test="${loginUser.profile_img != null}">
							<img clss= "pImg" src="/res/img/user/${loginUser.i_user}/${loginUser.profile_img}" alt="사용자지정 프로필">
						</c:when>
						<c:otherwise>
							<img class="pImg" src="/res/img/default_profile.jpg" alt="기본프로필">
						</c:otherwise>
					</c:choose>	
				</div>
					<span class="nm">${loginUser.nm}</span>님 welcome~
					<div class="logtout_a"><a href="/user/logout">로그아웃</a></div>
			</div>
			<div id="headerRight">
				<a class="ml15" href="/restaurant/restMap">지도</a>
				<a class="ml15" href="/restaurant/restReg">등록</a>
				<a class="ml15" href="/user/restFavorite">찜하기</a>
			</div>
			
		</header>
		<section>
			<jsp:include page="/WEB-INF/view/${view}.jsp"></jsp:include>
		</section>
		<footer>
			<span>회사 정보</span>
		</footer>
	</div>
</body>
</html>