package liuxuewei.ocr;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import liuxuewei.init.SYS;

public class HandleSimilarStr {
	public String getString(String str) {
		try {
			HashMap<String, String> likeCharMap = SYS.likeCharMap;
			str = str.toLowerCase();
			Iterator<?> iter = likeCharMap.entrySet().iterator();
			while (iter.hasNext()) {
				@SuppressWarnings("rawtypes")
				Map.Entry entry = (Map.Entry) iter.next();
				String key = entry.getKey().toString().toLowerCase();
				Object obj = entry.getValue();
				String val = "";
				if (obj instanceof String[]) {
					String[] strs = (String[]) obj;
					val = strs[0].trim();
				} else {
					val = obj.toString().trim();
				}
				str = str.replace(key, val);
			}
		} catch (Exception e) {

		}
		return str;
	}
}
