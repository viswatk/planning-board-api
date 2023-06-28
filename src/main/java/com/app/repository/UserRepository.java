package com.app.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.app.config.WriteableRepository;
import com.app.entity.User;

public interface UserRepository extends WriteableRepository<User, Integer> {

    @Query("SELECT s FROM User s WHERE s.id = :id")
    Optional<User> findUserById(@Param("id") Integer id);
    
    @Query("SELECT u FROM User u where u.userName = ?#{principal?.phoneNo}")
    public Optional<User> findLoggedInUser();

    public Optional<User> findByUserName(String userName);
    
    public Optional<User> findByUserNameOrEmail(String value1,String value2);

	public Optional<User> findByUserNameAndPasswordAndIsDeletedFalseAndIsLockedFalse(String userName,String encryptedPassword);

	
}
