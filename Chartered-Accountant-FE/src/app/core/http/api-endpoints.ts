/*
 * Backend endpoints, relative to `environment.apiUrl` (the backend's API_BASE, "/api").
 * Mirrors com.ca.charteredAccountant.common.URLConstants name for name. Keep the two in step.
 */

const PUBLIC = '/public';
const ADMIN = '/admin';

const seg = (value: string | number) => encodeURIComponent(String(value));

export const API = {
  Auth: {
    LOGIN: '/auth/login',
    GET_PROFILE: `${ADMIN}/auth/profile`,
    CHANGE_PASSWORD: `${ADMIN}/auth/change/password`,
  },

  OfficeLocation: {
    GET_ALL_ACTIVE_LOCATIONS: `${PUBLIC}/office/location/get/all`,
    GET_LOCATION_BY_SLUG: (slug: string) => `${PUBLIC}/office/location/get/${seg(slug)}`,
    GET_ALL_LOCATIONS: `${ADMIN}/office/location/get/all`,
    GET_LOCATION_BY_ID: (locationId: number) => `${ADMIN}/office/location/get/${seg(locationId)}`,
    SAVE_LOCATION: `${ADMIN}/office/location/save`,
    DELETE_LOCATION: `${ADMIN}/office/location/delete`,
  },

  ServiceCategory: {
    GET_ALL_CATEGORIES: `${PUBLIC}/service/category/get/all`,
    SAVE_CATEGORY: `${ADMIN}/service/category/save`,
    DELETE_CATEGORY: `${ADMIN}/service/category/delete`,
  },

  FirmService: {
    GET_ALL_ACTIVE_SERVICES: `${PUBLIC}/firm/service/get/all`,
    GET_SERVICE_BY_SLUG: (slug: string) => `${PUBLIC}/firm/service/get/${seg(slug)}`,
    GET_SERVICE_DROPDOWN: `${PUBLIC}/firm/service/get/dropdown`,
    GET_ALL_SERVICES: `${ADMIN}/firm/service/get/all`,
    GET_SERVICE_BY_ID: (serviceId: number) => `${ADMIN}/firm/service/get/${seg(serviceId)}`,
    SAVE_SERVICE: `${ADMIN}/firm/service/save`,
    DELETE_SERVICE: `${ADMIN}/firm/service/delete`,
  },

  Industry: {
    GET_ALL_INDUSTRIES: `${PUBLIC}/industry/get/all`,
    SAVE_INDUSTRY: `${ADMIN}/industry/save`,
    DELETE_INDUSTRY: `${ADMIN}/industry/delete`,
  },

  Faq: {
    GET_ACTIVE_FAQS: `${PUBLIC}/faq/get/all`,
    GET_ALL_FAQS: `${ADMIN}/faq/get/all`,
    SAVE_FAQ: `${ADMIN}/faq/save`,
    DELETE_FAQ: `${ADMIN}/faq/delete`,
  },

  Testimonial: {
    GET_ACTIVE_TESTIMONIALS: `${PUBLIC}/testimonial/get/all`,
    GET_ALL_TESTIMONIALS: `${ADMIN}/testimonial/get/all`,
    SAVE_TESTIMONIAL: `${ADMIN}/testimonial/save`,
    DELETE_TESTIMONIAL: `${ADMIN}/testimonial/delete`,
  },

  TeamMember: {
    GET_ACTIVE_TEAM_MEMBERS: `${PUBLIC}/team/member/get/all`,
    GET_ALL_TEAM_MEMBERS: `${ADMIN}/team/member/get/all`,
    SAVE_TEAM_MEMBER: `${ADMIN}/team/member/save`,
    DELETE_TEAM_MEMBER: `${ADMIN}/team/member/delete`,
  },

  Article: {
    GET_PUBLISHED_ARTICLES: `${PUBLIC}/article/get/all`,
    GET_ARTICLE_BY_SLUG: (slug: string) => `${PUBLIC}/article/get/${seg(slug)}`,
    GET_ALL_ARTICLES: `${ADMIN}/article/get/all`,
    GET_ARTICLE_BY_ID: (articleId: number) => `${ADMIN}/article/get/${seg(articleId)}`,
    SAVE_ARTICLE: `${ADMIN}/article/save`,
    DELETE_ARTICLE: `${ADMIN}/article/delete`,
  },

  ComplianceDeadline: {
    GET_UPCOMING_DEADLINES: `${PUBLIC}/compliance/deadline/get/upcoming`,
    GET_ALL_DEADLINES: `${ADMIN}/compliance/deadline/get/all`,
    SAVE_DEADLINE: `${ADMIN}/compliance/deadline/save`,
    DELETE_DEADLINE: `${ADMIN}/compliance/deadline/delete`,
  },

  Enquiry: {
    SUBMIT_ENQUIRY: `${PUBLIC}/enquiry/submit`,
    GET_ALL_ENQUIRIES: `${ADMIN}/enquiry/get/all`,
    GET_ENQUIRY_STATUS_COUNTS: `${ADMIN}/enquiry/get/status/counts`,
    GET_ENQUIRY_BY_ID: (enquiryId: number) => `${ADMIN}/enquiry/get/${seg(enquiryId)}`,
    UPDATE_ENQUIRY: `${ADMIN}/enquiry/update`,
  },

  Newsletter: {
    SUBSCRIBE: `${PUBLIC}/newsletter/subscribe`,
    UNSUBSCRIBE: `${PUBLIC}/newsletter/unsubscribe`,
    GET_ALL_SUBSCRIBERS: `${ADMIN}/newsletter/subscriber/get/all`,
  },

  JobOpening: {
    GET_ACTIVE_JOB_OPENINGS: `${PUBLIC}/job/opening/get/all`,
    GET_JOB_OPENING_BY_SLUG: (slug: string) => `${PUBLIC}/job/opening/get/${seg(slug)}`,
    GET_ALL_JOB_OPENINGS: `${ADMIN}/job/opening/get/all`,
    GET_JOB_OPENING_BY_ID: (jobId: number) => `${ADMIN}/job/opening/get/${seg(jobId)}`,
    SAVE_JOB_OPENING: `${ADMIN}/job/opening/save`,
    DELETE_JOB_OPENING: `${ADMIN}/job/opening/delete`,
  },

  JobApplication: {
    APPLY_FOR_JOB: `${PUBLIC}/job/application/apply`,
    GET_ALL_APPLICATIONS: `${ADMIN}/job/application/get/all`,
    UPDATE_APPLICATION_STATUS: `${ADMIN}/job/application/update/status`,
    DOWNLOAD_RESUME: (applicationId: number) =>
      `${ADMIN}/job/application/resume/${seg(applicationId)}`,
  },

  AdminUser: {
    GET_ALL_USERS: `${ADMIN}/user/get/all`,
    GET_USER_DROPDOWN: `${ADMIN}/user/get/dropdown`,
    SAVE_USER: `${ADMIN}/user/save`,
    DELETE_USER: `${ADMIN}/user/delete`,
  },
} as const;

/** Paths under this prefix need the back-office token. */
export const ADMIN_PREFIX = ADMIN;
