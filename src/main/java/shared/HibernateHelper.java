package shared;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;

import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

public class HibernateHelper {
        
        static protected Logger log = LogManager.getLogger(HibernateHelper.class);
        static protected List<Class> classes = new ArrayList<Class>();
        static protected SessionFactory sessionFactory;

        static protected boolean addMappings(List<Class> list, Class... mappings) 
        {
                boolean bNewClass = false;
                for (Class mapping : mappings)
                {
                        if (!list.contains(mapping)) {
                                list.add(mapping);
                                bNewClass = true;
                        }
                }
                return bNewClass;
        }
        
        static protected void configureFromFile(Configuration cfg) {
                try {
                        cfg.configure();
                } catch (HibernateException he) {
                        if (he.getMessage().equals("/hibernate.cfg.xml not found")) {
                                log.warn(he.getMessage());
                        } else {
                                log.error("Error in hibernate configuration file.", he);
                                throw he;
                        }
                }
        }

        static protected SessionFactory buildFactory(Configuration cfg)
                throws Exception {
                SessionFactory factory= null;
                try {
                        factory= cfg.buildSessionFactory();
                } catch (Exception ex) {
                        closeSessionFactory(factory);
                        factory = null;
                        throw ex;
                }
                return factory;
        }
        static protected void closeSessionFactory(SessionFactory factory)
        { if (factory != null) factory.close();}

        static public void closeFactory(){ closeSessionFactory(sessionFactory); }
        static protected SessionFactory createFactory(Properties props, List<Class> list) {
                SessionFactory factory = null;
                Configuration cfg = new Configuration();

                try {
                        if (props != null) cfg.addProperties(props);
                        //configureFromFile(cfg);
                        for (Class clazz : list)
                                cfg.addAnnotatedClass(clazz);
                        factory = buildFactory(cfg);
                } catch (Exception ex) {
                        log.error("Session factory creation failed.", ex);
                        closeSessionFactory(factory);
                        factory = null;
                        throw new HibernateException(ex);
                }

                return factory;
        }
        
        static public void initSessionFactory(Properties props, Class... mappings) {
                if (addMappings(classes, mappings)) {
                        closeSessionFactory(sessionFactory);
                        sessionFactory = createFactory(props, classes);
                }
        }
        static public void initSessionFactory(Class... mappings) { initSessionFactory(null, mappings); }

        static public void createTable(Properties props, Class... mappings){
                List<Class> tempList = new ArrayList<Class>();
                SessionFactory tempFactory = null;

                addMappings(tempList, mappings);
                if (props == null) props = new Properties();
                props.setProperty(Environment.HBM2DDL_AUTO, "create");
                tempFactory = createFactory(props, tempList);
                closeSessionFactory(tempFactory);
        }

        static public void createTable(Class... mappings) { createTable(null, mappings);}

        static public boolean isSessionOpen() { return sessionFactory != null; }
        
        static public void saveDB(Object obj) {
                Session session = null;
                try {
                        session = sessionFactory.openSession();
                        Transaction tx = session.beginTransaction();

                        session.save(obj);

                        tx.commit();
                } finally {
                        if (session != null) session.close();
                }

        }
        static public void updateDB(Object obj) {
                Session session = null;
                try {
                        session = sessionFactory.openSession();
                        Transaction tx = session.beginTransaction();

                        session.saveOrUpdate(obj);

                        tx.commit();
                } finally {
                        if (session != null) session.close();
                }

        }
        static public void updateDB(List list) {
                Session session = null;
                try {
                        session = sessionFactory.openSession();
                        Transaction tx = session.beginTransaction();
                        for (Object obj: list)
                                session.saveOrUpdate(obj);

                        tx.commit();
                } finally {
                        if (session != null) session.close();
                }

        }

        static public void removeDB(Object obj) {
                Session session = null;
                try {
                        session = sessionFactory.openSession();
                        Transaction tx = session.beginTransaction();

                        session.delete(obj);

                        tx.commit();
                } finally {
                        if (session != null) session.close();
                }

        }
        static public List getListData(Class classBean, String strKey, Object value) {
                List<Object> result = new ArrayList<Object>();
                Session session = null;
                try {
                        session = sessionFactory.openSession();
                        
                        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
                        CriteriaQuery query = criteriaBuilder.createQuery(classBean);

                        Root root = query.from(classBean);
                        if (strKey != null) query.select(root).where(criteriaBuilder.like(root.get(strKey), "%"+value+"%"));
                        else query.select(root);
                        Query cr = session.createQuery(query);
                        result = cr.getResultList();
                } finally {
                        if (session != null) session.close();
                }
                return result;
        }

        static public List getListData(Class classBean, String strKey, Object value, String strkey1, Object value1) {
                List<Object> result = new ArrayList<Object>();
                Session session = null;
                try {
                        session = sessionFactory.openSession();
                        
                        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
                        CriteriaQuery query = criteriaBuilder.createQuery(classBean);

                        Root root = query.from(classBean);
                        if (strKey != null) 
                                query.select(root).where(criteriaBuilder.like(root.get(strKey), "%"+value+"%"));
                        
                        if (strkey1 != null) 
                                query.select(root).where(criteriaBuilder.like(root.get(strkey1), "%"+value1+"%"));

                        Query cr = session.createQuery(query);
                        result = cr.getResultList();
                } finally {
                        if (session != null) session.close();
                }
                return result;
        }
         static public Object getFirstMatch(Class classBean, String strKey, Object value) {
                Object result = null;
                Session session = null;
                try {
                        session = sessionFactory.openSession();
                        
                        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
                        CriteriaQuery query = criteriaBuilder.createQuery(classBean);
                        Root root = query.from(classBean);
                        if (strKey != null) query.select(root).where(criteriaBuilder.like(root.get(strKey), "%"+value+"%"));
                        else query.select(root);
                        Query cr = session.createQuery(query);
                        result = cr.getSingleResult();
                } finally {
                        if (session != null) session.close();
                }
                return result;
        }
        static public List getListData(Class classBean) {
                return getListData(classBean, null, null);
        }
}
