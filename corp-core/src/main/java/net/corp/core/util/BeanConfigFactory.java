package net.corp.core.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;


@SuppressWarnings("unchecked")
public class BeanConfigFactory implements ApplicationContextAware
{

   private static BeanConfigFactory instance           = null;

   private ApplicationContext       applicationContext = null;

   /**
    * Private constructor.
    */
   public BeanConfigFactory()
   {
   }

   /**
    * @return Returns the static instance of this class.
    */
   public static BeanConfigFactory getInstance()
   {
      if (instance == null)
      {
         instance = new BeanConfigFactory();
      }

      return instance;
   }

   /**
    * @return Returns the context.
    */
   public ApplicationContext getApplicationContext()
   {
      return applicationContext;
   }

   /**
    * @param applicationContext
    *           The applicationContext to set.
    */
   public void setApplicationContext(ApplicationContext applicationContext)
   {
      this.applicationContext = applicationContext;
   }

   /**
    * Gets a reference to a bean initialized by Spring's WebApplicationContext.
    * 
    * @param id
    *           The id of the bean as defined in a configuration file.
    * @return Returns an instance of a bean with the specified id. or null if it
    *         does not exist.
    */
   public static <T> T getBean(String id)
   {
      return (T) getInstance().getApplicationContext().getBean(id);
   }
  
}
