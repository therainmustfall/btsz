package ch4;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import shared.ButtonMethod;
import shared.HelperBaseCh4;
import ch3.controMitBean.RequestData;
public class ControllerHelper extends HelperBaseCh4 {
        protected RequestData data;

        public ControllerHelper(HttpServlet servlet, HttpServletRequest request, HttpServletResponse response)
        {
                super(servlet, request, response);
                data = new RequestData();
        }

        public Object getData() { return data; }
        
        @ButtonMethod(buttonName="editButton", isDefault=true)
        public String edit() {
                return jspLocation("Edit.jsp");
        }
        @ButtonMethod(buttonName="confirmButton")
        public String confirm() {
                fillBeanFromRequest(data);
                //data.setHobby(request.getParameter("hobby"));
                //data.setAversion(request.getParameter("aversion"));
                return jspLocation("Confirm.jsp");
        }
        @ButtonMethod(buttonName="processButton")
        public String process() {
                return jspLocation("Process.jsp");
        }
        public void doGet() throws ServletException, IOException
        {
                addHelperToSession("helper", SessionData.READ);
                
                String address = executeButtonMethod();

                RequestDispatcher dispatcher = request.getRequestDispatcher(address);
                dispatcher.forward(request, response);
        }
        private String jspLocation(String addr) {
                return "/ch4/" + addr;
        }
        protected void copyFromSession(Object obj)
        {
                if (obj.getClass() == this.getClass())
                        data = ((ControllerHelper)obj).data;
        }
}

