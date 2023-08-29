package ch5;

import java.io.IOException;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.hibernate.mapping.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import shared.ButtonMethod;
import shared.HelperBaseCh5;
import shared.HibernateHelper;
import shared.RequestDataPersistent;
public class ControllerHelper extends HelperBaseCh5 {
        protected RequestDataPersistent data;

        public ControllerHelper(HttpServlet servlet, HttpServletRequest request, HttpServletResponse response)
        {
                super(servlet, request, response);
                data = new RequestDataPersistent();
        }

        public Object getData() { return data; }
       
        static public void initHibernate(HttpServlet servlet) {
                Properties props = new Properties();
                props.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
                props.setProperty("hibernate.connection.driver_class", "com.mysql.jdbc.Driver");

                props.setProperty("hibernate.c3p0.min_size", "1");
                props.setProperty("hibernate.c3p0.max_size", "5");
                props.setProperty("hibernate.c3p0.timeout", "300");
                props.setProperty("hibernate.c3p0.max_statements", "50");
                props.setProperty("hibernate.c3p0.idle_test_period", "300");

                props.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/webdev");
                props.setProperty("hibernate.connection.username", "root");
                props.setProperty("hibernate.connection.password", "");

                boolean create = Boolean.parseBoolean(servlet.getInitParameter("create"));

                if (create) HibernateHelper.createTable(props, RequestDataPersistent.class);
                HibernateHelper.initSessionFactory(props, RequestDataPersistent.class);
        }
        @ButtonMethod(buttonName="editButton", isDefault=true)
        public String edit() {
                return jspLocation("Edit.jsp");
        }
        @ButtonMethod(buttonName="confirmButton")
        public String confirm() {
                fillBeanFromRequest(data);
                if (isValid(data)) 
                {
                        LogManager.getLogger(ControllerHelper.class).info("valid data " + 
                                        request.getParameterMap().values().toString()); 
                        return jspLocation("Confirm.jsp");
                }
                else {
                        LogManager.getLogger(ControllerHelper.class).info("invalid data " + 
                                        request.getParameterMap().values().toString());
                        return jspLocation("Edit.jsp");
                }
        }
        @ButtonMethod(buttonName="processButton")
        public String process() {
                if (!isValid(data)) return jspLocation("Expired.jsp");
                HibernateHelper.updateDB(data);
                java.util.List list = HibernateHelper.getListData(data.getClass());
                request.setAttribute("database", list);
                return jspLocation("Process.jsp");
        }
        public void doGet() throws ServletException, IOException
        {
                addHelperToSession("helper", SessionData.IGNORE);
                
                String address = edit();

                RequestDispatcher dispatcher = request.getRequestDispatcher(address);
                dispatcher.forward(request, response);
        }
        public void doPost() throws ServletException, IOException
        {
                addHelperToSession("helper", SessionData.READ);
                
                String address = executeButtonMethod();

                RequestDispatcher dispatcher = request.getRequestDispatcher(address);
                dispatcher.forward(request, response);
        }
        private String jspLocation(String addr) {
                return "/ch5/" + addr;
        }
        protected void copyFromSession(Object obj)
        {
                if (obj.getClass() == this.getClass())
                        data = ((ControllerHelper)obj).data;
        }
}

