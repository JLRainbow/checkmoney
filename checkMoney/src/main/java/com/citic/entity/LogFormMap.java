package com.citic.entity;

import com.citic.annotation.TableSeg;
import com.citic.util.FormMap;



/**
 * 实体表
 */
@TableSeg(tableName = "t_log", id="id")
public class LogFormMap extends FormMap<String,Object>{

	private static final long serialVersionUID = 1L;
}
