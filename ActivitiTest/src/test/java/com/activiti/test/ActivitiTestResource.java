package com.activiti.test;

import java.io.InputStream;
import java.util.List;
import java.util.zip.ZipInputStream;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;


public class ActivitiTestResource {

	ProcessEngine processEngine = ProcessEngineConfiguration.createProcessEngineConfigurationFromResourceDefault()//从classpath下加载配置文件
			.buildProcessEngine();//流程引擎
	
	@Test
	public void createProcessEngine(){
		ProcessEngine processEngine = ProcessEngineConfiguration.createProcessEngineConfigurationFromResourceDefault()//从classpath下加载配置文件
										.buildProcessEngine();
		System.out.println(processEngine);
	}
	
	//流程定义（从classpath）
	@Test
	public void deployProcessDefine_classPath(){
		String title="helloWorld入门程序";
		Deployment deployment = processEngine.getRepositoryService()//拿到流程定义与部署相关的service
												.createDeployment()//创建一个部署对象
												.name(title)
												.addClasspathResource("diagrams/helloWorld.bpmn")//从classpath加载资源，一次只能加载一个资源文件
												.addClasspathResource("diagrams/helloWorld.png")
												.deploy();//完成部署
		
		System.out.println("部署id: " + deployment.getId());
		System.out.println("部署名称: " + deployment.getName());
	}
	
	//流程定义（从zip）
	@Test
	public void deployProcessDefine_zip(){
		String title="流程定义";
		InputStream in = this.getClass().getClassLoader().getResourceAsStream("diagrams/hellowWorld.zip");
		ZipInputStream zipInputStream = new ZipInputStream(in);
		Deployment deployment = processEngine.getRepositoryService()//拿到流程定义与部署相关的service
												.createDeployment()//创建一个部署对象
												.name(title)
												.addZipInputStream(zipInputStream)
												.deploy();//完成部署
		
		System.out.println("部署id: " + deployment.getId());
		System.out.println("部署名称: " + deployment.getName());
	}
	
	//启动流程示例
	@Test
	public void startProcessInstance(){
		String key = "helloWorld";//用来启动流程的key对应bpmn文件中的id
		ProcessInstance processInstance = processEngine.getRuntimeService()//拿到执行的流程实例和执行对象相关的service
											.startProcessInstanceByKey(key);//使用key启动默认使用最新版本的流程定义启动
		System.out.println("流程实例Id :" + processInstance.getId());
		System.out.println("该流程活动节点id :" + processInstance.getActivityId());
	}

	//查询当前人的个人任务
	@Test
	public void findMyPersonalTask(){
		String name="张三";
		List<Task> qryList = processEngine.getTaskService()//与正在执行任务相关的service
			.createTaskQuery()
			/*条件*/
			.taskAssignee(name)//查询谁的任务
//			.processDefinitionId(arg0)//流程定义id查询
//			.executionId(arg0)//执行对象id查询
			
			/*排序*/
//			.orderByTaskCreateTime().asc()
			
			/*结果集*/
//			.singleResult()
//			.listPage(arg0, arg1)
			.list();
		if(qryList!=null && qryList.size()>0){
			for(Task t : qryList){
				System.out.println("任务id: "+t.getId());
				System.out.println("任务名称: "+t.getName());
				System.out.println("人物创建时间: "+t.getCreateTime());
				System.out.println("任务办理人: "+t.getAssignee());
				System.out.println("流程实例id: "+t.getProcessInstanceId());
				System.out.println("执行对象id: "+t.getExecutionId());
				System.out.println("流程定义id: "+t.getProcessDefinitionId());
			}
		}
	}
	
	//完成任务
	@Test
	public void completeMyPersonalTask(){
		String taskId="804";
		processEngine.getTaskService()//与正在执行任务相关的service
			.complete(taskId);
		System.out.println("任务完成: "+taskId);
	}
	
	//流程是否结束
	@Test
	public void isProcessEnd(){
		String id="801";
		ProcessInstance pi = processEngine.getRuntimeService()//与正在执行的流程示例和执行对象有关
			.createProcessInstanceQuery()//创建流程实例查询
			.singleResult();
		if(pi==null){
			System.out.println("该流程已经结束");
		}else{
			System.out.println("该流程没有结束");
		}
	}
	
	//查询历史任务
	@Test
	public void viewHistoryTask(){
		String name = "张三";
		List<HistoricTaskInstance> list = processEngine.getHistoryService()//拿到与历史相关的service
			.createHistoricTaskInstanceQuery()//历史任务查询
			.taskAssignee(name)//办理人名称
			.list();
		
		if(list!=null && list.size()>0){
			for(HistoricTaskInstance historicTaskInstance : list){
				System.out.println(historicTaskInstance.getId()+"	"+
						historicTaskInstance.getProcessDefinitionId()+"	"+
						historicTaskInstance.getTaskDefinitionKey()+"	"+
						historicTaskInstance.getProcessInstanceId()+"	"+
						historicTaskInstance.getExecutionId()+"	"+
						historicTaskInstance.getName()+"	"+
						historicTaskInstance.getAssignee()+"	"+
						historicTaskInstance.getStartTime()+"	"+
						historicTaskInstance.getDurationInMillis());
				System.out.println("##########################################");
			}
		}
	}
	
	@Test
	public void viewHistoryProcess(){
		String ProcessInstanceId = "801";
		
		List<HistoricProcessInstance> list = processEngine.getHistoryService()//拿到与历史相关的service
				.createHistoricProcessInstanceQuery()//历史流程实例查询
				.processInstanceId(ProcessInstanceId)
				.list();
		if(list!=null && list.size()>0){
			for(HistoricProcessInstance historicTaskInstance : list){
				System.out.println(historicTaskInstance.getId()+"	"+
						historicTaskInstance.getProcessDefinitionId()+"	"+
						historicTaskInstance.getStartTime());
				System.out.println("##########################################");
			}
		}
	}
	
}
