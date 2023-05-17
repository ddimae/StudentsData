package ntukhpi.semit.dde.studentsdata.servlets.phones;

import ntukhpi.semit.dde.studentsdata.doaccess.DAOObjects;
import ntukhpi.semit.dde.studentsdata.entity.PhoneNumber;
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

@WebServlet("/add_phone")
public class PhonePersonAddServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("PhonePersonAddServlet#doGet");
        Long id_owner = Long.parseLong(request.getParameter("id_owner"));
        Person owner = DAOObjects.daoPerson.findById(id_owner);
        System.out.println(owner.getClass() + ": " + owner);
        request.setAttribute("error", "");
        PhoneNumber phoneToIns = new PhoneNumber(true, false, owner, "");
        //Now id = null!!! We must set not null value!!!
        phoneToIns.setId(0l);
        System.out.println("phoneToIns:" + phoneToIns);
        request.setAttribute("phone", phoneToIns);
        request.setAttribute("owner", phoneToIns.getOwner());
        String path = "/views/phones/phone.jsp";
        ServletContext servletContext = getServletContext();
        RequestDispatcher requestDispatcher = servletContext.getRequestDispatcher(path);
        requestDispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("PhonePersonAddServlet#doPost");
        //Get parameters
        String idPhoneStr = request.getParameter("id_phone");
//        System.out.println(idPhoneStr);
        String idOwnerStr = request.getParameter("id_owner");
//        System.out.println(idOwnerStr);
        String phoneNumber = request.getParameter("phone_number");
//        System.out.println(phoneNumber);
        String activeStr = new String(request.getParameter("active").getBytes("ISO-8859-1"), "utf-8");
//        System.out.println(activeStr);
        String priorStr = new String(request.getParameter("prior").getBytes("ISO-8859-1"), "utf-8");
//        System.out.println(priorStr);

        // Create object
        Long idPhone = Long.parseLong(idPhoneStr);
//        System.out.println(idPhone);
        Long idOwner = Long.parseLong(idOwnerStr);
//        System.out.println(idOwner);
//        System.out.println(phoneNumber);
        boolean active = "Активний".equals(activeStr);
//        System.out.println("Активний = " + active);
        boolean prior = "Основний".equals(priorStr);
//        System.out.println("Основний = " + prior);
        Person owner = DAOObjects.daoPerson.findById(idOwner);
        PhoneNumber phone = new PhoneNumber(active, prior, owner, phoneNumber);
        phone.setId(idPhone);
        // Неактивний телефон не може бути основним
        if (phone.isPrior() && !phone.isActive()) {
            System.out.println("\"Неактивний\" телефон не може бути \"Основним\"!!!");
            request.setAttribute("error", ContactMessages.MESSAGE03.getText());
            request.setAttribute("phone", phone);
            request.setAttribute("owner", owner);
            String path = "/views/phones/phone.jsp";
            ServletContext servletContext = getServletContext();
            RequestDispatcher requestDispatcher = servletContext.getRequestDispatcher(path);
            requestDispatcher.forward(request, response);
            return;
        }
        //Пошук поточного контакта "Основний"
        //Якщо новий контакт є основним, то поточний сбрасується у додатковий
        PhoneNumber phonePrior = DAOObjects.daoPhone.findPrior(owner);
        if (phonePrior != null && phone.isPrior()) {
            //спочатку зміна телефону поточного основного у додатковий
            phonePrior.setPrior(!phone.isPrior());
            boolean updateRes = DAOObjects.daoPhone.update(phonePrior.getId(), phonePrior);
            if (!updateRes) {
                System.out.println("Помилка скидання Основного телефону! Update SQL mistake!!!");
                String path = request.getContextPath() + "/phones?id_owner=" + idOwner+ "&msgcode=4";
                response.sendRedirect(path);
                return;
            }
        }
        //Call Insert
        boolean insertRes = DAOObjects.daoContact.insert(phone);
        if (insertRes) {
            //back to list Phones
            System.out.println("Новий номер телефону був доданий!");
            String path = request.getContextPath() + "/phones?id_owner=" + idOwner+ "&msgcode=1";
            response.sendRedirect(path);
            return;
        } else {
            System.out.println("Помилка додавання! Insert SQL mistake!!!");
            request.setAttribute("error", ContactMessages.MESSAGE02.getText());
            request.setAttribute("phone", phone);
            request.setAttribute("owner", owner);
            String path = "/views/phones/phone.jsp";
            ServletContext servletContext = getServletContext();
            RequestDispatcher requestDispatcher = servletContext.getRequestDispatcher(path);
            requestDispatcher.forward(request, response);
        }
    }
}
