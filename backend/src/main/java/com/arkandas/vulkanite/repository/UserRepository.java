package com.arkandas.vulkanite.repository;

import com.arkandas.vulkanite.model.db.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    @Query("select new com.arkandas.vulkanite.model.db.User(u.userId, u.userEmail, u.username, u.userfullname, u.password, u.userType) from user u where u.userEmail = :userEmail")
    User getUserWithEmail(@Param("userEmail") String userEmail);

    @Query("select new com.arkandas.vulkanite.model.db.User(u.userId, u.userEmail, u.username, u.userfullname, u.password, u.userType) from user u where u.userId = :userId")
    User getUserById(@Param("userId") User userId);

    @Query("select new com.arkandas.vulkanite.model.db.User(u.userId, u.userEmail, u.username, u.userfullname, u.password, u.userType) from user u where u.username = :userName")
    User findByUserName(@Param("userName") String userName);

}