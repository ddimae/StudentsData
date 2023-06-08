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
import java.util.List;
import java.util.Map;

@WebServlet("/addresses")
public class AddressPersonAllServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("AddressPersonAllServlet#doGet");
        Long id_owner = Long.parseLong(request.getParameter("id_owner"));
        Person personInDB = DAOObjects.daoPerson.findById(id_owner);
        System.out.println(personInDB);
        request.setAttribute("owner", personInDB);
        Map<Address,Boolean> addresses = personInDB.getAddresses();
        System.out.println(addresses);
        request.setAttribute("addresses", addresses);
        request.setAttribute("ref_to_return", DAOObjects.daoPerson.linkToReturn(personInDB));
        String messageCode = request.getParameter("msgcode");
        if (messageCode != null) {
            String message = ContactMessages.values()[Integer.parseInt(messageCode)].getText();
            System.out.println("message="+message);
            request.setAttribute("message", message);
        } else {
            request.setAttribute("message", ContactMessages.EMPTY.getText());
        }
        String path = "/views/addresses/addresses_person.jsp";
        ServletContext servletContext = getServletContext();
        RequestDispatcher requestDispatcher = servletContext.getRequestDispatcher(path);
        requestDispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        System.out.println("AddressPersonAllServlet#doPost ===> call doGet");
        doGet(request, response);
    }

}
