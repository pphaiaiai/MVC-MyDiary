package sit.int202.mydiary.servlets;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import sit.int202.mydiary.entities.Owner;
import sit.int202.mydiary.entities.User;
import sit.int202.mydiary.repositories.OwnerRepository;
import sit.int202.mydiary.repositories.UserRepository;

import java.io.IOException;
import java.security.MessageDigest;
import java.util.Date;

@WebServlet(name = "AuthenServlet", value = "/authen")
public class AuthenServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action != null && action.equals("logout")) {
            HttpSession session = request.getSession(false);

            if (session != null) {
                Owner owner = (Owner) session.getAttribute("owner");
                if(owner != null){
                    OwnerRepository ownerRepo = new OwnerRepository();
                    Date lastLogin = new Date();
                    owner.setLastLogin(lastLogin);
                    ownerRepo.update(owner);
                    session.invalidate();
                }
            }
        }
        response.sendRedirect("index.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("userName");
        String password = request.getParameter("password");

        if (username != null && username.trim().length() > 0 && password != null && password.trim().length() > 0) {
            OwnerRepository ownerRepo = new OwnerRepository();
            Owner owner = ownerRepo.find(username);
            if (owner != null) {
                if (sha256(password).equals(owner.getPassword())) {
                    HttpSession session = request.getSession(true);
                    if (session.getAttribute("owner") == null) {
                        session.setAttribute("owner", owner);
                    }
                    response.sendRedirect("list-diaries");
                    return;
                }
            }
        }
        response.sendRedirect("index.jsp");
    }

    private String sha256(final String base) {
        try {
            final MessageDigest digest = MessageDigest.getInstance("SHA-256");
            final byte[] hash = digest.digest(base.getBytes("UTF-8"));
            final StringBuilder hexString = new StringBuilder();
            for (int i = 0; i < hash.length; i++) {
                final String hex = Integer.toHexString(0xff & hash[i]);
                if (hex.length() == 1)
                    hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
