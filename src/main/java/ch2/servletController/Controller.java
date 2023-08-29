package ch2.servletController;

import java.io.IOException;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns={"/ch2/servletController/Controller"})
public class Controller extends HttpServlet
{
        @Override
        protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException
        {
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
        private String jspLocation(String addr) { return "/ch2/servletController/" + addr;}

}   
