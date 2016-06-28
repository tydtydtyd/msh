package core.excel;

import net.sf.jxls.transformer.XLSTransformer;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Tang Yong Di
 * @date 2016/2/5
 */
public class MultipleSheetsList<T extends Partition> {
	private static final Logger log = Logger.getLogger(MultipleSheetsList.class);

	/**
	 * {@link InputStream} for source XLS template file
	 */
	private InputStream is = null;
	/**
	 * List of beans where each list item should be exported into a separated worksheet
	 */
	private List<List<T>> objects = new ArrayList<>();
	/**
	 * Sheet names to be used for newly created worksheets
	 */
	private List<String> newSheetNames = new ArrayList<>();
	/**
	 * Bean name to be used for a list item when processing sheet
	 */
	private String beanName = "data";
	/**
	 * Common bean map containing all other objects to be used in the workbook
	 */
	private Map<String, Object> beanParams = new HashMap<>();
	/**
	 * Worksheet number (zero based) of the worksheet that needs to be used to create multiple worksheets
	 */
	private int startSheetNum = 0;

	private OutputStream os = new ByteArrayOutputStream();

	public MultipleSheetsList(String templatePath, String beanName) throws FileNotFoundException {
		this.is = new BufferedInputStream(new FileInputStream(templatePath));
		this.beanName = beanName;
	}

	public MultipleSheetsList(InputStream is, String beanName) {
		this.is = is;
		this.beanName = beanName;
	}

	public void store() throws Exception {
		storeTo(os);
	}

	public void storeTo(OutputStream os) throws Exception {
		log.info("start transform xls file...");
		long start = System.currentTimeMillis();
		XLSTransformer transformer = new XLSTransformer();
		Workbook workbook = transformer.transformMultipleSheetsList(is, objects, newSheetNames, beanName, beanParams, startSheetNum);
		workbook.write(os);
		is.close();
		os.flush();
		os.close();
		long end = System.currentTimeMillis();
		log.info("transform completed in " + (end - start) + " ms.");
	}

	public void addSheet(String sheetName, List<T> sheetData) {
		this.newSheetNames.add(sheetName);
		this.objects.add(sheetData);
	}

	public void addBeanParams(Map<String, Object> beanParams) {
		this.beanParams.putAll(beanParams);
	}

	public void setObjects(List<String> newSheetNames, List<List<T>> objects) {
		this.objects = objects;
		this.newSheetNames = newSheetNames;
	}

	public void setBeanName(String beanName) {
		this.beanName = beanName;
	}

	public void setStartSheetNum(int startSheetNum) {
		this.startSheetNum = startSheetNum;
	}

	public InputStream getIs() {
		return is;
	}

	public List getObjects() {
		return objects;
	}

	public List getNewSheetNames() {
		return newSheetNames;
	}

	public String getBeanName() {
		return beanName;
	}

	public Map getBeanParams() {
		return beanParams;
	}

	public int getStartSheetNum() {
		return startSheetNum;
	}

	public OutputStream getOs() {
		return os;
	}
}