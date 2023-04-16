package com.zhao.deep.utils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
/*import java.util.ArrayList;*/
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
/*import java.util.List;*/
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;

/**
 * 文件上传工具类
 * 
 * @author yangdc
 * @date Apr 18, 2012
 * 
 * <pre>
 * </pre>
 */
public class UploadUtils {
	Logger logger = Logger.getLogger(UploadUtils.class);
	/**
	 * 表单字段常量
	 */
	public static final String FORM_FIELDS = "form_fields";
	/**
	 * 文件域常量
	 */
	public static final String FILE_FIELDS = "file_fields";

	// 最大文件大小
	private long maxSize = 4000000;
	// 定义允许上传的文件扩展名
	private Map<String, String> extMap = new HashMap<String, String>();
	// 文件保存目录相对路径
	private String basePath = "upload";
	// 文件的目录名
	private String dirName = "images";
	// 上传临时路径
	private static final String TEMP_PATH = "/temp";
	private String tempPath = basePath + TEMP_PATH;
	// 若不指定则文件名默认为 yyyyMMddHHmmss_xyz
	private String fileName;

	// 文件保存目录路径
	private String savePath;
	// 文件最终的url包括文件名
	private String fileUrl;

	public UploadUtils() {
		// 其中images,flashs,medias,files,对应文件夹名称,对应dirName
		// key文件夹名称
		// value该文件夹内可以上传文件的后缀名
		extMap.put("images", "gif,jpg,jpeg,png,bmp");
		extMap.put("flashs", "swf,flv");
		extMap.put("medias", "swf,flv,mp3,wav,wma,wmv,mid,avi,mpg,asf,rm,rmvb");
		extMap.put("files", "doc,docx,xls,xlsx,ppt,htm,html,txt,zip,rar,gz,bz2");
	}

	/**
	 * 文件上传
	 * 
	 * @param request
	 * @return infos info[0] 验证文件域返回错误信息 info[1] 上传文件错误信息 info[2] savePath info[3] fileUrl info[4]  fields
	 */
	@SuppressWarnings("unchecked")
	public List uploadFile(HttpServletRequest request,String path) {
		List<String> nameList = new ArrayList<>();
		List infos = new ArrayList();
		// 验证
		String validate = this.validateFields(request,path);
		infos.add(validate);
		// 初始化表单元素
		Map<String, Object> fieldsMap = this.initFields(request);
		// 上传
		List<FileItem> fiList = (List<FileItem>) fieldsMap.get(UploadUtils.FILE_FIELDS);
		if (fiList != null && fiList.size() > 0) {
			for (FileItem item : fiList) {
				infos.add(this.saveFile(item));
				nameList.add(item.getName());
			} 
		} else {
			infos.add("initFields error");
		}
		
		infos.add(savePath);
		infos.add(fileUrl);
		Map<String, String> fields = (Map<String, String>)fieldsMap.get(UploadUtils.FORM_FIELDS);
		infos.add(fields);
		infos.add(nameList);
		return infos;
	}

	/**
	 * 上传验证,并初始化文件目录
	 * 
	 * @param request
	 */
	private String validateFields(HttpServletRequest request,String path) {
		String errorInfo = "true";
		// 获取内容类型
		String contentType = request.getContentType();
		int contentLength = request.getContentLength();
		// 文件保存目录路径
		savePath = path + File.separator +  basePath + File.separator;
		
		if (contentType == null || !contentType.startsWith("multipart")) {
			errorInfo = "请求不包含multipart/form-data流";
		} else if (maxSize < contentLength) {
			errorInfo = "上传文件大小超出文件最大大小[" + maxSize + "]";
		} else if (!ServletFileUpload.isMultipartContent(request)) {
			errorInfo = "请选择文件";
		} else if (!extMap.containsKey(dirName)) {
			errorInfo = "目录名不正确";
		} else {
			// 创建文件夹
			savePath += dirName + File.separator;
			File saveDirFile = new File(savePath);
			if (!saveDirFile.exists()) {
				saveDirFile.mkdirs();
			}
			// .../basePath/dirName/yyyyMMdd/
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			String ymd = sdf.format(new Date());
			savePath += ymd + File.separator;
		
			File dirFile = new File(savePath);
			if (!dirFile.exists()) {
				dirFile.mkdirs();
			}

			// 获取上传临时路径
			/*tempPath = request.getSession().getServletContext().getRealPath("/") + tempPath + File.separator;
			File file = new File(tempPath);
			if (!file.exists()) {
				file.mkdirs();
			}*/
		}

		return errorInfo;
	}

	/**
	 * 处理上传内容
	 * 
	 * @param request
	 * @param maxSize
	 * @return
	 */
//	@SuppressWarnings("unchecked")
	private Map<String, Object> initFields(HttpServletRequest request) {

		// 存储表单字段和非表单字段
		Map<String, Object> map = new HashMap<String, Object>();

		// 第一步：判断request
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		// 第二步：解析request
		if (isMultipart) {
			// Create a factory for disk-based file items
			DiskFileItemFactory factory = new DiskFileItemFactory();

			// 阀值,超过这个值才会写到临时目录,否则在内存中
			factory.setSizeThreshold(1024 * 1024 * 10);
			factory.setRepository(new File(tempPath));

			// Create a new file upload handler
			ServletFileUpload upload = new ServletFileUpload(factory);

			upload.setHeaderEncoding("UTF-8");

			// 最大上传限制
			upload.setSizeMax(maxSize);

			/* FileItem */
			List<FileItem> items = null;
			// Parse the request
			try {
				//items = upload.parseRequest(request);
				items =upload.parseRequest(request);
				
			} catch (FileUploadException e) {
				logger.error("文件上传错误！");
				e.printStackTrace();
			}

			// 第3步：处理uploaded items
			if (items != null && items.size() > 0) {
				Iterator<FileItem> iter = items.iterator();
				// 文件域对象
				List<FileItem> list = new ArrayList<FileItem>();
				// 表单字段
				Map<String, String> fields = new HashMap<String, String>();
				while (iter.hasNext()) {
					FileItem item = iter.next();
					// 处理所有表单元素和文件域表单元素
					if (item.isFormField()) { // 表单元素
						String name = item.getFieldName();
						String value = item.getString();
						fields.put(name, value);
					} else { // 文件域表单元素
						list.add(item);
					}
				}
				map.put(FORM_FIELDS, fields);
				map.put(FILE_FIELDS, list);
			}
		}
		return map;
	}

	/**
	 * 保存文件
	 * 
	 * @param obj
	 *            要上传的文件域
	 * @param file
	 * @return
	 */
	private String saveFile(FileItem item) {
		String error = "true";
		String fileName = item.getName();
		String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
       
		if (item.getSize() > maxSize) { // 检查文件大小
			error = "上传文件大小超过限制";
		} else {
			String newFileName;
			if ("".equals(fileName.trim())) {
				SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
				newFileName = df.format(new Date()) + "_" + new Random().nextInt(1000) + "." + fileExt;
			} else {
				newFileName = fileName;
			}
			// .../basePath/dirName/yyyyMMdd/yyyyMMddHHmmss_xxx.xxx
			fileUrl = savePath + newFileName;
			try {
				if (StringUtil.isNotBlank(fileExt)) {
					System.out.println(fileUrl);
					File uploadedFile = new File(savePath, newFileName);
					if (uploadedFile.exists()) {
						String[] names = newFileName.split("\\.");
						String newName = names[0];
						newName = newName + new Random().nextInt(10000) + "." + fileExt;
						uploadedFile = new File(savePath, newName);
						newFileName = newName;
						fileUrl = savePath + newFileName;
						System.out.println(fileUrl);
					}
					item.write(uploadedFile);
				}
			} catch (IOException e) {
				logger.error("上传失败了！！！");
				e.printStackTrace();
				error="upload ioException";
			} catch (Exception e) {
				e.printStackTrace();
				error="upload Exception";
			}
		}
		
		return error;
	}

	/** **********************get/set方法********************************* */

	public String getSavePath() {
		return savePath;
	}


	public long getMaxSize() {
		return maxSize;
	}

	public void setMaxSize(long maxSize) {
		this.maxSize = maxSize;
	}

	public Map<String, String> getExtMap() {
		return extMap;
	}

	public void setExtMap(Map<String, String> extMap) {
		this.extMap = extMap;
	}

	public String getBasePath() {
		return basePath;
	}

	public void setBasePath(String basePath) {
		this.basePath = basePath;
		tempPath = basePath + TEMP_PATH;
	}

	public String getDirName() {
		return dirName;
	}

	public void setDirName(String dirName) {
		this.dirName = dirName;
	}

	public String getTempPath() {
		return tempPath;
	}

	public void setTempPath(String tempPath) {
		this.tempPath = tempPath;
	}

	public String getFileUrl() {
		return fileUrl;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

}
