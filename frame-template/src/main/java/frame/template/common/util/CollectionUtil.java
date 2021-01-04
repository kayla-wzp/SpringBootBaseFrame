package frame.template.common.util;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@SuppressWarnings("rawtypes")
public class CollectionUtil {
	public static boolean isEmpty(Collection collection) {
		return CollectionUtils.isEmpty(collection);
	}

	public static boolean isNotEmpty(Collection collection) {
		return CollectionUtils.isNotEmpty(collection);
	}

	public static boolean isEmpty(Map map) {
		return MapUtils.isEmpty(map);
	}

	public static boolean isNotEmpty(Map map) {
		return MapUtils.isNotEmpty(map);
	}

	public static String getStringValue(Map map, String key) {
		return MapUtils.getString(map, key);
	}

	public static String getStringValue(Map map, String key, String defaultValue) {
		return MapUtils.getString(map, key, defaultValue);
	}

	public static long getLongValue(Map map, String key) {
		return MapUtils.getLongValue(map, key);
	}

	public static long getLongValue(Map map, String key, long defaultValue) {
		return MapUtils.getLongValue(map, key, defaultValue);
	}

	public static int getIntValue(Map map, String key) {
		return MapUtils.getIntValue(map, key);
	}

	public static int getIntValue(Map map, String key, int defaultValue) {
		return MapUtils.getIntValue(map, key, defaultValue);
	}

	public static Object getValue(Map map, String key) {
		return MapUtils.getObject(map, key);
	}

	public static Object geValue(Map map, String key, Object defaultValue) {
		return MapUtils.getObject(map, key, defaultValue);
	}

	public static boolean contain(String[] arr, String o) {
		if (arr == null || arr.length == 0 || o == null) {
			return false;
		}
		for (String obj : arr) {
			if (o.equals(obj)) {
				return true;
			}
		}
		return false;
	}

	public static boolean notContain(String[] arr, String o) {
		return !contain(arr, o);
	}

	public static boolean containIgnoreCase(String[] arr, String o) {
		if (arr == null || arr.length == 0 || o == null) {
			return false;
		}
		for (String obj : arr) {
			if (o.equalsIgnoreCase(obj)) {
				return true;
			}
		}
		return false;
	}

	public static boolean notContainIgnoreCase(String[] arr, String o) {
		return !containIgnoreCase(arr, o);
	}

	public static boolean contain(int[] arr, int o) {
		if (arr == null || arr.length == 0) {
			return false;
		}
		for (int obj : arr) {
			if (o == obj) {
				return true;
			}
		}
		return false;
	}

	public static boolean notContain(int[] arr, int o) {
		return !contain(arr, o);
	}

	public static List<String> removeDup(List<String> list) {
		return new ArrayList<>(new HashSet<>(list));
	}


	public static <T> List<List<T>> group(List<T> list, int batchSize) {
		if (!isEmpty((Collection)list) && batchSize >= 1) {
			if (batchSize == 1) {
				List<List<T>> outerList = new ArrayList();
				outerList.add(list);
				return outerList;
			} else {
				Map<Integer, List<T>> map = new TreeMap();

				for(int i = 0; i < list.size(); ++i) {
					T str = list.get(i);
					Integer key = i / batchSize;
					List<T> innerList = (List)map.get(key);
					if (innerList == null) {
						innerList = new ArrayList();
						map.put(key, innerList);
					}

					((List)innerList).add(str);
				}

				return new ArrayList(map.values());
			}
		} else {
			return null;
		}
	}
}
