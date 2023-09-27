package com.shp.fms.auth;

import org.springframework.data.repository.CrudRepository;

public interface RefreshRedisRepository extends CrudRepository<RefreshToken, String> {

	RefreshToken findByRefreshToken(String token);

}
