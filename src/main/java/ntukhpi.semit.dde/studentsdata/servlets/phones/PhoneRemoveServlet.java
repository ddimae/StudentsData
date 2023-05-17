package ntukhpi.semit.dde.studentsdata.servlets.phones;

import ntukhpi.semit.dde.studentsdata.doaccess.DAOObjects;
import ntukhpi.semit.dde.studentsdata.entity.Person;
import ntukhpi.semit.dde.studentsdata.entity.PhoneNumber;
import ntukhpi.semit.dde.studentsdata.utils.ContactMessages;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/delete_phone")
public class PhoneRemoveServlet extends HttpServlet {

        PhoneNumber myPhone = null;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("PhoneRemoveServlet#doGet");
        Long id_owner = Long.parseLong(request.getParameter("id_owner"));
        Person owner = DAOObjects.daoPerson.findById(id_owner);
        System.out.println(owner.getClass() + ": " + owner);
        Long id_phone = Long.parseLong(request.getParameter("id_phone"));
        PhoneNumber phoneToDel = DAOObjects.daoPhone.findById(id_phone);
        myPhone = phoneToDel;
        request.setAttribute("error", null);
        System.out.println("phoneToDel:" + phoneToDel);
        request.setAttribute("phone", phoneToDel);
        request.setAttribute("owner", owner);
        String path = "/views/phones/phone_delete.jsp";
        ServletContext servletContext = getServletContext();
        RequestDispatcher requestDispatcher = servletContext.getRequestDispatcher(path);
        requestDispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {
        System.out.println("PhoneRemoveServlet#doPost");
        String idPhoneStr = request.getParameter("id_phone");
        Long idPhoneDel = Long.parseLong(idPhoneStr);
        String idOwnerStr = request.getParameter("id_owner");
        Long idOwner = Long.parseLong(idOwnerStr);
        //Call Update
        boolean deleteRes = DAOObjects.daoPhone.delete(idPhoneDel);
        if (deleteRes) {
            //back to list of person's phones
            System.out.println("Телефон вилучений!!!");
            String path = request.getContextPath() + "/phones?id_owner="+idOwner+ "&msgcode=12";
            response.sendRedirect(path);
        } else {
            System.out.println("Помилка вилучення! Delete SQL mistake!!!");
            request.setAttribute("error", ContactMessages.MESSAGE13.getText());
            request.setAttribute("phone", myPhone);
            request.setAttribute("owner", idPhoneStr); //!!!!
            String path = "/views/phones/phone_delete.jsp";
            ServletContext servletContext = getServletContext();
            RequestDispatcher requestDispatcher = servletContext.getRequestDispatcher(path);
            requestDispatcher.forward(request, response);
        }
    }
}
