package org.example.servlets;

import org.example.accounts.AccountService;
import org.example.accounts.UserProfile;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;

@WebServlet("/reg")
public class UsersServlet extends HttpServlet {
    private final String fileManagerPath = "C:\\Users\\Lutin\\fileManager\\";
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = (String)req.getSession().getAttribute("login");
        if (login != null) {
            String encodePath = URLEncoder.encode(fileManagerPath + login, "UTF-8");
            resp.sendRedirect(req.getContextPath() + "/files?path=" + encodePath);
            return;
        }
        RequestDispatcher view = req.getRequestDispatcher("reg.jsp");
        view.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String login = req.getParameter("login");
        String pass = req.getParameter("pass");

        if (email.isEmpty()|| login.isEmpty() || pass.isEmpty()) {
            resp.setContentType("text/html;charset=utf-8");
            resp.getWriter().println("Заполните все поля");
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        if (AccountService.getUserByLogin(login) != null) {
            resp.setContentType("text/html;charset=utf-8");
            resp.getWriter().println("Такой пользователь уже существует");
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        UserProfile user = new UserProfile(login, pass, email);
        AccountService.AddNewUser(user);

        req.getSession().setAttribute("login", login);
        req.getSession().setAttribute("pass", pass);

        File directory = new File(fileManagerPath + login);
        if (!directory.mkdir()) {
            resp.setContentType("text/html;charset=utf-8");
            resp.getWriter().println("Не удалось создать папку пользователя");
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return;
        }

        String encodePath = URLEncoder.encode(fileManagerPath + login, "UTF-8");
        resp.sendRedirect(req.getContextPath() + "/files?path=" + encodePath);
    }
}
