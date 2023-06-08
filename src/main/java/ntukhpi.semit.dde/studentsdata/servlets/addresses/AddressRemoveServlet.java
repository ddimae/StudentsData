package ntukhpi.semit.dde.studentsdata.servlets.addresses;

import ntukhpi.semit.dde.studentsdata.doaccess.DAOObjects;
import ntukhpi.semit.dde.studentsdata.entity.Address;
import ntukhpi.semit.dde.studentsdata.entity.Person;
import ntukhpi.semit.dde.studentsdata.utils.ContactMessages;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/delete_address")
public class AddressRemoveServlet extends HttpServlet {

    Address myAddress = null;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("AddressRemoveServlet#doGet");
        Long idOwner = Long.parseLong(request.getParameter("id_owner"));
        Person owner = DAOObjects.daoPerson.findById(idOwner);
        System.out.println(owner.getClass() + ": " + owner);
        Long idAddress = Long.parseLong(request.getParameter("id_address"));
        System.out.println("Address Id = "+idAddress);
        Address addressToDel = DAOObjects.daoAddress.findById(idAddress);
        myAddress = addressToDel;
        request.setAttribute("error", null);
        System.out.println("addressToDel:" + addressToDel);
        request.setAttribute("addr", addressToDel);
        request.setAttribute("owner", owner);
        String path = "/views/addresses/address_delete.jsp";
        ServletContext servletContext = getServletContext();
        RequestDispatcher requestDispatcher = servletContext.getRequestDispatcher(path);
        requestDispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {
        System.out.println("AddressRemoveServlet#doPost");
        String idAddressStr = request.getParameter("id_address");
        Long idAddressDel = Long.parseLong(idAddressStr);
        String idOwnerStr = request.getParameter("id_owner");
        Long idOwner = Long.parseLong(idOwnerStr);
        //Call Update
        boolean deleteRes = DAOObjects.daoAddress.delete(idAddressDel);
        if (deleteRes) {
            //back to list of person's addresses
            System.out.println("Address вилучений!!!");
            String path = request.getContextPath() + "/addresses?id_owner="+idOwner+ "&msgcode=12";
            response.sendRedirect(path);
        } else {
            System.out.println("Помилка вилучення! Delete SQL mistake!!!");
            request.setAttribute("error", ContactMessages.MESSAGE13.getText());
            request.setAttribute("addr", myAddress);
            request.setAttribute("owner", idAddressStr); //!!!!
            String path = "/views/addresses/address_delete.jsp";
            ServletContext servletContext = getServletContext();
            RequestDispatcher requestDispatcher = servletContext.getRequestDispatcher(path);
            requestDispatcher.forward(request, response);
        }
    }
}
