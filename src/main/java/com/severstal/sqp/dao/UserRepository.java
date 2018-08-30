/**
 * ***********************************************************************
 * Copyright (c) 2017, SAP <sap.com>
 *
 * All portions of the code written by SAP are property of SAP.
 * All Rights Reserved.
 *
 * SAP
 *
 * Moscow, Russian Federation
 *
 * Web: sap.com
 * ***********************************************************************
 */
package com.severstal.sqp.dao;

import org.springframework.data.repository.CrudRepository;

import com.severstal.sqp.entity.User;

/**
 * @author AUTHOR <AUTHOR@sap.com>
 * @package com.severstal.sqp.dao.impl
 * @link http://sap.com/
 * @copyright 2017 SAP
 */
public interface UserRepository extends CrudRepository<User, Long> {


}
