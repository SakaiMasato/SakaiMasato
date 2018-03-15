package com.activiti.test;

import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricVariableInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.junit.Test;

import com.sun.jmx.snmp.tasks.TaskServer;

import entity.Person;

public class ProcessVariableTest {
	private ProcessEngine processEngine = ProcessEngineConfiguration.createProcessEngineConfigurationFromResourceDefault().buildProcessEngine();
	//���̶��壨��inputstrean��
		@Test
		public void deployProcessDefine_inputStream(){
			String title="ProcessVariable";
			InputStream bpmnIn = this.getClass().getResourceAsStream("/diagrams/processVariable.bpmn");
			InputStream pngIn = this.getClass().getResourceAsStream("/diagrams/processVariable.png");
			Deployment deployment = processEngine.getRepositoryService()//�õ����̶����벿����ص�service
													.createDeployment()//����һ���������
													.name(title)
													.addInputStream("processVariable.bpmn", bpmnIn)//name����ͼ����ļ���������ͬ
													.addInputStream("processVariable.png", pngIn)//name����ͼ����ļ���������ͬ
													.deploy();//��ɲ���
			
			System.out.println("����id: " + deployment.getId());
			System.out.println("��������: " + deployment.getName());
		}
		
		//��������
		@Test
		public void startProcess(){
			String key = "ProcessVariable";
			ProcessInstance pi = processEngine.getRuntimeService()//�õ�ִ�е�����ʵ����ִ�ж�����ص�service
				.startProcessInstanceByKey(key);
			System.out.println(pi.getProcessDefinitionId());//���̶���id
			System.out.println(pi.getProcessInstanceId());//����ʵ��id
			System.out.println(pi.getActivityId());//�����̻�ڵ�id
			System.out.println(pi.getId());//����ʵ��id
		}
		
		//���ñ�����
		@Test
		public void setVariable(){
			String taskId = "1504";
			TaskService taskService = processEngine.getTaskService();
//			taskService.setVariableLocal(taskId, "�ݼ�����", 3);//setVariableLocal�Ὣ�ñ�����taskId������,���������ñ����ÿ�
//			taskService.setVariable(taskId, "�ݼ�ʱ��", new Date());//����Local�򲻻��ÿ�
//			taskService.setVariable(taskId, "�ݼ�ԭ��", "�ؼ�̽��");
			Person p =new Person(10, "������");
			taskService.setVariable(taskId, "��Ա��Ϣ", p);//javabean���͵����̱������ñ������л�,����javabean�������Ϣ��������ʽ����bytearray_id_�ֶ���Ӧ����Դ����
			
			System.out.println("�������̱����ɹ�");
		}
		
		//��ñ���
		@Test
		public void getVariable(){//��һ��javabean���������̱����У�Ҫ���javabean�����ڱ仯���������,��javabean��������л���id��
			String taskId = "1504";
			TaskService taskService = processEngine.getTaskService();
			Map<String, Object> map =  taskService.getVariables(taskId);
			for(Map.Entry<String, Object> entry:map.entrySet()){
				System.out.println(entry.getKey()+" "+entry.getValue());
			}
		}
		
		@Test
		public void completeTask(){
			String taskId = "1702";
			processEngine.getTaskService().complete(taskId);
		}
		
		//ģ�����úͻ�ñ����ĳ���
		public void setAndGetVariable(){
			//������service���Բ������̱���
		RuntimeService runtimeService = processEngine.getRuntimeService();//������ִ�е�����ʵ����ִ�ж�����ص�service
		TaskService taskService = processEngine.getTaskService();//������ִ�е�������ص�service
		
		/*�������̱���*/
//		runtimeService.setVariable(excutionId, variableName, value);//ʹ��ִ�ж����id�������̱��������ƣ��������̱�����ֵ������һ��ֻ������һ�����̱�����ֵ
//		runtimeService.setVariables(excutionId, Map<variableName,value>);//ʹ��ִ�ж����id,��Map<���̱���������,���̱�����ֵ>��
		
//		taskService.setVariable(excutionId, variableName, value);
//		taskService.setVariables(excutionId, Map<variableName,value>);
		
//		runtimeService.startProcessInstanceByKey(processsDefinitionKey,Map<variableName,value>)//��������ʵ����ͬʱ�������������̱���
//		taskService.complete(taskId, Map<variableName,value>);//�������ʱ���������̱���
		
		/*��ȡ���̱���*/
//		runtimeService.getVariable(excutionId, variableName)
//		runtimeService.getVariables(excutionId);
//		runtimeService.getVariables(excutionId, list<variableName>);
		
//		taskService.getVariable(excutionId, variableName)
//		taskService.getVariables(excutionId);
//		taskService.getVariables(excutionId, list<variableName>);
		}
		
		//��ѯ��ʷ����
		@Test
		public void historyVariableQuery(){
			List<HistoricVariableInstance> list = processEngine.getHistoryService()
				.createHistoricVariableInstanceQuery()//��ʷ����ʵ��
				.variableName("�ݼ�����")
				.list();
			if(list!=null && list.size()>0){
				for (int i = 0; i < list.size(); i++) {
					System.out.println(list.get(i).getVariableName()+" "+list.get(i).getValue());
				}
			}
		}
}
