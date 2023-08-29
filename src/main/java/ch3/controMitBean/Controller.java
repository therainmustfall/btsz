package ch3.controMitBean;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns={"/ch3/controMitBean/Controller"})
public class Controller extends HttpServlet
{
        @Override
        protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException
        {
                ControllerHelper controllerHelper = new ControllerHelper(this, request, response);
                controllerHelper.doGet();
        }

}   
