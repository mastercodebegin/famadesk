package com.fama.famadesk.dao.basedao;
import java.util.List;

public interface IBaseDao<Entity>
{
	Entity create(Entity anEntity);
	Entity update(Entity anEntity);
	Entity findByPk(Integer entityPk);
	List<Entity> findAll(); 
}
