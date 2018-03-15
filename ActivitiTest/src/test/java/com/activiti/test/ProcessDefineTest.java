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
	ProcessEngine processEngine = ProcessEngineConfiguration.createProcessEngineConfigurationFromResourceDefault()//从classpath下加载配置文件
			.buildProcessEngine();//流程引擎
	
	//流程定义（从classpath）
	@Test
	public void deployProcessDefine_classPath(){
		String title="helloWorld入门程序";
		Deployment deployment = processEngine.getRepositoryService()//拿到流程定义与部署相关的service
												.createDeployment()//创建一个部署对象
												.name(title)
												.addClasspathResource("diagrams/helloWorld.bpmn")//从classpath加载资源，一次只能加载一个资源文件
												.addClasspathResource("diagrams/helloWorld.png")
												.deploy();//完成部署
		
		System.out.println("部署id: " + deployment.getId());
		System.out.println("部署名称: " + deployment.getName());
	}
	
	//流程定义（从zip）
	@Test
	public void deployProcessDefine_zip(){
		String title="流程定义";
		InputStream in = this.getClass().getClassLoader().getResourceAsStream("diagrams/hellowWorld.zip");
		ZipInputStream zipInputStream = new ZipInputStream(in);
		Deployment deployment = processEngine.getRepositoryService()//拿到流程定义与部署相关的service
												.createDeployment()//创建一个部署对象
												.name(title)
												.addZipInputStream(zipInputStream)
												.deploy();//完成部署
		
		System.out.println("部署id: " + deployment.getId());
		System.out.println("部署名称: " + deployment.getName());
	}
	
	//流程定义查询
	@Test
	public void queryProcessDefine(){
		List<ProcessDefinition> processDefinitionsList = processEngine.getRepositoryService()//获得流程定义与部署相关的service
			.createProcessDefinitionQuery()//创建与流程定义相关的查询
			/**查询条件*/
//			.deploymentId(id)//使用部署对象的id查询
//			.processDefinitionId(processDefinitionId)//使用流程定义id查询
//			.processDefinitionKey(processDefinitionKey)//使用流程流程定义的key查询
//			.processDefinitionKeyLike(processDefinitionKey)//流程定义key模糊查询
//			.processDefinitionName(processDefinitionName)//流程定义名称查询
			
			/**排序**/
			.orderByDeploymentId().asc()//按照流程定义id升序
//			.orderByProcessDefinitionVersion()//按照版本降序排列
			
			/**返回结果**/
			.list();//返回结果集
//			.listPage(firstResult, maxResult)//分页查询
//			.count()//返回数量
//			.singleResult()//返回唯一结果
		if(processDefinitionsList!=null && processDefinitionsList.size()>0){
			for(ProcessDefinition p:processDefinitionsList){
				System.out.println("流程定义id: "+p.getId());
				System.out.println("流程定义名称: "+p.getName());//对应bpmn文件中的name
				System.out.println("流程定义key: "+p.getKey());//对应bpmn文件中的id
				System.out.println("流程定义版本: "+p.getVersion());
				System.out.println("流程定义资源名称（bpmn）: "+p.getResourceName());
				System.out.println("流程定义资源名称（png）: "+p.getDiagramResourceName());
				System.out.println("部署对象id: "+p.getDeploymentId());
				System.out.println("-----------------------------------------");
			}
		}
	}
	
	//删除流程定义
	@Test
	public void delProcessDefine(){
		String deploymentId = "601";
		/**
		 * 普通删除
		 * 	若流程启动则会抛出异常
		 */
//		processEngine.getRepositoryService()
//			.deleteDeployment(deploymentId);
		
		/**
		 * 流程启动也可删除
		 */
		processEngine.getRepositoryService()
			.deleteDeployment(deploymentId, true);
		
		System.out.println("删除成功");
	}
	
	@Test
	//查看流程图
	public void viewProcessPic() throws IOException{
		String deploymentId = "101";
		//拿到资源名称
		List<String> resNamesList = processEngine.getRepositoryService()
			.getDeploymentResourceNames(deploymentId);
		//拿到png
		String resName = "";
		if(resNamesList!=null && resNamesList.size()>0){
			for(String name : resNamesList){
				if(name.indexOf(".png")>-1){
					resName = name;
				}
			}
		}
		//拿到图片输入流
		InputStream in = processEngine.getRepositoryService()
			.getResourceAsStream(deploymentId, resName);
		//将图片生成到d
		File f = new File("D:/"+resName);
		FileUtils.copyInputStreamToFile(in, f);
	}
	
	@Test
	//查看最新版本的流程
	public void viewLastVersionProcessDefine(){
		List<ProcessDefinition> list = processEngine.getRepositoryService()
				.createProcessDefinitionQuery()
				.orderByProcessDefinitionVersion().asc().list();//按版本升序排列流程定义
		Map<String, ProcessDefinition> lastVersionPd = new LinkedHashMap<String, ProcessDefinition>();//有排序的map
		if(list!=null && list.size()>0){
			for(ProcessDefinition pd:list){
				lastVersionPd.put(pd.getKey(), pd);
			}
		}
		for (Map.Entry<String, ProcessDefinition> entry : lastVersionPd.entrySet()) {
	        ProcessDefinition p = entry.getValue();
	        System.out.println("流程定义id: "+p.getId());
			System.out.println("流程定义名称: "+p.getName());//对应bpmn文件中的name
			System.out.println("流程定义key: "+p.getKey());//对应bpmn文件中的id
			System.out.println("流程定义版本: "+p.getVersion());
			System.out.println("流程定义资源名称（bpmn）: "+p.getResourceName());
			System.out.println("流程定义资源名称（png）: "+p.getDiagramResourceName());
			System.out.println("部署对象id: "+p.getDeploymentId());
			System.out.println("-----------------------------------------");
	    }
	}
	
	@Test
	//删除key值相同的所有流程定义
	public void delProcessDefineBykey(){
		String key="helloWorld";
		List<ProcessDefinition> list = processEngine.getRepositoryService()
				.createProcessDefinitionQuery()
				.processDefinitionKey(key)//按照key查询
				.list();//拿到所有流程定义
		if(list!=null && list.size()>0){
			for(ProcessDefinition pd:list){
				String deploymentId = pd.getDeploymentId();
				processEngine.getRepositoryService().deleteDeployment(deploymentId, true);
			}
		}
		System.out.println("删除成功");
	}
}
