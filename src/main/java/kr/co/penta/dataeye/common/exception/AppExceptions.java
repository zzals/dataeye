package kr.co.penta.dataeye.common.exception;

import java.util.Arrays;

public class AppExceptions
{
	public static AppException jobNotFound(String jobId)
	{
		return new AppException("job.not.found", Arrays.asList(new String[] { jobId }));
	}

	public static AppException serverError(Throwable cause)
	{
		return new AppException("unexpected.error", cause);
	}
}
