package com.intuit.ihg.common.utils.dataprovider;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Created by IntelliJ IDEA.
 * User: vvalsan
 * Date: 3/20/13
 * Time: 5:05 PM
 * To change this template use File | Settings | File Templates.
 */
public class Filter {
    	// //////////////// Enum /////////////
	static enum Operator {
		EqualsIgnoreCase,And;
	}

	public static Filter and(Filter left, Filter right) {
		return new Filter(left, right, Operator.And);
	}

	public static Filter equalsIgnoreCase(String name, String value) {
		return new Filter(name, value, Operator.EqualsIgnoreCase);
	}


	private static boolean match(Filter filter,
			Map<String, Object> parameters) {
		String name = (filter.name != null ? filter.name.toUpperCase() : null);
		Object values[] = filter.values;
		Operator operator = filter.operator;
		Filter left = filter.left;
		Filter right = filter.right;

		if (Operator.And.equals(operator)) {
			return left.match(parameters) && right.match(parameters);
		} else if (!parameters.containsKey(name)) {
			return false;
		} else if (Operator.EqualsIgnoreCase
				.equals(operator)
				&& (values == null || (values.length == 1 && values[0] == null))) {
			return (parameters.get(name) == null);
		} else if (Operator.EqualsIgnoreCase.equals(operator)) {
			return parameters.get(name).toString().toLowerCase()
					.equals(values[0].toString().toLowerCase());
		} else if (values == null || values[0] == null) {
			throw new RuntimeException(
					"Null values are not supported for Filter Operation: "
							+ operator);
		} else {// By the time the control reaches here values is not null

	}

		throw new RuntimeException("Should not reach here. Not Implemented Yet"
				+ "\n" + filter + "\n" + parameters);
	}

	private String name;

	private Object[] values;

	private Operator operator;

	private Filter left;

	private Filter right;

	private Filter(Filter left, Filter right, Operator condition) {
		this.left = left;
		this.right = right;
		this.operator = condition;
	}

	public Filter(String name, Object value, Operator condition) {
		this(name, new Object[] { value }, condition);
	}

	public Filter(String name, Object[] values, Operator condition) {
		super();
		this.name = name;
		this.values = values;
		this.operator = condition;
	}

	public Filter getLeft() {
		return left;
	}

	public String getName() {
		return name;
	}

	public Operator getOperator() {
		return operator;
	}

	public Filter getRight() {
		return right;
	}

	public Object[] getValues() {
		return values;
	}

	public boolean match(Map<String, Object> parameters) {
		Map<String, Object> parameters2 = new HashMap<String, Object>();
		for (Entry<String, Object> entry : parameters.entrySet()) {
			parameters2.put(entry.getKey().toUpperCase(), entry.getValue());
		}
		return match(this, parameters2);
	}

	public void setLeft(Filter left) {
		this.left = left;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setOperator(Operator condition) {
		this.operator = condition;
	}

	public void setRight(Filter right) {
		this.right = right;
	}

	public void setValues(Object[] values) {
		this.values = values;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		if (name != null) {
			sb.append(name + " " + operator.toString() + " "
					+ Arrays.toString(values));
		} else {
			sb.append((left != null ? left.toString() : "") + " "
					+ operator.toString() + " " + right.toString());
		}

		return "(" + sb.toString() + ")";
	}

}
