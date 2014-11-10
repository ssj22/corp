<%
org.smslib.OutboundMessage msg = new org.smslib.OutboundMessage("9767942400", "From JSP");
org.smslib.Service.getInstance().sendMessage(msg);



%>