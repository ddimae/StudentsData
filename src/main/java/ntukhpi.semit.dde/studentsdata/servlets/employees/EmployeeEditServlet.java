package ntukhpi.semit.dde.studentsdata.servlets.employees;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/editEmployee")
public class EmployeeEditServlet extends HttpServlet {

//    private Employee myEmpl;
//
//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        System.out.println("EmployeeEditServlet#doGet");
//        String idStr = request.getParameter("id");
//        Long id = Long.parseLong(idStr);
//        System.out.println("id = "+ id);
//        Employee emplToEdit = DAOStudentsHBN.getEmployeeById(id);
//        System.out.println(emplToEdit);
//        request.setAttribute("error",null);
//        request.setAttribute("employee",emplToEdit);
//        myEmpl = emplToEdit;
//        request.setAttribute("id",id);
//        String path = "/views/employees/employee.jsp";
//        ServletContext servletContext = getServletContext();
//        RequestDispatcher requestDispatcher = servletContext.getRequestDispatcher(path);
//        requestDispatcher.forward(request, response);
//    }
//
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        System.out.println("EmployeeEditServlet#doPost");
//        //Take parameters
//        String nameStr = request.getParameter("name");
//        String ageStr = request.getParameter("age");
//        String polStr = request.getParameter("pol");
//        String salaryStr = request.getParameter("salary");
//        String idStr = request.getParameter("id");
//        long id = Long.parseLong(idStr);
//
//        // Update object
//        System.out.println("EdEmployeeServlet#doPost");
//        int age = Integer.parseInt(ageStr);
//        double salary = Double.parseDouble(salaryStr);
//        boolean pol = polStr.equals("male") ? true : false;
//        Employee emplForUdate = new Employee(-1l, nameStr, pol, age, salary);
//        System.out.println("emplForUdate "+emplForUdate);
//
//
//        //Check presence this Employee in database
//        if ((!myEmpl.getName().equals(emplForUdate.getName()))&&EmployeeAllServlet.mylist.contains(emplForUdate)) {
//            request.setAttribute("error","Trying to input Employee with name stored in DB!!!");
//            request.setAttribute("employee", emplForUdate);
//            request.setAttribute("id",id); //!!!!
//            String path = "/views/employees/employee.jsp";
//            ServletContext servletContext = getServletContext();
//            RequestDispatcher requestDispatcher = servletContext.getRequestDispatcher(path);
//            requestDispatcher.forward(request, response);
//        } else {
//            //Call Update
//            boolean updateRes = DAOStudentsHBN.update(id, emplForUdate);
//            if (updateRes) {
//                //back to listEmployees
//                String path = request.getContextPath() + "/employees";
//                response.sendRedirect(path);
//            } else {
//                request.setAttribute("error", "Check data! Update SQL mistake!!!");
//                request.setAttribute("employee", emplForUdate);
//                request.setAttribute("id",id); //!!!!
//                String path = "/views/employees/employee.jsp";
//                ServletContext servletContext = getServletContext();
//                RequestDispatcher requestDispatcher = servletContext.getRequestDispatcher(path);
//                requestDispatcher.forward(request, response);
//            }
//        }
//    }
}