/**
 * BaseDAO.java
 *
 * Copyright 2007 easou, Inc. All Rights Reserved.
 */
package com.wechat.dao;


/**
 * TODO Revision History 2007-10-30,norby_easou,created it
 */
public interface BaseDao {

    /**
     * 根据主键获得实体
     * @param className 实体
     * @param id 实体主键
     * @return BaseEntity
     */
    Object getEntity(Class className, Integer id);

    

    /**
     * 保存实体
     * 
     * @param entity pojo instance
     */
    void saveEntity(Object entity);

   
    
    /**
     * 删除实体
     * @param o
     */
    void removeEntity(Object o); 
    
	/**
	 * 更新保存实体对象
	 * @param entity
	 */
	 void saveOrUpdateEntity(final Object entity);
	 


}
