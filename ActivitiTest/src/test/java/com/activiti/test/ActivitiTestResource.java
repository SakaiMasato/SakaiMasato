package com.activiti.test;

import java.io.InputStream;
import java.util.List;
import java.util.zip.ZipInputStream;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;


public class ActivitiTestResource {

	ProcessEngine processEngine = ProcessEngineConfiguration.createProcessEngineConfigurationFromResourceDefault()//��classpath�¼��������ļ�
			.buildProcessEngine();//��������
	
	@Test
	public void createProcessEngine(){
		ProcessEngine processEngine = ProcessEngineConfiguration.createProcessEngineConfigurationFromResourceDefault()//��classpath�¼��������ļ�
										.buildProcessEngine();
		System.out.println(processEngine);
	}
	
	//���̶��壨��classpath��
	@Test
	public void deployProcessDefine_classPath(){
		String title="helloWorld���ų���";
		Deployment deployment = processEngine.getRepositoryService()//�õ����̶����벿����ص�service
												.createDeployment()//����һ���������
												.name(title)
												.addClasspathResource("diagrams/helloWorld.bpmn")//��classpath������Դ��һ��ֻ�ܼ���һ����Դ�ļ�
												.addClasspathResource("diagrams/helloWorld.png")
												.deploy();//��ɲ���
		
		System.out.println("����id: " + deployment.getId());
		System.out.println("��������: " + deployment.getName());
	}
	
	//���̶��壨��zip��
	@Test
	public void deployProcessDefine_zip(){
		String title="���̶���";
		InputStream in = this.getClass().getClassLoader().getResourceAsStream("diagrams/hellowWorld.zip");
		ZipInputStream zipInputStream = new ZipInputStream(in);
		Deployment deployment = processEngine.getRepositoryService()//�õ����̶����벿����ص�service
												.createDeployment()//����һ���������
												.name(title)
												.addZipInputStream(zipInputStream)
												.deploy();//��ɲ���
		
		System.out.println("����id: " + deployment.getId());
		System.out.println("��������: " + deployment.getName());
	}
	
	//��������ʾ��
	@Test
	public void startProcessInstance(){
		String key = "helloWorld";//�����������̵�key��Ӧbpmn�ļ��е�id
		ProcessInstance processInstance = processEngine.getRuntimeService()//�õ�ִ�е�����ʵ����ִ�ж�����ص�service
											.startProcessInstanceByKey(key);//ʹ��key����Ĭ��ʹ�����°汾�����̶�������
		System.out.println("����ʵ��Id :" + processInstance.getId());
		System.out.println("�����̻�ڵ�id :" + processInstance.getActivityId());
	}

	//��ѯ��ǰ�˵ĸ�������
	@Test
	public void findMyPersonalTask(){
		String name="����";
		List<Task> qryList = processEngine.getTaskService()//������ִ��������ص�service
			.createTaskQuery()
			/*����*/
			.taskAssignee(name)//��ѯ˭������
//			.processDefinitionId(arg0)//���̶���id��ѯ
//			.executionId(arg0)//ִ�ж���id��ѯ
			
			/*����*/
//			.orderByTaskCreateTime().asc()
			
			/*�����*/
//			.singleResult()
//			.listPage(arg0, arg1)
			.list();
		if(qryList!=null && qryList.size()>0){
			for(Task t : qryList){
				System.out.println("����id: "+t.getId());
				System.out.println("��������: "+t.getName());
				System.out.println("���ﴴ��ʱ��: "+t.getCreateTime());
				System.out.println("���������: "+t.getAssignee());
				System.out.println("����ʵ��id: "+t.getProcessInstanceId());
				System.out.println("ִ�ж���id: "+t.getExecutionId());
				System.out.println("���̶���id: "+t.getProcessDefinitionId());
			}
		}
	}
	
	//�������
	@Test
	public void completeMyPersonalTask(){
		String taskId="804";
		processEngine.getTaskService()//������ִ��������ص�service
			.complete(taskId);
		System.out.println("�������: "+taskId);
	}
	
	//�����Ƿ����
	@Test
	public void isProcessEnd(){
		String id="801";
		ProcessInstance pi = processEngine.getRuntimeService()//������ִ�е�����ʾ����ִ�ж����й�
			.createProcessInstanceQuery()//��������ʵ����ѯ
			.singleResult();
		if(pi==null){
			System.out.println("�������Ѿ�����");
		}else{
			System.out.println("������û�н���");
		}
	}
	
	//��ѯ��ʷ����
	@Test
	public void viewHistoryTask(){
		String name = "����";
		List<HistoricTaskInstance> list = processEngine.getHistoryService()//�õ�����ʷ��ص�service
			.createHistoricTaskInstanceQuery()//��ʷ�����ѯ
			.taskAssignee(name)//����������
			.list();
		
		if(list!=null && list.size()>0){
			for(HistoricTaskInstance historicTaskInstance : list){
				System.out.println(historicTaskInstance.getId()+"	"+
						historicTaskInstance.getProcessDefinitionId()+"	"+
						historicTaskInstance.getTaskDefinitionKey()+"	"+
						historicTaskInstance.getProcessInstanceId()+"	"+
						historicTaskInstance.getExecutionId()+"	"+
						historicTaskInstance.getName()+"	"+
						historicTaskInstance.getAssignee()+"	"+
						historicTaskInstance.getStartTime()+"	"+
						historicTaskInstance.getDurationInMillis());
				System.out.println("##########################################");
			}
		}
	}
	
	@Test
	public void viewHistoryProcess(){
		String ProcessInstanceId = "801";
		
		List<HistoricProcessInstance> list = processEngine.getHistoryService()//�õ�����ʷ��ص�service
				.createHistoricProcessInstanceQuery()//��ʷ����ʵ����ѯ
				.processInstanceId(ProcessInstanceId)
				.list();
		if(list!=null && list.size()>0){
			for(HistoricProcessInstance historicTaskInstance : list){
				System.out.println(historicTaskInstance.getId()+"	"+
						historicTaskInstance.getProcessDefinitionId()+"	"+
						historicTaskInstance.getStartTime());
				System.out.println("##########################################");
			}
		}
	}
	
}
