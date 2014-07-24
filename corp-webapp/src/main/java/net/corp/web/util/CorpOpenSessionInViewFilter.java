package net.corp.web.util;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.orm.hibernate4.support.OpenSessionInViewFilter;

public class CorpOpenSessionInViewFilter extends OpenSessionInViewFilter {

	private static Logger LOGGER = Logger
			.getLogger(CorpOpenSessionInViewFilter.class.getName());

	private final static String FILTER_EXCLUDE_SEPARATOR = ";";

	// Path patterns separated by ";"
	private String excludeServletPaths;
	// The file extension patterns that need to be excluded. The patterns should
	// be in "*.<extension>" format separated by a ";".
	private String excludeFileExtensionPatterns;

	// ArrayList to hold the individual elements of the pattern; will be
	// populated on filter config load
	private List<String> excludeServletPathsList = null;
	private List<String> excludeFileExtensionPatternsList = null;

	public String getExcludeServletPaths() {
		return excludeServletPaths;
	}

	public void setExcludeServletPaths(String excludeServletPaths) {
		this.excludeServletPaths = excludeServletPaths;
	}

	public String getExcludeFileExtensionPatterns() {
		return excludeFileExtensionPatterns;
	}

	public void setExcludeFileExtensionPatterns(
			String excludeFileExtensionPatterns) {
		this.excludeFileExtensionPatterns = excludeFileExtensionPatterns;
	}

	/**
	 * This method gets the init parameters populated though the filter's
	 * init-param configuration and then populates the list to set the file
	 * extension and servlet path exclusions
	 */
	@Override
	protected void initFilterBean() throws ServletException {

		// Check if the init-param were loaded and if so populate the list. We
		// use an array first to load the strings as they are immutable and
		// hence we cannot change individual elements in List
		if (this.excludeServletPaths != null) {
			String[] excludeServletPathsArray = this.excludeServletPaths
					.split(FILTER_EXCLUDE_SEPARATOR);
			if (excludeServletPathsArray != null) {
				this.excludeServletPathsList = new ArrayList<String>();
				// Remove the "*" wild card
				for (String servletPath : excludeServletPathsArray) {
					this.excludeServletPathsList.add(servletPath.replace("*",
							""));
				}
			}

			LOGGER.info("Retrieved the exclude servlet path list: "
					+ this.excludeServletPathsList);
		}

		if (this.excludeFileExtensionPatterns != null) {
			String[] excludeFileExtensionPatternsArray = this.excludeFileExtensionPatterns
					.split(FILTER_EXCLUDE_SEPARATOR);
			if (excludeFileExtensionPatternsArray != null) {
				this.excludeFileExtensionPatternsList = new ArrayList<String>();
				// Remove the "*" wild card
				for (String fileExtension : excludeFileExtensionPatternsArray) {
					this.excludeFileExtensionPatternsList.add(fileExtension
							.replace("*", ""));
				}
			}

			LOGGER.info("Retrieved the exclude file extension list: "
					+ this.excludeFileExtensionPatternsList);
		}

		super.initFilterBean();
	}

	/**
	 * Can be overridden in subclasses for custom filtering control, returning
	 * true to avoid filtering of the given request. The default implementation
	 * always returns false
	 * 
	 * @param request
	 *            - current HTTP request
	 * @return whether the given request should <b>not</b> be filtered
	 */
	@Override
	protected boolean shouldNotFilter(HttpServletRequest request)
			throws ServletException {

		Boolean returnWhetherNotFilter = false;

		String servletFullPath = request.getServletPath()
				+ (request.getPathInfo() == null ? "" : request.getPathInfo());

		// First check if the servlet path is to be not filtered
		if (this.excludeServletPathsList != null) {
			for (String servletPathName : this.excludeServletPathsList) {
				if (servletFullPath.startsWith(servletPathName)) {
					returnWhetherNotFilter = true;
					break;
				}
			}
		}

		// Then check the file extension
		if (this.excludeFileExtensionPatternsList != null
				&& !returnWhetherNotFilter) {
			for (String fileExtensionName : this.excludeFileExtensionPatternsList) {
				if (servletFullPath.endsWith(fileExtensionName)) {
					returnWhetherNotFilter = true;
					break;
				}
			}
		}

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("The request full path is: " + servletFullPath
					+ " and the return  value of shouldNotFilter() method is: "
					+ returnWhetherNotFilter);
		}

		return returnWhetherNotFilter;
	}
}