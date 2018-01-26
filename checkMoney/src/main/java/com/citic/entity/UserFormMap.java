package com.citic.entity;

import com.citic.annotation.TableSeg;
import com.citic.util.FormMap;



/**
 * user实体表
 */
@TableSeg(tableName = "t_user", id="id")
public class UserFormMap extends FormMap<String,Object>{

	private static final long serialVersionUID = 1L;

}
