<%
  String address;
  if (request.getParameter("processButton") != null)
  {
        address = "Process.jsp";
  }
  else if (request.getParameter("confirmButton") != null)
  {
        address = "Confirm.jsp";
  } 
  else 
  {
        address = "Edit.jsp";
  }

  RequestDispatcher dispatcher = request.getRequestDispatcher(address);
  dispatcher.forward(request, response);
%>
