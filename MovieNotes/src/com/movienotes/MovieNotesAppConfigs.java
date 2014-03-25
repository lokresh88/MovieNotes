package com.movienotes;
import javax.servlet.ServletContextEvent;  
import javax.servlet.ServletContextListener;  
public class MovieNotesAppConfigs implements ServletContextListener {  
  
    @Override  
    public void contextInitialized(ServletContextEvent sce) {  
        /* Sets the context in a static variable */  
        DBUtil.setServletContext(sce.getServletContext());  
    }  
  
    @Override  
    public void contextDestroyed(ServletContextEvent sce) { }  
  
}  