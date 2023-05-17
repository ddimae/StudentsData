package ntukhpi.semit.dde.studentsdata.servlets.students;

import ntukhpi.semit.dde.studentsdata.doaccess.DAOObjects;
import ntukhpi.semit.dde.studentsdata.entity.AcademicGroup;
import ntukhpi.semit.dde.studentsdata.entity.Student;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/students")
public class StudentAllServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("StudentAllServlet#doGet");
        Long id_group = Long.parseLong(request.getParameter("id_group"));
        AcademicGroup groupInDB = DAOObjects.daoAcademicGroup.findById(id_group);
        System.out.println(groupInDB);
        request.setAttribute("group", groupInDB);
        List<Student> students = DAOObjects.daoStudent.getAllListByGroup(groupInDB);
        System.out.println(students);
        request.setAttribute("students", students);
        String path = "/views/students/students.jsp";
        ServletContext servletContext = getServletContext();
        RequestDispatcher requestDispatcher = servletContext.getRequestDispatcher(path);
        requestDispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        System.out.println("StudentAllServlet#doPost ===> call doGet");
        doGet(request, response);
    }

}
