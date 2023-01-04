<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.movie.controller.MovieDTO"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.member.controller.MemberDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String id = (String) session.getAttribute("id"); //JSP는 session이 내장 객체라 바로 사용 가능하다.
MemberDAO dao = new MemberDAO();
String member_pw = dao.getMember_pw(id);
Integer nowPage = (Integer)request.getAttribute("page");
Integer maxPage = (Integer)request.getAttribute("maxPage");
Integer startPage = (Integer) request.getAttribute("startPage");
Integer endPage = (Integer) request.getAttribute("endPage");
Integer listCount = (Integer) request.getAttribute("listCount");
ArrayList<MovieDTO> list = new ArrayList<>();
list = (ArrayList<MovieDTO>)request.getAttribute("list");

%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>MLW Movies</title>
<script type="text/javascript">
function fnModify(id, member_pw) {
	//alert("id : " + id);
	var pw = prompt("비밀번호를 입력하세요","");
	//alert("member_pw : " + member_pw + "\npw : " + pw);
	//member_pw : dto에 저장되어있던 비밀번호
	//pw : 방금 사용자로부터 입력받은 비밀번호
	if(member_pw == pw) { //맞는 비밀번호를 입력하면 URL이 바뀐다
		//alert("비밀번호 일치합니다.")
		location.href = "memberDetailAction.me?member_id=" + id;
	} else {
		alert("비밀번호가 일치하지 않습니다.");
	}
}
</script>
<style type="text/css">
	body {
		background-color : #202124;
		color : white;
	}
	
	a {
		color : white;
	}
</style>
</head>
<body>
<div align="right">
<img src="/FinalTestWeb/image/MLWMovies.png" align="left" height="50">
<br>
<div>
<%=id %>님 환영합니다!
<%if(id != null && id.equals("admin")) { %>
	<input type="button" value="회원 관리" onclick="location.href='memberListAction.me'" />
	<%} %>
<input type="button" value="로그아웃" onclick="location.href='memberLogout.me'"/>
<%if(id != null && !id.equals("admin")) { %>
	<input type="button" value="회원 정보 수정" onclick="fnModify('<%=id%>', '<%=member_pw %>')"/>
	<%} %>
</div>
</div>
<br>
<div align="center">
	<h1>상영중인 영화</h1>
	<table border="1">
	<tr align="center">
	<th width="200">Avatar</th>
	<th width="200">Pirates of the Caribbean: At World's End</th>
	<th width="200">Spectre</th>
	<th width="200">The Dark Knight Rises</th>
	<th width="200">John Carter</th>
	</tr>
	<tr>
	<th width="200"><img src="/FinalTestWeb/image/Avatar.jpg" width="200"></th>
	<th width="200"><img src="/FinalTestWeb/image/PoC.jpg" width="200"></th>
	<th width="200"><img src="/FinalTestWeb/image/Spectre.jpg" width="200"></th>
	<th width="200"><img src="/FinalTestWeb/image/TDKR.jpg" width="200"></th>
	<th width="200"><img src="/FinalTestWeb/image/JohnCarter.jpg" width="200"></th>
	</tr>
	</table>
</div>
<div align="center">
	<h3>[영화 목록]</h3>
	<table border= "1">
		<tr align="center">
			<th>번호</th>
			<th width="500">제목</th>
		</tr>
		<%for(int i = 0; i < list.size(); i++) { 
			MovieDTO dto = list.get(i);%>
			<tr align="center">
				<td><%=dto.getMovie_id() + 1 %></td>
				<td><a href = "movie/popup.jsp?movie_name=<%=dto.getMovie_name() %>" onclick="window. open(this.href, '_blank', 'width=500, height=650, tootbars=no, scrottbars=yes'); return false;" target = "_blank"><%=dto.getMovie_name() %></a></td>
			</tr>
		<%} %>
	</table>
</div>
</body>
</html>