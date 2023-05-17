package ntukhpi.semit.dde.studentsdata.servlets.students;

import ntukhpi.semit.dde.studentsdata.doaccess.DAOObjects;
import ntukhpi.semit.dde.studentsdata.entity.Student;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/edit_student")
public class StudentEditServlet extends HttpServlet {

    private Student myStud;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("StudentEditServlet#doGet");
        String idStr = request.getParameter("id");
        Long id = Long.parseLong(idStr);
        System.out.println("id-student to find - "+id);
        Student studToEdit = DAOObjects.daoStudent.findById(id);
        System.out.println(studToEdit);
        myStud = studToEdit;
        request.setAttribute("error",null);
        request.setAttribute("student",studToEdit);
        request.setAttribute("id",id);
        String path = "/views/students/student.jsp";
        ServletContext servletContext = getServletContext();
        RequestDispatcher requestDispatcher = servletContext.getRequestDispatcher(path);
        requestDispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        System.out.println("StudentEditServlet#doPost");
//        //Take parameters
//
//
//        // Create object
//       Student stud
//
//        //Check presence this Student in database
//        if () {
//            request.setAttribute("error","Trying to input Student with sname, fname, pname and birsday stored in DB!!!");
//            request.setAttribute("student", stud);
//            request.setAttribute("id",0);
//            String path = "/views/students/phone.jsp";
//            ServletContext servletContext = getServletContext();
//            RequestDispatcher requestDispatcher = servletContext.getRequestDispatcher(path);
//            requestDispatcher.forward(request, response);
//        } else {
//            //Call Insert
//            boolean insertRes = DAOStudentsHBN.insert(stud);
//            if (insertRes) {
//                //back to listStudents
//                String path = request.getContextPath() + "/students?id_group="+student.getAacademicGroup().getId();
//                response.sendRedirect(path);
//            } else {
//                request.setAttribute("error", "Check data! Insert SQL mistake!!!");
//                request.setAttribute("student", stud);
//                request.setAttribute("id",0);
//                String path = "/views/students/groups.jsp";
//                ServletContext servletContext = getServletContext();
//                RequestDispatcher requestDispatcher = servletContext.getRequestDispatcher(path);
//                requestDispatcher.forward(request, response);
//            }
//        }
    }
}
