package com.hillspet.wearables.service.user;

import org.springframework.stereotype.Service;

import com.hillspet.wearables.common.exceptions.ServiceExecutionException;
import com.hillspet.wearables.dto.User;
import com.hillspet.wearables.dto.filter.UserFilter;
import com.hillspet.wearables.request.UpdatePasswordRequest;
import com.hillspet.wearables.response.UsersResponse;

@Service
public interface UserService {

	void addUser(User user) throws ServiceExecutionException;

	void updateUser(User user) throws ServiceExecutionException;
	
	void updateUserProfile(User user)throws ServiceExecutionException;

	User getUserByEmailId(String emailId) throws ServiceExecutionException;

	User getUserById(int userId) throws ServiceExecutionException;

	UsersResponse getAllUsers(UserFilter filter) throws ServiceExecutionException;

	void deleteUser(int userId, int modifiedBy);

	void performForgotPasswordTask(String userName, int modifiedBy, String platform) throws ServiceExecutionException;

	void updatePassword(UpdatePasswordRequest updatePasswordRequest, String platform) throws ServiceExecutionException;
	
}
