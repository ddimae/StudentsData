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

@WebServlet("/add_address")
public class AddressPersonAddServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("AddressPersonAddServlet#doGet");
        Long id_owner = Long.parseLong(request.getParameter("id_owner"));
        Person owner = DAOObjects.daoPerson.findById(id_owner);
        System.out.println(owner.getClass() + ": " + owner);
        request.setAttribute("error", "");
        Address addressToIns = new Address("", "", "", "");
        //Now id = null!!! We must set not null value!!!
        addressToIns.setId(0l);
        System.out.println("addressToIns:" + addressToIns);
        request.setAttribute("address", addressToIns);
        request.setAttribute("owner", owner);
        String path = "/views/emails/email.jsp";
        ServletContext servletContext = getServletContext();
        RequestDispatcher requestDispatcher = servletContext.getRequestDispatcher(path);
        requestDispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("AddressPersonAddServlet#doPost");
        //Get parameters
        String idAddressStr = request.getParameter("id_address");
//        System.out.println(idAddressStr);
        String idOwnerStr = request.getParameter("id_owner");
//        System.out.println(idOwnerStr);
        String country = request.getParameter("country");
//        System.out.println(country);
        String region = request.getParameter("region");
//        System.out.println(region);
        String city = request.getParameter("city");
//        System.out.println(city);
        String address = request.getParameter("address");
//        System.out.println(address);
        String currentStr = new String(request.getParameter("current").getBytes("ISO-8859-1"), "utf-8");
//        System.out.println(currentStr);

        // Create object
        Long idAddress = Long.parseLong(idAddressStr);
//        System.out.println(idAddress);
        Long idOwner = Long.parseLong(idOwnerStr);
//        System.out.println(idOwner);
        boolean current = "Поточний".equals(currentStr);
//        System.out.println("Поточний = " + currentStr);
        Person owner = DAOObjects.daoPerson.findById(idOwner);
        Address addressIns = new Address(country,region,city,address);
        addressIns.setId(idAddress);

//        if (email.isPrior() && !email.isActive()) {
//            System.out.println("\"Неактивний\" email не може бути \"Основним\"!!!");
//            request.setAttribute("error", ContactMessages.MESSAGE03.getText());
//            request.setAttribute("email", email);
//            request.setAttribute("owner", owner);
//            String path = "/views/emails/email.jsp";
//            ServletContext servletContext = getServletContext();
//            RequestDispatcher requestDispatcher = servletContext.getRequestDispatcher(path);
//            requestDispatcher.forward(request, response);
//            return;
//        }
        //Пошук поточного контакта "Основний"
        //Якщо новий контакт є основним, то поточний сбрасується у додатковий
//        Email emailPrior = DAOObjects.daoEmail.findPrior(owner);
//        if (emailPrior != null && email.isPrior()) {
//            //спочатку зміна телефону поточного основного у додатковий
//            emailPrior.setPrior(!email.isPrior());
//            boolean updateRes = DAOObjects.daoEmail.update(emailPrior.getId(), emailPrior);
//            if (!updateRes) {
//                System.out.println("Помилка скидання Основного email! Update SQL mistake!!!");
//                String path = request.getContextPath() + "/emails?id_owner=" + idOwner + "&msgcode=4";
//                response.sendRedirect(path);
//                return;
//            }
//        }
        //Call Insert
        boolean insertRes = DAOObjects.daoAddress.insert(addressIns);
        if (insertRes) {
            //back to list Emails
            System.out.println("Новий address був доданий!");
            String path = request.getContextPath() + "/addresses?id_owner=" + idOwner + "&msgcode=1";
            response.sendRedirect(path);
            return;
        } else {
            System.out.println("Помилка додавання! Insert SQL mistake!!!");
            request.setAttribute("error", ContactMessages.MESSAGE02.getText());
            request.setAttribute("address", address);
            request.setAttribute("owner", owner);
            String path = "/views/addresses/address.jsp";
            ServletContext servletContext = getServletContext();
            RequestDispatcher requestDispatcher = servletContext.getRequestDispatcher(path);
            requestDispatcher.forward(request, response);
        }

    }
}
