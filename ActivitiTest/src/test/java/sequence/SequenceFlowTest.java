package sequence;

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

public class SequenceFlowTest {
	
	//��������
	private ProcessEngine processEngine = ProcessEngineConfiguration.createProcessEngineConfigurationFromResourceDefault().buildProcessEngine();
	
	//���̶���
	@Test
	public void getProcessDefine(){
		String title = "����";
		InputStream bpmnIn = this.getClass().getResourceAsStream("sequenceFlow.bpmn");
		InputStream pngIn = this.getClass().getResourceAsStream("sequenceFlow.png");
		Deployment d = processEngine.getRepositoryService()
			.createDeployment()
			.name(title)
			.addInputStream("sequenceFlow.bpmn", bpmnIn)
			.addInputStream("sequenceFlow.png", pngIn)
			.deploy();
		
		System.out.println(d.getId()+" "+d.getName());
	}
	
	@Test
	//��������
	public void startProcess(){
		String key = "sequenceFlow";
		ProcessInstance pi = processEngine.getRuntimeService()
			.startProcessInstanceByKey(key);
		System.out.println(pi.getActivityId()+" "+pi.getId());
	}
	
	//��������
	@Test
	public void findMyPersonalTask(){
		String name = "�������ž���";
		List<Task> list = processEngine.getTaskService()
				.createTaskQuery()
				.taskAssignee(name)
				.list();
		if(list!=null && list.size()>0){
			for (Task task : list) {
				System.out.println(task.getId());
			}
		}
	}
	
	//���(����Ҫ)
	@Test
	public void complete_1(){
		String taskId = "2304";
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("message", "����Ҫ");
		processEngine.getTaskService()
			.complete(taskId, map);
		System.out.println("�������");
	}
	
	//���(��Ҫ)
		@Test
		public void complete_2(){
			String taskId = "2404";
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("message", "��Ҫ");
			processEngine.getTaskService()
				.complete(taskId, map);
			System.out.println("�������");
		}
}
