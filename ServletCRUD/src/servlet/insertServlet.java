package servlet;

import dao.UserDao;
import entity.User;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/insertServlet")
public class insertServlet extends HttpServlet {
    Logger logger = Logger.getLogger(insertServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String tel = req.getParameter("tel");
        User user = new User();
        // 转值，中文需要转换为utf-8
        user.setHukaixuan_name(new String(name.getBytes("ISO-8859-1"), "UTF-8"));
        user.setHukaixuan_tel(new String(tel.getBytes("ISO-8859-1"), "UTF-8"));
        UserDao userDao = new UserDao();
        userDao.addUser(user);

        logger.info("增加了用户"+user);
        // System.out.println("增加了用户"+user);

        req.getRequestDispatcher("/selectServlet").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {



    }
}
