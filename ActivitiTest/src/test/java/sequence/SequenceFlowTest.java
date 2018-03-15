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
	
	//流程引擎
	private ProcessEngine processEngine = ProcessEngineConfiguration.createProcessEngineConfigurationFromResourceDefault().buildProcessEngine();
	
	//流程定义
	@Test
	public void getProcessDefine(){
		String title = "连线";
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
	//流程启动
	public void startProcess(){
		String key = "sequenceFlow";
		ProcessInstance pi = processEngine.getRuntimeService()
			.startProcessInstanceByKey(key);
		System.out.println(pi.getActivityId()+" "+pi.getId());
	}
	
	//个人任务
	@Test
	public void findMyPersonalTask(){
		String name = "张三部门经理";
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
	
	//完成(不重要)
	@Test
	public void complete_1(){
		String taskId = "2304";
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("message", "不重要");
		processEngine.getTaskService()
			.complete(taskId, map);
		System.out.println("任务完成");
	}
	
	//完成(重要)
		@Test
		public void complete_2(){
			String taskId = "2404";
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("message", "重要");
			processEngine.getTaskService()
				.complete(taskId, map);
			System.out.println("任务完成");
		}
}
