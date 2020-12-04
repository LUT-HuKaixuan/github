package servlet;

import dao.UserDao;
import entity.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/toUpdateServlet")
public class toUpdateServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name1 = req.getParameter("name1");
        String tel1 = req.getParameter("tel1");
        String id1 = req.getParameter("id1");
        User user = new User();
        user.setHukaixuan_id(Integer.valueOf(id1));
        user.setHukaixuan_name(name1);
        user.setHukaixuan_tel(tel1);

        UserDao userDao = new UserDao();
        userDao.UpdateUser(user);
        req.getRequestDispatcher("/selectServlet").forward(req, resp);
    }
}
