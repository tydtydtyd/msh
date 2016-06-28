package core.excel;

import java.util.List;

/**
 * @author Tang Yong Di
 * @date 2016/2/5
 */
public interface PartitionCallback<T extends Partition> {

	void execute(List<T> partList);
}
