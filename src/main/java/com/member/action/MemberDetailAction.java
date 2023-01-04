package com.member.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.common.action.Action;
import com.common.action.ActionForward;
import com.member.controller.MemberDAO;
import com.member.controller.MemberDTO;

public class MemberDetailAction implements Action {
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String member_id = request.getParameter("member_id");
		
		MemberDAO dao = new MemberDAO();
		MemberDTO dto = dao.getDetailMember(member_id);
		request.setAttribute("dto", dto);
		
		ActionForward forward = new ActionForward();
		forward.setPath("member/member_detailForm.jsp");
		forward.setRedirect(false);
		return forward;
	}
}
