package ntukhpi.semit.dde.studentsdata.doaccess;

public class DAOPhonesHBN {
//    /**
//     * Method returned list of inn get from DB table phones
//     *
//     * @return  ArrayList<Phone>
//     */
//    public static List<Phone> getPhonesList() {
//        List<Phone> myList = new ArrayList<>();
//        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
//            myList = session.createQuery("from Phone", Phone.class).list();
//            //myList.forEach(System.out::println);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return myList;
//    }
//
//    /**
//     * Method returned list of inn get from DB table phones for specified owner
//     *
//     * @return  ArrayList<Phone> for Employee with ownerName
//     */
//    public static List<Phone> getPhonesListOfOwner(String ownerName) {
//        Employee owner = DAOEmployeesHBN.getEmployeeByName(ownerName);
//        List<Phone> myList = new ArrayList<>();
//        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
//            CriteriaBuilder cb = session.getCriteriaBuilder();
//            CriteriaQuery<Phone> cr = cb.createQuery(Phone.class);
//            Root<Phone> root = cr.from(Phone.class);
//            cr.select(root).where(cb.equal(root.get("owner"), owner));
//            Query<Phone> query = session.createQuery(cr);
//            myList = query.getResultList();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return myList;
//    }
//
//    /**
//     * Method returned record from table phones by phoneNumber
//     *
//     * @return Phone
//     */
//    public static Phone getPhoneByPhoneNumber(String phoneNumber) {
//        List<Phone> results = null;
//        Phone phone = null;
//        //Find in DB by phoneNumber
//        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
//            //New approach
//            CriteriaBuilder cb = session.getCriteriaBuilder();
//            CriteriaQuery<Phone> cr = cb.createQuery(Phone.class);
//            Root<Phone> root = cr.from(Phone.class);
//            cr.select(root).where(cb.equal(root.get("phoneNumber"), phoneNumber));
//            Query<Phone> query = session.createQuery(cr);
//            results = query.getResultList();
//            if (!results.isEmpty()) {
//                phone = results.get(0);
//            } else {
//            }
//        } catch (Exception e) {
//            System.err.println("******* DAOPhoneHBN#getPhoneByPhoneNumber Find Phone in db (ByPhoneNumber) PROBLEM ********");
//        }
//        return phone;
//    }
//
//    /**
//     * Method returned record from table phones by id
//     *
//     * @return Phone
//     */
//    public static Phone getPhoneById(Long id) {
//        List<Phone> results = null;
//        Phone phone = null;
//        //Find in DB by id
//        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
//            //New approach
//            CriteriaBuilder cb = session.getCriteriaBuilder();
//            CriteriaQuery<Phone> cr = cb.createQuery(Phone.class);
//            Root<Phone> root = cr.from(Phone.class);
//            cr.select(root).where(cb.equal(root.get("id"), id));
//            Query<Phone> query = session.createQuery(cr);
//            results = query.getResultList();
//            if (!results.isEmpty()) {
//                phone = results.get(0);
//            } else {
//            }
//        } catch (Exception e) {
//            System.err.println("******* DAOPhoneHBN#getPhoneById Find Phone in db (ById) PROBLEM ********");
//        }
//        return phone;
//    }
//
//    /**
//     * Method to insert new record of Phones
//     *
//     * @param newPhone - instance of Employee for storing in table
//     * @return boolean - true if record has been added, false - in other case
//     */
//    public static boolean insert(Phone newPhone) {
//        boolean insertOk = false;
//        Transaction transaction = null;
//        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
//            // start a transaction
//            transaction = session.beginTransaction();
//            // save the student objects
//            session.save(newPhone);
//            // commit transaction
//            transaction.commit();
//            insertOk = true;
//        } catch (Exception e) {
//            if (transaction != null) {
//                transaction.rollback();
//            }
//            System.err.println("DAOPhonesHBN#insert ===> Something went wrong!");
//            insertOk = false;
//        }
//        return insertOk;
//    }
//
//    /**
//     * Method to update record of Phone with specified id
//     *
//     * @param id          - specified id
//     * @param newPhone - instance of INN for storing in table
//     * @return boolean - true if record has been updated, false - in other case
//     */
//    public static boolean update(Long id, Phone newPhone) {
//        boolean updateOk = false;
//        Transaction transaction = null;
//        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
//            transaction = session.beginTransaction();
//            Phone phoneToUpdate = session.get(Phone.class, id);
//            phoneToUpdate.setPhoneNumber(newPhone.getPhoneNumber()); //!!!
//            phoneToUpdate.setPhoneNumberType(newPhone.getPhoneNumberType());
//            phoneToUpdate.setActivePhone(newPhone.isActivePhone());
//            session.update(phoneToUpdate);
//            transaction.commit();
//            updateOk = true;
//        } catch (Exception ex) {
//            if (transaction != null) {
//                transaction.rollback();
//            }
//            System.err.println("DAOPhoneHBN#update ===> Something went wrong!");
//            updateOk = false;
//        }
//        return updateOk;
//    }
//
//    /**
//     * Method to delete record with specified id
//     *
//     * @param id  - specified id
//     * @return boolean - true if record has been updated, false - in other case
//     */
//    public static boolean deleteByID(Long id) {
//        boolean deleteOk = false;
//        if (id != -1) {
//            Transaction transaction = null;
//            try (Session session = HibernateUtil.getSessionFactory().openSession()) {
//                // start a transaction
//                transaction = session.beginTransaction();
//                Phone phoneToDelete = session.get(Phone.class, id);
//                // delete the INN objects
//                session.delete(phoneToDelete);
//                // commit transaction
//                transaction.commit();
//                deleteOk = true;
//            } catch (Exception e) {
//                if (transaction != null) {
//                    transaction.rollback();
//                }
//                System.err.println("DAOPhoneHBN#delete ===> Something went wrong!");
//                deleteOk = false;
//            }
//        }
//        return deleteOk;
//    }
//
//    /**
//     * Method to delete all phones for specified owner (Employee)
//     *
//     * @param owner  - specified owner
//     * @return boolean - true if record has been updated, false - in other case
//     */
//    public static boolean deleteByOwner(Employee owner) {
//        boolean deleteOk = false;
//        if (owner != null) {
//            Transaction transaction = null;
//            try (Session session = HibernateUtil.getSessionFactory().openSession()) {
//                // start a transaction
//                transaction = session.beginTransaction();
//                List<Phone> list = getPhonesListOfOwner(owner.getName());
//                for (Phone phone:list) {
//                    // delete the INN objects
//                    session.delete(phone);
//                }
//                // commit transaction
//                transaction.commit();
//                deleteOk = true;
//            } catch (Exception e) {
//                if (transaction != null) {
//                    transaction.rollback();
//                }
//                System.err.println("DAOPhoneHBN#deleteByOwner ===> Something went wrong!");
//                deleteOk = false;
//            }
//        }
//        return deleteOk;
//    }


}
