package startProcess;

import java.io.InputStream;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.junit.Test;

public class StartProcess {

	ProcessEngine pe = ProcessEngineConfiguration.createProcessEngineConfigurationFromResourceDefault().buildProcessEngine();
	
	@Test
	public void processDefine(){
		InputStream bpmnIn = this.getClass().getResourceAsStream("startProcess.bpmn");
		InputStream pngIn = this.getClass().getResourceAsStream("startProcess.png");
		
		Deployment deployment = pe.getRepositoryService()
			.createDeployment()
			.addInputStream("startProcess.bpmn", bpmnIn)
			.addInputStream("startProcess.png", pngIn)
			.deploy();
		System.out.println(deployment.getId()+" "+deployment.getName());
	}
	
	@Test
	public void startProcessDefine(){
		ProcessInstance pi = pe.getRuntimeService()
			.startProcessInstanceByKey("startProcess");//创建流程实例
		
		ProcessInstance p = pe.getRuntimeService()
			.createProcessInstanceQuery()
			.processInstanceId(pi.getProcessInstanceId())
			.singleResult();//正在运行的流程实例
		
		if(p == null){
			HistoricProcessInstance hpi = pe.getHistoryService()
				.createHistoricProcessInstanceQuery()
				.processInstanceId(pi.getProcessInstanceId())
				.singleResult();
			System.out.println(hpi.getId()+" "+hpi.getStartTime()+" "+hpi.getEndTime()+" "+hpi.getDurationInMillis());
		}
	}
}
