import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MonitorMemory {
	static Runtime r = Runtime.getRuntime();
	public static void main(String[] args) {
		showMemory();
	}

	public static void showMemory() {
		DecimalFormat format = new DecimalFormat("###,###,###.##");

		List<Map<String, Object>> list = new ArrayList<>();
		for(int i=0; i<100000; i++) {
			Map<String, Object> map = new HashMap<>();
			map.put("key", i);
			map.put("value", "cjsxowls"+i);
		}
		
		//JVM이 현재 시스템에 요구 가능한 최대 메모리량, 이 값을 넘으면 OutOfMemory 오류가 발생 합니다.               
		long max = r.maxMemory();

		//JVM이 현재 시스템에 얻어 쓴 메모리의 총량
		long total = r.totalMemory();
		
		//JVM이 현재 시스템에 청구하여 사용중인 최대 메모리(total)중에서 사용 가능한 메모리
		long free = r.freeMemory();

		System.out.println("Max:" + format.format(max) + ", Total:" + format.format(total) + ", Free:"+format.format(free));          
	}
}