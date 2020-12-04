package servlet;

import dao.ManageDao;
import entity.Manager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String path = "login.jsp";
		String Hukaixuan_managerid= request.getParameter("hukaixuan_managerid");
		String Hukaixuan_password = request.getParameter("hukaixuan_password");
		ManageDao manageDao = new ManageDao();
		Manager manager = new Manager();
		manager.setHukaixuan_managerid(Hukaixuan_managerid);
		manager.setHukaixuan_password(Hukaixuan_password);
		if (manageDao.findAll(manager)) {
			path = "welcome1.jsp";
		} else {
			response.setHeader("refresh", "0;URL=login.jsp");
		}
		request.getRequestDispatcher(path).forward(request, response);



	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		super.doPost(req, resp);
	}


}
