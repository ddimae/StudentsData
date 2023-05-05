package ntukhpi.semit.dde.studentsdata.servlets.employees;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/deleteEmployee")
public class EmployeeRemoveServlet extends HttpServlet {
//
//    private Employee myEmpl;
//
//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//
//        System.out.println("EmployeeRemoveServlet#doGet");
//        String idStr = request.getParameter("id");
//        Long idDel = Long.parseLong(idStr);
//        System.out.println("id = " + idDel);
//        Employee emplToDelete = DAOStudentsHBN.getEmployeeById(idDel);
//        System.out.println(emplToDelete);
//        request.setAttribute("employee", emplToDelete);
//        //Instance of object after updating can be changed
//        myEmpl = emplToDelete;
//        request.setAttribute("idDel", idDel);
//        request.setAttribute("error", null);
//        String path = "/views/employees/employeeDelete.jsp";
//        ServletContext servletContext = getServletContext();
//        RequestDispatcher requestDispatcher = servletContext.getRequestDispatcher(path);
//        requestDispatcher.forward(request, response);
//    }
//
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws
//            ServletException, IOException {
//        System.out.println("EmployeeRemoveServlet#doPost");
//        String idDelStr = request.getParameter("idDel");
//        Long idDel = Long.parseLong(idDelStr);
//        //Call Update
//        boolean deleteRes = DAOStudentsHBN.deleteByID(idDel);
//        if (deleteRes) {
//            //back to listEmployees
//            String path = request.getContextPath() + "/employees";
//            response.sendRedirect(path);
//        } else {
//            request.setAttribute("error", "Delete SQL mistake!!!");
//            request.setAttribute("employee", myEmpl);
//            request.setAttribute("idDel", idDel); //!!!!
//            String path = "/views/employees/employeeDelete.jsp";
//            ServletContext servletContext = getServletContext();
//            RequestDispatcher requestDispatcher = servletContext.getRequestDispatcher(path);
//            requestDispatcher.forward(request, response);
//        }
//    }
}
