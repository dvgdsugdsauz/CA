package com.ca.charteredAccountant.common;

public final class URLConstants {

	private URLConstants() {
	}

	public static final String API_BASE = "/api";
	public static final String PUBLIC = "/public";
	public static final String ADMIN = "/admin";

	public static final String PUBLIC_PATTERN = API_BASE + PUBLIC + "/**";
	public static final String ADMIN_PATTERN = API_BASE + ADMIN + "/**";

	public static class Auth {

//		=========================== For Authentication ========================

		public static final String LOGIN = "/auth/login";
		public static final String GET_PROFILE = ADMIN + "/auth/profile";
		public static final String CHANGE_PASSWORD = ADMIN + "/auth/change/password";

		public static final String LOGIN_PATTERN = API_BASE + LOGIN;
	}

	public static class OfficeLocation {

//		=========================== For Office Location (city landing pages) ========================

		public static final String GET_ALL_ACTIVE_LOCATIONS = PUBLIC + "/office/location/get/all";
		public static final String GET_LOCATION_BY_SLUG = PUBLIC + "/office/location/get/{slug}";

		public static final String GET_ALL_LOCATIONS = ADMIN + "/office/location/get/all";
		public static final String GET_LOCATION_BY_ID = ADMIN + "/office/location/get/{locationId}";
		public static final String SAVE_LOCATION = ADMIN + "/office/location/save";
		public static final String DELETE_LOCATION = ADMIN + "/office/location/delete";
	}

	public static class ServiceCategory {

//		=========================== For Service Category ========================

		public static final String GET_ALL_CATEGORIES = PUBLIC + "/service/category/get/all";

		public static final String SAVE_CATEGORY = ADMIN + "/service/category/save";
		public static final String DELETE_CATEGORY = ADMIN + "/service/category/delete";
	}

	public static class FirmService {

//		=========================== For Firm Service (service pages) ========================

		public static final String GET_ALL_ACTIVE_SERVICES = PUBLIC + "/firm/service/get/all";
		public static final String GET_SERVICE_BY_SLUG = PUBLIC + "/firm/service/get/{slug}";
		public static final String GET_SERVICE_DROPDOWN = PUBLIC + "/firm/service/get/dropdown";

		public static final String GET_ALL_SERVICES = ADMIN + "/firm/service/get/all";
		public static final String GET_SERVICE_BY_ID = ADMIN + "/firm/service/get/{serviceId}";
		public static final String SAVE_SERVICE = ADMIN + "/firm/service/save";
		public static final String DELETE_SERVICE = ADMIN + "/firm/service/delete";
	}

	public static class Industry {

//		=========================== For Industry ========================

		public static final String GET_ALL_INDUSTRIES = PUBLIC + "/industry/get/all";

		public static final String SAVE_INDUSTRY = ADMIN + "/industry/save";
		public static final String DELETE_INDUSTRY = ADMIN + "/industry/delete";
	}

	public static class Faq {

//		=========================== For FAQ ========================

		public static final String GET_ACTIVE_FAQS = PUBLIC + "/faq/get/all";

		public static final String GET_ALL_FAQS = ADMIN + "/faq/get/all";
		public static final String SAVE_FAQ = ADMIN + "/faq/save";
		public static final String DELETE_FAQ = ADMIN + "/faq/delete";
	}

	public static class Testimonial {

//		=========================== For Testimonial ========================

		public static final String GET_ACTIVE_TESTIMONIALS = PUBLIC + "/testimonial/get/all";

		public static final String GET_ALL_TESTIMONIALS = ADMIN + "/testimonial/get/all";
		public static final String SAVE_TESTIMONIAL = ADMIN + "/testimonial/save";
		public static final String DELETE_TESTIMONIAL = ADMIN + "/testimonial/delete";
	}

	public static class TeamMember {

//		=========================== For Team Member ========================

		public static final String GET_ACTIVE_TEAM_MEMBERS = PUBLIC + "/team/member/get/all";

		public static final String GET_ALL_TEAM_MEMBERS = ADMIN + "/team/member/get/all";
		public static final String SAVE_TEAM_MEMBER = ADMIN + "/team/member/save";
		public static final String DELETE_TEAM_MEMBER = ADMIN + "/team/member/delete";
	}

	public static class Article {

//		=========================== For Article (newsletters) ========================

		public static final String GET_PUBLISHED_ARTICLES = PUBLIC + "/article/get/all";
		public static final String GET_ARTICLE_BY_SLUG = PUBLIC + "/article/get/{slug}";

		public static final String GET_ALL_ARTICLES = ADMIN + "/article/get/all";
		public static final String GET_ARTICLE_BY_ID = ADMIN + "/article/get/{articleId}";
		public static final String SAVE_ARTICLE = ADMIN + "/article/save";
		public static final String DELETE_ARTICLE = ADMIN + "/article/delete";
	}

	public static class ComplianceDeadline {

//		=========================== For Compliance Deadline (due-date calendar) ========================

		public static final String GET_UPCOMING_DEADLINES = PUBLIC + "/compliance/deadline/get/upcoming";

		public static final String GET_ALL_DEADLINES = ADMIN + "/compliance/deadline/get/all";
		public static final String SAVE_DEADLINE = ADMIN + "/compliance/deadline/save";
		public static final String DELETE_DEADLINE = ADMIN + "/compliance/deadline/delete";
	}

	public static class Enquiry {

//		=========================== For Enquiry (leads) ========================

		public static final String SUBMIT_ENQUIRY = PUBLIC + "/enquiry/submit";

		/** Query params: page, size, optional status, optional search. */
		public static final String GET_ALL_ENQUIRIES = ADMIN + "/enquiry/get/all";
		public static final String GET_ENQUIRY_STATUS_COUNTS = ADMIN + "/enquiry/get/status/counts";
		public static final String GET_ENQUIRY_BY_ID = ADMIN + "/enquiry/get/{enquiryId}";
		public static final String UPDATE_ENQUIRY = ADMIN + "/enquiry/update";
	}

	public static class Newsletter {

//		=========================== For Newsletter Subscriber ========================

		public static final String SUBSCRIBE = PUBLIC + "/newsletter/subscribe";
		public static final String UNSUBSCRIBE = PUBLIC + "/newsletter/unsubscribe";

		public static final String GET_ALL_SUBSCRIBERS = ADMIN + "/newsletter/subscriber/get/all";
	}

	public static class JobOpening {

//		=========================== For Job Opening (careers) ========================

		public static final String GET_ACTIVE_JOB_OPENINGS = PUBLIC + "/job/opening/get/all";
		public static final String GET_JOB_OPENING_BY_SLUG = PUBLIC + "/job/opening/get/{slug}";

		public static final String GET_ALL_JOB_OPENINGS = ADMIN + "/job/opening/get/all";
		public static final String GET_JOB_OPENING_BY_ID = ADMIN + "/job/opening/get/{jobId}";
		public static final String SAVE_JOB_OPENING = ADMIN + "/job/opening/save";
		public static final String DELETE_JOB_OPENING = ADMIN + "/job/opening/delete";
	}

	public static class JobApplication {

//		=========================== For Job Application ========================

		public static final String APPLY_FOR_JOB = PUBLIC + "/job/application/apply";
		public static final String GET_ALL_APPLICATIONS = ADMIN + "/job/application/get/all";
		public static final String UPDATE_APPLICATION_STATUS = ADMIN + "/job/application/update/status";
		public static final String DOWNLOAD_RESUME = ADMIN + "/job/application/resume/{applicationId}";
	}

	public static class AdminUser {

//		=========================== For Admin User (ADMIN role only) ========================

		public static final String GET_ALL_USERS = ADMIN + "/user/get/all";
		public static final String GET_USER_DROPDOWN = ADMIN + "/user/get/dropdown";
		public static final String SAVE_USER = ADMIN + "/user/save";
		public static final String DELETE_USER = ADMIN + "/user/delete";
	}

}
