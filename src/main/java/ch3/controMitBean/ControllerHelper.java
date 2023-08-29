package ch3.controMitBean;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ControllerHelper extends HelperBase {
        protected RequestData data;

        public ControllerHelper(HttpServlet servlet, HttpServletRequest request, HttpServletResponse response)
        {
                super(servlet, request, response);
                data = new RequestData();
        }

        public Object getData() { return data; }

        public void doGet() throws ServletException, IOException
        {
                request.getSession().setAttribute("helper", this);
                data.setHobby(request.getParameter("hobby"));
                data.setAversion(request.getParameter("aversion"));

                String address;
                if (request.getParameter("processButton") != null) 
                {
                        address = jspLocation("Process.jsp");
                }
                else if (request.getParameter("confirmButton") != null)
                {
                        address = jspLocation("Confirm.jsp");
                }
                else
                {
                        address = jspLocation("Edit.jsp");
                }

                RequestDispatcher dispatcher = request.getRequestDispatcher(address);
                dispatcher.forward(request, response);
        }
        private String jspLocation(String addr) {
                return "/ch3/controMitBean/" + addr;
        }
}
