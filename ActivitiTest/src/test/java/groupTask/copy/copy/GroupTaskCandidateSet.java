package groupTask.copy.copy;

import java.util.Arrays;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;

public class GroupTaskCandidateSet implements TaskListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6693099271367963854L;

	@Override
	public void notify(DelegateTask dt) {
		String[] userids = {"����","�ٺ�","����"};
		dt.addCandidateUsers(Arrays.asList(userids));
		
	}

}
