package ntukhpi.semit.dde.studentsdata.servlets.addresses;

import ntukhpi.semit.dde.studentsdata.doaccess.DAOObjects;
import ntukhpi.semit.dde.studentsdata.entity.Person;
import ntukhpi.semit.dde.studentsdata.entity.Address;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/address_current")
public class AddressChangeCurrentServlet extends HttpServlet {

    private Address myaddress;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("AddressChangeCurrentServlet#doGet");
        //Get parameters
        String idAddressStr = request.getParameter("id_address");
//        System.out.println(idAddressStr);
        String idOwnerStr = request.getParameter("id_owner");
//        System.out.println(idOwnerStr);

        // Find objects
        Long idAddress = Long.parseLong(idAddressStr);
//        System.out.println(idAddressStr);
        Long idOwner = Long.parseLong(idOwnerStr);
//        System.out.println(idOwner);
        Person owner = DAOObjects.daoPerson.findById(idOwner);
        Address address = DAOObjects.daoAddress.findById(idAddress);
        //Пошук поточного контакта "Основний"
        Address addressCurrent = owner.getCurrentAddress();
        // Готується зміна на протилежне
        Boolean newCurrentValue = !owner.getCurrent(address);
        //Call Update
        boolean updateRes = false;
        if (addressCurrent != null&&!addressCurrent.equals(address)) {
            //спочатку зміна address поточного основного у додатковий
            owner.setCurrent(addressCurrent,!owner.getCurrent(addressCurrent));
            updateRes = DAOObjects.daoPerson.updateAddresses(owner.getId(), owner);
            if (!updateRes) {
                System.out.println("Помилка скидання Основного address! Update SQL mistake!!!");
                String path = request.getContextPath() + "/addresses?id_owner=" + idOwner+ "&msgcode=4";
                response.sendRedirect(path);
                return;
            }
        }
        //Встановлюємо поточний address як основний
        owner.setCurrent(address,newCurrentValue);
        updateRes = DAOObjects.daoPerson.updateAddresses(owner.getId(), owner);
        if (updateRes) {
            //back to addresses_persons
            if (address.equals(addressCurrent)&&!newCurrentValue) {
                System.out.println("\"Основний\" address вилучено!");
                String path = request.getContextPath() + "/addresses?id_owner=" + idOwner+ "&msgcode=6";
                response.sendRedirect(path);
                return;
            } else {
                System.out.println("\"Основний\" address змінено!");
                String path = request.getContextPath() + "/addresses?id_owner=" + idOwner+ "&msgcode=7";
                response.sendRedirect(path);
                return;
            }

        } else {
            System.out.println("Помилка оновлення! Перевірте наявність address із статусом \"Основний\"! Update SQL mistake!!!");
            String path = request.getContextPath() + "/addresses?id_owner="+idOwner+ "&msgcode=8";
            response.sendRedirect(path);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }
}