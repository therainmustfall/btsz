package  shared;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public abstract class HelperBaseCh4 {
        private Method methodDefault = null;
        protected HttpServletRequest request;
        protected HttpServletResponse response;
        protected HttpServlet servlet;
        Logger ch4Logger;
        public HelperBaseCh4(HttpServlet servlet, HttpServletRequest request, HttpServletResponse response)
        {
                this.servlet = servlet;
                this.request = request;
                this.response = response;
                initLogger();
        }
        protected void initLogger() {
                String logName = "btszbook.ch4.base.logger";
                String initName = servlet.getInitParameter("logName");
                if (initName != null) logName = initName;

                Level logLevel = Level.DEBUG;
                String strLevel = servlet.getInitParameter("logLevel");
                if (strLevel != null) logLevel = Level.toLevel(strLevel);

                ch4Logger = LogManager.getLogger(logName);
                ch4Logger.info("HelperBaseCh4 info logging.");
                ch4Logger.log(logLevel, "Helper Base ch4 loger initialized");
        }

        protected String executeButtonMethod() throws ServletException, IOException{
                String result = "";
                methodDefault = null;
                Class clazz = this.getClass();
                Class enclosingClass = clazz.getEnclosingClass();
                while (enclosingClass != null) {
                        clazz = enclosingClass.getClass();
                        enclosingClass = clazz.getEnclosingClass();
                }

                try {
                        result = executeButtonMethod(clazz, true);
                } catch (Exception ex) {
                        writeError(request, response, "Button Method Error", ex);
                        return "";
                }
                return result;
        }

        protected String executeButtonMethod(Class clazz, boolean searchForDefault)
                throws IllegalAccessException, InvocationTargetException {
                String result = "";
                Method[] methods = clazz.getDeclaredMethods();
                for (Method method : methods) {
                        ButtonMethod annotation = method.getAnnotation(ButtonMethod.class);
                        if (annotation != null) {
                                if (searchForDefault && annotation.isDefault())
                                        methodDefault = method;
                                if (request.getParameter(annotation.buttonName()) != null) {
                                        result = invokeButtonMethod(method);
                                        break;
                                }
                        }
                }
                if (result.equals("")) {
                        Class superClass = clazz.getSuperclass();
                        if (superClass != null)
                                result = executeButtonMethod(superClass, methodDefault == null);
                        if (result.equals("")) {
                                if (methodDefault != null) result = invokeButtonMethod(methodDefault);
                                else {
                                        ch4Logger.error("(executeButtonMethod) no default method ");
                                        result = "No default method was specified.";
                                }
                        }
                }
                return result;
        }
        protected String invokeButtonMethod(Method buttonMethod)
                throws IllegalAccessException, InvocationTargetException {
                String resultInvoke = "Could not invoke method";
                try {
                        resultInvoke = (String) buttonMethod.invoke(this, (Object[]) null);
                } catch (IllegalAccessException iae) {
                        ch4Logger.error("(invoke) Button method is not public.", iae);
                        throw iae;
                } catch (InvocationTargetException ite) {
                        ch4Logger.error("(invoke) button Method exception.", ite);
                        throw ite;
                }
                return resultInvoke;
        }
        static public void writeError(HttpServletRequest request, 
                        HttpServletResponse response, 
                        String title,
                        Exception ex) throws IOException, ServletException {
                PrintWriter out = response.getWriter();
                response.setContentType("text/html");
                out.println("<html><head><title>" + title + "</title></head><body><h2>" + title + "</h2>");
                if (ex.getMessage() != null) out.println("<h3>" + ex.getMessage() + "</h3>");
                if (ex.getCause() != null) out.println("<h4>" + ex.getCause() + "</h4>");
                StackTraceElement[] trace = ex.getStackTrace();
                if (trace != null && trace.length > 0) out.println("<pre>");
                ex.printStackTrace(out);
                out.println("</pre></body></html>");
                out.close();
        }
        public void addHelperToSession(String name, SessionData state) {
                if (SessionData.READ == state) {
                        Object sessionObj = request.getSession().getAttribute(name);
                        if (sessionObj != null) copyFromSession(sessionObj);
                }
                request.getSession().setAttribute(name,this);
        }
        public void fillBeanFromRequest(Object data) {
                try {
                        BeanUtils.populate(data, request.getParameterMap());
                } catch (IllegalAccessException iae) {
                        ch4Logger.error("Populate - Illegal Access.", iae);
                } catch (InvocationTargetException ite) {
                        ch4Logger.error("Populate - Invocation Targe.", ite);
                }
        }
        protected abstract void copyFromSession(Object helper);

        protected enum SessionData { READ, IGNORE}
}
