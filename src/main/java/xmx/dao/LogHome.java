package xmx.dao;
// default package
// Generated 2017-11-24 11:58:56 by Hibernate Tools 5.2.6.Final


import java.util.List;
import javax.naming.InitialContext;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import xmx.model.Log;

/**
 * Home object for domain model class Log.
 * @see .Log
 * @author Hibernate Tools
 */
@Repository("logDao")
public class LogHome {

    private static final org.apache.commons.logging.Log log = LogFactory.getLog(LogHome.class);

    @Autowired
    private SessionFactory sessionFactory;
    
    public void persist(Log transientInstance) {
        log.debug("persisting Log instance");
        try {
            sessionFactory.getCurrentSession().persist(transientInstance);
            log.debug("persist successful");
        }
        catch (RuntimeException re) {
            log.error("persist failed", re);
            throw re;
        }
    }
    
    public void attachDirty(Log instance) {
        log.debug("attaching dirty Log instance");
        try {
            sessionFactory.getCurrentSession().saveOrUpdate(instance);
            log.debug("attach successful");
        }
        catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    
    public void attachClean(Log instance) {
        log.debug("attaching clean Log instance");
        try {
            sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        }
        catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    
    public void delete(Log persistentInstance) {
        log.debug("deleting Log instance");
        try {
            sessionFactory.getCurrentSession().delete(persistentInstance);
            log.debug("delete successful");
        }
        catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }
    
    public Log merge(Log detachedInstance) {
        log.debug("merging Log instance");
        try {
            Log result = (Log) sessionFactory.getCurrentSession()
                    .merge(detachedInstance);
            log.debug("merge successful");
            return result;
        }
        catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }
    
    public Log findById( java.lang.Integer id) {
        log.debug("getting Log instance with id: " + id);
        try {
            Log instance = (Log) sessionFactory.getCurrentSession()
                    .get("xmx.model.Log", id);
            if (instance==null) {
                log.debug("get successful, no instance found");
            }
            else {
                log.debug("get successful, instance found");
            }
            return instance;
        }
        catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
    
    public List findByExample(Log instance) {
        log.debug("finding Log instance by example");
        try {
            List results = sessionFactory.getCurrentSession()
                    .createCriteria("xmx.model.Log")
                    .add(Example.create(instance))
            .list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        }
        catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    } 
}

