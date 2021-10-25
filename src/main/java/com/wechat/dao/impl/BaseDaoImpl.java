/**
 * BaseDAOHibernate.java
 * <p>
 * Copyright 2007 easou, Inc. All Rights Reserved.
 */
package com.wechat.dao.impl;

import com.mysql.jdbc.Connection;
import com.wechat.dao.BaseDao;
import com.wechat.util.Page;
import com.wechat.util.PageList;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.*;
import org.hibernate.criterion.Criterion;
import org.springframework.orm.hibernate4.SessionFactoryUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public abstract class BaseDaoImpl<T> implements
        BaseDao {


    @Resource
    private SessionFactory sessionFactory;

    protected final Log LOG = LogFactory.getLog(this.getClass().getName());

    protected Class clazz;

    protected String entityName = null;

    protected Class<T> entityClass;

    public BaseDaoImpl(final Class clazz) {
        super();
        this.clazz = clazz;
    }

    public BaseDaoImpl(final String type) {
        super();
        this.entityName = type;
    }

    public BaseDaoImpl() {
        //entityClass = ReflectUtils.getClassGenricType(getClass());
    }


    /**
     * 根据主键获得实体
     */
    @Override
    public final Object getEntity(Class className, Integer id) {


        return sessionFactory.getCurrentSession().get(className, id);


    }


    /**
     * 保存实体
     *
     * @param entity 实体对象
     */
    @Override
    @Transactional
    public final void saveEntity(final Object entity) {
        if (entityName == null) {
            sessionFactory.getCurrentSession().saveOrUpdate(entity);
        } else {
            sessionFactory.getCurrentSession().saveOrUpdate(entityName, entity);
        }
    }

    /**
     * 根据主键删除实体
     *
     * @param id 实体主键
     */
    @Override
    @Transactional
    public final void removeEntity(Object o) {
        sessionFactory.getCurrentSession().delete(o);
    }

    /**
     * 执行批量更新和删除操作的HQL
     *
     * @param sql hql语句
     * @return PageList
     */
    protected final List executeHQL(final String sql) {
        Session session = null;
        List ret = null;
        try {
            session = sessionFactory.getCurrentSession();
            if (sql.toUpperCase().startsWith("DELETE")
                    || sql.toUpperCase().startsWith("UPDATE")) {

                session.createQuery(sql).executeUpdate();

            } else {
                ret = session.createQuery(sql).list();
            }
        } catch (HibernateException e) {
            LOG.error("executeHQL() error:" + sql, e);

        }
        return ret;
    }


    /**
     * 执行分页查询
     *
     * @param selectField HQL语句中，SELECT 的内容(如果设置了此参数值,则sql参数中不可带SELCT语句部分)
     * @param countField HQL语句中，count 的内容
     * @param sql HQL语句
     * @param page 第几页
     * @param rowsPerPage 每页记录数
     * @return PageList
     */
    @SuppressWarnings("unchecked")
    protected final List pageListQuery(final String selectField,
                                       String countField, String sql, int page, int rowsPerPage) {
        PageList result = new PageList();
        Session session = null;
        try {
            session = sessionFactory.getCurrentSession();
            // 预留count的sql语句
            String countSql = sql.substring(sql.toUpperCase().indexOf("FROM"));
            // 设置返回的列，进行查询
            if (null != selectField) {
                sql = "SELECT " + selectField + sql;
            }
            if (page <= 0) {
                page = 1; // page最小为1
            }
            LOG.debug("query sql:" + sql);
            Query q = session.createQuery(sql);
            if (rowsPerPage > 0) { // rowsPerPage的值是0或-1时,都返回全部结果集
                q.setFirstResult(rowsPerPage * (page - 1));
                q.setMaxResults(rowsPerPage);
            }
            result.addAll(q.list());
            // 设置分页查询的列
            if (null == countField) {
                countField = "*";
            }

            int rowsCount = result.size();
            if (rowsPerPage > 1) {
                // 每页记录数大于1且结果集大于0才计算分页信息,否则当前页记录数就作为总的记录数
                countSql = "select count(" + countField + ") " + countSql;
                // 计算总记录数时,消除 Order by语句，提高性能
                int oPos = countSql.toUpperCase().indexOf("ORDER BY");
                if (oPos > 0) {
                    countSql = countSql.substring(0, oPos);
                }
                LOG.debug("count sql:" + countSql);
                rowsCount = ((Long) session.createQuery(countSql).iterate()
                        .next()).intValue();
            }

            if (0 == rowsCount) {
                page = 0;
            }
            if (rowsPerPage < 0) {
                page = 1;
                rowsPerPage = rowsCount;
            }
            result.setCurrentPage(page);
            result.setTotalRowCount(rowsCount);
            result.calcPageCount(rowsPerPage);
            if (page > result.getPageCount()) {
                page = result.getPageCount();
                q = session.createQuery(sql);
                if (rowsPerPage > 0) {
                    q.setFirstResult(rowsPerPage * (page - 1));
                    q.setMaxResults(rowsPerPage);
                }
                result.addAll(q.list());
                result.setCurrentPage(page);
                result.setTotalRowCount(rowsCount);
                result.calcPageCount(rowsPerPage);
            }

        } catch (HibernateException e) {
            LOG.error("pageListQuery() error:" + sql, e);

        }
        return result;
    }

    /**
     * 执行分页查询
     *
     * @param selectField HQL语句中，SELECT 的内容(如果设置了此参数值,则sql参数中不可带SELCT语句部分)
     * @param sql HQL语句
     * @param page 第几页
     * @param rowsPerPage 每页记录数
     * @param totalRowCount HQL语句获得的总记录数
     * @return PageList
     */
    @SuppressWarnings("unchecked")
    protected final PageList pageListQuery(final String selectField,
                                           String sql, int page, int rowsPerPage, final int totalRowCount) {
        PageList result = new PageList();
        Session session = null;
        try {
            session = sessionFactory.getCurrentSession();
            // 设置返回的列，进行查询
            if (null != selectField) {
                sql = "SELECT " + selectField + sql;
            }
            if (page <= 0) {
                page = 1; // page最小为1
            }

            Query q = session.createQuery(sql);
            if (rowsPerPage > 0) { // rowsPerPage��ֵ��0��-1ʱ,������ȫ�����
                q.setFirstResult(rowsPerPage * (page - 1));
                q.setMaxResults(rowsPerPage);
            }

            result.addAll(q.list());

            if (0 == totalRowCount) {
                page = 0;
            }
            if (rowsPerPage < 0) {
                page = 1;
                rowsPerPage = totalRowCount;
            }

            result.setCurrentPage(page);
            result.setTotalRowCount(totalRowCount);
            result.calcPageCount(rowsPerPage);

        } catch (HibernateException e) {
            LOG.error("pageListQuery() error:" + sql, e);

        }
        return result;
    }

    /**
     * 执行分页查询
     *
     * @param sql HQL语句
     * @param page 第几页
     * @param rowsPerPage 每页记录数
     * @param totalRowCount HQL语句获得的总记录数
     * @return PageList
     */
    protected final PageList pageListQuery(final String sql, final int page,
                                           final int rowsPerPage, final int totalRowCount) {
        return pageListQuery(null, sql, page, rowsPerPage, totalRowCount);
    }

    /**
     * 执行分页查询
     *
     * @param sql HQL语句
     * @param rowsPerPage 每页记录数
     * @param page 第几页
     * @return PageList
     * @throws HibernateException hibernate 异常
     */
    protected List pageListQuery(final String sql, final int page,
                                 final int rowsPerPage) throws HibernateException {
        return pageListQuery(null, null, sql, page, rowsPerPage);
    }

    /**
     * 执行分页查询
     *
     * @param countField HQL语句中，count 的内容
     * @param sql HQL语句
     * @param rowsPerPage 每页记录数
     * @param page 第几页
     * @return PageList
     * @throws HibernateException hibernate 异常
     */
    protected List pageListQuery(final String countField, final String sql,
                                 final int page, final int rowsPerPage) throws HibernateException {
        return pageListQuery(null, countField, sql, page, rowsPerPage);
    }

    /**
     * 计算HQL查询的返回记录数
     *
     * @param sql 查询语句
     * @param countField count语句操作的字段
     * @return 记录数
     */
    protected int countHQL(String sql, String countField) {

        int rowsCount = 0;
        Session session = null;
        try {
            session = sessionFactory.getCurrentSession();
            if (null == countField) {
                countField = "*";
            }
            sql = "select count(" + countField + ") " + sql;
            rowsCount = ((Long) session.createQuery(sql).iterate().next())
                    .intValue();
        } catch (HibernateException e) {
            LOG.error("countHQL() error:" + sql, e);

        }
        return rowsCount;
    }

    /**
     * 计算HQL查询的返回记录数
     *
     * @param sql 查询语句
     * @return 记录数
     */
    protected int countHQL(String sql) {
        return countHQL(sql, null);
    }


    /**
     * 执行批量更新和删除操作的HQL
     *
     * @param sql hql语句
     * @return PageList
     */
    protected final List executeHQLByCache(final String sql) {
        Session session = null;
        List ret = null;
        try {
            session = sessionFactory.getCurrentSession();
            if (sql.toUpperCase().startsWith("DELETE")
                    || sql.toUpperCase().startsWith("UPDATE")) {
                session.createQuery(sql).executeUpdate();
            } else {
                Query query = session.createQuery(sql);
                query.setCacheable(true);
                ret = query.list();
            }
        } catch (HibernateException e) {
            LOG.error("executeHQL() error:" + sql, e);

        }
        return ret;
    }


    /**
     * 执行分页查询
     *
     * @param selectField HQL语句中，SELECT 的内容(如果设置了此参数值,则sql参数中不可带SELCT语句部分)
     * @param countField HQL语句中，count 的内容
     * @param sql HQL语句
     * @param page 第几页
     * @param rowsPerPage 每页记录数
     * @return PageList
     */
    @SuppressWarnings("unchecked")
    protected final List pageListQueryByCache(final String sql, int page, int rowsPerPage) {
        PageList result = new PageList();
        Session session = null;
        try {
            session = sessionFactory.getCurrentSession();
            // 预留count的sql语句
            String countSql = sql.substring(sql.toUpperCase().indexOf("FROM"));
            // 设置返回的列，进行查询

            if (page <= 0) {
                page = 1; // page最小为1
            }
            LOG.debug("query sql:" + sql);
            Query q = session.createQuery(sql);
            q.setCacheable(true);
            if (rowsPerPage > 0) { // rowsPerPage的值是0或-1时,都返回全部结果集
                q.setFirstResult(rowsPerPage * (page - 1));
                q.setMaxResults(rowsPerPage);
            }
            result.addAll(q.list());


            int rowsCount = result.size();
            if (rowsPerPage > 1) {
                // 每页记录数大于1且结果集大于0才计算分页信息,否则当前页记录数就作为总的记录数
                countSql = "select count(*) " + countSql;
                // 计算总记录数时,消除 Order by语句，提高性能
                int oPos = countSql.toUpperCase().indexOf("ORDER BY");
                if (oPos > 0) {
                    countSql = countSql.substring(0, oPos);
                }
                LOG.debug("count sql:" + countSql);
                //rowsCount = ((Long) session.createQuery(countSql).iterate().next()).intValue();
                q = session.createQuery(countSql);
                q.setCacheable(true);
                rowsCount = new Integer(q.list().get(0).toString());
            }

            if (0 == rowsCount) {
                page = 0;
            }
            if (rowsPerPage < 0) {
                page = 1;
                rowsPerPage = rowsCount;
            }
            result.setCurrentPage(page);
            result.setTotalRowCount(rowsCount);
            result.calcPageCount(rowsPerPage);
            if (page > result.getPageCount()) {
                page = result.getPageCount();
                q = session.createQuery(sql);
                q.setCacheable(true);
                if (rowsPerPage > 0) {
                    q.setFirstResult(rowsPerPage * (page - 1));
                    q.setMaxResults(rowsPerPage);
                }
                result.addAll(q.list());
                result.setCurrentPage(page);
                result.setTotalRowCount(rowsCount);
                result.calcPageCount(rowsPerPage);
            }

        } catch (HibernateException e) {
            LOG.error("pageListQuery() error:" + sql, e);

        }
        return result;
    }


    /**
     * 执行sql查询语句
     * @param sql SQL语句
     * @return
     */
    protected final List executeSQL(final String sql) {

        Session session = null;
        List ret = new ArrayList();
        try {
            session = sessionFactory.getCurrentSession();

            Query query = session.createSQLQuery(sql);
            ret = query.list();

        } catch (HibernateException e) {
            LOG.error("executeSQLByCache() error:" + sql, e);

        }
        return ret;
    }

    /**
     * 执行sql查询语句
     * @param sql SQL语句
     * @return
     */
    protected final void executeUpdateSQL(final String sql) {

        Session session = null;

        try {
            session = sessionFactory.getCurrentSession();

            Query query = session.createSQLQuery(sql);
            query.executeUpdate();

        } catch (HibernateException e) {
            LOG.error("executeSQLByCache() error:" + sql, e);

        }

    }

    /**
     * 执行分页查询
     *
     * @param selectField HQL语句中，SELECT 的内容(如果设置了此参数值,则sql参数中不可带SELCT语句部分)
     * @param countField HQL语句中，count 的内容
     * @param sql HQL语句
     * @param page 第几页
     * @param rowsPerPage 每页记录数
     * @return PageList
     */
    @SuppressWarnings("unchecked")
    protected final List pageListQueryBySQL(final String sql, int page, int rowsPerPage) {
        PageList result = new PageList();
        Session session = null;
        try {
            session = sessionFactory.getCurrentSession();

            String countSql = sql;


            if (page <= 0) {
                page = 1;
            }
            LOG.debug("query sql:" + sql);
            Query q = session.createSQLQuery(sql);

            if (rowsPerPage > 0) {
                q.setFirstResult(rowsPerPage * (page - 1));
                q.setMaxResults(rowsPerPage);
            }
            result.addAll(q.list());


            int rowsCount = result.size();
            if (rowsPerPage > 1) {
                countSql = "select count(*) from ( " + countSql + " ) a ";
                q = session.createSQLQuery(countSql);
                rowsCount = new Integer(q.list().get(0).toString());
            }

            if (0 == rowsCount) {
                page = 0;
            }
            if (rowsPerPage < 0) {
                page = 1;
                rowsPerPage = rowsCount;
            }
            result.setCurrentPage(page);
            result.setTotalRowCount(rowsCount);
            result.calcPageCount(rowsPerPage);
            if (page > result.getPageCount()) {
                page = result.getPageCount();
                q = session.createSQLQuery(sql);

                if (rowsPerPage > 0) {
                    q.setFirstResult(rowsPerPage * (page - 1));
                    q.setMaxResults(rowsPerPage);
                }
                result.addAll(q.list());
                result.setCurrentPage(page);
                result.setTotalRowCount(rowsCount);
                result.calcPageCount(rowsPerPage);
            }

        } catch (HibernateException e) {
            LOG.error("pageListQuery() error:" + sql, e);

        }
        return result;
    }


    /**
     * 执行分页查询
     *
     * @param page 第几页
     * @param pageSize 每页记录数
     * @param query 查询对象
     * @return Page
     */

    @SuppressWarnings("unchecked")
    protected Page pageQueryBySQL(Query q, int currentPage, int pageSize) {
        PageList result = new PageList();
        try {
            if (currentPage <= 0) {
                currentPage = 1;
            }
            int rowsCount = q.list().size();
            if (pageSize > 0) {
                q.setFirstResult(pageSize * (currentPage - 1));
                q.setMaxResults(pageSize);
            }
            result.addAll(q.list());
            if (0 == rowsCount) {
                currentPage = 0;
            }
            if (pageSize < 0) {
                currentPage = 1;
                pageSize = rowsCount;
            }
            result.setCurrentPage(currentPage);
            result.setTotalRowCount(rowsCount);
            result.calcPageCount(pageSize);

            //当前页码大于总页码的时候
            if (currentPage > result.getPageCount()) {
                currentPage = result.getPageCount();

                if (pageSize > 0) {
                    q.setFirstResult(pageSize * (currentPage - 1));
                    q.setMaxResults(pageSize);
                }
                result.addAll(q.list());
                result.setCurrentPage(currentPage);
                result.setTotalRowCount(rowsCount);
                result.calcPageCount(pageSize);
            }

        } catch (HibernateException e) {
            e.printStackTrace();
        }
        Page tempPage = new Page(result, result.getTotalRowCount(), result.getPageCount(), new Integer(currentPage), new Integer(pageSize));

        return tempPage;
    }

    @SuppressWarnings("unchecked")
    protected Page pageQueryBySQL2(Query q, int currentPage, int pageSize) {
        PageList result = new PageList();
        try {
            if (currentPage <= 0) {
                currentPage = 1;
            }
            int rowsCount = q.list().size();
            if (pageSize > 0) {
                q.setFirstResult(pageSize * (currentPage - 1));
                q.setMaxResults(pageSize);
            }
            result.addAll(q.list());
            if (0 == rowsCount) {
                currentPage = 0;
            }
            if (pageSize < 0) {
                currentPage = 1;
                pageSize = rowsCount;
            }
            result.setCurrentPage(currentPage);
            result.setTotalRowCount(rowsCount);
            result.calcPageCount(pageSize);

            //当前页码大于总页码的时候
            if (currentPage > result.getPageCount()) {
                currentPage = result.getPageCount();

                if (pageSize > 0) {
                    q.setFirstResult(pageSize * (currentPage - 1));
                    q.setMaxResults(pageSize);
                }
                result.addAll(q.list());
                result.setCurrentPage(currentPage);
                result.setTotalRowCount(rowsCount);
                result.calcPageCount(pageSize);
            }

        } catch (HibernateException e) {
        }
        Page tempPage = new Page(result, result.getTotalRowCount(), result.getPageCount(), new Integer(currentPage), new Integer(pageSize));

        return tempPage;
    }

    /**
     * 执行分页查询
     *
     * @param page 第几页
     * @param pageSize 每页记录数
     * @param query 查询对象
     * @return Page
     */

    @SuppressWarnings("unchecked")
    protected Page pageQueryBySQL(Query q, String countSql, int currentPage, int pageSize) {
        PageList result = new PageList();
        try {
            if (currentPage <= 0) {
                currentPage = 1;
            }
            int rowsCount = this.countHQL(countSql);
            if (pageSize > 0) {
                q.setFirstResult(pageSize * (currentPage - 1));
                q.setMaxResults(pageSize);
            }
            result.addAll(q.list());
            if (0 == rowsCount) {
                currentPage = 0;
            }
            if (pageSize < 0) {
                currentPage = 1;
                pageSize = rowsCount;
            }
            result.setCurrentPage(currentPage);
            result.setTotalRowCount(rowsCount);
            result.calcPageCount(pageSize);

            //当前页码大于总页码的时候
            if (currentPage > result.getPageCount()) {
                currentPage = result.getPageCount();

                if (pageSize > 0) {
                    q.setFirstResult(pageSize * (currentPage - 1));
                    q.setMaxResults(pageSize);
                }
                result.addAll(q.list());
                result.setCurrentPage(currentPage);
                result.setTotalRowCount(rowsCount);
                result.calcPageCount(pageSize);
            }

        } catch (HibernateException e) {
            e.printStackTrace();
        }
        Page tempPage = new Page(result, result.getTotalRowCount(), result.getPageCount(), new Integer(currentPage), new Integer(pageSize));

        return tempPage;
    }


    /**
     * 执行分页查询
     *
     * @param selectField HQL语句中，SELECT 的内容(如果设置了此参数值,则sql参数中不可带SELCT语句部分)
     * @param countField HQL语句中，count 的内容
     * @param sql SQL语句
     * @param page 第几页
     * @param rowsPerPage 每页记录数
     * @return PageList
     */
    @SuppressWarnings("unchecked")
    protected Page pageQueryBySQL(final String sql, int currentPage, int pageSize) {
        PageList result = new PageList();
        Session session = null;
        try {
            session = sessionFactory.getCurrentSession();

            String countSql = sql;


            if (currentPage <= 0) {
                currentPage = 1;
            }
            LOG.debug("query sql:" + sql);
            Query q = session.createSQLQuery(sql);

            if (pageSize > 0) {
                q.setFirstResult(pageSize * (currentPage - 1));
                q.setMaxResults(pageSize);
            }
            result.addAll(q.list());


            int rowsCount = result.size();
            if (pageSize > 1) {
                countSql = "select count(*) from ( " + countSql + " ) a ";
                q = session.createSQLQuery(countSql);
                rowsCount = new Integer(q.list().get(0).toString());
            }

            if (0 == rowsCount) {
                currentPage = 0;
            }
            if (pageSize < 0) {
                currentPage = 1;
                pageSize = rowsCount;
            }
            result.setCurrentPage(currentPage);
            result.setTotalRowCount(rowsCount);
            result.calcPageCount(pageSize);
            if (currentPage > result.getPageCount()) {
                currentPage = result.getPageCount();
                q = session.createSQLQuery(sql);

                if (pageSize > 0) {
                    q.setFirstResult(pageSize * (currentPage - 1));
                    q.setMaxResults(pageSize);
                }
                result.addAll(q.list());
                result.setCurrentPage(currentPage);
                result.setTotalRowCount(rowsCount);
                result.calcPageCount(pageSize);
            }

        } catch (HibernateException e) {
            LOG.error("pageListQuery() error:" + sql, e);

        }
        Page tempPage = new Page(result, result.getTotalRowCount(), result.getPageCount(), new Integer(currentPage), new Integer(pageSize));

        return tempPage;
    }

    /**
     * 获取查询对象
     * @param sql
     * @return
     */
    protected Query getQuery(String sql) {
        Session session = null;
        Query q = null;
        try {
            session = sessionFactory.getCurrentSession();
            q = session.createSQLQuery(sql);

        } catch (Exception e) {

        }
        return q;
    }


    /**
     * 执行分页查询
     *
     * @param sql HQL语句
     * @param page 第几页
     * @param rowsPerPage 每页记录数
     * @param totalRowCount HQL语句获得的总记录数
     * @return PageList
     */
    protected final Page<T> pageQueryByHql(final String sql, final int page,
                                           final int rowsPerPage) {
        PageList pageList = (PageList) pageListQuery(sql, page, rowsPerPage);
        ;
        Page<T> tempPage = new Page<T>(pageList, pageList.getTotalRowCount(), pageList.getPageCount(), new Integer(page), new Integer(rowsPerPage));
        return tempPage;

    }


    /**
     * 根据实体类与ID获得对象
     *
     * @param clazz
     *            实体类
     * @param id
     *            主键ID
     */
    public <X> X get(final Class<X> clazz, final Serializable id) {
        return (X) sessionFactory.getCurrentSession().get(clazz, Long.parseLong(id.toString()));
        //return (X) sessionFactory.getCurrentSession().load(clazz, test);
    }

    /**
     * 根据实体类与ID获得对象
     *
     * @param clazz
     *            实体类
     * @param id
     *            主键ID
     */
    public <X> X get(final Class<X> clazz, int id) {
        return (X) sessionFactory.getCurrentSession().get(clazz, id);
        //return (X) sessionFactory.getCurrentSession().load(clazz, test);
    }

    /**
     * 根据id获得对象
     *
     * @param id
     *            主键ID
     */
    public T get(Serializable id) {
        return this.get(entityClass, id);
    }

    /**
     * 删除对象
     *
     * @param entity
     *            实体类
     */
    public void delete(final Object entity) {
        this.sessionFactory.getCurrentSession().delete(entity);
    }

    /**
     * 根据ID删除对象
     *
     * @param id
     *            主键ID
     */
    public void delete(final Serializable id) {
        delete(get(id));
    }

    /**
     * 根据实体类与ID删除对象
     *
     * @param clazz
     *            实体类
     * @param id
     *            主键ID
     */
    public void delete(final Class clazz, final Serializable id) {
        delete(get(clazz, id));
    }

    /**
     * 保存对象
     *
     * @param entity
     *            保存的实体对象
     */
    public void save(final Object entity) {
        this.sessionFactory.getCurrentSession().save(entity);
    }

    /**
     * 更新保存实体对象
     * @param entity
     */
    public void saveOrUpdate(final Object entity) {
        this.sessionFactory.getCurrentSession().saveOrUpdate(entity);
        //this.sessionFactory.getCurrentSession().merge(entity);
    }

    /**
     * 更新保存实体对象
     * @param entity
     */
    @Override
    public void saveOrUpdateEntity(final Object entity) {
        this.sessionFactory.getCurrentSession().saveOrUpdate(entity);
        //this.sessionFactory.getCurrentSession().merge(entity);
    }

    /**
     * 更新对象
     *
     * @param entity
     */
    public void update(final Object entity) {
        this.sessionFactory.getCurrentSession().update(entity);
    }

    /**
     * 动态更新对象更新对象
     *
     * @param entity
     */
    public void DynamicUpdate(Object obj) {
        Class class1 = obj.getClass();
        Field[] field1 = class1.getDeclaredFields();
        for (Field f : field1) {

        }
        this.sessionFactory.getCurrentSession().update(obj);
    }

    /**
     * 获取所有数据
     *
     * @param entityClass
     *            参数T的反射类型
     */
    public <X> List<X> getAll(final Class<X> entityClass) {
        return createCriteria(entityClass).list();
    }

    /**
     * 获取所有数据
     */
    public List<T> getAll() {
        return query();
    }

    /**
     * 根据条件获取数据
     *
     * @param criterions
     *            数量可变的Criterion
     */
    public List<T> query(final Criterion... criterions) {
        return createCriteria(criterions).list();
    }


    /**
     * HQL方式查询
     *
     * @param hql
     *            符合HQL语法的查询语句
     * @param values
     *            数量可变的条件值,按顺序绑定
     */
    public Query createQuery(final String hql, final Object... values) {
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        if (values != null) {
            for (int i = 0; i < values.length; i++) {
                query.setParameter(i, values[i]);
            }
        }
        return query;
    }

    /**
     * SQL方式查询
     *
     * @param sql
     *            符合SQL语法的查询语句
     * @param values
     *            数量可变的条件值,按顺序绑定
     */
    public SQLQuery createSQLQuery(final String sql, final Object... values) {

        SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
        if (values != null) {
            for (int i = 0; i < values.length; i++) {
                query.setParameter(i, values[i]);
            }
        }
        return query;
    }

    /**
     * 根据类型创建查询对象
     *
     * @param clazz
     *            类型
     */
    public Criteria createCriteria(final Class clazz) {
        return sessionFactory.getCurrentSession().createCriteria(clazz);
    }

    /**
     * 对象化查询
     *
     * @param entityClass
     *            参数T的反射类型
     * @param criterions
     *            数量可变的Criterion
     */
    public Criteria createCriteria(final Class clazz,
                                   final Criterion... criterions) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(clazz);
        for (Criterion c : criterions) {
            criteria.add(c);
        }
        return criteria;
    }

    /**
     * 对象化查询
     *
     * @param criterions
     *            数量可变的Criterion
     */
    public Criteria createCriteria(final Criterion... criterions) {
        return createCriteria(entityClass, criterions);
    }


    public Session getSession() {
        Session session = sessionFactory.getCurrentSession();

        return session;
    }

    public Session getOpenSession() {
        Session session = sessionFactory.openSession();

        return session;
    }


    public Connection getConnection() throws Exception {

        Session session = sessionFactory.getCurrentSession();
        Connection conn = null;
        try {
            conn = (Connection) SessionFactoryUtils.getDataSource(sessionFactory).getConnection();
        } catch (SQLException e) {

            e.printStackTrace();
        }
        return conn;

    }

    @Transactional
    void batchSaveOrUpdate(List<T> entitys, int batchSize) {

        Session session = this.sessionFactory.getCurrentSession();
        for (int i = 0; i < entitys.size(); i++) {
            session.saveOrUpdate(entitys.get(i));

            if (i % batchSize == 0 || i == entitys.size() - 1) {
                session.flush();
                session.clear();
            }
        }

    }

    @Transactional
    void batchSql(List<String> sqls, int batchSize) {

        Session session = this.sessionFactory.getCurrentSession();

        for (int i = 0; i < sqls.size(); i++) {

            session.createSQLQuery(sqls.get(i)).executeUpdate();

            if (i % batchSize == 0 || i == sqls.size() - 1) {
                session.flush();
                session.clear();
            }
        }

    }

}
