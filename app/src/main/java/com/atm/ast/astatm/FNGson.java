package com.atm.ast.astatm;

import com.atm.ast.astatm.exception.FNExceptionUtil;
import com.atm.ast.astatm.utils.FNObjectUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Map;

/**
 * @author AST Inc.
 */
public class FNGson {

	static FNGson instance;
	Gson gson;
	Gson gsonExcAnnotation;

	private FNGson() {
		this.init();
	}

	public static FNGson store() {
		if (instance == null) {
			instance = new FNGson();
		}
		return instance;
	}

	private void init() {
		this.gson = new GsonBuilder().create();
	}

	public String toJson(Object obj) {
		return this.gson.toJson(obj);
	}

	public <T> T getObject(Class<T> entity, Object response) {
		if (FNObjectUtil.isEmpty(response)) {
			return null;
		}
		try {
			String jsonString = response instanceof Map ? this.toJson(response) : FNJSonHelper.toJSON(response).toString();
			return FNObjectUtil.isNonEmptyStr(jsonString) ? this.gson.fromJson(jsonString, entity) : null;
		} catch (Exception e) {
			FNExceptionUtil.logException(e, ApplicationHelper.application().getContext(), true);
			return null;
		}
	}

	public <T> ArrayList<T> getList(Class<T> entity, Object response) {
		if (response == null) {
			return new ArrayList<>();
		}
		ArrayList<T> returnList = new ArrayList<>();
		try {
			String jsonsString = response instanceof Iterable ? this.toJson(response) : FNJSonHelper.toJSON(response).toString();
			ArrayList<Object> list = this.gson.fromJson(jsonsString, new TypeToken<ArrayList<T>>() {
			}.getType());
			for (Object obj : list) {
				returnList.add(this.getObject(entity, obj));
			}
		} catch (Exception e) {
			FNExceptionUtil.logException(e, ApplicationHelper.application().getContext(), true);
		}
		return returnList;
	}

	public <T> ArrayList<T> getList(Object response) {
		if (response == null) {
			return new ArrayList<>();
		}
		try {
			return this.gson.fromJson(FNJSonHelper.toJSON(response).toString(), new TypeToken<ArrayList<T>>() {
			}.getType());
		} catch (Exception e) {
			FNExceptionUtil.logException(e, ApplicationHelper.application().getContext(), true);
			return null;
		}
	}

}
