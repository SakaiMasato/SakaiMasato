package personalTaskTest;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;

public class PersonnalTask {
	private ProcessEngine processEngine = ProcessEngineConfiguration.createProcessEngineConfigurationFromResourceDefault().buildProcessEngine();

	@Test
	public void processDefine(){
		InputStream bpmnIn = this.getClass().getResourceAsStream("myPersonalTask.bpmn");
		InputStream pngIn = this.getClass().getResourceAsStream("myPersonalTask.png");
		Deployment deployment = processEngine.getRepositoryService()
			.createDeployment()
			.name("��������")
			.addInputStream("myPersonalTask.bpmn", bpmnIn)
			.addInputStream("myPersonalTask.png", pngIn)
			.deploy();
		System.out.println(deployment.getId()+" "+deployment.getName());
	}
	
	@Test
	public void starProcessDefine(){
		String key = "personalTask";
//		Map<String, Object> map = new HashMap<>();
//		map.put("assignee", "���޼�");
		ProcessInstance pi = processEngine.getRuntimeService()
			.startProcessInstanceByKey(key);
		System.out.println(pi.getActivityId()+" "+pi.getId()+" "+pi.getProcessDefinitionId()+" "+pi.getProcessInstanceId());
	}
	
	@Test
	public void findMyPersonalTask(){
		String assignee = "���ʦ̫";
		List<Task> list = processEngine.getTaskService()
			.createTaskQuery()
			.taskAssignee(assignee)
			.list();
		if(list!=null && list.size()>0){
			for (Task task : list) {
				System.out.println(task.getId()+" "+task.getProcessInstanceId()+" "+task.getAssignee());
			}
		}
	}
	
	@Test
	public void changeAssignee(){
		String taskId="7104";
		processEngine.getTaskService().setAssignee(taskId, "�¾���");
	}
	
	@Test
	public void completeTask(){
		String taskId = "6705";
		processEngine.getTaskService().complete(taskId);
		System.out.println("�������"+taskId);
	}
}
