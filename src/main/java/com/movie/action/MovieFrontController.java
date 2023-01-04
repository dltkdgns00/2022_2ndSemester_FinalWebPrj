package com.movie.action;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.common.action.Action;
import com.common.action.ActionForward;

@WebServlet("/MovieFrontController.mo")
public class MovieFrontController extends HttpServlet {
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String uri = request.getRequestURI();
		String context = request.getContextPath();
		String command = uri.substring(context.length());
		// System.out.println(command); //콘솔 창에 /memberLogin.me 등이 출력된다.

		Action action = null;
		ActionForward forward = null;

		if (command.equals("/movieList.mo")) {
			action = new MovieListAction();
			forward = action.execute(request, response);
		} else if (command.equals("/movieWrite.mo")) {
			forward = new ActionForward();
			forward.setPath("movie/movie_write.jsp");
			forward.setRedirect(false);
			// 가져갈게 있으면 true(forward) 없으면 false(sendRedirect)
			// URL 바뀌면 true(boardWrite.bo로 바뀌어서 안됨) 안바뀌면 false
		}
//		} else if (command.equals("/movieAddAction.mo")) {
//			action = new MovieAddAction();
//			forward = action.execute(request, response);
//		} else if (command.equals("/movieDetailAction.mo")) {
//			action = new MovieDetailAction();
//			forward = action.execute(request, response);
//		} else if (command.equals("/movieDeleteAction.mo")) {
//			action = new MovieDeleteAction();
//			forward = action.execute(request, response);
//		} else if (command.equals("/movieModifyView.mo")) {
//			action = new MovieModifyView();
//			forward = action.execute(request, response);
//		} else if (command.equals("/movieModifyAction.mo")) {
//			action = new MovieModifyAction();
//			forward = action.execute(request, response);
//		}

		if (forward != null) {
			if (forward.isRedirect()) { // true : sendRedirect() 전환
				response.sendRedirect(forward.getPath());
			} else { // false : forward() 전환
				RequestDispatcher rd = request.getRequestDispatcher(forward.getPath());
				rd.forward(request, response);
			}
		}
	}
}