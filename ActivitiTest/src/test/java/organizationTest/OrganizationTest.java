package organizationTest;

import java.io.InputStream;
import java.util.List;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.impl.persistence.entity.GroupEntity;
import org.activiti.engine.impl.persistence.entity.UserEntity;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;

public class OrganizationTest {

	//流程引擎
	private ProcessEngine processEngine = ProcessEngineConfiguration.createProcessEngineConfigurationFromResourceDefault().buildProcessEngine();

	//流程定义
	@Test
	public void processDefine(){
		InputStream bpmnIn = this.getClass().getResourceAsStream("organization.bpmn");
		InputStream pngIn = this.getClass().getResourceAsStream("organization.png");
		Deployment deployment = processEngine.getRepositoryService()
			.createDeployment()
			.name("组织(角色)任务测试")
			.addInputStream("organization.bpmn", bpmnIn)
			.addInputStream("organization.png", pngIn)
			.deploy();
		System.out.println(deployment.getId()+" "+deployment.getName());
	}
	
	//流程启动
	@Test
	public void starProcessDefine(){
		String key = "organization";
//			Map<String, Object> map = new HashMap<>();
//			map.put("userIds", "dada,xiaoxiao,zhongzhong");
		ProcessInstance pi = processEngine.getRuntimeService()
			.startProcessInstanceByKey(key);
		
		//创建角色
		processEngine.getIdentityService()//角色相关的service
			.saveGroup(new GroupEntity("总经理"));
		
		processEngine.getIdentityService()
			.saveGroup(new GroupEntity("部门经理"));
		
		processEngine.getIdentityService()
			.saveUser(new UserEntity("拉拉"));
		
		processEngine.getIdentityService()
			.saveUser(new UserEntity("盼盼"));
		
		//建立角色与用户的关系
		processEngine.getIdentityService()
			.createMembership("拉拉", "部门经理");
		
		processEngine.getIdentityService()
			.createMembership("盼盼", "总经理");
		
		
		System.out.println(pi.getActivityId()+" "+pi.getId()+" "+pi.getProcessDefinitionId()+" "+pi.getProcessInstanceId());
	}
	
	//任务角色查询
		@Test
		public void findMyGroupPersonalTask(){
			String groupId = "部门经理";
			List<Task> list = processEngine.getTaskService()
				.createTaskQuery()//任务查询
				.taskCandidateGroup(groupId)//候选人id
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
			String taskId = "8904";
			String userId = "拉拉";//该userId可以是组任务成员，也可以不是
			processEngine.getTaskService()
				.claim(taskId, userId);
		}
		
		@Test
		public void completeTask(){
			String taskId = "8904";
			processEngine.getTaskService()
				.complete(taskId);
		}
}
