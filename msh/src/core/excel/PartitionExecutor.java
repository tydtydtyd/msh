package core.excel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Tang Yong Di
 * @date 2016/2/5
 */
public class PartitionExecutor {

	public static <T extends Partition> void execute(List<T> list, PartitionCallback<T> callback) {
		execute(list, callback, 100);
	}

	public static <T extends Partition> void execute(List<T> list, PartitionCallback<T> callback, int partSize) {
		if (list.size() > partSize) {
			List<T> partList = new ArrayList<>();
			int i = 1;
			for (T result : list) {
				partList.add(result);
				if (i % partSize == 0 || i == list.size()) {
					callback.execute(partList);
					try {
						Thread.sleep(10);
					} catch (Exception ignored) {
						// continue
					} finally {
						partList.clear();
					}
				}
				i++;
			}
		} else {
			callback.execute(list);
		}
	}

	public static class Test implements Partition {
		private Integer value;

		public Test(Integer value) {
			this.value = value;
		}

		@Override
		public String toString() {
			return String.valueOf(value);
		}
	}

	public static void main(String[] args) {
		List<Test> list = new ArrayList<>();
		for (int i = 0; i < 1099; i++) {
			list.add(new Test(i));
		}
		PartitionExecutor.execute(list, new PartitionCallback<Test>() {
			@Override
			public void execute(List<Test> partList) {
				System.out.println(Arrays.toString(partList.toArray()));
			}
		}, 9);
	}
}

