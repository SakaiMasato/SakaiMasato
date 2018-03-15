package com.activiti.test;

import java.util.List;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricVariableInstance;
import org.junit.Test;

public class HistoryProcessDefineTest {

	private ProcessEngine processEngine = ProcessEngineConfiguration.createProcessEngineConfigurationFromResourceDefault().buildProcessEngine();
	
	//查询历史流程定义
	@Test
	public void queryHistoryProcessDefine(){
		String processInstanceId = "1501";
		HistoricProcessInstance hpi = processEngine.getHistoryService()
			.createHistoricProcessInstanceQuery()
			.processInstanceId(processInstanceId)
			.singleResult();
		System.out.println(hpi.getId()+" "+hpi.getProcessDefinitionId());
	}
	
	//查询历史活动
	@Test
	public void queryHistoryActivity(){
		String processInstanceId = "1501";
		List<HistoricActivityInstance> list = processEngine.getHistoryService()
			.createHistoricActivityInstanceQuery()//历史活动实例
			.processInstanceId(processInstanceId)
			.list();
		
		if(list!=null && list.size()>0){
			for (HistoricActivityInstance historicActivityInstance : list) {
				System.out.println(historicActivityInstance.getActivityId() + " " +
							historicActivityInstance.getProcessInstanceId() + " " +
							historicActivityInstance.getActivityName());
			}
		}
	}
	
	//查询历史任务
	@Test
	public void queryHistoryTask(){
		String processInstanceId = "1501";
		List<HistoricTaskInstance> list = processEngine.getHistoryService()
				.createHistoricTaskInstanceQuery()//历史任务
				.processInstanceId(processInstanceId)
				.list();
		
		if(list!=null && list.size()>0){
			for (HistoricTaskInstance historicTaskInstance : list) {
				System.out.println(historicTaskInstance.getId() + " " +
						historicTaskInstance.getProcessInstanceId() + " " +
						historicTaskInstance.getAssignee());
			}
		}
	}
	
	@Test
	//查询历史变量
	public void queryHistoryVariable(){
		String processInstanceId = "1501";
		List<HistoricVariableInstance> list = processEngine.getHistoryService()
				.createHistoricVariableInstanceQuery()
				.processInstanceId(processInstanceId)
				.list();
		
		if(list!=null && list.size()>0){
			for (HistoricVariableInstance historicVariableInstance : list) {
				System.out.println(historicVariableInstance.getId() + " " +
							historicVariableInstance.getVariableName());
			}
		}
	}
}
