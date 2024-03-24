package org.example.servlets;

import org.example.accounts.AccountService;
import org.example.accounts.UserProfile;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@WebServlet("/")
public class SessionsServlet extends HttpServlet {
    private final String fileManagerPath = "C:\\Users\\Lutin\\fileManager\\";
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = (String)req.getSession().getAttribute("login");
        if (login != null) {
            String encodePath = URLEncoder.encode(fileManagerPath + login, "UTF-8");
            resp.sendRedirect(req.getContextPath() + "/files?path=" + encodePath);
            return;
        }
        RequestDispatcher view = req.getRequestDispatcher("log.jsp");
        view.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String pass = req.getParameter("pass");

        if (login.isEmpty() || pass.isEmpty()) {
            resp.setContentType("text/html;charset=utf-8");
            resp.getWriter().println("Заполните все поля");
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        UserProfile user = AccountService.getUserByLogin(login);
        if (user == null) {
            resp.setContentType("text/html;charset=utf-8");
            resp.getWriter().println("Пользователя с таким логином не существует");
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        if (!user.getPass().equals(pass)) {
            resp.setContentType("text/html;charset=utf-8");
            resp.getWriter().println("Неверный пароль");
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        req.getSession().setAttribute("login", login);
        req.getSession().setAttribute("pass", pass);

        String encodePath = URLEncoder.encode(fileManagerPath + login, "UTF-8");
        resp.sendRedirect(req.getContextPath() + "/files?path=" + encodePath);
    }
}
