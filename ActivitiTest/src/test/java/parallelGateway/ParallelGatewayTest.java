package parallelGateway;

import java.io.InputStream;
import java.util.List;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;

public class ParallelGatewayTest {

	private ProcessEngine processEngine = ProcessEngineConfiguration.createProcessEngineConfigurationFromResourceDefault().buildProcessEngine();
	
	@Test
	public void processDefine(){
		InputStream bpmnIn = this.getClass().getResourceAsStream("parallelGateWay.bpmn");
		InputStream pngIn = this.getClass().getResourceAsStream("parallelGateway.png");
		Deployment deployment = processEngine.getRepositoryService()
			.createDeployment()
			.name("并行网关")
			.addInputStream("parallelGateway.bpmn", bpmnIn)
			.addInputStream("parallelGateWay.png", pngIn)
			.deploy();
		System.out.println(deployment.getId()+" "+deployment.getName());
	}
	
	@Test
	public void starProcessDefine(){
		String key = "parallelGateway";
		ProcessInstance pi = processEngine.getRuntimeService()
			.startProcessInstanceByKey(key);
		System.out.println(pi.getActivityId()+" "+pi.getId()+" "+pi.getProcessDefinitionId()+" "+pi.getProcessInstanceId());
	}
	
	
	@Test
	public void findMyPersonalTask(){
		String assignee = "客户爸爸";
		List<Task> list = processEngine.getTaskService()
			.createTaskQuery()
			.taskAssignee(assignee)
			.list();
		
		if(list!=null && list.size()>0){
			for (Task task : list) {
				System.out.println(task.getId()+" "+task.getName()+" "+task.getAssignee());
			}
		}
	}
	
	@Test
	public void completeTask(){
		String taskId = "5202";
		processEngine.getTaskService()
			.complete(taskId);
		System.out.println("任务完成  "+taskId);
	}
}
