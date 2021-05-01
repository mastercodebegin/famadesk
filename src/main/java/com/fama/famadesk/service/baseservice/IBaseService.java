package com.fama.famadesk.service.baseservice;

import java.util.List;

public interface IBaseService<Entity> {
		Entity create(Entity anEntity);
		Entity update(Entity anEntity);
		List<Entity> findAll(); 
		Entity findByPk(Integer entityPk);
	


}
