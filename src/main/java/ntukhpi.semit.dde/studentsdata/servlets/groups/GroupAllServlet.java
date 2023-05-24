package ntukhpi.semit.dde.studentsdata.servlets.groups;

import ntukhpi.semit.dde.studentsdata.doaccess.DAOObjects;
import ntukhpi.semit.dde.studentsdata.doaccess.DAOStudentsHBN;
import ntukhpi.semit.dde.studentsdata.entity.AcademicGroup;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/groups")
public class GroupAllServlet extends HttpServlet {

    public static List<AcademicGroup> groups = new ArrayList<>();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("GroupAllServlet#doGet");
        groups = DAOObjects.daoAcademicGroup.getAllList();
        System.out.println(groups);
        request.setAttribute("groups", groups);
        String path = "/views/groups/groups.jsp";
        ServletContext servletContext = getServletContext();
        RequestDispatcher requestDispatcher = servletContext.getRequestDispatcher(path);
        requestDispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

}
