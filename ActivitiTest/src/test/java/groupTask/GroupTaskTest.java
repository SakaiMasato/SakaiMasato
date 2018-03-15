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

	//��������
	private ProcessEngine processEngine = ProcessEngineConfiguration.createProcessEngineConfigurationFromResourceDefault().buildProcessEngine();

	//���̶���
	@Test
	public void processDefine(){
		InputStream bpmnIn = this.getClass().getResourceAsStream("groupTask.bpmn");
		InputStream pngIn = this.getClass().getResourceAsStream("groupTask.png");
		Deployment deployment = processEngine.getRepositoryService()
			.createDeployment()
			.name("���������")
			.addInputStream("groupTask.bpmn", bpmnIn)
			.addInputStream("groupTask.png", pngIn)
			.deploy();
		System.out.println(deployment.getId()+" "+deployment.getName());
	}
	
	//��������
	@Test
	public void starProcessDefine(){
		String key = "groupTask";
		ProcessInstance pi = processEngine.getRuntimeService()
			.startProcessInstanceByKey(key);
		System.out.println(pi.getActivityId()+" "+pi.getId()+" "+pi.getProcessDefinitionId()+" "+pi.getProcessInstanceId());
	}
	
	//��ѯ����ִ�е���������˱�
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
	
	//��ѯ��ʷ����������˱�
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
	
	//���������ѯ
	@Test
	public void findMyPersonalTask(){
		String userId = "СF";
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
	//�����������ѯ
	@Test
	public void findMyGroupPersonalTask(){
		String userId = "СC";
		List<Task> list = processEngine.getTaskService()
			.createTaskQuery()//�����ѯ
			.taskCandidateUser(userId)//��ѡ��id
			.orderByTaskCreateTime().asc()
			.list();
		
		if(list!=null && list.size()>0){
			for (Task task : list) {
				System.out.println(task.getId()+" "+task.getName()+" "+task.getProcessInstanceId());
			}
		}
	}
	
	//������ʰȡΪ��������
	@Test
	public void groupTask2PersonTask(){
		String taskId = "7404";
		String userId = "СF";//��userId�������������Ա��Ҳ���Բ���
		processEngine.getTaskService()
			.claim(taskId, userId);
	}
	
//	����������˸������񣬵�������֮ǰ������������
	@Test
	public void personTask2GroupTask(){
		String taskId = "7404";
		processEngine.getTaskService()
			.setAssignee(taskId, null);//���ð�����Ϊ�ռ���
	}
	
	//��Ӱ����ѡ��
	@Test
	public void addCandidateUser(){
		String taskId = "7404";
		String userId = "СF";//��userId�������������Ա��Ҳ���Բ���
		processEngine.getTaskService()
			.addCandidateUser(taskId, userId);
	}
	
	//ɾ�������ѡ��
	@Test
	public void delCandidateUser(){
		String taskId = "7404";
		String userId = "СF";
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
