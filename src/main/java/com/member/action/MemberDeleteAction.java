package com.member.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.common.action.Action;
import com.common.action.ActionForward;
import com.member.controller.MemberDAO;

public class MemberDeleteAction implements Action {
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String id = (String) session.getAttribute("id");
		
		ActionForward forward = new ActionForward();
		
		if(id == null) {	//ID체크 후 없으면 로그인 화면으로
			forward.setPath("memberLogin.me");
			forward.setRedirect(true);
			return forward;
		} else if(!id.equals("admin")) {	//어드민이 아니면 게시글 목록 화면으로
			response.setContentType("text/html; charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println("<script>alert('관리자가 아닙니다!');");
			out.println("location.href='boardList.bo';</script>");
			return null;
		} else {	//관리자 계정이라면
			/*
			//제대로 접근되는지 확인
			response.setContentType("text/html; charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println("<script>alert('관리자로 로그인 하셨습니다!');</script>");
			*/
			request.setCharacterEncoding("utf-8");
			String member_id = request.getParameter("member_id");
			MemberDAO dao = new MemberDAO(); 
			dao.deleteMember(member_id);
			forward.setPath("memberListAction.me");
			forward.setRedirect(true);
			return forward;
		}
	}
}
