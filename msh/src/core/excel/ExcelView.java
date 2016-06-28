package core.excel;

import net.sf.jxls.transformer.XLSTransformer;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.NotImplementedException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.util.ResourceUtils;
import org.springframework.web.servlet.view.AbstractView;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;
import java.util.Map;

/**
 * 导出Excel
 *
 * @author Tang Yong Di
 * @date 2016/2/5
 */
public class ExcelView<T extends Partition> extends AbstractView {

	private String filename;
	private String templateName;

	private List<T> objectList = null;

	public static final String CONTENT_TYPE = "application/vnd.ms-excel";
	public static final String TEMPLATE_PATH = "classpath:template/excel/";
	public static final String FORMAT = ".xls";

	public ExcelView() {
	}

	public ExcelView(String templateName) {
		this.templateName = templateName;
		this.filename = templateName;
	}

	public ExcelView(String templateName, List<T> objectList) {
		this(templateName);

		this.objectList = objectList;
	}

	@Override
	protected void renderMergedOutputModel(Map<String, Object> beanParams, HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType(CONTENT_TYPE);
		response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + ".xls\"");

		ServletOutputStream os = response.getOutputStream();

		File template = ResourceUtils.getFile(TEMPLATE_PATH + templateName + FORMAT);
		String path = template.getPath();
		if (this.objectList == null) {
			if (beanParams.isEmpty()) { // 下载模板
				FileUtils.copyFile(template, os);
			} else {
				sheetExport(beanParams, os, path);
			}
		} else {
			multiSheetsExport(beanParams, os, path);
		}
	}

	@SuppressWarnings("unchecked")
	private void multiSheetsExport(Map<String, Object> beanParams, ServletOutputStream os, String path) throws Exception {
		final MultipleSheetsList sheetsList = new MultipleSheetsList(path, "query");

		PartitionExecutor.execute(this.objectList, new PartitionCallback<T>() {
			@Override
			public void execute(List<T> partList) {
				sheetsList.addSheet("", partList);
			}
		});
		if (!beanParams.isEmpty()) {
			sheetsList.addBeanParams(beanParams);
		}
		sheetsList.storeTo(os);
		throw new NotImplementedException("未实现");
	}

	private void sheetExport(Map<String, Object> beanParams, ServletOutputStream os, String path) throws InvalidFormatException, IOException {
		XLSTransformer transformer = new XLSTransformer();
		InputStream is = new BufferedInputStream(new FileInputStream(path));
		Workbook workbook = transformer.transformXLS(is, beanParams);
		workbook.write(os);
		is.close();
		os.flush();
		os.close();
	}
}
