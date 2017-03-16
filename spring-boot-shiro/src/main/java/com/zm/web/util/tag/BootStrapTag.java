package com.zm.web.util.tag;

import java.io.StringReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTag;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.Tag;

import org.apache.commons.lang3.StringUtils;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.xml.sax.InputSource;

import com.zm.web.util.CustUtils;

/**
 * 封装分页方法
 * 
 * @author zhumin
 *
 */
public class BootStrapTag extends BodyTagSupport {

	private String id;

	@Override
	public int doStartTag() throws JspException {
		return BodyTag.EVAL_BODY_BUFFERED;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public int doEndTag() throws JspException {
		StringBuffer sb = new StringBuffer("");
		BodyContent bc = this.getBodyContent();
		String content = bc.getString();
		String result = "";
		try {
			if (!StringUtils.isEmpty(content)) {
				Map<String,Object> shared = new HashMap<String,Object>();
				content = StringUtils.trimToNull(content);
		        SAXBuilder builder = new SAXBuilder();  
		        StringReader sr = new StringReader(content);
		        InputSource is = new InputSource(sr);
		        Document doc = builder.build(is);
		        Element root = doc.getRootElement();  
				List lineList = root.getChildren("btable"); 
		        for (Iterator iter = lineList.iterator(); iter.hasNext();) {
		            Element lineElement = (Element) iter.next();
		            String url = lineElement.getAttributeValue("url");
		            shared.put("url", "'"+url+"'");
		            String onLoadSuccess = lineElement.getAttributeValue("onLoadSuccess");  
		            if(!StringUtils.isEmpty(onLoadSuccess)){
		            	onLoadSuccess = "onLoadSuccess:"+onLoadSuccess+",";
		            }
		            shared.put("onLoadSuccess", onLoadSuccess);
		            
		            String tableFunName = lineElement.getAttributeValue("tableFunName");  
		            shared.put("tableFunName", tableFunName+"()");
		            
		            String onLoadError = lineElement.getAttributeValue("onLoadError");  
		            if(!StringUtils.isEmpty(onLoadError)){
		            	onLoadError = "onLoadError:"+onLoadError+",";
		            }
		            shared.put("onLoadError", onLoadSuccess);
		            
		            String queryParams = lineElement.getAttributeValue("queryParams"); 
		            shared.put("queryParams", queryParams);
		            String id = lineElement.getAttributeValue("id");
		            shared.put("id", "$('#"+id+"')");
		            
		            List columns =   lineElement.getChildren("columns");
		            for (Iterator iterColumns = columns.iterator(); iterColumns.hasNext();) {
		            	
		            	 Element lineColumns = (Element) iterColumns.next();
		            	 List listColumn  = lineColumns.getChildren("column");
		            	  for (Iterator iterColumn = listColumn.iterator(); iterColumn.hasNext();) {
		            		  sb.append("{");
		            		  Element column = (Element) iterColumn.next();
		            		  String field = column.getAttributeValue("field");
		            		  if(!StringUtils.isEmpty(field)){
		            			  sb = sb.append(" field: '"+field+"',");
		            		  }
		            		  String title = column.getAttributeValue("title");
		            		  if(!StringUtils.isEmpty(title)){
		            			  sb = sb.append(" title: '"+title+"',");
		            		  }
		            		  String align = column.getAttributeValue("align");
		            		  if(!StringUtils.isEmpty(align)){
		            			  sb = sb.append(" align: '"+align+"',");
		            		  }
		            		  String formatter =  column.getAttributeValue("formatter");
		            		  if(!StringUtils.isEmpty(formatter)){
		            			  sb = sb.append(" formatter: '"+formatter+"',");
		            		  }
		            		  sb = sb.deleteCharAt(sb.length()-1).append("},");
		            	  }
		            }
		            if(sb.length() >0){
		            	sb = sb.deleteCharAt(sb.length()-1);
		            	 shared.put("items", sb.toString());
		            }
		            
		            System.out.println(sb.toString());
		            StringBuffer paramsb = new StringBuffer(""); 
		            List queryParamsXML =   lineElement.getChildren("queryParams");
		            for (Iterator iterqueryParamsXML = queryParamsXML.iterator(); iterqueryParamsXML.hasNext();) {
		            	 Element lineColumns = (Element) iterqueryParamsXML.next();
		            	 List queryParamList  = lineColumns.getChildren("queryParam");
		            	 for (Iterator iterQueryParam = queryParamList.iterator(); iterQueryParam.hasNext();) {
		            		  Element column = (Element) iterQueryParam.next();
		            		  String field = column.getAttributeValue("name");
		            		  paramsb = paramsb.append("pageReqeust.").append(field).append("=").append("$(\"#").append(field).append("\").val(); ");
		            	 }
		            }
		            if(paramsb.length() >0){
		            	paramsb = paramsb.deleteCharAt(paramsb.length()-1);
		            	shared.put("paramsb", paramsb);
		            }
		            System.out.println(paramsb);
		        }
		        result =   CustUtils.getBeetlMap(shared);
			}
			
			if(!StringUtils.isEmpty(result))
				this.pageContext.getOut().write(result);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return Tag.EVAL_PAGE;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
