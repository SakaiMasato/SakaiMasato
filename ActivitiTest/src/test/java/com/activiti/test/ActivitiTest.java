package com.activiti.test;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.junit.Test;

public class ActivitiTest {
	/**使用代码创建工作流需要的23张表**/
	@Test
	public void createTable(){
		//单例的流程引擎配置
		ProcessEngineConfiguration processEngineConfiguration = ProcessEngineConfiguration.createStandaloneProcessEngineConfiguration();
		//连接数据库的配置
		processEngineConfiguration.setJdbcDriver("oracle.jdbc.driver.OracleDriver");
		processEngineConfiguration.setJdbcUrl("jdbc:oracle:thin:@localhost:1521:orcl");
		processEngineConfiguration.setJdbcUsername("bxw");
		processEngineConfiguration.setJdbcPassword("root");
		
		//ProcessEngineConfiguration.DB_SCHEMA_UPDATE_CREATE_DROP;先删除表后创建表
		//ProcessEngineConfiguration.DB_SCHEMA_UPDATE_FALSE;不能自动创建表，需要表存在
		//ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE;如果表不存在则自动创建表
		processEngineConfiguration.setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);
		//工作流的核心对象,processEngine对象
		ProcessEngine processEngine = processEngineConfiguration.buildProcessEngine();
		System.out.println(processEngine);
	}
}
