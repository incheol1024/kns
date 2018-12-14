package com.devworker.kms.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public abstract class CommonUtil {
	private CommonUtil() {}
	private static final String ANONYMOUS = "Anonymous";
	
	public static <T> List<T> iterToList(Iterable<T> iter){
		List<T> list = new ArrayList<>();
		for(T t : iter) {
			list.add(t);
		}
		return list;
	}	
	
	public static String getCurrentUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
		    return authentication.getName();
		}
		return ANONYMOUS;
	}
}
