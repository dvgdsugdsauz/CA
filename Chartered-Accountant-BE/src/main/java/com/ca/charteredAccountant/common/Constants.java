package com.ca.charteredAccountant.common;

public final class Constants {

	private Constants() {
	}

//	=========================== Response Status Codes ========================

	public static final int OK = 200;
	public static final int CREATED = 201;
	public static final int NO_CONTENT = 204;
	public static final int BAD_REQUEST = 400;
	public static final int UNAUTHORIZED = 401;
	public static final int FORBIDDEN = 403;
	public static final int NOT_FOUND = 404;
	public static final int METHOD_NOT_ALLOWED = 405;
	public static final int CONFLICT = 409;
	public static final int PAYLOAD_TOO_LARGE = 413;
	public static final int INTERNAL_SERVER_ERROR = 500;

//	=========================== Record Flags ========================

	public static final Boolean ACTIVE = Boolean.TRUE;
	public static final Boolean INACTIVE = Boolean.FALSE;

//	=========================== Paging ========================

	public static final int DEFAULT_PAGE = 0;
	public static final int DEFAULT_PAGE_SIZE = 20;
	public static final int MAX_PAGE_SIZE = 100;

//	=========================== Reference Numbers ========================

	public static final String ENQUIRY_REF_PREFIX = "ENQ";
	public static final String ENQUIRY_REF_FORMAT = "%s-%d-%06d";

//	=========================== Security ========================

	public static final String AUTH_HEADER = "Authorization";
	public static final String BEARER_PREFIX = "Bearer ";
	public static final String ROLE_PREFIX = "ROLE_";
	public static final String CLAIM_USER_ID = "uid";
	public static final String CLAIM_ROLE = "role";
	public static final String CLAIM_FULL_NAME = "name";

//	=========================== File Upload ========================

	public static final long MAX_RESUME_SIZE_BYTES = 5L * 1024 * 1024;
	public static final String[] ALLOWED_RESUME_EXTENSIONS = { "pdf", "doc", "docx" };

//	=========================== Validation Patterns ========================

	public static class Patterns {

		private Patterns() {
		}

		// EMAIL and PHONE accept "" so a blank field reports only its @NotBlank message.
		public static final String EMAIL = "^$|^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$";
		public static final String PHONE = "^$|^[+0-9 ()-]{8,20}$";
		public static final String SLUG = "^[a-z0-9]+(?:-[a-z0-9]+)*$";
	}

//	=========================== Response Messages ========================

	public static class ResponseMessages {

		private ResponseMessages() {
		}

		public static final String FETCH_MESSAGE = "Data fetched successfully";
		public static final String DATA_NOT_AVAILABLE_MESSAGE = "Data not available";
		public static final String UNABLE_TO_FETCH_MESSAGE = "Unable to fetch data";
		public static final String SAVE_MESSAGE = "Data saved successfully";
		public static final String UPDATE_MESSAGE = "Data updated successfully";
		public static final String UNABLE_SAVE_MESSAGE = "Unable to save data";
		public static final String DELETED_MESSAGE = "Data deleted successfully";
		public static final String UNABLE_DELETE_MESSAGE = "Unable to delete data";
		public static final String INPUT_REQUIRED_MESSAGE = "Input is required";
		public static final String VALIDATION_FAILED_MESSAGE = "Please correct the highlighted fields";
		public static final String DUPLICATE_MESSAGE = "A record with the same value already exists";
		public static final String INTERNAL_ERROR_MESSAGE = "Something went wrong. Please try again later";

		public static final String LOGIN_SUCCESS_MESSAGE = "Login successful";
		public static final String INVALID_CREDENTIALS_MESSAGE = "Invalid username or password";
		public static final String ACCOUNT_DISABLED_MESSAGE = "Your account is disabled. Contact the administrator";
		public static final String UNAUTHORIZED_MESSAGE = "Please sign in to continue";
		public static final String FORBIDDEN_MESSAGE = "You do not have permission to perform this action";
		public static final String PASSWORD_CHANGED_MESSAGE = "Password changed successfully";
		public static final String CURRENT_PASSWORD_WRONG_MESSAGE = "Current password is incorrect";

		public static final String ENQUIRY_SUBMITTED_MESSAGE = "Thank you. A chartered accountant will call you back within one working day";
		public static final String SUBSCRIBED_MESSAGE = "Subscribed to tax updates";
		public static final String ALREADY_SUBSCRIBED_MESSAGE = "This email is already subscribed";
		public static final String UNSUBSCRIBED_MESSAGE = "Unsubscribed from tax updates";
		public static final String APPLICATION_SUBMITTED_MESSAGE = "Application received. Our HR team will contact you if shortlisted";
		public static final String JOB_CLOSED_MESSAGE = "This opening is no longer accepting applications";
		public static final String INVALID_FILE_MESSAGE = "Upload a PDF, DOC or DOCX file up to 5 MB";
		public static final String FILE_TOO_LARGE_MESSAGE = "The uploaded file is too large";
	}

}
