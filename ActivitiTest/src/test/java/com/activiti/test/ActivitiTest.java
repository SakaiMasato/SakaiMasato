package com.activiti.test;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.junit.Test;

public class ActivitiTest {
	/**ʹ�ô��봴����������Ҫ��23�ű�**/
	@Test
	public void createTable(){
		//������������������
		ProcessEngineConfiguration processEngineConfiguration = ProcessEngineConfiguration.createStandaloneProcessEngineConfiguration();
		//�������ݿ������
		processEngineConfiguration.setJdbcDriver("oracle.jdbc.driver.OracleDriver");
		processEngineConfiguration.setJdbcUrl("jdbc:oracle:thin:@localhost:1521:orcl");
		processEngineConfiguration.setJdbcUsername("bxw");
		processEngineConfiguration.setJdbcPassword("root");
		
		//ProcessEngineConfiguration.DB_SCHEMA_UPDATE_CREATE_DROP;��ɾ����󴴽���
		//ProcessEngineConfiguration.DB_SCHEMA_UPDATE_FALSE;�����Զ���������Ҫ�����
		//ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE;������������Զ�������
		processEngineConfiguration.setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);
		//�������ĺ��Ķ���,processEngine����
		ProcessEngine processEngine = processEngineConfiguration.buildProcessEngine();
		System.out.println(processEngine);
	}
}
