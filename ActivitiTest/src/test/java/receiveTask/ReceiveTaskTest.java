package receiveTask;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.junit.Test;

public class ReceiveTaskTest {
	private ProcessEngine processEngine = ProcessEngineConfiguration.createProcessEngineConfigurationFromResourceDefault().buildProcessEngine();

	@Test
	public void processDefine(){
		InputStream bpmnIn = this.getClass().getResourceAsStream("ReceiveTask.bpmn");
		InputStream pngIn = this.getClass().getResourceAsStream("ReceiveTask.png");
		Deployment deployment = processEngine.getRepositoryService()
			.createDeployment()
			.name("接收任务")
			.addInputStream("ReceiveTask.bpmn", bpmnIn)
			.addInputStream("ReceiveTask.png", pngIn)
			.deploy();
		System.out.println(deployment.getId()+" "+deployment.getName());
	}
	
	@Test
	public void starProcessDefine(){
		String key = "receiveTask";
		ProcessInstance pi = processEngine.getRuntimeService()
			.startProcessInstanceByKey(key);
		System.out.println(pi.getActivityId()+" "+pi.getId()+" "+pi.getProcessDefinitionId()+" "+pi.getProcessInstanceId());
	}
	
	@Test
	public void completeSumTask(){
		String id = "5901";
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("金额", 50000);
		ProcessInstance pi = processEngine.getRuntimeService()
			.createProcessInstanceQuery()
			.processDefinitionId(id)
			.singleResult();
		
		Execution execution = processEngine.getRuntimeService()
					.createExecutionQuery()
					.processInstanceId(id)
					.singleResult();
		processEngine.getRuntimeService()
			.setVariables(execution.getId(), map);
		processEngine.getRuntimeService().signal(execution.getId());
		
	}
	
	@Test
	public void completeSendTask(){
		String id = "5901";
		Execution execution = processEngine.getRuntimeService()
				.createExecutionQuery()
				.processInstanceId(id)
				.singleResult();
		Map<String,Object> map = processEngine.getRuntimeService().getVariables(execution.getId());
		System.out.println("短信:金额一共"+map.get("金额"));
		processEngine.getRuntimeService().signal(execution.getId());
	}
}
