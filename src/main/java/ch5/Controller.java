package ch5;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class Controller extends HttpServlet
{
        @Override
        public void init() {
                ControllerHelper.initHibernate(this);
        }
        @Override
        protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException
        {
                ControllerHelper controllerHelper = new ControllerHelper(this, request, response);
                controllerHelper.doGet();
        }

        @Override
        protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException
        {
                ControllerHelper helper = new ControllerHelper(this, request, response);
                helper.doPost();
        }
}   
