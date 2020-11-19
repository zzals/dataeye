package kr.co.penta.dataeye;

import com.microstrategy.web.objects.WebIServerSession;
import com.microstrategy.web.objects.WebObjectsFactory;
import com.microstrategy.web.objects.admin.WebObjectsAdminException;
import com.microstrategy.web.objects.admin.monitors.EnumWebMonitorType;
import com.microstrategy.web.objects.admin.monitors.Job;
import com.microstrategy.web.objects.admin.monitors.JobResults;
import com.microstrategy.web.objects.admin.monitors.JobSource;
import com.microstrategy.web.objects.admin.monitors.MonitorFilter;
import com.microstrategy.webapi.EnumDSSXMLAuthModes;
import com.microstrategy.webapi.EnumDSSXMLJobInfo;
import com.microstrategy.webapi.EnumDSSXMLLevelFlags;
import com.microstrategy.webapi.EnumDSSXMLMonitorFilterOperator;

public class MstrJobList {

	public static void main(String[] args) throws Exception {
		
		WebObjectsFactory webObjectsFactory = WebObjectsFactory.getInstance();
		WebIServerSession webIServerSession = webObjectsFactory.getIServerSession();
		
		webIServerSession.setServerName("192.168.100.73");
		webIServerSession.setServerPort(0);
		webIServerSession.setProjectID("DCA153244D89152BE9198DB4AE7055E8");
		//webIServerSession.setAuthMode(EnumDSSXMLAuthModes.DssXmlAuthLDAP);
		webIServerSession.setLogin("Administrator");
		//webIServerSession.setPassword("Penta1234!@");
		webIServerSession.setPassword("");
		webIServerSession.setAuthMode(EnumDSSXMLAuthModes.DssXmlAuthStandard);
		
		JobSource source = (JobSource) webObjectsFactory.getMonitorSource(EnumWebMonitorType.WebMonitorTypeJob);
		
		MonitorFilter filter = source.getFilter();
		filter.add(EnumDSSXMLJobInfo.DssXmlJobInfoProjectName, EnumDSSXMLMonitorFilterOperator.DssXmlEqual, "ebay");
		source.setLevel(EnumDSSXMLLevelFlags.DssXmlBrowsingLevel);
		
		try {

		      // Sends the request to Intelligence Server to retrieve job information

		      JobResults results = source.getJobs();

		      System.out.println(results.getCount());

		      // Loop through the results to print out browsing information, such as

		      // the job ID, the user that owns the job, and duration, in seconds, of the job

		      for (int i=0; i < results.getCount(); i++) {

		           Job singleJob = results.get(i);

		           System.out.print("job ID: " + singleJob.getJobID());
		           System.out.print(", user: " + singleJob.getUserName());
		           System.out.print(", UserLoginName: " + singleJob.getUserLoginName());
		           System.out.print(", ClientMachine: " + singleJob.getClientMachine());
		           System.out.print(", Description: " + singleJob.getDescription());
		           System.out.print(", JobStatus: " + singleJob.getJobStatus());
		           System.out.print(", JobPriority: " + singleJob.getJobPriority());
		           System.out.print(", JobPriority: " + singleJob.getProjectDSSID());
		           System.out.print(", duration: " + singleJob.getDuration() + " sec");

		           System.out.println();

		      }

		 } catch (WebObjectsAdminException woae) {

		          woae.printStackTrace();

		 }
	}
}
