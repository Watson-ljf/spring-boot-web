package com.watson.core.auth;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.watson.system.model.Permission;
import com.watson.system.model.User;
import com.watson.system.service.PermissionService;
import com.watson.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import com.wf.etp.authz.IUserRealm;
import com.wf.etp.authz.SubjectUtil;
import org.springframework.stereotype.Service;

@Service
public class UserRealm extends IUserRealm {
	@Autowired
	private UserService userService;
	@Autowired
	private PermissionService permissionService;

	@Override
	public Set<String> getUserRoles(String userId) {
		Set<String> roles = new HashSet<>();
		User user = userService.getUserById(userId);
		if(user != null){
			roles.add(user.getRoleId());
		}
		return roles;
	}

	@Override
	public Set<String> getUserPermissions(String userId) {
		Set<String> permissionValues = new HashSet<>();
		List<String> userRoles = SubjectUtil.getInstance().getUserRoles(userId);
		if(userRoles.size()>0){
			List<Permission> permissions = permissionService.getPermissionsByRole(userRoles.get(0));
			for (int i = 0; i < permissions.size(); i++) {
				String permissionValue = permissions.get(i).getPermissionValue();
				if(permissionValue!=null && !permissionValue.isEmpty()){
					permissionValues.add(permissionValue);
				}
			}
		}
		return permissionValues;
	}
}