package exclusiveGateway;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.junit.Test;


public class ExclusiveGatewayTest {

	private ProcessEngine processEngine = ProcessEngineConfiguration.createProcessEngineConfigurationFromResourceDefault().buildProcessEngine();
	
	//流程定义
	@Test
	public void processDefine(){
		String title = "排他网管";
		InputStream bpmnIn = this.getClass().getResourceAsStream("exclusiveGateway.bpmn");
		InputStream pngIn = this.getClass().getResourceAsStream("exclusiveGateway.png");
		
		Deployment deployment = processEngine.getRepositoryService()
			.createDeployment()
			.name(title)
			.addInputStream("exclusiveGateway.bpmn", bpmnIn)
			.addInputStream("exclusiveGateway.png", pngIn)
			.deploy();
		
		System.out.println(deployment.getId()+" "+deployment.getName());
	}
	
	//流程启动
	@Test
	public void startProcess(){
		String key = "exclusiveGateway";
		ProcessInstance pi = processEngine.getRuntimeService()
			.startProcessInstanceByKey(key);
		System.out.println(pi.getId()+" "+pi.getProcessDefinitionId()+" "+pi.getProcessInstanceId());
	}
	
	
	@Test
	public void completeTaskByVariable(){
		String taskId="4504";
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("message", 300);
		processEngine.getTaskService()
			.complete(taskId, map);
		System.out.println("任务完成 "+taskId);

	}
	@Test
	public void completeTask(){
		String taskId = "3703";
		processEngine.getTaskService()
			.complete(taskId);
		System.out.println("任务完成 "+taskId);
	}
	
	
}
