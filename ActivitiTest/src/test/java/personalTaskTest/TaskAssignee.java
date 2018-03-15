package personalTaskTest;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;

public class TaskAssignee implements TaskListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = -841692865267926592L;

	@Override
	public void notify(DelegateTask dt) {
		dt.setAssignee("Ãð¾øÊ¦Ì«");
	}

}
