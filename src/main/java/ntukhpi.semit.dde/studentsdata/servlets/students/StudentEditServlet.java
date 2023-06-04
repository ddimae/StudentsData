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
import java.time.LocalDate;

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
        System.out.println("StudentEditServlet#doPost");

        //Take parameters
        String idStr = request.getParameter("id");
        String idGroupStr = request.getParameter("id_group");
        String birthdayStr = request.getParameter("birthday");
        String contractStr = new String (request.getParameter("contract").getBytes("ISO-8859-1"), "utf-8");
        String scholarshipStr = new String (request.getParameter("scholarship").getBytes("ISO-8859-1"), "utf-8");

        //Transform data
        Long id = Long.parseLong(idStr);
        Long idGroup = Long.parseLong(idGroupStr);
        LocalDate birthday = LocalDate.parse(birthdayStr);
        boolean contract = "Контракт".equals(contractStr);
        boolean scholarship = "Так".equals(scholarshipStr);

        // Create object
        Student studForUpdate = new Student("","","",birthday,contract,scholarship);
        boolean updateOK = DAOObjects.daoStudent.update(id,studForUpdate);

       //Check presence this Student in database
        if (!updateOK) {
            request.setAttribute("error","Trouble updating information about student!");
            request.setAttribute("student", studForUpdate);
            request.setAttribute("id",0);
            String path = "/views/students/student.jsp";
            ServletContext servletContext = getServletContext();
            RequestDispatcher requestDispatcher = servletContext.getRequestDispatcher(path);
            requestDispatcher.forward(request, response);
        } else {
                String path = request.getContextPath() + "/students?id_group="+idGroup;
                response.sendRedirect(path);
            }
        }
    }
