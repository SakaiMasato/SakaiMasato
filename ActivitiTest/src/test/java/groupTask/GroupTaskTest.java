package groupTask;

import java.io.InputStream;
import java.util.List;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.history.HistoricIdentityLink;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.IdentityLink;
import org.activiti.engine.task.Task;
import org.junit.Test;

public class GroupTaskTest {

	//流程引擎
	private ProcessEngine processEngine = ProcessEngineConfiguration.createProcessEngineConfigurationFromResourceDefault().buildProcessEngine();

	//流程定义
	@Test
	public void processDefine(){
		InputStream bpmnIn = this.getClass().getResourceAsStream("groupTask.bpmn");
		InputStream pngIn = this.getClass().getResourceAsStream("groupTask.png");
		Deployment deployment = processEngine.getRepositoryService()
			.createDeployment()
			.name("组任务测试")
			.addInputStream("groupTask.bpmn", bpmnIn)
			.addInputStream("groupTask.png", pngIn)
			.deploy();
		System.out.println(deployment.getId()+" "+deployment.getName());
	}
	
	//流程启动
	@Test
	public void starProcessDefine(){
		String key = "groupTask";
		ProcessInstance pi = processEngine.getRuntimeService()
			.startProcessInstanceByKey(key);
		System.out.println(pi.getActivityId()+" "+pi.getId()+" "+pi.getProcessDefinitionId()+" "+pi.getProcessInstanceId());
	}
	
	//查询正在执行的任务办理人表
	@Test
	public void findRunPersonalTask(){
		String taskId = "7404";
		List<IdentityLink> list = processEngine.getTaskService()
			.getIdentityLinksForTask(taskId);
		if(list!=null && list.size()>0){
			for (IdentityLink identityLink : list) {
				System.out.println(identityLink.getTaskId()+" "+identityLink.getUserId());
			}
		}
	}
	
	//查询历史的任务办理人表
	@Test
	public void findHistoricPersonalTask(){
		String processInstanceId = "7401";
		String taskId = "7404";
		List<HistoricIdentityLink> list = processEngine.getHistoryService()
//				.getHistoricIdentityLinksForTask(taskId);
			.getHistoricIdentityLinksForProcessInstance(processInstanceId);
		if(list!=null && list.size()>0){
			for (HistoricIdentityLink HistoricIdentityLink : list) {
				System.out.println(HistoricIdentityLink.getTaskId()+" "+HistoricIdentityLink.getUserId()+" "+HistoricIdentityLink.getProcessInstanceId());
			}
		}
	}
	
	//个人任务查询
	@Test
	public void findMyPersonalTask(){
		String userId = "小F";
		List<Task> list = processEngine.getTaskService()
					.createTaskQuery()
					.taskAssignee(userId)
					.list();
		if(list!=null && list.size()>0){
			for (Task task : list) {
				System.out.println(task.getId()+" "+task.getName()+" "+task.getProcessInstanceId());
			}
		}
	}
	//个人组任务查询
	@Test
	public void findMyGroupPersonalTask(){
		String userId = "小C";
		List<Task> list = processEngine.getTaskService()
			.createTaskQuery()//任务查询
			.taskCandidateUser(userId)//候选人id
			.orderByTaskCreateTime().asc()
			.list();
		
		if(list!=null && list.size()>0){
			for (Task task : list) {
				System.out.println(task.getId()+" "+task.getName()+" "+task.getProcessInstanceId());
			}
		}
	}
	
	//组任务拾取为个人任务
	@Test
	public void groupTask2PersonTask(){
		String taskId = "7404";
		String userId = "小F";//该userId可以是组任务成员，也可以不是
		processEngine.getTaskService()
			.claim(taskId, userId);
	}
	
//	个人任务回退给组任务，但该任务之前必须是组任务
	@Test
	public void personTask2GroupTask(){
		String taskId = "7404";
		processEngine.getTaskService()
			.setAssignee(taskId, null);//设置办理人为空即可
	}
	
	//添加办理候选人
	@Test
	public void addCandidateUser(){
		String taskId = "7404";
		String userId = "小F";//该userId可以是组任务成员，也可以不是
		processEngine.getTaskService()
			.addCandidateUser(taskId, userId);
	}
	
	//删除办理候选人
	@Test
	public void delCandidateUser(){
		String taskId = "7404";
		String userId = "小F";
		processEngine.getTaskService()
			.deleteCandidateUser(taskId, userId);
	}
	
	@Test
	public void completeTask(){
		String taskId = "7404";
		processEngine.getTaskService()
			.complete(taskId);
	}
}
