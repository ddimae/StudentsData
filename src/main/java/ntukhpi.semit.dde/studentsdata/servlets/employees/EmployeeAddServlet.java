package ntukhpi.semit.dde.studentsdata.servlets.employees;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/addEmployee")
public class EmployeeAddServlet extends HttpServlet {
//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        System.out.println("StudentEditServlet#doGet");
//        request.setAttribute("error",null);
//        Employee emplData = new Employee(-1l,null,true,-1,-1.0);
//        request.setAttribute("employee",emplData);
//        request.setAttribute("id",-1);
//        String path = "/views/employees/group_upload.jsp";
//        ServletContext servletContext = getServletContext();
//        RequestDispatcher requestDispatcher = servletContext.getRequestDispatcher(path);
//        requestDispatcher.forward(request, response);
//    }
//
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        System.out.println("StudentEditServlet#doPost");
//        //Take parameters
//        String nameStr = request.getParameter("name");
//        String ageStr = request.getParameter("age");
//        String polStr = request.getParameter("pol");
//        String salaryStr = request.getParameter("salary");
//
//        // Create object
//        int age = Integer.parseInt(ageStr);
//        double salary = Double.parseDouble(salaryStr);
//        boolean pol = polStr.equals("male") ? true : false;
//        Employee empl = new Employee(-1l, nameStr, pol, age, salary);
//
//        //Check presence this Employee in database
//        if (EmployeeAllServlet.mylist.contains(empl)) {
//            request.setAttribute("error","Trying to input Employee with name stored in DB!!!");
//            request.setAttribute("employee", empl);
//            request.setAttribute("id",-1);
//            String path = "/views/employees/group_upload.jsp";
//            ServletContext servletContext = getServletContext();
//            RequestDispatcher requestDispatcher = servletContext.getRequestDispatcher(path);
//            requestDispatcher.forward(request, response);
//        } else {
//            //Call Insert
//            boolean insertRes = DAOStudentsHBN.insert(empl);
//            if (insertRes) {
//                //back to listEmployees
//                String path = request.getContextPath() + "/employees";
//                response.sendRedirect(path);
//            } else {
//                request.setAttribute("error", "Check data! Insert SQL mistake!!!");
//                request.setAttribute("employee", empl);
//                request.setAttribute("id",-1);
//                String path = "/views/employees/group_upload.jsp";
//                ServletContext servletContext = getServletContext();
//                RequestDispatcher requestDispatcher = servletContext.getRequestDispatcher(path);
//                requestDispatcher.forward(request, response);
//            }
//        }
//    }
}
