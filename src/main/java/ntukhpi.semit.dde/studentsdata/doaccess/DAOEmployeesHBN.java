package ntukhpi.semit.dde.studentsdata.doaccess;

/**
 * class DAOEmployeesHBN
 * <p>
 * DAO - data access object
 * Provide implementation of CRUD with specified table
 * In presented view can work with any type of DBMS
 */
public class DAOEmployeesHBN {
//    // Set of final String variables with SQL query text
//
////    private static final String SELECT_BY_ID_SQL_TEXT =
////            "SELECT * FROM EMPLOYEE WHERE id=?";
////    private static final String SELECT_ID_SQL_TEXT =
////            "SELECT * FROM employee WHERE name=?";
////    private static final String INSERT_SQL_TEXT =
////            "INSERT INTO employee (name,pol,age,salary) VALUES (?,?,?,?)";
////
////    private static final String UPDATE_SQL_TEXT =
////            "UPDATE employee SET name = ?,pol = ?,age = ?,salary = ? WHERE (id = ?)";
////
////    private static final String UPDATE_NAME_SQL_TEXT =
////            "UPDATE employee SET name = ? WHERE (id = ?)";
////
////    private static final String DELETE_ID_SQL_TEXT =
////            "DELETE FROM employee WHERE (id = ?)";
//
//
//    /**
//     * Method returned list of Employee get from DB table Employee
//     *
//     * @return EmployeeList (extends ArrayList<Employee>)
//     */
//    public static List<Employee> getEmployeeList() {
//        List<Employee> myList = new ArrayList<>();
//        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
//            myList = session.createQuery("from Employee", Employee.class).list();
//            //myList.forEach(System.out::println);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return myList;
//    }
//
//    /**
//     * Method to found record id by name
//     *
//     * @param id - key field in table Employee, specified value for looking in table
//     * @return Employee object value -!!!
//     */
//    public static Employee getEmployeeById(long id) {
//        Employee empl = null;
//        //Find in DB by id
//        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
//            //Now in employee new snapshot of table.
//            //Lets find object just inserted.
//            empl = session.get(Employee.class, id);
//        } catch (Exception e) {
//            System.err.println("=== DAOEmployeesHBN#getEmployeeById ====> Something went wrong!");
//        }
//        return empl;
//    }
//
//    /**
//     * Method to found record id by name
//     *
//     * @param name - key field in table Employee, specified value for looking in table
//     * @return Employee value - id found record or -1, record with specified name not it DB
//     */
//    public static Employee getEmployeeByName(String name) {
//        Employee empl = null;
//        List<Employee> results = null;
//        //Find in DB by id
//        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
//            //New approach
//            CriteriaBuilder cb = session.getCriteriaBuilder();
//            CriteriaQuery<Employee> cr = cb.createQuery(Employee.class);
//            Root<Employee> root = cr.from(Employee.class);
//            cr.select(root).where(cb.equal(root.get("name"),name));
//            Query<Employee> query = session.createQuery(cr);
//            results = query.getResultList();
//            //Deprecated approach
////            results = session.createCriteria(Employee.class)
////                    .add(Restrictions.eq("name", name))
////                    .list();
//            if (!results.isEmpty()) {
//                empl = results.get(0);
//            } else {
//            }
//        } catch (Exception e) {
//            System.err.println("=== DAOEmployeesHBN#getEmployeeByName === Something went wrong!");
//        }
//        return empl;
//    }
//
//    /**
//     * Method to insert new record of Employee
//     *
//     * @param newEmployee - instance of Employee for storing in table
//     * @return boolean - true if record has been added, false - in other case
//     */
//    public static boolean insert(Employee newEmployee) {
//        boolean insertOk = false;
//        //INSERT NEW and Show Employee after insert
//        Transaction transaction = null;
//        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
//            // start a transaction
//            transaction = session.beginTransaction();
//            // save the student objects
//            session.save(newEmployee);
//            // commit transaction
//            transaction.commit();
//            insertOk = true;
//        } catch (Exception e) {
//            if (transaction != null) {
//                transaction.rollback();
//            }
//            System.err.println("DAOEmployeesHBN#insert ===> Something went wrong!");
//            insertOk = false;
//        }
//        return insertOk;
//    }
//
//    /**
//     * Method to update record of Employee with specified id
//     *
//     * @param id          - specified id
//     * @param newEmployee - instance of Employee for storing in table
//     * @return boolean - true if record has been updated, false - in other case
//     */
//    public static boolean update(Long id, Employee newEmployee) {
//        boolean updateOk = false;
//        Transaction transaction = null;
//        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
//            transaction = session.beginTransaction();
//            System.out.println("id ="+id);
//            Employee employeeToUpdate = session.get(Employee.class, id);
//            System.out.println("DAO#update "+employeeToUpdate);
//            employeeToUpdate.setName(newEmployee.getName()); //!!!
//            employeeToUpdate.setAge(newEmployee.getAge());
//            employeeToUpdate.setPol(newEmployee.isPol());
//            employeeToUpdate.setSalary(newEmployee.getSalary());
//            session.update(employeeToUpdate);
//            transaction.commit();
//            updateOk = true;
//        } catch (Exception ex) {
//            if (transaction != null) {
//                transaction.rollback();
//            }
//            System.err.println("DAOEmployeesHBN#update ===> Something went wrong!");
//            updateOk = false;
//        }
//        return updateOk;
//    }
//
//    /**
//     * Method to update record name with specified id
//     *
//     * @param id           - specified id
//     * @param nameToUpdate - new name of record
//     * @return boolean - true if record has been updated, false - in other case
//     */
//    public static boolean updateName(Long id, String nameToUpdate) {
//        boolean updateOk = false;
//        if (id != -1) {
//            try{
//                updateOk = true;
//            } catch (Exception ex) {
//            }
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
//                Employee emplToDelete = session.get(Employee.class, id);
//                // delete the Employee objects
//                session.delete(emplToDelete);
//                // commit transaction
//                transaction.commit();
//                deleteOk = true;
//            } catch (Exception e) {
//                if (transaction != null) {
//                    transaction.rollback();
//                }
//                System.err.println("DAOEmployeesHBN#delete ===> Something went wrong!");
//                deleteOk = false;
//            }
//        }
//        return deleteOk;
//    }
}


