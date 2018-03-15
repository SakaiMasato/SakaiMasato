package com.activiti.test;

import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricVariableInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.junit.Test;

import com.sun.jmx.snmp.tasks.TaskServer;

import entity.Person;

public class ProcessVariableTest {
	private ProcessEngine processEngine = ProcessEngineConfiguration.createProcessEngineConfigurationFromResourceDefault().buildProcessEngine();
	//流程定义（从inputstrean）
		@Test
		public void deployProcessDefine_inputStream(){
			String title="ProcessVariable";
			InputStream bpmnIn = this.getClass().getResourceAsStream("/diagrams/processVariable.bpmn");
			InputStream pngIn = this.getClass().getResourceAsStream("/diagrams/processVariable.png");
			Deployment deployment = processEngine.getRepositoryService()//拿到流程定义与部署相关的service
													.createDeployment()//创建一个部署对象
													.name(title)
													.addInputStream("processVariable.bpmn", bpmnIn)//name必须和加载文件的名称相同
													.addInputStream("processVariable.png", pngIn)//name必须和加载文件的名称相同
													.deploy();//完成部署
			
			System.out.println("部署id: " + deployment.getId());
			System.out.println("部署名称: " + deployment.getName());
		}
		
		//流程启动
		@Test
		public void startProcess(){
			String key = "ProcessVariable";
			ProcessInstance pi = processEngine.getRuntimeService()//拿到执行的流程实例和执行对象相关的service
				.startProcessInstanceByKey(key);
			System.out.println(pi.getProcessDefinitionId());//流程定义id
			System.out.println(pi.getProcessInstanceId());//流程实例id
			System.out.println(pi.getActivityId());//该流程活动节点id
			System.out.println(pi.getId());//流程实例id
		}
		
		//设置变量，
		@Test
		public void setVariable(){
			String taskId = "1504";
			TaskService taskService = processEngine.getTaskService();
//			taskService.setVariableLocal(taskId, "休假天数", 3);//setVariableLocal会将该变量与taskId绑定起来,任务结束后该变量置空
//			taskService.setVariable(taskId, "休假时间", new Date());//不加Local则不会置空
//			taskService.setVariable(taskId, "休假原因", "回家探亲");
			Person p =new Person(10, "哈啊哈");
			taskService.setVariable(taskId, "人员信息", p);//javabean类型的流程变量设置必须序列化,表中javabean的相关信息以流的形式存在bytearray_id_字段相应的资源表中
			
			System.out.println("设置流程变量成功");
		}
		
		//获得变量
		@Test
		public void getVariable(){//当一个javabean放置在流程变量中，要求该javabean不能在变化（解决方法,在javabean中添加序列化的id）
			String taskId = "1504";
			TaskService taskService = processEngine.getTaskService();
			Map<String, Object> map =  taskService.getVariables(taskId);
			for(Map.Entry<String, Object> entry:map.entrySet()){
				System.out.println(entry.getKey()+" "+entry.getValue());
			}
		}
		
		@Test
		public void completeTask(){
			String taskId = "1702";
			processEngine.getTaskService().complete(taskId);
		}
		
		//模拟设置和获得变量的场景
		public void setAndGetVariable(){
			//有两个service可以操作流程变量
		RuntimeService runtimeService = processEngine.getRuntimeService();//与正在执行的流程实例，执行对象相关的service
		TaskService taskService = processEngine.getTaskService();//与正在执行的任务相关的service
		
		/*设置流程变量*/
//		runtimeService.setVariable(excutionId, variableName, value);//使用执行对象的id，和流程变量的名称，设置流程变量的值，但是一次只能设置一个流程变量的值
//		runtimeService.setVariables(excutionId, Map<variableName,value>);//使用执行对象的id,和Map<流程变量的名称,流程变量的值>：
		
//		taskService.setVariable(excutionId, variableName, value);
//		taskService.setVariables(excutionId, Map<variableName,value>);
		
//		runtimeService.startProcessInstanceByKey(processsDefinitionKey,Map<variableName,value>)//启动流程实例的同时，可以设置流程变量
//		taskService.complete(taskId, Map<variableName,value>);//任务完成时，设置流程变量
		
		/*获取流程变量*/
//		runtimeService.getVariable(excutionId, variableName)
//		runtimeService.getVariables(excutionId);
//		runtimeService.getVariables(excutionId, list<variableName>);
		
//		taskService.getVariable(excutionId, variableName)
//		taskService.getVariables(excutionId);
//		taskService.getVariables(excutionId, list<variableName>);
		}
		
		//查询历史变量
		@Test
		public void historyVariableQuery(){
			List<HistoricVariableInstance> list = processEngine.getHistoryService()
				.createHistoricVariableInstanceQuery()//历史变量实例
				.variableName("休假天数")
				.list();
			if(list!=null && list.size()>0){
				for (int i = 0; i < list.size(); i++) {
					System.out.println(list.get(i).getVariableName()+" "+list.get(i).getValue());
				}
			}
		}
}
