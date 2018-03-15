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

	//��������
	private ProcessEngine processEngine = ProcessEngineConfiguration.createProcessEngineConfigurationFromResourceDefault().buildProcessEngine();

	//���̶���
	@Test
	public void processDefine(){
		InputStream bpmnIn = this.getClass().getResourceAsStream("organization.bpmn");
		InputStream pngIn = this.getClass().getResourceAsStream("organization.png");
		Deployment deployment = processEngine.getRepositoryService()
			.createDeployment()
			.name("��֯(��ɫ)�������")
			.addInputStream("organization.bpmn", bpmnIn)
			.addInputStream("organization.png", pngIn)
			.deploy();
		System.out.println(deployment.getId()+" "+deployment.getName());
	}
	
	//��������
	@Test
	public void starProcessDefine(){
		String key = "organization";
//			Map<String, Object> map = new HashMap<>();
//			map.put("userIds", "dada,xiaoxiao,zhongzhong");
		ProcessInstance pi = processEngine.getRuntimeService()
			.startProcessInstanceByKey(key);
		
		//������ɫ
		processEngine.getIdentityService()//��ɫ��ص�service
			.saveGroup(new GroupEntity("�ܾ���"));
		
		processEngine.getIdentityService()
			.saveGroup(new GroupEntity("���ž���"));
		
		processEngine.getIdentityService()
			.saveUser(new UserEntity("����"));
		
		processEngine.getIdentityService()
			.saveUser(new UserEntity("����"));
		
		//������ɫ���û��Ĺ�ϵ
		processEngine.getIdentityService()
			.createMembership("����", "���ž���");
		
		processEngine.getIdentityService()
			.createMembership("����", "�ܾ���");
		
		
		System.out.println(pi.getActivityId()+" "+pi.getId()+" "+pi.getProcessDefinitionId()+" "+pi.getProcessInstanceId());
	}
	
	//�����ɫ��ѯ
		@Test
		public void findMyGroupPersonalTask(){
			String groupId = "���ž���";
			List<Task> list = processEngine.getTaskService()
				.createTaskQuery()//�����ѯ
				.taskCandidateGroup(groupId)//��ѡ��id
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
			String taskId = "8904";
			String userId = "����";//��userId�������������Ա��Ҳ���Բ���
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
