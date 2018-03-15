package com.activiti.test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.apache.commons.io.FileUtils;
import org.junit.Test;

public class ProcessDefineTest {
	ProcessEngine processEngine = ProcessEngineConfiguration.createProcessEngineConfigurationFromResourceDefault()//��classpath�¼��������ļ�
			.buildProcessEngine();//��������
	
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
	
	//���̶����ѯ
	@Test
	public void queryProcessDefine(){
		List<ProcessDefinition> processDefinitionsList = processEngine.getRepositoryService()//������̶����벿����ص�service
			.createProcessDefinitionQuery()//���������̶�����صĲ�ѯ
			/**��ѯ����*/
//			.deploymentId(id)//ʹ�ò�������id��ѯ
//			.processDefinitionId(processDefinitionId)//ʹ�����̶���id��ѯ
//			.processDefinitionKey(processDefinitionKey)//ʹ���������̶����key��ѯ
//			.processDefinitionKeyLike(processDefinitionKey)//���̶���keyģ����ѯ
//			.processDefinitionName(processDefinitionName)//���̶������Ʋ�ѯ
			
			/**����**/
			.orderByDeploymentId().asc()//�������̶���id����
//			.orderByProcessDefinitionVersion()//���հ汾��������
			
			/**���ؽ��**/
			.list();//���ؽ����
//			.listPage(firstResult, maxResult)//��ҳ��ѯ
//			.count()//��������
//			.singleResult()//����Ψһ���
		if(processDefinitionsList!=null && processDefinitionsList.size()>0){
			for(ProcessDefinition p:processDefinitionsList){
				System.out.println("���̶���id: "+p.getId());
				System.out.println("���̶�������: "+p.getName());//��Ӧbpmn�ļ��е�name
				System.out.println("���̶���key: "+p.getKey());//��Ӧbpmn�ļ��е�id
				System.out.println("���̶���汾: "+p.getVersion());
				System.out.println("���̶�����Դ���ƣ�bpmn��: "+p.getResourceName());
				System.out.println("���̶�����Դ���ƣ�png��: "+p.getDiagramResourceName());
				System.out.println("�������id: "+p.getDeploymentId());
				System.out.println("-----------------------------------------");
			}
		}
	}
	
	//ɾ�����̶���
	@Test
	public void delProcessDefine(){
		String deploymentId = "601";
		/**
		 * ��ͨɾ��
		 * 	��������������׳��쳣
		 */
//		processEngine.getRepositoryService()
//			.deleteDeployment(deploymentId);
		
		/**
		 * ��������Ҳ��ɾ��
		 */
		processEngine.getRepositoryService()
			.deleteDeployment(deploymentId, true);
		
		System.out.println("ɾ���ɹ�");
	}
	
	@Test
	//�鿴����ͼ
	public void viewProcessPic() throws IOException{
		String deploymentId = "101";
		//�õ���Դ����
		List<String> resNamesList = processEngine.getRepositoryService()
			.getDeploymentResourceNames(deploymentId);
		//�õ�png
		String resName = "";
		if(resNamesList!=null && resNamesList.size()>0){
			for(String name : resNamesList){
				if(name.indexOf(".png")>-1){
					resName = name;
				}
			}
		}
		//�õ�ͼƬ������
		InputStream in = processEngine.getRepositoryService()
			.getResourceAsStream(deploymentId, resName);
		//��ͼƬ���ɵ�d
		File f = new File("D:/"+resName);
		FileUtils.copyInputStreamToFile(in, f);
	}
	
	@Test
	//�鿴���°汾������
	public void viewLastVersionProcessDefine(){
		List<ProcessDefinition> list = processEngine.getRepositoryService()
				.createProcessDefinitionQuery()
				.orderByProcessDefinitionVersion().asc().list();//���汾�����������̶���
		Map<String, ProcessDefinition> lastVersionPd = new LinkedHashMap<String, ProcessDefinition>();//�������map
		if(list!=null && list.size()>0){
			for(ProcessDefinition pd:list){
				lastVersionPd.put(pd.getKey(), pd);
			}
		}
		for (Map.Entry<String, ProcessDefinition> entry : lastVersionPd.entrySet()) {
	        ProcessDefinition p = entry.getValue();
	        System.out.println("���̶���id: "+p.getId());
			System.out.println("���̶�������: "+p.getName());//��Ӧbpmn�ļ��е�name
			System.out.println("���̶���key: "+p.getKey());//��Ӧbpmn�ļ��е�id
			System.out.println("���̶���汾: "+p.getVersion());
			System.out.println("���̶�����Դ���ƣ�bpmn��: "+p.getResourceName());
			System.out.println("���̶�����Դ���ƣ�png��: "+p.getDiagramResourceName());
			System.out.println("�������id: "+p.getDeploymentId());
			System.out.println("-----------------------------------------");
	    }
	}
	
	@Test
	//ɾ��keyֵ��ͬ���������̶���
	public void delProcessDefineBykey(){
		String key="helloWorld";
		List<ProcessDefinition> list = processEngine.getRepositoryService()
				.createProcessDefinitionQuery()
				.processDefinitionKey(key)//����key��ѯ
				.list();//�õ��������̶���
		if(list!=null && list.size()>0){
			for(ProcessDefinition pd:list){
				String deploymentId = pd.getDeploymentId();
				processEngine.getRepositoryService().deleteDeployment(deploymentId, true);
			}
		}
		System.out.println("ɾ���ɹ�");
	}
}
