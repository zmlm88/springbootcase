package com.zm.web.configuration.db;


/**
 * 移动到DataSourceConfiguration
 * @author zmlm88@126.com
 *
 */


//@Configuration
////因为这个对象的扫描，需要在MyBatisConfig的后面注入，所以加上下面的注解.
//@AutoConfigureAfter(DataSourceConfiguration.class)
public class MyBatisMapperScannerConfig {
 
	
//	   @Bean
//	    public static MapperScannerConfigurer mapperScannerConfigurer() {
//	        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
//	        //获取之前注入的beanName为sqlSessionFactory的对象
//	        mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
//	        try{
////		        mapperScannerConfigurer.setBasePackage(PropertiesLoaderUtils.loadProperties(new ClassPathResource(Const.CONFIG_PROPERTIES)).getProperty(Const.BASE_PACKAGE_KEY));
//	        }catch(Exception e){
//	        	e.printStackTrace();
//	        	throw new NullPointerException("MyBatisMapperScannerConfig 接口层未配置");
//	        }
//	        return mapperScannerConfigurer;
//	    }
	
}
