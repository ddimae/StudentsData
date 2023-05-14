package ntukhpi.semit.dde.studentsdata.filters;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AuthorizationFilter implements Filter {

    public void init(FilterConfig config) throws ServletException {
    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
//        System.out.println("===== AuthorizationFilter#doFilter =====");
        HttpServletRequest req = (HttpServletRequest) request;
        HttpSession session = req.getSession(false);

        HttpServletResponse res = (HttpServletResponse) response;
        String uri = req.getRequestURI();
//        System.out.println("AuthorizationFilter#doFilter: URI " + uri);
//        System.out.println(session);
//        if (session != null) {
//            System.out.println("AuthorizationFilter#doFilter: session " + session.getId());
//        } else {
//            System.out.println("AuthorizationFilter#doFilter: session - no");
//        }
        //NEW 23.11.2022
        if (uri.endsWith("/") || uri.endsWith("login")) {
            if (session != null) {
                session.invalidate();
            }
//            System.out.println("AuthorizationFilter#doFilter: OK");
            // pass the request along the filter chain
            chain.doFilter(request, response);
        } else {
            if (session == null) {
//                System.err.println("AuthorizationFilter#doFilter: nonregistered access!!!");
                //request.setAttribute("errorDB","Not allowed do anything with db without login!!!");
                res.sendRedirect(req.getContextPath());
            } else {
                if (session.getAttribute("username") == null) {
//                    System.err.println("AuthorizationFilter#doFilter: nonregistered access2!!!");
                    //request.setAttribute("errorDB","Not allowed do anything with db without login!!!");
                    res.sendRedirect(req.getContextPath());
                } else {
//                    System.out.println("AuthorizationFilter#doFilter: OK");
                    // pass the request along the filter chain
                    chain.doFilter(request, response);
                }
            }
        }
    }
}
