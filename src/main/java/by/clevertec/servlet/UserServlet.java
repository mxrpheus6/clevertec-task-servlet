package by.clevertec.servlet;

import by.clevertec.model.User;
import by.clevertec.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/users/*")
public class UserServlet extends HttpServlet {
    private final UserService userService = new UserService();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public UserServlet() {
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();

        String pathInfo = req.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            List<User> users = userService.findAll();
            String json = objectMapper.writeValueAsString(users);
            out.print(json);
        } else if (pathInfo.equals("/get")) {
            String idParam = req.getParameter("id");
            if (idParam != null) {
                Long id = Long.parseLong(idParam);
                User user = userService.findById(id);
                if (user != null) {
                    String json = objectMapper.writeValueAsString(user);
                    out.print(json);
                } else {
                    resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    out.print("{\"error\": \"User not found\"}");
                }
            } else {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.print("{\"error\": \"Missing user ID\"}");
            }
        }
        out.flush();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();

        String pathInfo = req.getPathInfo();
        if (pathInfo.equals("/create")) {
            try {
                User user = objectMapper.readValue(req.getInputStream(), User.class);
                User createdUser = userService.create(user);
                String json = objectMapper.writeValueAsString(createdUser);
                resp.setStatus(HttpServletResponse.SC_CREATED);
                out.print(json);
            } catch (Exception e) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.print("{\"error\": \"Invalid user data\"}");
            }
        } else {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            out.print("{\"error\": \"Invalid path for POST\"}");
        }
        out.flush();
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();

        String pathInfo = req.getPathInfo();
        if (pathInfo.equals("/update")) {
            String idParam = req.getParameter("id");
            if (idParam != null) {
                try {
                    Long id = Long.parseLong(idParam);
                    User user = objectMapper.readValue(req.getInputStream(), User.class);
                    user.setId(id);
                    User updatedUser = userService.update(user);
                    if (updatedUser != null) {
                        String json = objectMapper.writeValueAsString(updatedUser);
                        out.print(json);
                    } else {
                        resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                        out.print("{\"error\": \"User not found\"}");
                    }
                } catch (Exception e) {
                    resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    out.print("{\"error\": \"Invalid user data\"}");
                }
            } else {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.print("{\"error\": \"Missing user ID\"}");
            }
        } else {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            out.print("{\"error\": \"Invalid path for PUT\"}");
        }
        out.flush();
    }
}
