package com.hillspet.wearables.dao.user;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.hillspet.wearables.common.exceptions.ServiceExecutionException;
import com.hillspet.wearables.dto.User;
import com.hillspet.wearables.dto.filter.BaseFilter;
import com.hillspet.wearables.dto.filter.UserFilter;

public interface UserDao {

	User addUser(User user) throws ServiceExecutionException;

	User updateUser(User user) throws ServiceExecutionException;

	void updateUserPassword(int userId, String userName, String password, boolean needChangePassword, int modifiedBy,
			String platform);

	void deleteUser(int userId, int modifiedBy) throws ServiceExecutionException;

	Optional<User> findByUsername(String userName) throws ServiceExecutionException;

	User getUserById(int userId) throws ServiceExecutionException;

	Map<String, Integer> getUserCount(UserFilter filter) throws ServiceExecutionException;

	List<User> getAllUsers(UserFilter filter) throws ServiceExecutionException;

	List<String> getPasswordHistoryById(int userId);

	User updateUserProfile(User user) throws ServiceExecutionException;

}
