package com.sb.camp.persistence;

import java.util.List;

public interface GenericDao<T, PK> {
	public List<T> selectAll();
	public T findById(PK id);
	public int insert(T vo);
	public int update(T vo);
	public int delete(PK id);
}
